-- Tạo Database
CREATE DATABASE IF NOT EXISTS QuanLyThueDat_VieID CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE QuanLyThueDat_VieID;

-- 1. Bảng VaiTro [cite: 709, 710]
DROP TABLE IF EXISTS VaiTro;
CREATE TABLE VaiTro (
    maVaiTro INT AUTO_INCREMENT PRIMARY KEY,
    tenVaiTro VARCHAR(50) NOT NULL,
    moTa VARCHAR(255)
);

-- 2. Bảng NguoiDung [cite: 713, 714]
CREATE TABLE NguoiDung (
    maNguoiDung BIGINT AUTO_INCREMENT PRIMARY KEY,
    tenDangNhap VARCHAR(50) NOT NULL UNIQUE,
    matKhau VARCHAR(255) NOT NULL,
    hoTen VARCHAR(100) NOT NULL,
    soDinhDanh VARCHAR(20) NOT NULL UNIQUE COMMENT 'Số CCCD hoặc Mã số thuế (Khóa VieID)',
    quocTich VARCHAR(50) DEFAULT 'Việt Nam',
    loaiDoiTuong VARCHAR(50) COMMENT 'Cá nhân, Tổ chức',
    email VARCHAR(100),
    sdt VARCHAR(15),
    diaChi VARCHAR(255),
    maVaiTro INT NOT NULL,
    trangThai TINYINT(1) DEFAULT 1 COMMENT '1: Hoạt động, 0: Khóa',
    ngayTao DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (maVaiTro) REFERENCES VaiTro(maVaiTro)
);

-- 3. Bảng LoaiDat [cite: 717, 718]
CREATE TABLE LoaiDat (
    maLoaiDat INT AUTO_INCREMENT PRIMARY KEY,
    tenLoaiDat VARCHAR(100) NOT NULL,
    moTa TEXT,
    thueSuat DECIMAL(5,2) NOT NULL COMMENT 'Đơn vị tính %'
);

-- 4. Bảng KhuVuc [cite: 721, 722]
CREATE TABLE KhuVuc (
    maKhuVuc INT AUTO_INCREMENT PRIMARY KEY,
    tenKhuVuc VARCHAR(100) NOT NULL,
    moTa TEXT,
    heSoK FLOAT NOT NULL COMMENT 'Hệ số điều chỉnh giá đất'
);

-- 5. Bảng ThuaDat [cite: 725, 726]
CREATE TABLE ThuaDat (
    maThuaDat BIGINT AUTO_INCREMENT PRIMARY KEY,
    soTo VARCHAR(20) NOT NULL,
    soThua VARCHAR(20) NOT NULL,
    diaChiChiTiet VARCHAR(255),
    dienTichGoc DOUBLE NOT NULL,
    mucDichSuDung VARCHAR(100),
    maLoaiDat INT,
    maKhuVuc INT,
    maChuSoHuu BIGINT,
    trangThai VARCHAR(50) DEFAULT 'Đang sử dụng',
    ngayTao DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (maLoaiDat) REFERENCES LoaiDat(maLoaiDat),
    FOREIGN KEY (maKhuVuc) REFERENCES KhuVuc(maKhuVuc),
    FOREIGN KEY (maChuSoHuu) REFERENCES NguoiDung(maNguoiDung)
);

-- 6. Bảng BangGiaDat [cite: 729, 730]
CREATE TABLE BangGiaDat (
    maBangGia INT AUTO_INCREMENT PRIMARY KEY,
    maKhuVuc INT,
    maLoaiDat INT,
    donGiaM2 DECIMAL(15,2) NOT NULL,
    ngayBanHanh DATE,
    ngayHetHieuLuc DATE,
    soCongVanQuyDinh VARCHAR(50),
    trangThai VARCHAR(20) DEFAULT 'Hiệu lực',
    FOREIGN KEY (maKhuVuc) REFERENCES KhuVuc(maKhuVuc),
    FOREIGN KEY (maLoaiDat) REFERENCES LoaiDat(maLoaiDat)
);

-- 7. Bảng HoSoKhaiThue [cite: 733, 734]
CREATE TABLE HoSoKhaiThue (
    maHoSo BIGINT AUTO_INCREMENT PRIMARY KEY,
    maThuaDat BIGINT,
    maChuDat BIGINT,
    dienTichKhaiBao DOUBLE,
    dienTichThucTe DOUBLE,
    mucDichSuDungKhaiBao VARCHAR(100),
    tongGiaTriDat DECIMAL(15,2),
    soTienPhaiDong DECIMAL(15,2),
    dauHieuSaiLech BOOLEAN DEFAULT FALSE,
    fileDinhKemGiaoDich TEXT,
    trangThaiXuLy VARCHAR(50) DEFAULT 'Chờ duyệt',
    ngayTao DATETIME DEFAULT CURRENT_TIMESTAMP,
    ngayDuyet DATETIME,
    FOREIGN KEY (maThuaDat) REFERENCES ThuaDat(maThuaDat),
    FOREIGN KEY (maChuDat) REFERENCES NguoiDung(maNguoiDung)
);

