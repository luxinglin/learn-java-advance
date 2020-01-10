package cn.com.gary.database.system.api;

import cn.com.gary.database.system.service.SysFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luxinglin
 * @version 1.0
 * @Description: TODO
 * @create 2020-01-08 15:58
 **/
@Api(tags = "文件Api")
@RestController
@RequestMapping("/v1/sys-file")
public class SysFileController {
    private final SysFileService sysFileService;

    @Autowired
    public SysFileController(SysFileService sysFileService) {
        this.sysFileService = sysFileService;
    }

    @ApiOperation(value = "列表查询", notes = "根据条件进行查询")
    @GetMapping(value = "/list-all")
    public Object list() {
        return sysFileService.list(null);
    }
}
