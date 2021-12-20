# Study With Me (스터디 모집) 리팩토링

  
## 기술스택
|Back-End|Tools & ETC|
|:---:|:---:|
|JAVA 11|IntelliJ|
|Spring Boot 2.4.4|GitHub|
|JPA & Querydsl|AWS(EC2, RDS, S3)|
|Junit5||
|MariaDB||

## 리팩토링
### 1.1v (2021.02~2021.07)
1. 엔티티 연관관계 부여
2. 조회 쿼리 최적화 (페이징)
3. Controller 상태코드 변경
4. 테스트 코드 리팩토링 (통합 테스트 위주)

### [1.2v (2021.12~)](https://github.com/olivejua/refactoring-study_with_me/milestone/1)
1. [API 문서 작성](https://github.com/olivejua/refactoring-study_with_me/issues/11)
2. 통합테스트 리팩토링, 단위테스트
3. [S3를 이용해서 파일업로드 하기](https://github.com/olivejua/refactoring-study_with_me/issues/9)
4. [Security 리팩토링](https://github.com/olivejua/refactoring-study_with_me/issues/10)
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
|feat|새로운 기능을 추가할 경우| 
|fix|버그를 고친 경우|
|design|UI 디자인 변경|  
|!HOTFIX|급하게 치명적인 버그를 고쳐야하는 경우| 
|style|코드 포맷 변경, 세미 콜론 누락, 코드 수정이 없는 경우| 
|refactor|프로덕션 코드 리팩토링| 
|comment|필요한 주석 추가 및 변경| 
|docs|문서를 수정한 경우 Test 테스트 추가, 테스트 리팩토링(프로덕션 코드 변경 X)| 
|chore|빌드 태스트 업데이트, 패키지 매니저를 설정하는 경우(프로덕션 코드 변경 X)| 
|rename|파일 혹은 폴더명을 수정하거나 옮기는 작업만인 경우| 
|remove|파일을 삭제하는 작업만 수행한 경우|

### Footer (Optional)
|name|description|
|:----:|-----------|
|fixes|이슈 수정 중 (아직 해결되지 않은 경우)|
|resolves|이슈를 해결했을 때 사용|
|ref|참고할 이슈가 있을 때 사용|
|related to|해당 커밋에 관련된 이슈번호 (아직 해결되지 않은 경우)|

컨벤션 출처: [협업을 위한 git 커밋컨벤션 설정하기](https://overcome-the-limits.tistory.com/entry/%ED%98%91%EC%97%85-%ED%98%91%EC%97%85%EC%9D%84-%EC%9C%84%ED%95%9C-%EA%B8%B0%EB%B3%B8%EC%A0%81%EC%9D%B8-git-%EC%BB%A4%EB%B0%8B%EC%BB%A8%EB%B2%A4%EC%85%98-%EC%84%A4%EC%A0%95%ED%95%98%EA%B8%B0)
