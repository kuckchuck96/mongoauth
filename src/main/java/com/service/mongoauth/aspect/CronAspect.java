package com.service.mongoauth.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.service.mongoauth.annotation.CronTraceable;

@Component
@Aspect
public class CronAspect {

	private static final Logger LOGGER = LoggerFactory.getLogger(CronAspect.class);

	@Before("@annotation(com.service.mongoauth.annotation.CronTraceable)")
	public void beforeCron(JoinPoint jp) throws Throwable {
		MethodSignature methodSignature = (MethodSignature) jp.getSignature();
		LOGGER.info(String.format("Started Cron Execution: %s",
				methodSignature.getMethod().getAnnotation(CronTraceable.class).name()));
	}

	@After("@annotation(com.service.mongoauth.annotation.CronTraceable)")
	public void afterCron(JoinPoint jp) throws Throwable {
		MethodSignature methodSignature = (MethodSignature) jp.getSignature();
		LOGGER.info(String.format("Ended Cron Execution: %s",
				methodSignature.getMethod().getAnnotation(CronTraceable.class).name()));
	}

}
