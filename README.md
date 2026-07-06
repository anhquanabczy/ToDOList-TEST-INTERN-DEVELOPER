# ToDo List Application 

Một ứng dụng quản lý công việc (To-Do List) đơn giản.
Tài liệu này sẽ mô tả chi tiết về cấu trúc và cách hoạt động của phần **Frontend** và **Backend**.

## Công nghệ sử dụng:
### Front-End ( Thư mục ToDoList)
* **HTML5:** Xây dựng cấu trúc trang web (Thanh tìm kiếm, Form nhập liệu, Danh sách công việc).
* **CSS3:** Thiết kế giao diện (UI) .
* **JavaScript:** Xử lý logic nghiệp vụ, thao tác DOM và giao tiếp với Backend.
* **Fetch API:** Xử lý các luồng gọi API (GET, POST, PUT, DELETE) bất đồng bộ (Asynchronous) đến máy chủ Spring Boot.
### Back-End:
* **Ngôn ngữ:** Java 17
* **Framework:** Spring Boot 3.3.0
* **Quản lý thư viện & Build tool:** Gradle 8.7
* **ORM:** Spring Data JPA (Hibernate)
* **Khác:** Spring Web (RESTful API)
## 1. Frontend 

Phần Frontend được xây dựng bằng HTML.

### Các tính năng chính (Features):
1. **Quản lý công việc (CRUD):**
   * **Create:** Thêm công việc mới vào danh sách.
   * **Read:** Hiển thị danh sách công việc theo thời gian thực.
   * **Update:** Chỉnh sửa tên công việc hoặc đánh dấu hoàn thành/chưa hoàn thành.
   * **Delete:** Xóa công việc khỏi danh sách.
2. **Tìm kiếm & Lọc:**
   * Ô tìm kiếm động: Lọc kết quả ngay khi người dùng gõ phím.
   * Bộ lọc trạng thái: Lọc công việc theo 3 mốc (Tất cả, Đã hoàn thành, Chưa hoàn thành).
3. **Kiểm tra dữ liệu :**
   * Ngăn chặn người dùng nhập tên công việc trống.
   * Bắt lỗi và hiển thị Popup (Alert) cảnh báo khi nhập trùng tên công việc đã có trong danh sách (kết hợp với logic từ Backend).
## 2. Backend (Thư Mục ToDOListCRUD)

Backend được xây dựng dựa trên framework **Spring Boot**, tuân thủ nghiêm ngặt **Kiến trúc 3 lớp (3-Tier Architecture)**: Controller -> Service  -> Repository.


### Các tính năng chính (Features):
1. **RESTful API Design:**
   * Cung cấp các endpoints chuẩn mực để Frontend giao tiếp (GET, POST, PUT, DELETE).
   * Hỗ trợ Query để thực hiện Tìm kiếm và Lọc ngay từ câu lệnh truy vấn.
2. **Xử lý Logic Nghiệp vụ:**
   * **Data Validation:** Ngăn chặn việc thêm công việc trùng lặp (không phân biệt chữ hoa/chữ thường) ở tầng Service.
   * **Error Handling:** Trả về mã lỗi HTTP chuẩn xác (ví dụ: `400 Bad Request`) kèm thông báo lỗi rõ ràng cho Frontend nếu dữ liệu không hợp lệ.
3. **Database Seeding (Khởi tạo dữ liệu mẫu):**
   * Sử dụng `CommandLineRunner` để tự động kiểm tra và chèn các dữ liệu mẫu (Ăn, uống, ngủ, nghỉ...) vào database ở lần chạy đầu tiên, giúp tiết kiệm thời gian test.

### Luồng xử lý 

Hệ thống phân tách trách nhiệm rõ ràng (Separation of Concerns). Dưới đây là luồng đi của dữ liệu và cách hệ thống xử lý các phản hồi HTTP:

#### 1. `controller/` (Phản hồi HTTP)
* **Chức năng:** Nhận request từ FE sau đó gọi tầng Service để xử lý và đóng gói kết quả. 

#### 2. `service/` (Xử lý Nghiệp vụ & Chặn lỗi)
* **Chức năng:** Chuẩn hóa dữ liệu và kiểm tra các quy tắc trước ghi gọi repo.
* Ví dụ: Nếu phát hiện công việc bị trùng lặp -> lỗi `IllegalArgumentException`.
* Nếu người dùng yêu cầu sửa/xóa một ID không có thực -> lỗi `EntityNotFoundException`.

