package com.example.mypets.model;

import java.time.LocalDate;

public class Pet {

    private int id;

    private String title;

    private LocalDate date;

    private String animal;

    private String race;

    private int weight;

    private String comment;

    private boolean archive;

    public Pet(int id, String title, LocalDate date, String animal, String race, int weight, String comment, boolean archive) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.animal = animal;
        this.race = race;
        this.weight = weight;
        this.comment = comment;
        this.archive = archive;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getAnimal() {
        return animal;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isArchive() {
        return archive;
    }

    public void setArchive(boolean archive) {
        this.archive = archive;
    }
}
