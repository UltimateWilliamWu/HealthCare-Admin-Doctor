package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanValidators;
import com.ruoyi.system.domain.Ward;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.ScheduleMapper;
import com.ruoyi.system.domain.Schedule;
import com.ruoyi.system.service.IScheduleService;

import javax.validation.Validator;

/**
 * 排班总览Service业务层处理
 *
 * @author William Wu
 * @date 2024-04-27
 */
@Service
public class ScheduleServiceImpl implements IScheduleService
{
    @Autowired
    private ScheduleMapper scheduleMapper;
    @Autowired
    protected Validator validator;

    /**
     * 查询排班总览
     *
     * @param id 排班总览主键
     * @return 排班总览
     */
    @Override
    public Schedule selectScheduleById(String id)
    {
        return scheduleMapper.selectScheduleById(id);
    }

    /**
     * 查询排班总览列表
     *
     * @param schedule 排班总览
     * @return 排班总览
     */
    @Override
    public List<Schedule> selectScheduleList(Schedule schedule)
    {
        return scheduleMapper.selectScheduleList(schedule);
    }

    /**
     * 新增排班总览
     *
     * @param schedule 排班总览
     * @return 结果
     */
    @Override
    public int insertSchedule(Schedule schedule)
    {
        return scheduleMapper.insertSchedule(schedule);
    }

    /**
     * 修改排班总览
     *
     * @param schedule 排班总览
     * @return 结果
     */
    @Override
    public int updateSchedule(Schedule schedule)
    {
        return scheduleMapper.updateSchedule(schedule);
    }

    /**
     * 批量删除排班总览
     *
     * @param ids 需要删除的排班总览主键
     * @return 结果
     */
    @Override
    public int deleteScheduleByIds(String[] ids)
    {
        return scheduleMapper.deleteScheduleByIds(ids);
    }

    /**
     * 删除排班总览信息
     *
     * @param id 排班总览主键
     * @return 结果
     */
    @Override
    public int deleteScheduleById(String id)
    {
        return scheduleMapper.deleteScheduleById(id);
    }

    @Override
    public String importSchedule(List<Schedule> userList, Boolean isUpdateSupport, String operName)
    {
        if (StringUtils.isNull(userList) || userList.size() == 0)
        {
            throw new ServiceException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (Schedule user : userList)
        {
            try
            {
                BeanValidators.validateWithException(validator, user);
                user.setCreateBy(operName);
                this.insertSchedule(user);
                successNum++;
                successMsg.append("<br/>").append(successNum).append("、收费项目 ").append(user.getId()).append(" 导入成功");

            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、收费项目 " + user.getId() + " 导入失败：";
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
