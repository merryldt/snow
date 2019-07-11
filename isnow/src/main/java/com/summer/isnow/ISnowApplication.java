package com.summer.isnow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;


/**
 * @author liudongting
 * @date 2019/7/10 12:09
 */
@ComponentScan(basePackages = "com.summer")
@EnableCaching
@SpringBootApplication
@ServletComponentScan
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
	//启动完成后执行的方法
	public void run(String... args) throws Exception {
		logger.error("启动完成！");
	}



}
