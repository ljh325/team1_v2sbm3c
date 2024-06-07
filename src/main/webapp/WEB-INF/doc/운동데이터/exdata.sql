/**********************************/
/* Table Name: 운동 데이터 */
/**********************************/
DROP TABLE EXDATA;

CREATE TABLE EXDATA(
		EXDATANO                      		NUMBER(10)		 NOT NULL   PRIMARY KEY,
		EXGROUP                       		VARCHAR2(100)	 NOT NULL,
		EXNAME                        		VARCHAR2(100)		 NULL ,
		MUSCLE                        		VARCHAR2(100)		 NULL ,
		MUSCLESUB                     		VARCHAR2(100)		 NULL ,
		EXLEVEL                         	NUMBER(10)		 NOT NULL,
		LOWMET                        		NUMBER(10,1)		 NOT NULL,
		MIDMET                        		NUMBER(10,1)		 NOT NULL,
		HIGHMET                       		NUMBER(10,1)		 NOT NULL,
		LOWACT                        		NUMBER(10)		 NOT NULL,
		MIDACT                        		NUMBER(10)		 NOT NULL,
		HIGHACT                       		NUMBER(10)		 NOT NULL,
		LOWRISK                       		NUMBER(10)		 NOT NULL,
		MIDRISK                       		NUMBER(10)		 NOT NULL,
		HIGHRISK                      		NUMBER(10)		 NOT NULL
);

COMMENT ON TABLE EXDATA is '운동 데이터';
COMMENT ON COLUMN EXDATA.EXDATANO is '운동데이터번호';
COMMENT ON COLUMN EXDATA.EXGROUP is '운동근육그룹';
COMMENT ON COLUMN EXDATA.EXNAME is '운동명';
COMMENT ON COLUMN EXDATA.MUSCLE is '주동근';
COMMENT ON COLUMN EXDATA.MUSCLESUB is '협응근';
COMMENT ON COLUMN EXDATA.EXLEVEL is '난이도';
COMMENT ON COLUMN EXDATA.LOWMET is '낮은강도MET';
COMMENT ON COLUMN EXDATA.MIDMET is '중간강도MET';
COMMENT ON COLUMN EXDATA.HIGHMET is '높은강도MET';
COMMENT ON COLUMN EXDATA.LOWACT is '낮은강도근육활성도';
COMMENT ON COLUMN EXDATA.MIDACT is '중간강도근육활성도';
COMMENT ON COLUMN EXDATA.HIGHACT is '높은강도근육활성도';
COMMENT ON COLUMN EXDATA.LOWRISK is '낮은강도부상위험도';
COMMENT ON COLUMN EXDATA.MIDRISK is '중간강도부상위험도';
COMMENT ON COLUMN EXDATA.HIGHRISK is '높은강도부상위험도';









DROP SEQUENCE exdata_seq;

CREATE SEQUENCE exdata_seq
  START WITH 1              -- 시작 번호
  INCREMENT BY 1          -- 증가값
  MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
  CACHE 2                       -- 2번은 메모리에서만 계산
  NOCYCLE;                     -- 다시 1부터 생성되는 것을 방지
