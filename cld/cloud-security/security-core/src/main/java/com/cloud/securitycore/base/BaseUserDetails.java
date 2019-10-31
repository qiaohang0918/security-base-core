package com.cloud.securitycore.base;

import com.cloud.cloudcommon.utils.CommonCheck;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

/**
 * @Aurher: QiaoHang
 * @Description:
 * @Data: 2019/9/28 9:49
 * @Modified By:
 */
public class BaseUserDetails implements UserDetails {

    private  String username;
    private  String password;
    private List<String> roles;
    private  List<GrantedAuthority> grantlist ;

    private static final Map<String,String> map = new HashMap<>();

    static {
        map.put("user1","user1");
        map.put("user2","user2");
        map.put("user3","user3");
        map.put("user4","user4");
    }

    public BaseUserDetails(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public BaseUserDetails(String username, String password, List<String> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        initGrantedAuthoritys();
    }

    public BaseUserDetails(String username, String password, List<String> roles, List<GrantedAuthority> grantlist) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.grantlist = grantlist;
    }

    public void initGrantedAuthoritys(){
        List<GrantedAuthority> list = new ArrayList<>();
        if(CommonCheck.nullList(this.roles))
            this.grantlist = list;
        else
            for (String role : this.roles) {
                list.add(new SimpleGrantedAuthority(role));
            }
    }

    public List<GrantedAuthority> getGrantedAuthority(){
        return this.grantlist;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        String accountNonExpired = map.get("user1");
        if(!accountNonExpired.equalsIgnoreCase(username))
            return true;
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        String accountNonLocked = map.get("user2");
        if(!accountNonLocked.equalsIgnoreCase(username))
            return true;
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        String credentialsNonExpired = map.get("user3");
        if(!credentialsNonExpired.equalsIgnoreCase(username))
            return true;
        return false;
    }

    @Override
    public boolean isEnabled() {
        String enabled = map.get("user4");
        if(!enabled.equalsIgnoreCase(username))
            return true;
        return false;
    }
}
