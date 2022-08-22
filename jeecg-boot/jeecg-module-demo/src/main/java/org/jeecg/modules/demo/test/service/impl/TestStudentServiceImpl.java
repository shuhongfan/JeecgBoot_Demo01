package org.jeecg.modules.demo.test.service.impl;

import org.jeecg.modules.demo.test.entity.TestStudent;
import org.jeecg.modules.demo.test.mapper.TestStudentMapper;
import org.jeecg.modules.demo.test.service.ITestStudentService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 学生表
 * @Author: jeecg-boot
 * @Date:   2022-08-22
 * @Version: V1.0
 */
@Service
public class TestStudentServiceImpl extends ServiceImpl<TestStudentMapper, TestStudent> implements ITestStudentService {
	
	@Autowired
	private TestStudentMapper testStudentMapper;
	
	@Override
	public List<TestStudent> selectByMainId(String mainId) {
		return testStudentMapper.selectByMainId(mainId);
	}
}
