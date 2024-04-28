package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.Doctor;

/**
 * 医生管理Service接口
 * 
 * @author William Wu
 * @date 2024-04-20
 */
public interface IDoctorService 
{
    /**
     * 查询医生管理
     * 
     * @param id 医生管理主键
     * @return 医生管理
     */
    public Doctor selectDoctorById(Integer id);

    /**
     * 查询医生管理列表
     * 
     * @param doctor 医生管理
     * @return 医生管理集合
     */
    public List<Doctor> selectDoctorList(Doctor doctor);

    /**
     * 新增医生管理
     * 
     * @param doctor 医生管理
     * @return 结果
     */
    public int insertDoctor(Doctor doctor);

    /**
     * 修改医生管理
     * 
     * @param doctor 医生管理
     * @return 结果
     */
    public int updateDoctor(Doctor doctor);

    /**
     * 批量删除医生管理
     * 
     * @param ids 需要删除的医生管理主键集合
     * @return 结果
     */
    public int deleteDoctorByIds(Integer[] ids);

    /**
     * 删除医生管理信息
     * 
     * @param id 医生管理主键
     * @return 结果
     */
    public int deleteDoctorById(Integer id);
}
