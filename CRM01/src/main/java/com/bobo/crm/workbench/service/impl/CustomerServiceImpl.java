package com.bobo.crm.workbench.service.impl;


import com.bobo.crm.utils.SqlSessionUtil;
import com.bobo.crm.workbench.dao.CustomerDao;
import com.bobo.crm.workbench.service.CustomerService;

import java.util.List;

/**
 * Author 北京动力节点
 */
public class CustomerServiceImpl implements CustomerService {

    private CustomerDao customerDao = SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);

    public List<String> getCustomerName(String name) {

        List<String> sList = customerDao.getCustomerName(name);

        return sList;
    }
}
















