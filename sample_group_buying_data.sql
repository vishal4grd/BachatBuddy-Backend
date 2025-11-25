-- Sample data for Group Buying Products
-- Run this script after the application creates the tables

-- Insert sample group buying products
INSERT INTO group_buying_products (product_name, product_image, original_price, group_price, total_needed, current_joined, category, description, is_active, created_at) VALUES
('Bluetooth Earbuds', 'assets/bluetooth-earbuds.jpg', 1099, 1000, 10, 7, 'Electronics', 'Premium wireless earbuds with noise cancellation', true, NOW()),
('5kg Premium Rice', 'assets/premium-rice.jpg', 450, 225, 10, 7, 'Groceries', 'High quality basmati rice', true, NOW()),
('500W Mixer Grinder', 'assets/mixer-grinder.jpg', 2399, 1300, 10, 7, 'Appliances', 'Powerful mixer grinder with 3 jars', true, NOW()),
('1L Sunflower Oil (Pack of 3)', 'assets/sunflower-oil.jpg', 320, 160, 10, 7, 'Groceries', 'Healthy cooking oil pack', true, NOW()),
('Casual Sneakers (Men)', 'assets/sneakers.jpg', 2500, 500, 10, 7, 'Fashion', 'Comfortable casual sneakers', true, NOW()),
('Cotton T-shirt (Combo of 3)', 'assets/tshirt.jpg', 1750, 400, 10, 7, 'Fashion', 'Premium cotton t-shirt combo', true, NOW());

-- Additional sample products
INSERT INTO group_buying_products (product_name, product_image, original_price, group_price, total_needed, current_joined, category, description, is_active, created_at) VALUES
('Smart Watch', 'assets/smartwatch.jpg', 3999, 2500, 15, 8, 'Electronics', 'Fitness tracking smart watch', true, NOW()),
('10kg Wheat Flour', 'assets/wheat-flour.jpg', 500, 300, 20, 12, 'Groceries', 'Fresh wheat flour', true, NOW()),
('LED Desk Lamp', 'assets/desk-lamp.jpg', 1200, 700, 8, 3, 'Home & Living', 'Adjustable LED desk lamp', true, NOW()),
('Yoga Mat', 'assets/yoga-mat.jpg', 800, 450, 12, 5, 'Sports', 'Non-slip yoga mat with carry bag', true, NOW());

-- Note: You'll need to update the product_image paths to match your actual image locations
-- The current_joined values can be adjusted based on your testing needs
