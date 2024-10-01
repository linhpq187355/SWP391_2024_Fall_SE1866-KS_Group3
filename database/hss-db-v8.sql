USE [master]
GO

-- Drop database if it exists
IF EXISTS (SELECT name FROM master.dbo.sysdatabases WHERE name = N'HSS_DB')
BEGIN
    ALTER DATABASE [HSS_DB] SET OFFLINE WITH ROLLBACK IMMEDIATE;
    ALTER DATABASE [HSS_DB] SET ONLINE;
    DROP DATABASE [HSS_DB];
END
GO

CREATE DATABASE [HSS_DB]
GO

USE [HSS_DB]
GO

CREATE TABLE HSS_Users (
  id               int IDENTITY NOT NULL, 
  email            varchar(255) NOT NULL, 
  hashedPassword   nvarchar(255) NULL, 
  phoneNumber      varchar(12) NULL, 
  username         varchar(50) NULL, 
  firstName        nvarchar(50) NULL, 
  lastName         nvarchar(50) NULL, 
  avatar           varchar(255) NULL, 
  dob              date NULL, 
  address          nvarchar(255) NULL, 
  gender           nvarchar(255) NULL, 
  citizenNumber    varchar(20) NULL, 
  createdAt        datetime NULL, 
  status           nvarchar(20) DEFAULT 'active' NOT NULL, 
  isVerified       bit DEFAULT 0 NOT NULL, 
  lastModified     datetime2(7) NULL, 
  wardsId          int NULL, 
  rolesid          int NOT NULL, 
  verificationCode nvarchar(255) NULL, 
  PRIMARY KEY (id));
