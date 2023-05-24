# ☀ IPS 도드니 안드로이드
**장애 아동 부모를 위한 네트워킹 모바일 서비스**<br/>
<br/><br/>

## 🛠 기술 스택
> **Android** : JAVA 11, AndroidStuio, Firebase Cloud Messaging<br/>

<br/><br/>

## 💻 안드로이드 코드 실행
### ※안드로이드 스튜디오 실행 전 플라스크 서버를 먼저 돌리고 안드로이드 스튜디오를 실행해야 합니다※
### ※아래 코드 수정을 필수로 해주세요※
<br/>

**1. LandingActivity.java**

```bash
네이버 로그인 api에서 새로운 사용자는 관리자가 네이버측에 등록해야하기 때문에 저희 도드니 팀원 중 한명의 아이디로 접속하는 방법입니다
1. 55번째 줄 & 83번째 줄을 주석처리해주세요!
2. 84~87줄(else문)을 주석처리해주세요!
3. 57번째 줄에 localUid를 1로 바꿔주세요!
```

**2. HuefeelAPI.java**

```bash
플라스크 서버와 로컬접속을 하기 위해 ip주소와 포트번호를 설정해야합니다.
1. 9번째 줄의 URL을 현재 로컬 컴퓨터의 로컬ip주소로 변경해주시고, 플라스크 서버에 설정한 포트번호로 수정해주세요
ex) 현재 로컬 컴퓨터의 ip주소값이 172.20.20.144 이고 플라스크에 설정한 포트번호가 5000번이라면 URL = "http://172.20.20.144:5000"
```

**3. 카카오 API**
1. Kakao Developers (<a ref="https://developers.kakao.com/console/app">https://developers.kakao.com/console/app</a>)에 접속해주세요.
2. 로그인을 하고, 전체 애플리케이션에서 '애플리케이션 추가하기'를 선택해주세요.
3. 앱 이름 및 사업자명에 '도드니'를 적고 체크박스를 선택한 뒤 저장을 클릭해주세요.
4. 만들어진 애플리케이션을 클리하고 <strong>REST API 키</strong>를 복사해주세요
5. Dodeuni_Android\app\src\main\java\dodeunifront\dodeuni\retroifit 에서 <strong>KakaoRetrofitBuilder.java</strong>를 찾아주세요.

**4. KakaoRetrofitBuilder.java**

```bash
카카오 API 키를 변경해야합니다.
1. 복사한 REST API 키를 13번째 줄의 key변수에 대입해주세요.
```

**5. 해시키 찾기**
```bash
해시값을 구해야합니다.
1. 복사한 REST API 키를 13번째 줄의 key변수에 대입해주세요.
```

**6. 플랫폼 설정하기**
1. (3번과 같은 사이트입니다.) 도드니 애플리케이션에서 '플랫폼 설정하기'를 선택해주세요.
2. 'Android 플랫폼 등록'을 선택해주세요.
3. 
<br/>


<br/><br/>

## 👩🏻‍💻 도드니 팀 안드로이드 개발자 
| 김예은 | 서묘진 |
| :-: | :-: |
| [@kimyenida](https://github.com/kimyenida) | [@nrj022](https://github.com/nrj022) |
|<img src="https://github.com/kimyenida.png" style="width:150px; height:150px;">|<img src="https://github.com/nrj022.png" style="width:150px; height:150px;">|
