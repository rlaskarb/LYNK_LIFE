package com.semi.lynk.function.db_management.controller;

import com.semi.lynk.function.db_management.model.dto.*;
import com.semi.lynk.function.db_management.service.DbService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation .*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;


@Controller
@RequestMapping("/db")
public class DbController {

    private final DbService dbService;


    @Autowired
    public DbController(DbService dbService) {
        this.dbService = dbService;
    }


    //    상품등록 구성 (손해보험사,생명회사)
    @GetMapping("/list")
    public String InsuranceCompany() {
        return "function/db_management/list";
    }

//========================================================================================================================

    //메리츠
    @GetMapping("/meritz")
    public String loadMeritzPage() {
        return "function/db_management/meritz";
    }
    //현대해상
    @GetMapping("/hyundai")
    public String loadHyundaiPage() {
        return "function/db_management/hyundai";
    }
    //한화손해보험
    @GetMapping("/hanwha")
    public String loadHanwhaPage() {
        return "function/db_management/hanwha";
    }
    //삼성화재
    @GetMapping("/samsung")
    public String loadsamsungPage() {
        return "function/db_management/samsung";
    }
    //DB손해보험
    @GetMapping("/dbins")
    public String loadDbinsPage() {
        return "function/db_management/dbins";
    }
    //MetLife
    @GetMapping("/metlife")
    public String loadMetLifePage() {
        return "function/db_management/metlife";
    }
    //한화생명
    @GetMapping("/hanwhalife")
    public String loadhanwhalifePage() {
        return "function/db_management/hanwhalife";
    }
    //SinhanLife
    @GetMapping("/shinhan")
    public String loadshinhanPage() {
        return "function/db_management/shinhan";
    }
    //흥국생명
    @GetMapping("/heungkuk")
    public String loadheungkukPage() {
        return "function/db_management/heungkuk";
    }
    //라이나생명
    @GetMapping("/lina")
    public String loadlinaPage() {
        return "function/db_management/lina";
    }

    // 공통 상품 등록
    @PostMapping("/{company}")
    public ResponseEntity<String> registerProduct(@PathVariable String company, @RequestBody ProductManageDTO productManageDTO) {
        dbService.insuranceRegistration(productManageDTO);
        return ResponseEntity.ok("Product registered successfully");
    }

    // 공통 상품 조회
    @GetMapping("/{company}/products")
    @ResponseBody
    public ResponseEntity<List<ProductManageDTO>> getProductsByCompany(@PathVariable String company) {
        List<ProductManageDTO> productManage = dbService.getProductsByCompany(company);
        return ResponseEntity.ok(productManage);
    }

    // 공통 상품 삭제
    @DeleteMapping("/{company}/{productNo}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String company, @PathVariable String productNo) {
        dbService.deleteProductByCompany(company, productNo);
        return ResponseEntity.ok().build();
    }

//====================================================================================================================

    // 고객 등록 페이지
    @GetMapping("/customer")
    public String customer(Model model) {
        model.addAttribute("CustomerDTO", new CustomerDTO());
        return "function/db_management/customer";
    }

    // 고객 등록 처리
    @PostMapping("/customer")
    @ResponseBody
    public ResponseEntity<String> registerCustomer(@RequestBody CustomerDTO customerDTO) {
        dbService.registerCustomer(customerDTO);
        return ResponseEntity.ok("고객이 성공적으로 등록되었습니다.");
    }

    // 등록 고객 확인
    @GetMapping("/customer/list")
    public ResponseEntity<List<CustomerDTO>> getCustomerList() {
        List<CustomerDTO> customers = dbService.getCustomerList();
        return ResponseEntity.ok(customers);
    }

    @DeleteMapping("/customer/delete/{customerNo}")
    public ResponseEntity<String> deleteCustomer(@PathVariable int customerNo) {
        dbService.deleteCustomer(customerNo);
        return ResponseEntity.ok("Customer deleted successfully");
    }


//=====================================================================================================================

    // 계약 등록 페이지 로드
    @GetMapping("/contract")
    public String loadContractPage(Model model) {
        // 고객 데이터 추가
        model.addAttribute("customers", dbService.getAllCustomers());

        // 직원 데이터 추가
        List<EmployeeDTO> employees = dbService.getAllEmployees();
        model.addAttribute("employees", employees);

        // `contract` 객체를 데이터베이스에서 가져오거나 새로운 객체로 초기화
        ContractDTO contract = dbService.getLatestContract();
        if (contract == null) {
            contract = new ContractDTO();
            contract.setLastReformDate(new Date()); // 기본 수정일 설정
            contract.setLastInseminatee("Unknown User"); // 기본 수정자 설정
        }
        model.addAttribute("contract", contract); // `contract`를 모델에 추가

        return "function/db_management/contract"; // 템플릿 경로
    }
// =====================================================================================================================

    // 고객 데이터 조회 (JSON 반환)
    @GetMapping("/customers")
    @ResponseBody
    public List<CustomerDTO> getCustomers() {
        return dbService.getAllCustomers();
    }

