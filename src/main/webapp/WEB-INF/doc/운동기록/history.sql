/**********************************/
/* Table Name: 운동기록 */
/**********************************/
 -- TOTALT    DATE           NULL,
DROP TABLE HISTORY;
DROP TABLE HISTORY CASCADE CONSTRAINTS;
CREATE TABLE HISTORY(
    HISTORYNO    NUMBER(10)     NOT NULL PRIMARY KEY,
    STARTT    DATE           NULL,
    ENDT     DATE           NULL,
    MEMBERNO  NUMBER(10)     NOT NULL,
    CONSTRAINT fk_member FOREIGN KEY (MEMBERNO) REFERENCES MEMBER(memberno)
);

COMMENT ON TABLE HISTORY is '운동기록';
COMMENT ON COLUMN HISTORY.HISTORYNO is '운동기록번호';
COMMENT ON COLUMN HISTORY.STARTT is '운동시작시간';
COMMENT ON COLUMN HISTORY.ENDT is '운동종료시간';
--COMMENT ON COLUMN HISTORY.TOTALT is '운동총시간';
COMMENT ON COLUMN HISTORY.MEMBERNO is '회원 번호';

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

-- 운동 시작 체크  
INSERT INTO history(historyno, startt, endt, memberno)
VALUES (history_seq.nextval, sysdate, null, 1);

-- 운동 종료 체크      
UPDATE history
SET endt = sysdate
WHERE memberno = 1 and history_seq.nextval;

-- 총시간 포함 
SELECT 
    historyno, 
    startt, 
    endt, 
    memberno,
    round((endt - startt) * 24 * 60, 0) AS totalt
FROM history
WHERE memberno = 40;

