package com.home.config;

import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableWebSecurity
public class MyWebSecurityConfigurerAdapter /*extends WebSecurityConfigurerAdapter*/ {

   /* @Override
    protected void configure(HttpSecurity http) throws Exception {


        //CSRF默认支持的方法： GET|HEAD|TRACE|OPTIONS，不支持POST ，不是我们想要的，故取消CSRF防御
        http.csrf().disable()
                .headers().frameOptions().disable();
    }*/

}