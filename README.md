# READ ME

## 뉴스피드 서비스 설명
회원가입을 통해 생성된 사용자가 뉴스피드를 작성(생성·조회·수정·삭제)할 수 있으며, 친구 관계(Follow)를 맺은 사용자의 게시물도 조회할 수 있습니다. 또한 게시물에 댓글을 작성하고 수정·삭제할 수 있어 사용자 간의 소통이 가능합니다.

## 주요 기능
1. 사용자 관리: JWT 인증/인가, 회원가입·로그인·로그아웃·회원탈퇴, 프로필 조회/수정, 비밀번호 변경
2. 뉴스피드: CRUD, 전체/기간별 조회, 페이징 지원
3. 친구 관리: 팔로우/언팔로우, 팔로워·팔로잉 목록 조회
4. 예외 처리: 전역 예외 처리 및 민감 동작 검증
 
## 와이어프레임
<img width="1429" height="1332" alt="Image" src="https://github.com/user-attachments/assets/e42f600e-3f3f-4bf1-a88b-f8666390b4ce" />

## API 명세서
 ### 1. 사용자
 |Method	|Endpoint	|Description	|Parameters	|Request Body	|Response	|Status Code|
 |---|---|---|---|---|---|---|
 |POST|/auth/signup|회원가입||{”email”: String,<br>”password”: String,<br>”userName”: String}|{ "id": Long,<br>"userName": "String",<br>"email": "String"}| 200 OK,<br>409 CONFLICT (비밀번호 오류),<br>400 BAD REQUEST (필드값 공란)|
 |POST|/auth/login|로그인|   |{”email”: String,<br>”password”: String}|{”id”: Long,<br>”email”: String,<br>”accessToken”: String}|200 OK,<br>404 NOT FOUND (없는 계정),<br>400 BAD REQUEST  (필드값 공란),<br>401 UNAUTHORIZED  (비밀 번호 오류)|
 |POST|auth/logout|로그아웃|   |없음 | 없음| 200 OK,<br>401 UNAUTHORIZED (로그인 없이 로그아웃 시),<br>401 UNAUTHORIZED (토큰 만료 시)|
 |POST|auth/withdraw|회원탈퇴|  |{”password”: String}|없음|200 OK,<br>401 UNAUTHORIZED (비밀번호 오류)|
 
 ### 2. 프로필
 |Method	|Endpoint	|Description	|Parameters	|Request Body	|Response	|Status Code|
