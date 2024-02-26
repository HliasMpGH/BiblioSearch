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
import gr.bibliotech.data.UserDAO;

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

        UserDAO userBean = Server.getInstance().getBean(UserDAO.class);

        // validate users' credentials
        try {
            userBean.authenticate(username, password);
            return "bookSearch.html";
        } catch (Exception e) {

            // no user found with these credentials
            model.addAttribute("message", e.getMessage());
            return "login.html";
        }
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

        UserDAO userBean = Server.getInstance().getBean(UserDAO.class);

        // validate users' credentials

        if (!userBean.isValidUserName(username)) {
            model.addAttribute("message",
                "Invalid Username. Usernames Must be Longer than 8 Characters!");

            return "register.html";
        }

        if (!userBean.isValidPassword(password)) {
            model.addAttribute("message",
                "Weak Password. Passwords Must Obey the Following rules:<br>" +
                "must have two uppercase letters<br>" +
                "must have one special case letter<br>" +
                "must have two digits<br>" +
                "must have three lowercase letters<br>" +
                "is of length 8"
            );

            return "register.html";
        }

        // user is verified; show the login page
        return "login.html";
    }
}
