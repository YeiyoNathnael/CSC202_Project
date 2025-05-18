// Abstract base class representing any media item (movies, series,)
//Defines common properties and behaviors for all media types

import java.util.Objects;

public abstract class Media implements Comparable<Media> {
    // The minimum allowed rating value (0.0 = worst)
    private static final double MIN_RATING = 0.0;
    // The maximum allowed rating value (10.0 = best)
    private static final double MAX_RATING = 10.0;
    
    private String id;
    private String title;
    private String genre;
    private double rating;
    private int duration; 
    
    public Media(String id, String title, String genre, double rating, int duration) {

        this.id = id;
        this.title = title;
        this.genre = genre;
        setRating(rating);  // Uses special rating validator
        this.duration = duration;
    }

     // Constructor overloading 
    public Media(String id, String title) {
        this(id, title, "Unknown", 0.0, 0);
    }

    //Abstract method that must be implemented in child classes.
     
    public abstract String getDisplayDetails();
    
    public String getId() { 
        return id; 
        }

    public String getTitle() {
         return title; 
        }

    public String getGenre() {
         return genre; 
        }

    public double getRating() {
         return rating; 
        }

    public int getDuration() {
         return duration; 
        }
    
    public void setTitle(String title) {
         this.title = title; 
        }

    public void setGenre(String genre) {
         this.genre = genre; 
        }

    public void setDuration(int duration) {
         this.duration = duration; 
        }
     
    // Changes the rating after validating it's within 0.0-10.0 range.
    // Throws an exception if the rating is invalid.
     
    public void setRating(double rating) {
    if (rating < MIN_RATING || rating > MAX_RATING) {
        throw new IllegalArgumentException("Rating must be between " + MIN_RATING + " and " + MAX_RATING);
    }
    this.rating = rating;
    }

    // Shows a summary like: "Inception [ID: M1, Genre: Sci-fi, Rating: 8.5, Duration: 148 min]"
     
    @Override
    public String toString() {
    return title + " [ID: " + id + ", Genre: " + genre + 
           ", Rating: " + String.format("%.1f", rating) + 
           ", Duration: " + duration + " min]";

    }


   //Compares this User object with another object for equality.
   //Equality is determined solely by comparing user IDs, because:
   //User IDs are guaranteed to be unique across the system
   //Implementation Details:
   //1. First checks if comparing with self 
   //2. Verifies the other object isnt null and is same class type
   //3. Performs safe type casting after class verification
   //4. Compares the String IDs using Strings equals() method
   //Note: This implementation is consistent with hashCode(), meaning:
   //If two Users are equal (same ID), they will have same hash code
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Media media = (Media) obj;
        return id.equals(media.id);
    }
    
    // Returns a unique number based on the ID Must match equals same ID means same hash code

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    //Alphabetical Ordering:
    //Compares media items by their titles in dictionary order
    //"Avatar" would come before "Inception" because "A" comes before "I" alphabetically
    //Return Values:
    //Returns a negative number if this.title comes before other.title
    //Returns zero if titles are identical
    //Returns a positive number if this.title comes after other.title

    @Override
    public int compareTo(Media other) {
        return this.title.compareTo(other.title); 
    }
}