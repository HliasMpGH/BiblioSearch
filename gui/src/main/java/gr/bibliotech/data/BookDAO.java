package gr.bibliotech.data;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import gr.bibliotech.app.Book;

@Repository
public class BookDAO {

    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

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

    /**
     * Filters a list of books by a specified filter
     * and returns the result
     * @param books a list of books to filter
     * @param filter the filter to choose books by
     * @return a list of books that match the filter
     */
    public List<Book> filterBooks(List<Book> books, String filter) {
        List<Book> filteredBooks = new ArrayList<>();

        // the pattern to search by
        Pattern pattern = Pattern.compile(filter, Pattern.CASE_INSENSITIVE | Pattern.LITERAL);

        for (Book book : books) {
            // try to match the pattern in the description and genre of Book
            Matcher descriptionString = pattern.matcher(book.getDescription());
            Matcher genreSting = pattern.matcher(book.getGenre());

            if (descriptionString.find() || genreSting.find()) {
                filteredBooks.add(book); // the book matches the filter
            }
        }
        return filteredBooks;
    }

    /**
     * Retrieves the books that match the query and genre
     * @param query the query issued
     * @param genre the genre specified
     * @return a list of books
     */
    public List<Book> findBooks(String query, String genre) {
        List<Book> books = (!query.trim().isEmpty() ? getMatchingBooks(query) : getBooks());

        if (!genre.equals("any")) {
            books = filterBooks(books, genre);
        }

        return books;
    }
}
