package net.zyc.store.config;

import net.zyc.store.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration // 加载拦截器
public class LoginInterceptorConfigurer implements WebMvcConfigurer {


//配置拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 创建
        HandlerInterceptor interceptor = new LoginInterceptor();
        //    配置白名单
        List<String> patterns = new ArrayList<>();
        patterns.add("/bootstrap3/**");
        patterns.add("/css/**");
        patterns.add("/images/**");
        patterns.add("/js/**");
        patterns.add("/web/register.html");
        patterns.add("/web/login.html");
        patterns.add("/web/index.html");
        patterns.add("/web/product.html");
        patterns.add("/users/reg");
        patterns.add("/users/login");
        patterns.add("/district/**");
        patterns.add("/products/**");


        //    注册自定义拦截器
        registry.addInterceptor(interceptor)
                .addPathPatterns("/**") // 拦截的路径
                .excludePathPatterns(patterns); // 放行路径
    }
}
