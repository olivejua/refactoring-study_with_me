# Study With Me (스터디 모집)
 
## 주제
나에게 맞는 스터디를 조건으로 검색하여 찾는 웹서비스

## 구현 목표
- 유저
    - 복잡한 회원가입 대신 간단한 소셜로그인을 통해 로그인.
    - 신규회원일 경우, 사이트에서 이용할 닉네임만 설정하면 회원가입 완료.
    - 이름은 원하면 언제든지 변경 가능.

- 스터디 모집 게시판
    - 참여하고 싶은 관련 기술스택, 스터디 모임 장소 등으로 검색 가능.
    - 원하는 스터디는 댓글을 통해 참여 의사를 밝힘.

- 장소 추천 게시판
    - 해당 장소의 썸네일과 지역, 추천, 비추천 수로 한눈에 볼 수 있음.
    - 1명의 User당 1회의 추천, 비추천을 누를 수 있음.
  
  
## 기술스택
|Back-End|Front-End|Tools & ETC|
|:---:|:---:|:---:|
|JAVA 11|HTML|IntelliJ|
|Spring Boot 2.4.4|Thymeleaf|GitHub|
|JPA & hibernate|AJAX|AWS|
|Junit|JavaScript||
|MariaDB|Bootstrap||
  


## 기능별 구동 화면

1. [유저](/docs/function/user.md)
    - 회원가입 / 로그인
2. 게시판
    1. [스터디 모집](/docs/function/study_recruitment.md)
    2. [장소 추천](/docs/function/place_recommendation.md)
    3. [질문](/docs/function/question.md)
3. 웹 구축
    - [웹 사이트 구경하기](http://ec2-15-164-33-81.ap-northeast-2.compute.amazonaws.com:8080/)

## 리팩토링
### 1.1v (2021.02~2021.07)
1. 엔티티 연관관계 부여
2. 조회 쿼리 최적화 (페이징)
3. Controller 상태코드 변경
4. 테스트 코드 리팩토링 (통합 테스트 위주)

### 1.2v (2021.12~)
1. API 문서 작성
2. 테스트 리팩토링, 단위테스트, 실패케이스도 같이
3. S3를 이용해서 파일업로드 하기
4. Security 리팩토링
5. HATEOAS 도입
6. Commit 컨벤션 정하기


## 커밋 컨벤션
```
type: [#issue number] Subject
(한줄 띄움)
body
(한줄 띄움)
footer
```
### Type
|name|description|
|:----:|-----------|
|Feat|새로운 기능을 추가할 경우| 
|Fix|버그를 고친 경우|
|Design|UI 디자인 변경|  
|!HOTFIX|급하게 치명적인 버그를 고쳐야하는 경우| 
|Style|코드 포맷 변경, 세미 콜론 누락, 코드 수정이 없는 경우| 
|Refactor|프로덕션 코드 리팩토링| 
|Comment|필요한 주석 추가 및 변경| 
|Docs|문서를 수정한 경우 Test 테스트 추가, 테스트 리팩토링(프로덕션 코드 변경 X)| 
|Chore|빌드 태스트 업데이트, 패키지 매니저를 설정하는 경우(프로덕션 코드 변경 X)| 
|Rename|파일 혹은 폴더명을 수정하거나 옮기는 작업만인 경우| 
|Remove|파일을 삭제하는 작업만 수행한 경우|

### Footer (Optional)
|name|description|
|:----:|-----------|
|Fixes|이슈 수정 중 (아직 해결되지 않은 경우)|
|Resolves|이슈를 해결했을 때 사용|
|Ref|참고할 이슈가 있을 때 사용|
|Related to|해당 커밋에 관련된 이슈번호 (아직 해결되지 않은 경우)|

컨벤션 출처: [협업을 위한 git 커밋컨벤션 설정하기](https://overcome-the-limits.tistory.com/entry/%ED%98%91%EC%97%85-%ED%98%91%EC%97%85%EC%9D%84-%EC%9C%84%ED%95%9C-%EA%B8%B0%EB%B3%B8%EC%A0%81%EC%9D%B8-git-%EC%BB%A4%EB%B0%8B%EC%BB%A8%EB%B2%A4%EC%85%98-%EC%84%A4%EC%A0%95%ED%95%98%EA%B8%B0)