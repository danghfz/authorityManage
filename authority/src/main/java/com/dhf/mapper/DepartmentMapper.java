package com.dhf.mapper;

import com.dhf.domain.Department;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author lenvoo
* @description 针对表【sys_department(系统部门表)】的数据库操作Mapper
* @createDate 2023-09-09 10:55:29
* @Entity com.dhf.domain.Department
*/
@Mapper
public interface DepartmentMapper extends BaseMapper<Department> {

}




