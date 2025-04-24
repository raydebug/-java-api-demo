# Java API Demo Project

A Spring Boot REST API with JWT authentication, file operations, and data processing capabilities.

## Features

### Authentication & Security
- JWT-based authentication
- Role-based access control (USER, ADMIN roles)
- Secure password handling with BCrypt
- Stateless session management
- Protected API endpoints
- Custom security filters and providers

### User Management
- User registration and login
- Profile management
- Role-based authorization
- Email-based authentication
- Custom user details service

### File Operations
- File upload and download
- Multi-file handling
- Secure file storage
- File metadata tracking
- File type validation

### Data Processing
- Excel file import/export
- CSV file import/export
- Batch processing support
- Data validation
- Error handling and reporting

### Monitoring & Logging
- Request/Response logging
- Performance monitoring
- Error tracking
- Audit logging
- Prometheus metrics

## Technical Stack

- Java 17
- Spring Boot 3.2.3
- Spring Security
- Spring Data JPA
- PostgreSQL
- JWT Authentication
- Apache POI (Excel processing)
- OpenCSV (CSV processing)
- Lombok
- Jakarta Validation
- REST Assured (Testing)
- TestContainers (Testing)
- JUnit 5 (Testing)

## Project Structure

```
src/
├── main/
│   ├── java/com/demo/
│   │   ├── config/         # Configuration classes
│   │   ├── controller/     # REST controllers
│   │   ├── dto/           # Data Transfer Objects
│   │   ├── exception/     # Exception handling
│   │   ├── model/         # Entity models
│   │   ├── monitoring/    # Monitoring and logging
│   │   ├── repository/    # JPA repositories
│   │   ├── security/      # Security configuration
│   │   └── service/       # Business logic
│   └── resources/         # Application properties
└── test/
    ├── java/com/demo/
    │   ├── config/        # Test configuration
    │   └── test/          # Test classes
    └── resources/         # Test resources
```

## Project Status

✅ Completed:
- Project structure and dependencies
- User management (CRUD operations)
- Authentication and authorization
- File operations (upload/download)
- Data processing (CSV/Excel)
- Basic monitoring setup
- API testing framework
- Database integration
- Security implementation
- Exception handling

🚧 In Progress:
- Search and filtering functionality
- Advanced monitoring features
- Performance optimization
- Documentation improvements

⏳ Planned:
- Batch processing enhancements
- Email notification system
- Report generation
- API versioning
- Cache implementation

## API Endpoints

### Authentication
- POST `/api/v1/auth/login` - User login
- POST `/api/v1/auth/refresh` - Refresh token

### User Management
- POST `/api/v1/users` - Create user
- GET `/api/v1/users` - List users (with pagination)
- GET `/api/v1/users/{id}` - Get user details
- PUT `/api/v1/users/{id}` - Update user
- DELETE `/api/v1/users/{id}` - Delete user

### File Operations
- POST `/api/v1/files/upload` - Single file upload
- POST `/api/v1/files/bulk-upload` - Multiple files upload
- GET `/api/v1/files/{id}` - Download file
- DELETE `/api/v1/files/{id}` - Delete file

### Data Processing
- POST `/api/v1/data/import` - Import data (CSV/Excel)
- GET `/api/v1/data/export` - Export data
- POST `/api/v1/data/validate` - Validate data

## Testing

The application includes comprehensive integration tests that verify API functionality:

- TestContainers for database testing
- REST Assured for API testing
- JUnit 5 for test lifecycle management
- Spring Boot Test for integration testing

Test configuration:
- Random port allocation
- Database cleanup before each test
- Test user creation and verification
- Proper authentication handling

To run the tests:
```bash
mvn test
```

## Getting Started

1. Prerequisites:
   - Java 17
   - Maven
   - PostgreSQL
   - Docker (for tests)

2. Configuration:
   ```properties
   # application.properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/demo_db
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   
   # JWT Configuration
   jwt.secret=your_jwt_secret
   jwt.expiration=86400000
   ```

3. Build and Run:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- Spring Boot team for the excellent framework
- TestContainers team for the testing infrastructure
- The open-source community for various libraries used
- Contributors who have helped improve this project 