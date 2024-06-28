/**********************************/
/* Table Name: 운동기록 */
/**********************************/
 -- TOTALT    DATE           NULL,
DROP TABLE HISTORY;
DROP TABLE HISTORY CASCADE CONSTRAINTS;

/**********************************/
/* Table Name: 운동기록 */
/**********************************/
CREATE TABLE HISTORY(
		EXRECORDNO                    		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		EXNAME                        		VARCHAR2(100)		 NOT NULL,
		EXTYPE                        		VARCHAR2(100)		 NOT NULL,
		HISCALORIE                    		NUMBER(10)		 NULL ,
		DURATION                      		NUMBER(10)		 NULL ,
		NOTES                         		VARCHAR2(1000)		 NULL ,
		STARTDATE                     		DATE		 NOT NULL,
        EXUPDATEDATE                        DATE             NULL,
		MEMBERNO                      		NUMBER(10)		 NOT NULL,
  FOREIGN KEY (MEMBERNO) REFERENCES MEMBER (MEMBERNO)
);

COMMENT ON TABLE HISTORY is '운동기록';
COMMENT ON COLUMN HISTORY.EXRECORDNO is '운동기록번호';
COMMENT ON COLUMN HISTORY.EXNAME is '운동명';
COMMENT ON COLUMN HISTORY.EXTYPE is '운동유형';
COMMENT ON COLUMN HISTORY.HISCALORIE is '소모칼로리';
COMMENT ON COLUMN HISTORY.DURATION is '운동시간';
COMMENT ON COLUMN HISTORY.NOTES is '메모';
COMMENT ON COLUMN HISTORY.STARTDATE is '등록날짜';
COMMENT ON COLUMN HISTORY.EXUPDATEDATE is '수정날짜';
COMMENT ON COLUMN HISTORY.MEMBERNO is '회원번호';


-- 시퀀스 삭제
DROP SEQUENCE history_seq;

-- 시퀀스 생성
CREATE SEQUENCE history_seq
  START WITH 1              -- 시작 번호
  INCREMENT BY 1          -- 증가값
  MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
  CACHE 2                       -- 2번은 메모리에서만 계산
  NOCYCLE;                     -- 다시 1부터 생성되는 것을 방지
  
SELECT * FROM history;
commit;

--		EXRECORDNO                    		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
--		EXNAME                        		VARCHAR2(100)		 NOT NULL,
--		EXTYPE                        		VARCHAR2(100)		 NOT NULL,
--		HISCALORIE                    		NUMBER(10)		 NULL ,
--		DURATION                      		VARCHAR2(100)		 NULL ,
--		NOTES                         		VARCHAR2(1000)		 NULL ,
--		STARTDATE                     		DATE		 NOT NULL,
--		MEMBERNO                      		NUMBER(10)		 NOT NULL,

-- 운동 기록 등록  
INSERT INTO history(exrecordno, exname, extype, hiscalorie, duration, notes, startdate, memberno)
VALUES (history_seq.nextval, '유산소', '런닝', 5, 60, '상체, 하체 완료', '2024-06-22 12:00:00', 40);

INSERT INTO history(exrecordno, exname, extype, hiscalorie, duration, notes, startdate, memberno)
VALUES (history_seq.nextval, '유산소', '런닝', 5, 60, '상체, 하체 완료', '2024-06-23 12:00:00', 40);

INSERT INTO history(exrecordno, exname, extype, hiscalorie, duration, notes, startdate, memberno)
VALUES (history_seq.nextval, '런닝', '런닝', 5, 60, '상체, 하체 완료', '2024-06-22 12:00:00', 40);


INSERT INTO history(exrecordno, exname, extype, hiscalorie, duration, notes, startdate, memberno)
VALUES (history_seq.nextval, '벤치프레스', '헬스', 10, 120, ' 하체 완료', sysdate, 39);

INSERT INTO history(exrecordno, exname, extype, hiscalorie, duration, notes, startdate, memberno)
VALUES (history_seq.nextval, '유산소', '산책', 5, 60, '걷기', sysdate, 40);

SELECT * FROM history;
commit;
-- 회원 운동 기록 전체 시간 
 SELECT SUM(DURATION)
FROM HISTORY
WHERE MEMBERNO = 39;  


--회원별 + 날짜별 총 운동 시간(time)
SELECT TRUNC(STARTDATE) AS start_date, SUM(DURATION) AS total_duration
FROM HISTORY
WHERE MEMBERNO = 39
GROUP BY TRUNC(STARTDATE)
ORDER BY TRUNC(STARTDATE);

-- 회원별 + 날짜별 총 운동 횟수(count)
SELECT TRUNC(STARTDATE) AS start_date,  COUNT(*) AS total_exercises
FROM HISTORY
WHERE MEMBERNO = 40
GROUP BY TRUNC(STARTDATE)
ORDER BY TRUNC(STARTDATE);


select* from history;
-- 회원별 운동기록 리스트조회
SELECT exrecordno, exname, extype, hiscalorie, duration, notes, startdate AS startdate, exupdatedate, memberno
FROM history
WHERE memberno = 40 AND TRUNC(startdate) = TO_DATE('2024-06-22', 'YYYY-MM-DD');

SELECT exrecordno, exname, extype, hiscalorie, duration, notes, startdate AS startdate, exupdatedate, memberno
FROM history
WHERE exrecordno = 1 AND memberno = 39;

-- 운동 기록 삭제
DELETE FROM history

-- 날짜별 운동 기록 삭제
DELETE FROM history
WHERE memberno = 39 AND TRUNC(startdate) = TO_DATE('2024-06-22', 'YYYY-MM-DD');



UPDATE history
SET exname = '축구', extype='기타 스포츠', hiscalorie= 33, duration=120, notes='아따 운동 끝났다', exupdatedate=sysdate
WHERE exrecordno = 1;
