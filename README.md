# Study With Me (스터디 모집)
 
## 주제
나에게 맞는 스터디를 조건으로 검색하여 찾는 웹서비스
 
## 목적

 
  
## 기술스택
### Back-End
- JAVA 8버전
- Spring Boot 2.4.4버전
- JPA
- Querydsl
- Spring Security
- Junit Test
- MariaDB
### Front-End
- HTML
- Thymeleaf
- AJAX
- JavaScript
- JQuery
- Bootstrap
### Tools & ETC
- IntelliJ
- GitHub
- AWS
- [편집 API] Naver Editor
- [지도 API] Kakao Map
- [주소 API] postcodify
  

## 구현 목표
- 유저
    - 복잡한 회원가입 대신 간단한 소셜로그인을 통해 로그인.
    - 신규회원일 경우, 사이트에서 이용할 닉네임만 설정하면 회원가입 완료.
    - 이름은 변경 가능.

- 스터디 모집 게시판
    - 참여하고 싶은 관련 기술스택을 검색할 수 있다.
    - 참여하고 싶은 스터디 모임 장소를 검색할 수 있다.
    - 



- 구동 화면
# 기능
1. [회원가입/로그인](/docs/function/user/userPage.md)
2. 게시판
    1. 스터디 모집
    2. 장소 추천
    3. 질문
3. 웹 구축


# 리팩토링
1. [Querydsl을 활용한 select 쿼리 최적화](/docs/refactoring/aboutQuerydsl/updateQueries.md)
2. 엔티티 연관관계 보완
3. 1메소드당 최소 1테스트케이스 작성
