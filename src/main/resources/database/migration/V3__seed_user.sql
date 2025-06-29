-- User Admin
INSERT INTO users (id, uid, messenger_id, first_name, last_name, phone, avatar, email, username, password, is_enabled, forgot_password_token, verification_token, is_locked, role, created_at, updated_at, status)
VALUES
    ('11111111-user-aaaa-aaaa-111111111111', 10001, 'messenger-001', 'Admin', 'System', '0123456789', NULL, 'admin@example.com', 'admin', '$2a$10$0V8hUfw9/49csZ2EFHXh/uvBeGpSBD0j5rk7piQBe1xGki2oRAaSi', 1, NULL, 'verify-token-1', 0, 'ADMIN', UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000, 0);

-- User John Doe
INSERT INTO users (id, uid, messenger_id, first_name, last_name, phone, avatar, email, username, password, is_enabled, forgot_password_token, verification_token, is_locked, role, created_at, updated_at, status)
VALUES
    ('22222222-user-aaaa-aaaa-222222222222', 10002, 'messenger-002', 'John', 'Doe', '0987654321', NULL, 'john@example.com', 'johndoe', '$2a$10$0V8hUfw9/49csZ2EFHXh/uvBeGpSBD0j5rk7piQBe1xGki2oRAaSi', 1, NULL, 'verify-token-2', 0, 'USER', UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000, 0);

-- User Jane Smith
INSERT INTO users (id, uid, messenger_id, first_name, last_name, phone, avatar, email, username, password, is_enabled, forgot_password_token, verification_token, is_locked, role, created_at, updated_at, status)
VALUES
    ('33333333-user-aaaa-aaaa-333333333333', 10003, 'messenger-003', 'Jane', 'Smith', '0977111222', NULL, 'jane@example.com', 'janesmith', '$2a$10$0V8hUfw9/49csZ2EFHXh/uvBeGpSBD0j5rk7piQBe1xGki2oRAaSi', 1, NULL, 'verify-token-3', 0, 'USER', UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000, 0);

-- User Seller Test
INSERT INTO users (id, uid, messenger_id, first_name, last_name, phone, avatar, email, username, password, is_enabled, forgot_password_token, verification_token, is_locked, role, created_at, updated_at, status)
VALUES
    ('44444444-user-aaaa-aaaa-444444444444', 10004, 'messenger-004', 'Seller', 'Test', '0966555444', NULL, 'seller@example.com', 'selleruser', '$2a$10$0V8hUfw9/49csZ2EFHXh/uvBeGpSBD0j5rk7piQBe1xGki2oRAaSi', 1, NULL, 'verify-token-4', 0, 'USER', UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000, 0);
