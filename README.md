# 📚 BookStore - Online Book Selling Website

BookStore là một ứng dụng web bán sách trực tuyến, xây dựng với **Spring Boot**, **Thymeleaf**, **Spring Security**, **JPA**, và **MySQL**.  
Người dùng có thể duyệt sách, quản lý giỏ hàng, đặt hàng, và thanh toán. Admin có thể quản lý sách, người dùng, và đơn hàng.

---

## 🚀 Công nghệ sử dụng

- **Java 17**
- **Javascript**
- **Spring Boot 3.5.4**
  - spring-boot-starter-web
  - spring-boot-starter-thymeleaf
  - spring-boot-starter-security
  - spring-boot-starter-data-jpa
  - spring-boot-starter-validation
  - spring-session-jdbc
- **Thymeleaf + thymeleaf-extras-springsecurity6**
- **Spring Security (Authentication & Authorization)**
- **Spring Data JPA + Hibernate**
- **MySQL Database**
- **Lombok**
- **Spring Boot DevTools** (hot reload)
- **JUnit + Spring Security Test** (test)

---

## 📂 Cấu trúc dự án

```bash
bookStore/
│── src/
│   ├── main/
│   │   ├── java/com/example/bookstore/
|   |   |   ├── config/
│   │   │   ├── controller/      
│   │   │   ├── domain/           
│   │   │   ├── repository/       
│   │   │   ├── service/         
│   │   │   └── BookStoreApplication.java
│   │   └── resources/
│   │       ├── static/          
│   │       ├── templates/       
│   │       ├── application.properties 
│   └── test/java/com/example/bookstore/   # Unit & Integration Tests
│
├── pom.xml
└── README.md
```
## 1️⃣ Clone repository
```
git clone https://github.com/username/bookStore.git](https://github.com/thangnguyen-max/bookstore-project.git
```
```
cd bookStore
```
## 2️⃣ Cấu hình Database
- tạo database với mysql
```
CREATE DATABASE bookstore;
```
- cấu hình properties
```
spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:3306/bookstore
spring.datasource.username=root
spring.datasource.password=your_password
```
## 3️⃣ Chạy dự án
```
./mvnw spring-boot:run
```
```
mvn spring-boot:run
```
- Ứng dụng chạy tại:
👉 http://localhost:8080
- link video demo:
👉 https://drive.google.com/file/d/1FnfCZ4AI_dji5X0p4sdctoGna4Tfp2H8/view?usp=drive_link

 ## 🧑‍💻 Chức năng chính 
 **👤 Người dùng:**

- Đăng ký, đăng nhập , đăng xuất(Spring Security)

- Xem danh sách sách

- Xem thông tin sách

- Thêm vào giỏ hàng / Xóa khỏi giỏ hàng

- Đặt hàng và thanh toán
  
- Xem lịch sử mua hàng

- Xem profile người dùng

 **🔑 Admin:**

- Quản lý sách (CRUD)

- Quản lý người dùng

- Quản lý đơn hàng

## 👨‍💻 Author: 
Thắng Nguyễn dev




