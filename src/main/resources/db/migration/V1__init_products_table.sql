drop table if exists products;
create table products(
    id int primary key auto_increment,
    name varchar(100) not null,
    quantity int not null,
    done bit
)