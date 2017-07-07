package edu.gatech;

import java.io.File;
import java.util.HashSet;

public interface AssignmentsInterface {

    public void initialize(File database);
    public HashSet<Assignment> getAssignments();
}
