package com.ruoyi.system.domain;

import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 科室管理对象 department
 * 
 * @author William Wu
 * @date 2024-04-20
 */
public class Department extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Integer id;

    /** 科室名 */
    @Excel(name = "科室名")
    private String departmentname;

    /** 级别 */
    @Excel(name = "级别")
    private String grade;

    /** 医生管理信息 */
    private List<Doctor> doctorList;

    public void setId(Integer id) 
    {
        this.id = id;
    }

    public Integer getId() 
    {
        return id;
    }
    public void setDepartmentname(String departmentname) 
    {
        this.departmentname = departmentname;
    }

    public String getDepartmentname() 
    {
        return departmentname;
    }
    public void setGrade(String grade) 
    {
        this.grade = grade;
    }

    public String getGrade() 
    {
        return grade;
    }

    public List<Doctor> getDoctorList()
    {
        return doctorList;
    }

    public void setDoctorList(List<Doctor> doctorList)
    {
        this.doctorList = doctorList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("departmentname", getDepartmentname())
            .append("grade", getGrade())
            .append("doctorList", getDoctorList())
            .toString();
    }
}
