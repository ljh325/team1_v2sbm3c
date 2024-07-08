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
#데이터 분석
import matplotlib.pyplot as plt
import io
from flask import Flask, send_file
import seaborn as sns
import matplotlib.font_manager as fm
from matplotlib import font_manager, rc
import platform 


client = OpenAI(
  api_key=os.getenv('OPENAI_API_KEY')
)

from flask import Flask, request, render_template
from flask_cors import CORS

import tool # tool.py

app = Flask(__name__)  # __name__ == '__main__'
CORS(app)

prompt3 ='''
[회원 건강정보]에 해당하는 사람이 [운동 목표]의 신체를 목표할 때의 일주일간의 운동계획을  
아래의 조건을 모두 지키며 작성해줘 
1.[운동표]에 있는 값을 기준으로 일주일간의 루틴을 출력하나 그 이후에도 [운동 목표]를 위한 운동을 무제한의 기간동안 지속 한다고 가정한다
,대신 [난이도]에 있는 난이도를 기준으로 계획을 세운다
2. 출력은 [출력 형식1]을 따르나 해당하는 [난이도]의 값을 따르고 그에 해당하는 [운동 난이도]의 하루운동 종류와 일주일 중 운동일 수,하루 권장 운동시간을 적용시킨다

그에 따라 운동 수 즉,{"exname": , "musclesub": , "cal": , "time": ,"body":  ,"set:"},의 수가 달라질수 있고

휴식이 필요한 날은  "health":"휴식"을 할당한다 
3.각각 운동의 대략적인 예상 운동시간(분)과 그 시간동안 해당하는 운동을 했을 경우 소모되는 예상 칼로리를 할당한다 
4.일주일간 자극 될 각각 근육 부위의 휴식기간의 균형과 효율을 고려한다
3.[회원 건강정보]에 해당하는 사람이 [운동 목표]의 신체 정보를 목표로 하기에 적합한 운동을 할당한다 
4.상체운동이면 body에 13,하체면 body에 14를 할당할것
5.cal은 칼로리,set를 뜻한다


 [난이도]
 난이도:


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
    
 

 [출력 형식1]
    {"healths": {
  "rutin": [
    {
      "day": "월요일",
      "health": [
        {"exname": , "musclesub": , "cal": , "time": ,"body":  ,"set:"},
        {"exname": , "musclesub": , "cal": , "time": ,"body":  ,"set:"},
        {"exname": , "musclesub": , "cal": , "time": ,"body":  ,"set:"},
        ]
    },
    {
      "day": "화요일",
      "health": [
    
        {"exname": , "musclesub": , "cal": , "time": ,"body":  ,"set:"},
        {"exname": , "musclesub": , "cal": , "time": ,"body":  ,"set:"},
        {"exname": , "musclesub": , "cal": , "time": ,"body":  ,"set:"}
      ]
    },
    {
      "day": "수요일",
      "health": [
        
        {"exname": , "musclesub": , "cal": , "time": ,"body":  ,"set:"},
        {"exname": , "musclesub": , "cal": , "time": ,"body":  ,"set:"},
        {"exname": , "musclesub": , "cal": , "time": ,"body":  ,"set:"}
      ]
    },
    {
      "day": "목요일",
      "health": [
       
        {"exname": , "musclesub": , "cal": , "time": ,"body":  ,"set:"},
        {"exname": , "musclesub": , "cal": , "time": ,"body":  ,"set:"},
        {"exname": , "musclesub": , "cal": , "time": ,"body":  ,"set:"}
      ]
    },
    {
      "day": "금요일",
      "health": [
    
        {"exname": , "musclesub": , "cal": , "time": ,"body":  ,"set:"},
        {"exname": , "musclesub": , "cal": , "time": ,"body":  ,"set:"},
        {"exname": , "musclesub": , "cal": , "time": ,"body":  ,"set:"}
      ]
    },
    {
      "day": "토요일",
      "health": [
    
        {"exname": , "musclesub": , "cal": , "time": ,"body":  ,"set:"},
        {"exname": , "musclesub": , "cal": , "time": ,"body":  ,"set:"},
        {"exname": , "musclesub": , "cal": , "time": ,"body":  ,"set:"}
      ]
    },
    {
      "day": "일요일",
      "health": [
     
        {"exname": , "musclesub": , "cal": , "time": ,"body":  ,"set:"},
        {"exname": , "musclesub": , "cal": , "time": ,"body":  ,"set:"},
        {"exname": , "musclesub": , "cal": , "time": ,"body":  ,"set:"}
    }
  ]
}}



[운동 표]

EXDATANO 운동근육그룹 운동명 주동근 협응근 난이도 소모칼로리 근육활성도 HIGHMET LOWACT MIDACT HIGHACT LOWRISK MIDRISK HIGHRISK

'''



