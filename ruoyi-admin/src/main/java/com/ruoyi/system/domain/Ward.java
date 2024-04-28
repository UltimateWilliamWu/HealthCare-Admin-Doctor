package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 病床管理对象 ward
 * 
 * @author William Wu
 * @date 2024-04-20
 */
public class Ward extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @Excel(name = "编号")
    private Integer id;

    /** 病房号 */
    @Excel(name = "病房号")
    private String building;

    /** 病床号 */
    @Excel(name = "病床号")
    private String bed;

    /** 护理等级 */
    @Excel(name = "护理等级")
    private String level;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 备注 */
    @Excel(name = "备注")
    private String note;

    public void setId(Integer id) 
    {
        this.id = id;
    }

    public Integer getId() 
    {
        return id;
    }
    public void setBuilding(String building) 
    {
        this.building = building;
    }

    public String getBuilding() 
    {
        return building;
    }
    public void setBed(String bed) 
    {
        this.bed = bed;
    }

    public String getBed() 
    {
        return bed;
    }
    public void setLevel(String level) 
    {
        this.level = level;
    }

    public String getLevel() 
    {
        return level;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setNote(String note) 
    {
        this.note = note;
    }

    public String getNote() 
    {
        return note;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("building", getBuilding())
            .append("bed", getBed())
            .append("level", getLevel())
            .append("status", getStatus())
            .append("note", getNote())
            .toString();
    }
}
