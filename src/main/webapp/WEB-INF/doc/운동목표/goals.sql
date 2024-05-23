DROP TABLE goals;
DROP TABLE goals CASCADE CONSTRAINTS;


SELECT hr.*
FROM healthrecom hr
JOIN mh mh ON hr.mhno = mh.mhno
JOIN member m ON mh.memberno = m.memberno
WHERE m.memberno = 1

drop table healthrecom

SELECT hr.*
FROM healthrecom hr
SELECT healthrecomno,hrecom, goalsno, mhno,rdate
    FROM healthrecom
    WHERE healthrecomno = 11


CREATE TABLE GOALS(
		GOALSNO                       		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		KG                            		NUMBER(10)		 NOT NULL,
		CKG                           		NUMBER(10)		 NOT NULL,
		CM                            		NUMBER(10)		 NULL ,
		MUSCLE                        		NUMBER(10)		 NULL ,
		MEMBERNO                      		NUMBER(10)		 NULL ,
		GDATE                         		DATE		 NOT NULL,
  FOREIGN KEY (MEMBERNO) REFERENCES MEMBER (MEMBERNO)
);

COMMENT ON TABLE GOALS is '운동 목표';
COMMENT ON COLUMN GOALS.GOALSNO is '목표 번호';
COMMENT ON COLUMN GOALS.KG is '체중';
COMMENT ON COLUMN GOALS.CKG is '체지방';
COMMENT ON COLUMN GOALS.CM is '신장';
COMMENT ON COLUMN GOALS.MUSCLE is '골격근량 ';
COMMENT ON COLUMN GOALS.MEMBERNO is '회원 번호';
COMMENT ON COLUMN GOALS.GDATE is '등록일';


DROP SEQUENCE goals_seq;

CREATE SEQUENCE goals_seq
  START WITH 1              -- 시작 번호
  INCREMENT BY 1          -- 증가값
  MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
  CACHE 2                       -- 2번은 메모리에서만 계산
  NOCYCLE;                     -- 다시 1부터 생성되는 것을 방지

-- CREATE
INSERT INTO goals(goalsno,kg, ckg, cm,muscle,memberno,gdate)
VALUES (goals_seq.nextval, 80, 20, 170, 10, 1,sysdate);


  

INSERT INTO goals(goalsno,kg, ckg, cm,muscle,memberno,gdate)
VALUES (goals_seq.nextval, 70, 20, 170, 10, 1,sysdate);

INSERT INTO goals(goalsno,kg, ckg, cm,muscle,memberno,gdate)
VALUES (goals_seq.nextval, 70, 20, 170, 10,37,sysdate);

commit

-- READ
SELECT goalsno,kg, ckg, cm,muscle,memberno,gdate
FROM goals
WHERE memberno = 2;

SELECT goalsno,kg, ckg, cm,muscle,memberno,gdate
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


  DELETE FROM goals
    WHERE goalsno = #{goalsno}

commit;