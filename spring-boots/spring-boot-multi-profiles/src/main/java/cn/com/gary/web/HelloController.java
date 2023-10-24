package cn.com.gary.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @Autowired
    private Environment env;

    @RequestMapping("/hello")
    public String index() {
        return "Hello World";
    }

    @RequestMapping("/getCurrentEnv")
    public String getCurrentEnv() {
        String[] actProfile = env.getActiveProfiles();
        String curProfile = actProfile[0];
        return curProfile;
    }

    @RequestMapping("/get")
    public String getEnv(@RequestParam("name") String name) {
        return env.getProperty(name);
    }
}
