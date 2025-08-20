# README

# GitHub 협업 가이드 — NewspeedInstagram
> 조직/과정: **내일배움캠프 스파르타 KDT 실무형 Kotlin & Spring 개발자 양성과정 8회차**  
> 팀: **스프링마이쮸**  
> 저장소(캐노니컬): **https://github.com/desembers/NewspeedInstagram**

팀으로 깃허브를 사용할 때 **조직(Organization) 생성부터 PR 머지까지** 한 문서로 끝내는 실전 가이드입니다.  
현재 저장소는 **Shared Repo(공유 레포)** 형태로 운영하며, 필요 시 Fork 기반 협업은 부록 A를 참고하세요.  
기본 브랜치는 `main`을 가정합니다. (팀에서 `master`를 쓴다면 문서의 `main`을 `master`로 바꿔서 사용하세요.)

---

## 목차

1. [빠른 시작(TL;DR)](#빠른-시작tldr)  
2. [0) 용어 & 팀 결정 사항](#0-용어--팀-결정-사항)  
3. [1) 조직(Organization) 만들기](#1-조직organization-만들기)  
4. [2) 레포지토리 생성 & 기본 설정](#2-레포지토리-생성--기본-설정)  
5. [3) 팀 권한/브랜치 보호 규칙 설정](#3-팀-권한브랜치-보호-규칙-설정)  
6. [4) 템플릿/자동화(.github) 세팅](#4-템플릿자동화github-세팅)  
7. [5) 로컬 준비 & 클론](#5-로컬-준비--클론)  
8. [6) 브랜치 전략](#6-브랜치-전략)  
9. [7) 작업 흐름(이슈→브랜치→커밋→푸시→PR→리뷰→머지)](#7-작업-흐름이슈브랜치커밋푸시pr리뷰머지)  
10. [8) PR 규칙 & 코드리뷰 체크리스트](#8-pr-규칙--코드리뷰-체크리스트)  
11. [9) 머지 후 정리 & 릴리스 노트](#9-머지-후-정리--릴리스-노트)  
12. [10) IntelliJ 팁](#10-intellij-팁)  
13. [11) 터미널 치트시트(복붙용)](#11-터미널-치트시트복붙용)  
14. [부록 A. Shared vs Fork 워크플로우](#부록-a-shared-vs-fork-워크플로우)  
15. [부록 B. GitHub Actions(Java/Gradle) 예시](#부록-b-github-actionsjavagradle-예시)  
16. [부록 C. 커밋 메시지 규칙(Conventional Commits)](#부록-c-커밋-메시지-규칙conventional-commits)  
17. [부록 D. CODEOWNERS/PR 템플릿 예시](#부록-d-codeownerspr-템플릿-예시)  
18. [부록 E. 자주 하는 실수 & 트러블슈팅](#부록-e-자주-하는-실수--트러블슈팅)

---

## 빠른 시작(TL;DR)

### 관리자(Admin)

1) **조직 만들기** → 멤버 초대 → 팀(Developers/Reviewers/Admins) 생성  
2) **레포 생성**(공개/비공개 선택, `main` 기본 브랜치)  
3) **브랜치 보호 규칙**: `main`에
   - Require pull request reviews (예: 최소 1~2명 승인)
   - Require status checks to pass (CI 통과 필수)
   - Require linear history 또는 Squash merge만 허용(권장)
4) **.github** 디렉터리 추가: `CODEOWNERS`, `pull_request_template.md`, 이슈 템플릿, CI

### 팀원(Member)

```bash
# 1) 클론
git clone https://github.com/desembers/NewspeedInstagram.git
cd NewspeedInstagram
git config user.name  "<Your Name>"
git config user.email "<you@example.com>"

# 2) 최신 main 기반 작업 브랜치
git checkout main && git pull --rebase origin main
git checkout -b feature/<scope>-<short-desc>

# 3) 커밋 & 푸시 & PR
git add -A
git commit -m "feat(<scope>): <요약>"
git push -u origin feature/<scope>-<short-desc>
# GitHub에서 PR 생성 → 리뷰 승인 → Squash & merge
```

---

## 0) 용어 & 팀 결정 사항

- **Organization**: 팀 공간. 멤버/팀/레포 권한을 중앙에서 관리합니다.
- **Repository**: 코드/이슈/PR의 단위 공간.
- **Branch**: 독립 작업 선. 기본은 `main`(보호).
- **PR(Pull Request)**: 브랜치 변경을 `main`으로 합치기 전 리뷰/검증 단계.

**팀이 먼저 정해야 할 것**

- 기본 브랜치 이름: `main`(권장) 또는 `master`
- 머지 방식: **Squash merge**(권장) / Merge commit / Rebase merge
- 리뷰 정책: 승인 인원(1~2), 필수 리뷰어(코드오너), 드래프트 PR 사용 여부
- 브랜치 네이밍: `feature/`, `fix/`, `chore/`, `docs/`, `refactor/` 등
- 커밋 규칙: Conventional Commits 사용 여부
- CI 필수 여부: 빌드/테스트 통과 시에만 머지 허용

---

## 1) 조직(Organization) 만들기

1. GitHub 우측 상단 **+** → **New organization**
2. 플랜/이름/청구 선택
3. 멤버 초대(이메일/아이디)
4. **Teams** 생성 (예: `developers`, `reviewers`, `admins`)  
   - 각 팀에 멤버 추가
   - 팀별 레포 접근 권한(Read/Triage/Write/Maintain/Admin) 부여

> 팁: 외부 협력자는 팀 권한을 제한(예: `Triage`/`Read`)하고, 기여는 Fork+PR로 운영 가능.

---

## 2) 레포지토리 생성 & 기본 설정

1. Organization에서 **New repository**
2. 이름/공개 범위(Private/Internal/Public) 선택
3. **Initialize**: README, `.gitignore`(예: Java), 라이선스 선택
4. 생성 후 **Settings ▸ General**
   - Default branch: `main`
   - Merge 버튼 정책: Squash만 허용(권장), Merge commit 비활성화
   - 자동으로 브랜치 삭제(After merging, automatically delete head branches) 활성화

> 현재 팀 저장소: **https://github.com/desembers/NewspeedInstagram**

---

## 3) 팀 권한/브랜치 보호 규칙 설정

**Settings ▸ Branches ▸ Add rule** (`main` 대상으로):

- ✅ Require a pull request before merging
  - Required approvals: **1~2**
  - Dismiss stale pull request approvals when new commits are pushed
  - Require review from Code Owners (옵션)
- ✅ Require status checks to pass before merging
  - CI 워크플로 이름 선택(예: `build`/`test`)
- ✅ Require branches to be up to date before merging (옵션)
- ✅ Include administrators (관리자도 규칙 적용)
- ✅ Restrict who can push to matching branches (보호 강화)
- (선택) ✅ Require linear history (merge commit 금지)

> **권장 머지 방식**: **Squash & merge** → `main` 히스토리를 한 줄로 깔끔하게 유지.

---

## 4) 템플릿/자동화(.github) 세팅

레포 루트에 **`.github/`** 디렉터리를 만들고 다음을 배치:

- `CODEOWNERS` : 파일/폴더별 기본 리뷰어 지정
- `pull_request_template.md` : 모든 PR에 공통 템플릿
- `ISSUE_TEMPLATE/bug_report.md`, `feature_request.md` : 이슈 양식
- `workflows/ci.yml` : GitHub Actions CI (Gradle/JDK 설정 예시는 부록 B)

> 예시는 [부록 D](#부록-d-codeownerspr-템플릿-예시) 참고.

---

## 5) 로컬 준비 & 클론

```bash
# 사용자 정보 (처음 1회)
git config --global user.name  "Your Name"
git config --global user.email "you@example.com"

# 레포 클론
git clone https://github.com/desembers/NewspeedInstagram.git
cd NewspeedInstagram

# 최신 main 동기화 후 브랜치 생성
git checkout main && git pull --rebase origin main
git checkout -b feature/<scope>-<short-desc>
```

> **Fork 기반**으로 운영한다면: 개인 계정으로 Fork → 본인 Fork를 클론 → `upstream`으로 원본을 추가하고 동기화(부록 A).

---

## 6) 브랜치 전략

가볍고 명확하게 **Trunk(=main) + 단명 기능 브랜치**를 권장:

- `main`: 항상 배포 가능한 상태, 보호 규칙 적용
- 작업 브랜치: `feature/<scope>-<short-desc>`, `fix/<scope>-<short-desc>` 등
- 큰 작업은 **작은 PR**로 쪼개 진행
- 오래된 브랜치는 PR이 닫히면 삭제

예시:

```
feature/feed-like-button
fix/login-null-pointer
docs/readme-collab-guide
refactor/profile-entity
```

---

## 7) 작업 흐름(이슈→브랜치→커밋→푸시→PR→리뷰→머지)

1) **이슈 만들기**: 목표/배경/완료 조건 정의, 라벨/담당자 지정  
2) **브랜치 생성**: `main` 최신화 후 `feature/...` 분기  
3) **작업 & 커밋**: 작은 단위, 테스트 코드 포함, 메시지 규칙 준수  
4) **푸시 & PR 생성**: PR 템플릿 채우고 이슈 연결(`Closes #123`)  
5) **코드리뷰/CI**: 리뷰 코멘트 반영, CI 실패 시 수정 푸시  
6) **머지**: Squash & merge → 브랜치 삭제(자동)  
7) **동기화**: 로컬에서 `git checkout main && git pull --rebase`

