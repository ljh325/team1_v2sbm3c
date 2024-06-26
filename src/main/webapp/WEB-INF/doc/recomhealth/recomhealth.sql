/**********************************/
/* Table Name: 추천 */
/**********************************/
DROP TABLE RECOMHT CASCADE CONSTRAINTS; -- 자식 무시하고 삭제 가능
CREATE TABLE RECOMHT(
		RECOMHTNO                        	NUMBER(10)		 NOT NULL   PRIMARY KEY,
		RECOM                        		NUMBER(10)		     NULL,
		HEALTHNO                    		NUMBER(10)		 NOT NULL,
		MEMBERNO                      		NUMBER(10)		 NOT NULL,
        FOREIGN KEY (HEALTHNO) REFERENCES HEALTH (HEALTHNO),
        FOREIGN KEY (memberno) REFERENCES member (memberno)
);

COMMENT ON TABLE RECOMHT is '추천';
COMMENT ON COLUMN RECOMHT.RECOMHTNO is '추천번호';
COMMENT ON COLUMN RECOMHT.RECOM is '추천여부';
COMMENT ON COLUMN RECOMHT.HEALTHNO is '컨텐츠 번호';
COMMENT ON COLUMN RECOMHT.MEMBERNO is '회원 번호';

DROP SEQUENCE RECOMHT_seq;

CREATE SEQUENCE RECOMHT_seq
  START WITH 1              -- 시작 번호
  INCREMENT BY 1          -- 증가값
  MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
  CACHE 2                       -- 2번은 메모리에서만 계산
  NOCYCLE;                     -- 다시 1부터 생성되는 것을 방지
  
-- CREATE
-- 최초 누르면 INSERT, 이후 추천 취소 시 추천여부 1에서 0, 0에서 다시 누를 시 0에서 1 반복, 최초 누를 때, 0에서 다시 누를 때 추천 + 1, 1에서 다시 누를 때 추천 - 1
INSERT INTO recom (recomno, recom, contentsno, memberno)
       VALUES (recom_seq.nextval, 1, 61, 61);
commit;
-- READ
SELECT recomno, recom, contentsno, memberno
FROM recom
WHERE memberno = 61;

SELECT recomno, recom, contentsno, memberno
FROM recom
WHERE contentsno = 69;

SELECT COUNT(memberno)
FROM recom
WHERE contentsno = 69 and memberno = 61;

SELECT recomno, contentsno, memberno, recom
FROM recom
WHERE contentsno = 61 and memberno = 61;

-- UPDATE
UPDATE recom
SET recom = 1
WHERE contentsno = 69 AND memberno = 61;

UPDATE recom
SET recom = 0
WHERE contentsno = 69 AND memberno = 61;





