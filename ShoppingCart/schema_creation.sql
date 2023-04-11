-- Run this script as SYSTEM user to create or reset the schema from scratch.
--to alter oracle session---
alter session set "_ORACLE_SCRIPT"=true;

---creation of user----
drop user shopping_cart cascade; -- drop existing user

create user shopping_cart identified by welcome DEFAULT TABLESPACE users TEMPORARY TABLESPACE temp; -- create user
grant all privileges to shopping_cart; -- grant privileges

---for postgres,
drop user shopping_cart; -- drop existing user
create user shopping_cart with password 'welcome'; -- create user
drop database shopping_cart; -- drop existing database
create database shopping_cart; -- create database
