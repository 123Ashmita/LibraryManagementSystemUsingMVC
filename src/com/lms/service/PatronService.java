package com.lms.service;

import com.lms.dao.PatronDAO;
import com.lms.entity.Patron;

import java.util.Scanner;

public class PatronService {
    private PatronDAO patronDAO = new PatronDAO();

    // Add a new patron
    public void addPatron(Scanner sc) {
        System.out.print("Enter Patron ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Patron Name: ");
        String name = sc.nextLine();

        if (name.trim().isEmpty()) {
            System.out.println("Name can't be empty.");
            return;
        }

        Patron patron = new Patron(id, name);
        patronDAO.addPatron(patron);
        System.out.println("Patron added.");
    }

    // Show all patrons
    public void listPatrons() {
        Patron[] patrons = patronDAO.getAllPatrons();
        System.out.println("---- Patron List ----");
        for (Patron patron : patrons) {
            patron.printPatrons();
        }
    }

    // Delete a patron
    public void deletePatron(Scanner sc) {
        System.out.print("Enter Patron ID to delete: ");
        int id = sc.nextInt();

        if (patronDAO.deletePatron(id)) {
            System.out.println("Patron deleted.");
        } else {
            System.out.println("Delete failed. Patron may not exist or  have borrowed books.");
        }
    }
}
	