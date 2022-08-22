package org.jeecg.modules.demo.test.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.common.system.vo.SelectTreeModel;
import org.jeecg.modules.demo.test.entity.TestTree;

import java.util.List;
import java.util.Map;

/**
 * @Description: 树测试
 * @Author: jeecg-boot
 * @Date:   2022-08-22
 * @Version: V1.0
 */
public interface TestTreeMapper extends BaseMapper<TestTree> {

	/**
	 * 编辑节点状态
	 * @param id
	 * @param status
	 */
	void updateTreeNodeStatus(@Param("id") String id,@Param("status") String status);

	/**
	 * 【vue3专用】根据父级ID查询树节点数据
	 *
	 * @param pid
	 * @param query
	 * @return
	 */
	List<SelectTreeModel> queryListByPid(@Param("pid") String pid, @Param("query") Map<String, String> query);

}
