package gr.bibliotech.web;

import java.util.List;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import gr.bibliotech.ErrorHandlers.InvalidInfoException;
import gr.bibliotech.app.Book;
import gr.bibliotech.data.BookDAO;
import gr.bibliotech.data.UserDAO;

@Controller
@ComponentScan("gr.bibliotech")
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

            // valid credentials; redirect to search
            return "redirect:/books";
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
        try {
            // username
            userBean.validateUsername(username);

            // password
            userBean.validatePassword(password);

            // email
            userBean.validateEmail(email);
        } catch (InvalidInfoException e) {
            model.addAttribute("message", e.getMessage());

            // invalid info; redirect to register
            return "register.html";
        }

        // valid credentials

        // register the user
        userBean.registerUser(username, email, password);

        // redirect to login
        return "redirect:/login";
    }
}
