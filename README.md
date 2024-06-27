# hhplus-tdd-week02
항해 플러스 백앤드5기 2주차


## [Chapter 1-2] 클린아키텍처


### ERD
![image](https://github.com/jangyoojeong/hhplus-tdd-week02/assets/55098858/f46bf5cf-43f4-442e-9ce3-2f0f31a85a1a)
1. lecture > 특강 정보 테이블
   * 특강에 대한 정보 관리
   
lecture_schedule > 일자별 특강 정보 테이블
   * 날짜별 특강 정보 관리 (+ 특강에 대한 수강 정원 관리)

registration > 특강 신청 테이블
   * 특강 신청자 목록 관리


### 과제
[ Description ]
* '특강 신청 서비스' 구현
* 항해 플러스 토요일 특강을 신청할 수 있는 서비스 개발
* 특강 신청 및 신청자 목록 관리를 RDBMS를 이용해 관리할 방법 고민

[ Requirements ]
* 아래 2가지 API 구현
  > 특강 신청 API
  > 특강 신청 여부 조회 API
* 각 기능 및 제약 사항에 대해 단위 테스트를 반드시 하나 이상 작성
* 다수의 인스턴스로 어플리케이션이 동작하더라도 기능에 문제가 없도록 작성
* 동시성 이슈를 고려하여 구현

[ API Specs ]

1️⃣ (핵심) 특강 신청 API `POST /lectures/apply`
* 특정 userId 로 선착순으로 제공되는 특강을 신청하는 API를 작성합니다.
* 동일한 신청자는 한 번의 수강 신청만 성공할 수 있습니다.
* 각 강의는 선착순 30명만 신청 가능합니다.
* 이미 신청자가 30명이 초과되면 이후 신청자는 요청을 실패합니다.
* 어떤 유저가 특강을 신청했는지 히스토리를 저장해야 한다.

2️⃣ (기본) 특강 목록 API `GET /lectures`
* 단 한번의 특강을 위한 것이 아닌 날짜별로 특강이 존재할 수 있는 범용적인 서비스로 변화시켜 봅니다.
* 이를 수용하기 위해, 특강 엔티티의 경우 기본 과제 SPEC 을 만족하는 설계에서 변경되어야 할 수 있습니다.
  > 수강신청 API 요청 및 응답 또한 이를 잘 수용할 수 있는 구조로 변경되어야 할 것입니다.
* 특강의 정원은 30명으로 고정이며, 사용자는 각 특강에 신청하기 전 목록을 조회해볼 수 있어야 합니다.
  > 추가로 정원이 특강마다 다르다면 어떻게 처리할것인가..? 고민해 보셔라 ~

3️⃣ (기본) 특강 신청 완료 여부 조회 API `GET /lectures/application/{userId}`
* 특정 userId 로 특강 신청 완료 여부를 조회하는 API 를 작성합니다.
* 특강 신청에 성공한 사용자는 성공했음을, 특강 등록자 명단에 없는 사용자는 실패했음을 반환합니다. (true, false)
  
💡 **KEY POINT**
* 정확하게 30명의 사용자에게만 특강을 제공할 방법을 고민해 봅니다.
* 같은 사용자에게 여러 번의 특강 슬롯이 제공되지 않도록 제한할 방법을 고민해 봅니다.


### 과제 평가 기준
[ Step 3 ]
* ERD 작성의 당워성 여부
* 아래 2가지 API 구현 여부
  > 특강 신청 API
  > 특강 신청 여부 조회 API
* 적절한 형태의 추상화를 통해 DB 의존성과 비즈니스 로직을 격리하였는지
  > Domain Model 패턴 ( JPA , TypeORM ) 의 형태로 작성한 경우, Application Layer 와 같이 비즈니스 로직이 적절히 보호되는 형태의 아키텍처를 적용하였는지
  > 도메인과 DB 를 분리하는 형태의 아키텍처로 작성한 경우, 도메인 로직이 Datasource Layer 와 같은 외부 의존 계층으로부터 보호되는 형태의 아키텍처를 적용하였는지

[ Step 4 ]
* 동시성 이슈에 대해 고려되었는지
  > DB 락 ( 낙관, 비관 ) / 분산 락 등과 같이 다수의 서버 인스턴스 환경에서도 동시성 문제가 제어 가능하도록 비즈니스 로직을 구성하였는지
* 확장 가능한 엔티티 구조를 고려하였는지
  > 특강 테이블, 신청 히스토리 테이블에 대한 구조
  > 적절히 각 도메인에 대한 서비스의 책임이 분리되었는지


### Schedule
* 6.25 ( 화요일 ) - Step 3
  - ERD 작성
  - 특강 신청 API
  - 특강 목록 조회 API
  - 특강 신청 완료 여부 조회 API
* 6.28 ( 금요일 ) - Step 4
  - 강의와 각 강의별 날짜가 추가 가능한 구조의 DB 가 구현되었는지
  - (동시성) 각 강의 별로 최대 30명까지만 정상적으로 요청되도록 기능 구현



#### Clean + Layered Architecture ?
![image](https://github.com/jangyoojeong/hhplus-tdd-week02/assets/55098858/bac6c895-72be-459b-bc39-a57a7850a9af)

* 애플리케이션의 핵심은 비즈니스 로직
* 데이터 계층 및 API 계층이 비즈니스 로직을 의존 ( 비즈니스의 Interface 활용 )
* 도메인 중심적인 계층 아키텍처
* Presentation 은 도메인을 API로 서빙, DataSource 는 도메인이 필요로 하는 기능을 서빙
* DIP 🆗 OCP 🆗



#### 레이어드 아키텍처의 대원칙
* 단방향, 하위참조
------------------------------------
interface
 > req, res
------------------------------------
domain
 > domain, domainService, domainRepository
------------------------------------
infra
 > domainRepositoryImpl, entity
------------------------------------



#### 동시성 제어 ?
* 낙관적락 > 충돌이 많이 발생하지 않을걸로 예상될때 주로 사용 ! (낙관적으로 생각)
          > 충돌이 발생하면 밴시킴으로 공정성이 없음. (충돌 발생하면 다시 배틀)
* 비관적락 > 층돌이 많이 발생할 것으로 예상될때 주로 사용 ! (비관적으로 생각)
          > 충돌이 발생하면 순서대로 영원히 대기하므로 공정성이 있음.
