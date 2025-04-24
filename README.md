# Java API Demo Project

## Features

### Authentication & Security
- JWT-based authentication
- Role-based access control
- Secure password handling with BCrypt
- Stateless session management
- Protected API endpoints

### User Management
- User registration and login
- Profile management
- Role-based authorization
- Email validation

### File Operations
- File upload and download
- Multi-file handling
- Secure file storage
- File metadata tracking

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

## Technical Stack

- Java 17
- Spring Boot 3.x
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

The application includes integration tests that verify the API functionality. Tests are configured to:
- Run on a random port to avoid conflicts
- Clean the database before each test
- Create and verify test users before authentication
- Use proper authentication for API calls

To run the tests:
```bash
mvn test
```

### Overview
The project uses a comprehensive testing strategy with:
- REST Assured for API testing
- TestContainers for database testing
- JUnit 5 for test lifecycle management
- Spring Boot Test for integration testing

### Test Categories

#### 1. Authentication Tests
- Login success/failure
- Token validation
- Authorization checks

#### 2. User Management Tests
- User creation
- User retrieval
- User updates
- User deletion
- List users with pagination

#### 3. File Operation Tests
- File upload
- File download
- Multiple file handling
- File deletion

#### 4. Data Processing Tests
- Excel import/export
- CSV import/export
- Data validation
- Error handling

### Test Configuration

#### Database
- Uses TestContainers PostgreSQL
- Automatic schema creation
- Test data initialization
- Isolated test environment
- Automatic cleanup

#### Security
- Test JWT configuration
- Mock authentication
- Role-based access testing
- Security context setup

#### Test Data
- Predefined test users
- Admin user: admin@example.com
- Regular user: user@example.com
- Test password: password (BCrypt encoded)

### Best Practices

1. **Test Independence**
   - Each test should be independent
   - Clean state before each test
   - No test order dependencies

2. **Naming Convention**
   - Test class: `*ApiTest.java`
   - Test methods: `shouldDoSomething()`
   - Clear and descriptive names

3. **Assertions**
   - Use REST Assured matchers
   - Check response status
   - Validate response body
   - Verify error messages

4. **Error Testing**
   - Test failure scenarios
   - Validate error responses
   - Check error messages
   - Test boundary conditions

## Monitoring

### Health Check Endpoints
- GET `/actuator/health` - System health status
- GET `/actuator/metrics` - Available metrics
- GET `/actuator/prometheus` - Prometheus metrics
- GET `/actuator/info` - Application information

### Metrics Collection
- API request counts
- Response times
- Error rates
- JVM metrics
- Custom business metrics

### Alert Configuration
Configure alerts in Grafana for:
- High error rates (> 1%)
- Long response times (> 500ms)
- High CPU usage (> 80%)
- Low memory availability (< 20%)

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

### Code Style
- Follow Google Java Style Guide
- Use meaningful variable and method names
- Write comprehensive documentation
- Include unit tests for new features

### Commit Messages
- Use clear and meaningful commit messages
- Follow conventional commits specification
- Reference issues and pull requests

### Pull Request Process
1. Update the README.md with details of changes
2. Update the CHANGELOG.md with version details
3. Ensure all tests pass
4. Get approval from two other developers
5. Merge only after CI/CD pipeline succeeds

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- Spring Boot team for the excellent framework
- TestContainers team for the testing infrastructure
- The open-source community for various libraries used
- Contributors who have helped improve this project 