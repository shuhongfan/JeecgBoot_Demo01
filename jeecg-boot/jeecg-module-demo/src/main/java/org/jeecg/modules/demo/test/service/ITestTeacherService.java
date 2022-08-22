package org.jeecg.modules.demo.test.service;

import org.jeecg.modules.demo.test.entity.TestStudent;
import org.jeecg.modules.demo.test.entity.TestTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 教师
 * @Author: jeecg-boot
 * @Date:   2022-08-22
 * @Version: V1.0
 */
public interface ITestTeacherService extends IService<TestTeacher> {

	/**
	 * 添加一对多
	 *
	 * @param testTeacher
	 * @param testStudentList
	 */
	public void saveMain(TestTeacher testTeacher,List<TestStudent> testStudentList) ;
	
	/**
	 * 修改一对多
	 *
   * @param testTeacher
   * @param testStudentList
	 */
	public void updateMain(TestTeacher testTeacher,List<TestStudent> testStudentList);
	
	/**
	 * 删除一对多
	 *
	 * @param id
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 *
	 * @param idList
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);
	
}
