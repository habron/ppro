package cz.habrondrej.projekt.controllers;


import cz.habrondrej.projekt.db.*;
import cz.habrondrej.projekt.model.Customer;
import cz.habrondrej.projekt.model.Employee;
import cz.habrondrej.projekt.model.User;
import cz.habrondrej.projekt.model.utils.*;
import cz.habrondrej.projekt.utils.FlashMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Controller
public class HomepageController {

    private List<FlashMessage> flashes;

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private SubscriptionService subscriptionService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private EventService eventService;

    @GetMapping("/")
    public String renderDefault(Model model) {

        model.addAttribute("events", eventService.getView());
        model.addAttribute("title", "Homepage");

        return "homepage/default";
    }

    @GetMapping("/profil")
    public String renderProfile(Model model) {

        User loggedUser = getLoggedUser();
        User user;

        if (getLoggedUser().getRole().getRole().equals("ROLE_CUSTOMER")) {
            user = customerService.findById(loggedUser.getId());
            model.addAttribute("userModel", new Customer());
        } else {
            user = employeeService.findById(loggedUser.getId());
            model.addAttribute("userModel", new Employee());
        }
        user.setAddress(addressService.findById(user.getAddressId()));
        user.setLogin(loginService.findById(user.getLoginId()));
        model.addAttribute("user", user);
        model.addAttribute("imageModel", new Image());
        model.addAttribute("subscriptions", subscriptionService.getAll());
        model.addAttribute("subscribeModel", new Subscribe());
        model.addAttribute("title", "Profil");

        return "homepage/profile";
    }

    @PostMapping("/profil")
    public String renderProfile(@ModelAttribute("userModel") User user, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "homepage/profile";
        }

        User loggedUser = getLoggedUser();
        flashes = new ArrayList<>();

        try {

            user.setRole(loggedUser.getRole());
            user.setId(loggedUser.getId());

            if (getLoggedUser().getRole().getRole().equals("ROLE_CUSTOMER")) {
                customerService.update(user);
            } else {
                if (user.getPersonalIdentificationNumber().isEmpty()) {
                    user.setPersonalIdentificationNumber(loggedUser.getPersonalIdentificationNumber());
                }
                if (user.getPhone().isEmpty()) {
                    user.setPhone(loggedUser.getPhone());
                }

                employeeService.update(user);
            }
            loginService.updateUsername(new Login(user.getLoginId(), user.getLogin().getUsername(), user.getLogin().getPassword()));
            addressService.update(new Address(user.getAddressId(), user.getAddress().getCity(), user.getAddress().getCountry(),
                    user.getAddress().getStreet(), user.getAddress().getZip()));

            flashes.add(new FlashMessage(FlashMessage.TYPE_SUCCESS, "Uloženo"));
        } catch (Exception e) {
            e.printStackTrace();
            flashes.add(new FlashMessage(FlashMessage.TYPE_DANGER, "Chyba databáze"));
        }

        User user1;

        if (getLoggedUser().getRole().getRole().equals("ROLE_CUSTOMER")) {
            user1 = customerService.findById(loggedUser.getId());
            model.addAttribute("userModel", new Customer());
        } else {
           user1 = employeeService.findById(loggedUser.getId());
            model.addAttribute("userModel", new Employee());
        }
        user1.setAddress(addressService.findById(user.getAddressId()));
        user1.setLogin(loginService.findById(user.getLoginId()));

        model.addAttribute("user", user1);
        model.addAttribute("subscriptions", subscriptionService.getAll());
        model.addAttribute("subscribeModel", new Subscribe());
        model.addAttribute("imageModel", new Image());
        model.addAttribute("title", "Profil");
        model.addAttribute("flashes", flashes);

        return "homepage/profile";
    }

    @PostMapping("/profil/predplatne")
    public String actionSubscribe(@ModelAttribute("subscribeModel")Subscribe subscribe, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "redirect:/profil";
        }

        try {
            subscribe.setCustomerID(getLoggedUser().getId());
            customerService.insertSubscribe(subscribe);
        } catch (Exception e) {

        }

        return "redirect:/profil";
    }

    @PostMapping("/profil/obrazek")
    public String actionUploadImage(@ModelAttribute("imageModel") Image image, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "redirect:/profil";
        }

        image.setName(image.getFile().getName());
        image.setType(image.getFile().getContentType());
        image.setUserID(getLoggedUser().getId());
        try {
            image.setImageData(image.getFile().getBytes());
            imageService.insert(image);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/profil";
    }

    @GetMapping("/profil/download")
    public ResponseEntity<byte[]> downloadImage() throws IOException {

        if (!getLoggedUser().getRole().getRole().equals("ROLE_CUSTOMER")) {
            return null;
        }

        final Image image = imageService.findById(getLoggedUser().getId());

        String type = image.getType().substring(image.getType().lastIndexOf("/") + 1);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(image.getType()));
        headers.setContentDispositionFormData("attachment", image.getName() + "." + type);

        return new ResponseEntity<>(image.getImageData(), headers, HttpStatus.OK);

    }

    private User getLoggedUser() {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        User loggedInUser = ((PdfUserDetails) authentication.getPrincipal()).getUserDetails();
        return loggedInUser;
    }
}
