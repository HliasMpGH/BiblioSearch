package gr.bibliotech;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@ComponentScan("gr.bibliotech")
@RequestMapping("books")
public class Controller {

    @GetMapping("/search")
    public String showSearchPage(@RequestParam("query") String query) {

        return "bookSearch.html";
    }

    @PostMapping("")
    public String handlePOST(@RequestBody String body) {
        return "the body is " + body;
    }
}
