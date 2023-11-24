create table product_orders
(
                         id int primary key auto_increment,
                         name varchar(100) not null,
                         done bit
);
alter table products add column product_order_id int null;
alter table products add foreign key(product_order_id) references product_orders(id);