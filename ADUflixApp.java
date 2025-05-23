import java.util.*;
/**
 * ADUflixApp is the main application class for a simple media streaming system.
 *
     * It allows users to:
     * - Load media from a file into the library
     * - Display all media items
     * - Search for media by title
     * - Watch media items (if they are playable) and store them in watch history
     * - View previously watched media
     * - Exit the application
 *
 * The application uses a console-based menu system and interacts with the user via standard input.
 * Media items are managed through the MediaLibrary class, and watched items are tracked in a separate list.
 */

public class ADUflixApp {
    private static MediaLibrary library = new MediaLibrary();
    private static ArrayList<Media> watchHistory = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n--- ADUflix Main Menu ---");
            System.out.println("1. Load media from file");
            System.out.println("2. Display all media");
            System.out.println("3. Search by title");
            System.out.println("4. Watch a media item");
            System.out.println("5. View watch history");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    library.loadFromFile("media_data.txt");
                    System.out.println("Media loaded.");
                    break;
                case "2":
                    library.displayAll();
                    break;
                case "3":
                    System.out.print("Enter title to search: ");
                    String title = scanner.nextLine();
                    for (Media m : library.searchByTitle(title)) {
                        System.out.println(m.getDisplayDetails());
                    }
                    break;
                case "4":
                    System.out.print("Enter title to watch: ");
                    String toWatch = scanner.nextLine();
                    for (Media m : library.searchByTitle(toWatch)) {
                        if (m instanceof Playable) {
                            ((Playable) m).play();
                            watchHistory.add(m);
                        }
                    }
                    break;
                case "5":
                    System.out.println("Watch History:");
                    for (Media m : watchHistory) {
                        System.out.println(m.getDisplayDetails());
                    }
                    break;
                case "6":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }

        scanner.close();
    }
}
