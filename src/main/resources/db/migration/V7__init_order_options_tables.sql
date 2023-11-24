create table order_options(
    id int primary key auto_increment,
    name varchar(100) not null
);
create table option_steps(
                             id int primary key auto_increment,
                             name varchar(100) not null,
                             order_options_id  int not null,
                             days_to_deadline int null,
                             foreign key(order_options_id) references order_options(id)
);
alter table product_orders add column order_options_id int null;
alter table product_orders add foreign key (order_options_id) references order_options(id);

