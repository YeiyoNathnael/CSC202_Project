

import java.util.Objects;

// Concrete class representing a Movie, which is a specific type of Media
// Inherits from Media and adds movie  properties
 
public class Movie extends Media implements Playable{
    private String director;  

    public Movie(String id, String title, String genre, double rating, int duration, String director) {
        super(id, title, genre, rating, duration);
        this.director = director;
    }

    // Provides detailed movie information in a formatted string 
    //Overrides abstract method from Media class
    //returns  string with all movie details

    @Override
    public String getDisplayDetails() {
        return "Movie: " + getTitle() + 
               "Director: " + director + 
               "Genre: " + getGenre() +
               "Rating: " + getRating() + 
               "Duration: " + getDuration() + " mins";
    }

    public String getAuthor() {
        return director; 
    }

    public void setdirector(String director) {
        this.director = director; 
    }
    
    // Shows a summary like: "Inception [ID: M1, Genre: Sci-fi, Rating: 8.5, Duration: 148 min]"
    @Override
    public String toString() {
        return super.toString() + ", Director: " + director;
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
        if (!super.equals(obj)) return false;
        Movie movie = (Movie) obj;
        return director.equals(movie.director);
    }
    
    // Returns a unique number based on the ID Must match equals same ID means same hash code
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), director);
    }
     public void play() {
        System.out.println("Playing Movie: " + getTitle());
    }
}