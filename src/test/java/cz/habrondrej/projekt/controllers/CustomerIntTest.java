package cz.habrondrej.projekt.controllers;

import cz.habrondrej.projekt.db.AddressService;
import cz.habrondrej.projekt.db.CustomerService;
import cz.habrondrej.projekt.db.LoginService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@WebMvcTest(CustomerController.class)
public class CustomerIntTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CustomerService customerService;
    @MockBean
    private AddressService addressService;
    @MockBean
    private LoginService loginService;
    @MockBean
    public BCryptPasswordEncoder passwordEncoder;
    @MockBean
    private UserDetailsService userDetailsService;

    @Test
    public void renderDefault() throws Exception {
        when(customerService.getAll()).thenReturn(new ArrayList<>());

        RequestBuilder request = MockMvcRequestBuilders.get("/zakaznici");
        MvcResult result = mvc.perform(request).andReturn();
        assertEquals("http://localhost/login", result.getResponse().getRedirectedUrl());
    }

}
