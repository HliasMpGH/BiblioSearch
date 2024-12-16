package gr.bibliotech.data;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gr.bibliotech.app.Book;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
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
    public List<Book> findBooks(String query, BookGenre genre) {
        List<Book> books = (
            !query.trim().isEmpty()
            ? bookRepository.getMatchingBooks(query)
            : bookRepository.getBooks()
        );

        if (genre != BookGenre.ANY) {
            books = filterBooks(books, genre.toString());
        }

        return books;
    }
}
