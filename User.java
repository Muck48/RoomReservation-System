public class User implements Displayable {
    private String firstName;
    private String lastName;
    private String username;

    public User(String firstName, String lastName, String username) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String displayDetails() {
        return firstName + " " + lastName + " (" + username + ")";
    }
}
