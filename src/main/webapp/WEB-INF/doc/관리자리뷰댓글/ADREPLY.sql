/**********************************/
/* Table Name: 관리자리뷰댓글 */
/**********************************/

DROP TABLE ADREPLY;
DROP TABLE ADREPLY CASCADE CONSTRAINTS;


CREATE TABLE ADREPLY(
		ADREPLYNO       NUMBER(10)		NOT NULL  PRIMARY KEY,
		ADCONTENTS      VARCHAR2(1000)	NOT NULL,
		ADDATE          DATE		    NOT NULL,
        ADUPDATE        DATE            NULL,
		REVIEWNO        NUMBER(10)		NOT NULL,
        ADMINSNO        NUMBER(10)		NOT NULL,
        FOREIGN KEY (REVIEWNO) REFERENCES REVIEW (REVIEWNO),
        FOREIGN KEY (ADMINSNO) REFERENCES ADMINS (ADMINSNO)
);

COMMENT ON TABLE ADREPLY is '관리자리뷰댓글';
COMMENT ON COLUMN ADREPLY.ADREPLYNO is '관리자리뷰댓글번호';
COMMENT ON COLUMN ADREPLY.ADCONTENTS is '댓글내용';
COMMENT ON COLUMN ADREPLY.ADDATE is '등록일';
COMMENT ON COLUMN ADREPLY.ADUPDATE is '수정일';
COMMENT ON COLUMN ADREPLY.REVIEWNO is '리뷰번호';
COMMENT ON COLUMN ADREPLY.ADMINSNO is '관리자 번호';


DROP SEQUENCE ADREPLY_SEQ;

CREATE SEQUENCE ADREPLY_SEQ
    START WITH 1         -- 시작 번호
    INCREMENT BY 1       -- 증가값
    MAXVALUE 9999999999  -- 최대값: 9999999999 --> NUMBER(10) 대응
    CACHE 2              -- 2번은 메모리에서만 계산
    NOCYCLE; 
    
-- 관리자 댓글 생성
INSERT INTO adreply(adreplyno, adcontents, addate, reviewno, adminsno)
VALUES (adreply_seq.nextval, '너무 좋아', sysdate, 5 , 1);

INSERT INTO adreply(adreplyno, adcontents, addate, reviewno, adminsno)
VALUES (adreply_seq.nextval, '너무 좋아요 유후', sysdate, 7, 1);

-- 관리자 댓글 조회
SELECT ad.adreplyno, ad.adcontents, ad.addate, ad.adupdate, ad.reviewno, ad.adminsno
FROM adreply ad, review r, admins a
WHERE ad.reviewno = r.reviewno AND ad.adminsno = a.adminsno AND ad.reviewno = 5;

-- 총 관리자 댓글 갯수
SELECT COUNT(*) 
FROM adreply;

-- 관리자 계정 별 댓글 갯수
SELECT COUNT(*) 
FROM adreply
WHERE adminsno = 1;


-- 관리자 댓글 수정
UPDATE adreply
SET adcontents = '싫어요', adupdate=sysdate
WHERE adminsno = 1 AND reviewno = 5;

-- 관리자 댓글 삭제    
DELETE FROM adreply
WHERE adminsno = 1 AND reviewno = 5;


DELETE FROM adreply;
commit;