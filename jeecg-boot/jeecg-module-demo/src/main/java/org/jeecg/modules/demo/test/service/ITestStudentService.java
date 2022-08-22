package org.jeecg.modules.demo.test.service;

import org.jeecg.modules.demo.test.entity.TestStudent;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 学生表
 * @Author: jeecg-boot
 * @Date:   2022-08-22
 * @Version: V1.0
 */
public interface ITestStudentService extends IService<TestStudent> {

	/**
	 * 通过主表id查询子表数据
	 *
	 * @param mainId 主表id
	 * @return List<TestStudent>
	 */
	public List<TestStudent> selectByMainId(String mainId);
}
