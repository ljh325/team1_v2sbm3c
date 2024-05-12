DROP TABLE contents CASCADE;

DROP TABLE contents;

CREATE TABLE contents (
    contentsno      NUMBER(10)      NOT NULL    PRIMARY KEY,
    cateno          NUMBER(10)      NOT NULL,
    contents_admin  NUMBER(1)      DEFAULT 0   NOT NULL, 
    title           VARCHAR2(100)   NOT NULL,
    content         CLOB            NOT NULL,
    recom           NUMBER(7)       DEFAULT 0   NOT NULL,
    viewcnt         NUMBER(7)       DEFAULT 0   NOT NULL,
    commentcnt      NUMBER(7)       DEFAULT 0   NOT NULL,
    passwd          VARCHAR2(15)    NOT NULL,
    tag             VARCHAR2(100)   NOT NULL,
    rdate           DATE            NOT NULL,
    file1           VARCHAR2(100)  NULL,
    file1saved      VARCHAR2(100)  NULL,
    thumb1          VARCHAR2(100)  NULL,
    size1           NUMBER(10)      NULL,
    youtube         VARCHAR2(1000)  NULL,
    memberno        NUMBER(10)      NULL
--    FOREIGN KEY (cateno) REFERENCES cate(cateno),
--    FOREIGN KEY (memberno) REFERENCES member(memberno)
    )
    
COMMENT ON TABLE CONTENTS is '커뮤티니';
COMMENT ON COLUMN CONTENTS.CONTENTSNO is '컨텐츠 번호';
COMMENT ON COLUMN CONTENTS.CATENO is '카테고리 번호';
COMMENT ON COLUMN CONTENTS.CONTENTS_ADMIN is '관리자권한';
COMMENT ON COLUMN CONTENTS.TITLE is '제목';
COMMENT ON COLUMN CONTENTS.CONTENT is '내용';
COMMENT ON COLUMN CONTENTS.RECOM is '추천수';
COMMENT ON COLUMN CONTENTS.VIEWCNT is '조회수';
COMMENT ON COLUMN CONTENTS.COMMENTCNT is '댓글수';
COMMENT ON COLUMN CONTENTS.PASSWD is '패스워드';
COMMENT ON COLUMN CONTENTS.TAG is '태그';
COMMENT ON COLUMN CONTENTS.RDATE is '등록일';
COMMENT ON COLUMN CONTENTS.FILE1 is '메인 이미지';
COMMENT ON COLUMN CONTENTS.FILE1SAVED is '실제 저장된 메인 이미지';
COMMENT ON COLUMN CONTENTS.THUMB1 is '메인 이미지 Preview';
COMMENT ON COLUMN CONTENTS.SIZE1 is '메인 이미지 크기';
COMMENT ON COLUMN CONTENTS.YOUTUBE is 'YOUTUBE 영상';
COMMENT ON COLUMN CONTENTS.MEMBERNO is '회원 번호';

DROP SEQUENCE contents_seq;

CREATE SEQUENCE contents_seq
  START WITH 1              -- 시작 번호
  INCREMENT BY 1          -- 증가값
  MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
  CACHE 2                       -- 2번은 메모리에서만 계산
  NOCYCLE;                     -- 다시 1부터 생성되는 것을 방지
-- CREATE
INSERT INTO contents(contentsno, memberno, cateno, contents_admin, title, content, recom, viewcnt, commentcnt, passwd, tag, rdate, file1,
       file1saved, thumb1, size1)
VALUES (contents_seq.nextval, 1, 2, 0, '식단', '다이어트 식단 추천', 0, 0, 0, '1234', '다이어트,식단', sysdate, 'food.jpg', 'food_1.jpg',
        'food_t.jpg', 1000);
        
INSERT INTO contents(contentsno, memberno, cateno, contents_admin, title, content, recom, viewcnt, commentcnt, passwd, tag, rdate, file1,
       file1saved, thumb1, size1)
VALUES (contents_seq.nextval, 1, 1, 0, '운동', '운동 추천', 0, 0, 0, '1234', '운동,다이어트', sysdate, 'health.jpg', 'health_1.jpg',
        'health_t.jpg', 1000);

INSERT INTO contents(contentsno, memberno, cateno, contents_admin, title, content, recom, viewcnt, commentcnt, passwd, tag, rdate, file1,
       file1saved, thumb1, size1)
VALUES (contents_seq.nextval, 1, 2, 0, '식단 짜는 방법', '식단 구성 추천', 0, 0, 0, '1234', '다이어트,식단', sysdate, 'food.jpg', 'food_1.jpg',
        'food_t.jpg', 1000);

INSERT INTO contents(contentsno, memberno, cateno, contents_admin, title, content, recom, viewcnt, commentcnt, passwd, tag, rdate, file1,
       file1saved, thumb1, size1)
VALUES (contents_seq.nextval, 1, 1, 0, '운동 방법', '다양한 운동 방법', 0, 0, 0, '1234', '다이어트,운동', sysdate, 'food.jpg', 'food_1.jpg',
        'food_t.jpg', 1000);
        
-- READ
SELECT contentsno, memberno, cateno, contents_admin, title, content, recom, viewcnt, commentcnt, passwd, tag, rdate, file1,
       file1saved, thumb1, size1, youtube
FROM contents;

SELECT contentsno, memberno, cateno, contents_admin, title, content, recom, viewcnt, commentcnt, passwd, tag, rdate, file1,
       file1saved, thumb1, size1, youtube
FROM contents
WHERE contentsno = 1;

-- UPDATE
UPDATE contents
SET title = '제목 수정'
WHERE contentsno = 1;

UPDATE contents
SET content = '내용 수정'
WHERE contentsno = 1;

UPDATE contents
SET tag = '태그 수정'
WHERE contentsno = 1;

-- DELETE
DELETE FROM contents
WHERE contentsno = 4;














