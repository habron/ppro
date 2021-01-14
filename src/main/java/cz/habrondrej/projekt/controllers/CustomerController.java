package cz.habrondrej.projekt.controllers;

import cz.habrondrej.projekt.db.AddressService;
import cz.habrondrej.projekt.db.CustomerService;
import cz.habrondrej.projekt.db.LoginService;
import cz.habrondrej.projekt.model.Customer;
import cz.habrondrej.projekt.model.utils.Address;
import cz.habrondrej.projekt.model.utils.Login;
import cz.habrondrej.projekt.utils.FlashMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CustomerController {

    private List<FlashMessage> flashes;

    @Autowired
    private CustomerService customerService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private LoginService loginService;

    @Autowired
    public BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/zakaznici")
    public String renderDefault(Model model) {

        List<Customer> customers = customerService.getAll();

        model.addAttribute("customers", customers);
        model.addAttribute("customerModel", new Customer());
        model.addAttribute("title", "Zákazníci");

        return "customers/default";
    }

    @PostMapping("/zakaznici")
    public String renderDefault(@ModelAttribute("customerModel") Customer customer, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "customers/default";
        }

        flashes = new ArrayList<>();

        try {
            customerService.delete(customer);
            flashes.add(new FlashMessage(FlashMessage.TYPE_SUCCESS, "Odstraněno"));
        } catch (Exception e) {
            flashes.add(new FlashMessage(FlashMessage.TYPE_DANGER, "Chyba databáze"));
        }

        List<Customer> customers = customerService.getAll();

        model.addAttribute("flashes", flashes);
        model.addAttribute("customerModel", new Customer());
        model.addAttribute("customer", customers);
        model.addAttribute("title", "Zákazníci");

        return "customers/default";
    }

    @GetMapping("/zakaznici/pridat")
    public String renderNew(Model model) {

        model.addAttribute("customer", new Customer());
        model.addAttribute("title", "Zákazníci");
        model.addAttribute("isAction", true);

        return "customers/new";
    }

    @PostMapping("/zakaznici/pridat")
    public String renderNew(@ModelAttribute("customer") Customer customer, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "customers/new";
        }

        flashes = new ArrayList<>();

        customer.getLogin().setPassword(passwordEncoder.encode(customer.getLogin().getPassword()));

        try {
            customerService.insert(customer);
            flashes.add(new FlashMessage(FlashMessage.TYPE_SUCCESS, "Vytvořeno"));
        } catch (Exception e) {
            flashes.add(new FlashMessage(FlashMessage.TYPE_DANGER, "Chyba databáze"));
        }

        model.addAttribute("flashes", flashes);
        model.addAttribute("customer", new Customer());
        model.addAttribute("title", "Zákazníci");
        model.addAttribute("isAction", true);

        return "customers/new";
    }

    @GetMapping("/zakaznici/upravit/{id}")
    public String renderEdit(@PathVariable("id") int id, Model model) {

        Customer customer = customerService.findById(id);

        customer.setAddress(addressService.findById(customer.getAddressId()));
        customer.setLogin(loginService.findById(customer.getLoginId()));

        model.addAttribute("customer", customer);
        model.addAttribute("customerModel", new Customer());
        model.addAttribute("title", "Zákazníci");
        model.addAttribute("isAction", true);
        model.addAttribute("isParam", true);

        return "customers/edit";
    }

    @PostMapping("/zakaznici/upravit/{id}")
    public String renderEdit(@PathVariable("id") int id, @ModelAttribute("customerModel") Customer customer, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "customers/edit";
        }

        flashes = new ArrayList<>();

        try {
            customerService.update(customer);
            loginService.updateUsername(new Login(customer.getLoginId(), customer.getLogin().getUsername(), customer.getLogin().getPassword()));
            addressService.update(new Address(customer.getAddressId(), customer.getAddress().getCity(), customer.getAddress().getCountry(),
                    customer.getAddress().getStreet(), customer.getAddress().getZip()));
            flashes.add(new FlashMessage(FlashMessage.TYPE_SUCCESS, "Uloženo"));
        } catch (Exception e) {
            flashes.add(new FlashMessage(FlashMessage.TYPE_DANGER, "Chyba databáze"));
        }

        customer = customerService.findById(id);

        customer.setAddress(addressService.findById(customer.getAddressId()));
        customer.setLogin(loginService.findById(customer.getLoginId()));

        model.addAttribute("flashes", flashes);
        model.addAttribute("customer", customer);
        model.addAttribute("title", "Zákazníci");
        model.addAttribute("isAction", true);
        model.addAttribute("isParam", true);

        return "customers/edit";
    }

}
