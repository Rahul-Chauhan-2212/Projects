-- Run this script as SYSTEM user to create or reset the schema from scratch.
--to alter oracle session---
alter session set "_ORACLE_SCRIPT"=true;

---creation of user----
drop user shopping_cart cascade; -- drop existing user

create user shopping_cart identified by welcome DEFAULT TABLESPACE users TEMPORARY TABLESPACE temp; -- create user
grant all privileges to shopping_cart; -- grant privileges

---for postgres,
drop user shopping_cart if exists; -- drop existing user
create user shopping_cart with password 'welcome'; -- create user
drop database shopping_cart if exists; -- drop existing database
create database shopping_cart; -- create database
grant all privileges on database shopping_cart to shopping_cart; --grant privileges
create schema if not exists shopping_cart;
grant all on schema shopping_cart to shopping_cart; -- schema privileges

