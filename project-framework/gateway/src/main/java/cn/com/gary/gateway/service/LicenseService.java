package cn.com.gary.gateway.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author luxinglin
 * @version 1.0
 * @Description: TODO
 * @create 2019-07-12 17:28
 **/
@Service
public class LicenseService {
    private final static Logger LOGGER = LoggerFactory.getLogger(LicenseService.class);
    @Autowired
    RestTemplate restTemplate;

    @Value("${license.server.url}")
    private String serverUrl;
    @Value("${license.check.url}")
    private String restfulUrl;

    /**
     * 校验license合法性
     */
    public void validate() {
        String url = serverUrl.concat(restfulUrl);
        try {
            final String key = "valid";
            Map result = restTemplate.getForEntity(url, HashMap.class).getBody();
            if (result.get(key) == null) {
                exit();
                return;
            }

            Boolean valid = new Boolean(result.get(key).toString());
            if (!valid) {
                exit();
            }
        } catch (Exception ex) {
            LOGGER.error("license校验异常，异常详情:{}", ex.getMessage());
            exit();
        }
    }

    /**
     * 系统退出
     */
    private void exit() {
        try {
            LOGGER.warn("license已过期，系统将在5s后自动退出");
            Thread.sleep(1000 * 5);
            System.exit(-1);
        } catch (Exception ex) {

        }
    }

}
