# Smart Phone Book [![Build Status](https://travis-ci.org/koda93/smartphonebook-be.svg?branch=master)](https://travis-ci.org/koda93/smartphonebook-be)

스마트 주소록 Backend Repository

## 프로젝트 목표
- 데스크탑/모바일 웹에서 접근 가능한 주소록 웹 사이트를 개발한다.
- 사용자는 구글 등 외부 계정으로 로그인이 가능하다.
- 사용자는 로그인 후 연락처를 등록/수정/삭제할 수 있다.
- 주소록 즐겨찾기/태그/검색이 가능하다.

## 프로젝트 목적
- Spring/JPA를 이용한 CRUD 데이터 처리 시스템을 구현한다.
- OAuth를 이용한 SNS 로그인을 구현한다.
- API 서버를 이용해 FE/BE 개발을 진행한다.
- RESTful API를 설계해본다.
- 테스트 주도 개발의 프로세스로 개발 프로젝트를 진행한다.
- Travis, AWS를 이용해 CI/CD 환경을 구축해본다.
- AWS를 이용해 https로 서비스를 배포해본다.

## Features
- 연락처 생성, 수정, 삭제
- 연락처에 번호, 주소, 기념일 등의 추가 정보 저장
- 각 정보의 custom 카테고리 추가/삭제
- 주소록 추가/수정 시 중복/유사 연락처 정리
- 로그인(OAuth)
- 프로필 사진 
- 연락처 즐겨찾기
- 연락처 태그(그룹)
- 연락처 검색
- 주소록 백업 Excel Export/Import

## Stack

- Spring Boot 2.1.x
- JPA 5.2.x
- Apache Tomcat
- Gradle 4.5.x
- STS
- MariaDB 10.2.x
- GitHub
- TravisCI
- AWS EC2/RDS/CodeDeploy
- OAuth 2
- SSL
- Logback 1.2.x
- Swagger 2.5.x
- JDBC
- JUnit
- Rest-Assured 3.0.x

## System Architecture

![System Arcthitecture](/images/system_architecture.jpeg)

## Related
- [spring-boot-webservice](https://github.com/koda93/spring-boot-webservice)
- [JPA Study](https://github.com/koda93/jpa-study)
- [spring-boot-file](https://github.com/koda93/spring-boot-file)
- [spring-boot-oauth](https://github.com/koda93/spring-boot-oauth)


## Members
- 공통 : 기획 및 UI 설계, DB 설계
- 고다경 : 서버 및 개발환경 구축, 웹 백엔드 개발, 서비스 배포
- 정소연 :  [웹 프론트 엔드](https://github.com/JESS2/SmartPhoneBook_FE) 개발
