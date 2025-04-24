-- Insert test admin user
INSERT INTO users (email, password, first_name, last_name, role, created_at) 
VALUES (
    'admin@example.com',
    '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', -- password: 'password'
    'Admin',
    'User',
    'ADMIN',
    CURRENT_TIMESTAMP
);

-- Insert test regular user
INSERT INTO users (email, password, first_name, last_name, role, created_at) 
VALUES (
    'user@example.com',
    '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', -- password: 'password'
    'Test',
    'User',
    'USER',
    CURRENT_TIMESTAMP
); 