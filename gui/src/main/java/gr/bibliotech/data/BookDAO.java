package gr.bibliotech.data;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
     * Retrives the Registered Books from the Data Base
     * @return a list of books
     */
    public List<Book> getBooks() {
        List<Book> books = jdbcTemplate.query("SELECT * FROM BOOKS", new BookMapper());

        return books;
    }

    /**
     * Returns the Books that Match a Search Term (title or author)
     * @param term the term to search by
     * @return a list of books that match the term
     */
    public List<Book> getMatchingBooks(String term) {
        List<Book> matchingBooks = new ArrayList<>();

        // the pattern to search by
        Pattern pattern = Pattern.compile(term, Pattern.CASE_INSENSITIVE | Pattern.LITERAL);

        for (Book book : getBooks()) {
            // try to match the pattern in the title and author of Book
            Matcher titleString = pattern.matcher(book.getTitle());
            Matcher authorSting = pattern.matcher(book.getAuthor());

            if (titleString.find() || authorSting.find()) {
                matchingBooks.add(book); // the book matches the search
            }
        }
        return matchingBooks;
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

    public List<Book> findBooks(String query, String filter, String genre) {
        List<Book> books = (!query.trim().isEmpty() ? getMatchingBooks(query) : getBooks());

        if (!genre.equals("any")) {
            books = filterBooks(books, genre);
        }

        return books;
    }
}
