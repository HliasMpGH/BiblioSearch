package gr.bibliotech;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Book Class.
 * Represents a Book.
 * 
 * Used to Handle Operations and Transactions
 * that Concern the platforms' Books.
 * 
 * 
 * @version 1.0
 * @author Dimitris Papathanasiou, Hlias Mpourdakos
 */
public class Book {

    private String ISBN;
    private String title;
    private String author;
    private String description;
    private String cover;
    private String genre;
    private int noPages;
    private int noCopies;

    // copy types available for each book (only one per instance)
    public static final String PHYSICAL = "physical";
    public static final String ONLINE = "online";

    /**
     * used to handle the connection for the DB transactions.
     * should be initialized once per instance of class
     */
    private Connection con;

    /**
     * 1st constructor - used to create new Books.
     * 
     * @param ISBN
     * @param title
     * @param author
     * @param description
     * @param cover
     * @param genre
     * @param noPages
     */
    public Book(String ISBN, String title, String author, String description, String cover, String genre, int noPages) {
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
        this.description = description;
        this.cover = cover;
        this.genre = genre;
        this.noPages = noPages;
        con = DataBase.getNewConnection();
        insert();
        this.noCopies = queryCopies();
        DataBase.close(con);
    }

    /**
     * 2nd constructor - used to shell already existing Books.
     * 
     * @param ISBN
     * @param title
     * @param author
     * @param description
     * @param cover
     * @param genre
     * @param noPages
     * @param noCopies
     */
    public Book(String ISBN, String title, String author, String description, String cover, String genre, int noPages, int noCopies){
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
        this.description = description;
        this.cover = cover;
        this.genre = genre;
        this.noPages = noPages;
        this.noCopies = noCopies;
    }

