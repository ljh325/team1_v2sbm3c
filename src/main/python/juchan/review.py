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

