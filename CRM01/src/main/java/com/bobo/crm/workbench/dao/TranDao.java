package com.bobo.crm.workbench.dao;

import com.bobo.crm.workbench.domain.Tran;

public interface TranDao {

    int save(Tran t);

    Tran detail(String id);
}
