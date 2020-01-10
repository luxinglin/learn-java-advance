package cn.com.gary.gateway.schedule;

import cn.com.gary.gateway.service.LicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 定期校验license合法性
 *
 * @author liurui
 */
public class LicenseSchedule {
    @Autowired
    LicenseService licenseService;

    @Scheduled(cron = "${license.cron}")
    public void exec() {
        licenseService.validate();
    }

    public void init() {
        licenseService.validate();
    }
}