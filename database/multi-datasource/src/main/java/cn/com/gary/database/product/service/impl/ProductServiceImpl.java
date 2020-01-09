package cn.com.gary.database.product.service.impl;

import cn.com.gary.biz.service.IBaseService;
import cn.com.gary.biz.util.MpPlusUtil;
import cn.com.gary.database.common.dto.system.ProductDTO;
import cn.com.gary.database.product.dao.entity.Product;
import cn.com.gary.database.product.dao.mapper.ProductMapper;
import cn.com.gary.database.product.service.ProductService;
import cn.com.gary.model.constants.PageConstant;
import cn.com.gary.util.EntityUtils;
import cn.com.gary.util.page.PageUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author pai
 * @since 2020-01-08
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService, IBaseService<ProductDTO> {

    @Override
    public IPage<ProductDTO> advPage(Page<ProductDTO> page, ProductDTO product) {
        Map<String, Object> params = new HashMap<>(2);
        //构造分页排序字段
        params.put(PageConstant.ORDER_BY_CLAUSE, MpPlusUtil.getInstance().constructOrderClause(page));
        //处理时间段查询条件
        PageUtil.executeTimeRangeStatement(ProductDTO.class, product.getTimeRangeParams());
        //构造分页查询参数
        params.put(PageConstant.PARAM_RECORD, product);

        com.github.pagehelper.Page<Object> pageInfo = PageHelper.startPage(Long.valueOf(page.getCurrent()).intValue(),
                Long.valueOf(page.getSize()).intValue());
        List<Product> rawList = baseMapper.pageProduct(params);

        List<ProductDTO> list = rawList.stream().
                map(item -> EntityUtils.copy(item, ProductDTO.class)).
                collect(Collectors.toList());
        return MpPlusUtil.getInstance().getIPageResult(pageInfo, page, list);
    }

    @Override
    public void export(Long userId, ProductDTO entity, HttpServletResponse response) throws Throwable {

    }
}
