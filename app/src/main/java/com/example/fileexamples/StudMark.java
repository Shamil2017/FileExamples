package com.example.fileexamples;

import androidx.annotation.NonNull;

public class StudMark {
    private String name;
    private String subject;
    private int grade;

    public StudMark(String name, String subject, int grade) {
        this.name = name;
        this.subject = subject;
        this.grade = grade;
    }

    @NonNull
    @Override
    public String toString() {
        return name+ " получил по "+subject+" оценку "+grade;
    }

    public String getName() {
        return name;
    }

    public String getSubject() {
        return subject;
    }

    public int getGrade() {
        return grade;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
