from flask import Flask, request, jsonify
from flask_cors import CORS
import tool
import cx_Oracle as cx



app = Flask(__name__)
CORS(app)  # Flask 애플리케이션에 CORS 설정 추가

###########################ORCALE DB 연결######################
# 데이터베이스 연결 정보
name = 'team1'
password = '69017000'
ip = '3.39.75.85:1521/XE'

@app.route('/emotion', methods=['POST'])
def receive_comments():
    # 클라이언트로부터 JSON 데이터 받기
    data = request.get_json()
    
    # 'comments' 키의 값 추출
    comments = data.get('comments', '')

    comments = tool.remove_empty_lines(comments) # 빈 라인 삭제

    # Chat GPT 프롬프트 작성
    prompt = f'''
    문장 「{comments}」을 분석하여 다음 작업을 수행하세요:
    1. 문장의 감성을 판단합니다:
    - 문장이 긍정적이면 "temperater: 2" 출력
    - 문장이 부정적이면 "temperater: 1" 출력
    2. 문장에서 특히 긍정적이거나 부정적으로 인식된 키워드를 파악합니다:
    - 문장에서 실제로 언급된 부분을  추출합니다.
    - 긍정적이거나 부정적인 특징이 여러 개 있는 경우, 한개만 작성하고
        최대한 문장형식으로 출력합니다.
    - 특징을 "핵심 특징: {{word}}" 형식으로 출력합니다.
    3. 파악된 핵심 특징을 다음 범주 중 하나로 분류하여 출력합니다:
    - 기능성 관련
    - 사용자 경험 관련
    - 건강 효과 관련
    - 서비스 관련
    - 범주를 "범주: {{keywordname}}" 형식으로 출력합니다.
    '''

    print(prompt)

    format = '''
    {
        "res": 2 또는 1,
        "keywords": [
            {"word": "example_word1", "keywordname": "example_keyword1"},
        ]
    }
    '''
    
    # 응답
    response = tool.answer('너는 긍정부정 감별사야', prompt, format)
    print(response)
    
    conn = cx.connect(name, password, ip)
    cursor = conn.cursor()
    
    # 감성 업데이트
    sql_update = '''
        UPDATE review 
        SET temperater = :temperater
        WHERE reviewno = (SELECT MAX(reviewno) FROM review)
    '''
    cursor.execute(sql_update, (response['res'],))

    # 키워드 삽입
    sql_insert = '''
        INSERT INTO keyword(keywordno, keywordname, word, reviewno)
        VALUES(keyword_seq.nextval, :keywordname, :word, (SELECT MAX(reviewno) FROM review))
    '''

    # 키워드가 여러 개일 경우 반복적으로 삽입
    for keyword in response['keywords']:
        cursor.execute(sql_insert, {'keywordname': keyword['keywordname'], 'word': keyword['word']})
    
    conn.commit()
    conn.close()

    import warnings
    warnings.filterwarnings(action='ignore')

    # 데이터 처리 및 시각화를 위한 라이브러리
    import pandas as pd
    import numpy as np
    import matplotlib.pyplot as plt
    import seaborn as sns
    from matplotlib import font_manager, rc
    import platform
    from IPython.core.interactiveshell import InteractiveShell

    # Jupyter notebook 설정
    InteractiveShell.ast_node_interactivity = "all"

    # 한글 폰트 설정
    if platform.system() == 'Windows':
        rc('font', family=font_manager.FontProperties(fname="C:/Windows/Fonts/malgun.ttf").get_name())
    else:
        plt.rc('font', family='NanumBarunGothic')

    # matplotlib 기본 설정
    plt.rcParams["font.size"] = 12
    plt.rcParams["figure.figsize"] = (6, 3)
    plt.rcParams['axes.unicode_minus'] = False

    # pandas 출력 옵션 설정
    pd.set_option('display.max_rows', 200)
    pd.set_option('display.max_columns', None)

    # matplotlib 그래프를 Jupyter notebook에 표시
    #%matplotlib inline

    # Oracle 연결
    import cx_Oracle
    from sqlalchemy import create_engine

    conn = cx_Oracle.connect('team1/69017000@3.39.75.85:1521/XE')
    cursor = conn.cursor()

    # 댓글 목록 조회
    sql = '''
    SELECT reviewno, contents
    FROM review
    ORDER BY reviewno ASC
    '''
    cursor.execute(sql)
    rows = cursor.fetchall()

    # 댓글 내용 리스트 생성
    total = [row[1] for row in rows]

    # 한글이 아닌 모든 문자 제거, 공백으로 변경
    import re
    data = re.sub('[^가-힣]', ' ', "".join(total))

    # 한글 형태소 분석기
    import konlpy
    from konlpy.tag import Kkma

    parser = Kkma()
    nouns = parser.nouns(data)

    # 불용어 제거
    STOPWORDS = ['나', '마세', '때', '절', '럼', '확대', '해결', '효율', '제', '새', '대', '내', '편', '네', '오늘', '고사', '언덕', '수', '앞', '거리', '저', '끝', '한번', '여러분', '대한민국', '국민', '우리', '상황', '대부분', '동안', '아래', '무엇', '채택', '때문', '추구', '창출', '그것', '위로', '요구', '이상', '진정한', '전환', '대통령', '나라']
    nouns = [noun for noun in nouns if noun not in STOPWORDS]

    # 데이터프레임 생성 및 단어 길이 계산
    df = pd.DataFrame({'word': nouns})
    df['len'] = df['word'].str.len()
    df = df.query('len >= 2').sort_values(by=['len'], ascending=True)

    # 단어 빈도 계산
    df2 = df.groupby(['word'], as_index=False).agg(n=('word', 'count')).sort_values(['n'], ascending=[False])

    # 상위 100개 단어 추출
    top100 = df2.reset_index(drop=True).head(100)

    # 시각화 준비
    top100 = top100.set_index('word')
    dict_df = top100.to_dict()['n']

    # 이미지 파일 이름에 사용할 번호 증가 로직
    import os

    def get_next_filename(directory, prefix='chart_', extension='.png'):
        files = os.listdir(directory)
        numbers = [int(f.replace(prefix, '').replace(extension, '')) for f in files if f.startswith(prefix) and f.endswith(extension)]
        if numbers:
            return f"{prefix}{max(numbers) + 1}{extension}"
        else:
            return f"{prefix}1{extension}"

    # 워드클라우드 생성 및 시각화
    from wordcloud import WordCloud

    if platform.system() == 'Windows':
        font_path = "C:/Windows/Fonts/malgun.ttf"
    else:
        font_path = "/Users/$USER/Library/Fonts/AppleGothic.ttf"

    wc = WordCloud(
        random_state=1234,
        font_path=font_path,
        width=800,
        height=400,
        background_color='white'
    )
    wc_img = wc.generate_from_frequencies(dict_df)

    plt.figure(figsize=(15, 10))
    plt.axis('off')
    plt.imshow(wc_img)

    # 저장할 디렉토리 및 파일 이름 설정
    save_directory = 'C:/kd/deploy/team1_v2sbm3c/wordcloud/storage'
    save_filename = get_next_filename(save_directory)

    # 이미지 저장
    file_path = os.path.join(save_directory, save_filename)
    plt.savefig(file_path)
    plt.show()

    # 테이블이 없으면 생성
    create_table_sql = '''
    CREATE TABLE chart (
        chartno NUMBER PRIMARY KEY,
        chartimages VARCHAR2(255)
    )
    '''
    try:
        cursor.execute(create_table_sql)
        conn.commit()
    except cx_Oracle.DatabaseError as e:
        error, = e.args
        if error.code == 955:  # ORA-00955: name is already used by an existing object
            print("Table already exists.")
        else:
            print(f"Database error: {e}")
            raise

    # 이미지 파일 이름을 DB에 삽입
    insert_sql = '''
    INSERT INTO chart (chartno, chartimages) VALUES (chart_seq.nextval, :chartimages)
    '''
    cursor.execute(insert_sql, {'chartimages': save_filename})
    conn.commit()

    # 연결 종료
    cursor.close()
    conn.close()
    return response

