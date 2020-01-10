package cn.com.gary.biz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import javax.servlet.http.HttpServletResponse;

/**
 * @author luxinglin
 * @version 1.0
 * @Description: TODO
 * @create 2020-01-09 11:05
 **/
public interface IBaseService<T> {
    /**
     * 高级分页
     *
     * @param page
     * @param entity
     * @return
     */
    IPage<T> advPage(Page<T> page, T entity);

    /**
     * 记录查询导出
     *
     * @param userId   导出人员id
     * @param entity   查询条件
     * @param response 响应
     * @throws Throwable
     */
    void export(Long userId, T entity, HttpServletResponse response) throws Throwable;
}
