package com.sbsi.dao;

import com.sbsi.entity.User;

public interface UserDetailsDao {
  User findUserByUsername(String username);
}