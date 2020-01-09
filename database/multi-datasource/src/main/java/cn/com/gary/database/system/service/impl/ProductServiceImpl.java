package cn.com.gary.database.system.service.impl;

import cn.com.gary.database.common.util.MpPlusPageUtil;
import cn.com.gary.database.system.dao.entity.Product;
import cn.com.gary.database.system.dao.mapper.ProductMapper;
import cn.com.gary.database.system.service.ProductService;
import cn.com.gary.model.constants.PageConstant;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author pai
 * @since 2020-01-08
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Override
    public IPage<Product> advPage(Page<Product> page, Product product) {
        Map<String, Object> params = new HashMap<>(2);
        //构造分页查询参数
        params.put(PageConstant.ORDER_BY_CLAUSE, MpPlusPageUtil.getInstance().constructOrderClause(page));
        //处理时间段查询条件
        ///PageUtil.executeTimeRangeStatement(AlertNotify.class, product.getTimeRangeParams());

        params.put(PageConstant.PARAM_RECORD, product);

        com.github.pagehelper.Page<Object> pageInfo = PageHelper.startPage(Long.valueOf(page.getCurrent()).intValue(),
                Long.valueOf(page.getSize()).intValue());
        List<Product> list = baseMapper.pageProduct(params);

        return MpPlusPageUtil.getInstance().getIPageResult(pageInfo, page, list);
    }
}
