package com.semi.lynk.function.db_management.service;

import com.semi.lynk.function.db_management.model.dao.DbMapper;
import com.semi.lynk.function.db_management.model.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class DbService {

    private final DbMapper dbMapper;


    @Autowired
    public DbService (DbMapper dbMapper){
        this.dbMapper=dbMapper;
    }

//====================================================================================================================
    //각종 보험사 등록

    public void insuranceRegistration(ProductManageDTO productManageDTO) {
        dbMapper.insertProduct(productManageDTO);
    }

    public List<ProductManageDTO> getProductsByCompany() {
        return dbMapper.selectProductsByCompany();
    }

    public void deleteProductByCompany(String company, String productNo) {
        dbMapper.deleteProductByCompany(company, productNo);
    }

//=====================================================================================================================
    //고객등록

    public void registerCustomer(CustomerDTO customerDTO) {
        dbMapper.insertCustomer(customerDTO);
    }

    public List<CustomerDTO> getCustomerList() {
        return dbMapper.selectCustomerList();
    }

    public void deleteCustomer(int customerNo) {
        dbMapper.deleteCustomer(customerNo);
    }
//=====================================================================================================================
    //신규계약 등록

    public List<EmployeeDTO> getAllEmployees() { return dbMapper.selectAllEmployees();}


    public List<CustomerDTO> getAllCustomers() {
        return dbMapper.selectAllCustomers();
    }


    public List<ProductManageDTO> searchProducts(String keyword, Integer insuranceCode) {
        return dbMapper.searchProducts(keyword, insuranceCode);
    }


    public void registerContract(ContractDTO contractDTO) {
        dbMapper.insertContract(contractDTO);

    }

    public ContractDTO getLatestContract() {
        ContractDTO contract = dbMapper.findLatestContract();
        if (contract == null) {
            contract = new ContractDTO();
            contract.setLastReformDate(new Date());
            contract.setLastInseminatee("Unknown User");
        }
        return contract;
    }
//===================================================================================================================
    //만기도래 조회

    public List<ExpiringCustomerDTO> searchExpiringCustomersByDateRange(String startDate, String endDate) {
        return dbMapper.selectExpiringCustomersByDateRange(startDate, endDate);
    }


    public List<ExpiringCustomerDTO> getExpiringCustomersByMonth(int year, int month) {

        return dbMapper.selectExpiringCustomersByMonth(year, month);
    }



//======================================================================================================================
    public List<ExpiringCustomerDTO> getExpiredCustomer() {
        List<ExpiringCustomerDTO> customers = dbMapper.getExpiredCustomer();

        // 보험회사 코드에 따라 이름 매핑
        for (ExpiringCustomerDTO customer : customers) {
            customer.setInsuranceCompany(mapInsuranceCompanyName(customer.getInsuranceCompany()));
        }

        return customers;
    }




    private String mapInsuranceCompanyName(String code) {
        switch (code) {
            case "1":
                return "메리츠화재";
            case "2":
                return "현대해상";
            case "3":
                return "한화손해보험";
            case "4":
                return "삼성화재";
            case "5":
                return "DB손해보험";
            case "6":
                return "MetLife";
            case "32":
                return "한화생명";
            case "33":
                return "SinhanLife";
            case "34":
                return "흥국생명";
            case "35":
                return "라이나생명";
            default:
                return "기타";
        }
    }


//    =============================================================================================================

    public List<TopSalesContractDTO> getTopSaleContract() {
        List<TopSalesContractDTO> contracts = dbMapper.fetchTopSalesContract();
        System.out.println("contracts = " + contracts);
        return contracts;
    }

//===================================================================================================================

    public List<InquiryDTO> searchInquiry(String name, String plannerName) {
        return dbMapper.searchInquiry(name,plannerName);
    }
//=====================================================================================================================

    public ContractDetailsDTO getContractDetails(String contractNo) {
        ContractDetailsDTO contractDetails = dbMapper.getContractDetails(contractNo);
        if (contractDetails == null) {
            System.out.println("No data found for contractNo: " + contractNo);
        }
        return contractDetails;
    }
}







