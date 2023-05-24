package org.example;

import java.util.List;

public class Director {
    private int id;
    private String name;
    private int birthYear;

    public Director(int id, String name, int birthYear) {
        this.id = id;
        this.name = name;
        this.birthYear = birthYear;
    }

    public Director (List<String> line) {
        this.id = Integer.parseInt(line.get(0));
        this.name = line.get(1);
        this.birthYear = Integer.parseInt(line.get(2));
    }

    public int getId () {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }
}
