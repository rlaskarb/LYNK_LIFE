package com.semi.lynk.function.login.service;

import com.semi.lynk.function.login.model.EmpDetails;
import com.semi.lynk.function.login.model.dao.LoginMapper;
import com.semi.lynk.function.login.model.dto.EmpAddDTO;
import com.semi.lynk.function.login.model.dto.LoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginService implements UserDetailsService {

    //****************************************************************
    // 회원가입 관련 내용들
    //****************************************************************
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private LoginMapper loginMapper;

    @Transactional
    public int addEmployee(EmpAddDTO empAddDTO) {
        empAddDTO.setUserPass(encoder.encode(empAddDTO.getUserPass()));
        if (empAddDTO.getImage() != null) {
            empAddDTO.setImage(empAddDTO.getImage());
        }
        int result = loginMapper.addEmployee(empAddDTO);
        loginMapper.addAuthorization(empAddDTO);
        return result;
    }

    public LoginDTO findByUsername(String empName) {
        LoginDTO login = loginMapper.findByUsername(empName);
        if (login == null) {
            return null;
        } else {
            return login;
        }
    }

    //****************************************************************
    // 로그인 관련 내용들
    //****************************************************************
    @Override
    public UserDetails loadUserByUsername(String empNo) throws UsernameNotFoundException {
        LoginDTO login = loginMapper.findByUsername(empNo);
        if (login == null) {
            throw new UsernameNotFoundException(empNo + "사번 정보를 찾을 수 없습니다.");
        }

        // 반환
        return new EmpDetails(login);
    }

}
