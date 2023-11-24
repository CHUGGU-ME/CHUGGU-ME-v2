# CHUGGU-ME v2.0.0
Pure Kotlin + brew & npm

## 📂 Proejct Introduction
- brew 혹은 npm 명령어를 사용하여 콘솔 환경에서 EPL 정보를 확인 할 수 있는 오픈소스 프로젝트 입니다.
- An open source project that users can check variety EPL information by using brew or npm command.
- brewもしくはnpm命令語を入力し、 コンソール環境でEPLの情報を確認できるオープンソースプロジェクト。

## ⏲️ Development Period
- Nov.4th.2023 ~ Nov.25th.2023

## ⚙️ Development Environment
- Spring Boot : 2.7.14
    - `implementation*("org.jetbrains.kotlinx:kotlinx-cli:0.3.6")`
    - `implementation*("org.apache.commons:commons-lang3:3.13.0")`
    - `implementation*("com.microsoft.playwright:playwright:1.28.1")`
    - `implementation*("com.google.code.gson:gson:2.10.1")`
    - `implementation*("org.jetbrains.kotlinx:kotlinx-serialization-cbor:1.3.0")`
- Gradle : 7.6.1
- Test tool : JUnit5(현재 적용되어 있지 않음)
- Kotlin : 1.9.10
- JAVA : corretto 11 ver.
- VCS : Git / GitHub
- Deployment tool: brew / npm&shipjs
- IDEA : IntelliJ
- Cooperation tool : JIRA & SLACK & Gather


## 📌 우리들의 PR규칙 Our rule for PR チームPR規則
# Git Flow
<img width="698" alt="image" src="https://github.com/KATJ-HH2/katj/assets/38535971/ec400cf4-1858-41c9-9db9-3705ef46b726">

### Git Flow 한계
  > Git-Flow는 등장하고 10년 넘게 표준처럼 자리잡고, 더 나아가 마치 만병통치약처럼 사용되었다. 현재는 **Git으로 관리되는 인기있는 유형의 소프트웨어가 웹 어플리케이션으로 이동**하고 있다. **웹 어플리케이션은 일반적으로 롤백되지 않고, 지속적으로 제공(Continuous Delivery)되므로 여러 버전의 소프트웨어를 지원할 필요가 없다**.
  > 그러나 명시적으로 버전을 관리해야하는 소프트웨어를 개발한다면, 여전히 Git Flow가 적합할 수 있다.

이러한 이유가 있지만, Git Flow의 흐름 및 전체적인 구조를 파악하는데 좋은 레퍼런스가 되며, 대부분의 기업과 우리는 Git Flow가 익숙하기 때문에 `GitHub Flow` 보다는 `Git Flow` 를 사용하고 있어요.

### Git Flow 限界
  > Git-Flowは登場してから１０年以上標準として落ち着き、まさに万能薬のような感じで人々に使われてきた。今は　**Gitで管理される人気持ちの有形のソフトウィアがウェブアプリケーションへ移動している。ウェブアプリケーションは一般的にはロルバックできず、持続的に提供されるため色々なバージョンのソフトウェアを支援する必要がない
  >　しかし、名刺的にバージョンを管理すべきのソフトウェアを開発するなら、相変わらずGit Flowが適する可能性がある。

このような理由があるが、Git Flowの流れ及び全体的な仕組みを把握するための良いレファレンスになり、大多数の企業と私たちはGit Flowに慣れてるため`GitHub Flow`よりは`Git Flow`を使ってます。


## 그럼 어떻게 사용하고있나요?
각 개발 메인 브랜치는 Dev, Prod를 메인으로 두고 있어요. 이 두개의 브랜치에서 Feature 브랜치가 생성되요. 각 Feature 브랜치는 기능 기준으로 만들어집니다. 
  Ex) 사용자 등록 기능을 만들면 `feature/tony` 식으로 만들게 되죠.
- 그리고 각 feature 브랜치를 생성하고 기능 개발이 끝나면 PR( Pull Request)를 생성하여, 팀원들의 코드리뷰를 받게됩니다.
- feature 브랜치의 코드리뷰가 끝나면 Dev 브랜치로 Merge가 되고, Dev 브랜치는 Prod로 Push를 하게 되는거죠.

## では、どう使ってますか？
各開発のメインブランチはDev、 Prodに基づいてます。この二つのブランチでFeatureブランチが作られます。各Featureブランチは昨日を中心にして作られます。
  Ex) 会員登録機能を作るのなら`feature/tony`　こうゆう方式で作ればいいです。
- そして、各Featureブランチを作り、機能の開発が終わればPR(Pull Request)を要請し、チームからのコードレビューを受けてもらいます。
- featureブランチのコードレビューが終わればDevブランチにMergeされ、DevブランチはProdへPushすることになります。

## 우리들의 규칙
#### [ GitHub 템플릿으로 Issue & Feature Request 내용 규격화]
- 언제든지 생길 수 있는 다양한 버그들에 대해서 서로 알기 쉽게 알아볼 수 있도록 했어요.

