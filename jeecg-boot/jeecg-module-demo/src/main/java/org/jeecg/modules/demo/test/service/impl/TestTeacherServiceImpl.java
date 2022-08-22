package org.jeecg.modules.demo.test.service.impl;

import org.jeecg.modules.demo.test.entity.TestTeacher;
import org.jeecg.modules.demo.test.entity.TestStudent;
import org.jeecg.modules.demo.test.mapper.TestStudentMapper;
import org.jeecg.modules.demo.test.mapper.TestTeacherMapper;
import org.jeecg.modules.demo.test.service.ITestTeacherService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 教师
 * @Author: jeecg-boot
 * @Date:   2022-08-22
 * @Version: V1.0
 */
@Service
public class TestTeacherServiceImpl extends ServiceImpl<TestTeacherMapper, TestTeacher> implements ITestTeacherService {

	@Autowired
	private TestTeacherMapper testTeacherMapper;
	@Autowired
	private TestStudentMapper testStudentMapper;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveMain(TestTeacher testTeacher, List<TestStudent> testStudentList) {
		testTeacherMapper.insert(testTeacher);
		if(testStudentList!=null && testStudentList.size()>0) {
			for(TestStudent entity:testStudentList) {
				//外键设置
				entity.setTeacherId(testTeacher.getId());
				testStudentMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateMain(TestTeacher testTeacher,List<TestStudent> testStudentList) {
		testTeacherMapper.updateById(testTeacher);
		
		//1.先删除子表数据
		testStudentMapper.deleteByMainId(testTeacher.getId());
		
		//2.子表数据重新插入
		if(testStudentList!=null && testStudentList.size()>0) {
			for(TestStudent entity:testStudentList) {
				//外键设置
				entity.setTeacherId(testTeacher.getId());
				testStudentMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delMain(String id) {
		testStudentMapper.deleteByMainId(id);
		testTeacherMapper.deleteById(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			testStudentMapper.deleteByMainId(id.toString());
			testTeacherMapper.deleteById(id);
		}
	}
	
}
