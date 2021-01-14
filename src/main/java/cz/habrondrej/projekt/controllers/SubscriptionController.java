package cz.habrondrej.projekt.controllers;

import cz.habrondrej.projekt.db.SubscriptionService;
import cz.habrondrej.projekt.model.Subscription;
import cz.habrondrej.projekt.utils.FlashMessage;
import org.springframework.beans.factory.annotation.Autowired;
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
public class SubscriptionController {

    private List<FlashMessage> flashes;

    @Autowired
    SubscriptionService subscriptionService;

    @GetMapping("/predplatne")
    public String renderDefault(Model model) {

        List<Subscription> subscriptions = subscriptionService.getAll();

        model.addAttribute("subscriptionModel", new Subscription());
        model.addAttribute("subscriptions", subscriptions);
        model.addAttribute("title", "Předplatné");

        return "subscription/default";
    }

    @PostMapping("/predplatne")
    public String renderDefault(@ModelAttribute("lockerModel") Subscription subscription, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "subscription/default";
        }

        flashes = new ArrayList<>();

        try {
            subscriptionService.delete(subscription);
            flashes.add(new FlashMessage(FlashMessage.TYPE_SUCCESS, "Odstraněno"));
        } catch (Exception e) {
            flashes.add(new FlashMessage(FlashMessage.TYPE_DANGER, "Chyba databáze"));
        }

        List<Subscription> subscriptions = subscriptionService.getAll();

        model.addAttribute("flashes", flashes);
        model.addAttribute("subscriptionModel", new Subscription());
        model.addAttribute("subscriptions", subscriptions);
        model.addAttribute("title", "Předplatné");

        return "subscription/default";
    }

    @GetMapping("/predplatne/pridat")
    public String renderNew(Model model) {

        model.addAttribute("subscription", new Subscription());
        model.addAttribute("title", "Předplatné");
        model.addAttribute("isAction", true);

        return "subscription/new";
    }

    @PostMapping("/predplatne/pridat")
    public String renderNew(@ModelAttribute("subscription") Subscription subscription, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "subscription/new";
        }

        flashes = new ArrayList<>();

        try {
            subscriptionService.insert(subscription);
            flashes.add(new FlashMessage(FlashMessage.TYPE_SUCCESS, "Vytvořeno"));
        } catch (Exception e) {
            flashes.add(new FlashMessage(FlashMessage.TYPE_DANGER, "Chyba databáze"));
        }


        model.addAttribute("flashes", flashes);
        model.addAttribute("subscription", new Subscription());
        model.addAttribute("title", "Předplatné");
        model.addAttribute("isAction", true);

        return "subscription/new";
    }

    @GetMapping("/predplatne/upravit/{id}")
    public String renderEdit(@PathVariable("id") int id, Model model) {

        Subscription subscription = subscriptionService.findById(id);


        model.addAttribute("subscription", subscription);
        model.addAttribute("subscriptionModel", new Subscription());
        model.addAttribute("title", "Předplatné");
        model.addAttribute("isAction", true);
        model.addAttribute("isParam", true);

        return "subscription/edit";
    }

    @PostMapping("/predplatne/upravit/{id}")
    public String renderEdit(@PathVariable("id") int id, @ModelAttribute("subscriptionModel") Subscription subscription, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "subscription/edit";
        }

        flashes = new ArrayList<>();

        try {
            subscription.setId(id);
            subscriptionService.update(subscription);
            flashes.add(new FlashMessage(FlashMessage.TYPE_SUCCESS, "Uloženo"));
        } catch (Exception e) {
            flashes.add(new FlashMessage(FlashMessage.TYPE_DANGER, "Chyba databáze"));
        }

        subscription = subscriptionService.findById(id);

        model.addAttribute("flashes", flashes);
        model.addAttribute("subscriptionModel", new Subscription());
        model.addAttribute("subscription", subscription);
        model.addAttribute("title", "Předplatné");
        model.addAttribute("isAction", true);
        model.addAttribute("isParam", true);

        return "subscription/edit";
    }
}
