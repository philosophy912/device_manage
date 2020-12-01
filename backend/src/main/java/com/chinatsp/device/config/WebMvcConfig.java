package com.chinatsp.device.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Value(("${upload_file}"))
    private String upload_images;

//    // token的全局配置
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new JwtInterceptor())
//                .addPathPatterns("/**")
//                .excludePathPatterns("/user/**")
//                .excludePathPatterns("*.html");
//    }

    // 跨域的全局配置
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //设置允许跨域的路径
        registry.addMapping("/**")
                //设置允许跨域请求的域名
                .allowedOrigins("*")
                //设置允许的方法
                .allowedMethods("*")
                //跨域允许时间
                .maxAge(36000)
                //是否允许证书 不再默认开启
                .allowCredentials(false);
    }

    // 静态资源处理
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        String upload_images = "D:\\Workspace\\github\\device_manage\\backend\\upload_file\\";
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + upload_images);
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }
}
