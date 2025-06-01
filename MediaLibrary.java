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
    public void loadFromFile(String filename) throws InvalidMediaDataException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNumber = 0;
            
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                String[] tokens = line.split(",");  // Split line by comma
                
                // Validate minimum number of fields
                if (tokens.length < 6) {
                    throw new InvalidMediaDataException(
                        "Line " + lineNumber + ": Insufficient data fields. Expected at least 6, got " + tokens.length);
                }
                
                String type = tokens[0].trim();         // Get the media type (e.g., Series)
                try {
                    // Depending on the type, construct the correct object
                    switch (type) {
                        case "Series":
                            if (tokens.length < 7) {
                                throw new InvalidMediaDataException(
                                    "Line " + lineNumber + ": Series requires 7 fields (Type,ID,Title,Genre,Rating,Duration,Seasons)");
                            }
                            addMedia(new Series(
                                    tokens[1].trim(),   //ID
                                    tokens[2].trim(),   //Title
                                    tokens[3].trim(),  //Genre
                                    Double.parseDouble(tokens[4].trim()), //Rating
                                    Integer.parseInt(tokens[5].trim()),  //Duration
                                    Integer.parseInt(tokens[6].trim()))); //Number of seasons
                            break;
                        case "Documentary":
                            addMedia(new Documentary(
                                    tokens[1].trim(),  //ID
                                    tokens[2].trim(),   //title
                                    tokens[3].trim(),   //Genre
                                    Double.parseDouble(tokens[4].trim()), //Rating
                                    Integer.parseInt(tokens[5].trim()),  //Duration
                                    tokens[6].trim()));  //Topic or subject
                            break;
                        case "Movie":
                            if (tokens.length < 7) {
                                throw new InvalidMediaDataException(
                                    "Line " + lineNumber + ": Movie requires 7 fields (Type,ID,Title,Genre,Rating,Duration,Director)");
                            }
                            addMedia(new Movie(
                                    tokens[1].trim(),   //ID
                                    tokens[2].trim(),   //Title
                                    tokens[3].trim(),   //Genre
                                    Double.parseDouble(tokens[4].trim()), //Rating
                                    Integer.parseInt(tokens[5].trim()),   //Duration
                                    tokens[6].trim()));  //Director
                            break;
                        default:
                            throw new InvalidMediaDataException(
                                "Line " + lineNumber + ": Unknown media type '" + type + "'. Expected 'Series', 'Documentary', or 'Movie'");
                    }  } catch (NumberFormatException e) {
                    throw new InvalidMediaDataException(
                        "Line " + lineNumber + ": Invalid number format in data: " + e.getMessage(), e);
                } catch (IllegalArgumentException e) {
                    throw new InvalidMediaDataException(
                        "Line " + lineNumber + ": Invalid data values: " + e.getMessage(), e);
                }
            }
        } catch (IOException e) {
            // Handles file not found or read errors
            throw new InvalidMediaDataException("Error reading file '" + filename + "': " + e.getMessage(), e);
        }
    }
    
    /*
     * Searches for media items whose title contains the given string (case-insensitive).
     * param title The search keyword
     * return A list of matched media items
     */

    public ArrayList<Media> searchByTitle(String title) {
        ArrayList<Media> results = new ArrayList<>();
        for (Media m : mediaList) {
            if (m.getTitle().toLowerCase().contains(title.toLowerCase())) {
                results.add(m);
            }
        }
        return results;
    }
    
    /*
     * Searches for media items by genre (case-insensitive).
     * @param genre The genre to search for
     * @return A list of matched media items
     */
    public ArrayList<Media> getMediaByGenre(String genre) {
        ArrayList<Media> results = new ArrayList<>();
        for (Media m : mediaList) {
            if (m.getGenre().toLowerCase().equals(genre.toLowerCase())) {
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
