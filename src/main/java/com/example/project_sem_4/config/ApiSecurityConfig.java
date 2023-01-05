package com.example.project_sem_4.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ApiSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Bean
    public ApiAuthorizationFilter apiAuthorizationFilter(){
        return new ApiAuthorizationFilter();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //override default login path
        ApiAuthenticationFilter apiAuthenticationFilter = new ApiAuthenticationFilter(authenticationManagerBean(),getApplicationContext());
        apiAuthenticationFilter.setFilterProcessesUrl("/login");
        http.cors().and().csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/register**",
                "/login**", "/token/refresh**",
                "/uploadfile**","/test**",
                "/cloud/**","/branch/create**",
                "/mail**","/account/active/**",
                "/order/create",
                "/order/createOrderDetail",
                "/service/search",
                "/service/findAllTypeService",
                "/service/{id}",
                "/type_service/search",
                "/type_service/{id}",
                "/booking/search",
                        "/feedback/create**")
                .permitAll();
        //ADMIN
        http.authorizeRequests().antMatchers("/account**").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/blog**").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/booking**").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/branch**").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/feedback**").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/order**").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/service**").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/type_service**").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/voucher**").hasAnyAuthority("ADMIN");
        //RECEPTIONISTS
        http.authorizeRequests().antMatchers("/account**").hasAnyAuthority("RECEPTIONISTS");
//        http.authorizeRequests().antMatchers("/service/search").hasAnyAuthority("RECEPTIONISTS");
//        http.authorizeRequests().antMatchers("/service/{id}").hasAnyAuthority("RECEPTIONISTS");
//        http.authorizeRequests().antMatchers("/service/findAllTypeService").hasAnyAuthority("RECEPTIONISTS");
        http.authorizeRequests().antMatchers("/feedback").hasAnyAuthority("RECEPTIONISTS");
        http.authorizeRequests().antMatchers("/feedback/{id}").hasAnyAuthority("RECEPTIONISTS");
        http.authorizeRequests().antMatchers("/feedback/changeStatus**").hasAnyAuthority("RECEPTIONISTS");
        http.authorizeRequests().antMatchers("/order**").hasAnyAuthority("RECEPTIONISTS");
        //CUSTOMER_CARE
//        http.authorizeRequests().antMatchers("/service/search").hasAnyAuthority("CUSTOMER_CARE");
//        http.authorizeRequests().antMatchers("/service/{id}").hasAnyAuthority("CUSTOMER_CARE");
//        http.authorizeRequests().antMatchers("/service/findAllTypeService").hasAnyAuthority("CUSTOMER_CARE");
        http.authorizeRequests().antMatchers("/booking**").hasAnyAuthority("CUSTOMER_CARE");
        //STAFF
//        http.authorizeRequests().antMatchers("/service/search").hasAnyAuthority("STAFF");
//        http.authorizeRequests().antMatchers("/service/{id}").hasAnyAuthority("STAFF");
//        http.authorizeRequests().antMatchers("/service/findAllTypeService").hasAnyAuthority("STAFF");
        http.authorizeRequests().antMatchers("/booking**").hasAnyAuthority("STAFF");
        //CUSTOMER
//        http.authorizeRequests().antMatchers("/service/search").hasAnyAuthority("CUSTOMER");
//        http.authorizeRequests().antMatchers("/service/{id}").hasAnyAuthority("CUSTOMER");
//        http.authorizeRequests().antMatchers("/service/findAllTypeService").hasAnyAuthority("CUSTOMER");
        http.authorizeRequests().antMatchers("/feedback").hasAnyAuthority("CUSTOMER");
        http.authorizeRequests().antMatchers("/feedback/{id}").hasAnyAuthority("CUSTOMER");
        http.authorizeRequests().antMatchers("/booking**").hasAnyAuthority("CUSTOMER");

        //add requests path for more role here
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(apiAuthenticationFilter);
        http.addFilterBefore(apiAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
