create table if not exists customer (
    customer_id int Auto_increment primary key,
    name varchar(255) not null,
    email varchar(255) not null unique,
    mobile_number varchar(20) not null unique,
    created_at date not null,
    created_by varchar(255) not null,
    updated_at date null,
    updated_by varchar(255) null
);

create table if not exists accounts (
    customer_id int not null,
    account_number int Auto_increment primary key,
    account_type varchar(100) not null,
    branch_address varchar(200) not null,
    created_at date not null,
    created_by varchar(255) not null,
    updated_at date null,
    updated_by varchar(255) null
--     foreign key (customer_id) references customer(customer_id)
);