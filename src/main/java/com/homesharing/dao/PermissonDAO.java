package com.homesharing.dao;

import com.homesharing.model.Permisson;

import java.util.List;

public interface PermissonDAO {
    List<Permisson> getAll();
    Permisson fetchByName(String name);
    Permisson fetchById(int id);
}
