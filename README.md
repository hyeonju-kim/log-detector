# log-detector — 에러 로그 수집·분석 대시보드

> 서비스 로그 파일에서 에러만 골라 DB에 쌓고, 사용자·시간대·에러 유형별로 조회하는 내부 관리 도구 (팀 프로젝트 "윈터푸디스" 부속, 2023.07)

![Java](https://img.shields.io/badge/Java-007396?style=flat&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=flat&logo=springboot&logoColor=white)
![Spring Data JPA](https://img.shields.io/badge/Spring_Data_JPA-6DB33F?style=flat&logo=spring&logoColor=white)
![H2](https://img.shields.io/badge/H2-004088?style=flat)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-005F0F?style=flat&logo=thymeleaf&logoColor=white)

## 왜 만들었나

운영 중 에러가 나면 로그 파일을 열어 직접 검색해야 했습니다.
"어떤 사용자에게, 언제, 어떤 에러가 가장 많이 나는지"를 한눈에 보기 위해 로그를 구조화해 쌓는 도구를 만들었습니다.

## 동작 방식

```
서비스 로그 파일 ──(10초마다 스케줄러)──▶ ERROR 라인 파싱 ──▶ DB 저장 ──▶ 조회 화면
```

- **수집**: `@Scheduled`로 10초마다 로그 파일을 읽어 `ERROR` 라인만 추출
- **파싱**: `[ 고객 id ] | [ 에러 유형 ] | [ 에러 시간 ] | [ 에러메시지 ]` 형식의 로그를 필드별로 나눠 엔티티로 저장
- **조회 화면**

| 화면 | 내용 |
|---|---|
| 전체 / 최신순 | 수집된 에러 로그 전체, 발생 시각 역순 |
| 시간대 범위 | `HOUR(errorTime) BETWEEN :start AND :end` |
| 사용자별 | 특정 고객 ID의 에러 이력 |
| 가장 많은 에러 | 에러 유형별 `GROUP BY` → 발생 횟수 순 |
| 가장 잦은 시간대 | 시간대별 에러 건수 집계 |

## 배운 점

- 로그를 사람이 읽는 텍스트가 아니라 **조회 가능한 데이터**로 바꾸면 운영 대응 속도가 달라진다는 걸 경험했습니다.
- 이 경험은 이후 실무에서 Prometheus·Grafana 모니터링과 DB 이상 탐지 리포트를 만드는 일로 이어졌습니다.

## 실행

```bash
./mvnw spring-boot:run   # http://localhost:8080/log
```
