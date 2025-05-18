import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
    
    /**
     * User class with watch history functionality
     * Uses static counter for auto-ID generation
     */
public class User {
    private static int idCounter = 1; // Counter to generate unique user IDs automatically
        
    private final String userId;
    private String username;
    private List<Media> watchHistory;
    
     //Creates a new user with the given name.
     //Automatically generates a unique ID ("User1", "User2")
     //Starts with an empty watch history.
    public User(String username) {
        this.userId = "User" + idCounter++;
        this.username = username;
        this.watchHistory = new ArrayList<>();
        }
        
     // Constructor overloading
    public User(String userId, String username, List<Media> watchHistory) {
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
    public List<Media> getWatchHistory() {
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
}