/**********************************/
/* Table Name:  회원 로그인 내역 */
/**********************************/
CREATE TABLE MLOGIN(
		MLOGINNO                      		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		IP                            		VARCHAR2(16)		 NOT NULL,
		LOGINDATE                     		DATE		 NOT NULL,
		MEMBERNO                      		NUMBER(10)		 NULL,
		ADMINNO                       		NUMBER(10)		 NULL 
);

COMMENT ON TABLE MLOGIN is ' 회원 로그인 내역';
COMMENT ON COLUMN MLOGIN.MLOGINNO is '회원 로그인 번호';
COMMENT ON COLUMN MLOGIN.IP is '접속 ip';
COMMENT ON COLUMN MLOGIN.LOGINDATE is '로그인 날짜';
COMMENT ON COLUMN MLOGIN.MEMBERNO is '회원번호';
COMMENT ON COLUMN MLOGIN.ADMINNO is '관리자 번호';