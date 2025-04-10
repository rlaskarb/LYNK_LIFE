package com.semi.lynk.function.login.model;

import com.semi.lynk.function.human.model.dto.DepartmentDTO;
import com.semi.lynk.function.human.model.dto.HumanDTO;
import com.semi.lynk.function.login.model.dto.LoginDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmpDetails implements UserDetails {
    private LoginDTO loginDTO;

    // 권한 정보 반환 메소드 (잘 모르겠음...)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        if (loginDTO.getRoleAdmin() == 1) {
            authorities.add(() -> "ROLE_ADMIN");
        } else {
            authorities.add(() -> "ROLE_USER");
        }
        System.out.println("loginDTO.getRoleAdmin() = " + loginDTO.getRoleAdmin());
        System.out.println("authorities = " + authorities);
        return authorities;
    }

    // 기본값
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of();
//    }

    // 비밀번호 반환 메소드
    @Override
    public String getPassword() {
        return loginDTO.getEmpPwd();
    }

    // 아이디 반환 메소드
    @Override
    public String getUsername() {
        return loginDTO.getEmpNo();
    }

    // 이름 반환 메소드
    public String getName() {
        return loginDTO.getEmpName();
    }

    // 이미지 반환 메소드
    public String getImage() {
        return loginDTO.getImage();
    }

    // 권한 반환 메소드
    public int getRoleAdmin() { return loginDTO.getRoleAdmin(); }             // 관리자 권한, management 페이지 접근 가능자, 0:권한 없음 / 1:권한 있음
    public int getRoleDraft() { return loginDTO.getRoleDraft(); }             // 기안 승인 권한, 0:권한 없음 / 1:권한 있음
    public int getRoleLeave() { return loginDTO.getRoleLeave(); }             // 연차 승인 권한, 0:권한 없음 / 1:권한 있음
    public int getRoleDepartment() { return loginDTO.getRoleDepartment(); }   // 부서 관리 권한, 0:권한 없음 / 1:권한 있음
    public int getRoleNotice() { return loginDTO.getRoleNotice(); }           // 게시글 작성 권한, 0:권한 없음 / 1:권한 있음
    public int getRoleSchedule() { return loginDTO.getRoleSchedule(); }       // 일정 관리 권한, 0:개인 / 1:부서 / 2:전사

    // 계정 만료 여부 (잘 모르겠음2)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 잠겨있는 계정 확인 메소드
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 탈퇴 계정 여부 표현 메소드
    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    // 계정 비활성화 여부로 사용자가 사용할 수 없는 상태 (삭제 등)
    @Override
    public boolean isEnabled() {
        return true;
    }

}
