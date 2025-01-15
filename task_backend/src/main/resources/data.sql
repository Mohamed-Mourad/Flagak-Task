-- Insert sample customers
INSERT INTO customers (customer_id, name, email, password)
VALUES
    (gen_random_uuid(), 'Mohamed Mourad', 'moh.mou@example.com', 'password123'),
    (gen_random_uuid(), 'Tony Stark', 'tony.stark@example.com', 'securepass456'),
    (gen_random_uuid(), 'Donald Blake', 'thor.odinson@example.com', 'asgardsec789')
;

-- Insert sample vendors
INSERT INTO vendors (vendor_id, business_name, email, password, business_certificate_number, billing_address)
VALUES
    (gen_random_uuid(), 'Tech Supplies Co.', 'tech.supplies@example.com', 'techpass123', 'CERT12345', '123 Tech Street, Cairo, Egypt'),
    (gen_random_uuid(), 'Fashion World', 'fashion.world@example.com', 'fashionpass456', 'CERT67890', '456 Fashion Avenue, Alexandria, Egypt'),
    (gen_random_uuid(), 'Sports Gear Inc.', 'sports.gear@example.com', 'sportspass789', 'CERT11223', '789 Sports Lane, Giza, Egypt')
;

-- Insert sample products
INSERT INTO products (product_id, product_name, price, stock_quantity, vendor_id)
VALUES
    (gen_random_uuid(), 'Laptop', 1200.00, 10, (SELECT vendor_id FROM vendors WHERE business_name = 'Tech Supplies Co.')),
    (gen_random_uuid(), 'Smartphone', 800.00, 25, (SELECT vendor_id FROM vendors WHERE business_name = 'Tech Supplies Co.')),
    (gen_random_uuid(), 'Running Shoes', 100.00, 50, (SELECT vendor_id FROM vendors WHERE business_name = 'Sports Gear Inc.')),
    (gen_random_uuid(), 'Jacket', 150.00, 30, (SELECT vendor_id FROM vendors WHERE business_name = 'Fashion World')),
    (gen_random_uuid(), 'Handbag', 200.00, 20, (SELECT vendor_id FROM vendors WHERE business_name = 'Fashion World'))
;

-- Insert sample carts
INSERT INTO carts (cart_id, customer_id)
VALUES
    (gen_random_uuid(), (SELECT customer_id FROM customers WHERE name = 'Mohamed Mourad')),
    (gen_random_uuid(), (SELECT customer_id FROM customers WHERE name = 'Tony Stark')),
    (gen_random_uuid(), (SELECT customer_id FROM customers WHERE name = 'Donald Blake'))
;

-- Insert sample cart items
INSERT INTO cart_items (cart_item_id, cart_id, product_id, quantity)
VALUES
    (gen_random_uuid(),
     (SELECT cart_id FROM carts WHERE customer_id = (SELECT customer_id FROM customers WHERE name = 'Mohamed Mourad')),
     (SELECT product_id FROM products WHERE product_name = 'Laptop'),
     1),
    (gen_random_uuid(),
     (SELECT cart_id FROM carts WHERE customer_id = (SELECT customer_id FROM customers WHERE name = 'Tony Stark')),
     (SELECT product_id FROM products WHERE product_name = 'Running Shoes'),
     2),
    (gen_random_uuid(),
     (SELECT cart_id FROM carts WHERE customer_id = (SELECT customer_id FROM customers WHERE name = 'Donald Blake')),
     (SELECT product_id FROM products WHERE product_name = 'Handbag'),
     1)
;
