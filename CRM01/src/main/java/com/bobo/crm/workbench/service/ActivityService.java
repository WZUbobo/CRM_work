package com.bobo.crm.workbench.service;

import com.bobo.crm.vo.PaginationVo;
import com.bobo.crm.workbench.domain.Activity;
import com.bobo.crm.workbench.domain.ActivityRemark;

import java.util.List;
import java.util.Map;

public interface ActivityService {
    boolean save(Activity a);

    PaginationVo<Activity> pageList(Map<String, Object> map);

    boolean delete(String[] ids);

    Map<String, Object> getUserAndActivity(String id);

    Activity detail(String id);

    List<ActivityRemark> getRemarkListByAid(String activityId);


    boolean deleteRemark(String id);

    List<Activity> getActivityListByClueId(String clueId);

    List<Activity> getActivityListByNameAndNotByClueId(Map<String, String> map);

    List<Activity> getActivityListByName(String aname);
}
