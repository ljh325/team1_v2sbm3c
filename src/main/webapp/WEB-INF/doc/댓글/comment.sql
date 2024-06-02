/**********************************/
/* Table Name: 댓글 */
/**********************************/
DROP TABLE COMMENTS;
DROP TABLE COMMENTS CASCADE CONSTRAINTS;

CREATE TABLE COMMENTS(
		COMMENTSNO                     		NUMBER(10)		 NOT NULL   PRIMARY KEY,
		CONTENTS              		        VARCHAR2(1000)   NOT NULL,
		RDATE                 		        DATE		     NOT NULL,
        REPLYCNT                            NUMBER(10) DEFAULT 0 NULL,
		MEMBERNO                      		NUMBER(10)		 NOT NULL,
		CONTENTSNO                    		NUMBER(10)		 NOT NULL,
        FOREIGN KEY(MEMBERNO) REFERENCES MEMBER(MEMBERNO),
        FOREIGN KEY (CONTENTSNO) REFERENCES CONTENTS(CONTENTSNO)
);
commit;
COMMENT ON TABLE COMMENTS is '댓글';
COMMENT ON COLUMN COMMENTS.COMMENTSNO is '댓글번호';
COMMENT ON COLUMN COMMENTS.CONTENTS is '댓글내용';
COMMENT ON COLUMN COMMENTS.REPLYCNT is '답글수';
COMMENT ON COLUMN COMMENTS.RDATE is '등록일';
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
INSERT INTO comments(commentsno, contents, rdate, memberno, contentsno)
VALUES(comments_seq.nextval, '안녕하세요, 반갑습니다.',sysdate, 36, 30); 

-- READ

SELECT c.commentsno, c.contents, c.rdate, c.memberno, c.contentsno, c.replycnt, m.id, m.thumbs, m.grade
FROM comments c, member m
WHERE (c.memberno = m.memberno) AND contentsno = 30
ORDER BY commentsno ASC;

SELECT c.commentsno, c.contents, c.rdate, c.memberno, c.contentsno, m.id, m.thumbs, m.grade
FROM comments c, member m
WHERE (c.memberno = m.memberno) AND commentsno = 32;

SELECT COUNT(*)
FROM comments;

-- UPDATE
UPDATE comments
SET contents = '댓글 수정'
WHERE commentsno = 1;

-- DELETE
DELETE FROM comments
WHERE commentsno = 1;