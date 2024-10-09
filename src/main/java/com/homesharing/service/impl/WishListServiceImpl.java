package com.homesharing.service.impl;

import com.homesharing.dao.WishListDAO;
import com.homesharing.dao.impl.WishListDAOImpl;
import com.homesharing.service.WishListService;

public class WishListServiceImpl implements WishListService {
    private WishListDAO wishListDAO;

    public WishListServiceImpl() {
        this.wishListDAO = new WishListDAOImpl(); // Khởi tạo DAO
    }

    @Override
    public boolean addToWishlist(int homeId, int userId) {
        return wishListDAO.addToWishlist(homeId, userId); // Gọi phương thức DAO
    }
}