# @app.route('/emotion', methods=['POST'])
# def receive_comments():
      
#     # 클라이언트로부터 JSON 데이터 받기
#     data = request.get_json()
    
#     # 'comments' 키의 값 추출
#     comments = data.get('comments', '')

#     comments = tool.remove_empty_lines(comments) # 빈 라인 삭제
    
#     # Chat GPT 프롬프트 작성
#     prompt = f'「{comments}」 문장이 " 긍정"이면 1 , "부정"이면 0을 출력 and 핵심 키워드를 파악하여 특별히  긍정적 또는 부정적으로 인식된 특정 특징을 긍정 또는 부정문장과 함께 출력 and 해당 키워드를 (기능성 관련, 사용자 경험 관련, 건강 효과 관련, 서비스 관련)괄호 안에서 해당하는 것을 출력'

#     print(prompt)

#     format = '''
#     {
#     "res": 1 또는 0,
#     "word":  ,
#     "keywordname":
#     }
#     '''
    
#     # 응답
#     response = tool.answer('너는 긍정부정 감별사야', prompt, format)
#     # response = tool.answer('너는 번역기야', prompt, format, 'gpt-4-turbo')
#     # response = tool.answer('너는 번역기야', prompt, format, 'gpt-4o')
#     print(response)
    
#     conn = cx.connect(name, password, ip)
#     cursor = conn.cursor()
    
