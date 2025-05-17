# CSC202_Project

Project Class Structure
Here's a list of the classes needed for the ADUflix project:

Media (Abstract Class):

Description: Base class for all media items.
Attributes: title (String), genre (String), rating (double/int), duration (int, e.g., in minutes), id (String/int, possibly static for auto-generation).
Methods:
Abstract method like getDisplayDetails() or play() (to be overridden by subclasses).
Getters and setters for all private fields.
toString() (overridden).
equals(Object obj) (overridden).
compareTo(Media other) (from Comparable interface).
Constructor(s), potentially overloaded.
Movie (Concrete Class, extends Media):

Description: Represents a movie.
Attributes: Any unique properties for movies (e.g., director (String), releaseYear (int)).
Methods:
Implementation of abstract method(s) from Media.
Getters and setters for its unique properties.
toString() (overridden).
equals(Object obj) (overridden).
Constructor(s) using super(), potentially overloaded.
Series (Concrete Class, extends Media):

Description: Represents a TV series.
Attributes: Any unique properties for series (e.g., numberOfSeasons (int), episodesPerSeason (int), isOngoing (boolean)).
Methods:
Implementation of abstract method(s) from Media.
Getters and setters for its unique properties.
toString() (overridden).
equals(Object obj) (overridden).
Constructor(s) using super(), potentially overloaded.
Documentary (Concrete Class, extends Media):

Description: Represents a documentary.
Attributes: Any unique properties for documentaries (e.g., subject (String), narrator (String)).
Methods:
Implementation of abstract method(s) from Media.
Getters and setters for its unique properties.
toString() (overridden).
equals(Object obj) (overridden).
Constructor(s) using super(), potentially overloaded.
User (Concrete Class):

Description: Represents a user of the platform.
Attributes: userId (String/int, possibly using a static counter for auto-generation), username (String), watchHistory (ArrayList of Media).
Methods:
watchMedia(Media item): Adds media to watch history.
viewWatchHistory(): Displays watch history.
getRecommendations(MediaLibrary library, double minRating, int maxDuration): Triggers recommendation logic.
Getters and setters.
toString() (overridden).
equals(Object obj) (overridden).
Constructor(s).
MediaLibrary (Concrete Class - Logic Related):

Description: Manages the collection of all available media items.
Attributes: allMedia (ArrayList of Media).
Methods:
addMedia(Media item).
removeMedia(Media item).
findMediaByTitle(String title).
loadMediaFromFile(String filePath): Reads media data from a text file.
getMediaByGenre(String genre).
sortMedia() (e.g., by rating, title using Comparable).
Getters.
Constructor(s).
RecommendationEngine (Concrete Class - Logic Related):

Description: Contains the logic for generating personalized media recommendations.
Methods:
generateRecommendations(User user, MediaLibrary library, double minRating, int maxDuration): Returns an ArrayList of recommended Media items based on watched genres and filters.
Static helper methods if applicable.
CustomException (e.g., InvalidInputException or MediaNotFoundException):

Description: A custom exception class to handle specific error conditions.
Methods: Standard exception constructors.
Interfaces (at least 2 required):

Playable Interface:
Method: void startPlayback(); (or similar). Implemented by Media subclasses.
Rateable Interface:
Method: void submitRating(double rating);. Implemented by Media subclasses if ratings can be updated post-creation.
ADUflixApp / Main / Test (Concrete Class):

Description: Main class to run the application, simulate user interactions via the console, and test functionalities.
Methods:
main(String[] args).
Methods to handle user input and display output.
Test methods for demonstrating functionality of other classes.
