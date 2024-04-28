package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.MedicineMapper;
import com.ruoyi.system.domain.Medicine;
import com.ruoyi.system.service.IMedicineService;

/**
 * 库存管理Service业务层处理
 *
 * @author William Wu
 * @date 2024-04-20
 */
@Service
public class MedicineServiceImpl implements IMedicineService
{
    @Autowired
    private MedicineMapper medicineMapper;

    /**
     * 查询库存管理
     *
     * @param id 库存管理主键
     * @return 库存管理
     */
    @Override
    public Medicine selectMedicineById(Integer id)
    {
        return medicineMapper.selectMedicineById(id);
    }

    /**
     * 查询库存管理列表
     *
     * @param medicine 库存管理
     * @return 库存管理
     */
    @Override
    public List<Medicine> selectMedicineList(Medicine medicine)
    {
        return medicineMapper.selectMedicineList(medicine);
    }

    /**
     * 新增库存管理
     *
     * @param medicine 库存管理
     * @return 结果
     */
    @Override
    public int insertMedicine(Medicine medicine)
    {
        return medicineMapper.insertMedicine(medicine);
    }

    /**
     * 修改库存管理
     *
     * @param medicine 库存管理
     * @return 结果
     */
    @Override
    public int updateMedicine(Medicine medicine)
    {
        return medicineMapper.updateMedicine(medicine);
    }

    /**
     * 批量删除库存管理
     *
     * @param ids 需要删除的库存管理主键
     * @return 结果
     */
    @Override
    public int deleteMedicineByIds(Integer[] ids)
    {
        return medicineMapper.deleteMedicineByIds(ids);
    }

    /**
     * 删除库存管理信息
     *
     * @param id 库存管理主键
     * @return 结果
     */
    @Override
    public int deleteMedicineById(Integer id)
    {
        return medicineMapper.deleteMedicineById(id);
    }

    @Override
    public int outboundsMedicine(Medicine medicine) {
        return medicineMapper.outboundMedicine(medicine);
    }
}
