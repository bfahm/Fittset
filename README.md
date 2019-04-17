# Fittset
### Training companion and note taking application.
An android application for saving data about your workout in a stylish and intuitive way instead of using a regular note taking application.

**Currently:** the application supports all the CRUD operations and can sort exercises depending on muscle group only. Custom Routines to be added in the next update.

*Application is still under construction and further additions.*

#### Next Target(s) - in the next master merge:

 - Keep log of all exercises inserted/updated in a new table for later usage of statistical graphs and workout history
  
	|exId|exName|latestWeight|date|
	|--|--|--|--|
	|...|...  |...  |...	|

 - Implement custom routines were exericses are attached to separate routines (mix of muscle groups)

	|rroutineId|routineName|exerciseId|exerciseName|muscleGroup|
	|--|--|--|--|--|
	|...|...  |...  |...	|...|
- All tables should be linked together (via `foreign key`)
- Implement those changes in a new `Stats Activity` and add a new fragment for the `customRoutines` in the `AllExercises Activity`

### Changes:
 - Migrated to Room Persistance Library
 - Implemented a View Model (**MVVM**)
 - Implemented a Repository for further additions to data saving functionality and cloud retrieving.
 - Rewritten the Input Form Activity and made various code arrangements and refactoring.
 - Added a checking functionality layer before inserting wrong data into table.
 - Migrated to RecyclerView instead of ListView (*deprecated*)
 - Added swipe functionality to show achievments.
 - Added click listeners to RecyclerView to be able to update an exercise (icon of fab also changes).

Screenshots:


|| | |
|:-------------------------:|:-------------------------:|:-------------------------:|
|![enter image description here](https://i.imgur.com/k9yCUS7.png) | ![enter image description here](https://i.imgur.com/zkTERtz.png)| ![enter image description here](https://i.imgur.com/rMIciO4.png)|:-------------------------:|:-------------------------:|:-------------------------:|
|![enter image description here](https://i.imgur.com/CsAaiyF.png)|![enter image description here](https://i.imgur.com/8hm2Bjb.png)|![enter image description here](https://i.imgur.com/housWPi.png)
|![enter image description here](https://i.imgur.com/iX55Tsc.png)|`Placeholder for another screenshot`|`Placeholder for another screenshot`