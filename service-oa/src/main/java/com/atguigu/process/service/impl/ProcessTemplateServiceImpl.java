package com.atguigu.process.service.impl;

import com.atguigu.model.process.ProcessTemplate;
import com.atguigu.model.process.ProcessType;
import com.atguigu.process.mapper.ProcessTemplateMapper;
import com.atguigu.process.service.ProcessTemplateService;
import com.atguigu.process.service.ProcessTypeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 审批模板 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2023-12-07
 */
@Service
public class ProcessTemplateServiceImpl extends ServiceImpl<ProcessTemplateMapper, ProcessTemplate> implements ProcessTemplateService {

    @Resource
    private ProcessTemplateMapper processTemplateMapper;

    @Resource
    private ProcessTypeService processTypeService;

    @Override
    public IPage<ProcessTemplate> selectPage(Page<ProcessTemplate> pageParam) {
        // 调用mapper的方法实现分页查询
        LambdaQueryWrapper<ProcessTemplate> queryWrapper = new LambdaQueryWrapper<ProcessTemplate>();
        queryWrapper.orderByDesc(ProcessTemplate::getId);
        IPage<ProcessTemplate> page = processTemplateMapper.selectPage(pageParam, queryWrapper);
        List<ProcessTemplate> processTemplateList = page.getRecords();

        List<Long> processTypeIdList = processTemplateList.stream().map(processTemplate -> processTemplate.getProcessTypeId()).collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(processTypeIdList)) {
            LambdaQueryWrapper<ProcessType> wrapper = new LambdaQueryWrapper<ProcessType>();
            wrapper.in(ProcessType::getId, processTypeIdList);
            Map<Long, ProcessType> processTypeIdToProcessTypeMap = processTypeService.list(wrapper).stream()
                    .collect(Collectors.toMap(ProcessType::getId, ProcessType -> ProcessType));
            // 遍历list集合，得到每个对象的审批类型ID
            for (ProcessTemplate processTemplate : processTemplateList) {
                // 得到每个对象的审批类型ID
                Long processTypeId = processTemplate.getProcessTypeId();
                // 根据审批类型ID，查询获取对应名称
                ProcessType processType = processTypeIdToProcessTypeMap.get(processTypeId);
                if (null == processType) continue;
                processTemplate.setProcessTypeName(processType.getName());
            }
        }
        return page;
    }
}
