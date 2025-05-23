import java.io.*; // for file output and input (BufferedReader, FileReader)
import java.util.*; // for arraylist , list , and collection

// MediaLibrary: the core class for managing a collection of Media objects (Series, Documentaries)
public class MediaLibrary {
    //  this ArrayList to store all media items (polymorphic: can hold Series, Documentary)
    private ArrayList<Media> mediaList = new ArrayList<>();

    // method to add the media items to mediaList
    public void addMedia(Media media) {
        mediaList.add(media);
    }
    // method to remove the media items from mediaList
    public void removeMedia(Media media) {
        mediaList.remove(media);
    }

    /*
     * Loads media data from a CSV-like file. Each line represents a media entry.
     * File format expected:
     *   Series,ID,Title,Genre,Rating,Duration,Seasons
     *   Documentary,ID,Title,Genre,Rating,Duration,Topic
     * This method uses BufferedReader to read each line, splits the line by commas,
     * identifies the media type, and constructs the corresponding object.
     */
    public void loadFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");  // Split line by comma
                String type = tokens[0];         // Get the media type (e.g., Series)
                // Depending on the type, construct the correct object
                switch (type) {
                    case "Series":
                        addMedia(new Series(
                                tokens[1],   //ID
                                tokens[2],   //Title
                                tokens[3],  //Genre
                                Double.parseDouble(tokens[4]), //Rating
                                Integer.parseInt(tokens[5]),  //Duration
                                Integer.parseInt(tokens[6]))); //Number of seasons
                        break;
                    case "Documentary":
                        addMedia(new Documentary(
                                tokens[1],  //ID
                                tokens[2],   //title
                                tokens[3],   //Genre
                                Double.parseDouble(tokens[4]), //Rating
                                Integer.parseInt(tokens[5]),  //Duration
                                tokens[6]));  //Topic or subject
                        break;

                }
            }
        } catch (IOException e) {
            // Handles file not found or read errors
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
    /*
     * Searches for media items whose title contains the given string (case-insensitive).
     * param title The search keyword
     * return A list of matched media items
     */

    public List<Media> searchByTitle(String title) {
        List<Media> results = new ArrayList<>();
        for (Media m : mediaList) {
            if (m.getTitle().toLowerCase().contains(title.toLowerCase())) {
                results.add(m);
            }
        }
        return results;
    }
    /*
     * Sorts the media list using natural ordering (defined in Media class via Comparable interface).
     * This requires `Media` to implement Comparable<Media>.
     */
    public void sortMedia() {
        Collections.sort(mediaList);
    }

    /*
     * Displays details of all media items in the library.
     * This calls each media's `getDisplayDetails()` method.
     */
    public void displayAll() {
        for (Media m : mediaList) {
            System.out.println(m.getDisplayDetails());
        }
    }
  // to  get  the complete list of all media items.
    public ArrayList<Media> getAllMedia() {
        return mediaList;
    }
}
