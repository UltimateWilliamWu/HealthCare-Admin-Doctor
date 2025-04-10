package com.ruoyi.system.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson2.JSONObject;
import com.google.ortools.Loader;
import com.google.ortools.sat.*;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.system.domain.Examination;
import com.ruoyi.system.domain.Medicine;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.multipart.MultipartFile;

/**
 * 排班总览Controller
 *
 * @author William Wu
 * &#064;date  2024-04-27
 */
@RestController
@RequestMapping("/system/scheduleAll")
public class ScheduleController extends BaseController
{
    @Autowired
    private IScheduleService scheduleService;

    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<Schedule> util = new ExcelUtil<Schedule>(Schedule.class);
        List<Schedule> userList = util.importExcel(file.getInputStream());
        LoginUser loginUser = getLoginUser();
        String operName = loginUser.getUsername();
        String message = scheduleService.importSchedule(userList, updateSupport, operName);
        return AjaxResult.success(message);
    }

    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<Schedule> util = new ExcelUtil<Schedule>(Schedule.class);
        util.importTemplateExcel(response,"用户数据");
    }

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
	@DeleteMapping(value = "/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(scheduleService.deleteScheduleByIds(ids));
    }

    /**
     * 单独添加排班
     */
    @PostMapping ("/addSchedule")
    public void addSchedule(@RequestBody Map<String, Object> requestData) {
        transferData(requestData);
    }

    /**
     * 批量添加排班
     * @param requestDataList 获取前端传来的对象数组
     */
    @PostMapping("/addBatchSchedule")
    public void addBatchSchedule(@RequestBody List<Map<String, Object>> requestDataList){
        for (Map<String, Object> requestData : requestDataList) {
            transferData(requestData);
        }
    }

    /**
     * 获取对应科室的所有医生
     * @param query 医生名
     * @return 医生数组
     */
    @GetMapping("/getSchedule")
    public TableDataInfo getSchedule(String query)
    {
        Schedule schedule=new Schedule();
        schedule.setCategory(query);
        startPage();
        List<Schedule> list = scheduleService.selectScheduleList(schedule);
        return getDataTable(list);
    }

    /**
    * 智能排班
     * 使用google or tools 作为辅助算法工具
     * 约束条件：
     *  每个班次每天分配给一位护士。（硬性约束条件）hu
     *  每位护士每天最多工作一次。（硬性约束条件）
     *  尽可能均分班次（软性约束条件）
    */
    @RequestMapping("/automaticScheduling")
    public ResponseEntity<List<Schedule>> automaticScheduling(@RequestBody Map<String, Object> requestData){
        List<String> date = (List<String>) requestData.get("Date");
        System.out.println(date);
        List<String> doctors = (List<String>) requestData.get("Doctor");
        System.out.println(doctors);

        List<Schedule> scheduleList = new ArrayList<>();;

        Loader.loadNativeLibraries();
        final int numNurses = doctors.size();//医生列表
        final int numDays =date.size();//日期列表
        final int numShifts = 3;//班次 早中晚三班

        final int[] allNurses = IntStream.range(0, numNurses).toArray();
        final int[] allDays = IntStream.range(0, numDays).toArray();
        final int[] allShifts = IntStream.range(0, numShifts).toArray();

        // Creates the model.
        CpModel model = new CpModel();

        // Creates shift variables.
        // shifts[(n, d, s)]: 医生 'n' works 班次 's' on 日期 'd'.
        Literal[][][] shifts = new Literal[numNurses][numDays][numShifts];
        for (int n : allNurses) {
            for (int d : allDays) {
                for (int s : allShifts) {
                    shifts[n][d][s] = model.newBoolVar("shifts_n" + n + "d" + d + "s" + s);
                }
            }
        }

        // 同一班次只能由一位医生负责.
        for (int d : allDays) {
            for (int s : allShifts) {
                List<Literal> nurses = new ArrayList<>();
                for (int n : allNurses) {
                    nurses.add(shifts[n][d][s]);
                }
                model.addExactlyOne(nurses);
            }
        }

        // 每位医生每天只能轮一班.
        for (int n : allNurses) {
            for (int d : allDays) {
                List<Literal> work = new ArrayList<>();
                for (int s : allShifts) {
                    work.add(shifts[n][d][s]);
                }
                model.addAtMostOne(work);
            }
        }

        // Try to distribute the shifts evenly, so that each nurse works
        // minShiftsPerNurse shifts. If this is not possible, because the total
        // number of shifts is not divisible by the number of nurses, some nurses will
        // be assigned one more shift.
        int minShiftsPerNurse = (numShifts * numDays) / numNurses;
        int maxShiftsPerNurse;
        if ((numShifts * numDays) % numNurses == 0) {
            maxShiftsPerNurse = minShiftsPerNurse;
        } else {
            maxShiftsPerNurse = minShiftsPerNurse + 1;
        }
        for (int n : allNurses) {
            LinearExprBuilder shiftsWorked = LinearExpr.newBuilder();
            for (int d : allDays) {
                for (int s : allShifts) {
                    shiftsWorked.add(shifts[n][d][s]);
                }
            }
            model.addLinearConstraint(shiftsWorked, minShiftsPerNurse, maxShiftsPerNurse);
        }

        CpSolver solver = new CpSolver();
        solver.getParameters().setLinearizationLevel(0);
        // Tell the solver to enumerate all solutions.
        solver.getParameters().setEnumerateAllSolutions(true);

        // Display the first five solutions.给出的解决方案个数
        final int solutionLimit = 1;
        class VarArraySolutionPrinterWithLimit extends CpSolverSolutionCallback {
            public VarArraySolutionPrinterWithLimit(
                    int[] allNurses, int[] allDays, int[] allShifts, Literal[][][] shifts, int limit) {
                solutionCount = 0;
                this.allNurses = allNurses;
                this.allDays = allDays;
                this.allShifts = allShifts;
                this.shifts = shifts;
                solutionLimit = limit;
            }

            @Override
            public void onSolutionCallback() {
                System.out.printf("Solution #%d:%n", solutionCount);
                for (int d : allDays) {
                    System.out.printf("Day %s%n", date.get(d));
                    for (int n : allNurses) {
                        boolean isWorking = false;
                        for (int s : allShifts) {
                            if (booleanValue(shifts[n][d][s])) {
                                isWorking = true;
                                Schedule schedule=new Schedule();//创建排班对象
                                schedule.setDate(date.get(d));//设置排班日期
                                schedule.setCategory(doctors.get(n));//设置医生
                                //设置班次
                                switch (s){
                                    case 0:
                                        schedule.setShifts("早");
                                        schedule.setBegin(date.get(d)+" 00:00");
                                        schedule.setEnd(date.get(d)+" 08:00");
                                        break;
                                    case 1:
                                        schedule.setShifts("中");
                                        schedule.setBegin(date.get(d)+" 08:00");
                                        schedule.setEnd(date.get(d)+" 16:00");
                                        break;
                                    case 2:
                                        schedule.setShifts("晚");
                                        schedule.setBegin(date.get(d)+" 16:00");
                                        schedule.setEnd(date.get(d)+" 24:00");
                                        break;
                                }
                                scheduleList.add(schedule);
                                System.out.printf("  Doctor %s work shift %d%n", doctors.get(n), s);
                            }
                        }
                        if (!isWorking) {
                            System.out.printf("  Doctor %s does not work%n", doctors.get(n));
                        }
                    }
                }
                solutionCount++;
                if (solutionCount >= solutionLimit) {
                    System.out.printf("Stop search after %d solutions%n", solutionLimit);
                    stopSearch();
                }
            }

            public int getSolutionCount() {
                return solutionCount;
            }

            private int solutionCount;
            private final int[] allNurses;
            private final int[] allDays;
            private final int[] allShifts;
            private final Literal[][][] shifts;
            private final int solutionLimit;
        }

        VarArraySolutionPrinterWithLimit cb =
                new VarArraySolutionPrinterWithLimit(allNurses, allDays, allShifts, shifts, solutionLimit);

        // Creates a solver and solves the model.
        CpSolverStatus status = solver.solve(model, cb);
        System.out.println("Status: " + status);
        System.out.println(cb.getSolutionCount() + " solutions found.");

        // Statistics.
        System.out.println("Statistics");
        System.out.printf("  conflicts: %d%n", solver.numConflicts());
        System.out.printf("  branches : %d%n", solver.numBranches());
        System.out.printf("  wall time: %f s%n", solver.wallTime());

        return ResponseEntity.ok(scheduleList);
    }

    @RequestMapping("/addScheduling")
    public AjaxResult inboundsScheduling(@RequestBody List<Schedule> schedules) {
        for (Schedule schedule : schedules) {
            scheduleService.insertSchedule(schedule);
        }
        return AjaxResult.success("导入成功！");
    }

    /**
     * 工具方法
     * 从前端获取数据并将数据传给实体类
     */
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
