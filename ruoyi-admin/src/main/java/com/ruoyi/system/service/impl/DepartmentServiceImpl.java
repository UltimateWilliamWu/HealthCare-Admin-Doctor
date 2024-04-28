package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.system.domain.Doctor;
import com.ruoyi.system.mapper.DepartmentMapper;
import com.ruoyi.system.domain.Department;
import com.ruoyi.system.service.IDepartmentService;

/**
 * 科室管理Service业务层处理
 *
 * @author William Wu
 * @date 2024-04-20
 */
@Service
public class DepartmentServiceImpl implements IDepartmentService
{
    @Autowired
    private DepartmentMapper departmentMapper;

    /**
     * 查询科室管理
     *
     * @param id 科室管理主键
     * @return 科室管理
     */
    @Override
    public Department selectDepartmentById(Integer id)
    {
        return departmentMapper.selectDepartmentById(id);
    }

    /**
     * 查询科室管理列表
     *
     * @param department 科室管理
     * @return 科室管理
     */
    @Override
    public List<Department> selectDepartmentList(Department department)
    {
        return departmentMapper.selectDepartmentList(department);
    }

    /**
     * 新增科室管理
     *
     * @param department 科室管理
     * @return 结果
     */
    @Transactional
    @Override
    public int insertDepartment(Department department)
    {
        int rows = departmentMapper.insertDepartment(department);
        insertDoctor(department);
        return rows;
    }

    /**
     * 修改科室管理
     *
     * @param department 科室管理
     * @return 结果
     */
    @Transactional
    @Override
    public int updateDepartment(Department department)
    {
        departmentMapper.deleteDoctorByDepartment(department.getId());
        insertDoctor(department);
        return departmentMapper.updateDepartment(department);
    }

    /**
     * 批量删除科室管理
     *
     * @param ids 需要删除的科室管理主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteDepartmentByIds(Integer[] ids)
    {
        departmentMapper.deleteDoctorByDepartments(ids);
        return departmentMapper.deleteDepartmentByIds(ids);
    }

    /**
     * 删除科室管理信息
     *
     * @param id 科室管理主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteDepartmentById(Integer id)
    {
        departmentMapper.deleteDoctorByDepartment(id);
        return departmentMapper.deleteDepartmentById(id);
    }

    /**
     * 新增医生管理信息
     *
     * @param department 科室管理对象
     */
    public void insertDoctor(Department department)
    {
        List<Doctor> doctorList = department.getDoctorList();
        Integer id = department.getId();
        if (StringUtils.isNotNull(doctorList))
        {
            List<Doctor> list = new ArrayList<Doctor>();
            for (Doctor doctor : doctorList)
            {
                doctor.setDepartment(String.valueOf(id));
                list.add(doctor);
            }
            if (!list.isEmpty())
            {
                departmentMapper.batchDoctor(list);
            }
        }
    }
}
