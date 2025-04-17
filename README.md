# Library Management System (LMS)

## Overview

The **Library Management System (LMS)** is a software application that helps in managing the operations of a library. The system follows the **Model-View-Controller (MVC)** design pattern. This system is used by both patrons and librarians to perform various actions like managing books, borrowing, and returning books, and managing patrons.<br><br>

### Key Features:
- **Add Books**: Librarians can add books to the library's collection.<br>
- **View Books**: Librarians and patrons can view the list of available books.<br>
- **Borrow Books**: Patrons can borrow books, if available.<br>
- **Return Books**: Patrons can return borrowed books.<br>
- **Add Patrons**: Admins can add new patrons to the system.<br>
- **Remove Patrons**: Admins can remove patrons from the system if needed.<br>
- **View Borrowed Books**: Patrons can view the list of books they have borrowed.<br><br>

## Architecture

This system follows the **MVC (Model-View-Controller)** architecture:<br>

- **Model**: Represents the data and business logic of the system. Examples of models include `Book`, `Patron`.<br>
- **View**: The user interface through which users interact with the system. In this case, it's a **console-based UI**.<br>
- **Controller**: Acts as an intermediary between the Model and the View. It handles user inputs and updates the Model.<br><br>

## Technologies Used

- **Java**: The main programming language used to develop this application.<br>
- **JDBC**: Java Database Connectivity for interacting with the database.<br>
- **MySQL**: Used as the relational database management system to store the data.<br><br>

## Requirements

- **JDK 8 or above**<br>
- **MySQL database**<br>
- A text editor or IDE (Eclipse, IntelliJ IDEA, or Visual Studio Code)<br><br>

To clone this repo go to master section and clone git url
