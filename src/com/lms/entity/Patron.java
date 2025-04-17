package com.lms.entity;

public class Patron {
	private int pid;
    private String name;
    private Book[] borrowedbooks;
    private int bCount;
    private final int MAX_Book=5;

    public Patron(int pid,String name) {
    	this.pid=pid;
        this.name = name;
        this.borrowedbooks = new Book[MAX_Book];
        this.bCount=0;
    }
    public int getPid() {
    	return pid;
    }
    public String getName() {
        return name;
    }
    public Book[] getBorrowedBooks() {
        return borrowedbooks;
    }
    public int getBorrowedBookCount() {
        return bCount	;
    }
    public void borrowBook(Book book) {
    	if(bCount>=MAX_Book) {
    		System.out.println("cannot borrow more than "+MAX_Book+" books.");
    		return;
         } 
    	borrowedbooks[bCount++]=book;
    	book.setAvailable(false);
    }
    
    public void returnBook(int bookId) {
        boolean found = false;
        for (int i = 0; i < bCount; i++) {
            if (borrowedbooks[i].getId() == bookId) {
                System.out.println(this.name + " has returned the book: " + borrowedbooks[i].getTitle());
                borrowedbooks[i].setAvailable(true);
                for (int j = i; j < bCount - 1; j++) {
                    borrowedbooks[j] = borrowedbooks[j + 1];
                }
                borrowedbooks[--bCount] = null;
                found = true;
                break;
            }
        }
      if (!found) {
            System.out.println(this.name + " did not borrow a book with ID: " + bookId);
        }
    }
	public void printPatrons() {
		System.out.println(this.pid+" "+this.name);
	}
}
