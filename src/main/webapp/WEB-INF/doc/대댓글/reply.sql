/**********************************/
/* Table Name: 대댓글 */
/**********************************/
DROP TABLE reply;
DROP TABLE reply CASCADE CONSTRAINTS;

CREATE TABLE REPLY(
		REPLYNO                       		NUMBER(10)		 NOT NULL   PRIMARY KEY,
		CONTENTS                		    VARCHAR2(1000)	 NOT NULL,
		RDATE                   		    DATE		     NOT NULL,
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
INSERT INTO reply(replyno, contents, rdate, memberno, contentsno, commentsno)
VALUES (reply_seq.nextval, '테스트.', sysdate, 35, 32, 4);

-- READ

-- 전체 대댓글 조회
SELECT m.id, m.grade, m.thumbs, r.replyno, r.contents, r.rdate, r.memberno, r.contentsno, r.commentsno
FROM reply r, member m
WHERE m.memberno = r.memberno
ORDER BY replyno ASC;

-- 대댓글 정보 조회
SELECT m.id, m.grade, m.thumbs, r.replyno, r.contents, r.rdate, r.memberno, r.contentsno, r.commentsno
FROM reply r, member m
WHERE m.memberno = r.memberno AND r.replyno = 1;

-- 댓글에 등록된 대댓글 목록 조회
SELECT m.id, m.grade, m.thumbs, r.replyno, r.contents, r.rdate, r.memberno, r.contentsno, r.commentsno
FROM reply r, member m
WHERE m.memberno = r.memberno AND commentsno = 3
ORDER BY replyno ASC;

-- 회원이 등록한 대댓글 목록 조회
SELECT m.id, m.grade, m.thumbs, r.replyno, r.contents, r.rdate, r.memberno, r.contentsno, r.commentsno
FROM reply r, member m
WHERE m.memberno = r.memberno AND r.memberno = 35
ORDER BY replyno ASC;

-- 댓글에 등록된 대댓글 갯수 조회
SELECT COUNT(*)
FROM reply
WHERE commentsno = 3;

-- 회원이 등록한 대댓글 갯수 조회
SELECT COUNT(*)
FROM reply
WHERE memberno = 35;

-- UPDATE
UPDATE reply
SET contents = '대댓글 수정'
WHERE replyno = 21;
commit;

-- DELETE
DELETE FROM reply
WHERE replyno = 1;



