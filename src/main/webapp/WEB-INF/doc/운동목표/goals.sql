DROP TABLE goals;
DROP TABLE goals CASCADE CONSTRAINTS;

CREATE TABLE GOALS(
		GOALSNO                       		NUMBER(10)		 NOT NULL,
		MEMBERNO                      		NUMBER(10)		 NULL ,
		GDATE                         		DATE		     NOT NULL,
		KG                            		NUMBER(10)		 NOT NULL,
		CKG                           		NUMBER(10)		 NOT NULL,
        CM                                  NUMBER(3)        NOT NULL,
        FOREIGN KEY(memberno) REFERENCES member(memberno)
);

COMMENT ON TABLE GOALS is '운동 목표';
COMMENT ON COLUMN GOALS.GOALSNO is '목표 번호';
COMMENT ON COLUMN GOALS.MEMBERNO is '회원 번호';
COMMENT ON COLUMN GOALS.GDATE is '등록일';
COMMENT ON COLUMN GOALS.KG is '체중';
COMMENT ON COLUMN GOALS.CKG is '체지방';
COMMENT ON COLUMN GOALS.CM is '신장';

DROP SEQUENCE goals_seq;

CREATE SEQUENCE goals_seq
  START WITH 1              -- 시작 번호
  INCREMENT BY 1          -- 증가값
  MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
  CACHE 2                       -- 2번은 메모리에서만 계산
  NOCYCLE;                     -- 다시 1부터 생성되는 것을 방지

-- CREATE
INSERT INTO goals(goalsno, memberno, gdate, kg, ckg, cm)
VALUES (goals_seq.nextval, 1, sysdate, 70, 10, 175);

INSERT INTO goals(goalsno, memberno, gdate, kg, ckg, cm)
VALUES (goals_seq.nextval, 2, sysdate, 80, 15, 174);

INSERT INTO goals(goalsno, memberno, gdate, kg, ckg, cm)
VALUES (goals_seq.nextval, 3, sysdate, 48, 18, 160);

INSERT INTO goals(goalsno, memberno, gdate, kg, ckg, cm)
VALUES (goals_seq.nextval, 4, sysdate, 50, 15, 165);

-- READ
SELECT goalsno, memberno, gdate, kg, ckg, cm
FROM goals
WHERE memberno = 2;

SELECT goalsno, memberno, gdate, kg, ckg, cm
FROM goals
WHERE kg > 60;

-- UPDATE
UPDATE goals
SET kg = 65, ckg = 10, cm = 175
WHERE memberno = 1;

UPDATE goals
SET ckg = 7
WHERE memberno = 1;

UPDATE goals
SET cm = 175
WHERE memberno = 1;


-- DELETE
DELETE FROM goals
WHERE memberno = 4;

  
commit;