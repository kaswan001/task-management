# Task Management System

A robust Spring Boot application for managing tasks and subtasks with JWT authentication.

## Features

- **User Management**
  - Secure registration and login
  - JWT-based authentication
  - Role-based access control

- **Task Management**
  - Create, read, update, and delete tasks
  - Priority levels (HIGH, MEDIUM, LOW)
  - Status tracking (TODO, IN_PROGRESS, COMPLETED)
  - Due date management
  - Soft deletion support

- **Subtask Management**
  - Create and manage subtasks for better task organization
  - Independent status tracking
  - Soft deletion support

## Technology Stack

- Java 17
- Spring Boot 3.1.5
- Spring Security with JWT
- Spring Data JPA
- H2 Database (in-memory)
- Maven
- Lombok

## Prerequisites

- JDK 17 or later
- Maven 3.6.x or later
- Your favorite IDE (IntelliJ IDEA, Eclipse, VS Code)

## Getting Started

1. Clone the repository:
```bash
git clone https://github.com/kaswan001/task-management
```

2. Navigate to the project directory:
```bash
cd task-management
```

3. Build the project:
```bash
mvn clean install
```

4. Run the application:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Endpoints

### Authentication
- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - Login and receive JWT token

### Tasks
- `POST /api/tasks` - Create a new task
- `GET /api/tasks` - Get all tasks (with optional filtering)
- `PATCH /api/tasks/{taskId}` - Update task status or due date
- `DELETE /api/tasks/{taskId}` - Delete a task (soft delete)

### Subtasks
- `POST /api/subtasks` - Create a new subtask
- `GET /api/subtasks` - Get all subtasks (with optional filtering)
- `PATCH /api/subtasks/{subTaskId}` - Update subtask status
- `DELETE /api/subtasks/{subTaskId}` - Delete a subtask (soft delete)

## API Examples

### Register a New User
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "password": "password123",
    "email": "test@example.com"
  }'
```

### Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "password": "password123"
  }'
```

### Create a Task
```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Important Task",
    "description": "This needs to be done ASAP",
    "priority": "HIGH",
    "dueDate": "2024-12-31T23:59:59"
  }'
```

## Security Features

- Password encryption using BCrypt
- JWT token-based authentication
- Stateless session management
- Role-based access control
- Protection against unauthorized access

## Database Schema

The application uses an H2 in-memory database with the following main entities:
- User
- Task
- SubTask

## Error Handling

The application includes comprehensive error handling for:
- Invalid input validation
- Authentication failures
- Authorization failures
- Resource not found
- Duplicate resources
- General server errors

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For support, please open an issue in the GitHub repository.
