DROP TABLE keyword;
DROP TABLE keyword CASCADE CONSTRAINTS;

/**********************************/
/* Table Name: 리뷰키워드 */
/**********************************/

CREATE TABLE chart (
    chartno     NUMBER(10)		 NOT NULL		 PRIMARY KEY,
    chartimages VARCHAR2(200)	 NOT NULL
);


DROP SEQUENCE CHART_SEQ;

CREATE SEQUENCE CHART_SEQ
    START WITH 1         -- 시작 번호
    INCREMENT BY 1       -- 증가값
    MAXVALUE 9999999999  -- 최대값: 9999999999 --> NUMBER(10) 대응
    CACHE 2              -- 2번은 메모리에서만 계산
    NOCYCLE;            

    SELECT k.keywordname, 
    r.reviewno, r.star, r.contents, r.temperater, r.rdate, r.udate, r.memberno,
    m.id, m.memberno, m.mname, m.nickname, m.profile, m.profilesaved, m.thumbs, m.sizes, m.grade
    FROM keyword k, review r, member m
    WHERE k.reviewno = r.reviewno AND r.memberno = m.memberno AND r.reviewno = 16;
    