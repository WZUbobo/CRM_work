package com.bobo.crm.workbench.dao;

import com.bobo.crm.workbench.domain.ClueActivityRelation;

import java.util.List;

public interface ClueActivityRelationDao {


    int unbund(String id);

    int bund(ClueActivityRelation car);

    List<ClueActivityRelation> getListByClueId(String clueId);

    int delete(ClueActivityRelation clueActivityRelation);
}
