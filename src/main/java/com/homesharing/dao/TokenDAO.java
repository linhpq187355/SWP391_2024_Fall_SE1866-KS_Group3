package com.homesharing.dao;

import com.homesharing.model.Token;

public interface TokenDAO {

    void insertToken(Token token);

    Token findToken(int userId);

    void updateTokenVerification(int userId);

}