## 我らの規則
#### [ GitHub テンプレートで Issue & Feature Request内容規格化]
- いつでもできる可能性のある色々なバグに対して、分かり合い易いようにしました。
```
## 버그 설명
[버그에 대한 명확하고 간결한 설명]

## 재현 단계

[버그를 재현하기 위한 단계]

## 예상 동작

[예상했던 동작과 실제 동작의 차이]


##　バグ説明
[バグに対して明確で簡潔な説明]

##　再現段階

[バグを再現するための段階]

## 予想同作

[予想していた同作とじっち実際の同作の違い]
```
- 각자의 기능 브랜치에서 작업하지만 필요에 따라서 요청시 규격화된 이슈를 남기기로 했어요.
- 各々の機能ブランチで作業するけど、必要な場合要請し規格化されたイシュっを残すことにしました。
```
## 기능 제안

[제안하는 기능에 대한 명확하고 간결한 설명]

## 예상 동작

[새로운 기능이 추가되면 어떻게 동작해야 하는지 설명]

## 추가 정보

[기능 제안에 대한 추가적인 정보나 컨텍스트]


## 機能提案

[提案する機能に対して明確で簡潔な説明]

## 予想同作

[新しい機能が追加されればどう同作するか説明]

## 追加情報

[機能提案に対する追加的な情報やコンテクスト]
```
#### [ GitHub 템플릿으로 PR내용 규격화 ]
- 우리는 중구난방의 PR 메세지 및 아주 커다란 커밋의 크기를 정하기로 했어요.
##### PR 템플릿
```
# 해결하려는 문제가 무엇인가요?
*

# 어떻게 해결했나요?
* 

## Attachment
```
#### [ GitHub テンプレートでPR内容規格化 ]
- 私たちは衆口ふさぎ難しのPRメッセージ及びとても大きいカーミットのサイズを定めることにしました。
##### PR テンプレート
```
# 解決しようとする問題はなんですか？
*

# どうやって解決できましたか？
* 

## Attachment
```

<details>
  <summary>[ Commit Prefix에 특정단어 사용 ] </summary>
  <div markdown="1">
    - commit 메세지에 Prefix 단어를 사용해서 해당 커밋의 내용을 미리 알 수 있게끔 사용하고 있어요.
    <img width="701" alt="image" src="https://github.com/KATJ-HH2/katj/assets/38535971/2c232c79-8a7c-4072-aefe-7d1e91fafbf5">
  </div>
</details>

<details>
  <summary>[ PR도 최대한 작은 단위로 쪼개서 올리기 ]</summary>
  <div markdown="1">
    - 사실 코드리뷰는 리뷰어의 입장이 제일 힘들어요. 
      본인 개발이 바쁘지만 팀원들, 우리 프로젝트의 개발 품질을 위해서 본인의 시간을 빼서 리뷰를 해주기 때문이죠. 
      그렇기 때문에 리뷰어가 리뷰를 하기 편하게 작은 단위의 Commit으로 올려 코드리뷰를 편하게 하는게 중요해요. (내가 리뷰어가 될 수 도있기 때문이죠..?) 
    <img width="675" alt="image" src="https://github.com/KATJ-HH2/katj/assets/38535971/7289159e-637c-4f7b-a70b-854146f5c509">
  </div>
</details>

<details>
  <summary>[ Merge는 적어도 하나의 Approve가 있어야해 ]</summary>
  <div markdown="1">
    - 우리는 정말.. 쉽지않은 규칙을 두었어요. 적어도 한 개의 Approve가 있어야 Merge를 할 수 있도록 설정해놓았어요. 
      이렇게 하는 이유는 리뷰어가 코드리뷰의 경각심을 깨우기 위함도 있지만, 바쁘다고 막 Merge를 하지 않기 위함을 방지하는것도 있어요.
      <img width="672" alt="image" src="https://github.com/KATJ-HH2/katj/assets/38535971/1297632f-810f-4763-a715-97e82eb1b39e">
  </div>
</details>

## 🏃Getting started in the local environment

### How to run
1. 반드시 기기에 자바와 코틀린이 설치되어 있는지 확인해주세요.
2. 이 리파지토리를 복제해 주세요:
```bash
git clone https://github.com/CHUGGU-ME/CHUGGU-ME-v2
```
### Navigate to the project directory
```bash
cd 추가 필요
```
### Build the project
```bash
추가 필요
```
### Run the application
```bash
추가 필요
```
### Open your web browser and go to [헬스체크 url](http:localhost:8080/hello)



## 📌 Feature offered
### EPL command words
- Command: $chuggume help
</br>Function: 사용 가능한 명령어와 설명을 확인할 수 있습니다.

- Command: $chuggume article
</br>Function: EPL 뉴스 번호를 입력 하여 해당 뉴스를 새창에 띄웁니다.

- Command: $chuggume news
</br>Function: EPL 뉴스 탑10의 제목, URL을 확인 합니다.

- Command: $chuggume goal
</br>Function: ascii-art를 사용한 간단한 애니메이션!

- Command: $chuggume update
</br>Function: EPL 선수 목록, 리그 성적 등의 정보를 최신으로 업데이트 합니다.

- Command: $chuggume schedule
</br>Function: EPL 경기 일정을 조회 합니다.

- Command: $chuggume player
</br>Function: EPL 선수 이름을 입력 하여 선수별 상세 정보를 조회할 수 있습니다.

- Command: $chuggume mom
</br>Function: 시즌, 경기 날짜, 해당 팀의 최고의 선수를 조회할 수 있습니다.


## 📑 Architecture



## 🤝Co-operation with JIRA
#### [ Jira를 활용하여 팀별 회의 후에도 애자일 방법론에 따라 신속한 기능 구현 및 변경을 진행하였어요 ]
![image](https://github.com/CHUGGU-ME/CHUGGU-ME-v2/assets/87371627/3f4a85eb-40b1-4adb-9cd4-1bf14c6c900c)

