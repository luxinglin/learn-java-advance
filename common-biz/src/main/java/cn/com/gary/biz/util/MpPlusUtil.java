package cn.com.gary.biz.util;

import cn.com.gary.model.constants.CommonConstants;
import cn.com.gary.model.constants.PageConstant;
import cn.com.gary.util.ToyUtil;
import cn.com.gary.util.page.PageUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页工具类
 *
 * @author luxinglin
 * @version 1.0
 * @Description: TODO
 * @create 2019-09-26 16:11
 **/
@Slf4j
public class MpPlusUtil<T> {

    private static MpPlusUtil instance;

    private MpPlusUtil() {
    }

    public static MpPlusUtil getInstance() {
        if (instance == null) {
            instance = new MpPlusUtil();
        }

        return instance;
    }

    /**
     * 基础参数构造类
     *
     * @param entity
     * @return
     */
    public QueryWrapper<T> initQueryWrapper(T entity) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        return queryWrapper;
    }

    /**
     * 分页查询基础参数构造
     *
     * @param page
     * @param asc
     * @param desc
     * @return
     */
    public QueryWrapper initPage(Page<T> page,
                                 String asc,
                                 String desc) {
        return initPage(page, null, asc, desc);
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
    public QueryWrapper initPage(Page<T> page, T entity,
                                 String asc,
                                 String desc) {
        if (asc != null && !asc.isEmpty()) {
            page.setAsc(asc.split(CommonConstants.COMMA));
        }
        if (desc != null && !desc.isEmpty()) {
            page.setDesc(desc.split(CommonConstants.COMMA));
        }

        //携带参数到service
        QueryWrapper queryWrapper = initQueryWrapper(entity);
        return queryWrapper;
    }

    /**
     * 构造分页查询返回结果
     *
     * @param pageInfo
     * @param page
     * @param records
     * @return
     */
    public IPage getIPageResult(com.github.pagehelper.Page<Object> pageInfo,
                                Page page, List records) {
        IPage result = new Page<>();
        if (pageInfo != null) {
            result.setTotal(pageInfo.getTotal());
            result.setPages(pageInfo.getPages());
        }
        if (page != null) {
            result.setCurrent(page.getCurrent());
            result.setSize(page.getSize());
        }
        result.setRecords(records);
        return result;
    }

    /**
     * 从分页参数里面获取并构造排序子句
     *
     * @param page 分页参数
     * @return
     */
    public String constructOrderClause(Page page) {
        if (page == null) {
            return null;
        }

        StringBuffer stringBuffer = new StringBuffer();
        if (page.ascs() != null && page.ascs().length > 0) {
            List<String> ascList = new ArrayList<>();
            for (String asc : page.ascs()) {
                ascList.add(PageUtil.sqlInject(asc));
            }

            for (String asc : ascList) {
                if (ToyUtil.nullOrEmpty(asc)) {
                    continue;
                }

                if (!stringBuffer.toString().isEmpty()) {
                    stringBuffer.append(CommonConstants.COMMA);
                }
                stringBuffer.append(ToyUtil.underScoreName(asc).toUpperCase()).append(PageConstant.ASC);
            }
        }

        if (page.descs() != null && page.descs().length > 0) {
            List<String> descList = new ArrayList<>();
            for (String desc : page.descs()) {
                descList.add(PageUtil.sqlInject(desc));
            }
            for (String desc : descList) {
                if (ToyUtil.nullOrEmpty(desc)) {
                    continue;
                }

                if (!stringBuffer.toString().isEmpty()) {
                    stringBuffer.append(CommonConstants.COMMA);
                }
                stringBuffer.append(ToyUtil.underScoreName(desc).toUpperCase()).append(PageConstant.DESC);
            }
        }
        log.debug("orderByClause = {}", stringBuffer.toString());
        return stringBuffer.toString();
    }
}
