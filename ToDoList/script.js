const API_URL = 'http://localhost:8080/api/tasks';

document.addEventListener('DOMContentLoaded', fetchTasks);

// 1. Tải danh sách công việc khi mở web 
function fetchTasks() {
    // Lấy giá trị đang nhập ở thanh tìm kiếm và bộ lọc
    const searchQuery = document.getElementById('searchInput').value.trim();
    const statusFilter = document.getElementById('statusFilter').value;

    let url = new URL(API_URL);
    
    if (searchQuery) {
        url.searchParams.append('search', searchQuery);
    }
    
    if (statusFilter === 'completed') {
        url.searchParams.append('completed', 'true');
    } else if (statusFilter === 'pending') {
        url.searchParams.append('completed', 'false');
    }

    // Gửi request xuống Backend
    fetch(url)
        .then(response => response.json())
        .then(data => renderTasks(data))
        .catch(error => console.error('Lỗi khi tải dữ liệu:', error));
}

// 2. Hàm hiển thị dữ liệu ra HTML 
function renderTasks(tasks) {
    const taskList = document.getElementById('taskList');
    taskList.innerHTML = ''; // Xóa danh sách cũ

    tasks.forEach(task => {
        const li = document.createElement('li');
        
        // Thêm class 'completed' nếu công việc đã xong
        const textClass = task.completed ? 'task-text completed' : 'task-text';

        li.innerHTML = `
            <span class="${textClass}" onclick="toggleTask(${task.id})">${task.title}</span>
            <div>
                <button class="edit-btn" onclick="editTask(${task.id}, '${task.title}')">Sửa</button>
                <button class="delete-btn" onclick="deleteTask(${task.id})">Xóa</button>
            </div>
        `;
        taskList.appendChild(li);
    });
}

// 3. Thêm công việc mới
function addTask() {
    const title = document.getElementById('taskInput').value.trim();
    if (!title) {
        alert("Vui lòng nhập công việc!");
        return;
    }

    fetch(API_URL, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ title: title, completed: false })
    })
    .then(async response => {
        // Nếu Backend trả về lỗi (Ví dụ: Mã 400 do trùng lặp)
        if (!response.ok) {
            const errorMessage = await response.text(); // Đọc câu lỗi từ Backend
            throw new Error(errorMessage); // Quăng lỗi để khối catch bên dưới bắt lấy
        }
        return response.json();
    })
    .then(data => {
        document.getElementById('taskInput').value = ''; // Xóa ô nhập
        fetchTasks(); // Tải lại danh sách
    })
    .catch(error => {
        // Bật popup thông báo lỗi cho người dùng
        alert(error.message); 
    });
}

// 4. Đổi trạng thái Hoàn thành/Chưa hoàn thành
function toggleTask(id) {
    fetch(`${API_URL}/${id}/toggle`, {
        method: 'PATCH'
    })
    .then(response => {
        if (response.ok) fetchTasks();
    });
}

// 5. Xóa công việc
function deleteTask(id) {
    if (confirm('Bạn có muốn xóa công việc này?')) {
        fetch(`${API_URL}/${id}`, {
            method: 'DELETE'
        })
        .then(response => {
            if (response.ok) fetchTasks();
        });
    }
}
// 6. Chỉnh sửa công việc
function editTask(id, currentTitle) {
    // Hiện hộp thoại 
    const newTitle = prompt("Nhập tiêu đề mới cho công việc:", currentTitle);
    
    // Nếu người dùng bấm Cancel hoặc xóa trắng tên rồi bấm OK thì thoát
    if (newTitle === null || newTitle.trim() === '') {
        return; 
    }

    // Gọi API cập nhật (PUT) xuống Backend
    fetch(`${API_URL}/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ title: newTitle.trim() })
    })
    .then(response => {
        if (response.ok) {
            fetchTasks(); // Cập nhật thành công thì tải lại danh sách
        } else {
            alert('Có lỗi xảy ra khi cập nhật công việc!');
        }
    })
    .catch(error => console.error('Lỗi khi sửa công việc:', error));
}