/**********************************/
/* Table Name: �����̹��� */
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

COMMENT ON TABLE REVIEWIMAGE is '�����̹���';
COMMENT ON COLUMN REVIEWIMAGE.IMAGENO is '�̹������Ϲ�ȣ';
COMMENT ON COLUMN REVIEWIMAGE.PROFILE is '���� �̹���';
COMMENT ON COLUMN REVIEWIMAGE.PROFILESAVED is '���� ����� �̹���';
COMMENT ON COLUMN REVIEWIMAGE.THUMBS is '���� �̹��� Preview';
COMMENT ON COLUMN REVIEWIMAGE.SIZES is '���� �̹��� ũ��';
COMMENT ON COLUMN REVIEWIMAGE.REVIEWNO is '�����ȣ';



DROP SEQUENCE REVIEWIMAGE_SEQ;

CREATE SEQUENCE REVIEWIMAGE_SEQ
    START WITH 1         -- ?��?�� 번호
    INCREMENT BY 1       -- 증�?�?
    MAXVALUE 9999999999  -- 최�?�?: 9999999999 --> NUMBER(10) ???��
    CACHE 2              -- 2번�? 메모리에?���? 계산
    NOCYCLE;  
    
    
    
-- �̹��� ���� C
INSERT INTO reviewImage(imageno, profile, profilesaved, thumbs, sizes, reviewno)
VALUES (reviewImage_seq.nextval, 't1.jpg', 't1.jpg', 't1_t.jpg', '3232', 2);

INSERT INTO reviewImage(imageno, profile, profilesaved, thumbs, sizes, reviewno)
VALUES (reviewImage_seq.nextval, 't1.jpg', 't1.jpg', 't1_t.jpg', '3232', 2);

INSERT INTO reviewImage(imageno, profile, profilesaved, thumbs, sizes, reviewno)
VALUES (reviewImage_seq.nextval, 't1.jpg', 't1.jpg', 't1_t.jpg', '3232', 2);

-- ��ȸ
SELECT imageno, profile, profilesaved, thumbs, sizes, reviewno 
FROM reviewImage
WHERE reviewno = 2;

-- �̹��� ����
UPDATE member
SET profile=#{profile}, profilesaved=#{profilesaved}, thumbs=#{thumbs}, sizes=#{sizes}
WHERE memberno = #{memberno};


SELECT imageno, profile, profilesaved, thumbs, sizes, reviewno
FROM reviewImage
WHERE reviewno = 51;