/**********************************/
/* Table Name: 팔로우팔로잉 */
/**********************************/
DROP TABLE FOLLOWS;
DROP TABLE FOLLOWS CASCADE CONSTRAINTS;

CREATE TABLE FOLLOWS(
		FOLLOWER_NO                     		NUMBER(10)		 NOT NULL,
		FOLLOWED_NO                   		NUMBER(10)		 NOT NULL,
		FOLLOW_DATE                   		DATE		 NULL ,
  PRIMARY KEY (FOLLOWER_NO, FOLLOWED_NO),
  FOREIGN KEY (FOLLOWER_NO) REFERENCES MEMBER(MEMBERNO),
  FOREIGN KEY (FOLLOWED_NO) REFERENCES MEMBER(MEMBERNO)
);

COMMENT ON TABLE FOLLOWS is '팔로우팔로잉';
COMMENT ON COLUMN FOLLOWS.FOLLOWER_NO is '팔로우하는사용자';
COMMENT ON COLUMN FOLLOWS.FOLLOWED_NO is '팔로우당하는사용자';
COMMENT ON COLUMN FOLLOWS.FOLLOW_DATE is '팔로우 한 날짜';

delete from follows;
commit;
-- 등록
INSERT INTO Follows (follower_no, followed_no, follow_date) VALUES (5, 6, sysdate); -- Alice가 Bob을 팔로우
INSERT INTO Follows (follower_no, followed_no, follow_date) VALUES (5, 7, sysdate); -- Alice가 Charlie를 팔로우
INSERT INTO Follows (follower_no, followed_no, follow_date) VALUES (6, 5, sysdate); -- Bob이 Charlie를 팔로우
INSERT INTO Follows (follower_no, followed_no, follow_date) VALUES (6, 7, sysdate); -- Charlie가 Dave를 팔로우
INSERT INTO Follows (follower_no, followed_no, follow_date) VALUES (6, 4, sysdate); -- Dave가 Alice를 팔로우

--팔로우 관계를 추가합니다:
INSERT INTO Follows (follower_no, followed_no) VALUES (2, 4); -- Bob이 Dave를 팔로우

--모든 팔로우 관계를 조회합니다:
SELECT  follower_no, followed_no, follow_date
FROM Follows;

--특정 사용자의 팔로우 관계를 조회합니다:
SELECT  follower_no, followed_no, follow_date
FROM Follows 
WHERE follower_no = 5; -- Alice가 팔로우하는 사람들

-- 허
SELECT  f.follower_no, f.followed_no, f.follow_date, 
m.memberno, m.id, m.mname, m.nickname, m.tel, m.mdate, m.grade, m.point, m.birth, m.sex, m.profile, m.profilesaved, m.thumbs, m.sizes
FROM Follows f, member m
WHERE m.memberno = f.followed_no AND followed_no = 9; -- Alice가 팔로우하는 사람들


    SELECT f.follower_no, f.followed_no, f.follow_date, 
    m.memberno, m.id, m.mname, m.nickname, m.tel, m.mdate, m.grade, m.point, m.birth, m.sex, m.profile, m.profilesaved, m.thumbs, m.sizes
    FROM Follows f, member m
    WHERE m.memberno = f.followed_no AND follower_no = 5;
    
    
SELECT f.follower_no, f.followed_no, f.follow_date,     
r.recimageno, r.recprofile, r.recprofilesaved, r.recthumbs, r.recsizes, r.reccontents, r.recvisible, r.recdate, r.recupdate, r.exrecordno, r.memberno    
FROM Follows f, recordimage r   
WHERE f.followed_no = r.memberno AND follower_no = 11;
-- 팔로우한게시글 조회
SELECT *
FROM (
    SELECT 
        f.follower_no,
        f.followed_no,
        f.follow_date,
        r.recimageno,
        r.recprofile,
        r.recprofilesaved,
        r.recthumbs,
        r.recsizes,
        r.reccontents,
        r.recvisible,
        r.recdate,
        r.recupdate,
        r.exrecordno,
        r.memberno,
        ROW_NUMBER() OVER (PARTITION BY r.exrecordno ORDER BY r.recdate DESC) AS row_num
    FROM Follows f
    JOIN recordimage r ON f.followed_no = r.memberno
    WHERE f.follower_no = 11
) t
WHERE t.row_num = 1;
  
    
    SELECT recimageno, recprofile, recprofilesaved, recthumbs, recsizes, reccontents, recvisible, recdate, recupdate, exrecordno, memberno
FROM recordimage
ORDER BY   COALESCE(recupdate, recdate) DESC;
    
--특정 사용자의 팔로우 관계를 조회합니다:
SELECT  follower_no, followed_no, follow_date
FROM Follows 
WHERE followed_no = 5; -- Alice가 팔로우당한 사람들



-- 현재 내가 팔로잉 한 수
SELECT count(followed_no)
FROM Follows 
WHERE follower_no = 5;

-- 현재 내 팔로우 수
SELECT count(follower_no)
FROM Follows 
WHERE followed_no = 5;

-- 내가 팔로우 한 사람이 있는지 확인
SELECT count(*) as cnt FROM Follows WHERE followed_no = 9 AND follower_no = 5; 

--팔로우 관계를 삭제합니다:
DELETE FROM Follows WHERE follower_no = 5 AND followed_no = 6; -- Bob이 Dave를 팔로우하는 관계 삭제











-- 특정 사용자 조회
SELECT * FROM member WHERE memberno = 1;

-- 모든 사용자 조회
SELECT * FROM member;













-- 삭제
-- 예: 내가 상대 팔로잉을 끊는다
DELETE FROM follows
WHERE 