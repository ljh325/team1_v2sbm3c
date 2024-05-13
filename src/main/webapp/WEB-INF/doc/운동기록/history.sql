/**********************************/
/* Table Name: 운동기록 */
/**********************************/
CREATE TABLE HISTORY(
		HISTORYNO                     		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		START_TIME                    		DATE		 	 NULL,
		LAST_TIME                     		DATE		 	 NULL,
		MEMBERNO                      		NUMBER(10)		 NOT NULL
);

COMMENT ON TABLE HISTORY is '운동기록';
COMMENT ON COLUMN HISTORY.HISTORYNO is '운동기록번호';
COMMENT ON COLUMN HISTORY.START_TIME is '운동시작시간';
COMMENT ON COLUMN HISTORY.LAST_TIME is '운동종료시간';
COMMENT ON COLUMN HISTORY.MEMBERNO is '회원 번호';