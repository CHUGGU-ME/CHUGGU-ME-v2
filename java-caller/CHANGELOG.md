## [1.1.14](https://github.com/CHUGGU-ME/CHUGGU-ME-v2/compare/v1.1.13...v1.1.14) (2023-11-24)



## [1.1.13](https://github.com/CHUGGU-ME/CHUGGU-ME-v2/compare/v1.1.12...v1.1.13) (2023-11-24)
모든 shedule을 출력하기 위한 for문 내부의 break 삭제
https://www.premierleague.com/fixtures navigate시 wait 기준 수정
 - 기존: 무조건 10초 대기
 - 변경: schedule을 구성하기 위한 element querySelect가 모두 null이 아닐 때까지 1초마다 wait


## [1.1.12](https://github.com/CHUGGU-ME/CHUGGU-ME-v2/compare/v1.1.11...v1.1.12) (2023-11-24)
명령어 추가: help, author


## [1.1.10](https://github.com/CHUGGU-ME/CHUGGU-ME-v2/compare/v1.1.9...v1.1.10) (2023-11-24)
update 명령시 발생하는 오류 수정 page.waitForLoadState(LoadState.NETWORKIDLE) -> page.waitForTimeout(~)


## [1.1.9](https://github.com/CHUGGU-ME/CHUGGU-ME-v2/compare/v1.1.8...v1.1.9) (2023-11-24)
player 명령어 오류 수정
goal 명령어 애니메이션으로 나오도록 수정
크롤링시에 웹 브라우저 보이지 않도록 수정


## [1.1.8](https://github.com/CHUGGU-ME/CHUGGU-ME-v2/compare/v1.1.7...v1.1.8) (2023-11-24)
배포 시 의존성 관리를 위해 package-lock.json -> npm-shrinkwrap.json으로 변경
update 명령어 수행 시 쿠키 허용 및 광고 닫는 로직 첫 번째 서비스에서만 수행하도록 변경
jarPath 재설정: rootPath를 __dirname으로 지정


## [1.1.7](https://github.com/CHUGGU-ME/CHUGGU-ME-v2/compare/v1.1.6...v1.1.7) (2023-11-23)
jar file 경로 지정 


## [1.1.6](https://github.com/CHUGGU-ME/CHUGGU-ME-v2/compare/v1.1.5...v1.1.6) (2023-11-22)
npx에서 사용할 수 있도록 수정


## [1.1.5](https://github.com/CHUGGU-ME/CHUGGU-ME-v2/compare/v1.1.4...v1.1.5) (2023-11-22)
chuggume 명령어 실행할 수 있도록 package.json에 실행 파일 위치 명시


## [1.1.4](https://github.com/CHUGGU-ME/CHUGGU-ME-v2/compare/v1.1.3...v1.1.4) (2023-11-22)
CI 스크립트에서 jar파일 경로 명시


## [1.1.3](https://github.com/CHUGGU-ME/CHUGGU-ME-v2/compare/v1.1.2...v1.1.3) (2023-11-22)
CI 스크립트에서 test 제외 옵션 추가


## [1.1.2](https://github.com/CHUGGU-ME/CHUGGU-ME-v2/compare/v1.1.1...v1.1.2) (2023-11-22)
실행 가능한 버전 배포
기능: 
 - goal: 골 ASCII 애니메이터
 - article: news로 조회한 뉴스 중 하나 선택시 해당 기사 팝업 
 - news: 10개 뉴스의 타이틀 제공
 - player {playerName}: 선수 프로필, 득점, assist 제공
 - schedule: 경기 일정 제공


## [1.1.1](https://github.com/CHUGGU-ME/CHUGGU-ME-v2/compare/v1.1.0...v1.1.1) (2023-11-20)
마이너 버전 업데이트



