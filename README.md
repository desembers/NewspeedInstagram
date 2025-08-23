# READ ME

## 뉴스피드 서비스 설명
 회원가입을 통해 생성된 유저가 게시물을 생성,조회,수정,삭제하고 친구 관계(Follow)를 맺은 유저의 게시물을 조회하며 게시물에 코멘트를 생성,수정,삭제할 수 있는 서비스.
 
## 와이어프레임

## API 명세서
 ### 1-1. 회원가입
  |Method	|Endpoint	|Description	|Parameters	|Request Body	|Response	|Status Code|
 |---|---|---|---|---|---|---|
 
 ### 1-2. 로그인
 ### 1-3. 로그아웃
 ### 1-4. 회원탈퇴
 
 ### 2-1. 프로필 생성
 ### 2-2. 프로필 조회
 ### 2-3. 프로필 수정
 
 ### 3-1. 뉴스피드 생성
 ### 3-2. 뉴스피드 조회
 ### 3-3. 뉴스피드 수정
 ### 3-4. 뉴스피드 삭제
 
 ### 4-1. 팔로우 생성
 ### 4-2. 팔로우 조회
 
 ### 5-1. 코멘트 생성
 ### 5-2. 코멘트 조회
 ### 5-3. 코멘트 수정
 ### 5-4. 코멘트 삭제
 
## ERD 명세서


## 테이블 명세서
### 1-1. 엔티티 - 속성 요약표
| 엔티티 | 속성(제약/인덱스)|
|---|---|
| 회원 users | id(PK), userName (UK), email (UK), password_hash, created_At, modified_At |
| 프로필 profiles | id(PK = users.Id, FK), nickName(displayName), bio, website, birthdate, created_At, modified_At |
| 뉴스피드 newfeeds | id(PK), author_id(Fk → users.Id, IDX), content, created_At, modified_At | 
| 팔로우 follows | follow(FK → users.id. IDX) |
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
|팔로우 하는 사람	|follower	|FK → users.id. IDX,| NOT NULL	|
|팔로우 받는 사람	|followed	|FK → users.id. IDX,| NOT NULL	|

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