#     sql = '''
#         UPDATE review 
#         SET temperater = :temperater
#         WHERE reviewno = (SELECT MAX(reviewno) FROM review)
#     '''

#     sql3 = '''
#     SELECT * FROM EXDATA
#     '''
#     sql2 = '''
#         INSERT INTO keyword(keywordno, keywordname, word, reviewno)
#         VALUES(keyword_seq.nextval, :keywordname , :word,  (SELECT MAX(reviewno) FROM review))
#     '''

#     # SQL 문 실행, response['res']와 1을 각각 새로운 positive와 reviewno로 사용하고, 기존 reviewno가 1인 행을 업데이트
#     result = cursor.execute(sql, (response['res'],))   # (1,): Tuple로 인식
#     result2 = cursor.execute(sql2, (response['keywordname']  , response['word'] , ))
#     print("result---> : " , result)
#     print("result2---> : " , result2)
#     conn.commit()
#     conn.close()

#     return response





# 키워드  update
@app.route('/update_emotion', methods=['POST'])
def receive_comments2():
      
    # 클라이언트로부터 JSON 데이터 받기
    data = request.get_json()
    print(data)
    # 'comments' 키의 값 추출
    comments = data.get('comments', '')
    reviewno = data.get('reviewno','')
    print(reviewno)
    comments = tool.remove_empty_lines(comments) # 빈 라인 삭제
    
    # Chat GPT 프롬프트 작성
    prompt = f'「{comments}」 문장이 " 긍정"이면 2 , "부정"이면 1을 출력 and 핵심 키워드를 파악하여 특별히  긍정적 또는 부정적으로 인식된 특정 특징을 긍정 또는 부정문장과 함께 출력[단 긍정 또는 부정이 2개 이상들어갈 경우 하나만 줄력 할것] and 해당 키워드를 (기능성 관련, 사용자 경험 관련, 건강 효과 관련, 서비스 관련)괄호 안에서 해당하는 것을 출력'

    print(prompt)

    format = '''
    {
        "res": 2 또는 1,
        "word" :  ,
        "keywordname" :
    }
    '''
    
    # 응답
    response = tool.answer('너는 긍정부정 감별사야', prompt, format)
    # response = tool.answer('너는 번역기야', prompt, format, 'gpt-4-turbo')
    # response = tool.answer('너는 번역기야', prompt, format, 'gpt-4o')
    print(response)
    
    conn = cx.connect(name, password, ip)
    cursor = conn.cursor()
    
    sql = '''
        UPDATE review 
        SET temperater = :temperater
        WHERE reviewno = :reviewno
    '''

    sql2 = '''
        UPDATE keyword
        SET keywordname= :keywordname , word= :word
        WHERE reviewno= :reviewno
    '''

    # SQL 문 실행, response['res']와 1을 각각 새로운 positive와 reviewno로 사용하고, 기존 reviewno가 1인 행을 업데이트
    result = cursor.execute(sql, (response['res'], reviewno))   # (1,): Tuple로 인식
    result2 = cursor.execute(sql2, (response['keywordname']  , response['word'] , reviewno))
    print("result---> : " , result)
    print("result2---> : " , result2)
    conn.commit()
    conn.close()


    
    return response







app.run(host="0.0.0.0", port=5000, debug=True) # 0.0.0.0: 어디서나 접속, debug=True: 소스 변경시 자동 재시작
###########################   끝   ######################

