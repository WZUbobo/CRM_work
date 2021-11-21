package com.bobo.crm.workbench.service;


import com.bobo.crm.workbench.domain.Clue;
import com.bobo.crm.workbench.domain.Tran;

/**
 * Author 北京动力节点
 */
public interface ClueService {

    boolean save(Clue c);

    Clue detail(String id);


    boolean unbund(String id);

    boolean bund(String cid, String[] aids);


    boolean convert(String clueId, Tran t, String createBy);

}
