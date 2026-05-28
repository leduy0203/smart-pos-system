# Smart POS Management System - Backend API

Hệ thống quản lý bán hàng (POS) toàn diện được xây dựng bằng Spring Boot. Thiết kế để quản lý các hoạt động nhà hàng/bán lẻ với đồng bộ hóa đơn hàng thực tế, xử lý thanh toán và quản lý hàng tồn kho.

**Trạng thái**: 🚀 Đang Phát Triển (Tháng 4 – Hiện tại)

---

## 📋 Mục Lục

- [Tổng Quan](#tổng-quan)
- [Công Nghệ Sử Dụng](#công-nghệ-sử-dụng)
- [Tính Năng](#tính-năng)
- [Yêu Cầu Hệ Thống](#yêu-cầu-hệ-thống)
- [Cài Đặt & Chạy](#cài-đặt--chạy)
- [Cấu Trúc Dự Án](#cấu-trúc-dự-án)
- [Tài Liệu API](#tài-liệu-api)
- [Cấu Hình Docker](#cấu-hình-docker)
- [Cơ Sở Dữ Liệu](#cơ-sở-dữ-liệu)
- [Đóng Góp](#đóng-góp)
- [Giấy Phép](#giấy-phép)

---

## 🎯 Tổng Quan

Smart POS là một hệ thống quản lý bán hàng toàn diện được thiết kế để tối ưu hóa hoạt động của nhà hàng và bán lẻ. Backend cung cấp các API RESTful để quản lý đơn hàng, theo dõi hàng tồn kho, xác thực người dùng, thông báo thực tế và xử lý thanh toán.

### Khả Năng Chính
- **Quản Lý Đơn Hàng Thực Tế**: Đồng bộ hóa đơn hàng trực tiếp trên nhiều thiết bị
- **Xác Thực Bảo Mật**: JWT với kiểm soát truy cập dựa trên vai trò (RBAC)
- **Tích Hợp Thanh Toán**: Cổng VNPay cho thanh toán Việt Nam
- **Quản Lý Tài Liệu Đa Phương Tiện**: Tích hợp Cloudinary cho ảnh sản phẩm và hóa đơn
- **Tối Ưu Hóa Hiệu Suất**: Bộ đệm Redis cho dữ liệu được truy cập thường xuyên
- **Khả Năng Mở Rộng**: Containerization Docker dễ triển khai

---

## 🛠️ Công Nghệ Sử Dụng

### Backend
- **Framework**: Java 21 với Spring Boot 3.4.11
- **Cơ Sở Dữ Liệu**: PostgreSQL (Lưu trữ dữ liệu chính)
- **Bộ Đệm**: Redis (JWT blacklist, bộ đệm sản phẩm)
- **Thời Gian Thực**: Socket.IO (Cập nhật đơn hàng & thông báo trực tiếp)
- **Bảo Mật**: Spring Security, JWT (JSON Web Tokens)
- **Tài Liệu API**: OpenAPI 3.0 (Swagger)
- **ORM**: JPA/Hibernate
- **Công Cụ Build**: Maven

### Dịch Vụ Bên Ngoài
- **Cổng Thanh Toán**: VNPay
- **Lưu Trữ Tài Liệu Đa Phương Tiện**: Cloudinary
- **Containerization**: Docker & Docker Compose

### Frontend
- **Framework**: Next.js (React)
- **Repository**: [smart-pos-frontend](đường-dẫn-frontend)

---

## ✨ Tính Năng

### 🔐 Xác Thực & Phân Quyền
- ✅ Xác thực dựa trên JWT
- ✅ Kiểm Soát Truy Cập Dựa Trên Vai Trò (RBAC)
- ✅ Các vai trò: Quản Trị Viên, Quản Lý, Nhân Viên, Phục Vụ
- ✅ Quản lý quyền trên từng vai trò
- ✅ JWT blacklist sử dụng Redis cho đăng xuất an toàn

### 📦 Quản Lý Sản Phẩm
- ✅ Danh mục sản phẩm có phân loại
- ✅ Tải ảnh lên qua Cloudinary
- ✅ Theo dõi giá vốn & giá bán
- ✅ Tính toán tỷ lệ chi phí thực phẩm
- ✅ Quản lý nhà cung cấp

### 🍽️ Quản Lý Bàn
- ✅ Theo dõi trạng thái bàn (CÓ SẴN, ĐANG SỬ DỤNG, ĐẬU CHỖ)
- ✅ Xác thực chuyển đổi trạng thái
- ✅ Đồng bộ hóa bàn thực tế

### 📋 Quản Lý Đơn Hàng
- ✅ Tạo & cập nhật đơn hàng
- ✅ Quản lý mục đơn hàng với theo dõi trạng thái
- ✅ Áp dụng chiết khấu (cấp độ đơn hàng)
- ✅ Luồng trạng thái đơn hàng
- ✅ Thông báo đơn hàng thực tế qua Socket.IO

### 💳 Xử Lý Thanh Toán
- ✅ Tích hợp cổng VNPay
- ✅ Hỗ trợ nhiều phương thức thanh toán
- ✅ Xử lý chiết khấu/khuyến mãi đơn hàng
- ✅ Theo dõi trạng thái thanh toán

### 📊 Tối Ưu Hóa Dữ Liệu
- ✅ Phân trang & lọc trên các endpoint danh sách
- ✅ Bộ đệm Redis cho dữ liệu sản phẩm
- ✅ Giảm truy vấn cơ sở dữ liệu với chiến lược bộ đệm
- ✅ Đồng bộ hóa đơn hàng hiệu quả

### 🔔 Cập Nhật Thực Tế
- ✅ Tích hợp Socket.IO cho cập nhật trực tiếp
- ✅ Thông báo trạng thái đơn hàng thực tế
- ✅ Đồng bộ hóa trạng thái bàn trực tiếp
- ✅ Xác nhận thanh toán tức thì

---

## 📋 Yêu Cầu Hệ Thống

- **Java**: JDK 21 hoặc cao hơn
- **Maven**: 3.8.1+
- **PostgreSQL**: 13 hoặc cao hơn
- **Redis**: 6.0 hoặc cao hơn
- **Docker**: 20.10+ (cho cấu hình container)
- **Docker Compose**: 2.0+ (cho phát triển cục bộ)

### API Keys Bắt Buộc
- **Cloudinary**: Tài khoản để lưu trữ tài liệu đa phương tiện
- **VNPay**: Tài khoản thương nhân để xử lý thanh toán

---

## 🚀 Cài Đặt & Chạy

### 1. Clone Repository
```bash
git clone <repository-url>
cd smart-pos-api
```

### 2. Cấu Hình Biến Môi Trường

Tạo file `.env` ở gốc dự án:
```env
# Cơ Sở Dữ Liệu
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/smart_pos
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=mật_khẩu_của_bạn

# Redis
SPRING_REDIS_HOST=localhost
SPRING_REDIS_PORT=6379

# Cloudinary
CLOUDINARY_CLOUD_NAME=tên_cloud_của_bạn
CLOUDINARY_API_KEY=api_key_của_bạn
CLOUDINARY_API_SECRET=api_secret_của_bạn

# VNPay
VNPAY_TMN_CODE=mã_tmn_của_bạn
VNPAY_HASH_SECRET=secret_của_bạn

# JWT
JWT_SECRET=khóa_bí_mật_jwt_của_bạn_ở_đây
JWT_EXPIRATION=3600000
```

### 3. Build Dự Án
```bash
mvn clean install
```

### 4. Chạy Ứng Dụng
```bash
mvn spring-boot:run
```

API sẽ khả dụng tại: `http://localhost:8080`

### 5. Truy Cập Tài Liệu API
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:8080/v3/api-docs`

---

## 📁 Cấu Trúc Dự Án

```
smart-pos-api/
├── src/
│   ├── main/
│   │   ├── java/com/smartpos/api/
│   │   │   ├── PosBackendApiApplication.java      # Điểm vào chính
│   │   │   ├── common/                             # Enums & constants
│   │   │   │   ├── OrderItemStatus.java
│   │   │   │   ├── OrderStatus.java
│   │   │   │   ├── PaymentMethod.java
│   │   │   │   ├── TableStatus.java
│   │   │   │   └── UserRole.java
│   │   │   ├── configuration/                      # Cấu hình Spring
│   │   │   │   ├── AppConfig.java
│   │   │   │   ├── CloudinaryConfig.java
│   │   │   │   ├── DataInitializer.java           # Hạt giống dữ liệu ban đầu
│   │   │   │   └── OpenAPIConfig.java
│   │   │   ├── controller/                         # REST endpoints
│   │   │   │   ├── UserController.java
│   │   │   │   ├── ProductController.java
│   │   │   │   ├── OrderController.java
│   │   │   │   ├── TableController.java
│   │   │   │   ├── CategoryController.java
│   │   │   │   ├── SupplierController.java
│   │   │   │   └── RoleController.java
│   │   │   ├── service/                            # Logic kinh doanh
│   │   │   │   ├── UserService.java
│   │   │   │   ├── ProductService.java
│   │   │   │   ├── OrderService.java
│   │   │   │   ├── CloudinaryService.java
│   │   │   │   └── impl/
│   │   │   │       ├── UserServiceImpl.java
│   │   │   │       ├── ProductServiceImpl.java
│   │   │   │       ├── OrderServiceImpl.java
│   │   │   │       └── CloudinaryServiceImpl.java
│   │   │   ├── repository/                         # Lớp truy cập dữ liệu
│   │   │   │   ├── UserRepository.java
│   │   │   │   ├── ProductRepository.java
│   │   │   │   ├── OrderRepository.java
│   │   │   │   └── CategoryRepository.java
│   │   │   ├── model/                              # JPA entities & DTOs
│   │   │   │   ├── entity/
│   │   │   │   │   ├── User.java
│   │   │   │   │   ├── Product.java
│   │   │   │   │   ├── Order.java
│   │   │   │   │   ├── Category.java
│   │   │   │   │   └── Role.java
│   │   │   │   ├── request/                        # Request DTOs
│   │   │   │   │   ├── CreateUserRequest.java
│   │   │   │   │   ├── UpdateUserRequest.java
│   │   │   │   │   ├── ChangePasswordRequest.java
│   │   │   │   │   ├── CreateProductRequest.java
│   │   │   │   │   └── CreateOrderRequest.java
│   │   │   │   └── response/                       # Response DTOs
│   │   │   │       ├── UserResponse.java
│   │   │   │       ├── ProductResponse.java
│   │   │   │       └── OrderResponse.java
│   │   │   ├── exception/                          # Xử lý ngoại lệ
│   │   │   │   ├── AppException.java
│   │   │   │   ├── ErrorCode.java
│   │   │   │   └── GlobalExceptionHandler.java
│   │   │   └── security/                           # Cấu hình bảo mật
│   │   │       └── JwtUtil.java
│   │   └── resources/
│   │       ├── application.yaml                    # Cấu hình chính
│   │       ├── application-dev.yaml                # Dev profile
│   │       └── db/migration/                       # V1__initial_schema.sql
│   └── test/
│       └── java/com/smartpos/api/
│           └── PosBackendApiApplicationTests.java
├── pom.xml                                         # Maven dependencies
├── docker-compose.yml                              # Điều phối container
├── Dockerfile                                      # Spring Boot image
└── README.md                                       # File này
```

---

## 📚 Tài Liệu API

### URL Cơ Bản
```
http://localhost:8080/api/v1
```

### Xác Thực
Tất cả các endpoint được bảo vệ yêu cầu token JWT trong header Authorization:
```
Authorization: Bearer <token_jwt_của_bạn>
```

### Core Endpoints

#### Người Dùng
```
POST   /users                      # Tạo người dùng
GET    /users/{id}                 # Lấy người dùng theo ID
PUT    /users/{id}                 # Cập nhật người dùng
DELETE /users/{id}                 # Xóa người dùng
POST   /users/{id}/change-password # Đổi mật khẩu
```

#### Sản Phẩm
```
POST   /products                   # Tạo sản phẩm (với tải ảnh)
GET    /products/{id}              # Lấy sản phẩm theo ID
PUT    /products/{id}              # Cập nhật sản phẩm
DELETE /products/{id}              # Xóa sản phẩm
GET    /products                   # Danh sách sản phẩm (phân trang)
```

#### Đơn Hàng
```
POST   /orders                     # Tạo đơn hàng
GET    /orders/{id}                # Lấy đơn hàng theo ID
PUT    /orders/{id}                # Cập nhật đơn hàng
GET    /orders                     # Danh sách đơn hàng (có lọc)
PATCH  /orders/{id}/status         # Cập nhật trạng thái đơn hàng
```

#### Bàn
```
GET    /tables/{id}                # Lấy bàn theo ID
PATCH  /tables/{id}/status         # Cập nhật trạng thái bàn
GET    /tables                     # Danh sách tất cả bàn
```

#### Danh Mục
```
POST   /categories                 # Tạo danh mục
GET    /categories                 # Danh sách danh mục
PUT    /categories/{id}            # Cập nhật danh mục
DELETE /categories/{id}            # Xóa danh mục
```

### Tham Số Truy Vấn
Hầu hết các endpoint danh sách hỗ trợ:
- `page`: Số trang (bắt đầu từ 0)
- `size`: Số mục trên mỗi trang
- `sort`: Sắp xếp theo trường (ví dụ: `sort=id,desc`)
- `search`: Lọc theo tên hoặc từ khóa

### Định Dạng Phản Hồi
```json
{
  "code": 200,
  "message": "Thông báo thành công",
  "data": {
    "id": 1,
    "name": "Ví dụ"
  },
  "pagination": {
    "page": 0,
    "size": 10,
    "totalElements": 100,
    "totalPages": 10
  }
}
```

Để xem chi tiết đầy đủ, truy cập Swagger UI tại `/swagger-ui.html`

---

## 🐳 Cấu Hình Docker

### Lựa Chọn 1: Sử Dụng Docker Compose (Được Khuyến Nghị)

```bash
# Khởi động tất cả dịch vụ (Spring Boot, PostgreSQL, Redis)
docker-compose up -d

# Xem logs
docker-compose logs -f api

# Dừng dịch vụ
docker-compose down
```

### Lựa Chọn 2: Lệnh Docker Thủ Công

#### Build Spring Boot Image
```bash
docker build -t smart-pos-api:latest .
```

#### Chạy PostgreSQL
```bash
docker run -d \
  --name postgres \
  -e POSTGRES_DB=smart_pos \
  -e POSTGRES_PASSWORD=password \
  -p 5432:5432 \
  postgres:15-alpine
```

#### Chạy Redis
```bash
docker run -d \
  --name redis \
  -p 6379:6379 \
  redis:7-alpine
```

#### Chạy Spring Boot
```bash
docker run -d \
  --name smart-pos-api \
  -p 8080:8080 \
  --env-file .env \
  smart-pos-api:latest
```

### Cấu Trúc Docker Compose
```yaml
version: '3.8'
services:
  api:
    build: .
    ports:
      - "8080:8080"
    environment:
      - SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/smart_pos
      - SPRING_REDIS_HOST=redis
    depends_on:
      - postgres
      - redis
  postgres:
    image: postgres:15-alpine
    ports:
      - "5432:5432"
    environment:
      - POSTGRES_DB=smart_pos
      - POSTGRES_PASSWORD=postgres
    volumes:
      - postgres_data:/var/lib/postgresql/data
  redis:
    image: redis:7-alpine
    ports:
      - "6379:6379"
    volumes:
      - redis_data:/data
volumes:
  postgres_data:
  redis_data:
```

---

## 🗄️ Cơ Sở Dữ Liệu

### Các Thực Thể Chính

#### Người Dùng
```sql
CREATE TABLE users (
  id BIGSERIAL PRIMARY KEY,
  user_name VARCHAR(50) UNIQUE NOT NULL,
  email VARCHAR(100) UNIQUE NOT NULL,
  phone_number VARCHAR(20) UNIQUE,
  full_name VARCHAR(100) NOT NULL,
  password VARCHAR(255) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

#### Sản Phẩm
```sql
CREATE TABLE products (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(150) NOT NULL,
  description TEXT,
  cost_price DECIMAL(15,2),
  selling_price DECIMAL(15,2),
  image_url VARCHAR(500),
  category_id BIGINT REFERENCES categories(id),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

#### Đơn Hàng
```sql
CREATE TABLE orders (
  id BIGSERIAL PRIMARY KEY,
  order_code VARCHAR(50) UNIQUE NOT NULL,
  table_id BIGINT REFERENCES restaurant_tables(id),
  total_amount DECIMAL(15,2),
  status VARCHAR(50),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

#### Bàn
```sql
CREATE TABLE restaurant_tables (
  id BIGSERIAL PRIMARY KEY,
  table_number INT UNIQUE NOT NULL,
  capacity INT,
  status VARCHAR(50),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

---

## 🤝 Đóng Góp

### Cấu Hình Phát Triển
1. Fork repository
2. Tạo nhánh tính năng: `git checkout -b feature/tên-tính-năng`
3. Commit thay đổi: `git commit -m 'Thêm tính năng'`
4. Push sang nhánh: `git push origin feature/tên-tính-năng`
5. Tạo Pull Request

### Phong Cách Mã
- Tuân theo [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)
- Sử dụng tên biến có ý nghĩa
- Thêm JavaDoc cho các phương thức công khai
- Viết unit test cho các tính năng mới

### Kiểm Tra
```bash
# Chạy tất cả các bài kiểm tra
mvn test

# Chạy bài kiểm tra cụ thể
mvn test -Dtest=UserServiceTest

# Bỏ qua bài kiểm tra
mvn install -DskipTests
```

---

## 📝 Giấy Phép

Dự án này là một dự án cho portfolio và dành cho mục đích giáo dục.

---

## 📞 Hỗ Trợ

Nếu có bất kỳ câu hỏi hoặc vấn đề nào, vui lòng tạo issue trong repository.

---

## 🗺️ Lộ Trình (Tính Năng Sắp Tới)

- [ ] Bảng điều khiển phân tích đơn hàng
- [ ] Mô-đun quản lý hàng tồn kho
- [ ] Hệ thống báo cáo nâng cao
- [ ] Hỗ trợ nhiều địa điểm
- [ ] Công cụ chiết khấu & khuyến mãi nâng cao
- [ ] Quản lý ca làm việc của nhân viên
- [ ] Hệ thống hiển thị bếp (KDS)
- [ ] Ứng dụng di động (React Native)

---

**Cập nhật Lần Cuối**: 28 Tháng 5, 2026
**Phiên Bản**: 0.1.0 (Đang Phát Triển)
