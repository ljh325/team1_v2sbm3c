
/**********************************/
/* Table Name: 등급 */
/**********************************/


DROP TABLE GRADE;
DROP TABLE GRADE CASCADE CONSTRAINTS;

CREATE TABLE GRADE(
		GRADENO                       		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		GNAME                         		VARCHAR2(10)		 NOT NULL,
        GUPDATE                             DATE,
		MEMBERNO                      		NUMBER(10)		 NULL ,
  FOREIGN KEY (MEMBERNO) REFERENCES MEMBER (MEMBERNO)
);

COMMENT ON TABLE GRADE is '등급';
COMMENT ON COLUMN GRADE.GRADENO is '등급 번호';
COMMENT ON COLUMN GRADE.GNAME is '등급명';
COMMENT ON COLUMN GRADE.GNAME is '등급 수정일';
COMMENT ON COLUMN GRADE.MEMBERNO is '회원 번호';


DROP SEQUENCE GRADE_SEQ;

CREATE SEQUENCE GRADE_SEQ
    START WITH 1         -- 시작 번호
    INCREMENT BY 1       -- 증가값
    MAXVALUE 9999999999  -- 최대값: 9999999999 --> NUMBER(10) 대응
    CACHE 2              -- 2번은 메모리에서만 계산
    NOCYCLE; 