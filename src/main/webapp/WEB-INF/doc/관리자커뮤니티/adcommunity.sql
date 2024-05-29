/**********************************/
/* Table Name: 관리자커뮤티니 */
/**********************************/
DROP TABLE ADCONTENTS;
DROP TABLE ADCONTENTS CASCADE CONSTRAINTS;

CREATE TABLE ADCONTENTS(
		ADCONTENTSNO                  		NUMBER(10)		 NOT NULL   PRIMARY KEY,
		CATENO                        		NUMBER(10)		 NOT NULL,
		TITLE                         		VARCHAR2(100)    NOT NULL,
		CONTENT                       		CLOB		     NOT NULL,
		RECOM                         		NUMBER(7)		 DEFAULT 0		 NOT NULL,
		VIEWCNT                       		NUMBER(7)		 DEFAULT 0		 NOT NULL,
		COMMENTCNT                    		NUMBER(7)		 DEFAULT 0		 NOT NULL,
		PASSWD                        		VARCHAR2(15)     NOT NULL,
		TAG                           		VARCHAR2(100)	     NULL ,
		RDATE                         		DATE		     NOT NULL,
        ID                                  VARCHAR2(30)     NOT NULL,
		FILE1                         		VARCHAR2(1000)		 NULL ,
		FILE1SAVED                    		VARCHAR2(1000)		 NULL ,
		THUMB1                        		VARCHAR2(1000)		 NULL ,
		SIZE1                         		NUMBER(10)		     NULL ,
		YOUTUBE                       		VARCHAR2(1000)		 NULL ,
		ADMINSNO                       		NUMBER(10)		     NULL ,
        FOREIGN KEY(CATENO) REFERENCES CATE(CATENO),
        FOREIGN KEY(ADMINSNO) REFERENCES ADMINS(ADMINSNO)
);

COMMENT ON TABLE ADCONTENTS is '관리자커뮤티니';
COMMENT ON COLUMN ADCONTENTS.ADCONTENTSNO is '컨텐츠 번호';
COMMENT ON COLUMN ADCONTENTS.CATENO is '카테고리 번호';
COMMENT ON COLUMN ADCONTENTS.TITLE is '제목';
COMMENT ON COLUMN ADCONTENTS.CONTENT is '내용';
COMMENT ON COLUMN ADCONTENTS.RECOM is '추천수';
COMMENT ON COLUMN ADCONTENTS.VIEWCNT is '조회수';
COMMENT ON COLUMN ADCONTENTS.COMMENTCNT is '댓글수';
COMMENT ON COLUMN ADCONTENTS.PASSWD is '패스워드';
COMMENT ON COLUMN ADCONTENTS.TAG is '태그';
COMMENT ON COLUMN ADCONTENTS.RDATE is '등록일';
COMMENT ON COLUMN ADCONTENTS.ID is '아이디';
COMMENT ON COLUMN ADCONTENTS.FILE1 is '메인 이미지';
COMMENT ON COLUMN ADCONTENTS.FILE1SAVED is '실제 저장된 메인 이미지';
COMMENT ON COLUMN ADCONTENTS.THUMB1 is '메인 이미지 Preview';
COMMENT ON COLUMN ADCONTENTS.SIZE1 is '메인 이미지 크기';
COMMENT ON COLUMN ADCONTENTS.YOUTUBE is 'YOUTUBE 영상';
COMMENT ON COLUMN ADCONTENTS.ADMINSNO is '관리자 번호';

DROP SEQUENCE adcontents_seq;


CREATE SEQUENCE adcontents_seq
  START WITH 1              -- 시작 번호
  INCREMENT BY 1          -- 증가값
  MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
  CACHE 2                       -- 2번은 메모리에서만 계산
  NOCYCLE;                     -- 다시 1부터 생성되는 것을 방지
  
-- CREATE
INSERT INTO adcontents(adcontentsno, adminsno, cateno, title, content, recom, viewcnt, commentcnt, passwd, tag, rdate, file1,
       file1saved, thumb1, size1, id)
VALUES (adcontents_seq.nextval, 1, 10, '식단', '다이어트 식단 추천', 0, 0, 0, '1234', '다이어트,식단', sysdate, 'food.jpg', 'food_1.jpg',
        'food_t.jpg', 1000, 'admin1');
        
        
-- READ
SELECT adcontentsno, adminsno, cateno, title, content, recom, viewcnt, commentcnt, passwd, tag, rdate, file1,
       file1saved, thumb1, size1, id
FROM adcontents
ORDER BY adcontentsno DESC;

SELECT adcontentsno, adminsno, cateno, title, content, recom, viewcnt, commentcnt, passwd, tag, rdate, file1,
       file1saved, thumb1, size1, youtube, id
FROM adcontents
WHERE adcontentsno = 1;

SELECT a.adcontentsno, a.title, ad.id 
FROM adcontents a, admins ad
WHERE a.adminsno = ad.adminsno;

-- UPDATE
UPDATE adcontents
SET title = '제목 수정'
WHERE adcontentsno = 1;

UPDATE adcontents
SET adcontent = '내용 수정'
WHERE adcontentsno = 1;

UPDATE adcontents
SET tag = '태그 수정'
WHERE adcontentsno = 1;

UPDATE adcontents
SET viewcnt = viewcnt + 1
WHERE adcontentsno = 12;

UPDATE adcontents
SET recom = recom + 1
WHERE adcontentsno = 12;

commit;

-- DELETE
DELETE FROM adcontents
WHERE adcontentsno = 4;

