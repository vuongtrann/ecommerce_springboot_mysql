-- 4 Category cha ngành hàng Quần Áo
INSERT INTO category (id, name, slug, created_at, updated_at, status)
VALUES
    ('11111111-aaaa-aaaa-aaaa-111111111111', 'Áo Nam', 'ao-nam', UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000, 0),
    ('22222222-aaaa-aaaa-aaaa-222222222222', 'Áo Nữ', 'ao-nu', UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000, 0),
    ('33333333-aaaa-aaaa-aaaa-333333333333', 'Quần Nam', 'quan-nam', UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000, 0),
    ('44444444-aaaa-aaaa-aaaa-444444444444', 'Quần Nữ', 'quan-nu', UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000, 0);

-- 10 Category con
INSERT INTO category (id, name, slug, parent_id, created_at, updated_at, status)
VALUES
    ('aaaa1111-bbbb-cccc-dddd-000000000001', 'Áo thun nam', 'ao-thun-nam', '11111111-aaaa-aaaa-aaaa-111111111111', UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000, 0),
    ('aaaa1111-bbbb-cccc-dddd-000000000002', 'Áo sơ mi nam', 'ao-so-mi-nam', '11111111-aaaa-aaaa-aaaa-111111111111', UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000, 0),
    ('aaaa1111-bbbb-cccc-dddd-000000000003', 'Áo khoác nam', 'ao-khoac-nam', '11111111-aaaa-aaaa-aaaa-111111111111', UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000, 0),

    ('aaaa1111-bbbb-cccc-dddd-000000000004', 'Áo thun nữ', 'ao-thun-nu', '22222222-aaaa-aaaa-aaaa-222222222222', UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000, 0),
    ('aaaa1111-bbbb-cccc-dddd-000000000005', 'Áo sơ mi nữ', 'ao-so-mi-nu', '22222222-aaaa-aaaa-aaaa-222222222222', UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000, 0),
    ('aaaa1111-bbbb-cccc-dddd-000000000006', 'Áo khoác nữ', 'ao-khoac-nu', '22222222-aaaa-aaaa-aaaa-222222222222', UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000, 0),

    ('aaaa1111-bbbb-cccc-dddd-000000000007', 'Quần jean nam', 'quan-jean-nam', '33333333-aaaa-aaaa-aaaa-333333333333', UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000, 0),
    ('aaaa1111-bbbb-cccc-dddd-000000000008', 'Quần short nam', 'quan-short-nam', '33333333-aaaa-aaaa-aaaa-333333333333', UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000, 0),

    ('aaaa1111-bbbb-cccc-dddd-000000000009', 'Quần jean nữ', 'quan-jean-nu', '44444444-aaaa-aaaa-aaaa-444444444444', UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000, 0),
    ('aaaa1111-bbbb-cccc-dddd-000000000010', 'Quần short nữ', 'quan-short-nu', '44444444-aaaa-aaaa-aaaa-444444444444', UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000, 0);
