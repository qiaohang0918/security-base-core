package com.cloud.securitycore.config;

import com.cloud.securitycore.awareable.AwarableUserDetailsService;
import com.cloud.securitycore.common.CommonAuthenticationFilureHandler;
import com.cloud.securitycore.common.CommonAuthenticationSuccessHandler;
import com.cloud.securitycore.explandAble.generator.Generator;
import com.cloud.securitycore.explandAble.generator.basecode.BaseCodeGenerator;
import com.cloud.securitycore.explandAble.generator.basecode.DefaultBaseCodeGenerator;
import com.cloud.securitycore.explandAble.generator.imagecode.DefaultImageCodeGenerator;
import com.cloud.securitycore.explandAble.generator.imagecode.ImageCodeGenerator;
import com.cloud.securitycore.filter.DefaultSecurityValidateCodeFilter;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * @Aurher: QiaoHang
 * @Description:
 * @Data: 2019/9/28 9:17
 * @Modified By:
 */
@Configuration
@EnableWebSecurity
public class SecurityConfigPathAdapter extends WebSecurityConfigurerAdapter implements ApplicationContextAware {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private CommonAuthenticationSuccessHandler commonAuthenticationSuccessHandler;

    @Autowired
    private CommonAuthenticationFilureHandler commonAuthenticationFilureHandler;

    @Autowired
    private DefaultSecurityValidateCodeFilter defaultSecurityImageCodeFilter;

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * The class implement ApplicationContextAware ，need override this method named setApplicationContext ，
     * this method given your current class a small path , support you the current SpringContainer , you can
     * get some beans from the SpringContainer !
     * @param context
     */
    @Override
    public void setApplicationContext(ApplicationContext context) {
        userDetailsService = context.getBean(UserDetailsService.class);
        super.setApplicationContext(context);
    }

    /**
     * This component named AwwareableUserDetailsService is a default Component implement the interface named UserDetailsService ,
     * supported the UsernamePasswordAuthenticationFilter a authentication function , of course , you can definition the
     * UserDetailsService by yourself , notice : UsernamePasswordAuthenticationFilter's authentication logic need a provider
     * named DaoAuthenticationProvider rely on PasswordEncoder !
     * @return
     */
    @Bean(name = "userDetailsService")
    @ConditionalOnMissingBean(value = UserDetailsService.class)
    public UserDetailsService awwareableUserDetailsService(){
        AwarableUserDetailsService userDetailsService = new AwarableUserDetailsService();
        System.out.println("默认的用户名密码过滤器(AwarableUserDetailsService)加载.....");
        return userDetailsService;
    }

    /**
     * This bena named DefaultImageCodeGenerator is a default ImageCodeGenerator we supported ,
     * users whom wanted a stronger securit can consider this conponent , of course , you can
     * definition the ImageCodeGenerator by yourself , in this way , you need create a component
     * which extends AbstructImageCodeGenerator , it's a class modifier by abstruct !
     * @return
     */
    @Bean(name = {"imageCodeGenerator"})
    @ConditionalOnMissingBean(value = ImageCodeGenerator.class)
    public ImageCodeGenerator initzationImageCodeGenerator(){
        System.out.println("默认的图像验证码生成器(DefaultImageCodeGenerator)加载.....");
        return new DefaultImageCodeGenerator();
    }

    @Bean(name = {"baseCodeGenerator"})
    @ConditionalOnMissingBean(BaseCodeGenerator.class)
    public BaseCodeGenerator initzationBaseCodeGenerator(){
        System.out.println("默认手机验证码生成器(BaseCodeGenerator)加载.....");
        return new DefaultBaseCodeGenerator();
    }


    /**
     * The PasswordEncoder is a DefaultPasswordEncoder named BcrptPasswordEncoder (make your password safty by salt) ,
     * of course you can also definenation your PasswordEncoder as your style , which neen implement PasswordEncoder ,
     * it's a interface !
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(value = PasswordEncoder.class)
    public PasswordEncoder passwordEncoder(){
        System.out.println("默认的密码加密器(BCryptPasswordEncoder)加载.....");
        return new BCryptPasswordEncoder();
    }

    /**
     * remember-me --->
     * Start on RememberMeAuthenticationFilter , hanldle a bean which implement TokenReponsitory
     * TokenReponsitory try to get the broswer's cookie named 'remember-me' , it's a series to
     * match you datasource'table named 'presistent_logins' to get the 'username' , then handle
     * the UserDetailsServiceImpl to Authentication your Permission !
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(PersistentTokenRepository.class)
    public PersistentTokenRepository jdbcTokenRepository(){
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        //tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }

    /**
     * The WebSecurity support some functions , such as config your sepecification release path !
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers("/resource/**");
    }

    /**
     * The AuthenticationManagerBuilder support some functions , such as assemble your UserDetailsService !
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
         http.addFilterBefore(defaultSecurityImageCodeFilter, UsernamePasswordAuthenticationFilter.class)
                 .formLogin()
                    .loginPage("/authentication/parseAndForword")
                    .loginProcessingUrl("/authentication/loginProcessing")
                     .successHandler(commonAuthenticationSuccessHandler)
                     .failureHandler(commonAuthenticationFilureHandler)
                    .and()
                 .rememberMe()
                    .tokenRepository(this.jdbcTokenRepository())
                    .userDetailsService(userDetailsService)
                    .tokenValiditySeconds(securityProperties.getBrowser().getTokenExpireSecend())
                    .and()
                .authorizeRequests()
                    .antMatchers("/authentication/parseAndForword",
                                "/authentication/loginProcessing",
                                "/standardLoginPage.html",
                                "/userDefindPage.html",
                                "/logout.do",
                                "/code/generator/**").permitAll()
                    .anyRequest()
                    .authenticated()
                    .and()
                    .csrf().disable();
    }
}
