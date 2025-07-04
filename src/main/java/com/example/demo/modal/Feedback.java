package com.example.demo.modal;

import jakarta.persistence.*;

@Entity
@Table(name = "feedback")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "student_name")
    private String studentName;

    @Column(name = "room_number")
    private String roomNumber;

    @Column(name = "description")
    private String description;

    public Feedback() {}

    public Feedback(String studentName, String roomNumber, String description) {
        this.studentName = studentName;
        this.roomNumber = roomNumber;
        this.description = description;
    }

    // Getters and setters...

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
