package com.homesharing.service;

import com.homesharing.model.Home;
import com.homesharing.model.WishList;

import java.util.List;

public interface WishListService {

    int saveWishList(int homeId, int userId);

    void removeWishList(int userId, int homeId,String status);

    List<Home> getWishlistByUserId(int userId);

    void addWishList(WishList wishlist);

    void addOrUpdateWishList(WishList wishlist);

    boolean isAlreadyInWishlist(int userId, int homeId, String status);

}
