/**********************************/
/* Table Name: ?? */
/**********************************/
CREATE TABLE MEMBER(
		MEMBERNO                      		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		ID                            		VARCHAR2(30)		 NOT NULL,
		PASSWD                        		VARCHAR2(200)		 NOT NULL,
		MNAME                         		VARCHAR2(30)		 NOT NULL,
		TEL                           		VARCHAR2(14)		 NOT NULL,
		ZIPCODE                       		VARCHAR2(5)		 NULL ,
		ADDRESS1                      		VARCHAR2(80)		 NULL ,
		ADDRESS2                      		VARCHAR2(50)		 NULL ,
		MDATE                         		DATE		 NOT NULL,
		GRADE                         		NUMBER(2)		 NOT NULL,
		POINT                         		NUMBER(10)		 NOT NULL,
        BIRTH                               NUMBER(10)		 NOT NULL,
  CONSTRAINT SYS_C008567 UNIQUE (ID)
);

commit;

COMMENT ON TABLE MEMBER is '??';
COMMENT ON COLUMN MEMBER.MEMBERNO is '?? λ²νΈ';
COMMENT ON COLUMN MEMBER.ID is '??΄?';
COMMENT ON COLUMN MEMBER.PASSWD is '?¨?€??';
COMMENT ON COLUMN MEMBER.MNAME is '?±λͺ?';
COMMENT ON COLUMN MEMBER.TEL is '? ?λ²νΈ';
COMMENT ON COLUMN MEMBER.ZIPCODE is '?°?Έλ²νΈ';
COMMENT ON COLUMN MEMBER.ADDRESS1 is 'μ£Όμ1';
COMMENT ON COLUMN MEMBER.ADDRESS2 is 'μ£Όμ2';
COMMENT ON COLUMN MEMBER.MDATE is 'κ°???Ό';
COMMENT ON COLUMN MEMBER.GRADE is '?±κΈ?';
COMMENT ON COLUMN MEMBER.PROFILE is '?? ?λ‘ν ?΄λ―Έμ?';
COMMENT ON COLUMN MEMBER.POINT is '?¬?Έ?Έ';



CREATE SEQUENCE member_seq
  START WITH 1              -- ?? λ²νΈ
  INCREMENT BY 1          -- μ¦κ?κ°?
  MAXVALUE 9999999999 -- μ΅λ?κ°?: 9999999 --> NUMBER(7) ???
  CACHE 2                       -- 2λ²μ? λ©λͺ¨λ¦¬μ?λ§? κ³μ°
  NOCYCLE;                     -- ?€? 1λΆ??° ??±?? κ²μ λ°©μ?
 