package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanValidators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.ExaminationMapper;
import com.ruoyi.system.domain.Examination;
import com.ruoyi.system.service.IExaminationService;

import javax.validation.Validator;

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
    @Autowired
    protected Validator validator;

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

    @Override
    public List<Examination> selectExaminationByDept(String dept) {
        return examinationMapper.selectExaminationByDept(dept);
    }

    /**
     * 导入用户数据
     *
     * @param userList 用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    @Override
    public String importExamination(List<Examination> userList, Boolean isUpdateSupport, String operName)
    {
        if (StringUtils.isNull(userList) || userList.size() == 0)
        {
            throw new ServiceException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (Examination user : userList)
        {
            try
            {
                BeanValidators.validateWithException(validator, user);
                user.setCreateBy(operName);
                this.insertExamination(user);
                successNum++;
                successMsg.append("<br/>").append(successNum).append("、收费项目 ").append(user.getName()).append(" 导入成功");

            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、收费项目 " + user.getName() + " 导入失败：";
                failureMsg.append(msg).append(e.getMessage());
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

}