commit;
-- 운동 데이터 삽입
INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '가슴', '바벨 벤치프레스', '상부가슴', '삼두근, 전면어깨', 2, 3.5, 5.5, 7.5, 60, 85, 95, 30, 60, 80);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '가슴', '덤벨 벤치프레스', '상부가슴', '삼두근, 전면어깨', 2, 3.5, 5.5, 7.5, 60, 85, 95, 30, 60, 80);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '가슴', '바벨 인클라인 벤치프레스', '상부가슴', '삼두근, 전면어깨', 2, 3.5, 5.5, 7.5, 60, 85, 95, 30, 60, 80);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '가슴', '덤벨 인클라인 벤치프레스', '상부가슴', '삼두근, 전면어깨', 2, 3.5, 5.5, 7.5, 60, 85, 95, 30, 60, 80);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '가슴', '바벨 디클라인 벤치프레스', '하부가슴', '삼두근, 전면어깨', 2, 3.5, 5.5, 7.5, 60, 85, 95, 30, 60, 80);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel,lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '가슴', '덤벨 디클라인 벤치프레스', '하부가슴', '삼두근, 전면어깨', 2, 3.5, 5.5, 7.5, 60, 85, 95, 30, 60, 80);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub,exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '가슴', '머신 플라이', '전체가슴', '삼두근, 전면어깨', 1, 2.8, 4.5, 6.0, 50, 70, 85, 20, 40, 60);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '가슴', '케이블 플라이', '전체가슴', '삼두근, 전면어깨', 1, 2.8, 4.5, 6.0, 50, 70, 85, 20, 40, 60);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '가슴', '푸시업', '전체가슴', '삼두근, 전면어깨', 1, 2.8, 4.5, 6.0, 40, 50, 70, 10, 20, 40);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '가슴', '인클라인 푸시업', '상부가슴', '삼두근, 전면어깨', 1, 2.8, 4.5, 6.0, 40, 50, 70, 10, 20, 40);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '가슴', '디클라인 푸시업', '하부가슴', '삼두근, 전면어깨', 1, 2.8, 4.5, 6.0, 40, 50, 70, 10, 20, 40);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '등', '풀업', '상부등', '이두근, 후면어깨', 3, 4.0, 7.0, 9.0, 80, 95, 100, 40, 70, 90);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '등', '친업', '상부등', '이두근, 후면어깨', 3, 4.0, 7.0, 9.0, 80, 95, 100, 40, 70, 90);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '등', '프론트 랫풀다운', '상부등', '이두근, 후면어깨', 2, 2.5, 4.0, 5.5, 60, 75, 90, 20, 40, 60);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '등', '리버스 그립 랫풀다운', '상부등', '이두근, 후면어깨', 2, 2.5, 4.0, 5.5, 60, 75, 90, 20, 40, 60);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '등', '바벨 벤트 오버 로우', '중부등', '이두근, 후면어깨', 2, 3.5, 5.5, 7.5, 60, 85, 95, 30, 60, 80);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '등', 'T-바 로우', '중부등', '이두근, 후면어깨', 2, 3.5, 5.5, 7.5, 60, 85, 95, 30, 60, 80);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '등', '원암 덤벨 로우', '중부등', '이두근, 후면어깨', 2, 3.5, 5.5, 7.5, 60, 85, 95, 30, 60, 80);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '등', '시티드 덤벨 로우', '중부등', '이두근, 후면어깨', 2, 3.5, 5.5, 7.5, 60, 85, 95, 30, 60, 80);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '등', '시티드 케이블 로우', '중부등', '이두근, 후면어깨', 2, 3.5, 5.5, 7.5, 60, 85, 95, 20, 40, 60);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '등', '스트레이트 암풀다운', '하부등', '이두근, 후면어깨', 2, 2.8, 4.5, 6.0, 50, 70, 85, 20, 40, 60);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '어깨', '바벨 오버헤드 프레스', '전면어깨', '삼두근, 상부가슴', 2, 3.5, 5.5, 7.5, 60, 85, 95, 30, 60, 80);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '어깨', '덤벨 오버헤드 프레스', '전면어깨', '삼두근, 상부가슴', 2, 3.5, 5.5, 7.5, 60, 85, 95, 30, 60, 80);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '어깨', '덤벨 레터럴 레이즈', '측면어깨', '전면어깨', 1, 2.8, 4.5, 6.0, 50, 70, 85, 20, 40, 60);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '어깨', '케이블 레터럴 레이즈', '측면어깨', '전면어깨', 1, 2.8, 4.5, 6.0, 50, 70, 85, 20, 40, 60);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '어깨', '덤벨 프론트 레이즈', '전면어깨', '측면어깨', 1, 2.8, 4.5, 6.0, 50, 70, 85, 20, 40, 60);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '어깨', '케이블 프론트 레이즈', '전면어깨', '측면어깨', 1, 2.8, 4.5, 6.0, 50, 70, 85, 20, 40, 60);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '어깨', '덤벨 리버스 플라이', '후면어깨', '상부등', 1, 2.8, 4.5, 6.0, 50, 70, 85, 20, 40, 60);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '어깨', '머신 리버스 플라이', '후면어깨', '상부등', 1, 2.8, 4.5, 6.0, 50, 70, 85, 20, 40, 60);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '어깨', '바벨 슈러그', '승모근', '전면어깨', 2, 2.8, 4.5, 6.0, 50, 70, 85, 20, 40, 60);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '어깨', '덤벨 슈러그', '승모근', '전면어깨', 2, 2.8, 4.5, 6.0, 50, 70, 85, 20, 40, 60);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '팔', '바벨 컬', '이두근', '전완근', 1, 2.0, 3.0, 4.0, 50, 70, 85, 20, 40, 60);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '팔', 'EZ 바벨 컬', '이두근', '전완근', 1, 2.0, 3.0, 4.0, 50, 70, 85, 20, 40, 60);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '팔', '덤벨 해머 컬', '이두근', '전완근', 1, 2.0, 3.0, 4.0, 50, 70, 85, 20, 40, 60);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '팔', '덤벨 컨센트레이션 컬', '이두근', '전완근', 1, 2.0, 3.0, 4.0, 50, 70, 85, 20, 40, 60);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '팔', '머신 프리처 컬', '이두근', '전완근', 1, 2.0, 3.0, 4.0, 50, 70, 85, 20, 40, 60);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '팔', '바벨 프리처 컬', '이두근', '전완근', 1, 2.0, 3.0, 4.0, 50, 70, 85, 20, 40, 60);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '팔', '케이블 트라이셉스 푸시다운', '삼두근', '전완근', 1, 2.0, 3.0, 4.0, 50, 70, 85, 20, 40, 60);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '팔', '로프 트라이셉스 푸시다운', '삼두근', '전완근', 1, 2.0, 3.0, 4.0, 50, 70, 85, 20, 40, 60);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '팔', '오버헤드 트라이셉스 익스텐션', '삼두근', '전완근', 1, 2.0, 3.0, 4.0, 50, 70, 85, 20, 40, 60);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '팔', '라잉 트라이셉스 익스텐션', '삼두근', '전완근', 1, 2.0, 3.0, 4.0, 50, 70, 85, 20, 40, 60);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '팔', '벤치 딥스', '삼두근', '전완근, 하부가슴', 2, 3.0, 4.5, 6.0, 60, 75, 90, 30, 60, 80);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '팔', '바 딥스', '삼두근', '전완근, 하부가슴', 3, 3.0, 4.5, 6.0, 60, 75, 90, 30, 60, 80);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '하체', '바벨 백 스쿼트', '대퇴사두근', '둔근, 햄스트링', 2, 6.0, 8.5, 11.0, 70, 95, 100, 40, 80, 90);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '하체', '덤벨 스쿼트', '대퇴사두근', '둔근, 햄스트링', 2, 5.5, 7.0, 9.0, 65, 90, 100, 35, 70, 85);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '하체', '머신 레그 프레스', '대퇴사두근', '둔근, 햄스트링', 2, 5.5, 7.0, 9.0, 65, 90, 100, 20, 40, 60);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '하체', '머신 레그 익스텐션', '대퇴사두근', '', 1, 5.0, 6.5, 8.5, 60, 80, 95, 20, 40, 60);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '하체', '머신 레그 컬', '햄스트링', '', 1, 5.0, 6.5, 8.5, 60, 80, 95, 20, 40, 60);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '하체', '바벨 루마니안 데드리프트', '햄스트링', '둔근, 하부등', 2, 5.5, 7.0, 9.0, 65, 90, 100, 30, 60, 80);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '하체', '덤벨 루마니안 데드리프트', '햄스트링', '둔근, 하부등', 2, 5.5, 7.0, 9.0, 65, 90, 100, 30, 60, 80);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '하체', '스탠딩 카프레이즈', '종아리', '', 1, 2.5, 3.5, 4.5, 40, 50, 70, 10, 20, 40);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '하체', '시티드 카프레이즈', '종아리', '', 1, 2.5, 3.5, 4.5, 40, 50, 70, 10, 20, 40);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '복근', '바디웨이트 크런치', '복직근', '', 1, 2.0, 3.0, 4.0, 30, 50, 70, 10, 20, 40);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '복근', '머신 크런치', '복직근', '', 1, 2.5, 4.0, 5.0, 40, 70, 85, 20, 40, 60);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '복근', '행잉 레그 레이즈', '복직근', '측면복근', 2, 3.5, 6.0, 7.0, 60, 90, 100, 30, 60, 80);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '복근', '캡틴 체어 레그 레이즈', '복직근', '측면복근', 2, 3.0, 5.0, 6.0, 50, 80, 95, 20, 40, 60);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '복근', '프론트 플랭크', '복직근', '측면복근', 1, 2.0, 3.0, 4.0, 30, 50, 70, 10, 20, 40);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '복근', '사이드 플랭크', '측면복근', '복직근', 1, 2.0, 3.0, 4.0, 30, 50, 70, 10, 20, 40);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '복근', '바벨 앱 롤아웃', '복직근', '', 2, 2.5, 4.0, 5.0, 50, 85, 100, 30, 60, 80);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '복근', '앱 휠 롤아웃', '복직근', '', 2, 2.5, 4.0, 5.0, 50, 85, 100, 30, 60, 80);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '유산소', '트레드밀', '전신', '', 2, 8.0, 11.0, 14.0, 60, 85, 100, 20, 50, 80);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '유산소', '스피닝', '전신', '', 2, 5.5, 7.0, 9.0, 50, 75, 95, 15, 40, 70);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '유산소', '엘립티컬 트레이너', '전신', '', 1, 6.0, 7.5, 10.0, 50, 70, 90, 10, 30, 50);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '유산소', '스텝퍼', '전신', '', 2, 5.5, 7.0, 9.0, 50, 75, 95, 15, 35, 60);

