package cz.habrondrej.projekt.controllers;

import cz.habrondrej.projekt.db.CustomerService;
import cz.habrondrej.projekt.db.LockerService;
import cz.habrondrej.projekt.model.Locker;
import cz.habrondrej.projekt.model.utils.LockerHistory;
import cz.habrondrej.projekt.utils.FlashMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class LockersRoomController {

    private List<FlashMessage> flashes;

    @Autowired
    private LockerService lockerService;
    @Autowired
    private CustomerService customerService;

    @GetMapping("/satna")
    public String renderDefault(Model model) {

        List<Locker> lockers = lockerService.getAll();

        model.addAttribute("lockerModel", new Locker());
        model.addAttribute("lockers", lockers);
        model.addAttribute("title", "Šatna");

        return "lockerRoom/default";
    }

    @PostMapping("/satna")
    public String renderDefault(@ModelAttribute("lockerModel") Locker locker, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "lockerRoom/default";
        }

        flashes = new ArrayList<>();

        try {
            lockerService.delete(locker);
            flashes.add(new FlashMessage(FlashMessage.TYPE_SUCCESS, "Odstraněno"));
        } catch (Exception e) {
            flashes.add(new FlashMessage(FlashMessage.TYPE_DANGER, "Chyba databáze"));
        }

        List<Locker> lockers = lockerService.getAll();

        model.addAttribute("flashes", flashes);
        model.addAttribute("lockerModel", new Locker());
        model.addAttribute("lockers", lockers);
        model.addAttribute("title", "Šatna");

        return "lockerRoom/default";
    }

    @GetMapping("/satna/pridat")
    public String renderNew(Model model) {

        model.addAttribute("locker", new Locker());
        model.addAttribute("title", "Šatna");
        model.addAttribute("isAction", true);

        return "lockerRoom/new";
    }

    @PostMapping("/satna/pridat")
    public String renderNew(@ModelAttribute("locker") Locker locker, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "lockerRoom/new";
        }

        flashes = new ArrayList<>();

        try {
            lockerService.insert(locker);
            flashes.add(new FlashMessage(FlashMessage.TYPE_SUCCESS, "Vytvořeno"));
        } catch (Exception e) {
            flashes.add(new FlashMessage(FlashMessage.TYPE_DANGER, "Chyba databáze"));
        }


        model.addAttribute("flashes", flashes);
        model.addAttribute("locker", new Locker());
        model.addAttribute("title", "Šatna");
        model.addAttribute("isAction", true);

        return "lockerRoom/new";
    }

    @GetMapping("/satna/upravit/{id}")
    public String renderEdit(@PathVariable("id") int id, Model model) {

        Locker locker = lockerService.findById(id);

        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        model.addAttribute("locker", locker);
        model.addAttribute("lockerModel", new Locker());
        model.addAttribute("historyModel", new LockerHistory());
        model.addAttribute("customers", customerService.getAll());
        model.addAttribute("currentTime", dateFormat.format(new Date()));
        model.addAttribute("title", "Šatna");
        model.addAttribute("isAction", true);
        model.addAttribute("isParam", true);

        return "lockerRoom/edit";
    }

    @PostMapping("/satna/upravit/{id}")
    public String renderEdit(@PathVariable("id") int id, @ModelAttribute("lockerModel") Locker locker, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "lockerRoom/edit";
        }

        flashes = new ArrayList<>();

        try {
            lockerService.update(locker);
            flashes.add(new FlashMessage(FlashMessage.TYPE_SUCCESS, "Uloženo"));
        } catch (Exception e) {
            flashes.add(new FlashMessage(FlashMessage.TYPE_DANGER, "Chyba databáze"));
        }

        locker = lockerService.findById(id);

        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        model.addAttribute("currentTime", dateFormat.format(new Date()));
        model.addAttribute("flashes", flashes);
        model.addAttribute("customers", customerService.getAll());
        model.addAttribute("lockerModel", new Locker());
        model.addAttribute("locker", locker);
        model.addAttribute("historyModel", new LockerHistory());
        model.addAttribute("title", "Šatna");
        model.addAttribute("isAction", true);
        model.addAttribute("isParam", true);

        return "lockerRoom/edit";
    }

    @PostMapping("/satna/zakaznik/{id}")
    public String actionAddCustomer(@PathVariable("id") int id, @ModelAttribute("historyModel") LockerHistory lockerHistory, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getAllErrors()) {
                System.out.println(error);
            }
            return "redirect:/satna/upravit/"+id;
        }

        try {
            lockerHistory.setEntityID(id);
            lockerHistory.setUser(customerService.findById(lockerHistory.getUserID()));
            lockerService.insert(lockerHistory);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return "redirect:/satna/upravit/"+id;
    }

}
