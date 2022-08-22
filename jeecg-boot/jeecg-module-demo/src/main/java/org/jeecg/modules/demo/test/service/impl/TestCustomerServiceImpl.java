package org.jeecg.modules.demo.test.service.impl;

import org.jeecg.modules.demo.test.entity.TestCustomer;
import org.jeecg.modules.demo.test.mapper.TestCustomerMapper;
import org.jeecg.modules.demo.test.service.ITestCustomerService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 顾客
 * @Author: jeecg-boot
 * @Date:   2022-08-22
 * @Version: V1.0
 */
@Service
public class TestCustomerServiceImpl extends ServiceImpl<TestCustomerMapper, TestCustomer> implements ITestCustomerService {

}
