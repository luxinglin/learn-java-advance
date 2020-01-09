package cn.com.gary.biz.api;

import cn.com.gary.biz.service.IBaseService;
import cn.com.gary.biz.util.MpPlusUtil;
import cn.com.gary.model.constants.MessageConstants;
import cn.com.gary.model.exception.BizException;
import cn.com.gary.model.pojo.RestResult;
import cn.com.gary.util.EntityUtils;
import cn.com.gary.util.web.WebUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * MybatisPlus Restful接口基础类
 *
 * @param <BizService>
 * @param <Entity>
 * @author luxinglin
 */
@Slf4j
public class MpBaseController<BizService extends IService, Entity, DTO> extends AbsController {
    @Autowired
    protected BizService bizService;

    @Autowired
    protected IBaseService<DTO> advService;

    @ApiOperation(value = "单个记录新增", notes = "新增单个实体对象")
    @ApiImplicitParam(paramType = "body", name = "entity", value = "实体对象", dataType = "Entity")
    @PostMapping(value = "")
    @ResponseBody
    public RestResult add(@RequestBody Entity entity) {
        if (entity == null) {
            throw new BizException(WebUtil.getMessage(MessageConstants.INSERT_NULL));
        }
        WebUtil.prepareInsertParams(entity, this.getLoginUserId().toString());
        bizService.saveOrUpdate(entity);
        log.info("新增了," + entity.getClass().getSimpleName() + "对象");
        RestResult restResult = new RestResult(entity);
        restResult.setMessage(WebUtil.getMessage(MessageConstants.INSERT_SUCCESS));
        return restResult;
    }

    @ApiOperation(value = "单条记录查询", notes = "根据主键id查询单个对象")
    @ApiImplicitParam(paramType = "path", name = "id", value = "实体对象ID", dataType = "Object")
    @GetMapping(value = "/{id}")
    @ResponseBody
    public RestResult get(@PathVariable Object id) {
        if (EntityUtils.isPKNullOrEmpty(id)) {
            throw new BizException(WebUtil.getMessage(MessageConstants.SELECT_BY_KEY_BUT_NULL));
        }
        RestResult entityObjectRestResponse = new RestResult();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("ID", id);
        Object o = bizService.getOne(queryWrapper);
        if (o == null) {
            throw new BizException(WebUtil.getMessage(MessageConstants.SELECT_BY_KEY_NOT_EXIST_PARAM,
                    new Object[]{id}));
        }
        entityObjectRestResponse.setData(o);
        return entityObjectRestResponse;
    }

    @ApiOperation(value = "记录修改", notes = "根据主键id修改单个实体对象")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "entity", value = "实体对象", dataType = "Entity")
    })
    @PutMapping(value = "")
    @ResponseBody
    public RestResult update(@RequestBody Entity entity) {
        if (entity == null) {
            throw new BizException(WebUtil.getMessage(MessageConstants.UPDATE_NULL));
        }
        WebUtil.prepareUpdateParams(entity, this.getLoginUserId().toString());
        bizService.saveOrUpdate(entity);
        log.info("修改了" + entity.getClass().getSimpleName() + "对象");
        RestResult restResult = new RestResult(entity);
        restResult.setMessage(WebUtil.getMessage(MessageConstants.UPDATE_SUCCESS));
        return restResult;
    }

    @ApiOperation(value = "单条记录删除", notes = "根据主键id删除单个实体对象")
    @ApiImplicitParam(paramType = "path", name = "id", value = "实体对象ID", dataType = "Object")
    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public RestResult remove(@PathVariable Object id) {
        if (EntityUtils.isPKNullOrEmpty(id)) {
            throw new BizException(WebUtil.getMessage(MessageConstants.DELETE_BY_KEY_BUT_NULL));
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("ID", id);
        bizService.remove(queryWrapper);
        log.info("删除了key为：" + id + "的对象");
        return new RestResult("删除记录成功");
    }

    @ApiOperation(value = "查询全表记录", notes = "查询全表记录")
    @GetMapping(value = "/all")
    @ResponseBody
    public RestResult all() {
        RestResult restResult = new RestResult();
        restResult.setData(bizService.list(null));
        return restResult;
    }

    @ApiOperation(value = "记录列表条件查询", notes = "根据条件进行查询")
    @GetMapping(value = "/list")
    @ResponseBody
    public RestResult list(Entity entity) {
        RestResult restResult = new RestResult();
        QueryWrapper queryWrapper = MpPlusUtil.getInstance().initQueryWrapper(entity);
        restResult.setData(bizService.list(queryWrapper));
        return restResult;
    }

    @ApiOperation(value = "记录单表分页条件查询", notes = "根据条件进行分页查询-单表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "current", value = "当前页面"),
            @ApiImplicitParam(paramType = "query", name = "size", value = "页大小"),
            @ApiImplicitParam(paramType = "query", name = "asc", value = "升序字段", defaultValue = "", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "desc", value = "降序字段", defaultValue = "", dataType = "String")
    })
    @GetMapping(value = "/page")
    @ResponseBody
    public RestResult<IPage> page(Entity entity,
                                  @RequestParam(value = "current") Long current,
                                  @RequestParam(value = "size") Long size,
                                  @RequestParam(value = "asc", defaultValue = "", required = false) String asc,
                                  @RequestParam(value = "desc", defaultValue = "", required = false) String desc
    ) {
        //分页基本信息
        current = patchPageSizeAndNum(current, 1L);
        size = patchPageSizeAndNum(size, 10L);

        Page<Entity> page = new Page<>(current, size);
        QueryWrapper queryWrapper = MpPlusUtil.getInstance().initPage(page, entity, asc, desc);

        IPage<Entity> entityIPage = bizService.page(page, queryWrapper);
        return new RestResult(entityIPage);
    }

    @ApiOperation(value = "记录跨表分页条件查询", notes = "根据条件进行分页查询-多表级联")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "current", value = "当前页面"),
            @ApiImplicitParam(paramType = "query", name = "size", value = "页大小"),
            @ApiImplicitParam(paramType = "query", name = "asc", value = "升序字段", defaultValue = "", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "desc", value = "降序字段", defaultValue = "", dataType = "String")
    })
    @GetMapping(value = "/adv-page")
    @ResponseBody
    public RestResult<IPage> advPage(DTO entity,
                                     @RequestParam(value = "current") Long current,
                                     @RequestParam(value = "size") Long size,
                                     @RequestParam(value = "asc", defaultValue = "", required = false) String asc,
                                     @RequestParam(value = "desc", defaultValue = "", required = false) String desc
    ) {
        //分页基本信息
        current = patchPageSizeAndNum(current, 1L);
        size = patchPageSizeAndNum(size, 10L);

        Page<DTO> page = new Page<>(current, size);
        MpPlusUtil.getInstance().initPage(page, asc, desc);

        IPage<DTO> entityIPage = advService.advPage(page, entity);
        return new RestResult(entityIPage);
    }

    @ApiOperation(value = "记录查询导出", notes = "根据条件进行资产条目数据导出到excel")
    @GetMapping("/export")
    public void export2Excel(DTO entity,
                             HttpServletResponse response) throws Throwable {
        advService.export(getLoginOrgId(), entity, response);
    }

    /**
     * 分页页面、页大小默认值设置
     *
     * @param value
     * @param defaultValue
     * @return
     */
    private Long patchPageSizeAndNum(Long value, Long defaultValue) {
        if (value == null || value.intValue() == 0) {
            return defaultValue;
        }
        return value;
    }
}