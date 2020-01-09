package cn.com.gary.database.common.config.aspect;

import cn.com.gary.biz.annotation.BizLogParam;
import cn.com.gary.biz.annotation.BizSysLog;
import cn.com.gary.biz.token.util.TokenUtil;
import cn.com.gary.biz.util.RequestUtil;
import cn.com.gary.database.common.constant.SysLogModuleEnum;
import cn.com.gary.database.system.dao.entity.SysLog;
import cn.com.gary.database.system.dao.entity.SysUser;
import cn.com.gary.database.system.service.SysLogService;
import cn.com.gary.database.system.service.SysUserService;
import cn.com.gary.model.constants.CommonConstants;
import cn.com.gary.util.ToyUtil;
import cn.com.gary.util.json.JsonUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.annotation.JsonInclude;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 业务日志切面功能实现
 *
 * @author luxinglin
 * @version 1.0
 * @Description: TODO
 * @create 2019-09-20 11:50
 **/
@Aspect
@Component
@Slf4j
public class BizSysLogAspect {
    private final SysLogService sysLogService;
    private final SysUserService sysUserService;
    @Resource
    AsyncTaskExecutor asyncTaskExecutor;

    @Autowired
    public BizSysLogAspect(SysLogService sysLogService, SysUserService sysUserService) {
        this.sysLogService = sysLogService;
        this.sysUserService = sysUserService;
    }

    @Pointcut("@annotation(cn.com.gary.biz.annotation.BizSysLog)")
    public void pointCut() {

    }

    @Around(value = "@annotation(bizSysLog)")
    public Object doAudit(ProceedingJoinPoint joinPoint, BizSysLog bizSysLog) throws Throwable {
        Object returnObj = null;
        boolean exception = false;
        String failReason = "[！失败：%s]";
        try {
            returnObj = joinPoint.proceed();
        } catch (Exception ex) {
            exception = true;
            failReason = String.format(failReason, ex.getMessage());
            throw ex;
        } finally {
            try {
                SysLog sysLog = constructSysLog(joinPoint, bizSysLog);
                if (exception) {
                    sysLog.setFailedReason(failReason);
                    sysLog.setSuccess(CommonConstants.BOOLEAN_FALSE);
                }
                asyncTaskExecutor.execute(() -> sysLogService.save(sysLog));
            } catch (Exception ex) {
                log.error("业务日志保存异常，异常详情： {}", ex.getMessage());
            }
        }
        return returnObj;
    }

    /**
     * 构造日志对象
     *
     * @param joinPoint 切入点
     * @param bizSysLog 日志注解对象
     * @return
     */
    private SysLog constructSysLog(ProceedingJoinPoint joinPoint, BizSysLog bizSysLog) {
        SysLog sysLog = new SysLog();
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            Long userId = RequestUtil.getLoginUserId(request);
            sysLog.setUserId(userId);
            sysLog.setCreatedBy(userId);
            sysLog.setOperateDt(LocalDateTime.now());
            sysLog.setIp(TokenUtil.getIpAddress(request));
            sysLog.setOperateModule(bizSysLog.module());
            sysLog.setCreatedDt(LocalDateTime.now());
            sysLog.setSuccess(CommonConstants.BOOLEAN_TRUE);

            //设置客户端信息
            try {
                String agent = request.getHeader("User-Agent");
                //解析agent字符串
                UserAgent userAgent = UserAgent.parseUserAgentString(agent);
                //获取浏览器对象
                Browser browser = userAgent.getBrowser();
                //获取操作系统对象
                OperatingSystem operatingSystem = userAgent.getOperatingSystem();
                String clientName = String.format("浏览器类型:%s，浏览器名:%s，访问设备类型:%s，操作系统:%s",
                        browser.getBrowserType(), browser.getName(), operatingSystem.getDeviceType().getName(),
                        operatingSystem.getName());
                sysLog.setClientInfo(clientName);
            } catch (Exception ex) {
                log.warn("获取访问客户端类型失败");
            }
        } catch (Exception ex) {
            log.warn("构造SysLog对象失败，异常详情:{}", ex.getMessage());
        }
        sysLog.setOperateDesc(constructDescription(joinPoint, bizSysLog));

