package com.homesharing.dao;

import com.homesharing.model.Home;
import com.homesharing.model.Price;
import com.homesharing.model.WishList;

import java.util.List;

public interface WishListDAO {
    int saveWishList(WishList wishlist);

    List<Home> getWishlistByUserId(int userId);

    void removeWishList(int userId, int homeId, String status);

    void addWishList(WishList wishlist);

    boolean isAlreadyInWishlist(int userId, int homeId, String status);

    void addOrUpdateWishList(WishList wishlist);

}