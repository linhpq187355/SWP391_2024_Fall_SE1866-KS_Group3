package com.homesharing.service.impl;

import com.homesharing.dao.WishListDAO;
import com.homesharing.dao.impl.WishListDAOImpl;
import com.homesharing.model.Home;
import com.homesharing.model.Price;
import com.homesharing.model.WishList;
import com.homesharing.service.WishListService;

import java.time.LocalDateTime;
import java.util.List;

public class WishListServiceImpl implements WishListService {
    private final WishListDAO wishListDAO;

    public WishListServiceImpl() {
        this.wishListDAO = new WishListDAOImpl(); // Khởi tạo DAO
    }

    @Override
    public int saveWishList(int homeId, int userId) {
        WishList wishlist = new WishList();
        wishlist.setHomeId(homeId);
        wishlist.setUserId(userId);
        wishlist.setCreatedDate(LocalDateTime.now()); // Thiết lập thời gian tạo
        wishlist.setStatus("active"); // Mặc định trạng thái là 'active'

        return wishListDAO.saveWishList(wishlist); // Gọi phương thức DAO để lưu wishlist
    }
    @Override
    public void addWishList(WishList wishlist) {
        wishListDAO.addWishList(wishlist);
    }
    @Override
    public List<Home> getWishlistByUserId(int userId) {
        return wishListDAO.getWishlistByUserId(userId); // Gọi phương thức DAO
    }

    @Override
    public List<Price> getHomePricesByHomeId(int homeId) {
        return wishListDAO.getHomePricesByHomeId(homeId);
    }
    @Override
    public boolean isAlreadyInWishlist(int userId, int homeId, String status) {
        return wishListDAO.isAlreadyInWishlist(userId, homeId,status); // Kiểm tra xem nhà có trong danh sách yêu thích chưa
    }

    @Override
    public void addOrUpdateWishList(WishList wishlist) {
        if (isAlreadyInWishlist(wishlist.getUserId(), wishlist.getHomeId(),"inactive")) {
            // Nếu đã tồn tại, cập nhật trạng thái thành 'active'
            wishListDAO.removeWishList(wishlist.getUserId(), wishlist.getHomeId(), "active");
        } else {
            // Nếu chưa tồn tại, thêm mới vào cơ sở dữ liệu
            wishListDAO.addWishList(wishlist);
        }
    }

    public void removeWishList(int userId, int homeId,String status) {
        wishListDAO.removeWishList(userId, homeId,status);
    }

}