        //登录时，根据用户名，设置登录用户id信息
        if (SysLogModuleEnum.AUTH_MGT_LOGIN.getCode().equals(bizSysLog.module())) {
            String userName = joinPoint.getArgs()[0].toString();
            QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("ACCOUNT", userName);
            List<SysUser> users = sysUserService.list(queryWrapper);
            if (ToyUtil.listNotEmpty(users)) {
                sysLog.setUserId(users.get(0).getId());
            }
        }

        //最大长度截取
        final int maxOperateDescLen = 500;
        if (ToyUtil.notEmpty(sysLog.getOperateDesc()) && sysLog.getOperateDesc().length() > maxOperateDescLen) {
            sysLog.setOperateDesc(sysLog.getOperateDesc().substring(0, maxOperateDescLen));
        }

        return sysLog;
    }

    /**
     * @param joinPoint
     * @param bizSysLog
     * @return
     */
    private String constructDescription(ProceedingJoinPoint joinPoint, BizSysLog bizSysLog) {
        StringBuffer sb = new StringBuffer();
        if (ToyUtil.notEmpty(bizSysLog.description())) {
            sb.append(bizSysLog.description());
        }

        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        Annotation[][] annotations = method.getParameterAnnotations();
        if (annotations == null || annotations.length == 0) {
            return sb.toString();
        }

        List<MethodParam> methodParams = new ArrayList<>();
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < annotations.length; i++) {
            Annotation[] parameterAnnotation = annotations[i];

            for (int j = 0; j < parameterAnnotation.length; j++) {
                Annotation annotation = parameterAnnotation[j];
                if (annotation instanceof BizLogParam) {
                    BizLogParam bizLogParam = (BizLogParam) annotation;

                    MethodParam methodParam = new MethodParam();
                    methodParam.setName(bizLogParam.name());
                    if (args[i] instanceof QueryWrapper) {
                        QueryWrapper queryWrapper = (QueryWrapper) args[i];
                        methodParam.setValue(describeWrapperParam(queryWrapper));
                    } else {
                        methodParam.setValue(args[i]);
                    }
                    methodParams.add(methodParam);
                }
            }
        }

        if (methodParams.size() == 0) {
            return sb.toString();
        }

        sb.append("，参数明细：");
        sb.append(methodParams.stream().map(item -> JsonUtil.toJson(item, JsonInclude.Include.NON_NULL)).
                collect(Collectors.joining(CommonConstants.COMMA)));

        return sb.toString();
    }

    /**
     * 解析queryWrapper参数
     *
     * @param queryWrapper queryWrapper对象
     * @return
     */
    private String describeWrapperParam(QueryWrapper queryWrapper) {
        StringBuffer parVal = new StringBuffer();
        if (queryWrapper == null) {
            return parVal.toString();
        }

        if (queryWrapper.getEntity() != null) {
            parVal.append(JsonUtil.toJson(queryWrapper.getEntity(), JsonInclude.Include.NON_NULL));
        }

        String statement = queryWrapper.getExpression().getSqlSegment();
        if (ToyUtil.notEmpty(statement)) {
            statement = statement.replaceAll("#\\{ew.paramNameValuePairs.", "").
                    replaceAll("}", "");
        }

        Map<String, Object> map = queryWrapper.getParamNameValuePairs();
        if (map == null || map.size() == 0) {
            parVal.append(statement);
            return parVal.toString();
        }

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String value = entry.getValue() != null ? entry.toString() : null;
            if (value != null) {
                int index = value.indexOf("=");
                if (index != -1) {
                    value = value.substring(index + 1);
                }
            }
            statement = statement.replace(entry.getKey(), value);
        }

        parVal.append(statement);
        return parVal.toString();
    }
}

@Getter
@Setter
class MethodParam {
    String name;
    Object value;

    @Override
    public String toString() {
        return JsonUtil.toJson(this, JsonInclude.Include.NON_NULL);
    }
}
