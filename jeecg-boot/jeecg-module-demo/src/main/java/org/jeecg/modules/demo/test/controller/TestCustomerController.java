package org.jeecg.modules.demo.test.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.test.entity.TestCustomer;
import org.jeecg.modules.demo.test.service.ITestCustomerService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 顾客
 * @Author: jeecg-boot
 * @Date:   2022-08-22
 * @Version: V1.0
 */
@Api(tags="顾客")
@RestController
@RequestMapping("/test/testCustomer")
@Slf4j
public class TestCustomerController extends JeecgController<TestCustomer, ITestCustomerService> {
	@Autowired
	private ITestCustomerService testCustomerService;
	
	/**
	 * 分页列表查询
	 *
	 * @param testCustomer
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "顾客-分页列表查询")
	@ApiOperation(value="顾客-分页列表查询", notes="顾客-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<TestCustomer>> queryPageList(TestCustomer testCustomer,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<TestCustomer> queryWrapper = QueryGenerator.initQueryWrapper(testCustomer, req.getParameterMap());
		Page<TestCustomer> page = new Page<TestCustomer>(pageNo, pageSize);
		IPage<TestCustomer> pageList = testCustomerService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param testCustomer
	 * @return
	 */
	@AutoLog(value = "顾客-添加")
	@ApiOperation(value="顾客-添加", notes="顾客-添加")
	//@RequiresPermissions("org.jeecg.modules.demo:test_customer:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody TestCustomer testCustomer) {
		testCustomerService.save(testCustomer);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param testCustomer
	 * @return
	 */
	@AutoLog(value = "顾客-编辑")
	@ApiOperation(value="顾客-编辑", notes="顾客-编辑")
	//@RequiresPermissions("org.jeecg.modules.demo:test_customer:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody TestCustomer testCustomer) {
		testCustomerService.updateById(testCustomer);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "顾客-通过id删除")
	@ApiOperation(value="顾客-通过id删除", notes="顾客-通过id删除")
	//@RequiresPermissions("org.jeecg.modules.demo:test_customer:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		testCustomerService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "顾客-批量删除")
	@ApiOperation(value="顾客-批量删除", notes="顾客-批量删除")
	//@RequiresPermissions("org.jeecg.modules.demo:test_customer:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.testCustomerService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "顾客-通过id查询")
	@ApiOperation(value="顾客-通过id查询", notes="顾客-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<TestCustomer> queryById(@RequestParam(name="id",required=true) String id) {
		TestCustomer testCustomer = testCustomerService.getById(id);
		if(testCustomer==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(testCustomer);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param testCustomer
    */
    //@RequiresPermissions("org.jeecg.modules.demo:test_customer:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, TestCustomer testCustomer) {
        return super.exportXls(request, testCustomer, TestCustomer.class, "顾客");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    //@RequiresPermissions("test_customer:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, TestCustomer.class);
    }

}
