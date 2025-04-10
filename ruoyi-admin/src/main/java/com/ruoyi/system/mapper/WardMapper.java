package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.Ward;

/**
 * 病床管理Mapper接口
 * 
 * @author William Wu
 * @date 2024-04-20
 */
public interface WardMapper 
{
    /**
     * 查询病床管理
     * 
     * @param id 病床管理主键
     * @return 病床管理
     */
    public Ward selectWardById(Integer id);

    /**
     * 查询病床管理列表
     * 
     * @param ward 病床管理
     * @return 病床管理集合
     */
    public List<Ward> selectWardList(Ward ward);

    /**
     * 新增病床管理
     * 
     * @param ward 病床管理
     * @return 结果
     */
    public int insertWard(Ward ward);

    /**
     * 修改病床管理
     * 
     * @param ward 病床管理
     * @return 结果
     */
    public int updateWard(Ward ward);

    /**
     * 删除病床管理
     * 
     * @param id 病床管理主键
     * @return 结果
     */
    public int deleteWardById(Integer id);

    /**
     * 批量删除病床管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWardByIds(Integer[] ids);
}
