# STITCH ⚽️🏀🥎

<img width="932" alt="image" src="https://user-images.githubusercontent.com/74666576/230858623-61a8b82d-8d8b-408f-a4fd-fba31b51975e.png">
STITCH는 스포츠 메이트 매칭 플랫폼 애플리케이션 입니다.

> **개발기간: 2023.02.13 ~ 2023.03.24**


## 배포 링크

> STITCH를 다운로드할 수 있는 링크입니다.
<a href="https://play.google.com/store/apps/details?id=com.seunggyu.stitch&pli=1">
  <img width="188" alt="image" src="https://user-images.githubusercontent.com/74666576/230870514-240794f8-fc71-44b2-b714-da83dfac157a.png">
</a>


## 팀 소개

|Planner 비니(손수빈)|Designer 개리(홍준표)|Server 홀튼(김형석)|Android 피오(박승규)|iOS 탈리(하늘이)|
|:-:|:-:|:-:|:-:|:-:|
|@비니|@개리|[@kim-hyeungsuk](https://github.com/kim-hyeungsuk)|[@seunggyu97](https://github.com/seunggyu97)|[@NEULiee](https://github.com/NEULiee)

<br/>

## ⚙️ 개발환경 및 라이브러리 

<p align="center">
  <img src="https://img.shields.io/badge/Kotlin-1.9.0-yellowgreen?logo=kotlin"/>
  <img src="https://img.shields.io/badge/Electric Eel | -2022.1.1-blue?logo=Android+Studio"/>
  <img src="https://img.shields.io/badge/targetSdk-33-green?logo=Android"/>
  <img src="https://img.shields.io/badge/minSdk-26-green?logo=Android"/>
</p>

 | Purpose                                                   | Library                                                   |
| ------------------------------------------------------------ | ------------------------------------------------------- |
| Background Task | Jetpack Work Manager  |
| Async Task | Flow, Coroutine |
| Local Database | referenceDataStore |
| Server Database | Amazon DynamoDB, Elastic beanstalk, Firebase Auth, Firebase RemoteConfig, Firebase Storage |
| Device | LocationServices, LocationManager |
| Presentation | Material Design2, Jetpack Navigation, ViewPager2, Lottie, Glide, NaverMap, Kizitonwose Calendar |

<br>

## 📁 프로젝트 주요 기능 

### 시작하기

> 🔑 Google 계정과 카카오 계정을 통해 로그인하면 간단한 정보를 입력한 후 STITCH를 시작할 수 있어요 ☺️

<img width="319" alt="image" src="https://user-images.githubusercontent.com/74666576/230871439-b3f4e38e-12d0-4652-9bee-304a10a7f3b7.png">

<br/>
<br/>   

> 😎 관심있는 운동을 3가지 이상 선택한 뒤 다음 화면으로 이동할 수 있어요 !

![화면_기록_2023-04-10_오후_6_17_17_AdobeExpress](https://user-images.githubusercontent.com/74666576/230873331-bd95d0dd-ac97-46cf-97cc-d9acb24005a8.gif)

<br/>
<br/>   

> 🏠 마지막으로 현재 살고 있는 동네를 검색을 통해 입력하면 끝!

> 동네는 현재 위치 기반으로 근처 동네를 조회하는 방법과 직접 입력해서 검색하는 방법이 있어요.

> 현재 위치 기반으로 조회하면, 현재 있는 위치에서 근접한 동네들이 표시되고, 직접 검색을 하면 동일한 이름의 동네들이 표시돼요!

![화면_기록_2023-04-10_오후_6_28_07_AdobeExpress](https://user-images.githubusercontent.com/74666576/230875336-230044b8-2bd3-4569-9949-10ed823b209c.gif)

<br/>
<br/>   

### 각 탭 설명

> 👍 홈 화면 탭에서 추천하는 Teach매치들과 새롭게 열린 매치들을 확인할 수 있어요 !

![화면_기록_2023-04-10_오후_6_35_58_AdobeExpress](https://user-images.githubusercontent.com/74666576/230876497-05cc1649-5028-4970-b08a-40394b827d5e.gif)

<br/>
<br/>   

> 📚 카테고리 탭에서는 모든 매치를 종목별로 분류하여 확인할 수 있어요 ~

![화면_기록_2023-04-10_오후_6_43_35_AdobeExpress](https://user-images.githubusercontent.com/74666576/230877879-9c05a915-9a95-46bc-81ea-734f060dfa62.gif)

<br/>
<br/>   

> 👋 마이매치 탭에서는 현재 참여중인 매치들을 볼 수 있어요 !

![화면_기록_2023-04-10_오후_6_48_13_AdobeExpress](https://user-images.githubusercontent.com/74666576/230878430-f5e45e14-8f49-4872-b68d-0dfc6c92ac30.gif)

<br/>
<br/>  

> 😊 마이메뉴 탭에서는 내가 개설한 매치들을 확인 할 수 있고 프로필/앱 설정을 할 수 있어요 !

![화면_기록_2023-04-10_오후_6_53_08_AdobeExpress](https://user-images.githubusercontent.com/74666576/230879370-8b9d9b6f-802c-4472-8c11-1fedc62477bb.gif)

<br/>
<br/>

### 매치 개설하기

> 🤼‍♀️ 새로운 매치를 개설하려면 + 모양의 Floating Button을 클릭하면 개설 화면으로 넘어가요.

> 개설하려는 매치의 종류를 선택한 뒤, 운동 종목을 선택해주세요 !

![화면_기록_2023-04-10_오후_7_01_03_AdobeExpress](https://user-images.githubusercontent.com/74666576/230881071-86119b48-5a3c-4797-9427-18af35ca12af.gif)

<br/>
<br/>

> 📝 이후 상세 내용들을 적어주세요.

> 매치를 잘 표현하는 사진을 첨부할 수 있고(선택), 매치 제목과 내용을 상세히 작성해주세요!

![화면_기록_2023-04-10_오후_7_09_44_AdobeExpress](https://user-images.githubusercontent.com/74666576/230881969-61994df4-ab39-488b-af33-d9d7795c394e.gif)

<br/>
<br/>

> 📝 매치가 진행될 날짜와 시간을 입력해주세요.

> 플레이 시간은 최소 30분 부터 최대 5시간 까지 30분 단위로 설정 가능해요 !

![화면_기록_2023-04-10_오후_7_12_56_AdobeExpress](https://user-images.githubusercontent.com/74666576/230882477-50229401-23d6-4c7d-af55-b9d2682bf0b7.gif)

<br/>
<br/>

> 📝 매치가 진행될 장소를 설정해주세요.

> 매치 장소는 종합운동장과 같이 장소명으로도 검색할 수 있어요.

> 또는 지도를 움직여서 주소를 설정할 수도 있답니다 😊

![화면_기록_2023-04-10_오후_7_16_49_AdobeExpress](https://user-images.githubusercontent.com/74666576/230883361-0e8cd1e1-e9cf-4cf1-b9f7-57e4c0c0f6fa.gif)

<br/>
<br/>

> 📝 마지막으로 참가인원과 참가비가 있다면 금액을 입력하면 매치 개설 끝 ! ⭐️

![화면_기록_2023-04-10_오후_7_20_22_AdobeExpress](https://user-images.githubusercontent.com/74666576/230884221-92a08c43-3d5d-49c8-8019-ef49dd2dd8fc.gif)

<br/>
<br/>



