-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 08, 2025 at 06:55 AM
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
  `loai_dat` varchar(255) DEFAULT NULL,
  `don_gia_m2` double DEFAULT NULL,
  `thue_suat` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `bang_gia_dat`
--

INSERT INTO `bang_gia_dat` (`id`, `loai_dat`, `don_gia_m2`, `thue_suat`) VALUES
(1, 'ODT', 30000000, 0.0005),
(2, 'ONT', 5000000, 0.0003);

-- --------------------------------------------------------

--
-- Table structure for table `ho_so_khai_thue`
--

CREATE TABLE `ho_so_khai_thue` (
  `id` bigint(20) NOT NULL,
  `dien_tich_khai_bao` double DEFAULT NULL,
  `ghi_chu` varchar(255) DEFAULT NULL,
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

INSERT INTO `ho_so_khai_thue` (`id`, `dien_tich_khai_bao`, `ghi_chu`, `ma_nguoi_khai`, `ma_thua_dat`, `muc_dich_su_dung`, `nam_khai_thue`, `ngay_nop`, `so_tien_thue`, `trang_thai`) VALUES
(1, 100, 'HoSoHopLe', 1, 1, 'Dat o', 2025, '2025-12-08 12:04:36.000000', 300000, 'DA_DUYET'),
(2, 80, 'Hệ thống phát hiện diện tích khai báo (80.0) nhỏ hơn diện tích gốc (100.0). Vui lòng giải trình.', 1, 1, 'Dat o', 2025, '2025-12-08 12:05:07.000000', 239999.99999999997, 'CANH_BAO_GIAN_LAN'),
(4, 100, '', 1, 1, 'Dat o', 2025, '2025-12-08 12:26:50.000000', 1000000, 'CHO_DUYET'),
(5, 100, '', 3, 1, 'Dat o', 2025, '2025-12-08 12:37:55.000000', 1000000, 'CHO_DUYET');

-- --------------------------------------------------------

--
-- Table structure for table `nguoi_dung`
--

CREATE TABLE `nguoi_dung` (
  `id` bigint(20) NOT NULL,
  `ten_dang_nhap` varchar(255) DEFAULT NULL,
  `mat_khau` varchar(255) DEFAULT NULL,
  `ho_ten` varchar(255) DEFAULT NULL,
  `cccd` varchar(255) DEFAULT NULL,
  `vai_tro` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `nguoi_dung`
--

INSERT INTO `nguoi_dung` (`id`, `ten_dang_nhap`, `mat_khau`, `ho_ten`, `cccd`, `vai_tro`) VALUES
(1, 'admin', '123456', 'Quan Tri Vien', '001090000001', 'ADMIN'),
(2, 'canbo', '123456', 'Nguyen Van Can Bo', '001090000002', 'CAN_BO'),
(3, 'chudat1', '123456', 'Tran Van Chu Dat', '001090000003', 'CHU_DAT'),
(4, 'canbo_moi', '123456', 'Nguyen Van A', '0987654321', 'CAN_BO');

-- --------------------------------------------------------

--
-- Table structure for table `thua_dat`
--

CREATE TABLE `thua_dat` (
  `id` bigint(20) NOT NULL,
  `dia_chi` varchar(255) DEFAULT NULL,
  `dien_tich_goc` double DEFAULT NULL,
  `loai_dat_quy_hoach` varchar(255) DEFAULT NULL,
  `ma_chu_so_huu` bigint(20) DEFAULT NULL,
  `so_thua` varchar(255) DEFAULT NULL,
  `so_to` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `thua_dat`
--

INSERT INTO `thua_dat` (`id`, `dia_chi`, `dien_tich_goc`, `loai_dat_quy_hoach`, `ma_chu_so_huu`, `so_thua`, `so_to`) VALUES
(1, 'So 1 Dai Lo Thang Long', 100, 'ODT', 3, 'Thua 10', 'To 01'),
(2, 'Ngo 5 Nguyen Trai', 50, 'ONT', 2, 'Thua 20', 'To 02');

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
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `nguoi_dung`
--
ALTER TABLE `nguoi_dung`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `ten_dang_nhap` (`ten_dang_nhap`);

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
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `ho_so_khai_thue`
--
ALTER TABLE `ho_so_khai_thue`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `nguoi_dung`
--
ALTER TABLE `nguoi_dung`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `thua_dat`
--
ALTER TABLE `thua_dat`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
