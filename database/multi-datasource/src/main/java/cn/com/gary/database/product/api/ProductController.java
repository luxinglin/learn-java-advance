package cn.com.gary.database.product.api;

import cn.com.gary.biz.api.MpBaseController;
import cn.com.gary.database.common.dto.system.ProductDTO;
import cn.com.gary.database.product.dao.entity.Product;
import cn.com.gary.database.product.service.ProductService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luxinglin
 * @version 1.0
 * @Description: TODO
 * @create 2020-01-08 16:00
 **/
@Api(tags = "产品Api")
@RestController
@RequestMapping("/v1/product")
public class ProductController extends MpBaseController<ProductService, Product, ProductDTO> {

}