#### 3. `exception/` (Bộ lọc lỗi toàn cục - Global Exception Handler)
* **Chức năng:** Dịch lỗi thành các mã HTTP chuẩn xác để Frontend dễ dàng xử lý.
* **Các mã lỗi (HTTP Status) trả về:**
  * **`400 Bad Request`**: Dùng cho lỗi dữ liệu đầu vào. (Ví dụ: lỗi trùng lặp tên công việc, tên công việc để trống).
  * **`404 Not Found`**: Frontend gửi Request sửa/xóa một ID công việc không tồn tại trong Database.
  * **`500 Internal Server Error`**: Bắt các lỗi hệ thống .

#### 4. Các tầng hỗ trợ (Model, Repository, Config)
* **`model/` (Entity):** Định nghĩa cấu trúc bảng trong Database (các cột `id`, `title`, `completed`).
* **`repository/`:** Giao tiếp trực tiếp với cơ sở dữ liệu. Định nghĩa sẵn các hàm truy vấn.

## 3. Hướng dẫn cài đặt và khởi chạy 
### Yêu cầu hệ thống 
Java SDK:17 
IDE: IntelliJ IDEA, Eclipse hoặc VS Code.
Build Tool: Gradle 8.7.

### Chạy dự án
#### Bước 1: Tải dự án và Mở bằng IDE

Clone repository này về máy của bạn.
Mở IntelliJ IDEA, chọn Open và trỏ vào thư mục BE của dự án (ToDOListCRUD) 
Mở Visual code và tải về các extendsion cần thiết như html hay js
#### Bước 2: Chạy
Ở đường dẫn ToDoListCRUD/src/main/java/com/example/todolistcrud/ToDoListCrudApplication.java trong Intellij nhấn **Run**
Ở Visual code nhấn go live ở góc dưới bên phải màn hình

<img width="323" height="185" alt="image" src="https://github.com/user-attachments/assets/a1d20335-50d8-40d7-b121-3b49626e6be0" />

khi kết nối thành công thì màn hình sẽ hiện ra. 
<img width="821" height="810" alt="image" src="https://github.com/user-attachments/assets/17208ddf-27cb-40f2-8d52-dc906f45e864" />
## Cách hoạt động 
* Thêm công việc mới vào ô thêm công việc và nhấn nút thêm
* Tìm kiếm công việc ở ô tìm kiếm
* Lọc theo trạng thái ở ô tất cả trạng thái và chọn hoàn thành/ chưa hoàn thành
* Sửa và xóa ỏ uô 1 bên công việc
* khi đã hoàn thành thì nhấn vào chính giữa công việc và công việc sẽ bị làm mờ và gạch ngang

## 4. Kiểm thử 


### Công nghệ sử dụng:
* **JUnit 5:** Framework tiêu chuẩn để viết và định hình các kịch bản kiểm thử trong Java.
* **Mockito:** Thư viện hỗ trợ tạo các "đối tượng giả" (Mock Object), giúp cô lập các tầng mã nguồn khi test.
* **MockMvc:** Công cụ của Spring Test giúp giả lập các HTTP Request (GET, POST...) để kiểm thử tầng Controller mà không cần phải chạy toàn bộ Server.

### Phạm vi kiểm thử:

**1. Kiểm thử tầng Logic nghiệp vụ (Service Layer):**
* Sử dụng Mockito để làm giả `TaskRepository`, giúp cô lập `TaskService` khỏi tầng Database.
* **Happy Path:** Xác nhận hàm thêm công việc hoạt động thành công, tự động chuẩn hóa dữ liệu và gọi đúng lệnh lưu trữ.
* **Exception Path:** Đảm bảo hệ thống ném ra đúng lỗi `IllegalArgumentException` khi phát hiện dữ liệu trùng lặp (không phân biệt hoa/thường), và chặn lệnh `save()` vào Database.

**2. Kiểm thử tầng Giao tiếp (API / Controller Layer):**
* Sử dụng `@WebMvcTest` kết hợp `@MockBean` để chỉ nạp các thành phần liên quan đến Web và làm giả kết quả từ `TaskService`.
* **Giả lập Request:** Bắn các luồng gọi API ảo vào endpoint `/api/tasks`.
* **Xác minh Response:** Đảm bảo Controller phản hồi đúng định dạng (`application/json`), đúng mã trạng thái HTTP (ví dụ: `200 OK`) và cấu trúc chuỗi JSON trả về hoàn toàn khớp với kỳ vọng (sử dụng `jsonPath`).




