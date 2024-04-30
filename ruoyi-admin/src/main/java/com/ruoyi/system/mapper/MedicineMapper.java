package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.Medicine;

/**
 * 库存管理Mapper接口
 *
 * @author William Wu
 * @date 2024-04-20
 */
public interface MedicineMapper
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
     * 删除库存管理
     *
     * @param id 库存管理主键
     * @return 结果
     */
    public int deleteMedicineById(Integer id);

    /**
     * 批量删除库存管理
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMedicineByIds(Integer[] ids);

    public int outboundMedicine(Medicine medicine);

    public List<Medicine> selectMedicineByDept(String dept);
}
