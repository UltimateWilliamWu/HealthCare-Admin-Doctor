package com.ruoyi.system.controller;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.Medicine;
import com.ruoyi.system.service.IMedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/system/outbounds")
public class OutboundController {
    @Autowired
    private IMedicineService medicineService;

    @RequestMapping
    public AjaxResult outboundsMedicine(@RequestBody List<Medicine> medicines) {
        for (Medicine medicine : medicines) {
            medicineService.outboundsMedicine(medicine);
        }
        return AjaxResult.success("导入成功！");
    }
}
