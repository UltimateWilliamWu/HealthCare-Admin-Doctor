package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 库存管理对象 medicine
 *
 * @author William Wu
 * @date 2024-04-20
 */
public class Medicine extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 药品编号 */
    @Excel(name = "药品编号")
    private Integer id;

    /** 药品名 */
    @Excel(name = "药品名")
    private String name;

    /** 制造商 */
    @Excel(name = "制造商")
    private String manufacturer;

    /** 价格 */
    @Excel(name = "价格")
    private Double price;

    /** 数量 */
    @Excel(name = "数量")
    private Long quantity;

    /** 剂型 */
    @Excel(name = "种类")
    private String dosage;

    /** 单位 */
    @Excel(name = "科室")
    private String unit;

    /** 规格 */
    @Excel(name = "规格")
    private String specification;

    /** 生产日期 */
    @Excel(name = "生产日期")
    private String productiondate;

    /** 保质期 */
    @Excel(name = "保质期")
    private String dateofuse;

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setManufacturer(String manufacturer)
    {
        this.manufacturer = manufacturer;
    }

    public String getManufacturer()
    {
        return manufacturer;
    }
    public void setPrice(Double price)
    {
        this.price = price;
    }

    public Double getPrice()
    {
        return price;
    }
    public void setQuantity(Long quantity)
    {
        this.quantity = quantity;
    }

    public Long getQuantity()
    {
        return quantity;
    }
    public void setDosage(String dosage)
    {
        this.dosage = dosage;
    }

    public String getDosage()
    {
        return dosage;
    }
    public void setUnit(String unit)
    {
        this.unit = unit;
    }

    public String getUnit()
    {
        return unit;
    }
    public void setSpecification(String specification)
    {
        this.specification = specification;
    }

    public String getSpecification()
    {
        return specification;
    }
    public void setProductiondate(String productiondate)
    {
        this.productiondate = productiondate;
    }

    public String getProductiondate()
    {
        return productiondate;
    }
    public void setDateofuse(String dateofuse)
    {
        this.dateofuse = dateofuse;
    }

    public String getDateofuse()
    {
        return dateofuse;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("manufacturer", getManufacturer())
            .append("price", getPrice())
            .append("quantity", getQuantity())
            .append("dosage", getDosage())
            .append("unit", getUnit())
            .append("specification", getSpecification())
            .append("productiondate", getProductiondate())
            .append("dateofuse", getDateofuse())
            .toString();
    }
}
