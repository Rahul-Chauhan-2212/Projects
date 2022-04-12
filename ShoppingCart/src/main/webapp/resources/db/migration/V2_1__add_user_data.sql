-----Add Role table data---
Insert into ROLE (ROLE_TYPE) values ('ADMIN');
Insert into ROLE (ROLE_TYPE) values ('USER');
----- Add User Table data----
Insert into USER_ACCOUNT (USER_NAME,CONTACT_NO,ENABLED,FIRST_NAME,FULL_NAME,LAST_NAME,USER_PASSWORD) values ('abc@gmail.com','36738389',1,'Virat','Virat Kohli','Kohli','$2a$10$vLu.DGGJ/BoDrVmf87j8PenKeRRqZoHWOOCOAHQaIiOsBkI/jL/lC');
Insert into USER_ACCOUNT (USER_NAME,CONTACT_NO,ENABLED,FIRST_NAME,FULL_NAME,LAST_NAME,USER_PASSWORD) values ('xyz@gmail.com','23638398',1,'KL','KL Rahul','Rahul','$2a$10$vLu.DGGJ/BoDrVmf87j8PenKeRRqZoHWOOCOAHQaIiOsBkI/jL/lC');
-----Add User Role Table Data----
Insert into USER_ROLES (USER_NAME,ROLE_TYPE) values ('abc@gmail.com','ADMIN');
Insert into USER_ROLES (USER_NAME,ROLE_TYPE) values ('xyz@gmail.com','USER');



