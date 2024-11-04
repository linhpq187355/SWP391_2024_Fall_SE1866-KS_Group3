package com.homesharing.service;

import com.homesharing.model.Announcement;
import com.homesharing.model.AnnouncementType;

import java.util.List;

public interface AnnouncementService {
    List<Announcement> getAllAnnouncements();
    List<AnnouncementType> getAllAnnouncementTypes();
    void saveAnnouncement(Announcement announcement);
}
