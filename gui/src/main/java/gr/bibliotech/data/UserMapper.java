package gr.bibliotech.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import gr.bibliotech.app.User;

public class UserMapper implements RowMapper<User> {

    /**
     * Map the user mysql data to class
     */
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        
        return new User(
            rs.getInt("idUser"),
            rs.getString("username"),
            rs.getString("email"),
            rs.getString("password")
        );
    }

}