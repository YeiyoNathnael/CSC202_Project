import java.util.*;

/**
 * RecommendationEngine provides personalized media recommendations
 * based on user's watch history, genre preferences, and filtering criteria
 */
public class RecommendationEngine {
    

    // Static counter to track total recommendations generated across all users
    private static int totalRecommendationsGenerated = 0;
    
    /**
     * Generates personalized recommendations for a user based on their watch history
     * @param user The user to generate recommendations for
     * @param library The media library to search through
     * @param minRating Minimum rating filter for recommendations
     * @param maxDuration Maximum duration filter for recommendations
     * @return List of recommended media items
     */
    public static ArrayList<Media> generateRecommendations(User user, MediaLibrary library, 
                                                     double minRating, int maxDuration) {
        if (user == null || library == null) {
            return new ArrayList<>();
        }
        
        ArrayList<Media> recommendations = new ArrayList<>();
        ArrayList<Media> watchHistory = user.getWatchHistory();
        ArrayList<String> watchedIds = new ArrayList<>();
        ArrayList<String> userGenres = new ArrayList<>();
        
        // Build watched items list and genre preferences using only ArrayList
        for (Media watchedItem : watchHistory) {
            watchedIds.add(watchedItem.getId());
            String genre = watchedItem.getGenre();
            if (!userGenres.contains(genre)) {
                userGenres.add(genre);
            }
        }
        
        // If user has no watch history, recommend highest rated items
        if (watchHistory.isEmpty()) {
            return getTopRatedMedia(library, minRating, maxDuration, 5);
        }
        
        // Generate recommendations based on genre preferences
        for (Media media : library.getAllMedia()) {
            if (shouldRecommend(media, watchedIds, userGenres, minRating, maxDuration)) {
                recommendations.add(media);
            }
        }
        
        // Sort recommendations by rating (highest first) using ArrayList's sort method
        recommendations.sort((m1, m2) -> Double.compare(m2.getRating(), m1.getRating()));
        
        totalRecommendationsGenerated += recommendations.size();
        return recommendations;
    }
    
    /**
     * Helper method to determine if a media item should be recommended
     */
    private static boolean shouldRecommend(Media media, ArrayList<String> watchedIds, 
                                         ArrayList<String> userGenres, 
                                         double minRating, int maxDuration) {
        // Don't recommend already watched items
        if (watchedIds.contains(media.getId())) {
            return false;
        }
        
        // Check rating and duration filters
        if (media.getRating() < minRating || media.getDuration() > maxDuration) {
            return false;
        }
        
        // Check if genre matches user preferences
        return userGenres.contains(media.getGenre());
    }
    
    /**
     * Gets top-rated media items for users with no watch history
     */
    private static ArrayList<Media> getTopRatedMedia(MediaLibrary library, double minRating, 
                                              int maxDuration, int limit) {
        ArrayList<Media> topRated = new ArrayList<>();
        
        for (Media media : library.getAllMedia()) {
            if (media.getRating() >= minRating && media.getDuration() <= maxDuration) {
                topRated.add(media);
            }
        }
        
        // Sort using ArrayList's built-in sort method (highest rating first)
        topRated.sort((m1, m2) -> Double.compare(m2.getRating(), m1.getRating()));
        
        // Return top 'limit' items without using subList
        ArrayList<Media> result = new ArrayList<>();
        int count = Math.min(limit, topRated.size());
        for (int i = 0; i < count; i++) {
            result.add(topRated.get(i));
        }
        return result;
    }
    
    /**
     * Static method to get total recommendations generated across all users
     * @return Total number of recommendations generated
     */
    public static int getTotalRecommendationsGenerated() {
        return totalRecommendationsGenerated;
    }
    
    /**
     * Static method to reset the recommendation counter (for testing purposes)
     */
    public static void resetRecommendationCounter() {
        totalRecommendationsGenerated = 0;
    }
    
    /**
     * Utility method to analyze user's genre distribution using only ArrayList
     * @param user The user to analyze
     * @return ArrayList of genre strings (duplicates indicate frequency)
     */
    public static ArrayList<String> analyzeUserGenrePreferences(User user) {
        ArrayList<String> genreList = new ArrayList<>();
        
        for (Media media : user.getWatchHistory()) {
            genreList.add(media.getGenre());
        }
        
        return genreList;
    }
}