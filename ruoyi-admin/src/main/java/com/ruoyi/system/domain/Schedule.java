package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 排班总览对象 schedule
 *
 * @author William Wu
 * @date 2024-04-27
 */
public class Schedule extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private int id;

    /** 日期 */
    @Excel(name = "日期")
    private String date;

    /** 班次 */
    @Excel(name = "班次")
    private String shifts;

    /** 班别 */
    @Excel(name = "班别")
    private String category;

    /** 开始 */
    @Excel(name = "开始")
    private String begin;

    /** 结束 */
    @Excel(name = "结束")
    private String end;

    public void setId(int id)
    {
        this.id = id;
    }

    public int getId()
    {
        return id;
    }
    public void setDate(String date)
    {
        this.date = date;
    }

    public String getDate()
    {
        return date;
    }
    public void setShifts(String shifts)
    {
        this.shifts = shifts;
    }

    public String getShifts()
    {
        return shifts;
    }
    public void setCategory(String category)
    {
        this.category = category;
    }

    public String getCategory()
    {
        return category;
    }
    public void setBegin(String begin)
    {
        this.begin = begin;
    }

    public String getBegin()
    {
        return begin;
    }
    public void setEnd(String end)
    {
        this.end = end;
    }

    public String getEnd()
    {
        return end;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("date", getDate())
            .append("shifts", getShifts())
            .append("category", getCategory())
            .append("begin", getBegin())
            .append("end", getEnd())
            .toString();
    }
}
