/**********************************/
/* Table Name: 운동기록이미지 */
/**********************************/
DROP TABLE RECORDIMAGE;
DROP TABLE RECORDIMAGE CASCADE CONSTRAINTS;

CREATE TABLE RECORDIMAGE(
		RECIMAGENO                    		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		RECPROFILE                    		VARCHAR2(100)		 NULL ,
		RECPROFILESAVED               		VARCHAR2(100)		 NULL ,
		RECTHUMBS                     		VARCHAR2(100)		 NULL ,
		RECSIZES                      		NUMBER(10)		 NULL ,
		RECCONTENTS                   		VARCHAR2(1000)		 NULL ,
		RECVISIBLE                    		NUMBER(10)		 NULL ,
		RECDATE                       		DATE		 NOT NULL,
		RECUPDATE                     		DATE		 NULL ,
		EXRECORDNO                    		NUMBER(10)		 NULL ,
        MEMBERNO                            NUMBER(10)   NOT NULL,
  --FOREIGN KEY (EXRECORDNO) REFERENCES HISTORY (EXRECORDNO),
  FOREIGN KEY (MEMBERNO) REFERENCES MEMBER (MEMBERNO)
);

COMMENT ON TABLE RECORDIMAGE is '기록이미지';
COMMENT ON COLUMN RECORDIMAGE.RECIMAGENO is '이미지파일번호';
COMMENT ON COLUMN RECORDIMAGE.RECPROFILE is '메인이미지';
COMMENT ON COLUMN RECORDIMAGE.RECPROFILESAVED is '실제 저장된 이미지';
COMMENT ON COLUMN RECORDIMAGE.RECTHUMBS is '메인 이미지 Preview';
COMMENT ON COLUMN RECORDIMAGE.RECSIZES is '메인 이미지 크기';
COMMENT ON COLUMN RECORDIMAGE.RECCONTENTS is '글내용';
COMMENT ON COLUMN RECORDIMAGE.RECVISIBLE is '공개비공개';
COMMENT ON COLUMN RECORDIMAGE.RECDATE is '등록일자';
COMMENT ON COLUMN RECORDIMAGE.RECUPDATE is '수정일자';
COMMENT ON COLUMN RECORDIMAGE.EXRECORDNO is '운동기록번호';
COMMENT ON COLUMN RECORDIMAGE.MEMBERNO is '회원번호';


-- 시퀀스 삭제
DROP SEQUENCE recordImage_seq;

-- 시퀀스 생성
CREATE SEQUENCE recordImage_seq
  START WITH 1              -- 시작 번호
  INCREMENT BY 1          -- 증가값
  MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
  CACHE 2                       -- 2번은 메모리에서만 계산
  NOCYCLE;                     -- 다시 1부터 생성되는 것을 방지
  
SELECT * FROM RECORDIMAGE;
delete from RECORDIMAGE;
commit;

-- 운동 기록 이미지 등록
INSERT INTO recordimage(recimageno, recprofile, recprofilesaved, recthumbs, recsizes, reccontents, recvisible, recdate, exrecordno, memberno)
VALUES (recordImage_seq.nextval, 'pig.jpg', 'pig.jpg', 'pig.jpg', 90, '오운완', 0, sysdate, 1, 40);

INSERT INTO recordimage(recimageno, recprofile, recprofilesaved, recthumbs, recsizes, reccontents, recvisible, recdate, exrecordno, memberno)
VALUES (recordImage_seq.nextval, 'h1.jpg', 'h1.jpg', 'h1.jpg', 60, '완료', 2, sysdate, 1, 40);

INSERT INTO recordimage(recimageno, recprofile, recprofilesaved, recthumbs, recsizes, reccontents, recvisible, recdate, exrecordno, memberno)
VALUES (recordImage_seq.nextval, 'h21.jpg', 'h21.jpg', 'h21.jpg', 602, '완료', 12, sysdate, 2, 40);

