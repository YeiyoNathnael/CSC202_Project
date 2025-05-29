import java.io.*;
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
     * - Get personalized recommendations
     * - Export watch history and recommendations
     * - Exit the application
 *
 * The application uses a console-based menu system and interacts with the user via standard input.
 * Media items are managed through the MediaLibrary class, and user functionality is handled through User objects.
 */

public class ADUflixApp {
    private static MediaLibrary library = new MediaLibrary();
    private static User currentUser = null;
    private static final Scanner scanner = new Scanner(System.in);
    
    // Static constants for better maintainability
    private static final String MENU_SEPARATOR = "=" + "=".repeat(30);
    private static final double DEFAULT_MIN_RATING = 0.0;
    private static final int DEFAULT_MAX_DURATION = 200;

    public static void main(String[] args) {
        System.out.println("Welcome to ADUflix!");
        
        // Create or select user
        setupUser();
        
        boolean running = true;
        while (running) {
            try {
                displayMainMenu();
                String choice = getUserInput("Choose an option: ");

                switch (choice) {
                    case "1":
                        loadMediaFromFile();
                        break;
                    case "2":
                        library.displayAll();
                        break;
                    case "3":
                        searchByTitle();
                        break;
                    case "4":
                        watchMedia();
                        break;
                    case "5":
                        currentUser.viewWatchHistory();
                        break;
                    case "6":
                        getRecommendations();
                        break;
                    case "7":
                        exportWatchHistory();
                        break;
                    case "8":
                        exportRecommendations();
                        break;
                    case "9":
                        displayStatistics();
                        break;
                    case "10":
                        // Save user data before exiting
                        currentUser.logout();
                        System.out.println("Your watch history has been saved.");
                        running = false;
                        System.out.println("Thank you for using ADUflix! Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input format. Please enter a valid option.");
                scanner.nextLine(); // Clear the invalid input
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }

        scanner.close();
    }
    
    /**
     * Sets up the current user for the session with persistent login
     */
    private static void setupUser() {
        System.out.println("\n" + MENU_SEPARATOR);
        System.out.println("User Setup");
        System.out.println(MENU_SEPARATOR);
        
        String username = getUserInput("Enter your username: ");
        
        // Try to load existing user data
        User loadedUser = User.loadUserData(username);
        
        if (loadedUser != null) {
            currentUser = loadedUser;
            System.out.println("Welcome back, " + username + "! Your watch history has been restored.");
            System.out.println("User ID: " + currentUser.getUserId());
            System.out.println("Previously watched items: " + currentUser.getWatchHistory().size());
        } else {
            currentUser = new User(username);
            System.out.println("Welcome, " + username + "! This appears to be your first time.");
            System.out.println("Your user ID is: " + currentUser.getUserId());
        }
    }
    
    /**
     * Displays the main menu options
     */
    private static void displayMainMenu() {
        System.out.println("\n" + MENU_SEPARATOR);
        System.out.println("ADUflix Main Menu - User: " + currentUser.getUsername());
        System.out.println(MENU_SEPARATOR);
        System.out.println("1. Load media from file");
        System.out.println("2. Display all media");
        System.out.println("3. Search by title");
        System.out.println("4. Watch a media item");
        System.out.println("5. View watch history");
        System.out.println("6. Get personalized recommendations");
        System.out.println("7. Export watch history to file");
        System.out.println("8. Export recommendations to file");
        System.out.println("9. View statistics");
        System.out.println("10. Exit");
    }
    
    /**
     * Loads media from file with enhanced error handling
     */
    private static void loadMediaFromFile() {
        String filename = getUserInput("Enter filename (or press Enter for 'media_data.txt'): ");
        if (filename.trim().isEmpty()) {
            filename = "media_data.txt";
        }
        
        try {
            library.loadFromFile(filename);
            System.out.println("Media loaded successfully from " + filename);
            System.out.println("Total media items in library: " + library.getAllMedia().size());
        } catch (InvalidMediaDataException e) {
            System.out.println("Error loading media data: " + e.getMessage());
            System.out.println("Please check your file format and try again.");
        }
    }
    
    /**
     * Searches for media by title
     */
    private static void searchByTitle() {
        String title = getUserInput("Enter title to search: ");
        ArrayList<Media> results = library.searchByTitle(title);
        
        if (results.isEmpty()) {
            System.out.println("No media found matching '" + title + "'");
        } else {
            System.out.println("\nSearch Results (" + results.size() + " found):");
            for (Media m : results) {
                System.out.println("- " + m.getDisplayDetails());
            }
        }
    }
    
    /**
     * Allows user to watch media items
     */
    private static void watchMedia() {
        String toWatch = getUserInput("Enter title to watch: ");
        ArrayList<Media> results = library.searchByTitle(toWatch);
        
        if (results.isEmpty()) {
            System.out.println("No media found matching '" + toWatch + "'");
            return;
        }
        
        for (Media m : results) {
            if (m instanceof Playable) {
                ((Playable) m).play();
                currentUser.watchMedia(m);
                // Auto-save watch history after adding new item
                currentUser.logout();
                System.out.println("Added to your watch history!");
            } else {
                System.out.println("This media item is not playable.");
            }
        }
    }
    
    /**
     * Gets and displays personalized recommendations
     */
    private static void getRecommendations() {
        try {
            double minRating = getDoubleInput("Enter minimum rating (0.0-10.0, default " + DEFAULT_MIN_RATING + "): ", DEFAULT_MIN_RATING);
            int maxDuration = getIntInput("Enter maximum duration in minutes (default " + DEFAULT_MAX_DURATION + "): ", DEFAULT_MAX_DURATION);
            
            ArrayList<Media> recommendations = currentUser.getRecommendations(library, minRating, maxDuration);
            
            System.out.println("\n" + MENU_SEPARATOR);
            System.out.println("Personalized Recommendations");
            System.out.println(MENU_SEPARATOR);
            
            if (recommendations.isEmpty()) {
                System.out.println("No recommendations found matching your criteria.");
                System.out.println("Try watching more content or adjusting your filters.");
            } else {
                System.out.println("Based on your viewing history, we recommend:");
                for (int i = 0; i < recommendations.size(); i++) {
                    System.out.println((i + 1) + ". " + recommendations.get(i).getDisplayDetails());
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input format. Please enter valid numbers.");
            scanner.nextLine(); // Clear invalid input
        }
    }
    
    /**
     * Exports user's watch history to a file
     */
    private static void exportWatchHistory() {
        String filename = getUserInput("Enter filename for watch history export (default: watchhistory_" + currentUser.getUserId() + ".txt): ");
        if (filename.trim().isEmpty()) {
            filename = "watchhistory_" + currentUser.getUserId() + ".txt";
        }
        
        try {
            currentUser.exportWatchHistory(filename);
            System.out.println("Watch history exported successfully to " + filename);
        } catch (IOException e) {
            System.out.println("Error exporting watch history: " + e.getMessage());
        }
    }
    
    /**
     * Exports recommendations to a file
     */
    private static void exportRecommendations() {
        try {
            double minRating = getDoubleInput("Enter minimum rating for recommendations (0.0-10.0, default " + DEFAULT_MIN_RATING + "): ", DEFAULT_MIN_RATING);
            int maxDuration = getIntInput("Enter maximum duration in minutes (default " + DEFAULT_MAX_DURATION + "): ", DEFAULT_MAX_DURATION);
            
            ArrayList<Media> recommendations = currentUser.getRecommendations(library, minRating, maxDuration);
            
            String filename = getUserInput("Enter filename for recommendations export (default: recommendations_" + currentUser.getUserId() + ".txt): ");
            if (filename.trim().isEmpty()) {
                filename = "recommendations_" + currentUser.getUserId() + ".txt";
            }
            
            currentUser.exportRecommendations(recommendations, filename, minRating, maxDuration);
            System.out.println("Recommendations exported successfully to " + filename);
        } catch (IOException e) {
            System.out.println("Error exporting recommendations: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Invalid input format. Please enter valid numbers.");
            scanner.nextLine(); // Clear invalid input
        }
    }
    
    /**
     * Displays application statistics
     */
    private static void displayStatistics() {
        System.out.println("\n" + MENU_SEPARATOR);
        System.out.println("ADUflix Statistics");
        System.out.println(MENU_SEPARATOR);
        System.out.println("Current User: " + currentUser.toString());
        System.out.println("Total Media in Library: " + library.getAllMedia().size());
        System.out.println("Total Recommendations Generated (All Users): " + RecommendationEngine.getTotalRecommendationsGenerated());
        
        // Display genre analysis for current user using only ArrayList
        ArrayList<String> genreList = RecommendationEngine.analyzeUserGenrePreferences(currentUser);
        if (!genreList.isEmpty()) {
            System.out.println("\nYour Genre Preferences:");
            
            // Count genres manually using ArrayList
            ArrayList<String> uniqueGenres = new ArrayList<>();
            ArrayList<Integer> genreCounts = new ArrayList<>();
            
            for (String genre : genreList) {
                int index = uniqueGenres.indexOf(genre);
                if (index == -1) {
                    uniqueGenres.add(genre);
                    genreCounts.add(1);
                } else {
                    genreCounts.set(index, genreCounts.get(index) + 1);
                }
            }
            
            // Create pairs for sorting and use ArrayList's sort method
            ArrayList<GenreCount> genrePairs = new ArrayList<>();
            for (int i = 0; i < uniqueGenres.size(); i++) {
                genrePairs.add(new GenreCount(uniqueGenres.get(i), genreCounts.get(i)));
            }
            
            // Sort by count (highest first) using ArrayList's sort method
            genrePairs.sort((gc1, gc2) -> Integer.compare(gc2.count, gc1.count));
            
            // Display sorted genre preferences
            for (GenreCount gc : genrePairs) {
                System.out.println("- " + gc.genre + ": " + gc.count + " items");
            }
        }
    }
    
    /**
     * Simple helper class to pair genre with count for sorting
     */
    private static class GenreCount {
        String genre;
        int count;
        
        GenreCount(String genre, int count) {
            this.genre = genre;
            this.count = count;
        }
    }
    
    /**
     * Utility method to get user input safely
     */
    private static String getUserInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
    
    /**
     * Utility method to get double input with default value
     */
    private static double getDoubleInput(String prompt, double defaultValue) {
        String input = getUserInput(prompt);
        if (input.trim().isEmpty()) {
            return defaultValue;
        }
        try {
            double value = Double.parseDouble(input.trim());
            if (value < 0.0 || value > 10.0) {
                System.out.println("Rating must be between 0.0 and 10.0. Using default: " + defaultValue);
                return defaultValue;
            }
            return value;
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format. Using default: " + defaultValue);
            return defaultValue;
        }
    }
    
    /**
     * Utility method to get integer input with default value
     */
    private static int getIntInput(String prompt, int defaultValue) {
        String input = getUserInput(prompt);
        if (input.trim().isEmpty()) {
            return defaultValue;
        }
        try {
            int value = Integer.parseInt(input.trim());
            if (value < 0) {
                System.out.println("Duration must be positive. Using default: " + defaultValue);
                return defaultValue;
            }
            return value;
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format. Using default: " + defaultValue);
            return defaultValue;
        }
    }
}
