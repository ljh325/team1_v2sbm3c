import json
import os
import time
import requests
from datetime import datetime
import random
import cx_Oracle as cx
import pandas as pd

import tool

from openai import OpenAI

client = OpenAI(
  api_key=os.getenv('OPENAI_API_KEY')
)

from flask import Flask, request, render_template
from flask_cors import CORS

import tool # tool.py

app = Flask(__name__)  # __name__ == '__main__'
CORS(app)

prompt = '''
    아래의 [회원 건강정보]을 가진 회원이 아래의 [운동 목표]를 목표로 해서 [3]달간 운동중이야, [식단 목록]에서 운동 목표를 달성하기 위해 필요한 하루의 식단을 식단의 섭취량(g)을 포함해서 출력해줘 식품 코드 또한 출력
    출력 형식은 JSON으로 해줘 

    [회원 건강정보]
    체중: kg
    체지방: %
    신장: cm
    골격근량: kg
    

    [운동 목표]
    체중: kg
    체지방: %
    신장: cm
    골격근량: kg
    
    

    [식단 목록]
    식품종류	식품코드
    
   
    바게트	403000400
    베이글	404000400
    빵	405000100
    호빵	418000400
    떡	501000500

    두부	001000100
    유부	002000300
    묵	004000400
    국수	001000100
    메밀국수/냉면국수	008000100
    볶음면	009000200
    쌀국수	010000200
    우동면	011000100
    쫄면	012000100
    칼국수	013000100
    파스타	014000100

    과·채주스	302030100
    과·채음료	303030100
    탄산수	402040100
    두유	500050100
    유산균음료	601060100
 
    드레싱	205020400

    카레	301020400
    배추김치	101010200
    물김치	102010200

    견과류	302030200
    시리얼	400040000
    과일가공품	701070100
    채소가공품	702070100
    두류가공품	703070200
    서류가공품	704070400
    기타 농산가공품	705010100


    옥수수	709070200

    햄	100010100
    소시지	201020100
    발효소시지	202020200
    베이컨	300030000
    육포	400040000
    양념육	501050100
    식육간편조리	700030000
    기타 식육가공품	800010200
    달걀/메추리알	100010700
    우유	101010100
    우유(멸균)	102010100
    강화우유	201020100
    농후발효유	202040200
    우유,유당분해우유	203020300
    가공우유	204020400
    가공우유(멸균)	205020400
    발효유	401040100
    크림발효유	403040300
    연유	603060500
    유크림	701070200
    버터	801080100
    치즈	900090100
    어육가공품	100010400
    어묵	104010400
    고등어	107010600
    골뱅이	108060000
    꽁치	109010600
    연어	111060000
    정어리	112010600
    참치	113010600
    젓갈/액젓	200020100
    조미건어포	301010600
    원재료성 수산가공품	302060000
    김	401040000
    김자반	402040000
    기타 수산가공품	600040000
    추출가공식품	210040000
    밥류	201020200
    누룽지	202020300
    주먹밥/김밥/초밥	203020200
    즉석 면요리	204020100
    죽	205020200
    스프	206020100
    국/탕류	207020200
    찌개/전골류	208020200
    전	209020300
    조림/찜	210020200
    함박스테이크/미트볼	211020200
    반찬	212020100
    도시락	213020200
    순대	218020200
    미숫가루	219020200
    기타 시리얼	220020200
    시리얼바/에너지바/영양바	221020200
    즉석 빵	222020200
    버거	223020200
    샌드위치	224020200

    샐러드	226020100
    소스/드레싱	227020200
    육수	230020300
    기타 음료	231020200
    
    체중조절용 즉석식품	233020200
    만두	302020200
    만두피	304030200
    기타 즉석식품	305020200
    기타가공품	200010000


    [출력 형식]
    {
    "res": 추천 식사 목록
    }

    '''

# 리스트의 값을 prompt 문자열에 적용시키는 코드
def populate_prompt(prompt, member_info, goal_info):
    """
    주어진 prompt에 member_info와 goal_info의 값을 채워넣는 함수
    :param prompt: 원본 프롬프트 문자열
    :param member_info: [회원 건강정보] 리스트
    :param goal_info: [운동 목표] 리스트
    :return: 채워진 새로운 프롬프트 문자열
    """
    filled_prompt = prompt
    
    # [회원 건강정보]에 해당하는 값 대체
    filled_prompt = filled_prompt.replace("체중: kg", f"체중: {member_info[0]}kg", 1)
    filled_prompt = filled_prompt.replace("체지방: %", f"체지방: {member_info[1]}%", 1)
    filled_prompt = filled_prompt.replace("신장: cm", f"신장: {member_info[2]}cm", 1)
    filled_prompt = filled_prompt.replace("골격근량: kg", f"골격근량: {member_info[3]}kg", 1)
    
    # [운동 목표]에 해당하는 값 대체
    filled_prompt = filled_prompt.replace("체중: kg", f"체중: {goal_info[0]}kg", 1)
    filled_prompt = filled_prompt.replace("체지방: %", f"체지방: {goal_info[1]}%", 1)
    filled_prompt = filled_prompt.replace("신장: cm", f"신장: {goal_info[2]}cm", 1)
    filled_prompt = filled_prompt.replace("골격근량: kg", f"골격근량: {goal_info[3]}kg", 1)
    
    return filled_prompt

# http://localhost:5000/foodrecom/create.html
@app.route('/foodrecom/create/<int:goalsno>/<int:mhno>')
def foodrecom_create_form(goalsno, mhno):
    return render_template('foodrecom/create.html', goalsno=goalsno, mhno=mhno)

@app.route('/foodrecom/create', methods=['POST'])
def foodrecom_create_proc():

    goalsno = request.form.get('goalsno')
    mhno = request.form.get('mhno')
    #데이터 베이스에 연결
    conn = cx.connect("team1", "69017000", "3.39.75.85:1521/xe")
    cursor = conn.cursor()

    
    select_mh = '''
    SELECT kg,ckg,cm,muscle from mh
    where mhno = :mhno
    '''
    
    cursor.execute(select_mh, {'mhno': mhno})
    mh = cursor.fetchall()
    
    select_goals = '''
    SELECT kg,ckg,cm,muscle from goals
    where goalsno = :goalsno
    '''
    cursor.execute(select_goals, {'goalsno': goalsno})
    goals = cursor.fetchall()
   
    filled_prompt = populate_prompt(prompt, mh[0], goals[0])
    print(filled_prompt)
    response = tool.answer(role='헬스트레이너야', prompt=filled_prompt, output='json', 
                        format='{"food": "값"}', llm='gpt-4o') 

    frecom = str(response)
   
    
    insert_query = """
    INSERT INTO FOODRECOM (FOODRECOMNO, FRECOM, GOALSNO, MHNO, RDATE)
    VALUES (FOODRECOM_SEQ.nextval, :frecom, :goalsno, :mhno, sysdate)
    """

    # 바인드 변수 사용하여 JSON 문자열 삽입
    cursor.execute(insert_query, {'frecom': frecom,'goalsno' : goalsno,'mhno' : mhno})

    # 커밋
    conn.commit()

    print("JSON 문자열이 데이터베이스에 저장되었습니다.")

    # 연결 종료
    cursor.close()
    conn.close()
    return response


if __name__ == '__main__':
    app.run(host="0.0.0.0", port=5000, debug=True)  # 0.0.0.0: 모든 Host 에서 접속 가능, debug=True: 소스 변경시 자동 restart

'''
activate ai
python member_img.py
http://localhost:5000/member_img
'''

