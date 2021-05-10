package com.westcatr.vcms.service.impl;

import com.westcatr.vcms.component.aop.WebSocketMsgAop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.westcatr.vcms.controller.query.FileTextQuery;
import com.westcatr.vcms.entity.FileText;
import com.westcatr.vcms.mapper.FileTextMapper;
import com.westcatr.vcms.service.FileTextService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.westcatr.rd.base.bmybatisplusbootstarter.wrapper.WrapperFactory;
import java.util.List;

/**
 * <p>
 * 富文本存储表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2020-05-15
 */
@Service
public class FileTextServiceImpl extends ServiceImpl<FileTextMapper, FileText> implements FileTextService {
   @Autowired
   WebSocketMsgAop socketMsg;
    @Override
    public IPage<FileText> ipage(FileTextQuery query) {
        return this.page(query.page(), new WrapperFactory<FileText>().create(query));
    }

    @Override
    public boolean isave(FileText param) {
       boolean save = this.save(param);
       return  save;
    }

    @Override
    public boolean iupdate(FileText param) {
        return this.updateById(param);
    }

    @Override
    public FileText igetById(String id) {
        return this.getById(id);
    }

    @Override
    public boolean iremove(String id) {
        return this.removeById(id);
    }
}
