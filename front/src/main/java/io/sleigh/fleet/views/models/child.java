import java.time.LocalDate;

public  class child {
    private final Long id;
    private final String firstName;
    private final String lastName;
    private final LocalDate birthday;
    private final String grade;

    public child(Long id, String firstName, String lastName, LocalDate birthday, String grade) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.grade = grade;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public String getGrade() {
        return grade;
    }
}