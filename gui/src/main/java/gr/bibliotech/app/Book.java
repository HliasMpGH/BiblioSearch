package gr.bibliotech.app;

/**
 * 
 * Book Class.
 * Represents a Book.
 * 
 */
public class Book {

    private String ISBN;
    private String title;
    private String author;
    private String description;
    private String cover;
    private String genre;
    private int noPages;

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
    public Book(String ISBN, String title, String author, String description, String cover, String genre, int noPages){
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
        this.description = description;
        this.cover = cover;
        this.genre = genre;
        this.noPages = noPages;
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

    /*
     * No Setters Allowed.
     */
}