INSERT INTO exdata(exdatano, exgroup, exname, muscle, musclesub, exlevel, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk)
VALUES (exdata_seq.nextval, '유산소', '로잉 머신', '전신', '', 2, 6.0, 7.5, 10.0, 60, 80, 95, 20, 40, 60);

commit;
-- 운동 데이터 조회
-- 전체 데이터
SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
ORDER BY exdatano ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
ORDER BY lowmet ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
ORDER BY lowmet DESC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
ORDER BY midmet ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
ORDER BY midmet DESC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
ORDER BY highmet ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
ORDER BY highmet DESC;

------------------------------
SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
ORDER BY lowact ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
ORDER BY lowact DESC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
ORDER BY midact ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
ORDER BY midact DESC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
ORDER BY highact ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
ORDER BY highact DESC;
-----------------------------------------------------
SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
ORDER BY lowrisk ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
ORDER BY lowrisk DESC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
ORDER BY midrisk ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
ORDER BY midrisk DESC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
ORDER BY highrisk ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
ORDER BY highrisk DESC;
--------------------------가슴 운동 데이터--------------------------------
SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '가슴'
ORDER BY exdatano ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '가슴'
ORDER BY lowmet ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '가슴'
ORDER BY lowmet DESC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '가슴'
ORDER BY midmet ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '가슴'
ORDER BY midmet DESC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '가슴'
ORDER BY highmet ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '가슴'
ORDER BY highmet DESC;

