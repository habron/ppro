package cz.habrondrej.projekt.controllers;

import cz.habrondrej.projekt.db.CustomerService;
import cz.habrondrej.projekt.model.Customer;
import cz.habrondrej.projekt.model.User;
import cz.habrondrej.projekt.model.utils.PdfUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;


@SessionAttributes({"currentUser"})
@Controller
public class LoginController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    public BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("customer", new Customer());

        return "login/register";
    }

    @PostMapping("/register")
    public String renderNew(@ModelAttribute("customer") Customer customer, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "login/registerFailed";
        }

        customer.getLogin().setPassword(passwordEncoder.encode(customer.getLogin().getPassword()));

        try {
            customerService.insert(customer);

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/registerFailed";
        }


        return "redirect:/login";
    }

    @GetMapping("/registerFailed")
    public String registerError(Model model) {
        model.addAttribute("error", true);
        model.addAttribute("customer", new Customer());

        return "login/register";
    }

    @GetMapping("/login")
    public String login() {
        return "login/login";
    }

    @PostMapping("/login")
    public String login(Model model, HttpSession session) {

        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        validatePrinciple(authentication.getPrincipal());
        User loggedInUser = ((PdfUserDetails) authentication.getPrincipal()).getUserDetails();

        model.addAttribute("currentUser", loggedInUser.getLogin().getUsername());
        session.setAttribute("userId", loggedInUser.getId());
        return "redirect:/";
    }

   @GetMapping("/loginFailed")
   public String loginError(Model model) {
       model.addAttribute("error", true);

       return "login/login";
   }

   @GetMapping("/logout")
   public String logout(SessionStatus session) {
       SecurityContextHolder.getContext().setAuthentication(null);
       session.setComplete();

       return "redirect:/";
   }

    private void validatePrinciple(Object principal) {
        if (!(principal instanceof PdfUserDetails)) {
            throw new  IllegalArgumentException("Principal can not be null!");
        }
    }

}