-- 8. Bảng LichSuSoHuu [cite: 737, 738]
CREATE TABLE LichSuSoHuu (
    maLichSu BIGINT AUTO_INCREMENT PRIMARY KEY,
    maThuaDat BIGINT,
    maChuDat BIGINT,
    ngayBatDau DATETIME NOT NULL,
    ngayKetThuc DATETIME,
    trangThai VARCHAR(50),
    hinhThucSoHuu VARCHAR(50),
    FOREIGN KEY (maThuaDat) REFERENCES ThuaDat(maThuaDat),
    FOREIGN KEY (maChuDat) REFERENCES NguoiDung(maNguoiDung)
);

-- 9. Bảng NhatKyXuLy [cite: 741, 742]
CREATE TABLE NhatKyXuLy (
    maNhatKy BIGINT AUTO_INCREMENT PRIMARY KEY,
    maHoSo BIGINT,
    maCanBo BIGINT,
    trangThaiTu VARCHAR(50),
    trangThaiDen VARCHAR(50),
    ghiChu TEXT,
    thoiGianXuLy DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (maHoSo) REFERENCES HoSoKhaiThue(maHoSo),
    FOREIGN KEY (maCanBo) REFERENCES NguoiDung(maNguoiDung)
);
USE QuanLyThueDat_VieID;

-- Tắt kiểm tra khóa ngoại để xóa và chèn dữ liệu dễ dàng
SET FOREIGN_KEY_CHECKS = 0;

-- Xóa dữ liệu cũ (nếu muốn làm sạch DB trước khi thêm)
TRUNCATE TABLE NhatKyXuLy;
TRUNCATE TABLE LichSuSoHuu;
TRUNCATE TABLE HoSoKhaiThue;
TRUNCATE TABLE ThuaDat;
TRUNCATE TABLE BangGiaDat;
TRUNCATE TABLE NguoiDung;
TRUNCATE TABLE KhuVuc;
TRUNCATE TABLE LoaiDat;
TRUNCATE TABLE VaiTro;

-- 1. Thêm VAI TRÒ (4 bản ghi cố định)
INSERT INTO VaiTro (tenVaiTro, moTa) VALUES 
('Admin', 'Quản trị viên hệ thống'),
('CanBoThue', 'Cán bộ thuế'),
('QuanLyDatDai', 'Cán bộ địa chính'),
('ChuDat', 'Người dân/Doanh nghiệp');

-- 2. Thêm LOẠI ĐẤT (10 bản ghi mẫu)
INSERT INTO LoaiDat (tenLoaiDat, moTa, thueSuat) VALUES 
('Đất ở nông thôn', 'Đất ở tại xã', 0.03),
('Đất ở đô thị', 'Đất ở tại phường, thị trấn', 0.05),
('Đất thương mại', 'Xây dựng cơ sở kinh doanh', 0.03),
('Đất sản xuất phi nông nghiệp', 'Nhà xưởng, kho bãi', 0.03),
('Đất trồng lúa', 'Canh tác lúa nước', 0.00),
('Đất trồng cây lâu năm', 'Cây ăn quả, công nghiệp', 0.00),
('Đất rừng sản xuất', 'Trồng rừng khai thác', 0.00),
('Đất nuôi trồng thủy sản', 'Ao hồ đầm', 0.00),
('Đất công cộng', 'Đường xá, cầu cống', 0.00),
('Đất khu công nghiệp', 'Khu chế xuất', 0.04);

-- 3. Thêm KHU VỰC (10 bản ghi mẫu)
INSERT INTO KhuVuc (tenKhuVuc, moTa, heSoK) VALUES 
('Quận Ba Đình - Vị trí 1', 'Mặt đường lớn trung tâm', 2.5),
('Quận Ba Đình - Vị trí 2', 'Ngõ rộng ô tô', 2.0),
('Quận Cầu Giấy - Vị trí 1', 'Mặt phố kinh doanh', 1.8),
('Quận Cầu Giấy - Vị trí 2', 'Ngõ nhỏ', 1.5),
('Quận Hoàng Mai - Vị trí 1', 'Khu đô thị mới', 1.4),
('Huyện Đông Anh - Khu A', 'Gần cầu Nhật Tân', 1.2),
('Huyện Sóc Sơn - Khu B', 'Gần sân bay', 1.1),
('Quận Hà Đông - Khu C', 'Xa trung tâm', 1.2),
('Thị xã Sơn Tây', 'Trung tâm thị xã', 1.0),
('Huyện Ba Vì', 'Vùng núi', 0.8);
DROP PROCEDURE IF EXISTS GenerateData;

