package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.ExaminationMapper;
import com.ruoyi.system.domain.Examination;
import com.ruoyi.system.service.IExaminationService;

/**
 * 检查费设置Service业务层处理
 * 
 * @author William Wu
 * @date 2024-04-24
 */
@Service
public class ExaminationServiceImpl implements IExaminationService 
{
    @Autowired
    private ExaminationMapper examinationMapper;

    /**
     * 查询检查费设置
     * 
     * @param id 检查费设置主键
     * @return 检查费设置
     */
    @Override
    public Examination selectExaminationById(Long id)
    {
        return examinationMapper.selectExaminationById(id);
    }

    /**
     * 查询检查费设置列表
     * 
     * @param examination 检查费设置
     * @return 检查费设置
     */
    @Override
    public List<Examination> selectExaminationList(Examination examination)
    {
        return examinationMapper.selectExaminationList(examination);
    }

    /**
     * 新增检查费设置
     * 
     * @param examination 检查费设置
     * @return 结果
     */
    @Override
    public int insertExamination(Examination examination)
    {
        return examinationMapper.insertExamination(examination);
    }

    /**
     * 修改检查费设置
     * 
     * @param examination 检查费设置
     * @return 结果
     */
    @Override
    public int updateExamination(Examination examination)
    {
        return examinationMapper.updateExamination(examination);
    }

    /**
     * 批量删除检查费设置
     * 
     * @param ids 需要删除的检查费设置主键
     * @return 结果
     */
    @Override
    public int deleteExaminationByIds(Long[] ids)
    {
        return examinationMapper.deleteExaminationByIds(ids);
    }

    /**
     * 删除检查费设置信息
     * 
     * @param id 检查费设置主键
     * @return 结果
     */
    @Override
    public int deleteExaminationById(Long id)
    {
        return examinationMapper.deleteExaminationById(id);
    }
}
