
DROP TABLE keyword;
DROP TABLE keyword CASCADE CONSTRAINTS;

/**********************************/
/* Table Name: 리뷰키워드 */
/**********************************/
CREATE TABLE keyword(
		KEYWORDNO                     		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		KEYWORDNAME                   		VARCHAR2(100)	 NOT NULL,
		WORD                          		VARCHAR2(100)	 NOT NULL,
		REVIEWNO                      		NUMBER(10)		 NOT NULL,
  FOREIGN KEY (REVIEWNO) REFERENCES REVIEW (REVIEWNO)
);

DROP SEQUENCE KEYWORD_SEQ;

CREATE SEQUENCE KEYWORD_SEQ
    START WITH 1         -- 시작 번호
    INCREMENT BY 1       -- 증가값
    MAXVALUE 9999999999  -- 최대값: 9999999999 --> NUMBER(10) 대응
    CACHE 2              -- 2번은 메모리에서만 계산
    NOCYCLE;            




COMMENT ON TABLE keyword is '리뷰키워드';
COMMENT ON COLUMN keyword.KEYWORDNO is '키워드번호';
COMMENT ON COLUMN keyword.KEYWORDNAME is '키워드명';
COMMENT ON COLUMN keyword.WORD is '단어';
COMMENT ON COLUMN keyword.REVIEWNO is '리뷰번호';




1) 등록
INSERT INTO keyword(keywordno, keywordname, word, reviewno)
VALUES(keyword_seq.nextval, '기능성 관련', '기능이 너무 좋아', 1);
INSERT INTO keyword(keywordno, keywordname, word, reviewno)
VALUES(keyword_seq.nextval, '사용자 경험 관련', '기능이 너무 좋아', 1);
INSERT INTO keyword(keywordno, keywordname, word, reviewno)
VALUES(keyword_seq.nextval, '건강 효과 관련', '너무 싫어요', 56);             
INSERT INTO keyword(keywordno, keywordname,  word, reviewno)
VALUES(keyword_seq.nextval, '서비스 관련', '조ㅅ같네 이런..', 55);  

-- 전체 목록
SELECT keywordno, keywordname, temperater, word, reviewno
FROM keyword
ORDER BY keywordname;

-- 전체 목록
SELECT k.keywordno, k.keywordname, k.word, r.temperater, k.reviewno
FROM keyword k, REVIEW r
WHERE k.reviewno = r.reviewno
ORDER BY k.keywordname;


-- 키워드 수정
UPDATE keyword
SET star=4, contents='야 너무 좋다',udate=sysdate
WHERE memberno = 40 AND reviewno = 1;

-- 리뷰 삭제
DELETE FROM review
WHERE memberno=40 and reviewno=3;

