package com.bobo.crm.workbench.web.controller;



import com.bobo.crm.settings.domain.User;
import com.bobo.crm.settings.service.UserService;
import com.bobo.crm.settings.service.impl.UserServiceImpl;
import com.bobo.crm.utils.DateTimeUtil;
import com.bobo.crm.utils.PrintJson;
import com.bobo.crm.utils.ServiceFactory;
import com.bobo.crm.utils.UUIDUtil;
import com.bobo.crm.vo.PaginationVo;
import com.bobo.crm.workbench.domain.Activity;
import com.bobo.crm.workbench.domain.ActivityRemark;
import com.bobo.crm.workbench.service.ActivityService;
import com.bobo.crm.workbench.service.impl.ActivityServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Author 北京动力节点
 */
public class ActivityController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("进入到市场活动控制器");

        String path = request.getServletPath();
        if("/workbench/activity/getUserList.do".equals(path)) {

            getUserList(request, response);
        }else if("/workbench/activity/save.do".equals(path)) {
            save(request,response);

        }else if("/workbench/activity/pageList.do".equals(path)) {
            pageList(request,response);

        }else if("/workbench/activity/delete.do".equals(path)) {
            delete(request,response);

        }else if("/workbench/activity/getUserListAndActivity.do".equals(path)) {
            getUserListAndActivity(request,response);

        }else if("/workbench/activity/detail.do".equals(path)) {
            detail(request,response);

        }else if("/workbench/activity/getRemarkListByAid.do".equals(path)) {
            getRemarkListByAid(request,response);

        }else if("/workbench/activity/deleteRemark.do".equals(path)) {
            deleteRemark(request,response);

        }

    }

    private void deleteRemark(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("删除备注操作");

        String id = request.getParameter("id");

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        boolean flag = as.deleteRemark(id);

        PrintJson.printJsonFlag(response, flag);
    }


    private void getRemarkListByAid(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("根据市场活动ID，取得备注信息列表");
        String activityId = request.getParameter("activityId");

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        List<ActivityRemark> arList = as.getRemarkListByAid(activityId);

        PrintJson.printJsonObj(response, arList);


    }

    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入到跳转详细信息页的操作");
        String id = request.getParameter("id");
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        Activity a =  as.detail(id);

        request.setAttribute("a",a);

        request.getRequestDispatcher("/workbench/activity/detail.jsp").forward(request,response);

    }

    private void getUserListAndActivity(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到查询用户信息列表 和 根据市场活动id查询单条记录的操作");

        String id = request.getParameter("id");
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

       Map<String,Object> map =  as.getUserAndActivity(id);
       PrintJson.printJsonObj(response,map);
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行市场活动的删除工作");
        String ids[] = request.getParameterValues("id");
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag = as.delete(ids);
        PrintJson.printJsonFlag(response,flag);


    }

    private void pageList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到查询市场活动信息列表的操作（结合条件查询+分页查询）");
        String name = request.getParameter("name");
        String owner = request.getParameter("owner");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String pageNoStr = request.getParameter("pageNo");
        int pageNo = Integer.valueOf(pageNoStr);

        //每页展现的记录数
        String pageSizeStr = request.getParameter("pageSize");
        int pageSize = Integer.valueOf(pageSizeStr);
        //计算出略过的记录数
        int skipCount = (pageNo-1)*pageSize;

        Map<String,Object> map = new HashMap<String, Object>();
        map.put("name", name);
        map.put("owner", owner);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("skipCount",skipCount);
        map.put("pageSize",pageSize);

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        /*

            前端要： 市场活动信息列表
                    查询的总条数

                    业务层拿到了以上两项信息之后，如果做返回
                    map
                    map.put("dataList":dataList)
                    map.put("total":total)
                    PrintJSON map --> json
                    {"total":100,"dataList":[{市场活动1},{2},{3}]}


                    vo
                    PaginationVO<T>
                        private int total;
                        private List<T> dataList;

                    PaginationVO<Activity> vo = new PaginationVO<>;
                    vo.setTotal(total);
                    vo.setDataList(dataList);
                    PrintJSON vo --> json
                    {"total":100,"dataList":[{市场活动1},{2},{3}]}


                    将来分页查询，每个模块都有，所以我们选择使用一个通用vo，操作起来比较方便

         */
        PaginationVo<Activity> vo = as.pageList(map);

        // vo
        PrintJson.printJsonObj(response,vo);

    }

    private void save(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行市场活动添加操作");

        String id = UUIDUtil.getUUID();
        String owner = request.getParameter("owner");
        String name = request.getParameter("name");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String cost = request.getParameter("cost");
        String description = request.getParameter("description");
        //创建时间：当前系统时间+
        String createTime = DateTimeUtil.getSysTime();
        //创建人：当前登录用户
        String createBy = ((User)request.getSession().getAttribute("user")).getName();

        Activity a = new Activity();
        a.setId(id);
        a.setCost(cost);
        a.setStartDate(startDate);
        a.setOwner(owner);
        a.setName(name);
        a.setEndDate(endDate);
        a.setDescription(description);
        a.setCreateTime(createTime);
        a.setCreateBy(createBy);

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        boolean flag = as.save(a);

        PrintJson.printJsonFlag(response, flag);
    }

    private void  getUserList(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("取得用户信息列表");

        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());

        List <User> uList = us.getUserList();

        PrintJson.printJsonObj(response,uList);


    }
}




































