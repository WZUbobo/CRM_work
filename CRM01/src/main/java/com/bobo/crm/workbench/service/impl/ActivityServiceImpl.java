package com.bobo.crm.workbench.service.impl;

import com.bobo.crm.settings.dao.UserDao;
import com.bobo.crm.settings.domain.User;
import com.bobo.crm.utils.SqlSessionUtil;
import com.bobo.crm.vo.PaginationVo;
import com.bobo.crm.workbench.dao.ActivityDao;
import com.bobo.crm.workbench.dao.ActivityRemarkDao;
import com.bobo.crm.workbench.domain.Activity;
import com.bobo.crm.workbench.domain.ActivityRemark;
import com.bobo.crm.workbench.service.ActivityService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityServiceImpl implements ActivityService {
    private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    private ActivityRemarkDao activityRemarkDao =
            SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);
    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    public boolean save(Activity a) {

        boolean flag = true;
        int count = activityDao.save(a);

        if (count != 1) {
            flag = false;
        }

        return flag;
    }

    @Override
    public PaginationVo<Activity> pageList(Map<String, Object> map) {
        //取得total
        int total = activityDao.getTotalByCondition(map);
        //取得dataList
        List<Activity> dataList = activityDao.getActivityListByCondition(map);
        //创建一个vo对象，将total和dataList封装到vo中
        PaginationVo<Activity> vo = new PaginationVo<>();
        vo.setTotal(total);
        vo.setDataList(dataList);

        //将vo返回
        return vo;
    }

    @Override
    public boolean delete(String[] ids) {
        boolean flag = true;
        //先查询出需要删除备注的数量
        int count1 = activityRemarkDao.getCountByAids(ids);
        //删除备注，返回受到影响的条数(实际删除的数量)
        int count2 = activityRemarkDao.deleteByAids(ids);

        if (count1 != count2) {
            flag = false;
        }
        //删除市场活动
        int count3 = activityDao.delete(ids);
        if (count3 != ids.length) {
            flag = false;
        }

        return flag;
    }

    @Override
    public Map<String, Object> getUserAndActivity(String id) {
        //取uList
        List<User> uList = userDao.getUserList();
        //取a
        Activity a = activityDao.getById(id);
        //将List和a打包返回map
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("uList", uList);
        map.put("a", a);

        return map;
    }

    @Override
    public Activity detail(String id) {
        Activity a = activityDao.detail(id);
        return a;
    }

    @Override
    public List<ActivityRemark> getRemarkListByAid(String activityId) {
        List<ActivityRemark> arList = activityRemarkDao.getRemarkListByAid(activityId);
        return arList;
    }

    @Override
    public boolean deleteRemark(String id) {
        boolean flag = true;

        int count = activityRemarkDao.deleteById(id);

        if(count!=1){

            flag = false;

        }

        return flag;
    }

    @Override
    public List<Activity> getActivityListByClueId(String clueId) {
        List<Activity> aList = activityDao.getActivityListByClueId(clueId);

        return aList;
    }

    @Override
    public List<Activity> getActivityListByNameAndNotByClueId(Map<String, String> map) {
        List<Activity> aList = activityDao.getActivityListByNameAndNotByClueId(map);
        return aList;
    }

    @Override
    public List<Activity> getActivityListByName(String aname) {
        List<Activity> aList = activityDao.getActivityListByName(aname);
        return aList;
    }


}



