/**********************************/
/* Table Name: 좋아요 */
/**********************************/

DROP TABLE LIKESYESNO;
DROP TABLE LIKESYESNO CASCADE CONSTRAINTS;

CREATE TABLE LIKESYESNO(
		LIKESNO                       		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		LIKESDATE                     		DATE		 NOT NULL,
		EXRECORDNO                    		NUMBER(10)		 NOT NULL,
		MEMBERNO                      		NUMBER(10)		 NOT NULL,
  FOREIGN KEY (EXRECORDNO) REFERENCES HISTORY (EXRECORDNO),
  FOREIGN KEY (MEMBERNO) REFERENCES MEMBER (MEMBERNO)
);

COMMENT ON TABLE LIKESYESNO is '좋아요';
COMMENT ON COLUMN LIKESYESNO.LIKESNO is '좋아요번호';
COMMENT ON COLUMN LIKESYESNO.LIKESDATE is '좋아요날짜';
COMMENT ON COLUMN LIKESYESNO.EXRECORDNO is '운동기록번호';
COMMENT ON COLUMN LIKESYESNO.MEMBERNO is '회원 번호';

-- 시퀀스 삭제
DROP SEQUENCE LIKESYESNO_seq;

-- 시퀀스 생성
CREATE SEQUENCE LIKESYESNO_seq
  START WITH 1              -- 시작 번호
  INCREMENT BY 1          -- 증가값
  MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
  CACHE 2                       -- 2번은 메모리에서만 계산
  NOCYCLE;                     -- 다시 1부터 생성되는 것을 방지

SELECT * FROM LIKESYESNO;
commit;

-- 좋아요 추가
INSERT INTO likesyesno(likesno, likesdate, exrecordno, memberno)
VALUES (likesyesno_seq.nextval, sysdate, 9, 5);

INSERT INTO likesyesno(likesno, likesdate, exrecordno, memberno)
VALUES (likesyesno_seq.nextval, sysdate, 9, 7);

INSERT INTO likesyesno(likesno, likesdate, exrecordno, memberno)
VALUES (likesyesno_seq.nextval, sysdate, 9, 11);

SELECT COUNT(*) FROM likesyesno
WHERE exrecordno = 9;

SELECT l.likesno, l.likesdate, l.exrecordno, l.memberno, 
       m.id, m.mname, m.nickname, m.mdate, m.grade, 
       m.point, m.birth, m.sex, m.profile, m.profilesaved, m.thumbs, m.sizes, m.introduce
FROM likesyesno l, member m
WHERE l.memberno = m.memberno AND exrecordno = 14;

DELETE FROM likesyesno
WHERE exrecordno = 9 AND memberno = 5;



SELECT COUNT(*) as cnt
FROM likesyesno
WHERE exrecordno = (
    SELECT exrecordno
    FROM (
        SELECT exrecordno
        FROM recordimage
        WHERE exrecordno = 9
    )
    WHERE ROWNUM = 1
);
commit;
        SELECT exrecordno
        FROM recordimage
        WHERE exrecordno = 9;
        
        
    SELECT keywordname, count(*) as cnt
    FROM keyword
    GROUP BY keywordname;









    
    
    