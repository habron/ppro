package cz.habrondrej.projekt.controllers;

import cz.habrondrej.projekt.db.AddressService;
import cz.habrondrej.projekt.db.EmployeeService;
import cz.habrondrej.projekt.db.LoginService;
import cz.habrondrej.projekt.db.RoleService;
import cz.habrondrej.projekt.model.Employee;
import cz.habrondrej.projekt.model.User;
import cz.habrondrej.projekt.model.utils.Address;
import cz.habrondrej.projekt.model.utils.Login;
import cz.habrondrej.projekt.model.utils.PdfUserDetails;
import cz.habrondrej.projekt.model.utils.Role;
import cz.habrondrej.projekt.utils.FlashMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class EmployeeController {

    private List<FlashMessage> flashes;

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private RoleService roleService;

    @Autowired
    public BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/zamestnanci")
    public String renderDefault(Model model) {

        List<Employee> employees = employeeService.getAll();

        model.addAttribute("employees", employees);
        model.addAttribute("employeeModel", new Employee());
        model.addAttribute("title", "Zaměstnanci");

        return "employees/default";
    }

    @PostMapping("/zamestnanci")
    public String renderDefault(@ModelAttribute("employeeModel") Employee employee, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "employees/default";
        }

        flashes = new ArrayList<>();

        if (!getLoggedUser().getRole().getRole().equals("ROLE_ADMIN")) {
            flashes.add(new FlashMessage(FlashMessage.TYPE_WARNING, "Nemáte oprávnění"));
        } else {

            try {
                employeeService.delete(employee);
                flashes.add(new FlashMessage(FlashMessage.TYPE_SUCCESS, "Odstraněno"));
            } catch (Exception e) {
                flashes.add(new FlashMessage(FlashMessage.TYPE_DANGER, "Chyba databáze"));
            }
        }

        List<Employee> employees = employeeService.getAll();

        model.addAttribute("flashes", flashes);
        model.addAttribute("employeeModel", new Employee());
        model.addAttribute("employees", employees);
        model.addAttribute("title", "Zaměstnanci");

        return "employees/default";
    }

    @GetMapping("/zamestnanci/pridat")
    public String renderNew(Model model) {

        Map<Integer, String> roles = getRoles();

        model.addAttribute("employee", new Employee());
        model.addAttribute("roles", roles);
        model.addAttribute("title", "Zaměstnanci");
        model.addAttribute("isAction", true);

        return "employees/new";
    }

    @PostMapping("/zamestnanci/pridat")
    public String renderNew(@ModelAttribute("employee") Employee employee, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "employees/new";
        }

        flashes = new ArrayList<>();

        employee.getLogin().setPassword(passwordEncoder.encode(employee.getLogin().getPassword()));

        try {
            employeeService.insert(employee);
            flashes.add(new FlashMessage(FlashMessage.TYPE_SUCCESS, "Vytvořeno"));
        } catch (Exception e) {
            flashes.add(new FlashMessage(FlashMessage.TYPE_DANGER, "Chyba databáze"));
        }

        Map<Integer, String> roles = getRoles();

        model.addAttribute("roles", roles);
        model.addAttribute("flashes", flashes);
        model.addAttribute("employee", new Employee());
        model.addAttribute("title", "Zaměstnanci");
        model.addAttribute("isAction", true);

        return "employees/new";
    }

    @GetMapping("/zamestnanci/upravit/{id}")
    public String renderEdit(@PathVariable("id") int id, Model model) {

        Employee employee = employeeService.findById(id);

        employee.setAddress(addressService.findById(employee.getAddressId()));
        employee.setLogin(loginService.findById(employee.getLoginId()));

        Map<Integer, String> roles = getRoles();

        model.addAttribute("roles", roles);
        model.addAttribute("employee", employee);
        model.addAttribute("employeeModel", new Employee());
        model.addAttribute("title", "Zaměstnanci");
        model.addAttribute("isAction", true);
        model.addAttribute("isParam", true);

        return "employees/edit";
    }

    @PostMapping("/zamestnanci/upravit/{id}")
    public String renderEdit(@PathVariable("id") int id, @ModelAttribute("employeeModel") Employee employee, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "employees/edit";
        }

        flashes = new ArrayList<>();

        Employee employee1 = employeeService.findById(employee.getId());

        if (getLoggedUser().getRole().getRole().equals("ROLE_MANAGER") && employee1.getRole().getRole().equals("ROLE_INSTRUCTOR") ||
            getLoggedUser().getRole().getRole().equals("ROLE_ADMIN")) {

            try {
                employeeService.update(employee);
                loginService.updateUsername(new Login(employee.getLoginId(), employee.getLogin().getUsername(), employee.getLogin().getPassword()));
                addressService.update(new Address(employee.getAddressId(), employee.getAddress().getCity(), employee.getAddress().getCountry(),
                        employee.getAddress().getStreet(), employee.getAddress().getZip()));
                flashes.add(new FlashMessage(FlashMessage.TYPE_SUCCESS, "Uloženo"));
            } catch (Exception e) {
                flashes.add(new FlashMessage(FlashMessage.TYPE_DANGER, "Chyba databáze"));
            }
        } else {
            flashes.add(new FlashMessage(FlashMessage.TYPE_WARNING, "Nemáte oprávnění"));
        }

        employee = employeeService.findById(id);

        employee.setAddress(addressService.findById(employee.getAddressId()));
        employee.setLogin(loginService.findById(employee.getLoginId()));

        Map<Integer, String> roles = getRoles();

        model.addAttribute("roles", roles);
        model.addAttribute("flashes", flashes);
        model.addAttribute("employee", employee);
        model.addAttribute("title", "Zaměstnanci");
        model.addAttribute("isAction", true);
        model.addAttribute("isParam", true);

        return "employees/edit";
    }

    private LinkedHashMap<Integer, String> getRoles() {

        List<Role> roles = roleService.getAll(getLoggedUser().getRole().getRole());
        Map<Integer,String> rolesId = new LinkedHashMap<>();

        for (Role role : roles) {
            rolesId.put(role.getId(), role.getName());
        }

        return (LinkedHashMap<Integer, String>) rolesId;
    }

    private User getLoggedUser() {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        User loggedInUser = ((PdfUserDetails) authentication.getPrincipal()).getUserDetails();
        return loggedInUser;
    }
}
