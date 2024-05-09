package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.RegistrationMapper;
import com.ruoyi.system.domain.Registration;
import com.ruoyi.system.service.IRegistrationService;

/**
 * 问诊Service业务层处理
 *
 * @author WilliamWu
 * @date 2024-04-24
 */
@Service
public class RegistrationServiceImpl implements IRegistrationService
{
    @Autowired
    private RegistrationMapper registrationMapper;

    /**
     * 查询问诊
     *
     * @param id 问诊主键
     * @return 问诊
     */
    @Override
    public Registration selectRegistrationById(Long id)
    {
        return registrationMapper.selectRegistrationById(id);
    }

    /**
     * 查询问诊列表
     *
     * @param registration 问诊
     * @return 问诊
     */
    @Override
    public List<Registration> selectRegistrationList(Registration registration)
    {
        return registrationMapper.selectRegistrationList(registration);
    }

    /**
     * 新增问诊
     *
     * @param registration 问诊
     * @return 结果
     */
    @Override
    public int insertRegistration(Registration registration)
    {
        return registrationMapper.insertRegistration(registration);
    }

    /**
     * 修改问诊
     *
     * @param registration 问诊
     * @return 结果
     */
    @Override
    public int updateRegistration(Registration registration)
    {
        return registrationMapper.updateRegistration(registration);
    }

    /**
     * 批量删除问诊
     *
     * @param ids 需要删除的问诊主键
     * @return 结果
     */
    @Override
    public int deleteRegistrationByIds(Long[] ids)
    {
        return registrationMapper.deleteRegistrationByIds(ids);
    }

    /**
     * 删除问诊信息
     *
     * @param id 问诊主键
     * @return 结果
     */
    @Override
    public int deleteRegistrationById(Long id)
    {
        return registrationMapper.deleteRegistrationById(id);
    }

    @Override
    public List<Registration> selectPatientByDoctorName(String username) {
        return registrationMapper.selectPatientByDoctorName(username);
    }
}
