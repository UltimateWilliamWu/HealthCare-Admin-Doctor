package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanValidators;
import com.ruoyi.system.domain.Examination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.WardMapper;
import com.ruoyi.system.domain.Ward;
import com.ruoyi.system.service.IWardService;

import javax.validation.Validator;

/**
 * 病床管理Service业务层处理
 *
 * @author William Wu
 * @date 2024-04-20
 */
@Service
public class WardServiceImpl implements IWardService
{
    @Autowired
    private WardMapper wardMapper;
    @Autowired
    protected Validator validator;

    /**
     * 查询病床管理
     *
     * @param id 病床管理主键
     * @return 病床管理
     */
    @Override
    public Ward selectWardById(Integer id)
    {
        return wardMapper.selectWardById(id);
    }

    /**
     * 查询病床管理列表
     *
     * @param ward 病床管理
     * @return 病床管理
     */
    @Override
    public List<Ward> selectWardList(Ward ward)
    {
        return wardMapper.selectWardList(ward);
    }

    /**
     * 新增病床管理
     *
     * @param ward 病床管理
     * @return 结果
     */
    @Override
    public int insertWard(Ward ward)
    {
        return wardMapper.insertWard(ward);
    }

    /**
     * 修改病床管理
     *
     * @param ward 病床管理
     * @return 结果
     */
    @Override
    public int updateWard(Ward ward)
    {
        return wardMapper.updateWard(ward);
    }

    /**
     * 批量删除病床管理
     *
     * @param ids 需要删除的病床管理主键
     * @return 结果
     */
    @Override
    public int deleteWardByIds(Integer[] ids)
    {
        return wardMapper.deleteWardByIds(ids);
    }

    /**
     * 删除病床管理信息
     *
     * @param id 病床管理主键
     * @return 结果
     */
    @Override
    public int deleteWardById(Integer id)
    {
        return wardMapper.deleteWardById(id);
    }

    @Override
    public String importWard(List<Ward> userList, Boolean isUpdateSupport, String operName)
    {
        if (StringUtils.isNull(userList) || userList.size() == 0)
        {
            throw new ServiceException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (Ward user : userList)
        {
            try
            {
                BeanValidators.validateWithException(validator, user);
                user.setCreateBy(operName);
                this.insertWard(user);
                successNum++;
                successMsg.append("<br/>").append(successNum).append("、收费项目 ").append(user.getBed()).append(" 导入成功");

            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、收费项目 " + user.getBed() + " 导入失败：";
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
