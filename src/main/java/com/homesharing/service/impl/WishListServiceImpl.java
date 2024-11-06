package com.homesharing.service.impl;

import com.homesharing.dao.HomeDAO;
import com.homesharing.dao.WishListDAO;
import com.homesharing.dao.impl.HomeDAOImpl;
import com.homesharing.dao.impl.WishListDAOImpl;
import com.homesharing.model.Home;
import com.homesharing.model.Price;
import com.homesharing.model.WishList;
import com.homesharing.service.WishListService;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class WishListServiceImpl implements WishListService {
    private final WishListDAO wishListDAO;
    private final HomeDAO homeDAO;

    public WishListServiceImpl() {
        this.wishListDAO = new WishListDAOImpl();
        this.homeDAO = new HomeDAOImpl();
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
//    @Override
//    public List<Home> getWishlistByUserId(int userId) {
//        return wishListDAO.getWishlistByUserId(userId); // Gọi phương thức DAO
//    }

    @Override
    public boolean isAlreadyInWishlist(int userId, int homeId, String status) {
        return wishListDAO.isAlreadyInWishlist(userId, homeId,status);
    }
    @Override
    public List<Home> getWishlistByUserId(int userId) {
        List<Home> homeList = wishListDAO.getWishlistByUserId(userId);
        try {
            for (Home home : homeList) {
                List<String> images = new ArrayList<>();
                String firstImage = homeDAO.fetchFirstImage(home.getId());
                if (firstImage != null) {
                    images.add(firstImage);
                }
                home.setImages(images);
            }
            return homeList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>(); // Trả về danh sách rỗng nếu có ngoại lệ
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
