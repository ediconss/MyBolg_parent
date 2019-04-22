package  com.sunkang.bolg.config;

import  com.sunkang.bolg.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
@Configuration
public class interceptorConfig extends WebMvcConfigurationSupport {
    @Autowired
    private JwtInterceptor jwtInterceptor;
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        System.out.println("interceptorConfig");
        //注册拦截器要声明拦截器对象和拦截要求
        registry.addInterceptor(jwtInterceptor).
                //要拦截的地址
                addPathPatterns("/**").
                //不拦截的地址
                excludePathPatterns("/login/**");
    }
}
