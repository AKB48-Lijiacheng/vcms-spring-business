package com.westcatr.vcms.service.impl;

import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.westcatr.vcms.controller.query.UserQuery;
import com.westcatr.vcms.entity.User;
import com.westcatr.vcms.mapper.UserMapper;
import com.westcatr.vcms.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.westcatr.rd.base.bmybatisplusbootstarter.wrapper.WrapperFactory;
import java.util.List;

/**
 * <p>
 * 系统用户 服务实现类
 * </p>
 *
 * @author admin
 * @since 2020-05-27
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public IPage<User> ipage(UserQuery query) {
        return this.page(query.page(), new WrapperFactory<User>().create(query));
    }

    @Override
    public boolean isave(User param) {
        return this.save(param);
    }

    @Override
    public boolean iupdate(User param) {
        return this.updateById(param);
    }

    @Override
    public User igetById(String id) {
        return this.getById(id);
    }

    @Override
    public boolean iremove(String id) {
        return this.removeById(id);
    }
}
