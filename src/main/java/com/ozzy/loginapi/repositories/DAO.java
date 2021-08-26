package com.ozzy.loginapi.repositories;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {

    Long create (T t);

    Optional<T> read(int id);

    int update(T t);

    int delete(int id);
    
    List<T> listAll();
}
