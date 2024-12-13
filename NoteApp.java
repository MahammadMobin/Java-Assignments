import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

abstract class Note {
    private String title;
    private String content;
    private String date;

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
        this.date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
    }

  
    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public abstract void display();
}

class RegularNote extends Note {
    public RegularNote(String title, String content) {
        super(title, content);
    }

    @Override
    public void display() {
        System.out.println("Title: " + getTitle());
        System.out.println("Content: " + getContent());
        System.out.println("Date: " + getDate());
    }
}

class PinnedNote extends Note {
    public PinnedNote(String title, String content) {
        super(title, content);
    }

    @Override
    public void display() {
        System.out.println("[Pinned Note]");
        System.out.println("Title: " + getTitle());
        System.out.println("Content: " + getContent());
        System.out.println("Date: " + getDate());
    }
}

public class NoteApp {
    private ArrayList<Note> notes;

    public NoteApp() {
        notes = new ArrayList<>();
    }

    public void addNote(String title, String content, boolean isPinned) {
        if (isPinned) {
            notes.add(new PinnedNote(title, content));
        } else {
            notes.add(new RegularNote(title, content));
        }
        System.out.println("Note added successfully!");
    }

    public void updateNote(int noteIndex, String newTitle, String newContent) {
        if (noteIndex >= 0 && noteIndex < notes.size()) {
            Note note = notes.get(noteIndex);
            note.setTitle(newTitle);
            note.setContent(newContent);
            System.out.println("Note updated successfully!");
        } else {
            System.out.println("Invalid note number.");
        }
    }

    public void deleteNote(String title) {
        boolean found = false;
        for (int i = 0; i < notes.size(); i++) {
            Note note = notes.get(i);
            if (note.getTitle().equalsIgnoreCase(title)) {
                notes.remove(i);
                System.out.println("Note \"" + title + "\" deleted successfully!");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("No note found with the title \"" + title + "\".");
        }
    }

    public void displayNotes() {
        if (notes.isEmpty()) {
            System.out.println("No notes available.");
        } else {
            System.out.println("Your Notes:");
            for (int i = 0; i < notes.size(); i++) {
                System.out.print((i + 1) + ". ");
                notes.get(i).display();
                System.out.println();
            }
        }
    }

    public void searchNotes(String keyword) {
        boolean found = false;
        for (int i = 0; i < notes.size(); i++) {
            Note note = notes.get(i);
            if (note.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                note.getContent().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.print((i + 1) + ". ");
                note.display();
                System.out.println();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No notes found with the keyword \"" + keyword + "\".");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        NoteApp app = new NoteApp();

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Add Note");
            System.out.println("2. Update Note");
            System.out.println("3. Delete Note");
            System.out.println("4. View All Notes");
            System.out.println("5. Search Notes");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Handle newline

            switch (choice) {
                case 1:
                    System.out.print("Enter note title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter note content: ");
                    String content = scanner.nextLine();
                    System.out.print("Is this a pinned note? (true/false): ");
                    boolean isPinned = scanner.nextBoolean();
                    scanner.nextLine(); // Handle newline
                    app.addNote(title, content, isPinned);
                    break;
                case 2:
                    System.out.print("Enter the note number to update: ");
                    int noteToUpdate = scanner.nextInt() - 1;
                    scanner.nextLine(); // Handle newline
                    System.out.print("Enter the new title: ");
                    String newTitle = scanner.nextLine();
                    System.out.print("Enter the new content: ");
                    String newContent = scanner.nextLine();
                    app.updateNote(noteToUpdate, newTitle, newContent);
                    break;
                case 3:
                    System.out.print("Enter the title of the note to delete: ");
                    String titleToDelete = scanner.nextLine();
                    app.deleteNote(titleToDelete);
                    break;
                case 4:
                    app.displayNotes();
                    break;
                case 5:
                    System.out.print("Enter a keyword to search: ");
                    String keyword = scanner.nextLine();
                    app.searchNotes(keyword);
                    break;
                case 6:
                    System.out.println("Exiting the application. Thank you!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid input. Please try again.");
            }
        }
    }
}

