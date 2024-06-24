
/**********************************/
/* Table Name: 기록이미지 */
/**********************************/
CREATE TABLE TABLE_26(
		COLUMN_1                      		INTEGER(10)		 NOT NULL		 PRIMARY KEY,
		COLUMN_2                      		INTEGER(10)		 NULL ,
		COLUMN_3                      		INTEGER(10)		 NULL ,
		COLUMN_4                      		INTEGER(10)		 NULL ,
		COLUMN_5                      		INTEGER(10)		 NULL ,
		RECCONTENTS                   		VARCHAR2(1000)		 NULL ,
		RECVISIBLE                    		NUMBER(10)		 NULL ,
		HISTORYNO                     		NUMBER(10)		 NULL ,
  FOREIGN KEY (HISTORYNO) REFERENCES HISTORY (HISTORYNO)
);

COMMENT ON TABLE TABLE_26 is '기록이미지';
COMMENT ON COLUMN TABLE_26.COLUMN_1 is '이미지파일번호';
COMMENT ON COLUMN TABLE_26.COLUMN_2 is '메인이미지';
COMMENT ON COLUMN TABLE_26.COLUMN_3 is '실제 저장된 이미지';
COMMENT ON COLUMN TABLE_26.COLUMN_4 is '메인 이미지 Preview';
COMMENT ON COLUMN TABLE_26.COLUMN_5 is '메인 이미지 크기';
COMMENT ON COLUMN TABLE_26.RECCONTENTS is '글내용';
COMMENT ON COLUMN TABLE_26.RECVISIBLE is '공개비공개';
COMMENT ON COLUMN TABLE_26.HISTORYNO is '운동기록번호';