------------------------------
SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '가슴'
ORDER BY lowact ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '가슴'
ORDER BY lowact DESC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '가슴'
ORDER BY midact ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '가슴'
ORDER BY midact DESC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '가슴'
ORDER BY highact ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '가슴'
ORDER BY highact DESC;
-----------------------------------------------------
SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '가슴'
ORDER BY lowrisk ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '가슴'
ORDER BY lowrisk DESC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '가슴'
ORDER BY midrisk ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '가슴'
ORDER BY midrisk DESC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '가슴'
ORDER BY highrisk ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '가슴'
ORDER BY highrisk DESC;
------------------------등 운동 데이터-------------------------------------------
SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '등'
ORDER BY exdatano ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '등'
ORDER BY lowmet ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '등'
ORDER BY lowmet DESC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '등'
ORDER BY midmet ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '등'
ORDER BY midmet DESC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '등'
ORDER BY highmet ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '등'
ORDER BY highmet DESC;

------------------------------
SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '등'
ORDER BY lowact ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '등'
ORDER BY lowact DESC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '등'
ORDER BY midact ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '등'
ORDER BY midact DESC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '등'
ORDER BY highact ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '등'
ORDER BY highact DESC;
-----------------------------------------------------
SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '등'
ORDER BY lowrisk ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '등'
ORDER BY lowrisk DESC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '등'
ORDER BY midrisk ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '등'
ORDER BY midrisk DESC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '등'
ORDER BY highrisk ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '등'
ORDER BY highrisk DESC;

-----------------------------어깨운동 데이터------------------------------
SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '어깨'
ORDER BY exdatano ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '어깨'
ORDER BY lowmet ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '어깨'
ORDER BY lowmet DESC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '어깨'
ORDER BY midmet ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '어깨'
ORDER BY midmet DESC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '어깨'
ORDER BY highmet ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '어깨'
ORDER BY highmet DESC;

