package com.ruoyi.system.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson2.JSONObject;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.Schedule;
import com.ruoyi.system.service.IScheduleService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 排班总览Controller
 *
 * @author William Wu
 * @date 2024-04-27
 */
@RestController
@RequestMapping("/system/scheduleAll")
public class ScheduleController extends BaseController
{
    @Autowired
    private IScheduleService scheduleService;

    /**
     * 查询排班总览列表
     */
    @PreAuthorize("@ss.hasPermi('system:scheduleAll:list')")
    @GetMapping("/list")
    public TableDataInfo list(Schedule schedule)
    {
        startPage();
        List<Schedule> list = scheduleService.selectScheduleList(schedule);
        return getDataTable(list);
    }

    /**
     * 导出排班总览列表
     */
    @PreAuthorize("@ss.hasPermi('system:scheduleAll:export')")
    @Log(title = "排班总览", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Schedule schedule)
    {
        List<Schedule> list = scheduleService.selectScheduleList(schedule);
        ExcelUtil<Schedule> util = new ExcelUtil<Schedule>(Schedule.class);
        util.exportExcel(response, list, "排班总览数据");
    }

    /**
     * 获取排班总览详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:scheduleAll:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(scheduleService.selectScheduleById(id));
    }

    /**
     * 新增排班总览
     */
    @PreAuthorize("@ss.hasPermi('system:scheduleAll:add')")
    @Log(title = "排班总览", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Schedule schedule)
    {
        return toAjax(scheduleService.insertSchedule(schedule));
    }

    /**
     * 修改排班总览
     */
    @PreAuthorize("@ss.hasPermi('system:scheduleAll:edit')")
    @Log(title = "排班总览", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Schedule schedule)
    {
        return toAjax(scheduleService.updateSchedule(schedule));
    }

    /**
     * 删除排班总览
     */
    @PreAuthorize("@ss.hasPermi('system:scheduleAll:remove')")
    @Log(title = "排班总览", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(scheduleService.deleteScheduleByIds(ids));
    }

    /*单独添加排班*/
    @PostMapping ("/addSchedule")
    public void addSchedule(@RequestBody Map<String, Object> requestData) {
        transferData(requestData);
    }

    //批量添加数据
    @PostMapping("/addBatchSchedule")
    public void addBatchSchedule(@RequestBody List<Map<String, Object>> requestDataList){
        for (Map<String, Object> requestData : requestDataList) {
            // 手动映射属性到实体类对象（如果需要的话）
            // 示例：创建 Schedule 对象并设置属性值
            transferData(requestData);
            // 处理实体类对象
            // 示例：保存到数据库或进行其他操作
        }
    }

    //获取排班信息
    @GetMapping("/getSchedule")
    public TableDataInfo getSchedule(String query)
    {
        Schedule schedule=new Schedule();
        System.out.println(query);
        schedule.setCategory(query);
        startPage();
        List<Schedule> list = scheduleService.selectScheduleList(schedule);
        return getDataTable(list);
    }



    //将jason数据传给实体类
    private void transferData(@RequestBody Map<String, Object> requestData) {
        Schedule schedule = new Schedule();
        schedule.setShifts((String) requestData.get("shiftName")); // 注意属性名的匹配
        schedule.setCategory((String) requestData.get("groupName")); // 注意属性名的匹配
        schedule.setBegin((String) requestData.get("startTime")); // 注意属性名的匹配
        schedule.setEnd((String) requestData.get("endTime")); // 注意属性名的匹配
        schedule.setDate((String) requestData.get("date")); // 注意属性名的匹配
        scheduleService.insertSchedule(schedule);
    }
}
