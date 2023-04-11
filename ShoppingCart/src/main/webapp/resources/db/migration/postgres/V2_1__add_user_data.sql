-----Add Role table data---
Insert into ROLE (ROLE_TYPE) values ('ADMIN');
Insert into ROLE (ROLE_TYPE) values ('USER');
----- Add User Table data----
Insert into USER_ACCOUNT (USER_NAME,CONTACT_NO,ENABLED,FIRST_NAME,FULL_NAME,LAST_NAME,USER_PASSWORD) values ('abc@gmail.com','36738389',TRUE,'Rahul','Rahul Chauhan','Chauhan','$2a$10$yfYPhQatMYip7fH6WExGGueoD2IF1pXUd2EM75VX3iZpexicbu7F6');
Insert into USER_ACCOUNT (USER_NAME,CONTACT_NO,ENABLED,FIRST_NAME,FULL_NAME,LAST_NAME,USER_PASSWORD) values ('xyz@gmail.com','23638398',TRUE,'Kiran','Kiran Chauhan','Chauhan','$2a$10$yfYPhQatMYip7fH6WExGGueoD2IF1pXUd2EM75VX3iZpexicbu7F6');
--- Real Password is --- Admin@123
-----Add User Role Table Data----
Insert into USER_ROLES (USER_NAME,ROLE_TYPE) values ('abc@gmail.com','ADMIN');
Insert into USER_ROLES (USER_NAME,ROLE_TYPE) values ('xyz@gmail.com','USER');

insert into cart(cart_id, total_price) values('xyz@gmail.com', 0.00); --- create empty cart for user with normal role
insert into cart(cart_id, total_price) values('abc@gmail.com', 0.00); --- create empty cart for user with normal role

