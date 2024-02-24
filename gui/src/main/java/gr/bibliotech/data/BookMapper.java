package gr.bibliotech.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import gr.bibliotech.app.Book;

public class BookMapper implements RowMapper<Book> {

    /**
     * Map the book mysql data to class
     */
    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        
        return new Book(
            rs.getString("ISBN"),
            rs.getString("title"),
            rs.getString("author"),
            rs.getString("description"),
            rs.getString("coverURL"),
            rs.getString("genre"),
            rs.getInt("noPages")
        );
    }
    
}
