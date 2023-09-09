package com.dhf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dhf.domain.Department;
import com.dhf.service.DepartmentService;
import com.dhf.mapper.DepartmentMapper;
import org.springframework.stereotype.Service;

/**
* @author lenvoo
* @description 针对表【sys_department(系统部门表)】的数据库操作Service实现
* @createDate 2023-09-09 10:55:29
*/
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department>
    implements DepartmentService{

}




