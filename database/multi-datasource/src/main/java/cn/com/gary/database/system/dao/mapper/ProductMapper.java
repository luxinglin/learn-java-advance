package cn.com.gary.database.system.dao.mapper;

import cn.com.gary.database.system.dao.entity.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author pai
 * @since 2020-01-08
 */
public interface ProductMapper extends BaseMapper<Product> {

    /**
     * 产品信息分页查询
     *
     * @param params 查询条件
     * @return
     */
    List<Product> pageProduct(@Param("params") Map<String, Object> params);
}
