package cn.com.gary.util.page;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 分页工具类
 *
 * @author luxinglin
 * @version 1.0
 * @Description: TODO
 * @create 2019-09-26 16:11
 **/
public class PageUtil<T> {
    private static final String COMMA = ",";
    private static PageUtil instance;

    private PageUtil() {
    }

    public static PageUtil getInstance() {
        if (instance == null) {
            instance = new PageUtil();
        }

        return instance;
    }

    /**
     * 分页查询基础参数构造
     *
     * @param page   分页对象
     * @param entity 分页查询参数
     * @param asc    升序排序字段
     * @param desc   降序排序字段
     * @return
     */
    public QueryWrapper init(Page<T> page, T entity,
                             String asc,
                             String desc) {
        if (asc != null && !asc.isEmpty()) {
            page.setAsc(asc.split(COMMA));
        }
        if (desc != null && !desc.isEmpty()) {
            page.setDesc(desc.split(COMMA));
        }

        //携带参数到service
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.setEntity(entity);

        return queryWrapper;
    }
}
