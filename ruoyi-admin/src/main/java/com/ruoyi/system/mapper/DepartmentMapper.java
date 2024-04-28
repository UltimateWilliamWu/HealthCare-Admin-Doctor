package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.Department;
import com.ruoyi.system.domain.Doctor;

/**
 * 科室管理Mapper接口
 * 
 * @author William Wu
 * @date 2024-04-20
 */
public interface DepartmentMapper 
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
     * 删除科室管理
     * 
     * @param id 科室管理主键
     * @return 结果
     */
    public int deleteDepartmentById(Integer id);

    /**
     * 批量删除科室管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDepartmentByIds(Integer[] ids);

    /**
     * 批量删除医生管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDoctorByDepartments(Integer[] ids);
    
    /**
     * 批量新增医生管理
     * 
     * @param doctorList 医生管理列表
     * @return 结果
     */
    public int batchDoctor(List<Doctor> doctorList);
    

    /**
     * 通过科室管理主键删除医生管理信息
     * 
     * @param id 科室管理ID
     * @return 结果
     */
    public int deleteDoctorByDepartment(Integer id);
}
