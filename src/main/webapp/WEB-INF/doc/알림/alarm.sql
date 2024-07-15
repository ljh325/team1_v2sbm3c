/**********************************/
/* Table Name: 알림 */
/**********************************/
DROP TABLE alarm;

CREATE TABLE ALARM(
		ALARMNO                       		NUMBER(10)		 NOT NULL   PRIMARY KEY,		
		MEMBERNO                      		NUMBER(10)		 NULL ,
		PATCHNO                       		NUMBER(10)		 NULL,
        FOREIGN KEY(memberno) REFERENCES member(memberno),
        FOREIGN KEY(patchno) REFERENCES patch(patchno)
);

COMMENT ON TABLE ALARM is '알림';
COMMENT ON COLUMN ALARM.ALARMNO is '알림번호';
COMMENT ON COLUMN ALARM.MEMBERNO is '회원 번호';
COMMENT ON COLUMN ALARM.PATCHNO is '변경사항번호';

DROP SEQUENCE alarm_seq;

CREATE SEQUENCE alarm_seq
  START WITH 1              -- 시작 번호
  INCREMENT BY 1          -- 증가값
  MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
  CACHE 2                       -- 2번은 메모리에서만 계산
  NOCYCLE;                     -- 다시 1부터 생성되는 것을 방지
  
-- CREATE

INSERT INTO alarm(alarmno, memberno, patchno)
VALUES(alarm_seq.nextval, 3, 67);

INSERT INTO alarm(alarmno, memberno, patchno)
VALUES(alarm_seq.nextval, 4, 67);

INSERT INTO alarm(alarmno, memberno, patchno)
VALUES(alarm_seq.nextval, 5, 67);

INSERT INTO alarm(alarmno, memberno, patchno)
VALUES(alarm_seq.nextval, 3, 68);

-- READ
SELECT alarmno, memberno, patchno
FROM alarm
WHERE memberno = 3;

SELECT alarmno, memberno, patchno
FROM alarm
WHERE alarmno = 3;

SELECT a.alarmno, a.memberno, a.patchno, p.title, p.rdate
FROM alarm a, patch p
WHERE a.patchno = p.patchno AND a.memberno = 3;

SELECT COUNT(alarmno) as cnt
FROM alarm
WHERE memberno = 3;

-- UPDATE


-- DELETE
DELETE FROM alarm
WHERE patchno = 68;


