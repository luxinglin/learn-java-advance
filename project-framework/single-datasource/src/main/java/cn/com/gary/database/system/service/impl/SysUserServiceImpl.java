package cn.com.gary.database.system.service.impl;

import cn.com.gary.database.system.dao.entity.SysUser;
import cn.com.gary.database.system.dao.mapper.SysUserMapper;
import cn.com.gary.database.system.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author luxinglin
 * @version 1.0
 * @Description: TODO
 * @create 2020-01-09 18:03
 **/
@Service
@Slf4j
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
}
