package com.george.service.impl;

import com.george.model.entity.RoleResources;
import com.george.dao.RoleResourcesMapper;
import com.george.service.IRoleResourcesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色资源关联表 服务实现类
 * </p>
 *
 * @author George Chan
 * @since 2019-09-28
 */
@Service
public class RoleResourcesServiceImpl extends ServiceImpl<RoleResourcesMapper, RoleResources> implements IRoleResourcesService {

}