---

## 8) PR 규칙 & 코드리뷰 체크리스트

**PR 제목 형식(권장)**

```
feat(feed): 좋아요 버튼 추가 (#123)
fix(auth): NPE 방지 로직 추가
docs: 협업 README 최초 작성
```

**PR 본문 체크리스트**

- [ ] 변경 요약(스크린샷/동영상 포함)
- [ ] 이슈 링크(`Closes #번호`)
- [ ] 테스트 방법/결과
- [ ] 호환성 이슈(마이그레이션/DB 변경 등)
- [ ] 추가 작업 항목(To-do)

**리뷰어 체크리스트**

- [ ] 요구사항 충족 여부
- [ ] 테스트/빌드 통과
- [ ] 코드 가독성/중복/네이밍/주석
- [ ] 예외/에러 처리
- [ ] 보안/성능/트랜잭션 고려
- [ ] 모듈/레이어 의존 방향 준수

---

## 9) 머지 후 정리 & 릴리스 노트

- PR 머지 → 브랜치 자동 삭제(설정)  
- 로컬 정리: `git fetch --prune`  
- 태깅/릴리스: `main` 기준 태그 생성 → Release 노트에 PR/이슈 자동 포함

```bash
git checkout main && git pull --rebase
git tag -a v1.0.0 -m "첫 릴리스"
git push origin v1.0.0
```

