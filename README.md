# HỆ THỐNG QUẢN LÝ THUẾ ĐẤT ĐAI (BACKEND)

Dự án Backend sử dụng Java Spring Boot phục vụ nghiệp vụ nộp thuế và chống gian lận đất đai.

## 1. Yêu cầu hệ thống
- Java JDK 17 hoặc 21.
- MySQL Server (XAMPP).
- Maven.

## 2. Hướng dẫn cài đặt
1. **Database:**
   - Mở file `database/db_a46744.sql`.
   - Import vào phpMyAdmin hoặc chạy lệnh SQL để tạo cấu trúc và dữ liệu mẫu.
   
2. **Cấu hình:**
   - Mở `src/main/resources/application.properties`.
   - Chỉnh sửa username/password MySQL nếu cần (Mặc định: root / rỗng).

3. **Chạy ứng dụng:**
   - Chạy file `QuanLyThueDatApplication.java`.
   - Server sẽ start tại: `http://localhost:9090`.

## 3. Tài liệu API
- Frontend 
- Link API cơ bản:
  - Đăng nhập: POST `/api/auth/login`
  - Nộp hồ sơ: POST `/api/hoso/nop-to-khai`
  - Thống kê: GET `/api/thongke/baocao`

## 4. Tác giả
- Nguyễn Hữu Tiến - A46744