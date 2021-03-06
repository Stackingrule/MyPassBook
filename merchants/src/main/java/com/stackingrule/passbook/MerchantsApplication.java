package com.stackingrule.passbook;

import com.stackingrule.passbook.security.AuthChechInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;

@SpringBootApplication
public class MerchantsApplication extends WebMvcConfigurerAdapter {

    @Resource
    private AuthChechInterceptor authChechInterceptor;

    public static void main(String[] args) {
        SpringApplication.run(MerchantsApplication.class, args);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(authChechInterceptor)
                .addPathPatterns("/merchants/**");
        super.addInterceptors(registry);

    }

}
