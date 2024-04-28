package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.Registration;

/**
 * 问诊Mapper接口
 * 
 * @author WilliamWu
 * @date 2024-04-24
 */
public interface RegistrationMapper 
{
    /**
     * 查询问诊
     * 
     * @param id 问诊主键
     * @return 问诊
     */
    public Registration selectRegistrationById(Long id);

    /**
     * 查询问诊列表
     * 
     * @param registration 问诊
     * @return 问诊集合
     */
    public List<Registration> selectRegistrationList(Registration registration);

    /**
     * 新增问诊
     * 
     * @param registration 问诊
     * @return 结果
     */
    public int insertRegistration(Registration registration);

    /**
     * 修改问诊
     * 
     * @param registration 问诊
     * @return 结果
     */
    public int updateRegistration(Registration registration);

    /**
     * 删除问诊
     * 
     * @param id 问诊主键
     * @return 结果
     */
    public int deleteRegistrationById(Long id);

    /**
     * 批量删除问诊
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRegistrationByIds(Long[] ids);
}
