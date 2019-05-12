package com.summer.isnow;

import com.summer.isnow.filter.SimpleCORSFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.List;


@ServletComponentScan
@ComponentScan(basePackages = "com.summer")
@EnableCaching
@SpringBootApplication
public class ISnowApplication extends SpringBootServletInitializer implements CommandLineRunner {
	private static Logger logger = LoggerFactory.getLogger(ISnowApplication.class);

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(ISnowApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(ISnowApplication.class, args);
	}

	// springboot运行后此方法首先被调用
	// 实现CommandLineRunner抽象类中的run方法
	public void run(String... args) throws Exception {
		logger.error("启动完成！");
	}

	//跨越配置1
	@Bean
	public FilterRegistrationBean getSimpleCORSFilter() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();

		SimpleCORSFilter simpleCORSFilter = new SimpleCORSFilter();
		registrationBean.setFilter(simpleCORSFilter);
		List<String> urlPatterns = new ArrayList<String>();
		urlPatterns.add("/*");
		registrationBean.setUrlPatterns(urlPatterns);
		registrationBean.setOrder(1);

		return registrationBean;
	}
}
