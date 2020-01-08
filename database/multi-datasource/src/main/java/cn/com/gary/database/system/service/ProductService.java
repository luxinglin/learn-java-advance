package cn.com.gary.database.system.service;

import cn.com.gary.database.system.dao.entity.Product;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author pai
 * @since 2020-01-08
 */
public interface ProductService extends IService<Product> {

    IPage<Product> advPage(Page<Product> page, Product product);
}
