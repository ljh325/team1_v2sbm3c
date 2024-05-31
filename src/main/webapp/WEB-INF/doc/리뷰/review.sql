/**********************************/
/* Table Name: 리뷰 */
/**********************************/

DROP TABLE REVIEW;
DROP TABLE REVIEW CASCADE CONSTRAINTS;

DROP SEQUENCE REVIEW_SEQ;

CREATE SEQUENCE REVIEW_SEQ
    START WITH 1         -- 시작 번호
    INCREMENT BY 1       -- 증가값
    MAXVALUE 9999999999  -- 최대값: 9999999999 --> NUMBER(10) 대응
    CACHE 2              -- 2번은 메모리에서만 계산
    NOCYCLE;            


CREATE TABLE REVIEW(
		REVIEWNO                      		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		STAR                          		NUMBER(5)		 NOT NULL,
		CONTENTS                      		VARCHAR2(1000)	 NOT NULL,
		RDATE                         		DATE		     NOT NULL,
        UDATE                         		DATE		     NULL,
		MEMBERNO                      		NUMBER(10)		 NOT NULL,
        FOODCATENO                          NUMBER(10)       NOT NULL,
  FOREIGN KEY (MEMBERNO) REFERENCES MEMBER (MEMBERNO),
  FOREIGN KEY (FOODCATENO) REFERENCES FOODCATE (FOODCATENO)
  -- 식단 왜래키 등록 
);

COMMENT ON TABLE REVIEW is '리뷰';
COMMENT ON COLUMN REVIEW.REVIEWNO is '리뷰번호';
COMMENT ON COLUMN REVIEW.STAR is '별점';
COMMENT ON COLUMN REVIEW.CONTENTS is '내용';
COMMENT ON COLUMN REVIEW.RDATE is '등록일';
COMMENT ON COLUMN REVIEW.UDATE is '등록일';
COMMENT ON COLUMN REVIEW.MEMBERNO is '회원 번호';
COMMENT ON COLUMN REVIEW.FOODCATENO is '식단 번호';

-- CREATE
INSERT INTO REVIEW(REVIEWNO, STAR, CONTENTS, RDATE, UDATE, MEMBERNO, FOODCATENO)
VALUES (REVIEW_seq.nextval, 5,'예시1',sysdate, null, 40, 3);

INSERT INTO REVIEW(REVIEWNO, STAR, CONTENTS, RDATE, UDATE, MEMBERNO, FOODCATENO)
VALUES (REVIEW_seq.nextval, 3,'예시2',sysdate, null, 40, 3);

INSERT INTO REVIEW(REVIEWNO, STAR, CONTENTS, RDATE, UDATE, MEMBERNO, FOODCATENO)
VALUES (REVIEW_seq.nextval, 2,'예시3',sysdate, null, 40, 3);


-- READ
SELECT r.REVIEWNO, r.STAR, r.CONTENTS, r.RDATE, r.UDATE, m.MEMBERNO, f.FOODCATENO
FROM MEMBER m, REVIEW r, FOODCATE f
WHERE m.memberno = r.memberno AND r.foodcateno = f.foodcateno;

-- 음식에 따른 전체 리뷰조회
SELECT r.REVIEWNO, r.STAR, r.CONTENTS, r.RDATE, r.UDATE, m.MEMBERNO, f.FOODCATENO
FROM MEMBER m, REVIEW r, FOODCATE f
WHERE m.memberno = r.memberno AND r.foodcateno = f.foodcateno 
AND f.foodcateno = 3;


commit;

-- 리뷰 페이징 목록
SELECT re_outer.reviewno, re_outer.star, re_outer.contents, re_outer.rdate, re_outer.udate, re_outer.memberno, re_outer.foodcateno, re_outer.r
FROM (
    SELECT re_inner.reviewno, re_inner.star, re_inner.contents, re_inner.rdate, re_inner.udate, re_inner.memberno, re_inner.foodcateno, rownum as r
    FROM (
        SELECT re.reviewno, re.star, re.contents, re.rdate, re.udate, m.memberno, f.foodcateno
        FROM MEMBER m, REVIEW re, FOODCATE f
        WHERE m.memberno = re.memberno AND re.foodcateno = f.foodcateno AND f.foodcateno = 3
        ORDER BY re.reviewno DESC
    ) re_inner
) re_outer
WHERE re_outer.r >= 1 AND re_outer.r <= 10;

-- 전체 리뷰 수
SELECT COUNT(*) as cnt
FROM review;

-- 리뷰 수정
UPDATE review
SET star=4, contents='야 너무 좋다',udate=sysdate
WHERE memberno = 40 AND reviewno = 1;

-- 리뷰 삭제
DELETE FROM review
WHERE memberno=40 and reviewno=3;


-- 별점 높은 순에 따른 리뷰 조회
SELECT r.reviewno, r.star, r.contents, r.rdate, r.udate, m.memberno, f.foodcateno
FROM member m, review r, foodcate f
WHERE m.memberno = r.memberno AND r.foodcateno = f.foodcateno AND f.foodcateno = 3
ORDER BY star DESC;
-- 별점 낮은 순에 따른 리뷰 조회
SELECT r.reviewno, r.star, r.contents, r.rdate, r.udate, m.memberno, f.foodcateno
FROM member m, review r, foodcate f
WHERE m.memberno = r.memberno AND r.foodcateno = f.foodcateno AND f.foodcateno = 3
ORDER BY star ASC;

-- 최근 작성순
SELECT r.reviewno, r.star, r.contents, r.rdate, r.udate, m.memberno, f.foodcateno
FROM member m, review r, foodcate f
WHERE m.memberno = r.memberno AND r.foodcateno = f.foodcateno AND f.foodcateno = 3
ORDER BY COALESCE(r.udate, r.rdate) DESC;
-- 오래된 작성순
SELECT r.reviewno, r.star, r.contents, r.rdate, r.udate, m.memberno, f.foodcateno
FROM member m, review r, foodcate f
WHERE m.memberno = r.memberno AND r.foodcateno = f.foodcateno AND f.foodcateno = 3
ORDER BY COALESCE(r.udate, r.rdate) ASC;






  
commit;
