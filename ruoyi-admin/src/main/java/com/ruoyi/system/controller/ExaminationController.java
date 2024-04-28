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
import com.ruoyi.system.domain.Examination;
import com.ruoyi.system.service.IExaminationService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 检查费设置Controller
 * 
 * @author William Wu
 * @date 2024-04-24
 */
@RestController
@RequestMapping("/system/examination")
public class ExaminationController extends BaseController
{
    @Autowired
    private IExaminationService examinationService;

    /**
     * 查询检查费设置列表
     */
    @PreAuthorize("@ss.hasPermi('system:examination:list')")
    @GetMapping("/list")
    public TableDataInfo list(Examination examination)
    {
        startPage();
        List<Examination> list = examinationService.selectExaminationList(examination);
        return getDataTable(list);
    }

    /**
     * 导出检查费设置列表
     */
    @PreAuthorize("@ss.hasPermi('system:examination:export')")
    @Log(title = "检查费设置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Examination examination)
    {
        List<Examination> list = examinationService.selectExaminationList(examination);
        ExcelUtil<Examination> util = new ExcelUtil<Examination>(Examination.class);
        util.exportExcel(response, list, "检查费设置数据");
    }

    /**
     * 获取检查费设置详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:examination:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(examinationService.selectExaminationById(id));
    }

    /**
     * 新增检查费设置
     */
    @PreAuthorize("@ss.hasPermi('system:examination:add')")
    @Log(title = "检查费设置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Examination examination)
    {
        return toAjax(examinationService.insertExamination(examination));
    }

    /**
     * 修改检查费设置
     */
    @PreAuthorize("@ss.hasPermi('system:examination:edit')")
    @Log(title = "检查费设置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Examination examination)
    {
        return toAjax(examinationService.updateExamination(examination));
    }

    /**
     * 删除检查费设置
     */
    @PreAuthorize("@ss.hasPermi('system:examination:remove')")
    @Log(title = "检查费设置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(examinationService.deleteExaminationByIds(ids));
    }
}
