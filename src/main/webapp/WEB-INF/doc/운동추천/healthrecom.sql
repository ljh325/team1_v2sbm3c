/**********************************/
/* Table Name: 운동추천 */
/**********************************/


  SELECT fr.foodrecomno, fr.frecom, fr.goalsno, fr.mhno, fr.rdate
    FROM (
        SELECT fr.foodrecomno, fr.frecom, fr.goalsno, fr.mhno, fr.rdate, fr.rownum AS r
        FROM (
            SELECT fr.foodrecomno, fr.frecom, fr.goalsno, fr.mhno, fr.rdate
            FROM foodrecom fr
            JOIN mh mh ON fr.mhno = mh.mhno
            JOIN member m ON mh.memberno = m.memberno
            where
         
             UPPER(fr.frecom) LIKE '%' || UPPER('베이글') || '%' 
           
                
            AND m.memberno = 10
                
            </where>
            ORDER BY fr.foodrecomno DESC
        )
    )
    WHERE r &gt;= #{start_num} AND r &lt;= #{end_num}

DROP TABLE HEALTHRECOM;
DROP TABLE HEALTHRECOM CASCADE CONSTRAINTS;

select * from foodrecom
DROP SEQUENCE HEALTHRECOM_SEQ;

CREATE SEQUENCE HEALTHRECOM_SEQ
    START WITH 1         -- 시작 번호
    INCREMENT BY 1       -- 증가값
    MAXVALUE 9999999999  -- 최대값: 9999999999 --> NUMBER(10) 대응
    CACHE 2              -- 2번은 메모리에서만 계산
    NOCYCLE;            

CREATE TABLE HEALTHRECOM(
		HEALTHRECOMNO                 		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		HRECOM                         		CLOB		 NOT NULL,
		GOALSNO                       		NUMBER(10)		 NOT NULL,
		MHNO                          		NUMBER(10)		 NOT NULL,
		RDATE                         		DATE		 NOT NULL,
  FOREIGN KEY (GOALSNO) REFERENCES GOALS (GOALSNO) ON DELETE CASCADE,
  FOREIGN KEY (MHNO) REFERENCES MH (MHNO) ON DELETE CASCADE
);

COMMENT ON TABLE HEALTHRECOM is '운동추천';
COMMENT ON COLUMN HEALTHRECOM.HEALTHRECOMNO is '운동추천번호';
COMMENT ON COLUMN HEALTHRECOM.HRECOM is '운동추천내용';
COMMENT ON COLUMN HEALTHRECOM.GOALSNO is '목표 번호';
COMMENT ON COLUMN HEALTHRECOM.MHNO is '회원 건강진단번호';
COMMENT ON COLUMN HEALTHRECOM.RDATE is '날짜';

select * from healthrecom 
INSERT INTO HEALTHRECOM (HEALTHRECOMNO, HRECOM, GOALSNO, MHNO, RDATE)
VALUES (HEALTHRECOM_SEQ.nextval, '운동 추천 내용', 7, 38, sysdate);
commit

 
--
--INSERT INTO HEALTHRECOM (HEALTHRECOMNO, RECOM, HEALTHNO, GOALSNO, MHNO, RDATE)
--VALUES (HEALTHRECOM_SEQ.nextval, '운동 추천 내용2', 2, 2, 2, sysdate);
--
--INSERT INTO HEALTHRECOM (HEALTHRECOMNO, RECOM, HEALTHNO, GOALSNO, MHNO, RDATE)
--VALUES (HEALTHRECOM_SEQ.nextval, '운동 추천 내용3', 3, 3, 3, sysdate);
--
--INSERT INTO HEALTHRECOM (HEALTHRECOMNO, RECOM, HEALTHNO, GOALSNO, MHNO, RDATE)
--VALUES (HEALTHRECOM_SEQ.nextval, '운동 추천 내용4', 4, 4, 4, sysdate);
--
--SELECT HEALTHRECOMNO, RECOM, HEALTHNO, GOALSNO, MHNO, RDATE
--FROM HEALTHRECOM;

UPDATE HEALTHRECOM SET HRECOM = '수정된 운동 추천 내용' 
WHERE HEALTHRECOMNO = 1;

DELETE FROM HEALTHRECOM;

DELETE FROM HEALTHRECOM
WHERE HEALTHRECOMNO = 1;