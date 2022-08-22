package org.jeecg.modules.demo.test.mapper;

import java.util.List;
import org.jeecg.modules.demo.test.entity.TestStudent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 学生表
 * @Author: jeecg-boot
 * @Date:   2022-08-22
 * @Version: V1.0
 */
public interface TestStudentMapper extends BaseMapper<TestStudent> {

	/**
	 * 通过主表id删除子表数据
	 *
	 * @param mainId 主表id
	 * @return boolean
	 */
	public boolean deleteByMainId(@Param("mainId") String mainId);

  /**
   * 通过主表id查询子表数据
   *
   * @param mainId 主表id
   * @return List<TestStudent>
   */
	public List<TestStudent> selectByMainId(@Param("mainId") String mainId);
}
