# Tài liệu dự án: QuanLyThueDat

Ngày tạo: 2025-12-10

## Tổng quan

Dự án `QuanLyThueDat` là một ứng dụng quản lý thuế đất bao gồm hai phần chính:
- Backend: ứng dụng Java Spring Boot (maven) cung cấp API REST, cấu trúc theo mô hình domain/usecase/presentation.
- Frontend: ứng dụng React (Vite) nằm trong `frontend-quanlythuedat-main` cung cấp giao diện người dùng.

Mục tiêu tài liệu này là tóm tắt cấu trúc mã nguồn, các thành phần chính, hướng dẫn build/run, và các file quan trọng để tham khảo nhanh.

## Cấu trúc repository (đã rút gọn)

- `pom.xml`, `mvnw`, `mvnw.cmd`: cấu hình Maven cho backend.
- `database/db_a46744.sql`: sao lưu/khởi tạo cơ sở dữ liệu.
- `src/main/java/com/thanglong/quanlythuedat`: mã nguồn backend (Spring Boot).
- `src/test/java/...`: unit/integration tests cho backend.
- `frontend-quanlythuedat-main`: mã nguồn frontend (Vite + React).

## Backend (Java / Spring Boot)

Đường dẫn chính: `src/main/java/com/thanglong/quanlythuedat`

- Entry point: `QuanlythuedatApplication.java` — cấu hình và chạy Spring Boot.
- Domain models (tập hợp lớp dữ liệu):
  - `domain/model/BangGiaDat.java`
  - `domain/model/HoSoKhaiThue.java`
  - `domain/model/NguoiDung.java`
  - `domain/model/ThuaDat.java`

- Repository interfaces: `domain/repository`
  - `IHoSoRepository.java`, `INguoiDungRepository.java`, `IThuaDatRepository.java`

- Infrastructure:
  - `infrastructure/config/BeanConfig.java` — cấu hình bean tùy chỉnh.
  - `infrastructure/config/OpenAPIConfig.java` — cấu hình OpenAPI/Swagger (nếu bật).
  - `infrastructure/config/SecurityConfig.java` — cấu hình bảo mật (JWT/CORS/... tùy code).
  - `infrastructure/repository/jpa/*` — các `Jpa*Repo` (Spring Data JPA repository interfaces).
  - `infrastructure/repository/entity/*` — các entity mapping (ví dụ `NguoiDungEntity.java`, `ThuaDatEntity.java`, ...).
  - `infrastructure/repository/impl/HoSoRepositoryImpl.java` — lớp implement repository tùy chỉnh.

- Presentation / Controllers (`presentation/controller`):
  - `AuthController.java` — endpoint đăng nhập/đăng ký (LoginDTO được sử dụng).
  - `AdminController.java` — các action dành cho admin.
  - `HoSoController.java` — quản lý hồ sơ (CRUD hồ sơ khai thuế).
  - `ThongKeController.java` — báo cáo/thống kê (dùng `BaoCaoThongKeDTO`).
  - `ThuaDatController.java` — thao tác với thửa đất.

- Use cases / Services (`usecase`):
  - `IAdminUseCase.java`, `IQuanLyHoSoUseCase.java` — interface use-case.
  - `impl/` — triển khai cụ thể các use case như `AdminUseCase`, `AuthUseCase`, `QuanLyHoSoUseCase`.
  - `dto/` — các lớp DTO trao đổi dữ liệu (ví dụ `HoSoInputDTO`, `HoSoOutputDTO`, `LoginDTO`, `BaoCaoThongKeDTO`).
  - `mapper/HoSoMapper.java` — ánh xạ giữa entity/domain/dto.

- File cấu hình: `src/main/resources/application.properties` — chứa thông tin cấu hình cơ sở dữ liệu, cổng, profile, JWT (nếu có), v.v.

### Các endpoint chính (tổng quan)
Do các controller có tên ý nghĩa, dự đoán các nhóm endpoint:
- `/api/auth/*` — đăng nhập, đăng ký, refresh token
- `/api/admin/*` — quản lý người dùng, cấu hình hệ thống
- `/api/hoso/*` — CRUD hồ sơ khai thuế
- `/api/thuadat/*` — CRUD thửa đất
- `/api/thongke/*` — lấy báo cáo/thống kê

Để có danh sách endpoint chính xác, mở nội dung các `Controller` tương ứng.

## Frontend (React + Vite)

Đường dẫn: `frontend-quanlythuedat-main`

- Công nghệ: React, Vite, axios (có `api/axiosClient.js`).
- Các component chính: `Login.jsx`, `Register.jsx`, `AdminDashboard.jsx`, `TaxOfficerDashboard.jsx`, `UserDashboard.jsx`.
- Tập tin cấu hình: `vite.config.js`, `package.json`, `eslint.config.js`.
- Public assets: `public/`.

## Cơ sở dữ liệu

- File SQL: `database/db_a46744.sql` — dùng để khôi phục/khởi tạo schema và dữ liệu mẫu.
- Kiểu DB: chưa thấy rõ trong tài liệu, nhưng `application.properties` sẽ chứa `spring.datasource.*` (MySQL/Postgres/H2...). Kiểm tra `src/main/resources/application.properties` để biết kết nối chính xác.

## Build & Run

Backend (Windows PowerShell):

```powershell
cd d:\quanlythuedat
.\mvnw.cmd clean package -DskipTests
.\mvnw.cmd spring-boot:run
# hoặc chạy jar sau khi package
java -jar target\*.jar
```

Frontend (dev):

```powershell
cd d:\quanlythuedat\frontend-quanlythuedat-main
npm install
npm run dev
# hoặc build cho production
npm run build
```

Ghi chú: Backend và frontend có thể chạy đồng thời; frontend gọi API backend theo `axiosClient` (kiểm tra `src/api/axiosClient.js` để biết baseURL hiện tại).

## Tests

- Có thư mục `src/test/java/.../QuanlythuedatApplicationTests.java`. Chạy test bằng:

```powershell
.\mvnw.cmd test
```

## Các file quan trọng để đọc đầu tiên
- `src/main/java/com/thanglong/quanlythuedat/QuanlythuedatApplication.java` — điểm khởi chạy.
- `src/main/resources/application.properties` — cấu hình hệ thống.
- `infrastructure/config/SecurityConfig.java` — cách bảo vệ API.
- `presentation/controller/AuthController.java` — luồng xác thực.
- `usecase/impl/AuthUseCase.java` — logic đăng nhập/đăng ký.
- `frontend-quanlythuedat-main/src/api/axiosClient.js` — cấu hình client phía front.

## Gợi ý mở rộng tài liệu (nếu muốn)
- Sinh OpenAPI/Swagger từ `OpenAPIConfig` (nếu đã cấu hình) và xuất ra `OPENAPI.yaml`.
- Liệt kê từng endpoint (method, path, request/response DTO) bằng cách đọc các `Controller` và DTO.
- Thêm sơ đồ ER từ `database/db_a46744.sql`.
- Hướng dẫn cài đặt môi trường dev (DB, biến môi trường JWT, SMTP, ...).

## Kết luận

File này tóm tắt cấu trúc và những nơi cần đọc tiếp để hiểu sâu hơn dự án. Tệp tài liệu được tạo tại: `PROJECT_DOCUMENTATION.md` (gốc repo).

Nếu bạn muốn, tôi có thể:
- trích xuất chi tiết endpoint từ các `Controller` và tạo phần API reference trong `PROJECT_DOCUMENTATION.md`;
- hoặc sinh `OPENAPI.yaml`/Swagger UI nếu cấu hình đã bật.

