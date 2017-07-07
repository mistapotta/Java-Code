## Changed Deliverable 3 Tests

### AssignmentsTest

#### testRefresh()

This method was modified to refresh the data from a separate copy of the Excel file that had been updated with additional records. This allowed simulating modification to the original file without actually changing it and allowed the test to pass successfully.

### AssignmentTest

#### testGetAssignmentGrade()

This method was modified because one of the expected Assignment Grades for Sheree Gadow was incorrect (it was 85 instead of 90).

### GradesDBTest2

#### testGetAllGrades()

This method is completely new and tests the Extra Credit getAllGrades() method that was added for Deliverable 3.

#### testGetAverageGrade()

This method is completely new and tests the Extra Credit getAverageGrade() method that was added for Deliverable 3.

#### testGetMedianGrade()

This method is completely new and tests the Extra Credit getMedianGrade() method that was added for Deliverable 3.

#### testGetStudentInfo()

This method was modified with just a few corrections to the output since the test string didn't actually match the output string that was being created (there were a few newlines and spaces missing).

### GradesToolGUITest

All of the test methods in GradesToolGUITest were updated in Deliverable 3.

### ProjectsTest

#### testRefresh()

This method was modified to refresh the data from a separate copy of the Excel file that had been updated with additional records. This allowed simulating modification to the original file without actually changing it and allowed the test to pass successfully.

### RatingsTest

#### testRefresh()

This method was modified to refresh the data from a separate copy of the Excel file that had been updated with additional records. This allowed simulating modification to the original file without actually changing it.

Additionally, there was a mistake in the assertion statement (we were asserting that the new number of ratings was greater than 3, when it should have actually been greater than 42). 

After we had made those corrections, the test was able to pass successfully.

### RatingTest

#### testGetTeam()

This method was modified with a slightly different call to get the Team Number and Project Number associated with the rating.

Originally, our intention was to save the entire Team object within the Rating but since that would add additional complexity (making sure the Team object had already been created) we opted to instead simply save the Team Number and Project Number directly within the Rating object.

```
Original Line:
	if (r.getTeam().getTeamNumber() == 2 || r.getTeam().getProjectNumber() == 2)
Modified Line:			
	if (r.getTeamNumber() == 2 || r.getProjectNumber() == 2)
```

### StudentTest

#### testGetTeams()

This method was modified for multiple reasons. 

The first, was the call to getName() it used, seemed to be referring to a method that belonged to TestCase, instead of to the internal Student object that had been created. So this needed to be corrected otherwise the test would always fail.

The other reason, though we aren't entirely sure if this approach is better, is the loop through the Teams had an assertion inside. So we modified the test so that a true or a false result was created within the loop, and then a single assertion occurred after the loop had finished to confirm that the student did in fact exist in all of the Team objects.

### TeamsTest

#### testRefresh()

This method was modified to refresh the data from a separate copy of the Excel file that had been updated with additional records. This allowed simulating modification to the original file without actually changing it and allowed the test to pass successfully.

### TeamTest

#### testGetRatings()

This method was modified because once we finally had the Rating and Ratings classes available so the test could run properly, we discovered that the comparisons for 7.33 and 8.67 didn't equal the double values contained within the associated Rating objects (those looked more like 7.33333333333333 and 8.66666666666667) so we changed the comparisons for those two values to a range instead, which helped the test to pass properly.