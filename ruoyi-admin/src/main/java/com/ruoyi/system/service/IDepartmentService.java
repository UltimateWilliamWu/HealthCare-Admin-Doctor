package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.Department;

/**
 * 科室管理Service接口
 * 
 * @author William Wu
 * @date 2024-04-20
 */
public interface IDepartmentService 
{
    /**
     * 查询科室管理
     * 
     * @param id 科室管理主键
     * @return 科室管理
     */
    public Department selectDepartmentById(Integer id);

    /**
     * 查询科室管理列表
     * 
     * @param department 科室管理
     * @return 科室管理集合
     */
    public List<Department> selectDepartmentList(Department department);

    /**
     * 新增科室管理
     * 
     * @param department 科室管理
     * @return 结果
     */
    public int insertDepartment(Department department);

    /**
     * 修改科室管理
     * 
     * @param department 科室管理
     * @return 结果
     */
    public int updateDepartment(Department department);

    /**
     * 批量删除科室管理
     * 
     * @param ids 需要删除的科室管理主键集合
     * @return 结果
     */
    public int deleteDepartmentByIds(Integer[] ids);

    /**
     * 删除科室管理信息
     * 
     * @param id 科室管理主键
     * @return 结果
     */
    public int deleteDepartmentById(Integer id);
}
