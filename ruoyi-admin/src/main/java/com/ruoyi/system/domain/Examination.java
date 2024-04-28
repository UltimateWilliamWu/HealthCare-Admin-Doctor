package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 检查费设置对象 examination
 * 
 * @author William Wu
 * @date 2024-04-24
 */
public class Examination extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 科室 */
    @Excel(name = "科室")
    private String department;

    /** 项目名 */
    @Excel(name = "项目名")
    private String name;

    /** 项目内容 */
    @Excel(name = "项目内容")
    private String content;

    /** 价格 */
    @Excel(name = "价格")
    private Double price;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
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
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }
    public void setPrice(Double price) 
    {
        this.price = price;
    }

    public Double getPrice() 
    {
        return price;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("department", getDepartment())
            .append("name", getName())
            .append("content", getContent())
            .append("price", getPrice())
            .toString();
    }
}
