package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 医生管理对象 doctor
 * 
 * @author William Wu
 * @date 2024-04-20
 */
public class Doctor extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 工号 */
    private Integer id;

    /** 科室名 */
    @Excel(name = "科室名")
    private String department;

    /** 医生名 */
    @Excel(name = "医生名")
    private String doctorname;

    /** 性别 */
    @Excel(name = "性别")
    private String sex;

    /** 职位 */
    @Excel(name = "职位")
    private String position;

    /** 挂号费 */
    @Excel(name = "挂号费")
    private Double salary;

    /** 密码 */
    @Excel(name = "密码")
    private String password;

    /** 备注 */
    @Excel(name = "备注")
    private String message;

    public void setId(Integer id) 
    {
        this.id = id;
    }

    public Integer getId() 
    {
        return id;
    }
    public void setDepartment(String department) 
    {
        this.department = department;
    }

    public String getDepartment() 
    {
        return department;
    }
    public void setDoctorname(String doctorname) 
    {
        this.doctorname = doctorname;
    }

    public String getDoctorname() 
    {
        return doctorname;
    }
    public void setSex(String sex) 
    {
        this.sex = sex;
    }

    public String getSex() 
    {
        return sex;
    }
    public void setPosition(String position) 
    {
        this.position = position;
    }

    public String getPosition() 
    {
        return position;
    }
    public void setSalary(Double salary) 
    {
        this.salary = salary;
    }

    public Double getSalary() 
    {
        return salary;
    }
    public void setPassword(String password) 
    {
        this.password = password;
    }

    public String getPassword() 
    {
        return password;
    }
    public void setMessage(String message) 
    {
        this.message = message;
    }

    public String getMessage() 
    {
        return message;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("department", getDepartment())
            .append("doctorname", getDoctorname())
            .append("sex", getSex())
            .append("position", getPosition())
            .append("salary", getSalary())
            .append("password", getPassword())
            .append("message", getMessage())
            .toString();
    }
}
