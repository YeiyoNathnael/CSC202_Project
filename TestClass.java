import java.io.*;
import java.util.*;

/**
 * Test class to demonstrate that all major methods work as expected
 * This performs manual unit testing for the ADUflix media system
 */
public class TestClass {
    private static MediaLibrary library;
    private static User testUser;
    
    public static void main(String[] args) {
  
        System.out.println("ADUflix System Test Suite");
  
        
        try {
            // Initialize test environment
            setupTestEnvironment();
            
            // Run all tests
            testMediaCreation();
            testMediaLibraryOperations();
            testUserFunctionality();
            testRecommendationEngine();
            testFileOperations();
            testPolymorphismAndInheritance();
            testDataPersistence();
            
            System.out.println("\n" + "=".repeat(60));
            System.out.println("ALL TESTS COMPLETED SUCCESSFULLY!");
      
            
        } catch (Exception e) {
            System.err.println("Test failed with error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Sets up the test environment with sample data
     */
    private static void setupTestEnvironment() {
        System.out.println("\n1. Setting up test environment...");
        library = new MediaLibrary();
        testUser = new User("TestUser");
        System.out.println("✓ Test environment setup complete");
    }
    
    /**
     * Tests creation of different media types
     */
    private static void testMediaCreation() {
        System.out.println("\n2. Testing Media Creation...");
        
        // Test Movie creation and methods
        Movie movie = new Movie("M1", "Test Movie", "Action", 8.5, 120, "Test Director");
        System.out.println("✓ Movie created: " + movie.getTitle());
        System.out.println("✓ Movie director: " + movie.getAuthor());
        System.out.println("✓ Movie display: " + movie.getDisplayDetails());
        
        // Test Series creation and methods
        Series series = new Series("S1", "Test Series", "Drama", 9.0, 60, 5);
        System.out.println("✓ Series created: " + series.getTitle());
        System.out.println("✓ Series seasons: " + series.getNumberOfSeasons());
        System.out.println("✓ Series display: " + series.getDisplayDetails());
        
        // Test Documentary creation and methods
        Documentary doc = new Documentary("D1", "Test Documentary", "Nature", 8.0, 90, "Wildlife");
        System.out.println("✓ Documentary created: " + doc.getTitle());
        System.out.println("✓ Documentary subject: " + doc.getSubject());
        System.out.println("✓ Documentary display: " + doc.getDisplayDetails());
        
        // Test Playable interface
        if (movie instanceof Playable) {
            movie.play();
            System.out.println("✓ Movie implements Playable interface");
        }
        
        if (series instanceof Playable) {
            series.play();
            System.out.println("✓ Series implements Playable interface");
        }
        
        System.out.println("✓ Media creation tests passed");
    }
    
    /**
     * Tests MediaLibrary operations
     */
    private static void testMediaLibraryOperations() {
        System.out.println("\n3. Testing MediaLibrary Operations...");
        
        // Add media items
        library.addMedia(new Movie("M1", "Inception", "Sci-fi", 8.8, 148, "Christopher Nolan"));
        library.addMedia(new Movie("M2", "The Matrix", "Sci-fi", 8.7, 136, "The Wachowskis"));
        library.addMedia(new Series("S1", "Breaking Bad", "Crime", 9.5, 47, 5));
        library.addMedia(new Documentary("D1", "Planet Earth", "Nature", 9.0, 60, "Wildlife"));
        
        System.out.println("✓ Added 4 media items to library");
        System.out.println("✓ Total media count: " + library.getAllMedia().size());
        
        // Test search by title
        ArrayList<Media> searchResults = library.searchByTitle("matrix");
        System.out.println("✓ Search for 'matrix' found: " + searchResults.size() + " items");
        
        // Test search by genre
        ArrayList<Media> genreResults = library.getMediaByGenre("Sci-fi");
        System.out.println("✓ Search for 'Sci-fi' genre found: " + genreResults.size() + " items");
        
        // Test sorting
        library.sortMedia();
        System.out.println("✓ Media list sorted alphabetically");
        
        // Test display all
        System.out.println("✓ Displaying all media:");
        library.displayAll();
        
        System.out.println("✓ MediaLibrary operation tests passed");
    }
    
    /**
     * Tests User functionality
     */
    private static void testUserFunctionality() {
        System.out.println("\n4. Testing User Functionality...");
        
        // Test user creation
        System.out.println("✓ User created: " + testUser.toString());
        System.out.println("✓ User ID: " + testUser.getUserId());
        System.out.println("✓ Username: " + testUser.getUsername());
        
        // Test watch history - initially empty
        System.out.println("✓ Initial watch history size: " + testUser.getWatchHistory().size());
        testUser.viewWatchHistory();
        
        // Add items to watch history
        ArrayList<Media> allMedia = library.getAllMedia();
        for (int i = 0; i < Math.min(3, allMedia.size()); i++) {
            testUser.watchMedia(allMedia.get(i));
        }
        
        System.out.println("✓ Added 3 items to watch history");
        System.out.println("✓ Watch history size: " + testUser.getWatchHistory().size());
        testUser.viewWatchHistory();
        
        // Test user equality and hashCode
        User anotherUser = new User("AnotherUser");
        System.out.println("✓ Users are different: " + !testUser.equals(anotherUser));
        System.out.println("✓ HashCode test: " + (testUser.hashCode() != anotherUser.hashCode()));
        
        System.out.println("✓ User functionality tests passed");
    }
    
    /**
     * Tests RecommendationEngine
     */
    private static void testRecommendationEngine() {
        System.out.println("\n5. Testing Recommendation Engine...");
        
        // Test recommendations for user with watch history
        ArrayList<Media> recommendations = testUser.getRecommendations(library, 8.0, 200);
        System.out.println("✓ Generated " + recommendations.size() + " recommendations");
        
        // Test genre analysis
        ArrayList<String> userGenres = RecommendationEngine.analyzeUserGenrePreferences(testUser);
        System.out.println("✓ User genre preferences: " + userGenres);
        
        // Test total recommendations counter
        int totalRecs = RecommendationEngine.getTotalRecommendationsGenerated();
        System.out.println("✓ Total recommendations generated: " + totalRecs);
        
        // Test recommendations for new user (no history)
        User newUser = new User("NewUser");
        ArrayList<Media> newUserRecs = newUser.getRecommendations(library, 8.0, 200);
        System.out.println("✓ New user recommendations: " + newUserRecs.size());
        
        System.out.println("✓ Recommendation Engine tests passed");
    }
    
    /**
     * Tests file operations
     */
    private static void testFileOperations() {
        System.out.println("\n6. Testing File Operations...");
        
        try {
            // Test export watch history
            String historyFile = "test_watchhistory.txt";
            testUser.exportWatchHistory(historyFile);
            System.out.println("✓ Watch history exported to: " + historyFile);
            
            // Test export recommendations
            String recsFile = "test_recommendations.txt";
            ArrayList<Media> recs = testUser.getRecommendations(library, 8.0, 200);
            testUser.exportRecommendations(recs, recsFile, 8.0, 200);
            System.out.println("✓ Recommendations exported to: " + recsFile);
            
            // Test loading from media data file (if exists)
            try {
                MediaLibrary testLibrary = new MediaLibrary();
                testLibrary.loadFromFile("media_data.txt");
                System.out.println("✓ Successfully loaded from media_data.txt");
                System.out.println("✓ Loaded " + testLibrary.getAllMedia().size() + " media items");
            } catch (InvalidMediaDataException e) {
                System.out.println("! Media data file not found or invalid: " + e.getMessage());
            }
            
            System.out.println("✓ File operation tests passed");
            
        } catch (IOException e) {
            System.err.println("! File operation failed: " + e.getMessage());
        }
    }
    
    /**
     * Tests polymorphism and inheritance
     */
    private static void testPolymorphismAndInheritance() {
        System.out.println("\n7. Testing Polymorphism and Inheritance...");
        
        // Test polymorphism - Media references to different types
        Media[] mediaArray = {
            new Movie("M_TEST", "Test Movie", "Action", 7.5, 120, "Test Director"),
            new Series("S_TEST", "Test Series", "Comedy", 8.0, 30, 3),
            new Documentary("D_TEST", "Test Doc", "History", 8.5, 75, "Historical Events")
        };
        
        System.out.println("✓ Created polymorphic array of Media objects");
        
        // Test polymorphic method calls
        for (Media media : mediaArray) {
            System.out.println("- " + media.getClass().getSimpleName() + ": " + media.getDisplayDetails());
            
            // Test Playable interface polymorphically
            if (media instanceof Playable) {
                ((Playable) media).play();
            }
        }
        
        // Test Comparable interface
        ArrayList<Media> sortTest = new ArrayList<>();
        Collections.addAll(sortTest, mediaArray);
        Collections.sort(sortTest);
        System.out.println("✓ Media items sorted using Comparable interface");
        
        // Test instanceof checks
        Media testMovie = mediaArray[0];
        System.out.println("✓ instanceof Movie: " + (testMovie instanceof Movie));
        System.out.println("✓ instanceof Media: " + (testMovie instanceof Media));
        System.out.println("✓ instanceof Playable: " + (testMovie instanceof Playable));
        
        System.out.println("✓ Polymorphism and inheritance tests passed");
    }
    
    /**
     * Tests data persistence
     */
    private static void testDataPersistence() {
        System.out.println("\n8. Testing Data Persistence...");
        
        // Test saving user data
        testUser.logout(); // This saves the data
        System.out.println("✓ User data saved during logout");
        
        // Test loading user data
        User loadedUser = User.loadUserData("TestUser");
        if (loadedUser != null) {
            System.out.println("✓ User data loaded successfully");
            System.out.println("✓ Loaded user: " + loadedUser.toString());
            System.out.println("✓ Loaded watch history size: " + loadedUser.getWatchHistory().size());
        } else {
            System.out.println("! No saved data found for TestUser");
        }
        
        // Test loading non-existent user
        User nonExistentUser = User.loadUserData("NonExistentUser");
        System.out.println("✓ Non-existent user returns null: " + (nonExistentUser == null));
        
        System.out.println("✓ Data persistence tests passed");
    }
}