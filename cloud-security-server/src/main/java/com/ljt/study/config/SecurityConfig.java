package com.ljt.study.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import java.util.Collections;

/**
 * @author LiJingTang
 * @date 2020-07-05 10:25
 */
@Slf4j
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String ADMIN = "admin";
    public static final String USER = "user";

//    @Autowired
//    private MyUserDetailService userDetailService;
//    @Autowired
//    private MyAuthenticationProvider authenticationProvider;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/img/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            // 配置任何请求都需要认证
            .authorizeRequests().anyRequest().authenticated()
//                .antMatchers().hasIpAddress()
            .and()
            // 支持form表单登录
            .formLogin()
//                .loginPage()
//                .usernameParameter()
//                .passwordParameter()
                .defaultSuccessUrl("/", true)
                .successHandler((request, response, authentication) -> log.info("登录成功了"))
                .failureUrl("/login?error=")
                .failureHandler((request, response, e) -> log.info("登录失败：{}", e.getLocalizedMessage()))
            .and()
            .logout()
                .addLogoutHandler((httpServletRequest, httpServletResponse, authentication) -> log.info("{} 退出了", authentication.getPrincipal()))
            .and()
            // 记住我 token 不一定是浏览器
            .rememberMe().and()
            // 限制同一账号只能同时登录一次
            .sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(true).and().and()
            // 开启basic验证登录
            .httpBasic().and()
            // post/put/delete/patch 不需要?_csrf=入参
            .csrf().disable();

        // 设置可以iframe访问
        http.headers().frameOptions().sameOrigin();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//            .userDetailsService(userDetailService).and()
//            .authenticationProvider(authenticationProvider);
//         jdbcAuthentication
        auth
            .inMemoryAuthentication()
            .withUser(admin())
            .withUser(user());
    }

    /**
     * 及时清理过期的session
     */
    @Bean
    HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Override
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(admin());
//        manager.createUser(user());
//        return manager;
//    }

    @Bean
    UserDetails admin() {
        return new User(ADMIN, passwordEncoder().encode(ADMIN), Collections.singletonList(new SimpleGrantedAuthority(ADMIN)));
    }

    @Bean
    UserDetails user() {
        return new User(USER, passwordEncoder().encode(USER), Collections.singletonList(new SimpleGrantedAuthority(USER)));
    }

}
