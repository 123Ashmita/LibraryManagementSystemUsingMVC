package com.lms.service;

import com.lms.dao.PatronDAO;
import com.lms.entity.Patron;

import java.util.Scanner;

public class PatronService {
    private final PatronDAO patronDAO;

    public PatronService() {
        this.patronDAO = new PatronDAO();
    }

    public void addPatron(Patron patron) {
        patronDAO.addPatron(patron);
    }

    public Patron[] listPatrons() {
        Patron[] patrons = patronDAO.getAllPatrons();
        System.out.println("--------Patron List -------");
        for (Patron patron : patrons) {
            patron.printPatrons();
        }
        return patrons;
    }

    public boolean deletePatron(int id) {
        return patronDAO.deletePatron(id);
    }
    public void handleAddPatron(Scanner sc) {
        System.out.print("Enter Patron ID: ");
        int patronId = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Patron Name: ");
        String name = sc.nextLine();

        if (name.trim().isEmpty()) {
            System.out.println("❌ Name can't be empty.");
            return;
        }

        Patron patron = new Patron(patronId, name.trim());
        addPatron(patron);
    }

    public void handleListPatrons() {
        listPatrons();
    }

    public void handleDeletePatron(Scanner sc) {
        System.out.print("Enter Patron ID to delete: ");
        int id = sc.nextInt();
        boolean deleted = deletePatron(id);
        if (deleted) {
            System.out.println("Patron deleted successfully.");
        } else {
            System.out.println("Cannot delete patron. They may not exist or have borrowed books.");
        }
    }

}
