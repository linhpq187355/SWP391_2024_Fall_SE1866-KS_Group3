package com.homesharing.dao;

import com.homesharing.model.Announcement;
import com.homesharing.model.AnnouncementType;

import java.util.List;

public interface AnnouncementDAO {
    List<AnnouncementType> allAnnouncementTypes();
    List<Announcement> allAnnouncements();
    void saveAnnouncement(Announcement announcement);
}
