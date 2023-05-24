package org.example;

import java.util.List;

public class Actor {
    private int id;
    private String name;
    private int birthYear;

    public Actor(int id, String name, int birthYear) {
        this.id = id;
        this.name = name;
        this.birthYear = birthYear;
    }

    public String getName() {
        return name;
    }

    public Actor (List<String> line) {
        this.id = Integer.parseInt(line.get(0));
        this.name = line.get(1);
        this.birthYear = Integer.parseInt(line.get(2));
    }

    public int getId () {
        return this.id;
    }


}
