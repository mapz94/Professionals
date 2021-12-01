package com.example.professionals.models;

import androidx.annotation.NonNull;

@Table(table_name = "professionals")
public class Professional extends Model {
    private String name;
    private String profession;
    private String salary;


    public Professional(String id, String name, String profession, String salary) {
        super.setId(id);
        this.name=name;
        this.profession=profession;
        this.salary = salary;
    }
    public Professional() {
    }

    @Override
    public String getId() {
        return super.getId();
    }

    @Override
    public void setId(String id) {
        super.setId(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        salary = salary;
    }

    @NonNull
    @Override
    public String toString() {
        return this.name + ", " + this.profession;
    }
}
