DROP table cate;
DROP table cate CASCADE CONSTRAINTS;

CREATE TABLE CATE(
		CATENO                        		NUMBER(10)		NOT NULL   PRIMARY KEY,
		NAME                          		VARCHAR2(30)    NOT NULL,
		NAMESUB                       		VARCHAR2(30)    NOT NULL,
		CNT                           		NUMBER(7)		NOT NULL,
		RDATE                         		DATE		    NOT NULL,
		SEQNO                         		NUMBER(5)		NOT NULL,
		VISIBLE                       		CHAR(1)		    NOT NULL,
		ADMINSNO                       		NUMBER(10)		NULL,
        ADMINS                              CHAR(1)         NOT NULL,
        FOREIGN KEY(adminsno) REFERENCES admins(adminsno)
);

COMMENT ON TABLE CATE is '카테고리';
COMMENT ON COLUMN CATE.CATENO is '카테고리번호';
COMMENT ON COLUMN CATE.NAME is '중분류명';
COMMENT ON COLUMN CATE.NAMESUB is '소분류명';
COMMENT ON COLUMN CATE.CNT is '관련 자료수';
COMMENT ON COLUMN CATE.RDATE is '등록일';
COMMENT ON COLUMN CATE.SEQNO is '출력 순서';
COMMENT ON COLUMN CATE.VISIBLE is '출력 모드';
COMMENT ON COLUMN CATE.ADMINSNO is '관리자 번호';

DROP SEQUENCE cate_seq;

CREATE SEQUENCE cate_seq
  START WITH 1              -- 시작 번호
  INCREMENT BY 1          -- 증가값
  MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
  CACHE 2                       -- 2번은 메모리에서만 계산
  NOCYCLE;                     -- 다시 1부터 생성되는 것을 방지
  
-- CREATE
INSERT INTO cate(cateno, name, namesub, cnt, rdate, seqno, visible, adminsno, admins)
VALUES (cate_seq.nextval, '운동', '-', 0, sysdate, 1, 'Y', 5,'N');

INSERT INTO cate(cateno, name, namesub, cnt, rdate, seqno, visible, adminsno, admins)
VALUES (cate_seq.nextval, '식단', '-', 0, sysdate, 2, 'Y', 1);

INSERT INTO cate(cateno, name, namesub, cnt, rdate, seqno, visible, adminsno, admins)
VALUES (cate_seq.nextval, '운동', '운동 추천', 0, sysdate, 10, 'Y', 1);

INSERT INTO cate(cateno, name, namesub, cnt, rdate, seqno, visible, adminsno, admins)
VALUES (cate_seq.nextval, '식단', '식단 추천', 0, sysdate, 20, 'Y', 1);

-- READ
SELECT cateno, name, namesub, cnt, rdate, seqno, visible, adminsno, admins
FROM cate;

SELECT cateno, name, namesub, cnt, rdate, seqno, visible, adminsno, admins
FROM cate
WHERE name = '운동';

SELECT cateno, name, namesub, cnt, rdate, seqno, visible, adminsno, admins
FROM cate
WHERE name = '식단';

SELECT cateno, name, namesub, cnt, rdate, seqno, visible, adminsno, admins
FROM cate
ORDER BY seqno ASC;

SELECT SUM(cnt)
FROM cate
WHERE name = '테스트2';

-- UPDATE
UPDATE cate
SET namesub = '업데이트 테스트'
WHERE cateno = 3;

UPDATE cate
SET name = '업데이트 테스트 제목'
WHERE cateno = 3;

UPDATE cate
SET seqno = seqno - 1
WHERE cateno = 16 AND seqno > 0;

UPDATE cate
SET visible = 'N'
WHERE cateno = 3 AND visible = 'Y';

UPDATE cate
SET visible = 'Y'
WHERE cateno = 3 AND visible = 'N';

UPDATE cate
SET admins = 'Y'
WHERE cateno = 6 AND admins = 'N';

UPDATE cate
SET admins = 'N'
WHERE cateno = 6 AND admins = 'Y';

UPDATE cate
SET cnt = cnt + 1
WHERE cateno = 3;

UPDATE cate
SET cnt = cnt - 1
WHERE cateno = 3;

-- DELETE
DELETE FROM cate
WHERE cateno = 4;

COMMIT;



