package com.ruoyi.system.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.system.domain.Examination;
import com.ruoyi.system.domain.Medicine;
import com.ruoyi.system.service.IExaminationService;
import com.ruoyi.system.service.IMedicineService;
import com.ruoyi.system.service.ISysDeptService;
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
import com.ruoyi.system.domain.Registration;
import com.ruoyi.system.service.IRegistrationService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 问诊Controller
 *
 * @author WilliamWu
 * &#064;date  2024-04-24
 */
@RestController
@RequestMapping("/system/registration")
public class RegistrationController extends BaseController
{
    @Autowired
    private IRegistrationService registrationService;
    @Autowired
    private ISysDeptService sysDeptService;
    @Autowired
    private IMedicineService medicineService;
    @Autowired
    private IExaminationService examinationService;

    /**
     * 查询问诊列表
     */
    @PreAuthorize("@ss.hasPermi('system:registration:list')")
    @GetMapping("/list")
    public TableDataInfo list(Registration registration)
    {
        startPage();
        List<Registration> list = registrationService.selectRegistrationList(registration);
        return getDataTable(list);
    }

    /**
     * 导出问诊列表
     */
    @PreAuthorize("@ss.hasPermi('system:registration:export')")
    @Log(title = "问诊", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Registration registration)
    {
        List<Registration> list = registrationService.selectRegistrationList(registration);
        ExcelUtil<Registration> util = new ExcelUtil<Registration>(Registration.class);
        util.exportExcel(response, list, "问诊数据");
    }

    /**
     * 获取问诊详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:registration:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(registrationService.selectRegistrationById(id));
    }

    /**
     * 新增问诊
     */
    @PreAuthorize("@ss.hasPermi('system:registration:add')")
    @Log(title = "问诊", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Registration registration)
    {
        return toAjax(registrationService.insertRegistration(registration));
    }

    /**
     * 修改问诊
     */
    @PreAuthorize("@ss.hasPermi('system:registration:edit')")
    @Log(title = "问诊", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Registration registration)
    {
        registration.setDelFlag("1");
        return toAjax(registrationService.updateRegistration(registration));
    }

    /**
     * 删除问诊
     */
    @PreAuthorize("@ss.hasPermi('system:registration:remove')")
    @Log(title = "问诊", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(registrationService.deleteRegistrationByIds(ids));
    }

    @GetMapping("/chargeOptions")
    public List<Map<String, Object>> getChargeOptions() {
        List<Map<String, Object>> chargeOptions = new ArrayList<>();

        // 构建收费项目数据 分为药品费和检查费
        //选项一 药品费
        Map<String, Object> option1 = new HashMap<>();
        option1.put("label", "药品");
        option1.put("value","药品");
        //药品费子项 科室 根据科室分类药物 科室名作为value
        List<Map<String, Object>> Dept = new ArrayList<>();
        List<SysDept> sysDept=sysDeptService.selectChargeDept();
        for (SysDept dept : sysDept) {
            Map<String, Object> child1 = new HashMap<>();
            child1.put("label", dept.getDeptName());
            child1.put("value", dept.getDeptName());
            //科室子项 药物项 并将价格作为value
            List<Map<String, Object>> subChildren1 = new ArrayList<>();
            List<Medicine> medicines = medicineService.selectMedicineByDept(dept.getDeptName());
            for (Medicine medicine : medicines) {
                Map<String, Object> subChild1 = new HashMap<>();
                subChild1.put("label", medicine.getName());
                subChild1.put("value", medicine.getPrice());
                subChildren1.add(subChild1);
            }
            child1.put("children", subChildren1);
            Dept.add(child1);
        }
        option1.put("children", Dept);
        chargeOptions.add(option1);

        //选项二 检查费
        Map<String, Object> option2 = new HashMap<>();
        option2.put("label", "检查费");
        option2.put("value", "检查费");
        List<Map<String, Object>> Dept2 = new ArrayList<>();
        for (SysDept dept : sysDept) {
            Map<String, Object> child1 = new HashMap<>();
            child1.put("label", dept.getDeptName());
            child1.put("value", dept.getDeptName());
            //科室子项 药物项 并将价格作为value
            List<Map<String, Object>> subChildren1 = new ArrayList<>();
            List<Examination> examinations = examinationService.selectExaminationByDept(dept.getDeptName());
            for (Examination examination : examinations) {
                Map<String, Object> subChild1 = new HashMap<>();
                subChild1.put("label", examination.getName());
                subChild1.put("value", examination.getPrice());
                subChildren1.add(subChild1);
            }
            child1.put("children", subChildren1);
            Dept2.add(child1);
        }
        option2.put("children",Dept2);
        chargeOptions.add(option2);

        return chargeOptions;
    }

    @GetMapping(value = "/s/{username}")
    public TableDataInfo getRegistrations(@PathVariable String username){
        startPage();
        List<Registration> list = registrationService.selectPatientByDoctorName(username);
        return getDataTable(list);
    }

}
