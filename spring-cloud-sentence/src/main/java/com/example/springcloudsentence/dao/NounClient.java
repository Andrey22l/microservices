package com.example.springcloudsentence.dao;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@FeignClient("SPRING-CLOUD-NOUN")
public interface NounClient {

	@RequestMapping(value="/", method=RequestMethod.GET)
	public String getWord();
	
}
