package com.tazdev.myhome.repository;

import com.tazdev.myhome.model.User;

public interface CustomizedUserRepository {
    void findByUsernameCustom(User user);
}
