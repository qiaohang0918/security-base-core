package com.cloud.securitycore.override;

import com.cloud.securitycore.base.BaseUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @Aurher: QiaoHang
 * @Description:
 * @Data: 2019/10/4 9:54
 * @Modified By:
 */
//@Component
public class OverrideUserDetailsService implements UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new BaseUserDetails(username,passwordEncoder.encode("123456"), Arrays.asList(new String[]{"user","admin"}));
    }
}
