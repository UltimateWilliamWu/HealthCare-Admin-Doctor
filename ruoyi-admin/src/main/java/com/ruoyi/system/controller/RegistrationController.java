package com.ruoyi.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
}
