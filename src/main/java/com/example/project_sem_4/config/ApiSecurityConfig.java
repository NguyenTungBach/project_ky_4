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
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //override default login path
        ApiAuthenticationFilter apiAuthenticationFilter = new ApiAuthenticationFilter(authenticationManagerBean());
        apiAuthenticationFilter.setFilterProcessesUrl("/login");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/register**", "/login**", "/token/refresh**","/uploadfile**").permitAll();
        //ADMIN
        http.authorizeRequests().antMatchers("/account**").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/blog**").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/booking**").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/branch**").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/cloud**").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/feedback**").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/order**").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/service**").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/test**").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/voucher**").hasAnyAuthority("ADMIN");
        //RECEPTIONISTS

        //CUSTOMER_CARE

        //STAFF

        //CUSTOMER

        //add requests path for more role here
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(apiAuthenticationFilter);
        http.addFilterBefore(new ApiAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
