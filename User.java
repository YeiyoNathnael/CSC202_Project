import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * User class with watch history functionality
 * Uses static counter for auto-ID generation
 */
public class User {
    private static int idCounter = 1; // Counter to generate unique user IDs automatically
        
    private final String userId;
    private String username;
    private ArrayList<Media> watchHistory;
    
     //Creates a new user with the given name.
     //Automatically generates a unique ID ("User1", "User2")
     //Starts with an empty watch history.
    public User(String username) {
        this.userId = "User" + idCounter++;
        this.username = username;
        this.watchHistory = new ArrayList<>();
        }
        
     // Constructor overloading
    public User(String userId, String username, ArrayList<Media> watchHistory) {
        this.userId = userId;
        this.username = username;
        this.watchHistory = new ArrayList<>(watchHistory);
        }
        
     // Adds a media item to the users watch history.
     //Only adds if the media item exists
    public void watchMedia(Media item) {
        if (item != null) {
            watchHistory.add(item);
            }
        }
        
      //Displays the users complete watch history in the console.
      //If the history is empty, shows: "[username]'s watch history is empty."
      //For non-empty history, displays each item with bullet points:
      //"[username]s Watch History:"
      //"- [media1]"
      //"- [media2]"
    public void viewWatchHistory() {
        if (watchHistory.isEmpty()) {
                System.out.println(username + "'s watch history is empty.");
                return;
        }
        System.out.println(username + "'s Watch History:");
        for (Media item : watchHistory) {
            System.out.println("- " + item);
        }
    }
    
