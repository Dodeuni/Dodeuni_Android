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

**LandingActivity.java**

```bash
네이버 로그인 api에서 새로운 사용자는 관리자가 네이버측에 등록해야하기 때문에 저희 도드니 팀원 중 한명의 아이디로 접속하는 방법입니다
1. 55번째 줄 & 83번째 줄을 주석처리해주세요!
2. 84~87줄(else문)을 주석처리해주세요!
3. 57번째 줄에 localUid를 1로 바꿔주세요!
```

**HuefeelAPI.java**

```bash
플라스크 서버와 로컬접속을 하기 위해 ip주소와 포트번호를 설정해야합니다.
1. 9번째 줄의 URL을 현재 로컬 컴퓨터의 로컬ip주소로 변경해주시고, 플라스크 서버에 설정한 포트번호로 수정해주세요
ex) 현재 로컬 컴퓨터의 ip주소값이 172.20.20.144 이고 플라스크에 설정한 포트번호가 5000번이라면 URL = "http://172.20.20.144:5000"
```
<br/>


<br/><br/>

## 👩🏻‍💻 도드니 팀 안드로이드 개발자 
| 김예은 | 서묘진 |
| :-: | :-: |
| [@kimyenida](https://github.com/kimyenida) | [@nrj022](https://github.com/nrj022) |
|<img src="https://github.com/kimyenida.png" style="width:150px; height:150px;">|<img src="https://github.com/nrj022.png" style="width:150px; height:150px;">|
