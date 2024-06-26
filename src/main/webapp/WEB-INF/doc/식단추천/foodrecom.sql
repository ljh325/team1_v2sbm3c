/**********************************/
/* Table Name: 식단추천 */
/**********************************/
DROP TABLE FOODRECOM;
DROP TABLE FOODRECOM CASCADE CONSTRAINTS;

select * from FOODRECOM

DROP SEQUENCE FOODRECOM_SEQ;

CREATE SEQUENCE FOODRECOM_SEQ
    START WITH 1         -- 시작 번호
    INCREMENT BY 1       -- 증가값
    MAXVALUE 9999999999  -- 최대값: 9999999999 --> NUMBER(10) 대응
    CACHE 2              -- 2번은 메모리에서만 계산
    NOCYCLE;            

 SELECT fr.foodrecomno,fr.frecom,fr.goalsno,fr.mhno,fr.rdate
    FROM foodrecom fr
    JOIN mh mh ON fr.mhno = mh.mhno
    JOIN member m ON mh.memberno = m.memberno
    WHERE m.memberno = 39
  

CREATE TABLE FOODRECOM(
		FOODRECOMNO                   		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		FRECOM                        		CLOB		 NOT NULL,
		GOALSNO                       		NUMBER(10)		 NOT NULL,
		MHNO                          		NUMBER(10)		 NOT NULL,
		RDATE                         		DATE		 NOT NULL,
  FOREIGN KEY (GOALSNO) REFERENCES GOALS (GOALSNO) ON DELETE CASCADE,
  FOREIGN KEY (MHNO) REFERENCES MH (MHNO) ON DELETE CASCADE
);

COMMENT ON TABLE FOODRECOM is '식단추천';
COMMENT ON COLUMN FOODRECOM.FOODRECOMNO is '식단추천번호';
COMMENT ON COLUMN FOODRECOM.FRECOM is '식단추천내용';
COMMENT ON COLUMN FOODRECOM.GOALSNO is '목표 번호';
COMMENT ON COLUMN FOODRECOM.MHNO is '회원건강 정보 번호';
COMMENT ON COLUMN FOODRECOM.RDATE is '날짜';

-- CREATE
INSERT INTO FOODRECOM (FOODRECOMNO, FRECOM, GOALSNO, MHNO, RDATE)
VALUES (FOODRECOM_SEQ.nextval, '식단', 1,1,sysdate);


-- 다음 시퀀스 값 사용


-- READ


SELECT FOODRECOMNO, CONTENTS, EATCATENO, GOALSNO, MHNO, RDATE
FROM FOODRECOM;
WHERE FOODRECOMNO > 2;

-- UPDATE
UPDATE FOODRECOM
SET FOODRECOMNO = 4
WHERE FOODRECOMNO = 3;

UPDATE FOODRECOM
SET FOODRECOMNO = 3
WHERE FOODRECOMNO = 1;

DELETE FROM FOODRECOM
WHERE FOODRECOMNO = 4;

 

DELETE FROM mh
WHERE mhno = 47;