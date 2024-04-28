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
import com.ruoyi.system.domain.Ward;
import com.ruoyi.system.service.IWardService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 病床管理Controller
 * 
 * @author William Wu
 * @date 2024-04-20
 */
@RestController
@RequestMapping("/system/ward")
public class WardController extends BaseController
{
    @Autowired
    private IWardService wardService;

    /**
     * 查询病床管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:ward:list')")
    @GetMapping("/list")
    public TableDataInfo list(Ward ward)
    {
        startPage();
        List<Ward> list = wardService.selectWardList(ward);
        return getDataTable(list);
    }

    /**
     * 导出病床管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:ward:export')")
    @Log(title = "病床管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Ward ward)
    {
        List<Ward> list = wardService.selectWardList(ward);
        ExcelUtil<Ward> util = new ExcelUtil<Ward>(Ward.class);
        util.exportExcel(response, list, "病床管理数据");
    }

    /**
     * 获取病床管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:ward:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return success(wardService.selectWardById(id));
    }

    /**
     * 新增病床管理
     */
    @PreAuthorize("@ss.hasPermi('system:ward:add')")
    @Log(title = "病床管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Ward ward)
    {
        return toAjax(wardService.insertWard(ward));
    }

    /**
     * 修改病床管理
     */
    @PreAuthorize("@ss.hasPermi('system:ward:edit')")
    @Log(title = "病床管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Ward ward)
    {
        return toAjax(wardService.updateWard(ward));
    }

    /**
     * 删除病床管理
     */
    @PreAuthorize("@ss.hasPermi('system:ward:remove')")
    @Log(title = "病床管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(wardService.deleteWardByIds(ids));
    }
}
