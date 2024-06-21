CREATE TABLE fcalendar(
		FCALENDARNO                   		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		SDAY                          		NUMBER(10)		 NULL ,
		EDAY                          		NUMBER(10)		 NULL ,
		FOODRECOMNO                   		NUMBER(10)		 NULL ,
		CDATE                         		DATE		 NULL ,
        MEMBERNO                   		NUMBER(10)		 NULL ,
        FOREIGN KEY (MEMBERNO) REFERENCES MEMBER (MEMBERNONO),
  FOREIGN KEY (FOODRECOMNO) REFERENCES FOODRECOM (FOODRECOMNO)
);
drop table fcalendar;

COMMENT ON TABLE fcalendar is '식단달력';
COMMENT ON COLUMN fcalendar.FCALENDARNO is '식단달력번호';
COMMENT ON COLUMN fcalendar.SDAY is '시작 날짜';
COMMENT ON COLUMN fcalendar.EDAY is '끝 날짜';
COMMENT ON COLUMN fcalendar.FOODRECOMNO is '식단추천번호';
COMMENT ON COLUMN fcalendar.CDATE is '선택 날짜';


COMMENT ON TABLE fcalendar is '식단달력';
COMMENT ON COLUMN fcalendar.FCALENDARNO is '식단달력번호';
COMMENT ON COLUMN fcalendar.SDAY is '시작 날짜';
COMMENT ON COLUMN fcalendar.EDAY is '끝 날짜';
COMMENT ON COLUMN fcalendar.FOODRECOMNO is '식단추천번호';


CREATE SEQUENCE FCALENDAR_SEQ
    START WITH 1         -- 시작 번호
    INCREMENT BY 1       -- 증가값
    MAXVALUE 9999999999  -- 최대값: 9999999999 --> NUMBER(10) 대응
    CACHE 2              -- 2번은 메모리에서만 계산
    NOCYCLE;            
commit
select * from fcalendar
INSERT into fcalendar (fcalendarno,SDAY,EDAY,FOODRECOMNO,CDATE)
values (FCALENDAR_SEQ.nextval,1,1,21,sysdate);
INSERT into fcalendar (fcalendarno,SDAY,EDAY,FOODRECOMNO,CDATE)
values (FCALENDAR_SEQ.nextval,1,1,21,sysdate);
INSERT into fcalendar (fcalendarno,SDAY,EDAY,FOODRECOMNO,CDATE)
values (FCALENDAR_SEQ.nextval,1,1,21,sysdate);