---

## 10) IntelliJ 팁

- **Git 툴 윈도우(Alt+9)**: 히스토리/브랜치/PR 흐름 시각화
- **Pull with rebase**: *Git ▸ Pull…* → `--rebase` 체크
- **Remotes 관리**: *Git ▸ Manage Remotes* 에서 `origin`(필수), `upstream`(Fork일 때)
- **코드 스타일 공유**: `.editorconfig`, IntelliJ Code Style/XML을 레포에 포함
- **Pre-commit 검사**: `gradlew test`를 Commit/Push 전에 실행하도록 설정

---

## 11) 터미널 치트시트(복붙용)

```bash
# 최신 main 기반 브랜치 만들기
git checkout main && git pull --rebase origin main
git checkout -b feature/<scope>-<short-desc>

# 변경 추적/커밋/푸시
git add -A
git commit -m "feat(<scope>): <요약>"
git push -u origin feature/<scope>-<short-desc>

# PR 이후 최신 main 동기화
git checkout main && git pull --rebase origin main

# 오래된 원격 브랜치 목록 정리
git fetch --prune
```

---

## 부록 A. Shared vs Fork 워크플로우

| 항목 | Shared Repo(조직 레포) | Fork 기반 |
|---|---|---|
| 권한 | 조직/팀 권한으로 제어 | 외부 기여에 유리 |
| 브랜치 | 같은 레포에서 분기 | 개인 포크에서 분기 후 PR |
| 보안 | 내부 멤버에 적합 | 외부 협력자 접근 최소화 |
| 설정 | 브랜치 보호로 통제 | Upstream 동기화 필요 |

