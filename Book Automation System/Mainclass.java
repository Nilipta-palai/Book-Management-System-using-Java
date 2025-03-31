import java.util.*;
import java.io.IOException;

class Book {
    private String title;
    private String author;
    private int isbn;

    public Book(String a, String b, int c) {
        this.title = a;
        this.author = b;
        this.isbn = c;
    }

    public void setTitle(String i) {
        this.title = i;
    }

    public String getTitle() {
        return title;
    }

    public void setAuthor(String n) {
        this.author = n;
    }

    public String getAuthor() {
        return author;
    }

    public void setIsbn(int m) {
        this.isbn = m;
    }

    public int getIsbn() {
        return isbn;
    }

    public void getAllBookDetails() {
        System.out.println(title + "," + author + "," + isbn);
    }
}

class EmptyFieldException extends Exception {
    public EmptyFieldException(String message) {
        super(message);
    }
}

class BookNotFoundException extends Exception {
    public BookNotFoundException(String message) {
        super(message);
    }
}

class BookManagerClass {
    static List<Book> al = new ArrayList<Book>();
    static Scanner sc = new Scanner(System.in);

    public boolean addBook(Book e) throws EmptyFieldException {
        for (Book existingBook : al) {
            if (existingBook.getIsbn() == e.getIsbn()) {
                throw new EmptyFieldException("Book with this ISBN already exists!");
            }
        }
        al.add(e);
        return true;
    }

    public boolean removeBook(int isbn) throws BookNotFoundException {
        boolean isRemove = false;
        Iterator<Book> it = al.iterator();
        while (it.hasNext()) {
            Book e = it.next();
            if (e.getIsbn() == isbn) {
                isRemove = true;
                it.remove();
                break;
            }
        }
        if (!isRemove) {
            throw new BookNotFoundException("Book with ISBN " + isbn + " not found!");
        }
        return isRemove;
    }

    public Book[] viewAll() {
        Book[] ar1 = new Book[al.size()];
        for (int i = 0; i < al.size(); i++) {
            ar1[i] = al.get(i);
        }
        return ar1;
    }

    public void updateBook() throws BookNotFoundException {
        System.out.println("Enter the ISBN that you want to update");
        int x = sc.nextInt();
        boolean found = false;
        for (int i = 0; i < al.size(); i++) {
            if (al.get(i).getIsbn() == x) {
                found = true;
                System.out.println("Enter Title");
                al.get(i).setTitle(sc.next());

                System.out.println("Enter Author");
                al.get(i).setAuthor(sc.next());

                System.out.println("Enter ISBN");
                al.get(i).setIsbn(sc.nextInt());
                break;
            }
        }
        if (!found) {
            throw new BookNotFoundException("Book with ISBN " + x + " not found!");
        }
    }
}

public class Mainclass {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BookManagerClass ob = new BookManagerClass();

        boolean ch = true;
        while (ch) {
            System.out.println("Press 1 to add\nPress 2 to remove\nPress 3 to view all\nPress 4 to update\nPress 5 to exit");
            int choice = sc.nextInt();
            try {
                switch (choice) {
                    case 1:
                        System.out.println("Enter Title");
                        sc.nextLine();  // consume newline
                        String title = sc.nextLine();
                        System.out.println("Enter Author");
                        String author = sc.nextLine();
                        System.out.println("Enter ISBN");
                        int isbn = sc.nextInt();
                        Book newBook = new Book(title, author, isbn);
                        ob.addBook(newBook);
                        break;
                    case 2:
                        System.out.println("Enter ISBN of the book to remove");
                        int isbnToRemove = sc.nextInt();
                        ob.removeBook(isbnToRemove);
                        break;
                    case 3:
                        Book[] books = ob.viewAll();
                        for (Book b : books) {
                            b.getAllBookDetails();
                        }
                        break;
                    case 4:
                        ob.updateBook();
                        break;
                    case 5:
                        ch = false;
                        break;
                    default:
                        System.out.println("Invalid option");
                        break;
                }
            } catch (EmptyFieldException e) {
                System.out.println("Caught the exception: " + e.getMessage());
            } catch (BookNotFoundException e) {
                System.out.println("Caught the exception: " + e.getMessage());
            }
        }
        sc.close();
    }
}