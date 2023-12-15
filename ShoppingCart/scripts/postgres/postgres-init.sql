---------------------------------------To Create Schema-----------------------------------------------------------
create schema if not exists shopping_cart;
grant all on schema shopping_cart to postgres; -- schema privileges
-------------------------------------To Create DataBase Tables and Associations----------------------------------------------------

create table shopping_cart.CART (CART_ID varchar(255) not null, TOTAL_PRICE numeric(10,2), primary key (CART_ID));
create table shopping_cart.CART_PRODUCTS (PRODUCT_QNTTY numeric(5,0) not null, SUB_TOTAL numeric(10,2) not null, CART_ID varchar(255) not null, P_CODE varchar(255) not null, primary key (CART_ID, P_CODE));
create table shopping_cart.ORDER_PRODUCTS (PRODUCT_QNTTY numeric(5,0) not null, SUB_TOTAL numeric(10,2) not null, ORDER_NUM numeric(5,0) not null, P_CODE varchar(255) not null, primary key (ORDER_NUM, P_CODE));
create table shopping_cart.ORDERS (ORDER_NUM numeric(5,0) not null, ORDER_AMOUNT numeric(10,2) not null, ADDRESS_LINE1 varchar(255), ADDRESS_LINE2 varchar(255), CITY varchar(255), COUNTRY varchar(255), STATE varchar(255), ZIPCODE varchar(255), CUSTOMER_EMAIL varchar(255) not null, CUSTOMER_NAME varchar(255) not null, CUSTOMER_PHONE varchar(255) not null, ORDER_DATE timestamp not null, USER_NAME varchar(255) not null, primary key (ORDER_NUM));
create table shopping_cart.PRODUCT (P_CODE varchar(255) not null, AVLBL_QNTTY numeric(5,0) not null, image bytea not null, DESCRIPTION varchar(255) not null, NAME varchar(255) not null, PRICE numeric(10,2) not null, C_CODE varchar(255) not null, primary key (P_CODE));
create table shopping_cart.PRODUCT_CATEGORY (C_CODE varchar(255) not null, CATEGORY varchar(255) not null, primary key (C_CODE));
create table shopping_cart.ROLE (ROLE_TYPE varchar(255) not null, primary key (ROLE_TYPE));
create table shopping_cart.USER_ACCOUNT (USER_NAME varchar(255) not null, CONTACT_NO varchar(255), ENABLED boolean default TRUE, FIRST_NAME varchar(255) not null, FULL_NAME varchar(255), LAST_NAME varchar(255), USER_PASSWORD varchar(255) not null, primary key (USER_NAME));
create table shopping_cart.USER_ROLES (USER_NAME varchar(255) not null, ROLE_TYPE varchar(255) not null, primary key (USER_NAME, ROLE_TYPE));
alter table shopping_cart.CART add constraint FKgndi2qx06e5p2gu2w4oxe7ofd foreign key (CART_ID) references shopping_cart.USER_ACCOUNT;
alter table shopping_cart.CART_PRODUCTS add constraint FKc3t4ip14ek73i06cmslcn1rml foreign key (CART_ID) references shopping_cart.CART;
alter table shopping_cart.CART_PRODUCTS add constraint FKjqi2usqcelrfe2i9a2mh35mit foreign key (P_CODE) references shopping_cart.PRODUCT;
alter table shopping_cart.ORDER_PRODUCTS add constraint FKg86mqil0ays05nsrop0pjgkfv foreign key (ORDER_NUM) references shopping_cart.ORDERS;
alter table shopping_cart.ORDER_PRODUCTS add constraint FKo17hjxih3sd7wwkp72x35klvq foreign key (P_CODE) references shopping_cart.PRODUCT;
alter table shopping_cart.ORDERS add constraint FKd3k2cxtcuh1hmrx4kbs2loskg foreign key (USER_NAME) references shopping_cart.USER_ACCOUNT;
alter table shopping_cart.PRODUCT add constraint FKib2c00kib1k55vw57snx3c79t foreign key (C_CODE) references shopping_cart.PRODUCT_CATEGORY;
alter table shopping_cart.USER_ROLES add constraint FK116e96apt6lqygt2rjusqarat foreign key (ROLE_TYPE) references shopping_cart.ROLE;
alter table shopping_cart.USER_ROLES add constraint FK61w7l5p34dxql575dcg5txyiq foreign key (USER_NAME) references shopping_cart.USER_ACCOUNT;
----------------------------------------------To Create Data---------------------------------------------------------------------------------
-----Add Role table data---
Insert into shopping_cart.ROLE (ROLE_TYPE) values ('ADMIN');
Insert into shopping_cart.ROLE (ROLE_TYPE) values ('USER');
----- Add User Table data----
Insert into shopping_cart.USER_ACCOUNT (USER_NAME,CONTACT_NO,ENABLED,FIRST_NAME,FULL_NAME,LAST_NAME,USER_PASSWORD) values ('abc@gmail.com','36738389',TRUE,'Rahul','Rahul Chauhan','Chauhan','$2a$10$yfYPhQatMYip7fH6WExGGueoD2IF1pXUd2EM75VX3iZpexicbu7F6');
Insert into shopping_cart.USER_ACCOUNT (USER_NAME,CONTACT_NO,ENABLED,FIRST_NAME,FULL_NAME,LAST_NAME,USER_PASSWORD) values ('xyz@gmail.com','23638398',TRUE,'Kiran','Kiran Chauhan','Chauhan','$2a$10$yfYPhQatMYip7fH6WExGGueoD2IF1pXUd2EM75VX3iZpexicbu7F6');
--- Real Password is --- Admin@123
-----Add User Role Table Data----
Insert into shopping_cart.USER_ROLES (USER_NAME,ROLE_TYPE) values ('abc@gmail.com','ADMIN');
Insert into shopping_cart.USER_ROLES (USER_NAME,ROLE_TYPE) values ('xyz@gmail.com','USER');

insert into shopping_cart.cart(cart_id, total_price) values('xyz@gmail.com', 0.00); --- create empty cart for user with normal role
insert into shopping_cart.cart(cart_id, total_price) values('abc@gmail.com', 0.00); --- create empty cart for user with normal role