CREATE TABLE HomeImages (
  id      int IDENTITY(1, 1) NOT NULL, 
  imgUrl  varchar(255) NOT NULL, 
  imgType varchar(50) NULL, 
  status  varchar(20) DEFAULT 'active' NOT NULL, 
  Homesid int NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE Roles (
  id          int IDENTITY NOT NULL, 
  name        varchar(50) NOT NULL UNIQUE, 
  description nvarchar(255) NULL, 
  PRIMARY KEY (id));
CREATE TABLE Preferences (
  id           int IDENTITY(1, 1) NOT NULL, 
  cleanliness  tinyint NULL, 
  workSchedule tinyint NULL, 
  smoking      tinyint NULL, 
  drinking     tinyint NULL, 
  interaction  tinyint NULL, 
  guests       tinyint NULL, 
  cooking      tinyint NULL, 
  pet          tinyint NULL, 
  usersId      int NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE Homes (
  id                int IDENTITY(1, 1) NOT NULL, 
  name              nvarchar(255) NOT NULL, 
  address           nvarchar(255) NOT NULL, 
  longitude         decimal(9, 6) NULL, 
  latitude          decimal(8, 6) NULL, 
  orientation       nvarchar(20) NULL, 
  area              decimal(10, 2) NOT NULL, 
  leaseDuration     int NOT NULL, 
  moveInDate        date NOT NULL, 
  numOfBedroom      int NOT NULL, 
  numOfBath         int NOT NULL, 
  createdDate       datetime NOT NULL, 
  modifiedDate      datetime NULL, 
  homeDescription   text NULL, 
  tenantDescription text NULL, 
  homeTypeId        int NOT NULL, 
  createdBy         int NOT NULL, 
  wardsId           int NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE HomeTypes (
  id          int IDENTITY(1, 1) NOT NULL, 
  name        nvarchar(255) NOT NULL UNIQUE, 
  description nvarchar(255) NULL, 
  status      varchar(20) DEFAULT 'active' NOT NULL, 
  icon        varchar(255) NULL, 
  PRIMARY KEY (id));
CREATE TABLE Provinces (
  id     int IDENTITY(1, 1) NOT NULL, 
  name   nvarchar(255) NOT NULL, 
  status nvarchar(20) DEFAULT 'active' NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE Districts (
  id          int IDENTITY(1, 1) NOT NULL, 
  name        nvarchar(255) NOT NULL, 
  status      nvarchar(20) DEFAULT 'active' NOT NULL, 
  provincesId int NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE Wards (
  id          int IDENTITY(1, 1) NOT NULL, 
  name        nvarchar(255) NOT NULL, 
  status      nvarchar(20) DEFAULT 'active' NOT NULL, 
  Districtsid int NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE Reviews (
  id               int IDENTITY(1, 1) NOT NULL, 
  star             tinyint NOT NULL, 
  comments         text NOT NULL, 
  createdDate      datetime NOT NULL, 
  lastModifiedDate datetime NOT NULL, 
  status           varchar(20) DEFAULT 'active' NOT NULL, 
  homeId           int NOT NULL, 
  userId           int NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE Amenities (
  id     int IDENTITY(1, 1) NOT NULL, 
  name   nvarchar(50) NOT NULL UNIQUE, 
  icon   varchar(255) NULL, 
  status varchar(20) DEFAULT 'active' NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE FireEquipments (
  id     int IDENTITY(1, 1) NOT NULL, 
  name   nvarchar(255) NOT NULL UNIQUE, 
  icon   varchar(255) NULL, 
  status varchar(20) DEFAULT 'active' NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE Reports (
  id           int IDENTITY(1, 1) NOT NULL, 
  reportDate   datetime NOT NULL, 
  title        nvarchar(255) NOT NULL, 
  description  text NOT NULL, 
  reportedDate datetime NOT NULL, 
  solvedDate   datetime NULL, 
  status       varchar(20) DEFAULT 'active' NOT NULL, 
  createdBy    int NOT NULL, 
  homeId       int NOT NULL, 
  reportTypeId int NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE ReportTypes (
  id     int IDENTITY(1, 1) NOT NULL, 
  reason nvarchar(255) NOT NULL UNIQUE, 
  icon   nvarchar(255) NULL, 
  PRIMARY KEY (id));
CREATE TABLE Announcements (
  id           int IDENTITY NOT NULL, 
  title        varchar(255) NOT NULL, 
  content      text NOT NULL, 
  createdDate  datetime NOT NULL, 
  modifiedDate datetime NULL, 
  status       varchar(20) DEFAULT 'active' NOT NULL, 
  createdBy    int NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE Conversations (
  id      int IDENTITY(1, 1) NOT NULL, 
  userOne int NOT NULL, 
  userTwo int NOT NULL, 
  time    datetime NOT NULL, 
  status  varchar(20) DEFAULT 'active' NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE AnnouncementType (
  id              int IDENTITY NOT NULL, 
  typeName        varchar(50) NOT NULL, 
  status          varchar(20) DEFAULT 'active' NOT NULL, 
  announcementsId int NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE Users_Announcements (
  reciever        int NOT NULL, 
  notificationsId int NOT NULL, 
  PRIMARY KEY (reciever, 
  notificationsId));
CREATE TABLE Appointments (
  id        int IDENTITY NOT NULL, 
  note      text NULL, 
  startDate datetime NOT NULL, 
  endDate   datetime NULL, 
  location  varchar(255) NOT NULL, 
  status    varchar(20) DEFAULT 'active' NOT NULL, 
  tenantId  int NOT NULL, 
  hostId    int NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE Replies (
  id             int IDENTITY NOT NULL, 
  reply          text NOT NULL, 
  time           datetime NOT NULL, 
  status         varchar(20) DEFAULT 'active' NOT NULL, 
  conversationId int NOT NULL, 
  userId         int NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE Notifications (
  id          int IDENTITY(1, 1) NOT NULL, 
  recieverId  int NOT NULL, 
  content     text NOT NULL, 
  createdDate datetime NOT NULL, 
  status      varchar(20) DEFAULT 'active' NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE Permissons (
  id          int IDENTITY(1, 1) NOT NULL, 
  name        nvarchar(50) NOT NULL UNIQUE, 
  description nvarchar(255) NULL, 
  PRIMARY KEY (id));
CREATE TABLE Wishlists (
  homeId      int NOT NULL, 
  userId      int NOT NULL, 
  createdDate datetime NOT NULL, 
  status      varchar(20) DEFAULT 'active' NOT NULL, 
  PRIMARY KEY (homeId, 
  userId));
CREATE TABLE Users_Permissons (
  [HSS Usersid] int NOT NULL, 
  Permissonsid  int NOT NULL, 
  PRIMARY KEY ([HSS Usersid], 
  Permissonsid));
CREATE TABLE Token (
  id            int IDENTITY NOT NULL, 
  userId        int NOT NULL, 
  otp           varchar(10) NOT NULL, 
  requestedTime datetime NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE Prices (
  id          int IDENTITY(1, 1) NOT NULL, 
  price       int NOT NULL, 
  createdDate datetime NOT NULL, 
  type        nvarchar(50) DEFAULT 'per-person' NOT NULL, 
  Homesid     int NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE Amenities_Homes (
  amenitiesId int NOT NULL, 
  homesId     int NOT NULL, 
  PRIMARY KEY (amenitiesId, 
  homesId));
CREATE TABLE FireEquipments_Homes (
  fireEquipmentsId int NOT NULL, 
  homesId          int NOT NULL, 
  PRIMARY KEY (fireEquipmentsId, 
  homesId));
ALTER TABLE HomeImages ADD CONSTRAINT FK_Homes_HomeImages FOREIGN KEY (Homesid) REFERENCES Homes (id);
ALTER TABLE Districts ADD CONSTRAINT FK_Provinces_Districts FOREIGN KEY (provincesId) REFERENCES Provinces (id);
ALTER TABLE Wards ADD CONSTRAINT FK_Districts_Wards FOREIGN KEY (Districtsid) REFERENCES Districts (id);
ALTER TABLE Reviews ADD CONSTRAINT FK_Homes_Reviews FOREIGN KEY (homeId) REFERENCES Homes (id);
ALTER TABLE Reports ADD CONSTRAINT FK_Homes_Reports FOREIGN KEY (homeId) REFERENCES Homes (id);
ALTER TABLE Reports ADD CONSTRAINT FK_Reports_Users FOREIGN KEY (createdBy) REFERENCES HSS_Users (id);
ALTER TABLE Reviews ADD CONSTRAINT FK_Reviews_Users FOREIGN KEY (userId) REFERENCES HSS_Users (id);
ALTER TABLE Users_Announcements ADD CONSTRAINT FKUsers_Anno390274 FOREIGN KEY (reciever) REFERENCES HSS_Users (id);
ALTER TABLE Users_Announcements ADD CONSTRAINT FKUsers_Anno484474 FOREIGN KEY (notificationsId) REFERENCES Announcements (id);
ALTER TABLE Homes ADD CONSTRAINT FK_HomeTypes_Homes FOREIGN KEY (homeTypeId) REFERENCES HomeTypes (id);
ALTER TABLE Conversations ADD CONSTRAINT FK_Users_Conversations FOREIGN KEY (userOne) REFERENCES HSS_Users (id);
ALTER TABLE Conversations ADD CONSTRAINT FKConversati369549 FOREIGN KEY (userTwo) REFERENCES HSS_Users (id);
ALTER TABLE Appointments ADD CONSTRAINT FKAppointmen241265 FOREIGN KEY (tenantId) REFERENCES HSS_Users (id);
ALTER TABLE Appointments ADD CONSTRAINT FKAppointmen450190 FOREIGN KEY (hostId) REFERENCES HSS_Users (id);
ALTER TABLE Replies ADD CONSTRAINT FK_Conversations_Replies FOREIGN KEY (conversationId) REFERENCES Conversations (id);
ALTER TABLE Replies ADD CONSTRAINT FK_Users_Replies FOREIGN KEY (userId) REFERENCES HSS_Users (id);
ALTER TABLE AnnouncementType ADD CONSTRAINT FKAnnounceme150962 FOREIGN KEY (announcementsId) REFERENCES Announcements (id);
ALTER TABLE Announcements ADD CONSTRAINT FK_Users_Announces FOREIGN KEY (createdBy) REFERENCES HSS_Users (id);
ALTER TABLE Notifications ADD CONSTRAINT FK_Users_Notifications FOREIGN KEY (recieverId) REFERENCES HSS_Users (id);
ALTER TABLE Reports ADD CONSTRAINT FK_Reports_ReportTypes FOREIGN KEY (reportTypeId) REFERENCES ReportTypes (id);
ALTER TABLE Wishlists ADD CONSTRAINT FKWishlists100026 FOREIGN KEY (homeId) REFERENCES Homes (id);
ALTER TABLE Wishlists ADD CONSTRAINT FKWishlists664103 FOREIGN KEY (userId) REFERENCES HSS_Users (id);
ALTER TABLE Homes ADD CONSTRAINT FK_Homes_Users FOREIGN KEY (createdBy) REFERENCES HSS_Users (id);
ALTER TABLE HSS_Users ADD CONSTRAINT FK_Users_Roles FOREIGN KEY (rolesid) REFERENCES Roles (id);
ALTER TABLE Users_Permissons ADD CONSTRAINT FKUsers_Perm485883 FOREIGN KEY ([HSS Usersid]) REFERENCES HSS_Users (id);
ALTER TABLE Users_Permissons ADD CONSTRAINT FKUsers_Perm645932 FOREIGN KEY (Permissonsid) REFERENCES Permissons (id);
ALTER TABLE Token ADD CONSTRAINT FKToken785194 FOREIGN KEY (userId) REFERENCES HSS_Users (id);
ALTER TABLE Prices ADD CONSTRAINT FK_Prices_Homes FOREIGN KEY (Homesid) REFERENCES Homes (id);
ALTER TABLE Amenities_Homes ADD CONSTRAINT FKAmenities_151909 FOREIGN KEY (amenitiesId) REFERENCES Amenities (id);
ALTER TABLE Amenities_Homes ADD CONSTRAINT FKAmenities_413308 FOREIGN KEY (homesId) REFERENCES Homes (id);
ALTER TABLE FireEquipments_Homes ADD CONSTRAINT FKFireEquipm893547 FOREIGN KEY (fireEquipmentsId) REFERENCES FireEquipments (id);
ALTER TABLE FireEquipments_Homes ADD CONSTRAINT FKFireEquipm478382 FOREIGN KEY (homesId) REFERENCES Homes (id);
ALTER TABLE Preferences ADD CONSTRAINT FKPreference475209 FOREIGN KEY (usersId) REFERENCES HSS_Users (id);
ALTER TABLE Homes ADD CONSTRAINT FKHomes418421 FOREIGN KEY (wardsId) REFERENCES Wards (id);
