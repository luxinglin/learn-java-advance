package cn.com.gary.gateway.api;

import cn.com.gary.model.pojo.RestResult;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author luxinglin
 * @version 1.0
 * @Description: TODO
 * @create 2018-08-20 18:11
 ***/
@RestController
@RequestMapping(value = "/")
public class VersionController {
    final static String DATE_REG = "(?:(?!0000)[0-9]{4}(?:(?:0[1-9]|1[0-2])(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])(?:29|30)|(?:0[13578]|1[02])31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)0229)";

    /**
     * 获取发布日期
     *
     * @param version
     * @return
     */
    public static String getVersionDate(String version) {
        if (version == null || version.isEmpty()) {
            return "未知";
        }
        final Pattern pattern = Pattern.compile(DATE_REG);
        Matcher matcher = pattern.matcher(version);
        if (matcher.find()) {
            return matcher.group();
        } else {
            return "未知";
        }
    }

    /**
     * 组件版本获取
     *
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/version", method = RequestMethod.GET)
    public RestResult version() throws IOException {
        Properties properties = PropertiesLoaderUtils.loadAllProperties("version.properties");
        Map map = new HashMap(1);
        map.put("version", properties.getProperty("version", "1.0.0"));
        map.put("releaseDate", getVersionDate(map.get("version").toString()));
        return new RestResult(map);
    }
}
