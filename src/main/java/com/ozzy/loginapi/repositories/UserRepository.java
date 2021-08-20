package com.ozzy.loginapi.repositories;

import com.ozzy.loginapi.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.Arrays;
import java.util.Optional;

@Repository
public class UserRepository implements DAO<User> {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long create(User user) {
        
        PreparedStatementCreator psc = new PreparedStatementCreatorFactory("insert into users(firstname, lastname, username, email, password) values (?,?,?,?,?)",
                                                                           Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,Types.VARCHAR)
                .newPreparedStatementCreator(Arrays.asList(user.getFirstname(),user.getLastname(),user.getUsername(),user.getEmail(),user.getPassword()));

        KeyHolder keyHolder = new GeneratedKeyHolder();

        return (long) jdbcTemplate.update(psc, keyHolder);
    }

    @Override
    public Optional<User> read(int id) {
        String sql =
        return Optional.empty();
    }

    @Override
    public int update(User user) {
        return 0;
    }

    @Override
    public int delete(int id) {
        return 0;
    }

}
