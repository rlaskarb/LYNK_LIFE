# ⛱️ LYNK Insurance

<p align="center">
<img src="https://github.com/user-attachments/assets/90084dab-0f6b-4e53-80ff-6eef8a848f14" alt="보험 이미지" style="width: 100%;" />
  <br/>
  <em>고객을 놓치지 않기 위해, 설계사를 지키는 그룹웨어의 가치</em>
</p>



### ⌚ 프로젝트 기간 : 2024.11.22 ~ 2025.01.03
<br/><br/>

## 🧭 빠른 이동

- [프로젝트 소개](#프로젝트-소개)
- [기술 스택](#기술-스택)
- [기능 설명](#기능-설명)
- [논리 & 물리 데이터 모델링](#논리--물리-데이터-모델링)


<br/>
<hr/>

<a name = "프로젝트-소개"></a>
## 📌  프로젝트 소개 
보험회사를 위한 그룹웨어 시스템을 개발하였으며, 주요 기능으로는 인사관리, 전자결재, DB관리, 계약관리 등을 포함하고 있습니다.  
업무 효율성과 관리 편의성을 높이기 위해 직원 관리, 문서 승인 자동화, 데이터베이스 최적화, 계약 정보의 체계적 관리에 초점을 맞춰 개발하였습니다.

이 프로젝트에서 저는 **DB관리와 계약관리 기능을 담당하여**,  
고객 등록, 계약 등록, 상품 등록, 계약 조회 등의 기능을 구현하였습니다.  
작업자가 실수 없이 효율적으로 사용할 수 있도록, **실사용자의 관점에서 인터페이스와 기능 흐름에 집중하여 개발**하였습니다.
<br>

### ⚙️ 주요 기능
#### ✅ 실시간 영업 실적 현황판
 설계사가 계약을 체결하면 월별 실적에 자동 반영되며,
 다른 설계사들과의 비교를 통해 동기부여와 성취감을 유도할 수 있도록 구성했습니다.

#### ✅ 만기 도래 고객 관리
계약 만기 고객을 월별로 필터링하여 손쉽게 조회할 수 있도록 하여,
고객을 놓치지 않고 효율적으로 관리할 수 있게 만들었습니다.

#### ✅ 입력 실수 방지를 위한 자동화 처리
타이핑 한 글자의 실수가 중요한 데이터 변동으로 이어질 수 있어,
설계사, 고객, 상품 정보를 자동으로 불러오는 방식을 적용하여
사용자의 실수를 최소화하고 업무 효율성을 높였습니다.

📒[노션](https://www.notion.so/ohgiraffers/LYNK-insurance-41442a14f58b4933b2521f1ad1b6b12c)
🎉[피그마](https://www.figma.com/design/Q4jZS9WpOBIHCMfMs3R3Tb/LYNK?node-id=0-1&p=f&t=QuIOA9sE0bmWyK9q-0)


<br/>
<hr/>



<a name = "기술-스택"></a>
## 🧰 기술 스택


### 🧠 Backend

| 구분             | 사용 기술 |
|------------------|-----------|
| **프레임워크 & 언어** | Java 17, Spring Boot |
| **ORM & DB**        | MyBatis, JDBC |
| **DBMS**            | MySQL |
| **템플릿 엔진**     | Thymeleaf |
| **API 설계**        | RESTful API |
| **입력 검증**       | Spring Validation |
| **로깅 / AOP**      | Lombok (@Slf4j) |
| **배포 환경**       | 로컬 환경 (개인 개발 기준) |
| **기타**            | ojdbc8 / ojdbc11 드라이버, DevTools |

<hr/>

### 🎨 Frontend

| 구분             | 사용 기술 |
|------------------|-----------|
| **기본 기술**       | HTML5, CSS3, JavaScript |
| **UI 프레임워크**   | Bootstrap |
| **시각화 도구**     | Chart.js |
| **모달 처리**       | Bootstrap Modal |
| **스타일링**       | Flexbox 기반 레이아웃, 반응형 일부 대응 |

<hr/>

### 🛠 기타

| 구분             | 사용 기술 |
|------------------|-----------|
| **버전 관리**       | Git, GitHub |
| **개발 방식**       | MVC 구조 기반 설계 |
| **요소 연동**       | 고객/상품/설계사 정보 연동 기능 |
| **성과 시각화**     | 실적 현황판 (금액/계약 건수 실시간 반영) |


<br/>
<hr/>
<br/>

<a name="기능-설명"></a>
## 📌 기능 설명 

### ✅ 만기도래 고객 조회
![만기도래고객](https://github.com/user-attachments/assets/f728813d-03a3-44cb-ac71-520392831097)

홈 화면에서 월별로 만기 도래 고객을 한눈에 확인할 수 있습니다.  
각 고객의 보험사, 상품명, 계약자, 만기일 정보를 카드 형태로 보여주며,  
관리자는 이를 통해 중요한 계약 일정들을 놓치지 않고 빠르게 확인할 수 있습니다.


<br/>
<hr/>

### ✅ 계약 등록

![계약등록](https://github.com/user-attachments/assets/c66060b3-a93c-47b9-8061-1d6b2e305aa6)

보험 계약을 등록하는 기능입니다.  
사전에 등록된 **상품 정보, 고객 정보, 설계사 정보**를 불러와  
작업자가 직접 입력하는 실수를 최소화하고,  
등록된 계약은 **홈 화면 실적 현황판에 실시간으로 반영**되어  
**계약 금액과 계약 건수**를 바로 확인할 수 있습니다.

<br/>
<hr/>

### ✅ 고객등록
![고객등록](https://github.com/user-attachments/assets/1473b3d8-ce41-4add-abde-68f544987cc3)

신규 고객을 등록하는 화면입니다.
고객명, 주민번호, 연락처, 주소 등 기본 정보를 입력하면 리스트에 추가됩니다.

<br/>
<hr/>

### ✅ 상품 등록
![상품등록](https://github.com/user-attachments/assets/e6a97468-038a-4b86-9401-91aabfb765ca)

보험회사별 상품을 등록하는 화면입니다.  
상품 카테고리에 따라 자동 분류되며, 계약 시 선택할 수 있도록 연동됩니다.

<br/>
<hr/>

### ✅ 계약조회
![계약조회](https://github.com/user-attachments/assets/ec1f11ae-50c8-449d-954b-3d554d11e9d6)

등록된 계약 목록을 확인할 수 있는 화면입니다.  
계약번호를 클릭하면 상세 정보를 확인할 수 있도록 연결되어 있습니다.

<br/>
<hr/>

<a name="논리--물리-데이터-모델링"></a>
## 📌 논리 데이터 모델링 ( 총 20개 테이블 )
![image](https://github.com/user-attachments/assets/f8f6b537-4e9b-44d1-817d-df0fff28eca1)
<br><br>

<br/>
<hr/>


<br><br>


