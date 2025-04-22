# Java API Demo Project

A robust Spring Boot-based REST API demonstrating various enterprise-level features and best practices.

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

## Project Structure

## Project Status
✅ Completed:
- Project structure and dependencies
- User entity and DTO
- Application properties
- Basic configuration
- User management implementation
- Security setup with JWT
- Exception handling
- File operations
- Data processing (CSV/Excel import/export)
- Monitoring setup
  - Health checks
  - Metrics collection
  - Request logging
  - Prometheus integration

🚧 In progress:
- Search & filtering

⏳ Pending:
- Monitoring setup

## API Endpoints

### 1. User Management
- `POST /api/v1/users` - Create user
- `GET /api/v1/users` - List users (with pagination)
- `GET /api/v1/users/{id}` - Get user details
- `PUT /api/v1/users/{id}` - Update user
- `DELETE /api/v1/users/{id}` - Delete user
- `POST /api/v1/users/batch` - Batch user creation

### 2. Authentication & Authorization
- `POST /api/v1/auth/login` - User login
- `POST /api/v1/auth/refresh` - Refresh token
- `POST /api/v1/auth/logout` - Logout
- `POST /api/v1/auth/reset-password` - Password reset

### 3. File Operations
- `POST /api/v1/files/upload` - Single file upload
- `POST /api/v1/files/bulk-upload` - Multiple files upload
- `GET /api/v1/files/{id}` - Download file
- `DELETE /api/v1/files/{id}` - Delete file

### 4. Data Processing
- `POST /api/v1/data/import` - Import data (CSV/Excel)
- `GET /api/v1/data/export` - Export data
- `POST /api/v1/data/validate` - Validate data

### 5. Search & Filtering
- `GET /api/v1/search` - Global search
- `GET /api/v1/filter` - Advanced filtering
- `GET /api/v1/suggest` - Auto-suggestions

## Technical Features

1. **Security**
   - JWT Authentication
   - Role-based authorization
   - API key authentication
   - Rate limiting

2. **Data Handling**
   - Request validation
   - Error handling
   - Response pagination
   - Data filtering
   - Sorting

3. **Performance**
   - Caching (Redis)
   - Async processing
   - Batch operations

4. **Monitoring**
   - API metrics
   - Health checks
   - Audit logging
   - Request tracing

## Project Structure 

## Monitoring Endpoints

### 1. Health Check
- `GET /actuator/health` - System health status

### 2. Metrics
- `GET /actuator/metrics` - Available metrics
- `GET /actuator/prometheus` - Prometheus metrics
- `GET /actuator/info` - Application information

### 3. Custom Metrics
- API request counts
- Response times
- Error rates
- Custom health indicators 

## Monitoring Guide

### 1. Built-in Actuator Endpoints
Access these endpoints directly in your browser or using tools like curl:

- Health Check: `http://localhost:8080/actuator/health`
- Metrics List: `http://localhost:8080/actuator/metrics`
- Specific Metric: `http://localhost:8080/actuator/metrics/{metric.name}`
- Prometheus Format: `http://localhost:8080/actuator/prometheus`

### 2. Setting up Prometheus
1. Download Prometheus from https://prometheus.io/download/
2. Create `prometheus.yml`:
```yaml
global:
  scrape_interval: 15s

scrape_configs:
  - job_name: 'spring-actuator'
    metrics_path: '/actuator/prometheus'
    static_configs:
      - targets: ['localhost:8080']
```
3. Start Prometheus:
```bash
./prometheus --config.file=prometheus.yml
```
4. Access Prometheus UI: `http://localhost:9090`

### 3. Setting up Grafana
1. Download Grafana from https://grafana.com/grafana/download
2. Start Grafana
3. Access Grafana UI: `http://localhost:3000` (default credentials: admin/admin)
4. Add Prometheus as a data source:
   - URL: `http://localhost:9090`
   - Access: Browser
5. Import dashboard templates:
   - JVM (Micrometer) ID: 4701
   - Spring Boot 2.1 Statistics ID: 10280

### 4. Available Metrics

#### System Metrics
- JVM Memory Usage
- System CPU Usage
- Thread States
- Garbage Collection

#### Application Metrics
- HTTP Request Counts
- Response Times
- Error Rates
- API Endpoint Usage

#### Custom Business Metrics
- Total API Requests: `api.requests.total`
- User Operations: `user.operations`
- File Operations: `file.operations`

### 5. Logging

JSON formatted logs are available in the console and can be collected using tools like:
- ELK Stack (Elasticsearch, Logstash, Kibana)
- Graylog
- Splunk

### 6. Sample Monitoring Commands

Check application health:
```bash
curl http://localhost:8080/actuator/health
```

View all available metrics:
```bash
curl http://localhost:8080/actuator/metrics
```

Check specific metric (e.g., http requests):
```bash
curl http://localhost:8080/actuator/metrics/http.server.requests
```

View Prometheus format metrics:
```bash
curl http://localhost:8080/actuator/prometheus
```

### 7. Recommended Monitoring Stack

For production environments, we recommend:
1. **Metrics Collection & Storage**
   - Prometheus for metrics collection
   - Grafana for visualization
   - Alert Manager for alerting

2. **Log Management**
   - ELK Stack or Graylog for log aggregation
   - Filebeat for log shipping

3. **APM (Application Performance Monitoring)**
   - Spring Boot Admin Server
   - Or commercial solutions like Datadog, New Relic

### 8. Alert Setup
Configure alerts in Grafana for:
- High error rates (> 1%)
- Long response times (> 500ms)
- High CPU usage (> 80%)
- Low memory availability (< 20%) 