DELIMITER $$

CREATE PROCEDURE GenerateData()
BEGIN
    DECLARE i INT DEFAULT 1;
    
    -- 4. Sinh 60 NGƯỜI DÙNG (Để đảm bảo có đủ Admin, Cán bộ và Chủ đất)
    SET i = 1;
    WHILE i <= 60 DO
        INSERT INTO NguoiDung (tenDangNhap, matKhau, hoTen, soDinhDanh, quocTich, loaiDoiTuong, email, sdt, diaChi, maVaiTro, trangThai)
        VALUES (
            CONCAT('user', i), 
            'e10adc3949ba59abbe56e057f20f883e', -- password: 123456
            CONCAT('Nguyễn Văn ', CHAR(65 + (i % 26)), i), -- Tạo tên: Nguyễn Văn A1...
            CONCAT('00109', LPAD(i, 7, '0')), -- CCCD: 001090000001...
            IF(i % 10 = 0, 'Nước ngoài', 'Việt Nam'),
            IF(i % 5 = 0, 'Tổ chức', 'Cá nhân'),
            CONCAT('user', i, '@gmail.com'),
            CONCAT('098', LPAD(i, 7, '0')),
            CONCAT('Địa chỉ số ', i, ', Hà Nội'),
            IF(i <= 5, 1, IF(i <= 10, 2, IF(i <= 15, 3, 4))), -- 5 Admin, 5 Cán bộ thuế, 5 Cán bộ đất, còn lại là Chủ đất
            1
        );
        SET i = i + 1;
    END WHILE;

    -- 5. Sinh 50 BẢNG GIÁ ĐẤT
    SET i = 1;
    WHILE i <= 50 DO
        INSERT INTO BangGiaDat (maKhuVuc, maLoaiDat, donGiaM2, ngayBanHanh, ngayHetHieuLuc, soCongVanQuyDinh, trangThai)
        VALUES (
            FLOOR(1 + RAND() * 10), -- Random Khu vực 1-10
            FLOOR(1 + RAND() * 10), -- Random Loại đất 1-10
            FLOOR(1000000 + RAND() * 50000000), -- Giá từ 1tr đến 50tr
            '2024-01-01',
            '2025-12-31',
            CONCAT('QD-', 2024 + i, '/UBND'),
            'Hiệu lực'
        );
        SET i = i + 1;
    END WHILE;

    -- 6. Sinh 60 THỬA ĐẤT (Liên kết với Chủ đất có ID từ 16 đến 60)
    SET i = 1;
    WHILE i <= 60 DO
        INSERT INTO ThuaDat (soTo, soThua, diaChiChiTiet, dienTichGoc, mucDichSuDung, maLoaiDat, maKhuVuc, maChuSoHuu, trangThai)
        VALUES (
            CONCAT('T', FLOOR(1 + RAND() * 100)),
            CONCAT('S', FLOOR(1 + RAND() * 500)),
            CONCAT('Lô đất số ', i, ', Khu vực ', FLOOR(1 + RAND() * 10)),
            FLOOR(30 + RAND() * 1000), -- Diện tích 30m2 - 1000m2
            IF(RAND() > 0.5, 'Đất ở', 'Kinh doanh'),
            FLOOR(1 + RAND() * 10),
            FLOOR(1 + RAND() * 10),
            FLOOR(16 + RAND() * 44), -- Random Chủ đất (ID từ 16-60)
            'Đang sử dụng'
        );
        SET i = i + 1;
    END WHILE;

    -- 7. Sinh 60 HỒ SƠ KHAI THUẾ
    SET i = 1;
    WHILE i <= 60 DO
        INSERT INTO HoSoKhaiThue (maThuaDat, maChuDat, dienTichKhaiBao, dienTichThucTe, mucDichSuDungKhaiBao, tongGiaTriDat, soTienPhaiDong, trangThaiXuLy, ngayTao)
        VALUES (
            i, -- Mỗi hồ sơ gắn với 1 thửa đất theo thứ tự
            (SELECT maChuSoHuu FROM ThuaDat WHERE maThuaDat = i), -- Lấy đúng chủ của đất đó
            100.0, -- Giả lập khai báo
            100.0,
            'Đất ở',
            FLOOR(100000000 + RAND() * 1000000000),
            FLOOR(500000 + RAND() * 5000000), -- Thuế từ 500k - 5tr
            ELT(FLOOR(1 + RAND() * 3), 'Chờ duyệt', 'Đã duyệt', 'Yêu cầu bổ sung'),
            DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 365) DAY)
        );
        SET i = i + 1;
    END WHILE;

    -- 8. Sinh 50 LỊCH SỬ SỞ HỮU
    SET i = 1;
    WHILE i <= 50 DO
        INSERT INTO LichSuSoHuu (maThuaDat, maChuDat, ngayBatDau, ngayKetThuc, trangThai, hinhThucSoHuu)
        VALUES (
            FLOOR(1 + RAND() * 60), -- Random Thửa đất
            FLOOR(16 + RAND() * 44), -- Random Chủ đất
            DATE_SUB(NOW(), INTERVAL FLOOR(1000 + RAND() * 2000) DAY),
            NULL,
            'Đang sở hữu',
            'Riêng'
        );
        SET i = i + 1;
    END WHILE;

    -- 9. Sinh 50 NHẬT KÝ XỬ LÝ
    SET i = 1;
    WHILE i <= 50 DO
        INSERT INTO NhatKyXuLy (maHoSo, maCanBo, trangThaiTu, trangThaiDen, ghiChu, thoiGianXuLy)
        VALUES (
            FLOOR(1 + RAND() * 60), -- Random Hồ sơ
            FLOOR(6 + RAND() * 5), -- Random Cán bộ thuế (ID từ 6-10)
            'Chờ duyệt',
            'Đã duyệt',
            'Hồ sơ hợp lệ, đủ điều kiện',
            DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 30) DAY)
        );
        SET i = i + 1;
    END WHILE;

