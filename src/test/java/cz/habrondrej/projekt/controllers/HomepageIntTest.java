package cz.habrondrej.projekt.controllers;

import cz.habrondrej.projekt.db.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@WebMvcTest(HomepageController.class)
public class HomepageIntTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EmployeeService employeeService;
    @MockBean
    private CustomerService customerService;
    @MockBean
    private AddressService addressService;
    @MockBean
    private LoginService loginService;
    @MockBean
    private SubscriptionService subscriptionService;
    @MockBean
    private ImageService imageService;
    @MockBean
    private EventService eventService;
    @MockBean
    private UserDetailsService userDetailsService;

    @Test
    public void renderDefault() throws Exception {
        when(eventService.getView()).thenReturn(new ArrayList<>());

        RequestBuilder request = MockMvcRequestBuilders.get("/");
        MvcResult result = mvc.perform(request).andReturn();
        assertEquals("/homepage/default.jsp", result.getResponse().getForwardedUrl());
    }
}
