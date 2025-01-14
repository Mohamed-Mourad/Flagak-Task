-- Create customers table
CREATE TABLE customers (
                           customer_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                           name VARCHAR(255) NOT NULL,
                           email VARCHAR(255) UNIQUE NOT NULL,
                           password VARCHAR(255) NOT NULL,
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create vendors table
CREATE TABLE vendors (
                         vendor_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                         business_name VARCHAR(255) NOT NULL,
                         business_certificate_number VARCHAR(50) UNIQUE NOT NULL,
                         billing_address TEXT NOT NULL,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create products table
CREATE TABLE products (
                          product_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                          product_name VARCHAR(255) NOT NULL,
                          price DECIMAL(10, 2) NOT NULL CHECK (price > 0),
                          stock_quantity INT NOT NULL CHECK (stock_quantity >= 0),
                          vendor_id UUID NOT NULL,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          FOREIGN KEY (vendor_id) REFERENCES vendors(vendor_id) ON DELETE CASCADE
);

-- Create carts table
CREATE TABLE carts (
                       cart_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                       customer_id UUID NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE CASCADE
);

-- Create cart_items table
CREATE TABLE cart_items (
                            cart_item_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                            cart_id UUID NOT NULL,
                            product_id UUID NOT NULL,
                            quantity INT NOT NULL CHECK (quantity > 0),
                            added_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            FOREIGN KEY (cart_id) REFERENCES carts(cart_id) ON DELETE CASCADE,
                            FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE
);
