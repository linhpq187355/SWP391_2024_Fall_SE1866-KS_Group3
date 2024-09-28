package com.homesharing.service;

import com.homesharing.model.Preference;
import com.homesharing.model.User;
import jakarta.servlet.http.HttpServletRequest;

public interface UserProfileService {
    User getUser(int userId);
    Preference getPreference(int userId);
}
