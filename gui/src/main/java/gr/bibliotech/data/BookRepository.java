package gr.bibliotech.data;

import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import gr.bibliotech.app.Book;

@Repository
public class BookRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Retrieves the Registered Books from the Data Base
     * @return a list of books
     */
    public List<Book> getBooks() {
        return jdbcTemplate.query(
            "SELECT * FROM BOOKS", new BookMapper()
        );
    }

    /**
     * Returns the Books that Match a Search Term (title or author)
     * @param term the term to search by
     * @return a list of books that match the term
     */
    public List<Book> getMatchingBooks(String term) {
        String query = ""
        + " SELECT * FROM BOOKS"
        + " WHERE LOWER(TITLE)"
        + " LIKE LOWER(?)"
        + " OR LOWER(AUTHOR)"
        + " LIKE LOWER(?)";

        String matchTerm = "%" + term + "%";

        return jdbcTemplate.query(
            query,
            new Object[] {matchTerm, matchTerm}, // the parameters to bind
            new int[] {Types.VARCHAR, Types.VARCHAR}, // the sql types of the parameters
            new BookMapper() // the record mapper
        );
    }
}
