package com.service.mongoauth.scheduler;

import com.service.mongoauth.annotation.CronTraceable;
import com.service.mongoauth.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MongoAuthScheduler {

    @Autowired
    private UserService userService;

    @Scheduled(cron = "${cron.exp.remusers}")
    @CronTraceable(name = "RemoveOldUnverifiedUsers")
    protected void removedOldUnVerifiedUsers() {
        try {
            userService.removeUnverifiedOldUsers();
        } catch (Exception ex) {
            log.error("Unable to execute cron.", ex);
        }
    }

}
