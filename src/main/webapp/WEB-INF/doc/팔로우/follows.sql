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


-- 등록
INSERT INTO Follows (follower_no, followed_no, follow_date) VALUES (3, 4, sysdate); -- Alice가 Bob을 팔로우
INSERT INTO Follows (follower_no, followed_no, follow_date) VALUES (3, 5, sysdate); -- Alice가 Charlie를 팔로우
INSERT INTO Follows (follower_no, followed_no, follow_date) VALUES (4, 3, sysdate); -- Bob이 Charlie를 팔로우
INSERT INTO Follows (follower_no, followed_no, follow_date) VALUES (4, 6, sysdate); -- Charlie가 Dave를 팔로우
INSERT INTO Follows (follower_no, followed_no, follow_date) VALUES (6, 4, sysdate); -- Dave가 Alice를 팔로우

--팔로우 관계를 추가합니다:
INSERT INTO Follows (follower_id, followed_id) VALUES (2, 4); -- Bob이 Dave를 팔로우

--모든 팔로우 관계를 조회합니다:
SELECT * FROM Follows;

--특정 사용자의 팔로우 관계를 조회합니다:
SELECT * FROM Follows WHERE follower_id = 1; -- Alice가 팔로우하는 사람들


--특정 사용자를 팔로우하는 사람들을 조회합니다:
SELECT * FROM Follows WHERE followed_id = 3; -- Charlie를 팔로우하는 사람들

--팔로우 관계를 삭제합니다:
DELETE FROM Follows WHERE follower_id = 2 AND followed_id = 4; -- Bob이 Dave를 팔로우하는 관계 삭제

-- 특정 사용자 조회
SELECT * FROM member WHERE memberno = 1;

-- 모든 사용자 조회
SELECT * FROM member;

--사용자를 삭제합니다:
DELETE FROM Users WHERE user_id = 5;











-- 삭제
-- 예: 내가 상대 팔로잉을 끊는다
DELETE FROM follows
WHERE 