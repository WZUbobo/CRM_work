package com.bobo.crm.workbench.service;

import com.bobo.crm.workbench.domain.Tran;
import com.bobo.crm.workbench.domain.TranHistory;

import java.util.List;

public interface TranService {
    boolean save(Tran t, String customerName);

    Tran detail(String id);

    List<TranHistory> getHistoryListByTranId(String tranId);
}
