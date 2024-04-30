package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.Medicine;

/**
 * 库存管理Service接口
 *
 * @author William Wu
 * @date 2024-04-20
 */
public interface IMedicineService
{
    /**
     * 查询库存管理
     *
     * @param id 库存管理主键
     * @return 库存管理
     */
    public Medicine selectMedicineById(Integer id);

    /**
     * 查询库存管理列表
     *
     * @param medicine 库存管理
     * @return 库存管理集合
     */
    public List<Medicine> selectMedicineList(Medicine medicine);

    /**
     * 新增库存管理
     *
     * @param medicine 库存管理
     * @return 结果
     */
    public int insertMedicine(Medicine medicine);

    /**
     * 修改库存管理
     *
     * @param medicine 库存管理
     * @return 结果
     */
    public int updateMedicine(Medicine medicine);

    /**
     * 批量删除库存管理
     *
     * @param ids 需要删除的库存管理主键集合
     * @return 结果
     */
    public int deleteMedicineByIds(Integer[] ids);

    /**
     * 删除库存管理信息
     *
     * @param id 库存管理主键
     * @return 结果
     */
    public int deleteMedicineById(Integer id);

    public int outboundsMedicine(Medicine medicine);

    public List<Medicine> selectMedicineByDept(String dept);
}
