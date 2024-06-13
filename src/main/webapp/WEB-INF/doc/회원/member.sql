/**********************************/
/* Table Name: ?��?�� */
/**********************************/
CREATE TABLE MEMBER(
		MEMBERNO                      		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		ID                            		VARCHAR2(30)		 NOT NULL,
		PASSWD                        		VARCHAR2(200)		 NOT NULL,
		MNAME                         		VARCHAR2(30)		 NOT NULL,
		TEL                           		VARCHAR2(14)		 NOT NULL,
		ADDRESS1                      		VARCHAR2(80)		 NULL ,
		ADDRESS2                      		VARCHAR2(50)		 NULL ,
		MDATE                         		DATE		 NOT NULL,
		GRADE                         		NUMBER(2)		 NOT NULL,
		POINT                         		NUMBER(10)		 NOT NULL,
        BIRTH                               NUMBER(10)		 NOT NULL,
        SEX                                 VARCHAR(100)     NULL,
        PROFILE                             VARCHAR(100)     NULL,
        PROFILESAVED                        VARCHAR(100)     NULL,
        THUMBS                              VARCHAR(100)     NULL,
        SIZES                               NUMBER(10)       NULL,
        NICKNAME                            VARCHAR(100)     NULL,
  CONSTRAINT SYS_C008567 UNIQUE (ID)
);

commit;

COMMENT ON TABLE MEMBER is '회원';
COMMENT ON COLUMN MEMBER.MEMBERNO is '회원 번호';
COMMENT ON COLUMN MEMBER.ID is '아이디';
COMMENT ON COLUMN MEMBER.PASSWD is '패스워드';
COMMENT ON COLUMN MEMBER.MNAME is '성명';
COMMENT ON COLUMN MEMBER.TEL is '전화번호';
COMMENT ON COLUMN MEMBER.ADDRESS1 is '주소1';
COMMENT ON COLUMN MEMBER.ADDRESS2 is '주소2';
COMMENT ON COLUMN MEMBER.MDATE is '가입일';
COMMENT ON COLUMN MEMBER.GRADE is '등급';
COMMENT ON COLUMN MEMBER.POINT is '포인트';
COMMENT ON COLUMN MEMBER.PROFILE is '메인 이미지';
COMMENT ON COLUMN MEMBER.PROFILESAVED is '실제 저장된 메인 이미지';
COMMENT ON COLUMN MEMBER.THUMBS is '메인 이미지 Priview';
COMMENT ON COLUMN MEMBER.SIZES is '메인 이미지 크기';
COMMENT ON COLUMN MEMBER.NICKNAME is '닉네임';


CREATE SEQUENCE member_seq
  START WITH 1              -- ?��?�� 번호
  INCREMENT BY 1          -- 증�?�?
  MAXVALUE 9999999999 -- 최�?�?: 9999999 --> NUMBER(7) ???��
  CACHE 2                       -- 2번�? 메모리에?���? 계산
  NOCYCLE;                     -- ?��?�� 1�??�� ?��?��?��?�� 것을 방�?
 commit;
 
 
 
 
                       <when test="word == null or word == ''"> <!-- 검색하지 않는 경우 -->
                        WHERE cateno=#{cateno}
                      </when>
 
 
-- 카테고리별 검색 + 페이징 목록
SELECT memberno, id, passwd, mname, nickname, tel, address1, address2, mdate, grade, profile, point, birth, sex, r
    FROM (
        SELECT memberno, id, passwd, mname, nickname, tel, address1, address2, mdate, grade, profile, point, birth, sex, rownum as r
            FROM (
                SELECT memberno, id, passwd, mname, nickname, tel, address1, address2, mdate, grade, profile, point, birth, sex
                FROM member
                <choose>
                <when test="word == null or word == ''"> -- 검색하지 않는 경우 --
                </when>
                <otherwise>
                    WHERE cateno=#{cateno} AND ( UPPER(title) LIKE '%' || UPPER('juch') || '%' 
                        OR UPPER(mname) LIKE '%' || UPPER('juch') || '%' 
                        OR UPPER(word) LIKE '%' || UPPER('juch') || '%')
                </otherwise>
            ORDER BY contentsno DESC
        )
    )
WHERE  r >= 1 AND r <= 10;
    
    
    <!-- 1 page: WHERE r >= 1 AND r <= 10; 
          2 page: WHERE r >= 11 AND r <= 20;
          3 page: WHERE r >= 21 AND r <= 30; -->
  </select>
 
 
 
 
 
 