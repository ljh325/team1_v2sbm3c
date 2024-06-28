/**********************************/
/* Table Name: 변경사항 */
/**********************************/
DROP TABLE PATCH;
CREATE TABLE PATCH(
		PATCHNO                       		NUMBER(10)		 NOT NULL   PRIMARY KEY,
		TITLE                         		VARCHAR2(100)		 NOT NULL,
		CONTENT                       		CLOB		 NOT NULL,
		VIEWCNT                       		NUMBER(7)		 DEFAULT 0		 NULL
);

COMMENT ON TABLE PATCH is '변경사항';
COMMENT ON COLUMN PATCH.PATCHNO is '변경사항번호';
COMMENT ON COLUMN PATCH.TITLE is '제목';
COMMENT ON COLUMN PATCH.CONTENT is '내용';
COMMENT ON COLUMN PATCH.VIEWCNT is '조회수';

DROP SEQUENCE patch_seq;

CREATE SEQUENCE patch_seq
  START WITH 1              -- 시작 번호
  INCREMENT BY 1          -- 증가값
  MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
  CACHE 2                       -- 2번은 메모리에서만 계산
  NOCYCLE;                     -- 다시 1부터 생성되는 것을 방지

SELECT content
FROM patch;
  
SELECT m.id, m.nickname, m.thumbs, m.grade
FROM patch p, member m 
WHERE p.memberno = m.memberno;