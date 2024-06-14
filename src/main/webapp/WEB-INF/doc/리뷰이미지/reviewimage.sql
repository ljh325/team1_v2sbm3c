/**********************************/
/* Table Name: 리뷰이미지 */
/**********************************/



DROP TABLE REVIEWIMAGE;
DROP TABLE REVIEWIMAGE CASCADE CONSTRAINTS;



CREATE TABLE REVIEWIMAGE(
		IMAGENO                       		NUMBER(10)		 NOT NULL PRIMARY KEY,
		PROFILE                       		VARCHAR2(100)		 NULL ,
		PROFILESAVED                  		VARCHAR2(100)		 NULL ,
		THUMBS                        		VARCHAR2(100)		 NULL ,
		SIZES                         		NUMBER(10)		 NULL ,
		REVIEWNO                      		NUMBER(10)		 NOT NULL,
        FOREIGN KEY (REVIEWNO) REFERENCES REVIEW (REVIEWNO)
);

COMMENT ON TABLE REVIEWIMAGE is '리뷰이미지';
COMMENT ON COLUMN REVIEWIMAGE.IMAGENO is '이미지파일번호';
COMMENT ON COLUMN REVIEWIMAGE.PROFILE is '메인 이미지';
COMMENT ON COLUMN REVIEWIMAGE.PROFILESAVED is '실제 저장된 이미지';
COMMENT ON COLUMN REVIEWIMAGE.THUMBS is '메인 이미지 Preview';
COMMENT ON COLUMN REVIEWIMAGE.SIZES is '메인 이미지 크기';
COMMENT ON COLUMN REVIEWIMAGE.REVIEWNO is '리뷰번호';



DROP SEQUENCE REVIEWIMAGE_SEQ;

CREATE SEQUENCE REVIEWIMAGE_SEQ
    START WITH 1         -- ?떆?옉 踰덊샇
    INCREMENT BY 1       -- 利앷?媛?
    MAXVALUE 9999999999  -- 理쒕?媛?: 9999999999 --> NUMBER(10) ???쓳
    CACHE 2              -- 2踰덉? 硫붾え由ъ뿉?꽌留? 怨꾩궛
    NOCYCLE;  
    
    
    
-- 이미지 생성 C
INSERT INTO reviewImage(imageno, profile, profilesaved, thumbs, sizes, reviewno)
VALUES (reviewImage_seq.nextval, 't1.jpg', 't1.jpg', 't1_t.jpg', '3232', 2);

INSERT INTO reviewImage(imageno, profile, profilesaved, thumbs, sizes, reviewno)
VALUES (reviewImage_seq.nextval, 't1.jpg', 't1.jpg', 't1_t.jpg', '3232', 2);

INSERT INTO reviewImage(imageno, profile, profilesaved, thumbs, sizes, reviewno)
VALUES (reviewImage_seq.nextval, 't1.jpg', 't1.jpg', 't1_t.jpg', '3232', 2);

-- 조회
SELECT imageno, profile, profilesaved, thumbs, sizes, reviewno 
FROM reviewImage
WHERE reviewno = 2;

-- 이미지 수정
UPDATE member
SET profile=#{profile}, profilesaved=#{profilesaved}, thumbs=#{thumbs}, sizes=#{sizes}
WHERE memberno = #{memberno}