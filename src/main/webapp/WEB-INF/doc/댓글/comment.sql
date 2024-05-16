/**********************************/
/* Table Name: 댓글 */
/**********************************/
DROP TABLE COMMENTS;
DROP TABLE COMMENTS CASCADE CONSTRAINTS;

CREATE TABLE COMMENTS(
		COMMENTSNO                     		NUMBER(10)		 NOT NULL   PRIMARY KEY,
		COMMENTS_CONTENTS              		VARCHAR2(1000)		 NOT NULL,
		COMMENTS_RDATE                 		DATE		 NOT NULL,
		MEMBERNO                      		NUMBER(10)		 NOT NULL,
		CONTENTSNO                    		NUMBER(10)		 NOT NULL,
        FOREIGN KEY(MEMBERNO) REFERENCES MEMBER(MEMBERNO),
        FOREIGN KEY (CONTENTSNO) REFERENCES CONTENTS(CONTENTSNO)
);

COMMENT ON TABLE COMMENTS is '댓글';
COMMENT ON COLUMN COMMENTS.COMMENTSNO is '댓글번호';
COMMENT ON COLUMN COMMENTS.COMMENTS_CONTENTS is '댓글내용';
COMMENT ON COLUMN COMMENTS.COMMENTS_RDATE is '등록일';
COMMENT ON COLUMN COMMENTS.MEMBERNO is '회원 번호';
COMMENT ON COLUMN COMMENTS.CONTENTSNO is '컨텐츠 번호';

DROP SEQUENCE comments_seq;

CREATE SEQUENCE comments_seq
  START WITH 1              -- 시작 번호
  INCREMENT BY 1          -- 증가값
  MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
  CACHE 2                       -- 2번은 메모리에서만 계산
  NOCYCLE;                     -- 다시 1부터 생성되는 것을 방지
  
-- CREATE
INSERT INTO comments(commentsno, comments_contents, comments_rdate, memberno, contentsno)
VALUES (comments_seq.nexval, '안녕하세요, 반갑습니다.', sysdate, 1, 1); 

-- READ
SELECT commentsno, commentsno, comments_contents, comments_rdate, memberno, contentsno
FROM contents;

-- UPDATE
UPDATE comments
SET comments_contents = '댓글 수정'
WHERE commentsno = 1;

-- DELETE
DELETE FROM comments
WHERE commentsno = 1;