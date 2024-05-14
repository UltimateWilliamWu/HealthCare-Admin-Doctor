package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.Schedule;

/**
 * 排班总览Service接口
 *
 * @author William Wu
 * @date 2024-04-27
 */
public interface IScheduleService
{
    /**
     * 查询排班总览
     *
     * @param id 排班总览主键
     * @return 排班总览
     */
    public Schedule selectScheduleById(String id);

    /**
     * 查询排班总览列表
     *
     * @param schedule 排班总览
     * @return 排班总览集合
     */
    public List<Schedule> selectScheduleList(Schedule schedule);

    /**
     * 新增排班总览
     *
     * @param schedule 排班总览
     * @return 结果
     */
    public int insertSchedule(Schedule schedule);

    /**
     * 修改排班总览
     *
     * @param schedule 排班总览
     * @return 结果
     */
    public int updateSchedule(Schedule schedule);

    /**
     * 批量删除排班总览
     *
     * @param ids 需要删除的排班总览主键集合
     * @return 结果
     */
    public int deleteScheduleByIds(String[] ids);

    /**
     * 删除排班总览信息
     *
     * @param id 排班总览主键
     * @return 结果
     */
    public int deleteScheduleById(String id);

    public String importSchedule(List<Schedule> userList, Boolean isUpdateSupport, String operName);
}
