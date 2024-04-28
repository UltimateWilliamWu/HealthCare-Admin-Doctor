package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.MessageUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.manager.AsyncManager;
import com.ruoyi.framework.manager.factory.AsyncFactory;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.DoctorMapper;
import com.ruoyi.system.domain.Doctor;
import com.ruoyi.system.service.IDoctorService;

/**
 * 医生管理Service业务层处理
 *
 * @author William Wu
 * @date 2024-04-20
 */
@Service
public class DoctorServiceImpl implements IDoctorService
{
    @Autowired
    private DoctorMapper doctorMapper;
    @Autowired
    private ISysUserService userService;

    private void registerDoctor(Doctor doctor){
        SysUser sysUser = new SysUser();
        String username=doctor.getDoctorname();
        String password=doctor.getPassword();
        sysUser.setUserName(username);
        // 验证码开关
        if (StringUtils.isEmpty(username))
        {
            throw new ServiceException("用户名不能为空！");
        }
        else if (StringUtils.isEmpty(password))
        {
            throw new ServiceException("用户密码不能为空！");
        }
        else if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH)
        {
            throw new ServiceException("账户长度必须在2到20个字符之间！");
        }
        else if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH)
        {
            throw new ServiceException("密码长度必须在5到20个字符之间！");
        }
        else if (!userService.checkUserNameUnique(sysUser))
        {
            throw new ServiceException("注册账号已存在!");
        }
        else
        {
            sysUser.setNickName(username);
            sysUser.setPassword(SecurityUtils.encryptPassword(password));
            boolean regFlag = userService.registerUser(sysUser);
            if (!regFlag)
            {
                throw new ServiceException("注册失败,请联系系统管理人员！");
            }
            else
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.REGISTER, MessageUtils.message("user.register.success")));
            }
        }
    }
    /**
     * 查询医生管理
     *
     * @param id 医生管理主键
     * @return 医生管理
     */
    @Override
    public Doctor selectDoctorById(Integer id)
    {
        return doctorMapper.selectDoctorById(id);
    }

    /**
     * 查询医生管理列表
     *
     * @param doctor 医生管理
     * @return 医生管理
     */
    @Override
    public List<Doctor> selectDoctorList(Doctor doctor)
    {
        return doctorMapper.selectDoctorList(doctor);
    }

    /**
     * 新增医生管理
     *
     * @param doctor 医生管理
     * @return 结果
     */
    @Override
    public int insertDoctor(Doctor doctor)
    {
        registerDoctor(doctor);
        return doctorMapper.insertDoctor(doctor);
    }

    /**
     * 修改医生管理
     *
     * @param doctor 医生管理
     * @return 结果
     */
    @Override
    public int updateDoctor(Doctor doctor)
    {
        return doctorMapper.updateDoctor(doctor);
    }

    /**
     * 批量删除医生管理
     *
     * @param ids 需要删除的医生管理主键
     * @return 结果
     */
    @Override
    public int deleteDoctorByIds(Integer[] ids)
    {
        return doctorMapper.deleteDoctorByIds(ids);
    }

    /**
     * 删除医生管理信息
     *
     * @param id 医生管理主键
     * @return 结果
     */
    @Override
    public int deleteDoctorById(Integer id)
    {
        return doctorMapper.deleteDoctorById(id);
    }
}
