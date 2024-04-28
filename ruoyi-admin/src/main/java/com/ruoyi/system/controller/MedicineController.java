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
import com.ruoyi.system.domain.Medicine;
import com.ruoyi.system.service.IMedicineService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 库存管理Controller
 * 
 * @author William Wu
 * @date 2024-04-20
 */
@RestController
@RequestMapping("/system/medicine")
public class MedicineController extends BaseController
{
    @Autowired
    private IMedicineService medicineService;

    /**
     * 查询库存管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:medicine:list')")
    @GetMapping("/list")
    public TableDataInfo list(Medicine medicine)
    {
        startPage();
        List<Medicine> list = medicineService.selectMedicineList(medicine);
        return getDataTable(list);
    }

    /**
     * 导出库存管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:medicine:export')")
    @Log(title = "库存管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Medicine medicine)
    {
        List<Medicine> list = medicineService.selectMedicineList(medicine);
        ExcelUtil<Medicine> util = new ExcelUtil<Medicine>(Medicine.class);
        util.exportExcel(response, list, "库存管理数据");
    }

    /**
     * 获取库存管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:medicine:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return success(medicineService.selectMedicineById(id));
    }

    /**
     * 新增库存管理
     */
    @PreAuthorize("@ss.hasPermi('system:medicine:add')")
    @Log(title = "库存管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Medicine medicine)
    {
        return toAjax(medicineService.insertMedicine(medicine));
    }

    /**
     * 修改库存管理
     */
    @PreAuthorize("@ss.hasPermi('system:medicine:edit')")
    @Log(title = "库存管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Medicine medicine)
    {
        return toAjax(medicineService.updateMedicine(medicine));
    }

    /**
     * 删除库存管理
     */
    @PreAuthorize("@ss.hasPermi('system:medicine:remove')")
    @Log(title = "库存管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(medicineService.deleteMedicineByIds(ids));
    }
}
