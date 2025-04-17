package com.lms.controller;

import com.lms.service.*;

import java.util.Scanner;

public class PatronController {
    private final PatronService patronService;

    public PatronController(PatronService service) {
        this.patronService = service;
    }

    public void handleAddPatron(Scanner sc) {
    	patronService.addPatron(sc);;
    }

    public void listPatrons() {
    	patronService.listPatrons();
    }

    public void deletePatron(Scanner sc) {
        patronService.deletePatron(sc);
    }

}
