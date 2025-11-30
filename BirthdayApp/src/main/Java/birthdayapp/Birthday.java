package com.example.bday.model;

import java.time.LocalDate;

public class Birthday {
    private int id;
    private String name;
    private LocalDate dob;
    private String phone;
    private String note;

    public Birthday(int id, String name, LocalDate dob, String phone, String note) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.phone = phone;
        this.note = note;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public LocalDate getDob() { return dob; }
    public String getPhone() { return phone; }
    public String getNote() { return note; }

    public void setName(String name) { this.name = name; }
    public void setDob(LocalDate dob) { this.dob = dob; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setNote(String note) { this.note = note; }
}