    /**
     * Inserts a Books' Data into the Data Base.
     * This Data Should be Passed to the Object Upon Instantiation.
     * Rest Attributes are Calculated in the db Core.
     *
     */
    private void insert() {
        String insert = "INSERT INTO book(ISBN, title, author, description, coverURL, genre, noPages) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stm = con.prepareStatement(insert)) {
            stm.setString(1, ISBN);
            stm.setString(2, title);
            stm.setString(3, author);
            stm.setString(4, description);
            stm.setString(5, cover);
            stm.setString(6, genre);
            stm.setInt(7, noPages);
            stm.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("An Error Occured During Preparation of Data");
        }
    }

    /**
     * retrieves the number of copies of this book from the db
     * @return number of copies of this book that the database containts
     */
    private int queryCopies() {
        String query = "SELECT noCopies " +
                        "FROM book " +
                        "WHERE ISBN = ?";

        try (PreparedStatement stm = con.prepareStatement(query)) {
            stm.setString(1, this.getISBN());
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("noCopies");
            } else {
                System.err.println("No Books with this isbn found");
            }
            rs.close();
        } catch (Exception e) {
            throw new RuntimeException("An Error Occured while Collecting Data");
        }
        return -1;
    }

    /*
     * Getters
     */

    public String getISBN() {
        return ISBN;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public String getCover() {
        return cover;
    }

    public String getGenre() {
        return genre;
    }

    public int getNoPages() {
        return noPages;
    }

    public int getNoCopies() {
        return noCopies;
    }

    /*
     * No Setters Allowed.
     */



    /*
     * Static Methods, Used for Global Operations 
     */

    /**
     * Retrives the Registered Books from the Data Base
     * @return a list of books
     * @throws Exception if the DB connection experiences any issues
     */
    public static List<Book> getBooks() {
        List<Book> books = new ArrayList<>();

        String query = "SELECT * FROM BOOKS";

        Connection con = DataBase.getNewConnection();
        try (PreparedStatement stm = con.prepareStatement(query)) {
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                books.add(new Book(
                    rs.getString("ISBN"),
                    rs.getString("title"), 
                    rs.getString("author"), 
                    rs.getString("description"),
                    rs.getString("coverURL"),
                    rs.getString("genre"), 
                    rs.getInt("noPages"), 
                    rs.getInt("noCopies"))
                );
            }
            rs.close();
        } catch (Exception e) {
            throw new RuntimeException("An Error Occured while Collecting Data");
        } finally {
            DataBase.close(con);
        }
        return books;
    }

    /**
     * Returns the book with Said ISBN.
     * Useful for Associations Between Data
     * @param isbn the isbn of the book to return
     * @return the book with Said id
     * @throws Exception if a match between a book and the isbn 
     * could not be resolved
     */
    public static Book getBook(String isbn) {

        for (Book book : getBooks()) {
            if (book.getISBN().equals(isbn)) {
                return book;
            }
        }
        throw new RuntimeException("No Book Found that Resolved to the Requested ISBN");
    }

    /**
     * Returns the Books that Match a Search Term (title or author)
     * @param term the term to search by
     * @return a list of books that match the term
     */
    public static List<Book> getMatchingBooks(String term) {
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
    public static List<Book> filterBooks(List<Book> books, String filter) {
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

    public static List<Book> getMostPopularBooks() {
        List<Book> popularBooks = new ArrayList<>();

        String query = "select * from book where genre = " +
                        "(select genre from book " +
                        "where isbn in " +
                        "(select toBookISBN from lookingToexchange) " +
                        "group by genre " +
                        "order by count(*) " +
                        "LIMIT 1)";

        Connection con = DataBase.getNewConnection();
        try (PreparedStatement stm = con.prepareStatement(query)) {
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                popularBooks.add(new Book(
                    rs.getString("ISBN"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("description"),
                    rs.getString("coverURL"),
                    rs.getString("genre"),
                    rs.getInt("noPages"),
                    rs.getInt("noCopies"))
                );
            }

            return popularBooks;
        } catch (Exception e) {
            throw new RuntimeException("An Error Occured while Collecting Data");
        } finally {
            DataBase.close(con);
        }

    }

    /**
     * Checks if an addition of a books is acceptable.
     * A book addition is considered acceptable if it has not
     * been made again by the same user in the same format.
     * @param user the user that tried to sumbit the request
     * @param book the book the user wishes to add to his collection
     * @param type the type of format of said book
     * @throws Exception if the request cannot be sumbitted
     * or the DB connection experiences any issues

    public static void checkBookAddition(User user, Book book, String type) {
        int bookCounts;

        String query = "SELECT COUNT(*) as bookcount FROM hasbook " +
                        "WHERE idUser = ? AND ISBN = ? AND copyType = ?";

        Connection con = DataBase.getNewConnection();
        try (PreparedStatement stm = con.prepareStatement(query)) {
            stm.setInt(1, user.getID());
            stm.setString(2, book.getISBN());
            stm.setString(3, type);
            ResultSet rs = stm.executeQuery();
            rs.next();
            bookCounts = rs.getInt("bookcount");
        } catch (Exception e) {
           throw new RuntimeException("An Error Occured while Collecting Data");
        } finally {
            DataBase.close(con);
        }
    
        if (bookCounts != 0) {
            throw new RuntimeException(book.getTitle() + " Already Exists in your Collection in " + type + " Format!");
        }
    }
*/
    /**
     * Checks if a book of a user is locked.
     * A book is locked when the user has put said
     * book as an offer in one of his requests.
     * @param user the user that owns the book
     * @param book the book the user wishes to remove from his collection
     * @param type the type of format of said book
     * @throws Exception if the request cannot be sumbitted
     * or the DB connection experiences any issues

    public static void checkBookLock(User user, Book book, String type) {
        int bookLocks;

        String query = "SELECT COUNT(*) as lockcount FROM lookingtoexchange as LT " +
                        "WHERE idUser = ? AND fromBookISBN = ? AND fbcopyType = ? " +
                        "AND (SELECT COUNT(*) FROM exchangeActions WHERE idRequest = LT.idRequest " +
                        "AND actn = true) = 0";

        Connection con = DataBase.getNewConnection();
        try (PreparedStatement stm = con.prepareStatement(query)) {
            stm.setInt(1, user.getID());
            stm.setString(2, book.getISBN());
            stm.setString(3, type);
            ResultSet rs = stm.executeQuery();
            rs.next();
            bookLocks = rs.getInt("lockcount");
        } catch (Exception e) {
           throw new RuntimeException("An Error Occured while Collecting Data");
        } finally {
            DataBase.close(con);
        }
    
        if (bookLocks != 0) {
            throw new RuntimeException(book.getTitle() + " Exists in an Offer of yours in " + type + " Format. You Can't Exchange it!");
        }
    }
*/

    public static List<Book> findBooks(String query, String filter, String genre) {
        List<Book> books = (!query.trim().isEmpty() ? getMatchingBooks(query) : getBooks());

        if (!genre.equals("any")) {
            books = filterBooks(books, genre);
        }

        return books;
    }

    //addBook should be added on profile jsp page
}
