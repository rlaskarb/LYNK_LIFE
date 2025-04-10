package com.semi.lynk.function.human.model.dao;

import com.semi.lynk.function.human.model.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EmployeeMapper {
    List<EmployeeDTO> employeeFullList();

    List<EmpAndDepDTO> joinListResult();

    int registMapperhum(RegistHumDTO registHumDTO);

    List<LookUpDTO> lookUpMapper();

    int modifyEmployee(ModifyDTO modifyDTO);

    List<EmpAndDepDTO> findEmployeeImageByEmployeeNo(@Param("employeeNo") int employeeNo);

}
