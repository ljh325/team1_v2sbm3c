/**********************************/
/* Table Name: 회원 건강 정보 */
/**********************************/
select * 
from member

DROP TABLE MH;
DROP TABLE MH CASCADE CONSTRAINTS;

DROP SEQUENCE MH_SEQ;

CREATE SEQUENCE MH_SEQ
    START WITH 1         -- 시작 번호
    INCREMENT BY 1       -- 증가값
    MAXVALUE 9999999999  -- 최대값: 9999999999 --> NUMBER(10) 대응
    CACHE 2              -- 2번은 메모리에서만 계산
    NOCYCLE;            

CREATE TABLE MH(
		MHNO                          		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		KG                            		NUMBER(10)		 NOT NULL,
		CKG                           		NUMBER(10)		 NOT NULL,
		CM                            		NUMBER(10)		 NOT NULL,
		MUSCLE                        		NUMBER(10)		 NOT NULL,
		MEMBERNO                      		NUMBER(10)		 NULL ,
		INSERTDATE                    		DATE		 NOT NULL,
  FOREIGN KEY (MEMBERNO) REFERENCES MEMBER (MEMBERNO)
);



COMMENT ON TABLE MH is '회원 건강 정보';
COMMENT ON COLUMN MH.MHNO is '회원건강 정보 번호';
COMMENT ON COLUMN MH.KG is '체중';
COMMENT ON COLUMN MH.CKG is '체지방';
COMMENT ON COLUMN MH.CM is '신장';
COMMENT ON COLUMN MH.MUSCLE is '골격근량';
COMMENT ON COLUMN MH.MEMBERNO is '회원 번호';
COMMENT ON COLUMN MH.INSERTDATE is '등록일';

select * 
from member
-- CREATE
INSERT INTO MH(MHNO,KG,CKG,CM,MUSCLE,MEMBERNO,INSERTDATE)
VALUES (MH_seq.nextval, 90.5,18.5,180,40,37,sysdate);

INSERT INTO MH(MHNO,KG,CKG,CM,MUSCLE,MEMBERNO,INSERTDATE)
VALUES (MH_seq.nextval, 90.5,18.5,180,40,1,sysdate);

INSERT INTO MH(MHNO,KG,CKG,CM,MUSCLE,MEMBERNO,INSERTDATE)
VALUES (MH_seq.nextval, 90.5,18.5,180,40,1,sysdate);

INSERT INTO MH(MHNO,KG,CKG,CM,MUSCLE,MEMBERNO,INSERTDATE)
VALUES (1, 90.5,18.5,180,40,1,sysdate);

select * 
from member
-- READ
SELECT REVIEWNO, STAR, CONTENTS, RDATE, CNT, MEMBERNO, EATNO
FROM REIVIEW;

SELECT REVIEWNO, STAR, CONTENTS, RDATE, CNT, MEMBERNO, EATNO
FROM REIVIEW
WHERE STAR > 9.0;

-- UPDATE
UPDATE REIVIEW
SET STAR = 6.5
WHERE memberno = 1;

UPDATE REIVIEW
SET STAR = 7.0
WHERE memberno = 2;


-- DELETE
DELETE FROM REIVIEW
WHERE memberno = 4;

SELECT MHNO,KG,CKG,CM,MUSCLE,MEMBERNO,INSERTDATE
FROM MH;

-- UPDATE
UPDATE mh
SET muscle = 40
WHERE  MHNO = 1;

DELETE FROM mh
WHERE mhno = 3;

commit

---- READ
--SELECT REVIEWNO, STAR, CONTENTS, RDATE, CNT, MEMBERNO, EATNO
--FROM REIVIEW;
--
--SELECT REVIEWNO, STAR, CONTENTS, RDATE, CNT, MEMBERNO, EATNO
--FROM REIVIEW
--WHERE STAR > 9.0;
--
---- UPDATE
--UPDATE REIVIEW
--SET STAR = 6.5
--WHERE memberno = 1;
--
--UPDATE REIVIEW
--SET STAR = 7.0
--WHERE memberno = 2;
--
--
---- DELETE
--DELETE FROM REIVIEW
--WHERE memberno = 4;

  
commit;
  
commit;