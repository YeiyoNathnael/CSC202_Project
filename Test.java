public class Test {
    public static void main(String[] args) {
        // Test Media and Movie classes
        Movie movie1 = new Movie("M1", "Inception", "Sci-Fi", 8.8, 148, "Christopher Nolan");
        Movie movie2 = new Movie("M2", "The Shawshank Redemption", "Drama", 9.3, 142, "Frank Darabont");
        
        // Verify abstract method implementation
        System.out.println(" Movie Details ");
        System.out.println(movie1.getDisplayDetails());
        
        // Test Comparable implementation
        System.out.println(" Comparison result: " + movie1.compareTo(movie2));
        
        // Test User class
        User user1 = new User("JohnDoe");
        System.out.println(" Testing User ");
        System.out.println(" New user created: " + user1);
        
        // Test watch history functionality
        user1.watchMedia(movie1);
        user1.watchMedia(movie2);
        user1.viewWatchHistory();
        
        // Test equals() methods
        Movie movie1Copy = new Movie("M1", "Inception", "Sci-Fi", 8.8, 148, "Christopher Nolan");
        System.out.println(" Test equality: " + movie1.equals(movie1Copy));
        
        // Test static ID counter
        User user2 = new User("JaneDoe");
        System.out.println(" Second user created: " + user2);
        
        // Test exception handling in rating validation
        try {
            movie1.setRating(11.0);
        } catch (IllegalArgumentException e) {
            System.out.println(" Caught expected exception: " + e.getMessage());
        }
    }
}