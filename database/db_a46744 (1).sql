-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 08, 2025 at 11:12 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_a46744`
--

-- --------------------------------------------------------

--
-- Table structure for table `bang_gia_dat`
--

CREATE TABLE `bang_gia_dat` (
  `id` bigint(20) NOT NULL,
  `don_gia_m2` double DEFAULT NULL,
  `khu_vuc` varchar(255) DEFAULT NULL,
  `ma_loai_dat` varchar(255) DEFAULT NULL,
  `nam_ap_dung` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `bang_gia_dat`
--

INSERT INTO `bang_gia_dat` (`id`, `don_gia_m2`, `khu_vuc`, `ma_loai_dat`, `nam_ap_dung`) VALUES
(1, 20000000, 'Huyen A', 'ODT', 2025),
(2, 5000000, 'Huyen A', 'ONT', 2025),
(3, 1000000, 'Huyen A', 'CLN', 2025);

-- --------------------------------------------------------

--
-- Table structure for table `ho_so_khai_thue`
--

CREATE TABLE `ho_so_khai_thue` (
  `ma_ho_so` bigint(20) NOT NULL,
  `dien_tich_khai_bao` double DEFAULT NULL,
  `ghi_chu` varchar(1000) DEFAULT NULL,
  `ma_nguoi_khai` bigint(20) DEFAULT NULL,
  `ma_thua_dat` bigint(20) DEFAULT NULL,
  `muc_dich_su_dung` varchar(255) DEFAULT NULL,
  `nam_khai_thue` int(11) DEFAULT NULL,
  `ngay_nop` datetime(6) DEFAULT NULL,
  `so_tien_thue` double DEFAULT NULL,
  `trang_thai` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `ho_so_khai_thue`
--

INSERT INTO `ho_so_khai_thue` (`ma_ho_so`, `dien_tich_khai_bao`, `ghi_chu`, `ma_nguoi_khai`, `ma_thua_dat`, `muc_dich_su_dung`, `nam_khai_thue`, `ngay_nop`, `so_tien_thue`, `trang_thai`) VALUES
(1, 100, 'Nộp lần đầu', 3, 1, 'ODT', 2025, '2025-12-08 17:10:17.000000', 1000000, 'CHO_DUYET');

-- --------------------------------------------------------

--
-- Table structure for table `khieu_nai`
--

CREATE TABLE `khieu_nai` (
  `id` bigint(20) NOT NULL,
  `ma_ho_so` bigint(20) DEFAULT NULL,
  `ma_nguoi_gui` bigint(20) DEFAULT NULL,
  `ngay_gui` datetime(6) DEFAULT NULL,
  `noi_dung` varchar(1000) DEFAULT NULL,
  `phan_hoi_cua_can_bo` varchar(255) DEFAULT NULL,
  `trang_thai` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `loai_dat`
--

CREATE TABLE `loai_dat` (
  `ma_loai_dat` varchar(255) NOT NULL,
  `ten_loai_dat` varchar(255) DEFAULT NULL,
  `thue_suat` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `loai_dat`
--

INSERT INTO `loai_dat` (`ma_loai_dat`, `ten_loai_dat`, `thue_suat`) VALUES
('CLN', 'Đất trồng cây lâu năm', 0.0001),
('LUA', 'Đất trồng lúa', 0),
('ODT', 'Đất ở đô thị', 0.0005),
('ONT', 'Đất ở nông thôn', 0.0003);

-- --------------------------------------------------------

--
-- Table structure for table `nguoi_dung`
--

CREATE TABLE `nguoi_dung` (
  `id` bigint(20) NOT NULL,
  `cccd` varchar(255) DEFAULT NULL,
  `dia_chi` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `ho_ten` varchar(255) DEFAULT NULL,
  `mat_khau` varchar(255) DEFAULT NULL,
  `sdt` varchar(255) DEFAULT NULL,
  `ten_dang_nhap` varchar(255) NOT NULL,
  `vai_tro` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `nguoi_dung`
--

INSERT INTO `nguoi_dung` (`id`, `cccd`, `dia_chi`, `email`, `ho_ten`, `mat_khau`, `sdt`, `ten_dang_nhap`, `vai_tro`) VALUES
(1, '001090000001', 'Hà Nội', 'admin@tax.gov.vn', 'Quản Trị Viên', '123456', '0901111111', 'admin', 'ADMIN'),
(2, '001090000002', 'Hà Nội', 'canbo1@tax.gov.vn', 'Nguyễn Văn Cán Bộ', '123456', '0902222222', 'canbo1', 'CAN_BO'),
(3, '001090000003', 'Số 5 Nguyễn Trãi', 'dan1@gmail.com', 'Trần Văn Chủ Đất', '123456', '0903333333', 'chudat1', 'CHU_DAT'),
(4, '001090000004', 'Biệt thự Tây Hồ', 'dan2@gmail.com', 'Lê Thị Giàu Có', '123456', '0904444444', 'chudat2', 'CHU_DAT');

-- --------------------------------------------------------

--
-- Table structure for table `thua_dat`
--

CREATE TABLE `thua_dat` (
  `id` bigint(20) NOT NULL,
  `dia_chi` varchar(255) DEFAULT NULL,
  `dien_tich_goc` double DEFAULT NULL,
  `khu_vuc` varchar(255) DEFAULT NULL,
  `ma_chu_so_huu` bigint(20) DEFAULT NULL,
  `ma_loai_dat` varchar(255) DEFAULT NULL,
  `so_thua` varchar(255) DEFAULT NULL,
  `so_to` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `thua_dat`
--

INSERT INTO `thua_dat` (`id`, `dia_chi`, `dien_tich_goc`, `khu_vuc`, `ma_chu_so_huu`, `ma_loai_dat`, `so_thua`, `so_to`) VALUES
(1, 'Số 1 Đại Lộ Thăng Long', 100, 'Huyen A', 3, 'ODT', 'Thửa 01', 'Tờ 10'),
(2, 'Ngõ 5 Xã X', 500, 'Huyen A', 3, 'CLN', 'Thửa 02', 'Tờ 10'),
(3, 'Khu Biệt Thự Ven Hồ', 200, 'Huyen A', 4, 'ODT', 'Thửa 05', 'Tờ 20');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bang_gia_dat`
--
ALTER TABLE `bang_gia_dat`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `ho_so_khai_thue`
--
ALTER TABLE `ho_so_khai_thue`
  ADD PRIMARY KEY (`ma_ho_so`);

--
-- Indexes for table `khieu_nai`
--
ALTER TABLE `khieu_nai`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `loai_dat`
--
ALTER TABLE `loai_dat`
  ADD PRIMARY KEY (`ma_loai_dat`);

--
-- Indexes for table `nguoi_dung`
--
ALTER TABLE `nguoi_dung`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKo0s268lrp9is6o1e4ek6m1lc6` (`ten_dang_nhap`),
  ADD UNIQUE KEY `UKga1g2gg00rdm6nm6c3rnv0syu` (`cccd`);

--
-- Indexes for table `thua_dat`
--
ALTER TABLE `thua_dat`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `bang_gia_dat`
--
ALTER TABLE `bang_gia_dat`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `ho_so_khai_thue`
--
ALTER TABLE `ho_so_khai_thue`
  MODIFY `ma_ho_so` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `khieu_nai`
--
ALTER TABLE `khieu_nai`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `nguoi_dung`
--
ALTER TABLE `nguoi_dung`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `thua_dat`
--
ALTER TABLE `thua_dat`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
