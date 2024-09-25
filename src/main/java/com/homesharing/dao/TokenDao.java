package com.homesharing.dao;

import com.homesharing.model.Token;

public interface TokenDao {

    void insertToken(Token token);

    Token findToken(int userId);

    void updateTokenVerification(int userId);

}
