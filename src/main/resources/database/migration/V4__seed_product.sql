-- Product 1: Áo Thun Nam Basic
INSERT INTO product (id, name, description, slug, primary_imageurl, sku, quantity, quantity_available, sold_quantity, original_price, selling_price, discounted_price, no_of_view, selling_type, avg_rating, no_of_rating, has_variants, created_at, updated_at, status)
VALUES
    ('prod-0001', 'Áo Thun Nam Basic', 'Áo thun cotton thoáng mát', 'ao-thun-nam-basic', 'https://example.com/p1.jpg', 'SKU-0001', 100, 100, 0, 200000, 180000, 150000, 10, 'NORMAL', 4.5, 25, FALSE, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000, 0);
INSERT INTO product_category VALUES ('prod-0001', 'aaaa1111-bbbb-cccc-dddd-000000000001');
INSERT INTO product_brand VALUES ('prod-0001', '11111111-brand-aaaa-aaaa-111111111111');
INSERT INTO product_collection VALUES ('prod-0001', '11111111-collect-aaaa-aaaa-111111111111');
INSERT INTO product_tag VALUES ('prod-0001', '11111111-tag-aaaa-aaaa-111111111111');

-- Product 2: Áo Sơ Mi Nữ Trắng
INSERT INTO product (id, name, description, slug, primary_imageurl, sku, quantity, quantity_available, sold_quantity, original_price, selling_price, discounted_price, no_of_view, selling_type, avg_rating, no_of_rating, has_variants, created_at, updated_at, status)
VALUES
    ('prod-0002', 'Áo Sơ Mi Nữ Trắng', 'Áo sơ mi nữ thanh lịch', 'ao-so-mi-nu-trang', 'https://example.com/p2.jpg', 'SKU-0002', 120, 120, 0, 300000, 270000, 0, 8, 'NORMAL', 4.0, 20, FALSE, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000, 0);
INSERT INTO product_category VALUES ('prod-0002', 'aaaa1111-bbbb-cccc-dddd-000000000005');
INSERT INTO product_brand VALUES ('prod-0002', '22222222-brand-aaaa-aaaa-222222222222');
INSERT INTO product_collection VALUES ('prod-0002', '22222222-collect-aaaa-aaaa-222222222222');
INSERT INTO product_tag VALUES ('prod-0002', '22222222-tag-aaaa-aaaa-222222222222');

-- Product 3: Quần Jean Nam Skinny
INSERT INTO product (id, name, description, slug, primary_imageurl, sku, quantity, quantity_available, sold_quantity, original_price, selling_price, discounted_price, no_of_view, selling_type, avg_rating, no_of_rating, has_variants, created_at, updated_at, status)
VALUES
    ('prod-0003', 'Quần Jean Nam Skinny', 'Quần jean nam trẻ trung', 'quan-jean-nam-skinny', 'https://example.com/p3.jpg', 'SKU-0003', 150, 150, 0, 450000, 400000, 0, 6, 'NORMAL', 4.3, 30, FALSE, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000, 0);
INSERT INTO product_category VALUES ('prod-0003', 'aaaa1111-bbbb-cccc-dddd-000000000007');
INSERT INTO product_brand VALUES ('prod-0003', '33333333-brand-aaaa-aaaa-333333333333');
INSERT INTO product_collection VALUES ('prod-0003', '11111111-collect-aaaa-aaaa-111111111111');
INSERT INTO product_tag VALUES ('prod-0003', '33333333-tag-aaaa-aaaa-333333333333');

-- Product 4: Áo Khoác Nam Chống Nắng
INSERT INTO product (id, name, description, slug, primary_imageurl, sku, quantity, quantity_available, sold_quantity, original_price, selling_price, discounted_price, no_of_view, selling_type, avg_rating, no_of_rating, has_variants, created_at, updated_at, status)
VALUES
    ('prod-0004', 'Áo Khoác Nam Chống Nắng', 'Áo khoác chất liệu nhẹ chống tia UV', 'ao-khoac-nam-chong-nang', 'https://example.com/p4.jpg', 'SKU-0004', 80, 80, 0, 500000, 450000, 0, 9, 'NORMAL', 4.2, 15, FALSE, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000, 0);
INSERT INTO product_category VALUES ('prod-0004', 'aaaa1111-bbbb-cccc-dddd-000000000003');
INSERT INTO product_brand VALUES ('prod-0004', '11111111-brand-aaaa-aaaa-111111111111');
INSERT INTO product_collection VALUES ('prod-0004', '22222222-collect-aaaa-aaaa-222222222222');
INSERT INTO product_tag VALUES ('prod-0004', '11111111-tag-aaaa-aaaa-111111111111');

-- Product 5: Áo Thun Nữ Form Rộng
INSERT INTO product (id, name, description, slug, primary_imageurl, sku, quantity, quantity_available, sold_quantity, original_price, selling_price, discounted_price, no_of_view, selling_type, avg_rating, no_of_rating, has_variants, created_at, updated_at, status)
VALUES
    ('prod-0005', 'Áo Thun Nữ Form Rộng', 'Áo thun nữ thoải mái thời trang', 'ao-thun-nu-form-rong', 'https://example.com/p5.jpg', 'SKU-0005', 110, 110, 0, 220000, 200000, 0, 7, 'NORMAL', 4.1, 18, FALSE, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000, 0);
INSERT INTO product_category VALUES ('prod-0005', 'aaaa1111-bbbb-cccc-dddd-000000000004');
INSERT INTO product_brand VALUES ('prod-0005', '22222222-brand-aaaa-aaaa-222222222222');
INSERT INTO product_collection VALUES ('prod-0005', '11111111-collect-aaaa-aaaa-111111111111');
INSERT INTO product_tag VALUES ('prod-0005', '22222222-tag-aaaa-aaaa-222222222222');

-- Product 6: Quần Short Nữ Năng Động
INSERT INTO product (id, name, description, slug, primary_imageurl, sku, quantity, quantity_available, sold_quantity, original_price, selling_price, discounted_price, no_of_view, selling_type, avg_rating, no_of_rating, has_variants, created_at, updated_at, status)
VALUES
    ('prod-0006', 'Quần Short Nữ Năng Động', 'Quần short nữ thời trang hè', 'quan-short-nu-nang-dong', 'https://example.com/p6.jpg', 'SKU-0006', 140, 140, 0, 280000, 250000, 0, 5, 'NORMAL', 4.4, 22, FALSE, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000, 0);
INSERT INTO product_category VALUES ('prod-0006', 'aaaa1111-bbbb-cccc-dddd-000000000010');
INSERT INTO product_brand VALUES ('prod-0006', '33333333-brand-aaaa-aaaa-333333333333');
INSERT INTO product_collection VALUES ('prod-0006', '22222222-collect-aaaa-aaaa-222222222222');
INSERT INTO product_tag VALUES ('prod-0006', '33333333-tag-aaaa-aaaa-333333333333');

-- Product 7 -> Product 20 tiếp tục tương tự
-- Đổi tên sản phẩm, slug, ảnh, SKU, giá, category, brand, collection, tag theo dữ liệu bạn cung cấp
-- Đảm bảo mix đầy đủ 10 category con, 3 brand, 2 collection, 3 tag để đủ 20 sản phẩm
-- Giữ nguyên format SQL đã chuẩn hóa ở trên
