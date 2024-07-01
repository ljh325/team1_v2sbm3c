/**********************************/
/* Table Name: 좋아요 */
/**********************************/

DROP TABLE LIKESYESNO;
DROP TABLE LIKESYESNO CASCADE CONSTRAINTS;

CREATE TABLE LIKESYESNO(
		LIKESNO                       		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		LIKESDATE                     		DATE		 NOT NULL,
		RECIMAGENO                    		NUMBER(10)		 NOT NULL,
		MEMBERNO                      		NUMBER(10)		 NOT NULL,
  FOREIGN KEY (RECIMAGENO) REFERENCES RECORDIMAGE (RECIMAGENO),
  FOREIGN KEY (MEMBERNO) REFERENCES MEMBER (MEMBERNO)
);

COMMENT ON TABLE LIKESYESNO is '좋아요';
COMMENT ON COLUMN LIKESYESNO.LIKESNO is '좋아요번호';
COMMENT ON COLUMN LIKESYESNO.LIKESDATE is '좋아요날짜';
COMMENT ON COLUMN LIKESYESNO.RECIMAGENO is '이미지파일번호';
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