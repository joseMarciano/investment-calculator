package com.investment.managment.user;

import java.util.Set;

public interface UserGateway {

    User update(User user);

    Set<User> findAll();

    void removeById(UserID userID);
}
