package org.jeecg.modules.demo.test.vo;

import java.util.List;
import org.jeecg.modules.demo.test.entity.TestTeacher;
import org.jeecg.modules.demo.test.entity.TestStudent;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelEntity;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 教师
 * @Author: jeecg-boot
 * @Date:   2022-08-22
 * @Version: V1.0
 */
@Data
@ApiModel(value="test_teacherPage对象", description="教师")
public class TestTeacherPage {

	/**主键*/
	@ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**创建人*/
	@ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新人*/
	@ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
	/**所属部门*/
	@ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
	/**教师性别*/
	@Excel(name = "教师性别", width = 15)
	@ApiModelProperty(value = "教师性别")
    private java.lang.String name;
	/**教师性别*/
	@Excel(name = "教师性别", width = 15, dicCode = "sex")
    @Dict(dicCode = "sex")
	@ApiModelProperty(value = "教师性别")
    private java.lang.String sex;
	/**教师年龄*/
	@Excel(name = "教师年龄", width = 15)
	@ApiModelProperty(value = "教师年龄")
    private java.lang.Integer age;

	@ExcelCollection(name="学生表")
	@ApiModelProperty(value = "学生表")
	private List<TestStudent> testStudentList;

}