|---|---|---|---|---|---|---|
|POST|/users/me/profiles|프로필 생성|없음|{”displayName”: String,<br>”bio”: String,<br>”website”: String,<br>”birthdate” : String}|{”userId”: Long,<br>”displayName” : String,<br>”bio” : String,<br>”website” : String,<br>”birthdate” : LocalDate, <br>”createdAt” : LocalDateTime,<br>"updatedAt” : LocalDateTime} | 200 OK|
|GET|/users/{userId}/profiles|프로필 조회|path : Long userId|없음|{”userId”: Long,<br>”displayName” : String,<br>”bio” : String,<br>”website” : String,<br>”birthdate” : LocalDate,<br>”createdAt” : LocalDateTime,<br>”updatedAt” : LocalDateTime}|200 OK|
|PATCH|/users/me/profiles|프로필 수정|없음|{”displayName”: String,<br>”bio”: String,<br>”website”: String,<br>”birthdate” : String}|{”userId”: Long<br>”displayName” : String,<br>”bio” : String,<br>”website” : String,<br>”birthdate” : LocalDate,<br>”createdAt” : LocalDateTime,<br>”updatedAt” : LocalDateTime}|200 OK|

 ### 3. 뉴스피드
 |Method	|Endpoint	|Description	|Parameters	|Request Body	|Response	|Status Code|
|---|---|---|---|---|---|---|
 |POST|/newsfeeds|뉴스피드 생성 | |{”content” : string}|{”id” : long ,<br>“authorId” : long,<br>“content” : String,<br>“createdAt” : localDateTime,<br>“updatedAt : localDateTIme}| 200 OK|
 |GET|/newsFeeds?start=YYYY-MM-DD&end=YYYY-MM-DD&page=0&size=10&sort=updatedAt,desc|뉴스피드 조회|Query: page (int, default: 1)<br>size (int, default: 10)<br>start(date)<br>end(date)<br>sort(updatedAt, DESC)|없음|{<br>"content": [<br>{"id": long,<br>"authorId": long,<br>"content": String,<br>"comments": [”id”:long, “userId”:long, “newsFeedId” : long, “text” : String, “createdAt” : LocalDateTime, “updatedAt” : LocalDateTime],<br>"createdAt": LocalDateTime,<br>"updatedAt": LocalDateTime}<br>],<br>"page": {<br>"size": long<br>"number": long,<br>"totalElements": long,<br>"totalPages": long<br>}<br>}|200 OK|
|PATCH|/newsfeeds/{newsfeedId}|뉴스피드 수정 | path : Long newsfeedId | {”content” : String}|{”id” : long ,<br>“authorId” :long,<br>“content” : String,<br>“createdAt” : localDateTime,<br>“updatedAt : localDateTIme}|200 OK,<br>400 BAD REQUEST(유저아이디와 작성자 아이디 불일치)|
|DELETE|/newsfeeds/{newsfeedId}|뉴스피드 삭제|path : Long newsfeedId|없음|없음|200 OK|
 
 ### 4. 팔로우
 |Method	|Endpoint	|Description	|Parameters	|Request Body	|Response	|Status Code|
|---|---|---|---|---|---|---|
|POST|/follow/{followedId}|팔로우생성|path : Long followedId|없음|{”followedId” : Id}|200 OK|
|GET|/follow/following|팔로우 조회|없음|없음|{”follows” : [{”followedId” :  Id,”followedId” :  Id,”followedId” : String,… ]}|200 OK|
|GET|/follow/fllowers|팔로워 조회|없음|없음|{”followers” : [{”followedId” :  Id,”followedId” :  Id,”followedId” : String,… ]}|200 OK|
|DELETE|follow/{followId}|언팔로우|path : Long followId|없음|없음|200 Ok|

 
 ### 5. 코멘트
 |Method	|Endpoint	|Description	|Parameters	|Request Body	|Response	|Status Code|
|---|---|---|---|---|---|---|
|POST|/newsfeeds/{feedId}/comments|댓글생성| |{”text” : String}|{”id” : long,<br>“userId” : long,<br>“newsfeed”: long,<br>“text” :String,<br>“LocalDateTime” : createdAt,<br>“LocalDateTime” : updatedAt}|200 OK|
|GET|/newsfeeds/{newsfeedId}/comments|댓글 조회| |없음|{”id” : long,<br>“userId” : long,<br>“newsfeed”: long,<br>“text” :String,<br>“LocalDateTime” : createdAt,<br>“LocalDateTime” : updatedAt}|200 OK|
|GET|comments/{commentId}|코멘트 단건 조회| |없음 |{”id” : long,<br>“userId” : long,<br>“newsfeed”: long,<br>“text” :String,<br>“LocalDateTime” : createdAt,<br>“LocalDateTime” : updatedAt}|200 OK|
|GET|/newsFeeds/1/comments|뉴스피드의 코멘트 조회| |없음|{”id” : long,<br>“userId” : long,<br>“newsfeed”: long,<br>“text” :String,<br>“LocalDateTime” : createdAt,<br>“LocalDateTime” : updatedAt}|200 OK|
|PUT|/newsfeeds/comments/{commentId}|코멘트 수정| |{”text” : String}|{”id” : long,<br>“userId” : long,<br>“newsfeed”: long,<br>“text” :String,<br>“LocalDateTime” : createdAt,<br>“LocalDateTime” : updatedAt}|200 OK|
|DELETE|/newsFeeds/comments/{commentId}|코멘트 삭제| |없음|없음|200 OK|
 
## ERD 명세서
```mermaid
classDiagram
direction BT
class comment {
   bit(1) deleted
   datetime(6) created_at
   bigint newsfeed_id
   datetime(6) updated_at
   bigint user_id
   varchar(255) text
   bigint id
}
class follows {
   datetime(6) created_at
   bigint from_user_id
   bigint to_user_id
   bigint id
}
class logout_token {
   bit(1) deleted
   datetime(6) created_at
   datetime(6) expired_at
   datetime(6) updated_at
   varchar(255) token
}
class news_feed {
   bit(1) deleted
   datetime(6) created_at
   datetime(6) updated_at
   bigint user_id
   varchar(255) content
   bigint id
}
class profiles {
   date birthdate
   bit(1) deleted
   datetime(6) created_at
   datetime(6) updated_at
   varchar(50) display_name
   varchar(255) website
   longtext content
   bigint user_id
}
class users {
   bit(1) deleted
   datetime(6) created_at
   datetime(6) updated_at
   varchar(30) user_name
   varchar(100) password
   varchar(255) email
   bigint id
}

comment  -->  news_feed : newsfeed_id:id
comment  -->  users : user_id:id
follows  -->  users : to_user_id:id
follows  -->  users : from_user_id:id
news_feed  -->  users : user_id:id
profiles  -->  users : user_id:id
```

## 테이블 명세서
### 1-1. 엔티티 - 속성 요약표
| 엔티티 | 속성(제약/인덱스)|
|---|---|
| 회원 users | id(PK), userName, email (UK), password_hash, created_At, updated_At |
| 프로필 profiles | id(PK = users.Id, FK), nickName(displayName), bio, website, birthdate, created_At, updated_At |
| 뉴스피드 newfeeds | id(PK), author_id(Fk → users.Id, IDX), content, created_At, updated_At | 
| 팔로우 follows | from_user(FK → users.id. IDX), to_user, created_At |

### 1-2. 엔티티표
|사용자 Users	|컬럼	|제약	|설명|
|------------|----|-----|--|
|Id	|Id	|PK	|사용자 |식별자|
|이름	|userName	|UK, Not Null	|사용자 이름|
|회원 |이메일	email	|UK, Not Null	|사용자 아이디는 이메일 형식이어야 합니다.
영문, 숫자 권장 - 예외처리?|
|비밀번호	|password	|Not Null	|Bcrypt 등 해시|
|가입일	|created_At	|Not Null	|생성 시각|
|수정일	|updated_At	|Not Null	|수정 시각|

|프로필 profiles	|컬럼|	제약	|설명|
|------------|----|-----|--|
|Id|	id	|PK, FK → userId	|사용자와 1:1 (같은 키)|
|닉네임	|nickName(displayName)	|UK, NOT NULL	|표시 이름(닉네임)|
|자기소개	|bio	|	|자기소개|
|웹사이트|	website	|	|웹사이트|
|생일	|birthdate	|	|생년월일|
|가입일|	created_At|	NOT NULL	|생성 시각|
수정일	|updated-At|	NOT NULL|	수정 시각|

|뉴스피드 newfeeds	|컬럼	|제약|	설명|
|------------|----|-----|--|
|게시글 ID	|id	|PK, AI|	게시글 ID|
|작성자	|user_id	|FK → users.id. IDX,| NOT NULL|	작성자|
|본문	|content|		|본문|
|작성일	|created_At	|IDX, NOT NULL	|작성 시각|
|수정일	|updated-At	|NOT NULL	|수정 시각|


|친구관리 follows	|컬럼	|제약	|설명|
|------------|----|-----|--|
|팔로우 하는 사람	|from_user	|FK → users.id. IDX,| NOT NULL	|
|팔로우 받는 사람	|to_user	|FK → users.id. IDX,| NOT NULL	|

## 패키지
```
instagram
    │  apitest.http
    │  InstagramApplication.java
    │
    ├─auth
    │  │  JwtTokenProvider.java
    │  │
    │  ├─annotation
    │  │      Auth.java
    │  │
    │  ├─config
    │  │      AuthArgumentResolver.java
    │  │
    │  ├─controller
    │  │      AuthController.java
    │  │
    │  ├─dto
    │  │      AuthUser.java
    │  │      LoginRequest.java
    │  │      LoginResponse.java
    │  │      SignupRequest.java
    │  │      SignupResponse.java
    │  │      WithdrawRequest.java
    │  │
    │  ├─entity
    │  │      LogoutToken.java
    │  │
    │  ├─filter
    │  │      FilterConfig.java
    │  │      JwtFilter.java
    │  │
    │  ├─repository
    │  │      LogoutTokenRepository.java
    │  │
    │  └─service
    │          AuthService.java
    │          TokenValidCheckService.java
    │
    ├─comment
    │  ├─controller
    │  │      CommentCotroller.java
    │  │
    │  ├─dto
    │  │  ├─request
    │  │  │      CommentSaveRequestDto.java
    │  │  │      CommentUpdateRequestDto.java
    │  │  │
    │  │  └─response
    │  │          CommentResponse.java
    │  │
    │  ├─entity
    │  │      Comment.java
    │  │
    │  ├─repository
    │  │      CommentRepository.java
    │  │
    │  └─service
    │          CommentService.java
    │
    ├─common
    │  ├─advice
    │  │      GlobalExceptionHandler.java
    │  │
    │  ├─config
    │  │      PasswordEncoder.java
    │  │
    │  ├─consts
    │  │      Const.java
    │  │
    │  ├─entity
    │  │      BaseEntity.java
    │  │      JpaConfig.java
    │  │
    │  └─exception
    │          InVaidEmailFromatException.java
    │          InValidException.java
    │          InValidPasswordException.java
    │          InValidPasswordFormatException.java
    │          NesFeedException.java
    │          UnauthorizedAccessException.java
    │
    ├─newsFeeds
    │  ├─controller
    │  │      NewsFeedController.java
    │  │
    │  ├─dto
    │  │      CustomUserDetails.java
    │  │      NewsFeedGetResponse.java
    │  │      NewsFeedPatchRequest.java
    │  │      NewsFeedPatchResponse.java
    │  │      NewsFeedSaveRequest.java
    │  │      NewsFeedSaveResponse.java
    │  │
    │  ├─entity
    │  │      NewsFeed.java
    │  │
    │  ├─Repository
    │  │      NewsFeedRepository.java
    │  │
    │  └─service
    │          NewsFeedService.java
    │
    ├─profile
    │  ├─controller
    │  │      ProfileController.java
    │  │
    │  ├─dto
    │  │  ├─request
    │  │  │      ProfileSaveRequestDto.java
    │  │  │      ProfileUpdateRequestDto.java
    │  │  │
    │  │  └─response
    │  │          ProfileResponseDto.java
    │  │
    │  ├─entity
    │  │      Profile.java
    │  │
    │  ├─repository
    │  │      ProfileRepository.java
    │  │
    │  └─service
    │          ProfileService.java
    │
    └─user
        ├─controller
        │      UserController.java
        │
        ├─dto
        │  ├─request
        │  │      UserUpdateRequestDto.java
        │  │
        │  └─response
        │          UserResponseDto.java
        │
        ├─entity
        │      User.java
        │
        ├─repository
        │      UserRepository.java
        │
        └─service
                UserService.java
```
