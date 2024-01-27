package gr.bibliotech;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;


@Controller
@ComponentScan("gr.bibliotech")
@RequestMapping("books")
public class HttpController {

    @GetMapping("")
    public String showSearchPage() {
        return "bookSearch.html";
    }

    @GetMapping("/search")
    public String showResults(@RequestParam("query") String query,
                              @RequestParam("filter") String filter,
                              @RequestParam("genre") String genre,
                              Model model) {

        return "bookSearch.html";
    }

    @PostMapping("")
    public String handlePOST(@RequestBody String body) {
        return "the body is " + body;
    }
}
