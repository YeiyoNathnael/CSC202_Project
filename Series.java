/*
Series class that extends the Media Class and Implements the Playble interface;
      ---- also introduce unique attribute like number of the seasons .
 */
public class Series extends Media implements Playable{
    private int numberOfSeasons;
// constructor
    public Series(String id, String title, String genre, double rating, int duration, int numberOfSeasons) {
        super(id, title, genre, rating, duration);
        this.numberOfSeasons = numberOfSeasons;
    }
//getter for the encapsilated attribute
    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }
    //setter for the encapsilated attribute
    public void setNumberOfSeasons(int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }
// Implementing method of Playable interface and Printing the title
    @Override
    public void play() {
        System.out.println("Playing series: " + getTitle());
    }
// Implementing the abstract method of Media class and as well calling the toString method of super class(Media) and appending the new attribute to it.
    @Override
    public String getDisplayDetails() {
        return super.toString() + ", Seasons: " + numberOfSeasons;
    }

}
