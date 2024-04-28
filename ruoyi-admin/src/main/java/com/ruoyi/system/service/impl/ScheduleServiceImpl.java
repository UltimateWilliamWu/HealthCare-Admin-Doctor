package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.ScheduleMapper;
import com.ruoyi.system.domain.Schedule;
import com.ruoyi.system.service.IScheduleService;

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
}
