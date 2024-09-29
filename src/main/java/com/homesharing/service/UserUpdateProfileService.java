package com.homesharing.service;


import com.homesharing.model.Preference;
import com.homesharing.model.User;

public interface UserUpdateProfileService {
    int updateUserProfile(User user);
    int updateUserPreference(Preference preference);
    int insertUserPreference(Preference preference);
    String getUserAvatar(int id);
}
