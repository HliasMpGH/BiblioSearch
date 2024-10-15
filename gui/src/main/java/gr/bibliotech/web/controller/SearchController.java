package gr.bibliotech.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import gr.bibliotech.app.Book;
import gr.bibliotech.data.BookGenre;
import gr.bibliotech.data.BookService;

/**
 * The Controller that Handles
 * All the Requests Regarding
 * Book Needs.
 */
@Controller
@ComponentScan("gr.bibliotech")
@RequestMapping("books")
public class SearchController {

    private BookService bookService;

    @Autowired
    public SearchController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * The Main Search Page.
     */
    @GetMapping("")
    public String showSearchPage() {
        return "bookSearch.html";
    }

    /**
     * Handles GET requests upon user search
     * interaction. Presents results of the
     * requests.
     */
    @GetMapping("/search")
    public String showResults(@RequestParam("query") String query,
                              @RequestParam("genre") String genre,
                              Model model) {

        // get the books that match the specified criteria
        List<Book> matchingBooks = bookService.findBooks(query, BookGenre.valueOf(genre));

        // present the results, or an appropriate message in case of no results
        if (matchingBooks.size() != 0) {
            model.addAttribute("books", matchingBooks);
        } else {
            model.addAttribute(
             "message",
             "No Books that Match the Criteria Found!"
            );
        }

        return "bookSearch.html";
    }
}
