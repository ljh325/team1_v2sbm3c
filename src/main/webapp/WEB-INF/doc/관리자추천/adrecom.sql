/**********************************/
/* Table Name: 관리자추천 */
/**********************************/
DROP TABLE adrecom;
CREATE TABLE ADRECOM(
		ADRECOMNO                     		NUMBER(10)		 NOT NULL   PRIMARY KEY,
		ADRECOM                       		NUMBER(10)		 NULL ,
		ADCONTENTSNO                  		NUMBER(10)		 NULL ,
		MEMBERNO                      		NUMBER(10)		 NULL ,
        FOREIGN KEY (adcontentsno) REFERENCES adcontents (adcontentsno),
        FOREIGN KEY (memberno) REFERENCES member (memberno)
);

COMMENT ON TABLE ADRECOM is '관리자추천';
COMMENT ON COLUMN ADRECOM.ADRECOMNO is '좋아요번호';
COMMENT ON COLUMN ADRECOM.ADRECOM is '좋아요여부';
COMMENT ON COLUMN ADRECOM.ADCONTENTSNO is '컨텐츠 번호';
COMMENT ON COLUMN ADRECOM.MEMBERNO is '회원 번호';

DROP SEQUENCE adrecom_seq;

CREATE SEQUENCE adrecom_seq
  START WITH 1              -- 시작 번호
  INCREMENT BY 1          -- 증가값
  MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
  CACHE 2                       -- 2번은 메모리에서만 계산
  NOCYCLE;                     -- 다시 1부터 생성되는 것을 방지

-- CREATE
-- 최초 누르면 INSERT, 이후 추천 취소 시 추천여부 1에서 0, 0에서 다시 누를 시 0에서 1 반복, 최초 누를 때, 0에서 다시 누를 때 추천 + 1, 1에서 다시 누를 때 추천 - 1
INSERT INTO adrecom (adrecomno, adrecom, adcontentsno, memberno)
       VALUES (adrecom_seq.nextval, 1, 11, 61);
commit;
-- READ
SELECT adrecomno, adrecom, adcontentsno, memberno
FROM adrecom
WHERE memberno = 61;

SELECT adrecomno, adrecom, adcontentsno, memberno
FROM adrecom
WHERE adcontentsno = 11;

SELECT COUNT(memberno)
FROM adrecom
WHERE adcontentsno = 11 and memberno = 61;

SELECT adrecomno, adrecom, adcontentsno, memberno
FROM adrecom
WHERE adcontentsno = 11 and memberno = 61;

-- UPDATE
UPDATE adrecom
SET adrecom = 1
WHERE adcontentsno = 11 AND memberno = 61;

UPDATE adrecom
SET adrecom = 0
WHERE adcontentsno = 11 AND memberno = 61;

