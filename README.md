# LYNK Insurance
![header](https://capsule-render.vercel.app/api?type=waving&height=180&text=LYNK&fontAlign50&backgroundColor=333333&fontWeight=bold&fontColor=BBE8A7)

### ⌚ 프로젝트 기간 : 2024.11.22 ~ 2025.01.03
<br><br>

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
- 고객/계약/상품 관리
- 만기계약 고객 필터링 조회
- 계약 상세 페이지 
- 실시간 영업 실적 현황판 (금액 & 건수)

<br/>
<hr/>

## 📌 프로젝트 문서
📒[노션](https://www.notion.so/ohgiraffers/LYNK-insurance-41442a14f58b4933b2521f1ad1b6b12c)
🎉[피그마](https://www.figma.com/design/Q4jZS9WpOBIHCMfMs3R3Tb/LYNK?node-id=0-1&p=f&t=QuIOA9sE0bmWyK9q-0)


<br>

<br/>
<hr/>


<a name = "기술-스택"></a>
## 🧰 기술 스택
- Java 17, Spring Boot, MyBatis
- MySQL
- HTML/CSS, Bootstrap, JavaScript
- Chart.js


<br/>
<hr/>

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
## 📌 물리 데이터 모델
![image](https://github.com/user-attachments/assets/f8f6b537-4e9b-44d1-817d-df0fff28eca1)
<br><br>

<br/>
<hr/>
<hr/>

<br><br>


