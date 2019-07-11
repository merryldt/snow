package com.summer.isnow.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.summer.isnow.handler.LogCostInterceptor;
import com.summer.isnow.handler.MyExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class fastJsonConfig extends WebMvcConfigurerAdapter {


    @Autowired
    private LogCostInterceptor logCostInterceptor;

    /**
     * 使用阿里 fastjson 作为JSON MessageConverter
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(

                //美化json输出，否则会作为整行输出
                SerializerFeature.PrettyFormat,
                // 保留map空的字段
                SerializerFeature.WriteMapNullValue,
                // 将String类型的null转成""
                SerializerFeature.WriteNullStringAsEmpty,
                // 将Number类型的null转成0
                SerializerFeature.WriteNullNumberAsZero,
                // 将List类型的null转成[]
                SerializerFeature.WriteNullListAsEmpty,
                // 将Boolean类型的null转成false
                SerializerFeature.WriteNullBooleanAsFalse,
                // 避免循环引用
                SerializerFeature.DisableCircularReferenceDetect);


        converter.setFastJsonConfig(config);
        converter.setDefaultCharset(Charset.forName("UTF-8"));
        List<MediaType> mediaTypeList = new ArrayList<>();
        // 解决中文乱码问题，相当于在Controller上的@RequestMapping中加了个属性produces = "application/json"
        mediaTypeList.add(MediaType.APPLICATION_JSON);
        converter.setSupportedMediaTypes(mediaTypeList);
        converters.add(converter);
    }

    /**
     * // 多个拦截器组成一个拦截器链
     * // addPathPatterns 用于添加拦截规则
     * // excludePathPatterns 用户排除拦截
     * // registry.addInterceptor(getInterfaceAuthCheckInterceptor()
     * // 这种方式无论什么情况都可以
     * // registry.addInterceptor(new InterfaceAuthCheckInterceptor())
     * // 这种情况时，自定义的interceptor中不能注入其他内容，比如redis或者其他service，如果要注入，必须使用上面这种方法
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(logCostInterceptor).addPathPatterns()
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/swagger-resources/**",
                        "/webjars/**",
                        "/v2/**",
                        "/swagger-ui.html/**",
                        "/configuration/**");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  //设置允许跨域的路径
                .allowedOrigins("*")       //设置允许跨域请求的域名
//				.allowedHeaders("*")      //设置允许跨域请求的域名
                .allowedMethods("GET", "HEAD", "POST","PUT", "DELETE", "OPTIONS")
                .allowCredentials(true).maxAge(3600);
//				.allowedMethods("*")       //设置允许跨域请求的方法（get、post）

    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);
    }

    //
//	@Override
//	protected void configureAsyncSupport(AsyncSupportConfigurer configurer) {
//		configurer.setTaskExecutor(new ConcurrentTaskExecutor(Executors.newFixedThreadPool(3)));
//		configurer.setDefaultTimeout(30000);
//	}
    @Override
    public void configureDefaultServletHandling( DefaultServletHandlerConfigurer configurer) {
       //是为了使对静态资源的请求转发到servlet容器上，而不是控制器。
        configurer.enable();
    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add(new MyExceptionHandler());
        super.configureHandlerExceptionResolvers(exceptionResolvers);
    }
}