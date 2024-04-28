package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 问诊对象 registration
 * 
 * @author WilliamWu
 * @date 2024-04-24
 */
public class Registration extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 医保卡号 */
    @Excel(name = "医保卡号")
    private String patientid;

    /** 患者姓名 */
    @Excel(name = "患者姓名")
    private String patientname;

    /** 挂号费 */
    @Excel(name = "挂号费")
    private Long fee;

    /** 性别 */
    @Excel(name = "性别")
    private String patientsex;

    /** 科室 */
    private String departmentid;

    /** 医生工号 */
    private String doctorid;

    /** 日期 */
    @Excel(name = "日期")
    private String date;

    /** 时间 */
    @Excel(name = "时间")
    private String time;

    /** 诊断 */
    @Excel(name = "诊断")
    private String message;

    /** 完成标记 */
    private String delFlag;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setPatientid(String patientid) 
    {
        this.patientid = patientid;
    }

    public String getPatientid() 
    {
        return patientid;
    }
    public void setPatientname(String patientname) 
    {
        this.patientname = patientname;
    }

    public String getPatientname() 
    {
        return patientname;
    }
    public void setFee(Long fee) 
    {
        this.fee = fee;
    }

    public Long getFee() 
    {
        return fee;
    }
    public void setPatientsex(String patientsex) 
    {
        this.patientsex = patientsex;
    }

    public String getPatientsex() 
    {
        return patientsex;
    }
    public void setDepartmentid(String departmentid) 
    {
        this.departmentid = departmentid;
    }

    public String getDepartmentid() 
    {
        return departmentid;
    }
    public void setDoctorid(String doctorid) 
    {
        this.doctorid = doctorid;
    }

    public String getDoctorid() 
    {
        return doctorid;
    }
    public void setDate(String date) 
    {
        this.date = date;
    }

    public String getDate() 
    {
        return date;
    }
    public void setTime(String time) 
    {
        this.time = time;
    }

    public String getTime() 
    {
        return time;
    }
    public void setMessage(String message) 
    {
        this.message = message;
    }

    public String getMessage() 
    {
        return message;
    }
    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("patientid", getPatientid())
            .append("patientname", getPatientname())
            .append("fee", getFee())
            .append("patientsex", getPatientsex())
            .append("departmentid", getDepartmentid())
            .append("doctorid", getDoctorid())
            .append("date", getDate())
            .append("time", getTime())
            .append("message", getMessage())
            .append("delFlag", getDelFlag())
            .toString();
    }
}
