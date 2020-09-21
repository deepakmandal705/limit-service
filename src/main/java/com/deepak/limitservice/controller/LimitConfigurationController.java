package com.deepak.limitservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deepak.limitservice.bean.LimitConfiguration;
import com.deepak.limitservice.config.Configuration;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class LimitConfigurationController {

	@Autowired
	private Configuration configuration;
	
	@GetMapping(value = "limits")
	public LimitConfiguration retrieveLimits() {
		return new LimitConfiguration(1, 1000);
	}
	
	@GetMapping(value = "limits-properties")
	public LimitConfiguration retrieveLimitsFromProperties() {
		return new LimitConfiguration(configuration.getMinimum(), 
				configuration.getMaximum());
	}
	
	@GetMapping(value = "limits-fault-tolerance")
	@HystrixCommand(fallbackMethod = "fallbackLimitValues")
	public LimitConfiguration retrieveLimitsConfiguration() {
		throw new RuntimeException("Configuration not found");
	}
	
	public LimitConfiguration fallbackLimitValues() {
		return new LimitConfiguration(1, 100);
	}
}
