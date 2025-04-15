# Welcome!
This is the backend for Growth Mindset, a REST API that allows a user to log and track their workouts.

## Table of Contents
   [1. Quick Start](#Quick-Start) </br>
   [2. Install Guide](#Install-Guide) </br>
   [3. How to Use Guide](#How-to-Use-Guide) </br> 

***Assuming you have Java 17, MySQL, an IDE, and an api tester installed, you can clone the repository and skip the Install Guide Section.***

### <a name="Quick-Start">1. Quick Start<a>
  1. Clone this repository </br>
    ``` 
      git clone https://github.com/klevi43/GMBackend
    ```
  2. Set up a database connection. (Tutorial [here](https://www.youtube.com/watch?v=ImqxBiv5yIY))
  3. Add the following properties application.properties file. Be sure to add your database connection url, database username, and database password.
     ```
       spring.application.name=GM
       spring.jpa.hibernate.ddl-auto=update
       spring.datasource.url=${DB_URL}
       spring.datasource.username=${USERNAME}
       spring.datasource.password=${PASSWORD}
       spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
       spring.jpa.show-sql=true
     ```
  4. You're all set!

### <a name="Install-Guide">2. Install Guide<a>

### Install Java 17 

Install the Java Development Kit (JDK) and Java 17 by clicking [here](https://www.oracle.com/java/technologies/javase/jdk17-0-13-later-archive-downloads.html). The Java Runtime Environment (JRE) is included in Java 17, so there is no need to download and install that separately. 


WINDOWS JAVA 17 [INSTALL VIDEO](https://www.youtube.com/watch?v=cL4GcZ6GJV8)
MAC JAVA 17 [INSTALL VIDEO](https://www.youtube.com/watch?v=SdKIBGnkhDY)
UBUNTU JAVA 17 [INSTALL VIDEO](https://www.youtube.com/watch?v=vVrIDJ--GOA)

You can confirm that Java is correctly installed on your system by typing the command below on the command line.

java --version

### Install MySQL

WINDOWS USERS First install the latest Microsoft Visual C++ Redistributable Version found [here](https://learn.microsoft.com/en-us/cpp/windows/latest-supported-vc-redist?view=msvc-170#latest-microsoft-visual-c-redistributable-version). MySQL can't be installed without it.



Now let's install MySQL. You can find the latest version [here](https://dev.mysql.com/downloads/mysql/)

In order to make requests to the api


## How to use

### 1. Create a New Account
```
URL: http://localhost:8080/register
REQUEST TYPE: POST
FIELDS:
{
  "email": "your-email",
  "password": "your-password"
}
```


### 2. Login 
```
URL: http://localhost:8080/login
REQUEST TYPE: POST
FIELDS:
{
  "email": "your-email",
  "password": "your-password"
}
```

The server will send a 200 status code response with your JWT (valid for 3 hours).
***You will need this JWT to do any of the subsequent operations***

### 3. Adding the JWT
In Talend API Tester, go to the "Headers" section. Click "Add Authorizaion."
Enter "Authorization" for the key and then enter "Bearer [your_jwt_token]"

## Workout Related Functionality
### 4. Create a Workout
*** NOTE: You must do this first before adding an exercise or a set. Exercises and sets cannot exist without a workout. ***
```
URL: http://localhost:8080/workouts/create
REQUEST TYPE: POST
FIELDS:
{
  "name": "your-workout-name",
  "date": "YYYY-MM-DD"
}
```

### 5. Get All Your Most Recent Workouts
*** Shows all the most recently created workouts for a given name. ***
```
URL: http://localhost:8080/workouts
REQUEST TYPE: GET
```

### 6. Get Your Workout History
*** Shows all the workouts you've logged ***
```
URL: http://localhost:8080/workouts/history
REQUEST TYPE: GET
```

### 7. Get a Single Workout 
*** Shows a single workout. You can see all your created exercises and sets here as well ***
```
URL: http://localhost:8080/workouts/workout?workoutId=[workout-id]
REQUEST TYPE: GET
```

### 8. Update a Workout 
*** The name and date fields are optional ***
```
URL: http://localhost:8080/workouts/update
REQUEST TYPE: PUT
FIELDS:
{
  "name": "your-workout-name",
  "date": "YYYY-MM-DD"
}
```
### 9. Delete an Existing Workout\
*** NOTE: THIS DELETES ALL ASSOCIATED EXERCISES AND THEIR SETS AS WELL. ***
```
URL: http://localhost:8080/workouts/delete
REQUEST TYPE: DELETE
```

## Exercise Related Functionality
### 1. Create an Exercise
*** Create an exercise within an existing workout. ***

```
URL: http://localhost:8080/workouts/exercises/create?workoutId=[workout-id]
REQUEST TYPE: POST
FIELDS:
{
  "name": "your-exercise-name"
}
```
### 2. Update an Exercise
*** Update an exercise within an existing workout. ***
```
URL: http://localhost:8080/workouts/exercises/update?workoutId=[workout-id]&exerciseId=[exercise-id]
REQUEST TYPE: PUT
FIELDS:
{
  "name": "your-exercise-name"
}
```

### 3. Delete an Exercise
*** Delete an existing exercise from an existing workout. NOTE: THIS DELETES ALL SETS ASSOCIATED WITH THIS EXERCISE AS WELL. ***
```
URL: http://localhost:8080/workouts/exercises/update?workoutId=[workout-id]&exerciseId=[exercise-id]
REQUEST TYPE: DELETE
```

## Set Related Functionality
### 1. Create a Set
*** Create a set for an existing exercise within an existing workout. ***
```
URL: http://localhost:8080/workouts/exercises/sets/create?workoutId=[workout-id]&exerciseId=[exercise-id]
REQUEST TYPE: POST
FIELDS:
{
  "weight": [Weight},
  "Reps": [Reps]
}
```

### 2. Update a Set
*** Update an existing set for an existing exercise with an existing workout. NOTE: Fields are optional. ***
```
URL: http://localhost:8080/workouts/exercises/sets/update?workoutId=[workout-id]&exerciseId=[exercise-id]*setId=[set-id]
REQUEST TYPE: PUT
FIELDS:
{
  "weight": [Weight},
  "Reps": [Reps]
}
```

### 2. Delete a Set
*** Delete an existing set for an existing exercise with an existing workout.***
```
URL: http://localhost:8080/workouts/exercises/sets/delete?workoutId=[workout-id]&exerciseId=[exercise-id]*setId=[set-id]
REQUEST TYPE: DELETE
```

