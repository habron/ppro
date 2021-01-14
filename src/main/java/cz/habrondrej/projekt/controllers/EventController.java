package cz.habrondrej.projekt.controllers;

import cz.habrondrej.projekt.db.EventService;
import cz.habrondrej.projekt.db.RoomsService;
import cz.habrondrej.projekt.model.Employee;
import cz.habrondrej.projekt.model.Event;
import cz.habrondrej.projekt.model.Room;
import cz.habrondrej.projekt.model.User;
import cz.habrondrej.projekt.model.utils.Note;
import cz.habrondrej.projekt.model.utils.PdfUserDetails;
import cz.habrondrej.projekt.utils.FlashMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class EventController {

    private List<FlashMessage> flashes;

    @Autowired
    private EventService eventService;
    @Autowired
    private RoomsService roomsService;

    @GetMapping("/udalosti")
    public String renderDefault(Model model) {

        List<Event> events = eventService.getAll();

        for (Event event : events) {
            event.isUserSigned(getLoggedUser());
        }

        model.addAttribute("events", events);
        model.addAttribute("eventModel", new Event());
        model.addAttribute("title", "Události");

        return "events/default";
    }

    @PostMapping("/udalosti")
    public String renderDefault(@ModelAttribute("eventModel") Event event, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "events/default";
        }

        flashes = new ArrayList<>();

        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        User loggedInUser = ((PdfUserDetails) authentication.getPrincipal()).getUserDetails();

        if (!(loggedInUser.getRole().getRole().equals("ROLE_ADMIN") || loggedInUser.getRole().getRole().equals("ROLE_MANAGER"))) {

            if (loggedInUser.getRole().getRole().equals("ROLE_CUSTOMER")) {
                try {
                    eventService.signInUser(eventService.findById(event.getId()), loggedInUser);
                } catch (Exception e) {
                    e.printStackTrace();
                    flashes.add(new FlashMessage(FlashMessage.TYPE_DANGER, "Chyba databáze"));
                }
            } else {
                flashes.add(new FlashMessage(FlashMessage.TYPE_WARNING, "Nemáte oprávnění"));
            }
        } else {
            try {
                eventService.delete(event);
                flashes.add(new FlashMessage(FlashMessage.TYPE_SUCCESS, "Odstraněno"));
            } catch (Exception e) {
                flashes.add(new FlashMessage(FlashMessage.TYPE_DANGER, "Chyba databáze"));
            }
        }

        List<Event> events = eventService.getAll();

        for (Event event1 : events) {
            event1.isUserSigned(getLoggedUser());
        }

        model.addAttribute("flashes", flashes);
        model.addAttribute("eventModel", new Event());
        model.addAttribute("events", events);
        model.addAttribute("title", "Události");

        return "events/default";
    }

    @GetMapping("/udalosti/pridat")
    public String renderNew(Model model) {

        Map<Integer, String> roomsId = getRoomsId();

        model.addAttribute("event", new Event());
        model.addAttribute("roomsId", roomsId);
        model.addAttribute("title", "Události");
        model.addAttribute("isAction", true);

        return "events/new";
    }

    @PostMapping("/udalosti/pridat")
    public String renderNew(@ModelAttribute("event") Event event, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "events/new";
        }

        flashes = new ArrayList<>();

        try {
            event.setUserCreate((Employee) getLoggedUser());
            eventService.insert(event);
            flashes.add(new FlashMessage(FlashMessage.TYPE_SUCCESS, "Vytvořeno"));
        } catch (Exception e) {
            flashes.add(new FlashMessage(FlashMessage.TYPE_DANGER, "Chyba databáze"));
        }
        Map<Integer, String> roomsId = getRoomsId();

        model.addAttribute("flashes", flashes);
        model.addAttribute("roomsId", roomsId);
        model.addAttribute("event", new Event());
        model.addAttribute("title", "Události");
        model.addAttribute("isAction", true);

        return "events/new";
    }

    @GetMapping("/udalosti/detail/{id}")
    public String renderDetail(@PathVariable("id") int id, Model model) {

        Event event = eventService.findById(id);
        event.setRoom(roomsService.findById(event.getRoomId()));


        model.addAttribute("event", event);
        model.addAttribute("title", "Události");
        model.addAttribute("isAction", true);
        model.addAttribute("isParam", true);

        return "events/detail";
    }

    @GetMapping("/udalosti/upravit/{id}")
    public String renderEdit(@PathVariable("id") int id, Model model) {

        Event event = eventService.findById(id);
        event.setRoom(roomsService.findById(event.getRoomId()));

        if (getLoggedUser().getRole().getRole().equals("ROLE_INSTRUCTOR") && getLoggedUser().getId() != event.getUserCreate().getId()) {
            return "redirect:/udalosti";
        }

        Map<Integer, String> roomsId = getRoomsId();

        model.addAttribute("roomsId", roomsId);
        model.addAttribute("event", event);
        model.addAttribute("eventModel", new Event());
        model.addAttribute("noteModel", new Note());
        model.addAttribute("title", "Události");
        model.addAttribute("isAction", true);
        model.addAttribute("isParam", true);

        return "events/edit";
    }

    @PostMapping("/udalosti/upravit/{id}")
    public String renderEdit(@PathVariable("id") int id, @ModelAttribute("eventModel") Event event, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "events/edit";
        }

        flashes = new ArrayList<>();

        try {
            eventService.update(event);
            flashes.add(new FlashMessage(FlashMessage.TYPE_SUCCESS, "Uloženo"));
        } catch (Exception e) {
            e.printStackTrace();
            flashes.add(new FlashMessage(FlashMessage.TYPE_DANGER, "Chyba databáze"));
        }

        event = eventService.findById(id);
        event.setRoom(roomsService.findById(event.getRoomId()));

        Map<Integer, String> roomsId = getRoomsId();

        model.addAttribute("roomsId", roomsId);
        model.addAttribute("noteModel", new Note());
        model.addAttribute("flashes", flashes);
        model.addAttribute("eventModel", new Event());
        model.addAttribute("event", event);
        model.addAttribute("title", "Události");
        model.addAttribute("isAction", true);
        model.addAttribute("isParam", true);

        return "events/edit";
    }

    @PostMapping("/udalosti/poznamka/{id}")
    public String renderEdit(@PathVariable("id") int id, @ModelAttribute("noteModel") Note note, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "events/edit";
        }

        flashes = new ArrayList<>();

        try {
            note.setEventID(id);
            note.setEmployee((Employee) getLoggedUser());
            eventService.insertNote(note);
            flashes.add(new FlashMessage(FlashMessage.TYPE_SUCCESS, "Uloženo"));
        } catch (Exception e) {
            flashes.add(new FlashMessage(FlashMessage.TYPE_DANGER, "Chyba databáze"));
        }


        return "redirect:/udalosti/upravit/"+id;
    }

    private LinkedHashMap<Integer, String> getRoomsId() {

        List<Room> rooms = roomsService.getAll();
        Map<Integer,String> roomsId = new LinkedHashMap<>();

        for (Room room : rooms) {
            roomsId.put(room.getId(), room.getRoomType().getName());
        }

        return (LinkedHashMap<Integer, String>) roomsId;
    }

    private User getLoggedUser() {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        User loggedInUser = ((PdfUserDetails) authentication.getPrincipal()).getUserDetails();
        return loggedInUser;
    }
}
