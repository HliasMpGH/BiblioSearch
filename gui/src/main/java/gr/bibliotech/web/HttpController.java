package gr.bibliotech.web;

import java.util.List;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import gr.bibliotech.app.Book;
import gr.bibliotech.data.BookDAO;

@Controller
@ComponentScan("gr.bibliotech")
@RequestMapping("books")
public class HttpController {

    /**
     * The Main Search Page.
     */
    @GetMapping("")
    public String showSearchPage() {
        return "bookSearch.html";
    }

    /**
     * Configures GET requests upon user search
     * interaction. Presents results of the
     * requests.
     */
    @GetMapping("/search")
    public String showResults(@RequestParam("query") String query,
                              @RequestParam("filter") String filter,
                              @RequestParam("genre") String genre,
                              Model model) {

        BookDAO bookBean = Server.getInstance().getBean(BookDAO.class);

        // get the books that match the specified criteria
        List<Book> matchingBooks = bookBean.findBooks(query, filter, genre);


        // present the results, or an appropriate message in case of no results
        if (matchingBooks.size() != 0) {
            model.addAttribute("books", matchingBooks);
        } else {
            model.addAttribute("message", "No Books that Match the Criteria Found!");
        }

        return "bookSearch.html";
    }
}
