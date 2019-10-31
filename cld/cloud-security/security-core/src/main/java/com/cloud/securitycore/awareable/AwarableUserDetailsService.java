package com.cloud.securitycore.awareable;

import com.cloud.securitycore.base.BaseUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Aurher: QiaoHang
 * @Description:
 * @Data: 2019/9/28 9:46
 * @Modified By:
 */
public class AwarableUserDetailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<String> roles = new ArrayList<>();
        String[] strings = {"user", "admin", "system"};
        String encode = passwordEncoder.encode("00012");
        return new BaseUserDetails(username,encode,roles);
    }
}
