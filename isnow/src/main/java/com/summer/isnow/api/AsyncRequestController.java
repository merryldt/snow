package com.summer.isnow.api;

import com.summer.icore.model.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

@RestController
@RequestMapping("api/")
public class AsyncRequestController {

	@GetMapping("/async")
	public Callable<User> doAsync(){
		return ()->{
			Thread.sleep(5000);
			return (User)SecurityUtils.getSubject().getPrincipal();
		};
	}
}
