package edu.gatech;

import java.io.File;
import java.util.HashSet;

public interface StudentsInterface {

    public void initialize(File database);
    public HashSet<Student> getStudents();
}
