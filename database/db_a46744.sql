-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 01, 2026 at 08:38 AM
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
-- Table structure for table `complaints`
--

CREATE TABLE `complaints` (
  `id` bigint(20) NOT NULL,
  `declaration_id` bigint(20) DEFAULT NULL,
  `sender_id` bigint(20) DEFAULT NULL,
  `submission_date` datetime(6) DEFAULT NULL,
  `content` varchar(1000) DEFAULT NULL,
  `officer_response` varchar(255) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `evidence_file` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `land_plots`
--

CREATE TABLE `land_plots` (
  `id` bigint(20) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `original_area` double DEFAULT NULL,
  `zone_name` varchar(255) DEFAULT NULL,
  `owner_id` bigint(20) DEFAULT NULL,
  `land_type_id` varchar(50) DEFAULT NULL,
  `plot_number` varchar(50) DEFAULT NULL,
  `map_sheet_number` varchar(50) DEFAULT NULL,
  `detailed_address` varchar(255) DEFAULT NULL,
  `zone_id` varchar(50) DEFAULT NULL,
  `usage_purpose` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `land_plots`
--

INSERT INTO `land_plots` (`id`, `address`, `original_area`, `zone_name`, `owner_id`, `land_type_id`, `plot_number`, `map_sheet_number`, `detailed_address`, `zone_id`, `usage_purpose`, `created_at`) VALUES
(1, 'Nhà A', 100, 'Huyen A', 4, 'ODT', 'Thửa 1', 'Tờ 1', NULL, '', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `land_price_frames`
--

CREATE TABLE `land_price_frames` (
  `id` bigint(20) NOT NULL,
  `price_per_m2` double DEFAULT NULL,
  `zone_name` varchar(255) DEFAULT NULL,
  `land_type_id` varchar(50) DEFAULT NULL,
  `fiscal_year` int(11) DEFAULT NULL,
  `price_per_m2_duplicate` double DEFAULT NULL,
  `zone_id` varchar(50) DEFAULT NULL,
  `issue_date` datetime(6) DEFAULT NULL,
  `expiry_date` datetime(6) DEFAULT NULL,
  `decision_number` varchar(255) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `land_price_frames`
--

INSERT INTO `land_price_frames` (`id`, `price_per_m2`, `zone_name`, `land_type_id`, `fiscal_year`, `price_per_m2_duplicate`, `zone_id`, `issue_date`, `expiry_date`, `decision_number`, `status`) VALUES
(1, 20000000, 'Huyen A', 'ODT', 2025, 0, '', NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `land_types`
--

CREATE TABLE `land_types` (
  `id` varchar(50) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `tax_rate` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `land_types`
--

INSERT INTO `land_types` (`id`, `name`, `tax_rate`) VALUES
('CLN', 'Đất trồng cây lâu năm', 0.0001),
('LUA', 'Đất trồng lúa', 0),
('ODT', 'Đất ở đô thị', 0.0005),
('ONT', 'Đất ở nông thôn', 0.0003);

-- --------------------------------------------------------

--
-- Table structure for table `processing_logs`
--

CREATE TABLE `processing_logs` (
  `id` bigint(20) NOT NULL,
  `note` varchar(255) DEFAULT NULL,
  `officer_id` bigint(20) DEFAULT NULL,
  `declaration_id` bigint(20) DEFAULT NULL,
  `processed_at` datetime(6) DEFAULT NULL,
  `status_to` varchar(255) DEFAULT NULL,
  `status_from` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tax_declarations`
--

CREATE TABLE `tax_declarations` (
  `id` bigint(20) NOT NULL,
  `declared_area` double DEFAULT NULL,
  `note` varchar(1000) DEFAULT NULL,
  `declarant_id` bigint(20) DEFAULT NULL,
  `land_plot_id` bigint(20) DEFAULT NULL,
  `usage_purpose` varchar(255) DEFAULT NULL,
  `tax_year` int(11) DEFAULT NULL,
  `submission_date` datetime(6) DEFAULT NULL,
  `tax_amount` double DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `attachment_file` varchar(255) DEFAULT NULL,
  `processing_history` varchar(2000) DEFAULT NULL,
  `fraud_flag` bit(1) DEFAULT NULL,
  `transaction_attachment` varchar(255) DEFAULT NULL,
  `declared_usage_purpose` varchar(255) DEFAULT NULL,
  `approval_date` datetime(6) DEFAULT NULL,
  `amount_due` double DEFAULT NULL,
  `total_land_value` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `citizen_id` varchar(255) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT NULL,
  `id_card_image` varchar(255) DEFAULT NULL,
  `subject_type` int(11) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `nationality` varchar(255) DEFAULT NULL,
  `status` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `citizen_id`, `address`, `email`, `full_name`, `password`, `phone_number`, `role`, `is_active`, `id_card_image`, `subject_type`, `created_at`, `nationality`, `status`) VALUES
(1, '001090000001', 'Hà Nội', 'admin@tax.gov.vn', 'Quản Trị Viên', '123456', '0901000001', 'ADMIN', 1, NULL, NULL, NULL, NULL, 1),
(2, '001090000002', 'Hà Nội', 'canbo@tax.gov.vn', 'Nguyễn Văn Cán Bộ', '123456', '0902000002', 'CAN_BO', 1, NULL, NULL, NULL, NULL, 1),
(3, '001090000009', 'Sở Tài Nguyên', 'qldat@tax.gov.vn', 'Lê Quản Lý Đất', '123456', '0909999888', 'QL_DAT_DAI', 1, NULL, NULL, NULL, NULL, 1),
(4, '001090000003', 'Số 5 Nguyễn Trãi', 'dan@gmail.com', 'Trần Văn Dân', '123456', '0988111222', 'CHU_DAT', 1, NULL, NULL, NULL, NULL, 1),
(5, '030099000123', NULL, NULL, 'Nguyễn Văn Test', '123456', NULL, 'CHU_DAT', 1, NULL, NULL, NULL, NULL, 1),
(6, '099123456789', 'Hà Nội', 'canbo@test.com', 'Cán Bộ Test', '123456', '0988111222', 'CAN_BO', 1, NULL, NULL, NULL, NULL, 1);

-- --------------------------------------------------------

--
-- Table structure for table `zones`
--

CREATE TABLE `zones` (
  `id` varchar(50) NOT NULL,
  `coefficient_k` double DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `complaints`
--
ALTER TABLE `complaints`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_complaint_sender` (`sender_id`);

--
-- Indexes for table `land_plots`
--
ALTER TABLE `land_plots`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_plots_owner` (`owner_id`),
  ADD KEY `fk_plots_type` (`land_type_id`);

--
-- Indexes for table `land_price_frames`
--
ALTER TABLE `land_price_frames`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `land_types`
--
ALTER TABLE `land_types`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `processing_logs`
--
ALTER TABLE `processing_logs`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_log_officer` (`officer_id`);

--
-- Indexes for table `tax_declarations`
--
ALTER TABLE `tax_declarations`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_decl_user` (`declarant_id`),
  ADD KEY `fk_decl_plot` (`land_plot_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKga1g2gg00rdm6nm6c3rnv0syu` (`citizen_id`),
  ADD UNIQUE KEY `unique_cccd` (`citizen_id`),
  ADD UNIQUE KEY `citizen_id` (`citizen_id`);

--
-- Indexes for table `zones`
--
ALTER TABLE `zones`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `complaints`
--
ALTER TABLE `complaints`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `land_plots`
--
ALTER TABLE `land_plots`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `land_price_frames`
--
ALTER TABLE `land_price_frames`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `processing_logs`
--
ALTER TABLE `processing_logs`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tax_declarations`
--
ALTER TABLE `tax_declarations`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `complaints`
--
ALTER TABLE `complaints`
  ADD CONSTRAINT `fk_complaint_sender` FOREIGN KEY (`sender_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `land_plots`
--
ALTER TABLE `land_plots`
  ADD CONSTRAINT `fk_plots_owner` FOREIGN KEY (`owner_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `fk_plots_type` FOREIGN KEY (`land_type_id`) REFERENCES `land_types` (`id`);

--
-- Constraints for table `processing_logs`
--
ALTER TABLE `processing_logs`
  ADD CONSTRAINT `fk_log_officer` FOREIGN KEY (`officer_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `tax_declarations`
--
ALTER TABLE `tax_declarations`
  ADD CONSTRAINT `fk_decl_plot` FOREIGN KEY (`land_plot_id`) REFERENCES `land_plots` (`id`),
  ADD CONSTRAINT `fk_decl_user` FOREIGN KEY (`declarant_id`) REFERENCES `users` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
