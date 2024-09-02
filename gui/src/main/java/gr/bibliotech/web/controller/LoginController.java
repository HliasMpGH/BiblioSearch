package gr.bibliotech.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import gr.bibliotech.error.InvalidInfoException;
import gr.bibliotech.data.UserDAO;

/**
 * The Controller that Handles
 * All the Requests Regarding
 * User Needs Such as Authentication.
 */
@Controller
@ComponentScan("gr.bibliotech")
public class LoginController {

    private UserDAO userDAO;

    @Autowired
    public LoginController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

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
     * Validates the info of a user
     * when a login takes place
     */
    @PostMapping("login/validate")
    public String authenticateLogin(@RequestParam("username") String username,
                              @RequestParam("password") String password,
                              Model model) {

        // validate users' credentials
        try {
            userDAO.authenticate(username, password);

            // valid credentials; redirect to search
            return "redirect:/books";
        } catch (Exception e) {

            // no user found with these credentials
            model.addAttribute("message", e.getMessage());
            return "login.html";
        }
    }

    /**
     * Validates the info of a user
     * when a new registration takes place
     */
    @PostMapping("register/validate")
    public String authenticateRegister(@RequestParam("username") String username,
                              @RequestParam("password") String password,
                              @RequestParam("email") String email,
                              Model model) {

        try {
            /* validate users' credentials */

            // username
            userDAO.validateUsername(username);

            // password
            userDAO.validatePassword(password);

            // email
            userDAO.validateEmail(email);

            /* valid credentials */

            // register the user
            userDAO.registerUser(username, email, password);

            // redirect to login
            return "redirect:/login";
        } catch (InvalidInfoException e) {
            model.addAttribute("message", e.getMessage());

            // invalid info; redirect to register
            return "register.html";
        }
    }
}
