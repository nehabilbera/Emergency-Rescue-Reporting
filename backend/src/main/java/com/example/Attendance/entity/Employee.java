package com.example.Attendance.entity;
 
import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

import java.util.Random;
 
@Entity

public class Employee {
 
    @Id

    private Long id; // ID will be generated randomly before persisting
 
    private String name;

    private String mobile;

    private String dob;

    private String bloodGroup;

    private String medicalIssue;

    private String address;

    private String alternateMobile;
 
    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)

    @JsonIgnore

    private List<Attendance> attendances;
 
    // PrePersist method to generate a random ID before persisting the entity

    @PrePersist

    private void generateRandomId() {

        if (this.id == null) { 

            Random random = new Random();

            long min = 2111111L;

            long max = 2999999L;

            long generatedId = min + random.nextInt((int) (max - min + 1));  

            this.id = generatedId;

        }

    }
 
    // Getters and Setters

    public Long getEmployeeId() {

        return id;

    }
 
    public void setEmployeeId(Long id) {

        this.id = id;

    }
 
    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
 
    public String getPhone_number() { return mobile; }

    public void setPhone_number(String mobile) { this.mobile = mobile; }
 
    public String getDob() { return dob; }

    public void setDob(String dob) { this.dob = dob; }
 
    public String getBlood_group() { return bloodGroup; }

    public void setBlood_group(String bloodGroup) { this.bloodGroup = bloodGroup; }
 
    public String getMedical_issue() { return medicalIssue; }

    public void setMedical_issue(String medicalIssue) { this.medicalIssue = medicalIssue; }
 
    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }
 
    public String getAlternate_phone_number() { return alternateMobile; }

    public void setAlternate_phone_number(String alternate_phone_number) { this.alternateMobile = alternate_phone_number; }
 
    public List<Attendance> getAttendances() { return attendances; }

    public void setAttendances(List<Attendance> attendances) { this.attendances = attendances; }

}

 