INSERT INTO HomeTypes (name, description)
VALUES (N'Nhà nguyên căn', N'Cho thuê cả ngôi nhà'),
	   (N'Phòng trọ', N'Phòng thuê trong nhà riêng hoặc chung cư, không có bếp'),
       (N'Chung cư mini', N'Căn hộ nhỏ, giá cả phải chăng'),
       (N'Villa', N'Biệt thự, nhà sang trọng'),
       (N'Studio', N'Căn hộ nhỏ, kết hợp phòng ngủ và phòng khách'),
       (N'Penthouse', N'Căn hộ ở tầng cao nhất của tòa nhà');

INSERT INTO Amenities (name)
VALUES
    (N'Wifi'),
    (N'Máy giặt'),
    (N'Tủ lạnh'),
    (N'Điều hòa'),
    (N'Bếp ga'),
    (N'Bình nóng lạnh'),
    (N'Tủ quần áo'),
    (N'Bàn làm việc');
	
INSERT INTO FireEquipments (name)
VALUES
    (N'Cửa thoát hiểm'),
	(N'Cầu thang thoát hiểm'),
    (N'Bình chữa cháy'),
    (N'Chuông báo cháy'),
	(N'Mặt nạ phòng độc'),
    (N'Lối thoát hiểm'),
	(N'Đèn khẩn cấp và exit'),
	(N'Bộ dụng cụ thoát nạn');
	
INSERT INTO ReportTypes (reason) VALUES 
(N'Tin đăng không đúng sự thật'),
(N'Hình ảnh không rõ ràng hoặc không liên quan'),
(N'Giá cho thuê không chính xác'),
(N'Thông tin về tiện nghi sai lệch'),
(N'Bài đăng vi phạm quy định'),
(N'Tin đăng đã hết hạn'),
(N'Nhà đã được thuê nhưng vẫn còn bài đăng'),
(N'Chủ nhà không phản hồi'),
(N'Không cung cấp thông tin chi tiết');
	
INSERT INTO [dbo].[Roles]([name]) VALUES
('admin'),('moderator'),('tenant'),('host');

INSERT INTO [dbo].[HSS_Users]([email],[hashedPassword],[rolesid]) VALUES
('admin123@gmail.com','c63c294a92e253e7f7a3e5417a95e6ae598b68d82d5136d18c8fa9d992474799',1), --pass12345
('moderator123@gmail.com','c63c294a92e253e7f7a3e5417a95e6ae598b68d82d5136d18c8fa9d992474799',2),