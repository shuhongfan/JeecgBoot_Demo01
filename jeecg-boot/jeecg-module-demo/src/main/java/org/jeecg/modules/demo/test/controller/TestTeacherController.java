package org.jeecg.modules.demo.test.controller;

import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.vo.LoginUser;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.test.entity.TestStudent;
import org.jeecg.modules.demo.test.entity.TestTeacher;
import org.jeecg.modules.demo.test.vo.TestTeacherPage;
import org.jeecg.modules.demo.test.service.ITestTeacherService;
import org.jeecg.modules.demo.test.service.ITestStudentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 教师
 * @Author: jeecg-boot
 * @Date:   2022-08-22
 * @Version: V1.0
 */
@Api(tags="教师")
@RestController
@RequestMapping("/test/testTeacher")
@Slf4j
public class TestTeacherController {
	@Autowired
	private ITestTeacherService testTeacherService;
	@Autowired
	private ITestStudentService testStudentService;
	
	/**
	 * 分页列表查询
	 *
	 * @param testTeacher
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "教师-分页列表查询")
	@ApiOperation(value="教师-分页列表查询", notes="教师-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<TestTeacher>> queryPageList(TestTeacher testTeacher,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<TestTeacher> queryWrapper = QueryGenerator.initQueryWrapper(testTeacher, req.getParameterMap());
		Page<TestTeacher> page = new Page<TestTeacher>(pageNo, pageSize);
		IPage<TestTeacher> pageList = testTeacherService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param testTeacherPage
	 * @return
	 */
	@AutoLog(value = "教师-添加")
	@ApiOperation(value="教师-添加", notes="教师-添加")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody TestTeacherPage testTeacherPage) {
		TestTeacher testTeacher = new TestTeacher();
		BeanUtils.copyProperties(testTeacherPage, testTeacher);
		testTeacherService.saveMain(testTeacher, testTeacherPage.getTestStudentList());
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param testTeacherPage
	 * @return
	 */
	@AutoLog(value = "教师-编辑")
	@ApiOperation(value="教师-编辑", notes="教师-编辑")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody TestTeacherPage testTeacherPage) {
		TestTeacher testTeacher = new TestTeacher();
		BeanUtils.copyProperties(testTeacherPage, testTeacher);
		TestTeacher testTeacherEntity = testTeacherService.getById(testTeacher.getId());
		if(testTeacherEntity==null) {
			return Result.error("未找到对应数据");
		}
		testTeacherService.updateMain(testTeacher, testTeacherPage.getTestStudentList());
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "教师-通过id删除")
	@ApiOperation(value="教师-通过id删除", notes="教师-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		testTeacherService.delMain(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "教师-批量删除")
	@ApiOperation(value="教师-批量删除", notes="教师-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.testTeacherService.delBatchMain(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "教师-通过id查询")
	@ApiOperation(value="教师-通过id查询", notes="教师-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<TestTeacher> queryById(@RequestParam(name="id",required=true) String id) {
		TestTeacher testTeacher = testTeacherService.getById(id);
		if(testTeacher==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(testTeacher);

	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "学生表通过主表ID查询")
	@ApiOperation(value="学生表主表ID查询", notes="学生表-通主表ID查询")
	@GetMapping(value = "/queryTestStudentByMainId")
	public Result<List<TestStudent>> queryTestStudentListByMainId(@RequestParam(name="id",required=true) String id) {
		List<TestStudent> testStudentList = testStudentService.selectByMainId(id);
		return Result.OK(testStudentList);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param testTeacher
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, TestTeacher testTeacher) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<TestTeacher> queryWrapper = QueryGenerator.initQueryWrapper(testTeacher, request.getParameterMap());
      LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

      //配置选中数据查询条件
      String selections = request.getParameter("selections");
      if(oConvertUtils.isNotEmpty(selections)) {
         List<String> selectionList = Arrays.asList(selections.split(","));
         queryWrapper.in("id",selectionList);
      }
      //Step.2 获取导出数据
      List<TestTeacher> testTeacherList = testTeacherService.list(queryWrapper);

      // Step.3 组装pageList
      List<TestTeacherPage> pageList = new ArrayList<TestTeacherPage>();
      for (TestTeacher main : testTeacherList) {
          TestTeacherPage vo = new TestTeacherPage();
          BeanUtils.copyProperties(main, vo);
          List<TestStudent> testStudentList = testStudentService.selectByMainId(main.getId());
          vo.setTestStudentList(testStudentList);
          pageList.add(vo);
      }

      // Step.4 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "教师列表");
      mv.addObject(NormalExcelConstants.CLASS, TestTeacherPage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("教师数据", "导出人:"+sysUser.getRealname(), "教师"));
      mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
      return mv;
    }

    /**
    * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
      Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
      for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
          // 获取上传文件对象
          MultipartFile file = entity.getValue();
          ImportParams params = new ImportParams();
          params.setTitleRows(2);
          params.setHeadRows(1);
          params.setNeedSave(true);
          try {
              List<TestTeacherPage> list = ExcelImportUtil.importExcel(file.getInputStream(), TestTeacherPage.class, params);
              for (TestTeacherPage page : list) {
                  TestTeacher po = new TestTeacher();
                  BeanUtils.copyProperties(page, po);
                  testTeacherService.saveMain(po, page.getTestStudentList());
              }
              return Result.OK("文件导入成功！数据行数:" + list.size());
          } catch (Exception e) {
              log.error(e.getMessage(),e);
              return Result.error("文件导入失败:"+e.getMessage());
          } finally {
              try {
                  file.getInputStream().close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
      return Result.OK("文件导入失败！");
    }

}
