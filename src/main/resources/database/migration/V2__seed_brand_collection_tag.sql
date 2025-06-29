-- Seed Brand
INSERT INTO brand (id, name, description, slug, created_at, updated_at, status)
VALUES
    ('11111111-brand-aaaa-aaaa-111111111111', 'Nike', 'Thương hiệu thời trang thể thao nổi tiếng', 'nike', UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000, 0),
    ('22222222-brand-aaaa-aaaa-222222222222', 'Adidas', 'Thương hiệu thời trang toàn cầu', 'adidas', UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000, 0),
    ('33333333-brand-aaaa-aaaa-333333333333', 'Gucci', 'Thương hiệu cao cấp nổi tiếng Ý', 'gucci', UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000, 0);

-- Seed Collection
INSERT INTO collection (id, collection_name, collection_description, collection_image, slug, created_at, updated_at, status)
VALUES
    ('11111111-collect-aaaa-aaaa-111111111111', 'Summer 2024', 'Bộ sưu tập mùa hè 2024', 'summer-2024.jpg', 'summer-2024', UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000, 0),
    ('22222222-collect-aaaa-aaaa-222222222222', 'Winter 2024', 'Bộ sưu tập mùa đông 2024', 'winter-2024.jpg', 'winter-2024', UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000, 0);

-- Seed Tag
INSERT INTO tag (id, tag_name, created_at, updated_at, status)
VALUES
    ('11111111-tag-aaaa-aaaa-111111111111', 'Sale', UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000, 0),
    ('22222222-tag-aaaa-aaaa-222222222222', 'New Arrival', UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000, 0),
    ('33333333-tag-aaaa-aaaa-333333333333', 'Best Seller', UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000, 0);
