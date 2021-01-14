package cz.habrondrej.projekt.model;


import cz.habrondrej.projekt.model.utils.Role;

import java.sql.Date;

public class Employee extends User {

    public Employee() {}

    public Employee(int id, Date bornDate, String email, String firstname, String lastname, String personalIdentificationNumber, String phone, Role role) {
        this.id = id;
        this.bornDate = bornDate;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.personalIdentificationNumber = personalIdentificationNumber;
        this.phone = phone;
        this.role = role;
    }

    public Employee(int id, Date bornDate, String email, String firstname, String lastname, String personalIdentificationNumber, String phone, Role role, int addressId, int loginId) {
        this.id = id;
        this.bornDate = bornDate;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.personalIdentificationNumber = personalIdentificationNumber;
        this.phone = phone;
        this.addressId = addressId;
        this.loginId = loginId;
        this.role = role;
    }
}