    // 설계사 목록 조회 (JSON 응답)
    @GetMapping("/employees")
    @ResponseBody
    public List<EmployeeDTO> getEmployees() {
        return dbService.getAllEmployees();
    }

    // 보험회사명 코드 조회
    @GetMapping("/insuranceCodes")
    @ResponseBody
    public List<Map<String, Object>> getInsuranceCodes() {
        List<Map<String, Object>> insuranceCodes = new ArrayList<>();
        insuranceCodes.add(Map.of("code", 1, "name", "메리츠화재"));
        insuranceCodes.add(Map.of("code", 2, "name", "현대해상"));
        insuranceCodes.add(Map.of("code", 3, "name", "한화손해보험"));
        insuranceCodes.add(Map.of("code", 4, "name", "삼성화재"));
        insuranceCodes.add(Map.of("code", 5, "name", "DB손해보험"));
        insuranceCodes.add(Map.of("code", 31, "name", "MetLife"));
        insuranceCodes.add(Map.of("code", 32, "name", "한화생명"));
        insuranceCodes.add(Map.of("code", 33, "name", "SinhanLife"));
        insuranceCodes.add(Map.of("code", 34, "name", "흥국생명"));
        insuranceCodes.add(Map.of("code", 35, "name", "라이나생명"));
        return insuranceCodes;
    }


    @GetMapping("/products")
    @ResponseBody
    public List<ProductManageDTO> searchProducts(@RequestParam(required = false) String keyword,
                                                 @RequestParam(required = false, defaultValue = "0")
                                                 Integer insuranceCode) {
        if (insuranceCode == 0) {
            return new ArrayList<>(); // 기본 동작 처리
        }
        return dbService.searchProducts(keyword, insuranceCode);
    }

//======================================================================================================================

    @PostMapping("/contract")
    @ResponseBody
    public Map<String, Object> registerContract(@RequestBody ContractDTO contractDTO, HttpSession session, RedirectAttributes redirectAttributes) {
        Map<String, Object> response = new HashMap<>();
        String empNo = (String) session.getAttribute("empNo");

        contractDTO.setLastReformDate(new Date());
        contractDTO.setLastInseminatee(empNo);

        try {
            dbService.registerContract(contractDTO);
            response.put("status", "success");
            response.put("message", "계약이 성공적으로 등록되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", "계약 등록 중 오류가 발생했습니다.");
        }

        return response; // JSON 응답 반환
    }


//======================================================================================================================

    @GetMapping("/expiringcustomer")
    public String loadExpiringCustomerPage() {
        return "function/db_management/expiringcustomer";
    }

    @GetMapping("/expiringcustomer/search")
    @ResponseBody
    public List<ExpiringCustomerDTO> searchExpiringCustomersByDateRange(
            @RequestParam String startDate,
            @RequestParam String endDate
    ) {
        return dbService.searchExpiringCustomersByDateRange(startDate, endDate);
    }



    @GetMapping("/expiringcustomer/month")
    @ResponseBody
    public List<ExpiringCustomerDTO> getExpiringCustomersByMonth(
            @RequestParam int year,
            @RequestParam int month
    ) {
        System.out.println("수신된 year: " + year + ", month: " + month); // 디버깅 로그 추가
        return dbService.getExpiringCustomersByMonth(year, month);
    }


//=====================================================================================================================

    //만기 도래고객 홈 화면 띄우기
    @GetMapping("/api/expiring-contracts")
    public ResponseEntity<List<ExpiringCustomerDTO>> getExpiredCustomers() {
        try {
            List<ExpiringCustomerDTO> customers = dbService.getExpiredCustomer();
            return ResponseEntity.ok(customers);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

//=====================================================================================================================
    //영업 실적 현황판
    @GetMapping("/top-sales")
    @ResponseBody
    public List<TopSalesContractDTO> getTopSalesContract() {
        List<TopSalesContractDTO> result = dbService.getTopSaleContract();
        return result;
    }
//======================================================================================================================

    @GetMapping("/inquiry")
    public String SelectInquiry(
            @RequestParam(value ="name" , required = false) String name,
            @RequestParam(value ="plannerName",required =false) String plannerName,Model model){

        List<InquiryDTO> inquiryDTO = dbService.searchInquiry(name, plannerName);
        model.addAttribute("inquiryDTO", inquiryDTO);
        return "function/db_management/inquiry";
    }

    @GetMapping("/inquiry/json")
    @ResponseBody
    public List<InquiryDTO>selectInquiryJson(
            @RequestParam(value = "name" , required = false) String name,
            @RequestParam(value = "plannerName" ,required = false) String plannerName){
        return dbService.searchInquiry(name, plannerName);
    }


//======================================================================================================================

    @GetMapping("/contract/details/{contractNo}")
    public String getContractDetails(@PathVariable("contractNo") String contractNo, Model model) {
        ContractDetailsDTO contractDetailsDTO = dbService.getContractDetails(contractNo);
        if (contractDetailsDTO == null) {
            throw new RuntimeException("No contract details found for contractNo: " + contractNo);
        }
        model.addAttribute("contractDetails", contractDetailsDTO);

        return "function/db_management/contractdetails";
    }




}






















