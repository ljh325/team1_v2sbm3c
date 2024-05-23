/**********************************/
/* Table Name: 대댓글 */
/**********************************/
DROP TABLE reply;
DROP TABLE reply CASCADE CONSTRAINTS;

CREATE TABLE REPLY(
		REPLYNO                       		NUMBER(10)		 NOT NULL   PRIMARY KEY,
		CONTENTS                		    VARCHAR2(1000)	 NOT NULL,
		RDATE                   		    DATE		     NOT NULL,
        ID                                  VARCHAR2(30)     NOT NULL,
		MEMBERNO                      		NUMBER(10)		 NOT NULL,
		CONTENTSNO                    		NUMBER(10)		 NOT NULL,
		COMMENTSNO                     		NUMBER(10)		 NOT NULL,
        FOREIGN KEY(CONTENTSNO) REFERENCES contents(CONTENTSNO),
        FOREIGN KEY(MEMBERNO)   REFERENCES member(MEMBERNO),
        FOREIGN KEY(COMMENTSNO) REFERENCES comments(COMMENTSNO)
);

COMMENT ON TABLE REPLY is '대댓글';
COMMENT ON COLUMN REPLY.REPLYNO is '대댓글번호';
COMMENT ON COLUMN REPLY.CONTENTS is '대댓글내용';
COMMENT ON COLUMN REPLY.RDATE is '등록일';
COMMENT ON COLUMN REPLY.ID is '작성자 ID';
COMMENT ON COLUMN REPLY.MEMBERNO is '회원 번호';
COMMENT ON COLUMN REPLY.CONTENTSNO is '컨텐츠 번호';
COMMENT ON COLUMN REPLY.COMMENTSNO is '댓글번호';

DROP SEQUENCE reply_seq;

CREATE SEQUENCE reply_seq
  START WITH 1              -- 시작 번호
  INCREMENT BY 1          -- 증가값
  MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
  CACHE 2                       -- 2번은 메모리에서만 계산
  NOCYCLE;                     -- 다시 1부터 생성되는 것을 방지
  
-- CREATE
INSERT INTO reply(replyno, contents, rdate, id, memberno, contentsno, commentsno)
VALUES (reply_seq.nextval, '안녕하세요, 반갑습니다.', sysdate, 'user1@gmail.com', 37, 30, 10);

-- READ
SELECT replyno, contents, rdate, id, memberno, contentsno, commentsno
FROM reply
ORDER BY replyno ASC;

SELECT replyno, contents, rdate, id, memberno, contentsno, commentsno
FROM reply
WHERE replyno = 2;


SELECT replyno, contents, rdate, id, memberno, contentsno, commentsno
FROM reply
WHERE commentsno = 10
ORDER BY replyno ASC;

SELECT replyno, contents, rdate, id, memberno, contentsno, commentsno
FROM reply
WHERE memberno = 37
ORDER BY replyno ASC;

SELECT COUNT(*)
FROM reply
WHERE commentsno = 10;

SELECT COUNT(*)
FROM reply
WHERE contentsno = 30;

SELECT COUNT(*)
FROM reply
WHERE memberno = 37;

-- UPDATE
UPDATE reply
SET contents = '대댓글 수정'
WHERE replyno = 21;
commit;

-- DELETE
DELETE FROM reply
WHERE replyno = 1;