> 내부 팀이면 **Shared Repo**가 단순/명확. 외부 협업자는 **Fork** 권장.

### Fork 동기화 요약

```bash
# 원본을 upstream으로 등록 (Fork 사용 시)
git remote add upstream https://github.com/desembers/NewspeedInstagram.git

# 동기화
git checkout main
git pull --rebase upstream main
git push origin main
```

---

## 부록 B. GitHub Actions(Java/Gradle) 예시

`.github/workflows/ci.yml`

```yaml
name: ci
on:
  pull_request:
    branches: [ "main" ]
  push:
    branches: [ "main" ]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-java@v4
        with:
          distribution: temurin
          java-version: "17"
          cache: gradle
      - name: Build & Test
        run: ./gradlew clean build --stacktrace
```

> 브랜치 보호에서 **Required status checks**로 `ci`를 선택하세요.

---

## 부록 C. 커밋 메시지 규칙(Conventional Commits)

```
<type>(<scope>): <subject>
```

**type 예시**: `feat`, `fix`, `docs`, `style`, `refactor`, `test`, `chore`, `build`, `ci`

예시:

```
feat(feed): 좋아요 버튼 추가
fix(auth): NPE 방지 로직 추가
docs: 협업 README 최초 작성
```

---

## 부록 D. CODEOWNERS/PR 템플릿 예시

`.github/CODEOWNERS`

```
# 팀/사용자를 코드오너로 지정(파일 경로 기준)
# 임시로 저장소 오너 @desembers 를 기본 리뷰어로 지정
*       @desembers

# 팀 슬러그가 생기면 아래처럼 교체하세요(예: @your-org/reviewers)
# backend/ @your-org/backend-team
# frontend/ @your-org/frontend-team
```

`.github/pull_request_template.md`

```md
## 요약
- 변경 내용:

## 관련 이슈
- Closes #

## 테스트 방법
- [ ] 로컬에서 `./gradlew test` 통과
- [ ] 주요 기능 수동 테스트 스텝:

## 체크리스트
- [ ] 작은 단위의 PR (리뷰어 30분 내 확인 가능)
- [ ] 되돌리기 쉬움(리스크 분리)
- [ ] 문서/주석/예외 처리 보강
- [ ] 성능/보안 고려
```

`.github/ISSUE_TEMPLATE/bug_report.md`

```md
---
name: "🐞 Bug Report"
about: 버그 제보
labels: bug
---

## 버그 설명

## 재현 방법

## 기대 동작

## 스크린샷/로그
```

`.github/ISSUE_TEMPLATE/feature_request.md`

```md
---
name: "🚀 Feature Request"
about: 신규 기능 제안
labels: enhancement
---

## 제안 내용

## 배경/문제

## 완료 기준
- [ ] 
- [ ] 
```

---

## 부록 E. 자주 하는 실수 & 트러블슈팅

- **직접 main에 푸시 시도** → 브랜치 보호로 차단: 작업 브랜치를 만들고 PR로 진행
- **PR 열고 나서 추가 커밋** → 동일 브랜치에 푸시하면 PR에 자동 반영
- **CI 실패** → 로컬에서 동일 명령(`./gradlew clean build`) 재현 후 수정
- **충돌(conflict)** → `git pull --rebase origin main`로 최신 반영 → 충돌 파일 수정 → `git add` → `git rebase --continue`
- **자격 증명 문제(HTTPS)** → 비밀번호 대신 **PAT** 사용 또는 **SSH** 키 설정
- **브랜치 전환 불가(로컬 변경 있음)** → `git stash`로 보관 후 전환 → `git stash pop`

---

> 이 문서로 **조직 생성 → 권한/보호 규칙 → 브랜치 전략 → PR/리뷰/머지 → CI**까지 팀 협업의 기본 흐름을 바로 적용할 수 있습니다.  
> 필요 시 팀 규칙에 맞게 PR/이슈 템플릿과 브랜치 보호 옵션을 세부 조정하세요.
