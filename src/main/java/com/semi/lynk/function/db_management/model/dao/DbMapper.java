package com.semi.lynk.function.db_management.model.dao;

import com.semi.lynk.function.db_management.model.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface DbMapper {


    void insertProduct(ProductManageDTO productManageDTO);

    List<ProductManageDTO> selectProductsByCompany(@Param("company") String company);

    void deleteProductByCompany(@Param("company") String company, @Param("productNo") String productNo);


//========================================================================

    void insertCustomer(CustomerDTO customerDTO);

    List<CustomerDTO> selectCustomerList();

    void deleteCustomer(int customerNo);

//===========================================================================

    List<EmployeeDTO> selectAllEmployees();

    List<CustomerDTO> selectAllCustomers();

    List<ProductManageDTO> searchProducts(@Param("keyword") String keyword,
                                          @Param("insuranceCode") Integer insuranceCode);

    void insertContract(ContractDTO contractDTO);

    ContractDTO findLatestContract();

//=======================================================================================================================

    List<ExpiringCustomerDTO> selectExpiringCustomersByDateRange(@Param("startDate") String startDate,
                                                                 @Param("endDate") String endDate);


    List<ExpiringCustomerDTO> selectExpiringCustomersByMonth(@Param("year") int year, @Param("month") int month);

//======================================================================================================================

    List<ExpiringCustomerDTO> getExpiredCustomer();

//======================================================================================================================

    List<TopSalesContractDTO> fetchTopSalesContract();

//======================================================================================================================

    List<InquiryDTO> searchInquiry(
            @Param("name") String name,
            @Param("plannerName") String plannerName);

//=====================================================================================================================

    ContractDetailsDTO getContractDetails(@Param("contractNo") String contractNo);



}





