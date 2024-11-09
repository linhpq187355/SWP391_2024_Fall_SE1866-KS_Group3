package com.homesharing.service.impl;

import com.homesharing.dao.AnnouncementDAO;
import com.homesharing.dao.impl.AnnouncementDAOImpl;
import com.homesharing.model.Announcement;
import com.homesharing.model.AnnouncementType;
import com.homesharing.service.AnnouncementService;

import java.util.List;

public class AnnouncementServiceImpl implements AnnouncementService {
    AnnouncementDAO announcementDAO;

    public AnnouncementServiceImpl(AnnouncementDAO announcementDAO) {
        this.announcementDAO = new AnnouncementDAOImpl();
    }

    /**
     * @return
     */
    @Override
    public List<Announcement> getAllAnnouncements() {
        return this.announcementDAO.allAnnouncements();
    }

    /**
     * @return
     */
    @Override
    public List<AnnouncementType> getAllAnnouncementTypes() {
        return this.announcementDAO.allAnnouncementTypes();
    }

    /**
     * @param announcement
     */
    @Override
    public void saveAnnouncement(Announcement announcement) {
        this.announcementDAO.saveAnnouncement(announcement);
    }
}
