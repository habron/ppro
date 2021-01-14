package cz.habrondrej.projekt.controllers;

import cz.habrondrej.projekt.db.RoomTypeService;
import cz.habrondrej.projekt.db.RoomsService;
import cz.habrondrej.projekt.model.Room;
import cz.habrondrej.projekt.model.RoomType;
import cz.habrondrej.projekt.model.User;
import cz.habrondrej.projekt.model.utils.PdfUserDetails;
import cz.habrondrej.projekt.utils.FlashMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class RoomsController {

    private List<FlashMessage> flashes;

    @Autowired
    private RoomsService roomsService;
    @Autowired
    private RoomTypeService roomTypeService;

    @GetMapping("/mistnosti")
    public String renderDefault(Model model) {

        List<Room> rooms = roomsService.getAll();

        model.addAttribute("rooms", rooms);
        model.addAttribute("roomModel", new Room());
        model.addAttribute("title", "Místnosti");

        return "rooms/default";
    }

    @PostMapping("/mistnosti")
    public String renderDefault(@ModelAttribute("roomModel") Room room, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "rooms/default";
        }

        flashes = new ArrayList<>();

        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        User loggedInUser = ((PdfUserDetails) authentication.getPrincipal()).getUserDetails();

        if (!(loggedInUser.getRole().getRole().equals("ROLE_ADMIN") || loggedInUser.getRole().getRole().equals("ROLE_MANAGER"))) {
            flashes.add(new FlashMessage(FlashMessage.TYPE_WARNING, "Nemáte oprávnění"));
        } else {

            try {
                roomsService.delete(room);
                flashes.add(new FlashMessage(FlashMessage.TYPE_SUCCESS, "Odstraněno"));
            } catch (Exception e) {
                flashes.add(new FlashMessage(FlashMessage.TYPE_DANGER, "Chyba databáze"));
            }

        }

        List<Room> rooms = roomsService.getAll();

        model.addAttribute("rooms", rooms);
        model.addAttribute("roomModel", new Room());
        model.addAttribute("title", "Místnosti");
        model.addAttribute("flashes", flashes);

        return "rooms/default";
    }

    @GetMapping("/mistnosti/pridat")
    public String renderNew(Model model) {

        Map<Integer, String> roomTypesId = getRoomTypesId();

        model.addAttribute("room", new Room());
        model.addAttribute("roomTypesId", roomTypesId);
        model.addAttribute("title", "Místnosti");
        model.addAttribute("isAction", true);
        return "rooms/new";
    }

    @PostMapping("/mistnosti/pridat")
    public String renderNew(@ModelAttribute("room") Room room, BindingResult bindingResult, ModelMap model) {
        if (bindingResult.hasErrors()) {
            return "rooms/new";
        }

        flashes = new ArrayList<>();

        try {
            roomsService.insert(room);
            flashes.add(new FlashMessage(FlashMessage.TYPE_SUCCESS, "Vytvořeno"));
        } catch (Exception e) {
            flashes.add(new FlashMessage(FlashMessage.TYPE_DANGER, "Chyba databáze"));
        }

        Map<Integer, String> roomTypesId = getRoomTypesId();

        model.addAttribute("room", new Room());
        model.addAttribute("roomTypesId", roomTypesId);
        model.addAttribute("flashes", flashes);
        model.addAttribute("title", "Místnosti");
        model.addAttribute("isAction", true);
        return "rooms/new";
    }

    @GetMapping("/mistnosti/upravit/{id}")
    public String renderEdit(@PathVariable int id, Model model) {

        Room room = roomsService.findById(id);

        Map<Integer, String> roomTypesId = getRoomTypesId();

        room.getRoomType().setOperatingRules(room.getRoomType().getOperatingRules().replaceAll("\n", "<br/>"));

        model.addAttribute("room", room);
        model.addAttribute("roomTypesId", roomTypesId);
        model.addAttribute("roomModel", new Room());
        model.addAttribute("title", "Místnosti");

        model.addAttribute("isAction", true);
        model.addAttribute("isParam", true);
        return "rooms/edit";
    }

    @PostMapping("/mistnosti/upravit/{id}")
    public String renderEdit(@PathVariable int id, @ModelAttribute("roomModel") Room room, BindingResult bindingResult, ModelMap model) {
        if (bindingResult.hasErrors()) {
            return "rooms/edit";
        }

        flashes = new ArrayList<>();

        room.setId(id);
        try {
            roomsService.update(room);
            flashes.add(new FlashMessage(FlashMessage.TYPE_SUCCESS, "Uloženo"));
        } catch (Exception e) {
            flashes.add(new FlashMessage(FlashMessage.TYPE_DANGER, "Chyba databáze"));
        }

        room = roomsService.findById(id);
        Map<Integer, String> roomTypesId = getRoomTypesId();

        model.addAttribute("room", room);
        model.addAttribute("roomTypesId", roomTypesId);

        model.addAttribute("flashes", flashes);
        model.addAttribute("title", "Místnosti");
        model.addAttribute("isAction", true);
        model.addAttribute("isParam", true);
        return "rooms/edit";
    }

    @GetMapping("/typy-mistnosti")
    public String renderTypes(Model model) {

        List<RoomType> roomTypes = roomTypeService.getAll();

        model.addAttribute("roomTypes", roomTypes);
        model.addAttribute("title", "Místnosti");
        model.addAttribute("roomTypeModel", new RoomType());
        return "rooms/types";
    }

    @PostMapping("/typy-mistnosti")
    public String renderTypes(@ModelAttribute("roomTypeModel") RoomType roomType, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "rooms/types";
        }

        flashes = new ArrayList<>();

        try {
            roomTypeService.delete(roomType);
            flashes.add(new FlashMessage(FlashMessage.TYPE_SUCCESS, "Odstraněno"));
        } catch (Exception e) {
            flashes.add(new FlashMessage(FlashMessage.TYPE_DANGER, "Chyba databáze"));
        }

        List<RoomType> roomTypes = roomTypeService.getAll();

        model.addAttribute("roomTypes", roomTypes);
        model.addAttribute("title", "Místnosti");
        model.addAttribute("flashes", flashes);

        return "rooms/types";
    }

    @GetMapping("/typy-mistnosti/pridat")
    public String renderNewTypes(Model model) {

        model.addAttribute("roomType", new RoomType());
        model.addAttribute("title", "Místnosti");
        model.addAttribute("isAction", true);
        return "rooms/newTypes";
    }

    @PostMapping("/typy-mistnosti/pridat")
    public String renderNewTypes(@ModelAttribute("roomType") RoomType roomType, BindingResult bindingResult, ModelMap model) {
        if (bindingResult.hasErrors()) {
            return "rooms/newTypes";
        }

        flashes = new ArrayList<>();

        try {
            roomTypeService.insert(roomType);
            flashes.add(new FlashMessage(FlashMessage.TYPE_SUCCESS, "Vytvořeno"));
        } catch (Exception e) {
            flashes.add(new FlashMessage(FlashMessage.TYPE_DANGER, "Chyba databáze"));
        }

        model.addAttribute("roomType", new RoomType());
        model.addAttribute("flashes", flashes);
        model.addAttribute("title", "Místnosti");
        model.addAttribute("isAction", true);
        return "rooms/newTypes";
    }

    @GetMapping("/typy-mistnosti/upravit/{id}")
    public String renderEditTypes(@PathVariable int id, Model model) {

        RoomType roomType = roomTypeService.findById(id);

        model.addAttribute("roomType", roomType);
        model.addAttribute("roomTypeModel", new RoomType());
        model.addAttribute("title", "Místnosti");

        model.addAttribute("isAction", true);
        model.addAttribute("isParam", true);
        return "rooms/editTypes";
    }

    @PostMapping("/typy-mistnosti/upravit/{id}")
    public String renderEditTypes(@PathVariable int id, @ModelAttribute("roomTypeModel") RoomType roomType, BindingResult bindingResult, ModelMap model) {
        if (bindingResult.hasErrors()) {
            return "rooms/editTypes";
        }

        flashes = new ArrayList<>();

        roomType.setId(id);
        try {
            roomTypeService.update(roomType);
            flashes.add(new FlashMessage(FlashMessage.TYPE_SUCCESS, "Uloženo"));
        } catch (Exception e) {
            flashes.add(new FlashMessage(FlashMessage.TYPE_DANGER, "Chyba databáze"));
        }

        roomType = roomTypeService.findById(id);

        model.addAttribute("roomType", roomType);

        model.addAttribute("flashes", flashes);
        model.addAttribute("title", "Místnosti");
        model.addAttribute("isAction", true);
        model.addAttribute("isParam", true);
        return "rooms/editTypes";
    }

    private LinkedHashMap<Integer, String> getRoomTypesId() {

        List<RoomType> roomTypes = roomTypeService.getAll();
        Map<Integer,String> roomTypesId = new LinkedHashMap<>();

        for (RoomType roomType : roomTypes) {
            roomTypesId.put(roomType.getId(), roomType.getName());
        }

        return (LinkedHashMap<Integer, String>) roomTypesId;
    }
}
