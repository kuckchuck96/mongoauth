package com.service.mongoauth.model.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.service.mongoauth.service.UserService;

@Component
public class MongoAuthScheduler {

	private static final Logger LOGGER = LoggerFactory.getLogger(MongoAuthScheduler.class);

	@Autowired
	private UserService userService;

	@Scheduled(cron = "${cron.exp.remusers}")
	private void removedOldUnVerifiedUsers() {
		LOGGER.info("***** Started removing unverified and old users *****");
		try {
			userService.removeUnverifiedOldUsers();
		} catch (Exception ex) {
			LOGGER.error("Unable to execute cron.", ex);
		}
		LOGGER.info("***** Ended removing unverified and old users *****");
	}

}
