package gr.bibliotech.web;

import java.util.List;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import gr.bibliotech.app.Book;
import gr.bibliotech.data.BookDAO;

@Controller
@ComponentScan("gr.bibliotech")
@RequestMapping("details")
public class LoginController {

    /**
     * The Login Page.
     */
    @GetMapping("login")
    public String showLoginPage() {
        return "login.html";
    }

    /**
     * The Register Page.
     */
    @GetMapping("register")
    public String showRegisterPage() {
        return "register.html";
    }

    /**
     * Configures GET requests upon user search
     * interaction. Presents results of the
     * requests.
     */
    @PostMapping("login/validate")
    public String showResults(@RequestParam("username") String username,
                              @RequestParam("password") String password,
                              Model model) {

        // validate users' credentials
        return "bookSearch.html";
    }

    /**
     * Configures GET requests upon user search
     * interaction. Presents results of the
     * requests.
     */
    @PostMapping("register/validate")
    public String showResults(@RequestParam("username") String username,
                              @RequestParam("password") String password,
                              @RequestParam("email") String email,
                              Model model) {

        // validate users' credentials
        return "login.html";
    }
}
