/*
 * Copyright(C) 2024, Homesharing Inc.
 * Homesharing:
 *  Roommate Matching and Home Sharing Service
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * 2024-10-01      1.0              Pham Quang Linh     First Implement
 * 2024-10-10      2.0              Pham Quang Linh     Second Implement
 */
package com.homesharing.service;

import com.homesharing.model.*;

import java.util.List;

/**
 * Interface for the HomePageService.
 * This interface defines the methods related to home management on the homepage.
 */
public interface HomePageService {
    List<Home> getHomes();
    /**
     * Retrieves a list of new homes that have been added recently.
     *
     * @return a list of {@link Home} objects representing new homes.
     */
    List<Home> getNewHomes();
    List<Price> getHomePrice(List<Home> homes);
    void addHome(Home home);
    Home getHomeById(int id);
    int getHomeCount();

    /**
     * Retrieves a list of homes that match the given host IDs for a specific user.
     *
     * @param matchingHostsId an array of host IDs to find matching homes.
     * @param userId the ID of the user looking for matching homes.
     * @return a list of {@link Home} objects that match the specified host IDs for the user.
     */
    List<Home> getMatchingHome(int[] matchingHostsId, int userId);
}
