//package com.homesharing.service;
//
//import com.homesharing.model.Home;
//import org.junit.jupiter.params.ParameterizedTest;
//import com.homesharing.service.impl.HomePageServiceImpl;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.params.provider.CsvSource;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class HomePageServiceTest {
//    private HomePageService homePageService;
//
//    @BeforeEach
//    void setUp() {
//        homePageService = new HomePageServiceImpl();
//    }
//
//    @AfterEach
//    void tearDown() {
//    }
//
//    @ParameterizedTest
//    @CsvSource({
//            "1,Test Home,123 Test St,1.23,4.56,North,100.5,12,2023-06-01T10:00,2,1,2023-05-31T09:00,2023-05-31T09:00,Nice home,Good tenants,1,1,1,1",
//            "2,Another Home,456 Sample Ave,7.89,10.11,South,150.75,24,2023-07-01T11:00,3,2,2023-06-15T14:00,2023-06-15T14:00,Spacious home,Quiet tenants,2,2,2,2"
//    })
//    void testAddHome(String id, String name, String address, String longitude, String latitude, String orientation, String area, String leaseDuration, String moveInDate, String numOfBedRoom, String numOfBath, String createdDate, String modifiedDate, String homeDescription, String tenantDescription, String wardId, String homeTypeId, String createdBy, String priceId) {
//        Home home = new Home();
//        home.setId(Integer.parseInt(id));
//        home.setName(name);
//        home.setAddress(address);
//        home.setLongitude(new BigDecimal(longitude));
//        home.setLatitude(new BigDecimal(latitude));
//        home.setOrientation(orientation);
//        home.setArea(new BigDecimal(area));
//        home.setLeaseDuration(Integer.parseInt(leaseDuration));
//        home.setMoveInDate(LocalDateTime.parse(moveInDate));
//        home.setNumOfBedroom(Integer.parseInt(numOfBedRoom));
//        home.setNumOfBath(Integer.parseInt(numOfBath));
//        home.setCreatedDate(LocalDateTime.parse(createdDate));
//        home.setModifiedDate(LocalDateTime.parse(modifiedDate));
//        home.setHomeDescription(homeDescription);
//        home.setTenantDescription(tenantDescription);
//        home.setWardId(Integer.parseInt(wardId));
//        home.setHomeTypeId(Integer.parseInt(homeTypeId));
//        home.setCreatedBy(Integer.parseInt(createdBy));
//        home.setPriceId(Integer.parseInt(priceId));
//
//        int initialHomeCount = homePageService.getHomes().size();
//
//        // Add the new home
//        homePageService.addHome(home);
//
//        // Get the updated list of homes
//        List<Home> updatedHomes = homePageService.getHomes();
//
//        // Check if a new home was added
//        assertEquals(initialHomeCount + 1, updatedHomes.size(), "A new home should have been added");
//
//        // Check if the last added home matches our input
//        Home lastAddedHome = updatedHomes.get(updatedHomes.size() - 1);
//        assertEquals(home.getName(), lastAddedHome.getName(), "Added home name should match the input");
//        assertEquals(home.getAddress(), lastAddedHome.getAddress(), "Added home address should match the input");
//
//    }
//}