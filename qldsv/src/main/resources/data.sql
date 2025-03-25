INSERT INTO `cauhoidiendang` VALUES 
(56,'2023-10-05 18:27','Hôm nay trường có lễ không ạ....','54'),
(57,'2023-10-05 18:27','Sự khác biệt giữa phần mềm và ứng dụng web?','abc56');
INSERT INTO `diem`(id_diem, ten_diem_id_loai_diem, so_diem, id_mon_hoc_id_mon_hoc_dang_ky, khoa_diem) VALUES (316,1,9,55,0),(317,2,9,55,0),(318,6,6.3,55,0),(319,1,9,56,0),(320,2,9,56,0),(321,6,6.3,56,0),(322,1,9,57,0),(323,2,9,57,0),(324,6,6.3,57,0),(325,1,9,58,0),(326,2,9,58,0),(327,6,6.3,58,0),(328,1,9,59,0),(329,2,9,59,0),(330,6,6.3,59,0),(331,1,9,60,0),(332,2,9,60,0),(333,6,6.3,60,1),(334,1,9,61,1),(335,2,9,61,1),(336,6,6.3,61,1),(337,1,9,62,0),(338,2,9,62,0),(339,6,9,62,0),(340,1,9,63,0),(341,2,9,63,0),(342,6,9,63,0),(343,1,9,64,0),(344,2,9,64,0),(345,6,9,64,0),(346,1,9,65,0),(347,2,9,65,0),(348,6,9,65,0),(349,1,9,66,0),(350,2,9,66,0),(351,6,9,66,0),(352,1,9,67,0),(353,2,9,67,0),(354,6,9,67,0),(378,3,0,55,0),(379,3,0,56,0),(380,3,0,57,0),(381,3,0,58,0),(382,3,0,59,0),(383,3,0,60,0),(384,3,0,61,0),(385,4,0,55,0),(386,4,0,56,0),(387,4,0,57,0),(388,4,0,58,0),(389,4,0,59,0),(390,4,0,60,0),(391,4,0,61,0),(392,5,0,55,0),(393,5,0,56,0),(394,5,0,57,0),(395,5,0,58,0),(396,5,0,59,0),(397,5,0,60,0),(398,5,0,61,0),(405,1,0,73,0),(406,2,0,73,0),(407,6,0,73,0);
INSERT INTO `giangvien`(id_giang_vien, ho_ten, ngay_sinh, gioi_tinh, email, dia_chi, so_dien_thoai, id_tai_khoan_id_tai_khoan) VALUES 
('19','Dương Hữu Thành','1990-03-07',1,'huuthanh@gmail.com','37/46 Phan Văn Trị, P12, Gò Vấp','0377221375','54'),
('20','Phan Thị Dương','1985-11-12',0,'duong@gmail.com','450 Lê ai, Tân Kỳ, Tân Phú','0255715246',NULL),
('21','Phạm Hoàng Thiên Ân','1995-07-09',0,'thienan@gmail.com','235 Gò Dầu, Tân Quý, tân Phú','0776655789','62'),
('22','Phạm Hoàng Gia Khang','1989-03-04',1,'giakhoang@gmail.com','205 Năm Kỳ Khởi Nghĩa, P5, Quận 3','0724556785','55');
INSERT INTO `giaovu`(id_giao_vu, ten_giao_vu, gioi_tinh, so_dien_thoai, id_tai_khoan_id_tai_khoan) VALUES 
('4','Phạm Hoàng Ân',1,'0397769522','52'),
('5','Phan Yến Vi',0,'0377765521','53');
INSERT INTO `hedaotao` VALUES (2,'Đại Trà'),(3,'Chất Lượng Cao');
INSERT INTO `hocky`(id_hoc_ky, ten_hoc_ky_id_loai_hoc_ky, ngay_bat_dau, ngay_ket_thuc, id_lop_id_lop_hoc, ngay_dang_ky, ngay_het_han) VALUES (3,2,'2024-01-30 00:00:00','2024-04-30 00:00:00',9,'2023-10-18 00:00:00','2023-10-23 00:00:00'),(4,1,'2023-10-18 00:00:00','2024-01-20 00:00:00',9,'2023-10-10 00:00:00','2023-10-15 00:00:00'),(5,1,'2023-10-18 00:00:00','2024-01-20 00:00:00',10,'2023-10-10 00:00:00','2023-10-15 00:00:00');
INSERT INTO `khoa` VALUES (1,'2020-2024'),(2,'2021-2025');
INSERT INTO `khoadaotao` VALUES (1,'Công Nghệ Thông Tin'),(2,'Kinh Tế'),(4,'Ngôn Ngữ'),(5,'Thể Dục');
INSERT INTO `loaidiem` VALUES (1,'Điểm Giữa Kỳ'),(2,'Điểm Cuối Kỳ'),(3,'Điểm KT1'),(4,'Điểm KT2'),(5,'Điểm KT3'),(6,'Điểm Trung Bình');
INSERT INTO `loaihocky` VALUES (1,'Học Kỳ 1'),(2,'Học Kỳ 2'),(3,'Học Kỳ 3'),(4,'Học Kỳ 4'),(5,'Học Kỳ 5'),(6,'Học Kỳ 6'),(7,'Học Kỳ 7'),(8,'Học Kỳ 8'),(9,'Học Kỳ 9'),(10,'Học Kỳ 10'),(11,'Học Kỳ 11');
INSERT INTO `lophoc`(id_lop_hoc, ten_lop_hoc, id_khoa_hoc_idkhoa, id_nganh_id_nganh_dao_tao, id_he_dao_tao_idhedaotao) VALUES (9,'DH20IT01',1,1,2),(10,'DH20CS01',1,2,2),(11,'DH20IT02',1,1,2),(12,'DH20IT03',1,1,2),(13,'DH20CS02',1,2,2);
INSERT INTO `monhoc`(id_mon_hoc, ten_mon_hoc, so_tin_chi) VALUES (30,'Khoa Học Máy Tính',4),(31,'Lập Trình Hiện Đại',4),(32,'Cơ Sở Lập Trình',4),(33,'Kiến Trúc Lập Trình',4),(34,'Lập Trình Hướng Đối Tượng',4),(36,'Kiến Trúc Máy Tính',4),(37,'Thiết Kế Web',4),(38,'Công Nghệ Phần Mềm',4),(39,'Toán Cao Cấp',4),(40,'Mạng Máy Tính',4),(41,'Tiếng Anh Nâng Cao 1',3),(42,'Tiếng Anh Nâng Cao 2',3),(43,'Tiếng Anh Nâng Cao 3',3),(44,'Cấu Trúc Dữ Liệu Và Giải Thuật',4);
INSERT INTO `monhoc_hocky`(id_mon_hoc_hoc_ky, id_mon_hoc_id_mon_hoc, id_hocky_id_hoc_ky, so_luong, ngay_bat_dau, ngay_ket_thuc, id_giang_vien_id_giang_vien, phong_hoc_id_phong_hoc, so_luong_con_lai) VALUES (28,30,3,80,'2024-01-30 00:00:00','2024-04-30 00:00:00','19',1,78),(29,31,3,80,'2024-01-30 00:00:00','2024-04-30 00:00:00','19',1,80),(46,32,4,80,'2023-10-18 00:00:00','2023-01-20 00:00:00','19',2,73),(47,33,4,80,'2023-10-18 00:00:00','2024-01-20 00:00:00','19',2,73),(48,34,4,80,'2023-10-18 00:00:00','2024-01-20 00:00:00','19',2,80),(49,36,4,80,'2023-10-18 00:00:00','2024-01-20 00:00:00','19',3,80),(50,32,5,80,'2023-10-18 00:00:00','2024-01-20 00:00:00','19',4,80),(51,33,5,80,'2023-10-18 00:00:00','2024-01-20 00:00:00','19',5,80),(52,34,5,80,'2023-10-18 00:00:00','2024-01-20 00:00:00','19',6,80),(53,36,5,80,'2023-10-18 00:00:00','2024-01-20 00:00:00','19',7,80),(54,32,3,80,'2024-01-30 00:00:00','2024-04-30 00:00:00','19',8,80),(55,33,3,80,'2024-01-30 00:00:00','2024-04-30 00:00:00','19',1,80),(56,34,3,80,'2024-01-30 00:00:00','2024-04-30 00:00:00','19',2,80);
INSERT INTO `monhocdangky`(id_mon_hoc_dang_ky, id_mon_hoc_id_mon_hoc_hoc_ky, id_sinh_vien_id_sinh_vien, xep_loai, trang_thai, khoa_mon, thanh_toan) VALUES (55,46,'52','Giỏi',0,0,1),(56,46,'53','Giỏi',0,0,1),(57,46,'54','Giỏi',0,0,1),(58,46,'55','Giỏi',0,0,1),(59,46,'59','Giỏi',0,0,1),(60,46,'57','Giỏi',0,0,1),(61,46,'58','Giỏi',0,0,1),(62,47,'52','Giỏi',0,0,1),(63,47,'53','Giỏi',0,0,1),(64,47,'54','Giỏi',0,0,1),(65,47,'55','Giỏi',0,0,1),(66,47,'59','Giỏi',0,0,1),(67,47,'57','Giỏi',0,0,1),(73,28,'52',NULL,0,0,1);
INSERT INTO `nganhdaotao` VALUES (1,'Công Nghệ Thông Tin',1),(2,'Khoa Học Máy Tính',1);
INSERT INTO `phonghoc` VALUES (1,'NK-001'),(2,'NK-002'),(3,'NK-003'),(4,'NK-004'),(5,'NK-005'),(6,'NK-006'),(7,'NK-007'),(8,'NK-101'),(9,'NK-102'),(10,'NK-103'),(11,'NK-104');
INSERT INTO `sinhvien`(id_sinh_vien, ho_ten, ngay_sinh, dia_chi, gioi_tinh, so_dien_thoai, ma_lop_id_lop_hoc, email, id_tai_khoan_id_tai_khoan) VALUES 
('52','Nguyễn Thanh Thuyền','2002-04-22','200/14 Gò Dầu, Tân Quý, Tân Phú',1,'0371251727',9,'thanhthuyen@ou.edu.vn','abc56'),
('53','Lê Anh Khoa','2002-03-21','37 Lê Thánh Tông, P11, Tân Bình',1,'0277650217',9,'cua2432002@gmail.com','abc63'),
('54','Phan Quế Thanh','2002-03-05','41 Tân Hải, P13, Tân Bình',0,'0722451702',9,'quethanh@ou.edu.vn',NULL),
('55','Phan Anh Thư','2002-04-06','31 Lê Lai, p14, Gò Vấp',0,'0211745022',9,'anhthu@ou.edu.vn',NULL),
('57','Phan Thanh Hải','2002-04-06','41 Tân Hải, P13, Tân Bình',1,'0123456789',9,'thanhhai@ou.edu.vn',NULL),
('58','Nguyễn Hải Ninh','2002-03-21','37 Lê Thánh Tông, P11, Tân Bình',1,'1234565678',10,'haininh@ou.edu.vn',NULL),
('59','Lâm Thi Hạnh','2002-03-05','200/14 Gò Dầu, Tân Quý, Tân Phú',0,'0124711246',10,'thihanh@ou.edu.vn',NULL),
('60','Nguyễn Mỹ Chi','2002-03-05','37 Lê Thánh Tông, P11, Tân Bình',0,'2725241359',10,'mychi@ou.edu.vn',NULL),
('61','Hàn Thanh Lâm','2002-03-05','31 Lê Lai, p14, Gò Vấp',1,'7112583146',10,'thanhlam@ou.edu.vn',NULL),
('62','Tăng Thị Mỹ','2002-03-05','41 Tân Hải, P13, Tân Bình',0,'7120311245',10,'thimy@ou.edu.vn',NULL);
INSERT INTO `taikhoan` VALUES ('52',NULL),('53',NULL),('54','https://res.cloudinary.com/dhcvsbuew/image/upload/v1697104804/ihv6h8jxle6xhnvtchuv.jpg'),('55',NULL),('abc56','https://res.cloudinary.com/dhcvsbuew/image/upload/v1697662181/kyxsf60npwxl8dltsw2h.jpg'),('62',NULL),('abc63',NULL);
INSERT INTO `traloidiendan`(id_tra_loi_dien_dan, noi_dung_tra_loi, id_tai_khoan_id_tai_khoan, id_cau_hoi_dien_dan_id_cau_hoi_dien_dan) VALUES (34,'Có TOD nha bạn.','54',56);