-- 회원이 보는 이미지 리스트 조회
SELECT recimageno, recprofile, recprofilesaved, recthumbs, recsizes, reccontents, recvisible, recdate, exrecordno, memberno
FROM recordimage
WHERE memberno = 40;

-- 기록 별 이미지 조회
SELECT r.recimageno, r.recprofile, r.recprofilesaved, r.recthumbs, r.recsizes, r.reccontents, r.recvisible, r.recdate, h.exrecordno, r.memberno
FROM recordimage r , history h
WHERE h.exrecordno = 40 AND r.memberno = 5;

select * from recordimage;

-- 전체 이미지 수
SELECT recimageno, recprofile, recprofilesaved, recthumbs, recsizes, reccontents, recvisible, recdate, recupdate, exrecordno, memberno
FROM recordimage
ORDER BY   COALESCE(recupdate, recdate) DESC;

commit;
    SELECT r.recimageno, r.recprofile, r.recprofilesaved, r.recthumbs, r.recsizes, r.reccontents, r.recvisible, r.recdate, r.recupdate, r.exrecordno, r.memberno, 
    m.memberno, m.id, m.mname, m.nickname, m.tel, m.mdate, m.grade, m.point, m.birth, m.sex, m.profile, m.profilesaved, m.thumbs, m.sizes
    FROM recordimage r, member m
    WHERE r.memberno = m.memberno
    ORDER BY  COALESCE(r.recupdate, r.recdate) DESC;

SELECT r.recimageno, r.recprofile, r.recprofilesaved, r.recthumbs, r.recsizes, r.reccontents, r.recvisible, r.recdate, r.recupdate, r.exrecordno, r.memberno, 
       m.memberno, m.id, m.mname, m.nickname, m.tel, m.mdate, m.grade, m.point, m.birth, m.sex, m.profile, m.profilesaved, m.thumbs, m.sizes
FROM recordimage r
JOIN member m ON r.memberno = m.memberno
WHERE r.recimageno IN (
    SELECT MAX(recimageno)
    FROM recordimage
    GROUP BY exrecordno
)
ORDER BY COALESCE(r.recupdate, r.recdate) DESC;


select count(*) from recordimage;
-- 회원별 총 이미지 수

-- 운동기록번호 중복 제거한 총 수
SELECT COUNT(DISTINCT exrecordno) 
        FROM recordimage
        WHERE memberno = 5;


SELECT recimageno, recprofile, recprofilesaved, recthumbs, recsizes, reccontents, recvisible, recdate, exrecordno, memberno
FROM (
    SELECT recimageno, recprofile, recprofilesaved, recthumbs, recsizes, reccontents, recvisible, recdate, exrecordno, memberno,
           ROW_NUMBER() OVER (PARTITION BY exrecordno ORDER BY recimageno) AS rn
    FROM recordimage
    WHERE memberno = 5
)
WHERE rn = 1;

    SELECT recimageno, recprofile, recprofilesaved, recthumbs, recsizes, reccontents, recvisible, recdate, DISTINCT(exrecordno), memberno
    FROM recordimage
    WHERE memberno = 5
-- 운동 이미지 기록 수정
--UPDATE recordimage
--SET recprofile=#{recprofile}, recprofilesaved=#{recprofilesaved}, recthumbs=#{recthumbs}, recsizes=#{recsizes}
--reccontents=#{reccontents}, recvisible=#{recvisible}, recupdate=sysdate
--WHERE memberno = #{memberno};

-- 이미지 수정
UPDATE recordimage
SET recprofile='pi.jpg', recprofilesaved='pi.jpg', recthumbs='pi.jpg', recsizes=3500, 
reccontents='이것만 오운완 ', recvisible=0, recupdate=sysdate
WHERE memberno = 40;

-- 이미지 삭제
DELETE FROM recordimage
WHERE exrecordno = 1;


