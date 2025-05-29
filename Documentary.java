
/*
Documentary class that extends the Media Class and Implements the Playable interface;
      ---- also introduce unique attribute like subject .
 */

public class Documentary extends Media implements Playable {
    private String subject;
    // constructor
    public Documentary(String id, String title, String genre, double rating, int duration, String subject) {
        super(id, title, genre, rating, duration);
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }
// setter
    public void setSubject(String subject) {
        this.subject = subject;
    }
    // Implementing the abstract method of Media class and as well calling the toString method of super class(Media) and appending the new attribute to it.
    @Override
    public String getDisplayDetails() {
        return super.toString() + ", Subject: " + subject;
    }
    // Implementing method of Playable interface as well print respective title.
    @Override
    public void play() {
        System.out.println("Playing documentary: " + getTitle());
    }

}
