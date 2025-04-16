# Welcome!
This is the backend for Growth Mindset, a REST API that allows a user to log and track their workouts.

## Table of Contents
   1. [Quick Start](#Quick-Start) </br>
   2. [Install Guide](#Install-Guide) </br>
   3. [How to Use Guide](#How-to-Use-Guide) </br> 



<a name="Quick-Start"><a>
### 1. Quick Start
***NOTE: Use this section if you already have Java 17, MySQL, an API tester, and an IDE installed. Otherwise, go to the [Install Guide](#Install-Guide) section.***
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

<a name="Install-Guide"><a>
## 2. Install Guide
### 1. Install Java 17 

   1. Download and install the Java Development Kit (JDK) and Java 17 by clicking [here](https://www.oracle.com/java/technologies/javase/jdk17-0-13-later-archive-downloads.html). The Java Runtime Environment (JRE) is included in Java 17, so there is no need to download and install that separately. 
      - WINDOWS JAVA 17 [INSTALL VIDEO](https://www.youtube.com/watch?v=cL4GcZ6GJV8)
      - MAC JAVA 17 [INSTALL VIDEO](https://www.youtube.com/watch?v=SdKIBGnkhDY) </br>
You can confirm that Java is correctly installed on your system by typing the command below on the command line.
```
java --version
```
### 2. Install MySQL </br>
Download and install MySQL by clicking [here](https://dev.mysql.com/downloads/mysql/)</br>
***WINDOWS USERS First install the latest Microsoft Visual C++ Redistributable Version found [here](https://learn.microsoft.com/en-us/cpp/windows/latest-supported-vc-redist?view=msvc-170#latest-microsoft-visual-c-redistributable-version). MySQL can't be installed without it.*** 

<a name="How-to-Use-Guide"></a>
## How to use
To do any of the actions below, please enter the following information into the correct fields in your API tester. </br> 
### 1. Create a New Account
```
URL: http://localhost:8080/register
REQUEST TYPE: POST
BODY:
{
  "email": "your-email",
  "password": "your-password"
}
```


### 2. Login 
```
URL: http://localhost:8080/login
REQUEST TYPE: POST
BODY:
{
  "email": "your-email",
  "password": "your-password"
}
```

The server will send a 200 status code response with your JWT (valid for 3 hours).
Add the JWT to the authorization header field in the following format: </br>
Authorization | Bearer [your-JWT-here]

## Workout Related Functionality
### 1. Create a Workout
***NOTE: You must do this first before adding an exercise or a set. Exercises and sets cannot exist without a workout.***
```
URL: http://localhost:8080/workouts/create
REQUEST TYPE: POST
BODY:
{
  "name": "your-workout-name",
  "date": "YYYY-MM-DD"
}
```

### 2. Get All Your Most Recent Workouts
Shows all the most recently logged version of your workouts.
```
URL: http://localhost:8080/workouts
REQUEST TYPE: GET
```

### 3. Get Your Workout History
Shows all the workouts you've logged.
```
URL: http://localhost:8080/workouts/history
OPTIONAL REQUEST PARAMS: pageNo=[page-no]&pageSize=[page-size]
REQUEST TYPE: GET
```
<a name="Get-Workout-By-Id"></a>
### 4. Get a Single Workout 
Shows a single workout. You can see all your created exercises and sets here as well.
```
URL: http://localhost:8080/workouts/workout?workoutId=[workout-id]
REQUEST TYPE: GET
```

### 5. Update a Workout 
The name and date fields are optional.
```
URL: http://localhost:8080/workouts/update?workoutId=[workout-id]
REQUEST TYPE: PUT
FIELDS:
{
  "name": "your-workout-name",
  "date": "YYYY-MM-DD"
}
```
### 6. Delete an Existing Workout
***NOTE: THIS DELETES ALL ASSOCIATED EXERCISES AND THEIR SETS AS WELL.***
```
URL: http://localhost:8080/workouts/delete?workoutId=[workout-id]
REQUEST TYPE: DELETE
```

## Exercise Related Functionality
Looking for a GET method to see your exercises? Go [here](#Get-Workout-By-Id)
### 1. Create an Exercise
***Create an exercise within an existing workout.***

```
URL: http://localhost:8080/workouts/exercises/create?workoutId=[workout-id]
REQUEST TYPE: POST
BODY:
{
  "name": "your-exercise-name"
}
```
### 2. Update an Exercise
Update an exercise within an existing workout.
```
URL: http://localhost:8080/workouts/exercises/update?workoutId=[workout-id]&exerciseId=[exercise-id]
REQUEST TYPE: PUT
BODY:
{
  "name": "your-exercise-name"
}
```

### 3. Delete an Exercise
Delete an existing exercise from an existing workout. ***NOTE: THIS DELETES ALL SETS ASSOCIATED WITH THIS EXERCISE AS WELL.***
```
URL: http://localhost:8080/workouts/exercises/update?workoutId=[workout-id]&exerciseId=[exercise-id]
REQUEST TYPE: DELETE
```

## Set Related Functionality
***Looking for a GET method to see your sets? Go [here](#Get-Workout-By-Id)***
### 1. Create a Set
Create a set for an existing exercise within an existing workout.
```
URL: http://localhost:8080/workouts/exercises/sets/create?workoutId=[workout-id]&exerciseId=[exercise-id]
REQUEST TYPE: POST
BODY:
{
  "weight": [Weight},
  "Reps": [Reps]
}
```

### 2. Update a Set
Update an existing set for an existing exercise with an existing workout. NOTE: Fields are optional.
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
Delete an existing set for an existing exercise with an existing workout.
```
URL: http://localhost:8080/workouts/exercises/sets/delete?workoutId=[workout-id]&exerciseId=[exercise-id]*setId=[set-id]
REQUEST TYPE: DELETE
```