prompt = '''
    아래의 [회원 건강정보]을 가진 회원이 아래의 [운동 목표]를 목표로 해서 [3]달간 운동중이야, [식단 목록]에서 운동 목표를 달성하기 위해 필요한 하루의 식단을 식단의 섭취량(g)을 포함해서 출력해줘 식품 코드 또한 출력
    출력 형식은 JSON으로 [출력형식1]과 같은 형식으로 출력해줘

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
    
   
    만두	302020200
 
  
    
    [출력 형식1]
    {
    "res": [
        {
            "day": 1,
            "meals": [
                {
                    "meal": "breakfast",
                    "items": [
                        {"food": 식품명, "code": 식품코드, "amount_g": 섭취량(g) },
                        {"food": 식품명, "code": 식품코드, "amount_g": 섭취량(g) },
                        {"food": 식품명, "code": 식품코드, "amount_g": 섭취량(g) }
                    ]
                },
                {
                    "meal": "lunch",
                    "items": [
                        {"food": 식품명, "code": 식품코드, "amount_g": 섭취량(g) },
                        {"food": 식품명, "code": 식품코드, "amount_g": 섭취량(g) },
                        {"food": 식품명, "code": 식품코드, "amount_g": 섭취량(g) }
                    ]
                },
                {
                    "meal": "dinner",
                    "items": [
                        {"food": 식품명, "code": 식품코드, "amount_g": 섭취량(g) },
                        {"food": 식품명, "code": 식품코드, "amount_g": 섭취량(g) },
                        {"food": 식품명, "code": 식품코드, "amount_g": 섭취량(g) }
                    ]
                }
            ]
        },
        {
            "day": 2,
            "meals": [
                {
                    "meal": "breakfast",
                    "items": [
                        {"food": 식품명, "code": 식품코드, "amount_g": 섭취량(g) },
                        {"food": 식품명, "code": 식품코드, "amount_g": 섭취량(g) },
                        {"food": 식품명, "code": 식품코드, "amount_g": 섭취량(g) }
                    ]
                },
                {
                    "meal": "lunch",
                    "items": [
                        {"food": 식품명, "code": 식품코드, "amount_g": 섭취량(g) },
                        {"food": 식품명, "code": 식품코드, "amount_g": 섭취량(g) },
                        {"food": 식품명, "code": 식품코드, "amount_g": 섭취량(g) }
                    ]
                },
                {
                    "meal": "dinner",
                    "items": [
                        {"food": 식품명, "code": 식품코드, "amount_g": 섭취량(g) },
                        {"food": 식품명, "code": 식품코드, "amount_g": 섭취량(g) },
                        {"food": 식품명, "code": 식품코드, "amount_g": 섭취량(g) }
                    ]
                }
            ]
        },
        {
            "day": 3,
            "meals": [
                {
                    "meal": "breakfast",
                    "items": [
                        {"food": 식품명, "code": 식품코드, "amount_g": 섭취량(g) },
                        {"food": 식품명, "code": 식품코드, "amount_g": 섭취량(g) },
                        {"food": 식품명, "code": 식품코드, "amount_g": 섭취량(g) }
                    ]
                },
                {
                    "meal": "lunch",
                    "items": [
                        {"food": 식품명, "code": 식품코드, "amount_g": 섭취량(g) },
                        {"food": 식품명, "code": 식품코드, "amount_g": 섭취량(g) },
                        {"food": 식품명, "code": 식품코드, "amount_g": 섭취량(g) }
                    ]
                },
                {
                    "meal": "dinner",
                    "items": [
                        {"food": 식품명, "code": 식품코드, "amount_g": 섭취량(g) },
                        {"food": 식품명, "code": 식품코드, "amount_g": 섭취량(g) },
                        {"food": 식품명, "code": 식품코드, "amount_g": 섭취량(g) }
                    ]
                }
            ]
        }
    ]
}

    [출력 형식2]
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

def level_prompt2(prompt, level):
    if level == "상급":
        level_info = '''
        하루 운동 종류 수: 5-7가지 운동
        일주일 중 운동일 수: 7일중 5-6일 
        하루 권장 운동 시간: 90~120분 이상
        '''
    elif level == "중급":
        level_info = '''
        하루 운동 종류 수: 3-5가지 운동
        일주일 중 운동일 수: 7일중 4-5일
        하루 권장 운동 시간: 60~90분
        '''
    else :
        level_info = '''
        하루 운동 종류 수: 2-3가지 운동
        일주일 중 운동일 수: 7일중 3~4일
        하루 권장 운동 시간: 60분
        '''
    filled_prompt = prompt
    filled_prompt = prompt.replace("난이도:", f"난이도: {level_info}", 1)
    return filled_prompt
        

    
# http://localhost:5000/foodrecom/create.html
@app.route('/foodrecom/create/<int:goalsno>/<int:mhno>')
def foodrecom_create_form(goalsno, mhno):
    return render_template('foodrecom/create.html', goalsno=goalsno, mhno=mhno)

@app.route('/healthrecom/create/<int:goalsno>/<int:mhno>')
def healthrecom_create_form(goalsno, mhno):
    return render_template('healthrecom/create.html', goalsno=goalsno, mhno=mhno)

@app.route('/healthrecom/create', methods=['POST'])
def healthrecom_create_proc():
    level = request.form.get('level')
    goalsno = request.form.get('goalsno')
    mhno = request.form.get('mhno')
    
    
    
    #데이터 베이스에 연결
    conn = cx.connect("team1", "69017000", "3.39.75.85:1521/xe")
    cursor = conn.cursor()
    
    
    select_ex = '''
    SELECT *
    from exdata 
    '''
    
    cursor.execute(select_ex)
    rows = cursor.fetchall()

    # 쿼리 결과 문자열 생성
    result_str = '\n'.join([', '.join(map(str, row)) for row in rows])
    
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
    
    filled_prompt = populate_prompt(prompt3, mh[0], goals[0]) + result_str
    
    
    final_prompt = level_prompt2(filled_prompt,level) 
    
    print(final_prompt)
    response = tool.answer(role='헬스트레이너야', prompt=final_prompt, output='json', 
                        format='{"health": "값"}', llm='gpt-4o') 
    
    
    
    hrecom = str(response)
   
    
    insert_query = """
    INSERT INTO HEALTHRECOM (HEALTHRECOMNO, HRECOM, GOALSNO, MHNO, RDATE)
    VALUES (FOODRECOM_SEQ.nextval, :hrecom, :goalsno, :mhno, sysdate)
    """

    # 바인드 변수 사용하여 JSON 문자열 삽입
    cursor.execute(insert_query, {'hrecom': hrecom,'goalsno' : goalsno,'mhno' : mhno})

    # 커밋
    conn.commit()

    print("JSON 문자열이 데이터베이스에 저장되었습니다.")

    # 연결 종료
    cursor.close()
    conn.close()
    return response
    
    
    
    

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

#데이터 분석을 위한 코드
@app.route('/full_analysis/bargraph')
def bargraph_proc():
    
    if (platform.system() == 'Windows'):  # Windows
        rc('font', family=font_manager.FontProperties(fname="C:/Windows/Fonts/malgun.ttf").get_name())
        path = '.' # Local
    else:    
        plt.rc('font', family='NanumBarunGothic')  # Ubuntu 18.04 기준 한글 처리
        path = '/content/drive/My Drive/kd_ml/core' # Colab
  
    plt.rcParams['axes.unicode_minus'] = False  # minus 부호는 unicode 적용시 한글이 깨짐으로 설정
    #history 테이블의 값을 가져와 액셀로 생성한다
    conn = cx.connect("team1", "69017000", "3.39.75.85:1521/xe")
    cursor = conn.cursor()

    select_his = '''
    SELECT *
    from history 
    '''

    cursor.execute(select_his)
    rows = cursor.fetchall()    
        
    columns = ['EXRECORDNO', 'EXNAME', 'EXTYPE', 'HISCALORIE', 'DURATION', 'NOTES', 'RECORDDATE', 'STARTDATE', 'EXUPDATEDATE', 'MEMBERNO']
    df = pd.DataFrame(rows, columns=columns)

    file_path = 'exercise_records.xlsx'
    df.to_excel(file_path, index=False)

    file_path = './exercise_records.xlsx'

    # 엑셀 파일 읽기
    df = pd.read_excel(file_path)

    # EXTYPE의 분포 계산
    extype_distribution = df['EXTYPE'].value_counts().reset_index()
    extype_distribution.columns = ['EXTYPE', 'Count']


    
    # EXTYPE 분포 시각화
    plt.figure(figsize=(10, 6))
    sns.barplot(data=extype_distribution, x='EXTYPE', y='Count')
    plt.title('EXTYPE Distribution')
    plt.xlabel('EXTYPE')
    plt.ylabel('Count')
    plt.xticks(rotation=90)  # x축 레이블 회전
      
    img = io.BytesIO()
    plt.savefig(img, format='png')
    img.seek(0)
    plt.close()    
    
    return send_file(img, mimetype='image/png')

@app.route('/full_analysis/scatterplot')
def scatterplot_proc():
    
    
    #history 테이블의 값을 가져와 액셀로 생성한다
    conn = cx.connect("team1", "69017000", "3.39.75.85:1521/xe")
    cursor = conn.cursor()

    select_his = '''
    SELECT *
    from history 
    '''

    cursor.execute(select_his)
    rows = cursor.fetchall()    
        
    columns = ['EXRECORDNO', 'EXNAME', 'EXTYPE', 'HISCALORIE', 'DURATION', 'NOTES', 'RECORDDATE', 'STARTDATE', 'EXUPDATEDATE', 'MEMBERNO']
    df = pd.DataFrame(rows, columns=columns)

    file_path = 'exercise_records.xlsx'
    df.to_excel(file_path, index=False)

    file_path = './exercise_records.xlsx'

    # 엑셀 파일 읽기
    df = pd.read_excel(file_path)
    plt.rcParams["font.size"] = 12         # 글자 크기
    plt.rcParams["figure.figsize"] = (6, 3) # 10:4의 그래프 비율
    sns.scatterplot(data=df, x='HISCALORIE', y='DURATION')

    # EXTYPE의 분포 계산
    extype_distribution = df['EXTYPE'].value_counts().reset_index()
    extype_distribution.columns = ['EXTYPE', 'Count']



      
    img = io.BytesIO()
    plt.savefig(img, format='png')
    img.seek(0)
    plt.close()    
    
    return send_file(img, mimetype='image/png')


#참고
# def bar_chart():
#     # 데이터 생성
#     categories = ['A', 'B', 'C', 'D', 'E']
#     values = [10, 24, 36, 40, 52]

#     # 막대 그래프 생성
#     plt.figure(figsize=(10, 5))
#     plt.bar(categories, values)
#     plt.xlabel('Categories')
#     plt.ylabel('Values')
#     plt.title('Bar Chart Example')

#     # 이미지 버퍼에 저장
#     img = io.BytesIO()
#     plt.savefig(img, format='png')
#     img.seek(0)
#     plt.close()

#     # 이미지 반환
#     return send_file(img, mimetype='image/png')

if __name__ == '__main__':
    app.run(debug=True)




if __name__ == '__main__':
    app.run(host="0.0.0.0", port=5000, debug=True)  # 0.0.0.0: 모든 Host 에서 접속 가능, debug=True: 소스 변경시 자동 restart

'''
activate ai
python member_img.py
http://localhost:5000/member_img
'''

