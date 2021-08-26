package com.ozzy.loginapi.repositories;

import com.ozzy.loginapi.exceptions.DataNotFoundException;
import com.ozzy.loginapi.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository implements DAO<User> {

    private final JdbcTemplate jdbcTemplate;

    RowMapper<User> rowMapper = (rs,rowNum) -> {
        User user = new User();
        user.setId(rs.getLong("user_id"));
        user.setFirstname(rs.getString("firstname"));
        user.setLastname(rs.getString("lastname"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        return user;
    };

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
        String sql = "select * from users where user_id = ?";
        User user = null;
        try{
            user = jdbcTemplate.queryForObject(sql, rowMapper, id);
        }catch (DataAccessException e){
            throw new DataNotFoundException("User not found!!!");
        }
        return Optional.ofNullable(user);
    }

    @Override
    public int update(User user) {
        String sql = "update users set firstname=?, lastname=?, username=?, email=?, password=? where user_id=?";
        return jdbcTemplate.update(sql,user.getFirstname(),user.getLastname(),user.getUsername(),user.getEmail(),user.getPassword(),user.getId());
    }

    @Override
    public int delete(int id) {
        String sql = "delete from users where user_id=?";
        return jdbcTemplate.update(sql,id);
    }
    
    @Override
    public List<User> listAll() {
        String sql = "select firstname, lastname, username, email, password from users";
        return jdbcTemplate.query(sql, rowMapper);
    }
    
    
}
