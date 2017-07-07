package edu.gatech;

import java.io.File;
import java.util.HashSet;

public interface RatingsInterface {

    public void initialize(File database);
    public HashSet<Rating> getRatings();
}
