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
    아래의 [회원 건강정보]를 바탕으로 [운동 목표]를 설정해서 운동중이야, [식단 목록]에서 운동 목표를 달성하기 위해 필요한 하루 식단을 출력해줘 출력한 식단에 [목록]의 식품종류에 포함되는 식품이 있을 경우 식품 코드 또한 출력
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
    강냉이/팝콘	101000100
    마카롱	102000100
    비스킷/쿠키/크래커	103000100
    스낵과자	104000100
    웨이퍼	105000100
    일반과자	106000100
    전통과자	107000100
    누가	201000200
    마시멜로	202000200
    사탕	203000200
    양갱	204000200
    젤리	205000100
    캐러멜	206000200
    코팅캔디	207000100
    푸딩	208000100
    껌	301000300
    도넛	401000400
    머핀	402000400
    바게트/치아바타	403000400
    베이글	404000400
    빵	405000100
    스콘	406000100
    식빵	407000400
    카스텔라	408000100
    케이크	409000100
    크로와상	410000100
    크로켓	411000400
    타르트	412000400
    파이/만쥬	413000100
    프레즐	414000400
    피자	415000400
    핫도그	416000400
    호떡	417000400
    호빵	418000400
    떡	501000500
    아이스크림	101010100
    아이스밀크	103010100
    샤베트	104010300
    빙과	301030000
    초콜릿	101010400
    초콜릿과자	102020200
    초코파이	103020500
    코코아	201010300
    코코아매스	202010100
    기타 코코아가공품	203010400
    설탕	101010100
    시럽	201020000
    물엿	601060100
    덩어리엿	602060200
    당류가공품	701030100
    잼	001000100
    두부	001000100
    유부	002000300
    묵	004000400
    기타 두부 가공품	005000100
    콩기름(대두유)	101010100
    옥수수기름(옥배유)	102010200
    유채씨유/카놀라유	103010300
    미강유(현미유)	104010400
    참기름	105010500
    들기름	107010700
    홍화유	109010900
    해바라기씨유	110011000
    땅콩기름(낙화생유)	112011200
    올리브유	113011300
    팜유	114011400
    고추씨기름	116011600
    식물성유지	117010100
    기타 식용유지	120030300
    아보카도오일	121011700
    포도씨유	122011700
    홍화씨유	123011700
    식용돈지	202020200
    혼합식용유	301030100
    향미유	302030200
    쇼트닝	304030400
    마가린	305030500
    식물성크림	307030700
    국수	001000100
    기타면	002000100
    라면	003000200
    자장라면	004000100
    기타 라면	005000200
    당면	006000100
    라이스페이퍼	007000300
    메밀국수/냉면국수	008000100
    볶음/비빔라면	009000200
    쌀국수	010000200
    우동면	011000100
    쫄면	012000100
    칼국수	013000100
    파스타	014000100
    라면과자	015000400
    침출차	101010100
    액상차	102010200
    분말차	103010300
    액상커피	201020000
    인스턴트커피	202020000
    과·채주스	302030100
    과·채음료	303030100
    탄산음료	401040100
    탄산수	402040100
    두유	500050100
    발효음료	600060300
    유산균음료	601060100
    효모음료	602060200
    인삼/홍삼음료	700070000
    액상음료	801080100
    농축음료	802080200
    성장기용 조제식	300030000
    영·유아용 이유식	400030000
    체중조절용 조제식품	500050000
    임산·수유부용 식품	600060000
    고령자용 영양조제식품	700070000
    조제유류	800010100
    영아용 조제식	900020000
    환자용 식품	000010100
    메주	002000200
    국간장	003000300
    진간장	004000300
    청국장	005001200
    나토	006001200
    된장	009000800
    고추장	010001000
    춘장	011001100
    혼합장	013000900
    식초	101010100
    복합조미식품	201020100
    마요네즈	202020200
    토마토케첩	203020300
    분말스프	204020100
    드레싱	205020400
    기타 소스류	206020400
    카레	301020400
    천연향신료	501050100
    소금	600060300
    고춧가루	700040100
    배추김치	101010200
    물김치	102010200
    기타김치	103010200
    김칫속	104010100
    장아찌	201020100
    단무지/피클	202010200
    과·채당절임	203020200
    기타 조림	300020100
    절임식품	400020100
    막걸리	101010100
    소주	102020100
    청주	103010300
    맥주	104010400
    과실주	105010500
    위스키	202020200
    고량주	206020400
    밀가루	200020100
    땅콩버터	301030100
    견과류	302030200
    시리얼	400040000
    과일가공품	701070100
    채소가공품	702070100
    두류가공품	703070200
    서류가공품	704070400
    기타 농산가공품	705010100
    빵가루	706070200
    부침가루/튀김가루/믹스	708070200
    옥수수	709070200
    호박씨분말	710070500
    냉동과일	711070100
    햄	100010100
    소시지	201020100
    발효소시지	202020200
    베이컨	300030000
    육포	400040000
    양념육	501050100
    식육간편조리	700030000
    기타 식육가공품	800010200
    달걀/메추리알	100010700
    알함유가공품	200010700
    분유	000100100
    전지/탈지분유	001100100
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
    식용곤충	200020000
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
    감자튀김	214020200
    튀김	215020200
    치킨	216020200
    떡볶이	217020200
    순대	218020200
    미숫가루	219020200
    기타 시리얼	220020200
    시리얼바/에너지바/영양바	221020200
    즉석 빵	222020200
    버거	223020200
    샌드위치	224020200
    즉석 피자	225020300
    샐러드	226020100
    소스/드레싱	227020200
    육수	230020300
    기타 음료	231020200
    성장기 즉석식품	232020200
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