    /**
     * Exports the user's watch history to a text file
     * @param filename The name of the file to write to
     * @throws IOException If there's an error writing to the file
     */
    public void exportWatchHistory(String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("Watch History for: " + username + " (ID: " + userId + ")");
            writer.println("Export Date: " + new java.util.Date());
            writer.println("Total Items Watched: " + watchHistory.size());
            writer.println("=" + "=".repeat(50));
            
            if (watchHistory.isEmpty()) {
                writer.println("No items in watch history.");
            } else {
                for (int i = 0; i < watchHistory.size(); i++) {
                    Media item = watchHistory.get(i);
                    writer.println((i + 1) + ". " + item.getDisplayDetails());
                }
            }
            
            writer.println("=" + "=".repeat(50));
            writer.println("End of Watch History");
        }
    }
    
    /**
     * Exports personalized recommendations to a text file
     * @param recommendations List of recommended media items
     * @param filename The name of the file to write to
     * @param minRating The minimum rating filter used
     * @param maxDuration The maximum duration filter used
     * @throws IOException If there's an error writing to the file
     */
    public void exportRecommendations(ArrayList<Media> recommendations, String filename, 
                                    double minRating, int maxDuration) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("Personalized Recommendations for: " + username + " (ID: " + userId + ")");
            writer.println("Generated Date: " + new java.util.Date());
            writer.println("Filter Criteria - Min Rating: " + minRating + ", Max Duration: " + maxDuration + " mins");
            writer.println("Total Recommendations: " + recommendations.size());
            writer.println("=" + "=".repeat(50));
            
            if (recommendations.isEmpty()) {
                writer.println("No recommendations found matching your criteria.");
                writer.println("Try adjusting your filters or watching more content to improve recommendations.");
            } else {
                writer.println("Based on your viewing history, we recommend:");
                writer.println();
                
                for (int i = 0; i < recommendations.size(); i++) {
                    Media item = recommendations.get(i);
                    writer.println((i + 1) + ". " + item.getDisplayDetails());
                }
            }
            
            writer.println("=" + "=".repeat(50));
            writer.println("End of Recommendations");
        }
    }
    
    /**
     * Gets personalized recommendations using the RecommendationEngine
     * @param library The media library to search through
     * @param minRating Minimum rating filter
     * @param maxDuration Maximum duration filter
     * @return List of recommended media items
     */
    public ArrayList<Media> getRecommendations(MediaLibrary library, double minRating, int maxDuration) {
        return RecommendationEngine.generateRecommendations(this, library, minRating, maxDuration);
    }
        
    public String getUserId() {
        return userId; 
    }

    public String getUsername() {
        return username; 
    }

    public void setUsername(String username) {
        this.username = username; 
    }
    
    //Returns a copy of the watch history
    //Other classes can see whats been watched but can't change  list.
    public ArrayList<Media> getWatchHistory() {
        return new ArrayList<>(watchHistory); 
    }
        
     //Generates a user-friendly summary string of this User object.
     //The returned string follows this exact format:
     //"User [ID: (userId), Username: (username), Watched Items: (count)]"
     //Example output:
     //"User [ID: U5, Username: Ayyub, Watched Items: 12]"
    @Override
    public String toString() {
        return "User [ID: " + userId + 
       ", Username: " + username + 
       ", Watched Items: " + watchHistory.size() + "]";
    }
        
    
   //Compares this User object with another object for equality.
   //Equality is determined solely by comparing user IDs, because:
   //User IDs are guaranteed to be unique across the system
   //Implementation Details:
   //1. First checks if comparing with self (optimization)
   //2. Verifies the other object isnt null and is same class type
   //3. Performs safe type casting after class verification
   //4. Compares the String IDs using Strings equals() method
   //Note: This implementation is consistent with hashCode(), meaning:
   //If two Users are equal (same ID), they will have same hash code
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return userId.equals(user.userId);
    }
        
     // Returns a unique number based on the ID Must match equals same ID means same hash code
    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
    
    /**
     * Saves the user's watch history to a persistent file
     * @throws IOException If there's an error writing to the file
     */
    private void saveWatchHistory() throws IOException {
        String filename = "userdata_" + username + ".txt";
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("USER_DATA");
            writer.println("Username:" + username);
            writer.println("UserId:" + userId);
            writer.println("WatchHistory:");
            
            for (Media item : watchHistory) {
                // Save in format: MediaType,ID,Title,Genre,Rating,Duration,ExtraInfo
                if (item instanceof Series) {
                    Series series = (Series) item;
                    writer.println("Series," + item.getId() + "," + item.getTitle() + "," + 
                                 item.getGenre() + "," + item.getRating() + "," + 
                                 item.getDuration() + "," + series.getNumberOfSeasons());
                } else if (item instanceof Documentary) {
                    Documentary doc = (Documentary) item;
                    writer.println("Documentary," + item.getId() + "," + item.getTitle() + "," + 
                                 item.getGenre() + "," + item.getRating() + "," + 
                                 item.getDuration() + "," + doc.getSubject());
                } else if (item instanceof Movie) {
                    Movie movie = (Movie) item;
                    writer.println("Movie," + item.getId() + "," + item.getTitle() + "," + 
                                 item.getGenre() + "," + item.getRating() + "," + 
                                 item.getDuration() + "," + movie.getAuthor());
                }
            }
        }
    }
    
    /**
     * Loads the user's watch history from a persistent file
     * @param username The username to load data for
     * @return User object with loaded watch history, or null if file doesn't exist
     */
    public static User loadUserData(String username) {
        String filename = "userdata_" + username + ".txt";
        File file = new File(filename);
        
        if (!file.exists()) {
            return null; // No saved data for this user
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line = reader.readLine();
            if (!"USER_DATA".equals(line)) {
                return null; // Invalid file format
            }
            
            String savedUsername = null;
            String savedUserId = null;
            ArrayList<Media> savedHistory = new ArrayList<>();
            
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Username:")) {
                    savedUsername = line.substring(9);
                } else if (line.startsWith("UserId:")) {
                    savedUserId = line.substring(7);
                } else if (line.equals("WatchHistory:")) {
                    // Read watch history entries
                    while ((line = reader.readLine()) != null) {
                        String[] tokens = line.split(",");
                        if (tokens.length >= 6) {
                            try {
                                String type = tokens[0].trim();
                                String id = tokens[1].trim();
                                String title = tokens[2].trim();
                                String genre = tokens[3].trim();
                                double rating = Double.parseDouble(tokens[4].trim());
                                int duration = Integer.parseInt(tokens[5].trim());
                                
                                Media media = null;
                                switch (type) {
                                    case "Series":
                                        if (tokens.length >= 7) {
                                            int seasons = Integer.parseInt(tokens[6].trim());
                                            media = new Series(id, title, genre, rating, duration, seasons);
                                        }
                                        break;
                                    case "Documentary":
                                        if (tokens.length >= 7) {
                                            String subject = tokens[6].trim();
                                            media = new Documentary(id, title, genre, rating, duration, subject);
                                        }
                                        break;
                                    case "Movie":
                                        if (tokens.length >= 7) {
                                            String director = tokens[6].trim();
                                            media = new Movie(id, title, genre, rating, duration, director);
                                        }
                                        break;
                                }
                                
                                if (media != null) {
                                    savedHistory.add(media);
                                }
                            } catch (NumberFormatException e) {
                                // Skip invalid entries
                                System.out.println("Skipping invalid watch history entry: " + line);
                            } catch (IllegalArgumentException e) {
                                // Skip invalid entries
                                System.out.println("Skipping invalid watch history entry: " + line);
                            }
                        }
                    }
                }
            }
            
            if (savedUsername != null && savedUserId != null) {
                User user = new User(savedUserId, savedUsername, savedHistory);
                return user;
            }
            
        } catch (IOException e) {
            System.out.println("Error loading user data: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Saves user data when logging out
     */
    public void logout() {
        try {
            saveWatchHistory();
        } catch (IOException e) {
            System.out.println("Warning: Could not save watch history: " + e.getMessage());
        }
    }
}