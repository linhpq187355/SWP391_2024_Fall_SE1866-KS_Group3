package com.homesharing.dao;

import com.homesharing.model.Preference;

public interface PreferenceDAO {
    Preference getPreference(int userId);
}
