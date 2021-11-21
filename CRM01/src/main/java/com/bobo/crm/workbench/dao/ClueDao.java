package com.bobo.crm.workbench.dao;

import com.bobo.crm.workbench.domain.Clue;

public interface ClueDao {


   Clue detail(String id);

    int save(Clue c);

    Clue getById(String clueId);

    int delete(String clueId);
}
