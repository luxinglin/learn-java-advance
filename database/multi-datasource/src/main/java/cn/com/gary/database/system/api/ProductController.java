package cn.com.gary.database.system.api;

import cn.com.gary.database.system.dao.entity.Product;
import cn.com.gary.database.system.service.ProductService;
import cn.com.gary.model.constants.MessageConstants;
import cn.com.gary.model.pojo.RestResult;
import cn.com.gary.util.page.PageUtil;
import cn.com.gary.util.web.WebUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author luxinglin
 * @version 1.0
 * @Description: TODO
 * @create 2020-01-08 16:00
 **/
@Api(tags = "产品Api")
@RestController
@RequestMapping("/v1/product")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @ApiOperation(value = "保存对象", notes = "保存单个对象")
    @PostMapping(value = "")
    public RestResult<Product> add(@RequestBody Product product) {
        productService.save(product);
        RestResult restResult = new RestResult(product);
        restResult.setMessage(WebUtil.getMessage(MessageConstants.INSERT_SUCCESS));
        return restResult;
    }

    @ApiOperation(value = "列表查询", notes = "根据条件进行查询")
    @GetMapping(value = "/list")
    public RestResult<Product> listAll() {
        return new RestResult(productService.list(null));
    }

    @ApiOperation(value = "分页查询", notes = "根据条件进行分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "asc", value = "升序字段", defaultValue = "", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "desc", value = "降序字段", defaultValue = "", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "current", value = "当前页面", dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "size", value = "页大小", dataType = "Long")
    })
    @GetMapping(value = "/page")
    public RestResult<IPage> page(Product entity,
                                  Long current,
                                  Long size,
                                  @RequestParam(value = "asc", defaultValue = "", required = false) String asc,
                                  @RequestParam(value = "desc", defaultValue = "", required = false) String desc) {
        //分页基本信息
        Page<Product> page = new Page<>(current, size);
        PageUtil.getInstance().init(page, null, asc, desc);

        IPage<Product> entityIPage = productService.advPage(page, entity);
        return new RestResult(entityIPage);
    }
}
