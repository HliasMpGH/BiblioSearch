package gr.bibliotech.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import gr.bibliotech.app.Book;

/**
 * JPA implementation of Book.
 */
public class BookMapper implements RowMapper<Book> {

    /**
     * Maps the book table data to class data.
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
