package com.research.chat.domain.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.research.chat.domain.ai.impl.FileInfoBotService;
import com.research.chat.domain.core.service.PdfHandlerService;
import com.research.chat.domain.core.service.model.Literature;
import com.research.chat.domain.core.service.util.PdfUtil;
import com.research.chat.domain.repo.Dbutils;
import com.research.chat.domain.repo.mapper.LocalFileRecordMapper;
import com.research.chat.domain.repo.model.LocalFileRecordDO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;
import java.util.Optional;

/**
 * name：PdfHandlerServiceImpl
 * Author：tritone
 * Date：2024/1/30  23:31
 */
@Service
public class PdfHandlerServiceImpl implements PdfHandlerService {

    @Autowired
    FileInfoBotService fileInfoBotService;

    public LocalFileRecordMapper getLocalFileRecordMapper() {
        return Dbutils.getMapper(LocalFileRecordMapper.class);
    }

    @Override
    public void importFile(String absolutePath) {
        File file = new File(absolutePath);

        String fileName = file.getName();

        LambdaQueryWrapper<LocalFileRecordDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LocalFileRecordDO::getFilename, fileName);

        LocalFileRecordDO localFileRecordDO = getLocalFileRecordMapper().selectOne(queryWrapper);
        if (localFileRecordDO != null) {
            getLocalFileRecordMapper().deleteById(localFileRecordDO.getId());
        }

        String firstText = PdfUtil.getPdfText(absolutePath,1);

        String authors =  fileInfoBotService.getAuthorFromText(firstText);

        LocalFileRecordDO recordDO = new LocalFileRecordDO();
        recordDO.setAuthors(authors);
        recordDO.setFilename(fileName);
        recordDO.setCreateTime(new Date());
        recordDO.setUpdateTime(new Date());

        getLocalFileRecordMapper().insert(recordDO);
    }

    @Override
    public void getList() {
        var list =  getLocalFileRecordMapper().selectList(new LambdaQueryWrapper<>());
    }

    @Override
    public Literature getByName(String fileName) {
        var literatureDO =
                getLocalFileRecordMapper().selectOne(new LambdaQueryWrapper<LocalFileRecordDO>().eq(LocalFileRecordDO::getFilename, fileName));

        if(literatureDO == null ){
            return null;
        }
        Literature literature = new Literature();
        BeanUtils.copyProperties(literatureDO,literature);

        return literature;


    }
}