END$$

DELIMITER ;
-- Gọi thủ tục để sinh dữ liệu
CALL GenerateData();

-- Bật lại kiểm tra khóa ngoại
SET FOREIGN_KEY_CHECKS = 1;

-- Kiểm tra kết quả (đếm số lượng bản ghi)
SELECT 'NguoiDung' AS TableName, COUNT(*) AS Total FROM NguoiDung
UNION ALL
SELECT 'ThuaDat', COUNT(*) FROM ThuaDat
UNION ALL
SELECT 'HoSoKhaiThue', COUNT(*) FROM HoSoKhaiThue
UNION ALL
SELECT 'NhatKyXuLy', COUNT(*) FROM NhatKyXuLy;

USE QuanLyThueDat_VieID;

-- 1. Tắt kiểm tra khóa ngoại để xóa dữ liệu cũ
SET FOREIGN_KEY_CHECKS = 0;

-- 2. Xóa sạch dữ liệu bảng NguoiDung
TRUNCATE TABLE NguoiDung;

-- 3. Tạo lại thủ tục sinh dữ liệu với mật khẩu '123456'
DROP PROCEDURE IF EXISTS SeedUsers_SimplePass;

DELIMITER $$
CREATE PROCEDURE SeedUsers_SimplePass()
BEGIN
    DECLARE i INT DEFAULT 1;

    -- A. Thêm Admin và Cán bộ (Dữ liệu tĩnh) - Mật khẩu 123456
    INSERT INTO NguoiDung (tenDangNhap, matKhau, hoTen, soDinhDanh, email, sdt, diaChi, maVaiTro, trangThai) VALUES 
    ('admin', '123456', 'Nguyễn Quản Trị', '001090009999', 'admin@vieid.gov.vn', '0901234567', 'Hà Nội', 1, 1),
    ('canbo1', '123456', 'Trần Thanh Tra', '001090008888', 'thue1@vieid.gov.vn', '0901234568', 'Ba Đình, Hà Nội', 2, 1),
    ('canbo2', '123456', 'Lê Địa Chính', '001090007777', 'datdai1@vieid.gov.vn', '0901234569', 'Cầu Giấy, Hà Nội', 3, 1);
    
    -- B. Thêm 50 Người dân (Dữ liệu động) - Mật khẩu 123456
    WHILE i <= 50 DO
        INSERT INTO NguoiDung (tenDangNhap, matKhau, hoTen, soDinhDanh, email, sdt, diaChi, maVaiTro, trangThai) 
        VALUES (
            CONCAT('user', i), 
            '123456', -- Mật khẩu cố định 6 kí tự
            CONCAT('Người Dân ', i),
            CONCAT('0300900', LPAD(i, 5, '0')), -- Tạo số CCCD giả: 030090000001...
            CONCAT('user', i, '@gmail.com'),
            CONCAT('098', LPAD(i, 7, '0')),
            'Hà Nội',
            4, -- Vai trò Chủ đất
            1  -- Trạng thái Hoạt động
        );
        SET i = i + 1;
    END WHILE;
END$$
DELIMITER ;

-- 4. Chạy thủ tục để nạp dữ liệu
CALL SeedUsers_SimplePass();

-- 5. Bật lại kiểm tra khóa ngoại
SET FOREIGN_KEY_CHECKS = 1;

-- 6. Kiểm tra kết quả
SELECT maNguoiDung, tenDangNhap, matKhau FROM NguoiDung LIMIT 10;

SELECT * FROM NguoiDung;
