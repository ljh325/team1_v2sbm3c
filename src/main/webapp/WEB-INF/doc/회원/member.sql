/**********************************/
/* Table Name: ?šŒ?› */
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

COMMENT ON TABLE MEMBER is '?šŒ?›';
COMMENT ON COLUMN MEMBER.MEMBERNO is '?šŒ?› ë²ˆí˜¸';
COMMENT ON COLUMN MEMBER.ID is '?•„?´?””';
COMMENT ON COLUMN MEMBER.PASSWD is '?Œ¨?Š¤?›Œ?“œ';
COMMENT ON COLUMN MEMBER.MNAME is '?„±ëª?';
COMMENT ON COLUMN MEMBER.TEL is '? „?™”ë²ˆí˜¸';
COMMENT ON COLUMN MEMBER.ZIPCODE is '?š°?Ž¸ë²ˆí˜¸';
COMMENT ON COLUMN MEMBER.ADDRESS1 is 'ì£¼ì†Œ1';
COMMENT ON COLUMN MEMBER.ADDRESS2 is 'ì£¼ì†Œ2';
COMMENT ON COLUMN MEMBER.MDATE is 'ê°??ž…?¼';
COMMENT ON COLUMN MEMBER.GRADE is '?“±ê¸?';
COMMENT ON COLUMN MEMBER.PROFILE is '?šŒ?› ?”„ë¡œí•„ ?´ë¯¸ì?';
COMMENT ON COLUMN MEMBER.POINT is '?¬?¸?Š¸';



CREATE SEQUENCE member_seq
  START WITH 1              -- ?‹œ?ž‘ ë²ˆí˜¸
  INCREMENT BY 1          -- ì¦ê?ê°?
  MAXVALUE 9999999999 -- ìµœë?ê°?: 9999999 --> NUMBER(7) ???‘
  CACHE 2                       -- 2ë²ˆì? ë©”ëª¨ë¦¬ì—?„œë§? ê³„ì‚°
  NOCYCLE;                     -- ?‹¤?‹œ 1ë¶??„° ?ƒ?„±?˜?Š” ê²ƒì„ ë°©ì?
 