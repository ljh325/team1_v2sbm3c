/**********************************/
/* Table Name: 대댓글 */
/**********************************/
DROP TABLE reply;
DROP TABLE reply CASCADE CONSTRAINTS;

CREATE TABLE REPLY(
		REPLYNO                       		NUMBER(10)		 NOT NULL   PRIMARY KEY,
		REPLY_CONTENTS                		VARCHAR2(1000)		 NOT NULL,
		REPLY_RDATE                   		DATE		 NOT NULL,
		MEMBERNO                      		NUMBER(10)		 NOT NULL,
		CONTENTSNO                    		NUMBER(10)		 NOT NULL,
		COMMENTSNO                     		NUMBER(10)		 NULL,
        FOREIGN KEY(CONTENTSNO) REFERENCES contents(CONTENTSNO),
        FOREIGN KEY(MEMBERNO) REFERENCES member(MEMBERNO)
);

COMMENT ON TABLE REPLY is '대댓글';
COMMENT ON COLUMN REPLY.REPLYNO is '대댓글번호';
COMMENT ON COLUMN REPLY.REPLY_CONTENTS is '대댓글내용';
COMMENT ON COLUMN REPLY.REPLY_RDATE is '등록일';
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
INSERT INTO reply(replyno, reply_contents, reply_rdate, memberno, contentsno, commentsno)
VALUES (reply_seq.nextval, '안녕하세요, 반갑습니다.', sysdate, 2, 1, 1);

-- READ
SELECT replyno, reply_contents, reply_rdate, memberno, contentsno, commentsno
FROM reply
ORDER BY replyno ASC;

-- UPDATE
UPDATE reply
SET reply_contents = '대댓글 수정'
WHERE replyno = 1;

-- DELETE
DELETE FROM reply
WHERE replyno = 1;