------------------------------
SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '어깨'
ORDER BY lowact ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '어깨'
ORDER BY lowact DESC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '어깨'
ORDER BY midact ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '어깨'
ORDER BY midact DESC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '어깨'
ORDER BY highact ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '어깨'
ORDER BY highact DESC;
-----------------------------------------------------
SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '어깨'
ORDER BY lowrisk ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '어깨'
ORDER BY lowrisk DESC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '어깨'
ORDER BY midrisk ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '어깨'
ORDER BY midrisk DESC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '어깨'
ORDER BY highrisk ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '어깨'
ORDER BY highrisk DESC;

-----------------------------하체 운동 데이터--------------------------------
SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '하체'
ORDER BY exdatano ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '하체'
ORDER BY lowmet ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '하체'
ORDER BY lowmet DESC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '하체'
ORDER BY midmet ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '하체'
ORDER BY midmet DESC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '하체'
ORDER BY highmet ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '하체'
ORDER BY highmet DESC;

------------------------------
SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '하체'
ORDER BY lowact ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '하체'
ORDER BY lowact DESC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '하체'
ORDER BY midact ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '하체'
ORDER BY midact DESC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '하체'
ORDER BY highact ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '하체'
ORDER BY highact DESC;
-----------------------------------------------------
SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '하체'
ORDER BY lowrisk ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '하체'
ORDER BY lowrisk DESC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '하체'
ORDER BY midrisk ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '하체'
ORDER BY midrisk DESC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '하체'
ORDER BY highrisk ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '하체'
ORDER BY highrisk DESC;
-------------------------------팔 운동 데이터-------------------------------
SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '팔'
ORDER BY exdatano ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '팔'
ORDER BY lowmet ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '팔'
ORDER BY lowmet DESC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '팔'
ORDER BY midmet ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '팔'
ORDER BY midmet DESC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '팔'
ORDER BY highmet ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '팔'
ORDER BY highmet DESC;

------------------------------
SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '팔'
ORDER BY lowact ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '팔'
ORDER BY lowact DESC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '팔'
ORDER BY midact ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '팔'
ORDER BY midact DESC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '팔'
ORDER BY highact ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '팔'
ORDER BY highact DESC;
-----------------------------------------------------
SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '팔'
ORDER BY lowrisk ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '팔'
ORDER BY lowrisk DESC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '팔'
ORDER BY midrisk ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '팔'
ORDER BY midrisk DESC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '팔'
ORDER BY highrisk ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '팔'
ORDER BY highrisk DESC;
----------------------------복근 운동 데이터-----------------------------
SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '복근'
ORDER BY exdatano ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '복근'
ORDER BY lowmet ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '복근'
ORDER BY lowmet DESC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '복근'
ORDER BY midmet ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '복근'
ORDER BY midmet DESC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '복근'
ORDER BY highmet ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '복근'
ORDER BY highmet DESC;

------------------------------
SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '복근'
ORDER BY lowact ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '복근'
ORDER BY lowact DESC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '복근'
ORDER BY midact ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '복근'
ORDER BY midact DESC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '복근'
ORDER BY highact ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '복근'
ORDER BY highact DESC;
-----------------------------------------------------
SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '복근'
ORDER BY lowrisk ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '복근'
ORDER BY lowrisk DESC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '복근'
ORDER BY midrisk ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '복근'
ORDER BY midrisk DESC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '복근'
ORDER BY highrisk ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '복근'
ORDER BY highrisk DESC;
---------------------------유산소 운동 데이터--------------------------------------
SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '유산소'
ORDER BY exdatano ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '유산소'
ORDER BY lowmet ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '유산소'
ORDER BY lowmet DESC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '유산소'
ORDER BY midmet ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '유산소'
ORDER BY midmet DESC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '유산소'
ORDER BY highmet ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '유산소'
ORDER BY highmet DESC;

------------------------------
SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '유산소'
ORDER BY lowact ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '유산소'
ORDER BY lowact DESC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '유산소'
ORDER BY midact ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '유산소'
ORDER BY midact DESC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '유산소'
ORDER BY highact ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '유산소'
ORDER BY highact DESC;
-----------------------------------------------------
SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '유산소'
ORDER BY lowrisk ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '유산소'
ORDER BY lowrisk DESC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '유산소'
ORDER BY midrisk ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '유산소'
ORDER BY midrisk DESC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '유산소'
ORDER BY highrisk ASC;

SELECT exdatano, exgroup, exname, muscle, musclesub, lowmet, midmet, highmet, lowact, midact, highact, lowrisk, midrisk, highrisk 
FROM exdata
WHERE exgroup = '유산소'
ORDER BY highrisk DESC;





commit;
delete from exdata;
rollback;



