package com.homesharing.dao;

import com.homesharing.model.Preference;

public interface PreferenceDAO {
    Preference getPreference(int userId);

    int updatePreference(Preference preference);

    int insertPreference(Preference preference);
}
