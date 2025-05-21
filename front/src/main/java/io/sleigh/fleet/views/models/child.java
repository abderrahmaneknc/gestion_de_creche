package io.sleigh.fleet.views.models;

public class child {
    private int id;
    private String name;
    private int age;
    private String classroom;

    public child(int id, String name, int age, String classroom) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.classroom = classroom;
    }

    // Getters and Setters
    public int getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getClassroom() { return classroom; }
}
