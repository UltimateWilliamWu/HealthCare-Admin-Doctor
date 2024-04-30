package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.Examination;

/**
 * 检查费设置Mapper接口
 *
 * @author William Wu
 * @date 2024-04-24
 */
public interface ExaminationMapper
{
    /**
     * 查询检查费设置
     *
     * @param id 检查费设置主键
     * @return 检查费设置
     */
    public Examination selectExaminationById(Long id);

    /**
     * 查询检查费设置列表
     *
     * @param examination 检查费设置
     * @return 检查费设置集合
     */
    public List<Examination> selectExaminationList(Examination examination);

    /**
     * 新增检查费设置
     *
     * @param examination 检查费设置
     * @return 结果
     */
    public int insertExamination(Examination examination);

    /**
     * 修改检查费设置
     *
     * @param examination 检查费设置
     * @return 结果
     */
    public int updateExamination(Examination examination);

    /**
     * 删除检查费设置
     *
     * @param id 检查费设置主键
     * @return 结果
     */
    public int deleteExaminationById(Long id);

    /**
     * 批量删除检查费设置
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteExaminationByIds(Long[] ids);

    public List<Examination>selectExaminationByDept(String dept);
}
