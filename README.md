# Welcome!
This is the backend for Growth Mindset, a REST API that allows a user to log and track their workouts. This app is made for anyone who wants to keep a record of their completed workouts so that they can more effectively track their progess in the gym. It was made using Java 17, Spring Boot, Hibernate, JPA, Blaze Persistence, Maven.

## Table of Contents
   1. [Prerequisites](#Install-Guide) </br>
   2. [Getting the Project Up and Running](#Quick-Start) </br>
   3. [How to Use Guide](#How-to-Use-Guide) </br>
      - [Registration and Login](#How-to-Use-Guide)
      - [Workout Related Functionality](#Workout)
      - [Exercise Related Functionality](#Exercise)
      - [Set Related Functionality](#Set)

<a name="Install-Guide"><a>
## 1. Prerequisites
If you already have the prerequisites installed, then please move on to next [section](#Quick-Start)
### 1. Install Java 17 </br>
   1. Download and install the Java Development Kit (JDK) and Java 17 by clicking [here](https://www.oracle.com/java/technologies/javase/jdk17-0-13-later-archive-downloads.html). The Java Runtime Environment (JRE) is included in Java 17, so there is no need to download and install that separately. 
[Windows tutorial video](https://www.youtube.com/watch?v=cL4GcZ6GJV8) | [Mac tutorial video](https://www.youtube.com/watch?v=SdKIBGnkhDY) | [Ubuntu 24 tutorial video](https://www.youtube.com/watch?v=3dnLOtXU77Y&t=19s)

### 2. Install MySQL and MySQL Workbench</br>
   1. Download and install MySQL by clicking [here](https://dev.mysql.com/downloads/mysql/) | [Windows video tutorial](https://www.youtube.com/watch?v=u96rVINbAUI) | [Mac video tutorial](https://www.youtube.com/watch?v=ODA3rWfmzg8) | [Ubuntu 24 video tutorial](https://www.youtube.com/watch?v=455KKhZyvow)</br>
***WINDOWS USERS First install the latest Microsoft Visual C++ Redistributable Version found [here](https://learn.microsoft.com/en-us/cpp/windows/latest-supported-vc-redist?view=msvc-170#latest-microsoft-visual-c-redistributable-version). MySQL can't be installed without it.*** 
### 3. Install an IDE
   1. Download and install Eclipse by clicking [here](https://www.eclipse.org/downloads/packages/installer) | [Windows video tutorial](https://www.youtube.com/watch?v=wMTdB7ElrIQ) | [Mac video tutorial](https://www.youtube.com/watch?v=V7ggAMY2ybU) | [Ubuntu 24 video tutorial](https://www.youtube.com/watch?v=GVgwCRnz_EI)
### 4. Install an API tester
   1. Download and install Google Chrome by clicking [here](https://script.google.com/macros/s/AKfycbz2fFu-Yl-4nNYW8GsCcChd9NeZF0M1_Tbibp892wgj9WWm1JN-8r2OFIyHYi2AJhqs/exec?af_r=https://www.google.com/chrome%3Fgad_source%3D1&gclid=Cj0KCQjwh_i_BhCzARIsANimeoHD2FZPWBKYfJawBU1Iy7DYT9XpP0N6sm9Sunp1J81lj4aj1aBQ6FkaAreDEALw_wcB)
   2. Download the Talend API Tester Chrome extension | [Video tutorial](https://www.youtube.com/watch?v=Sclw3AdvqJs)

<a name="Quick-Start"><a>
### 2. Getting the Project Up and Running
***NOTE: Use this section if you already have Java 17, MySQL, an IDE, and an API tester installed. Otherwise, go back to the [Prerequisites](#Install-Guide) section.***
  1. Clone this repository by entering the following command in the terminal in your IDE. </br>
    ``` 
      git clone https://github.com/klevi43/GMBackend
    ```
  2. Set up a database/schema in MySQL Workbench. | [Video tutorial here](https://www.youtube.com/watch?v=ImqxBiv5yIY)
  3. Back in your IDE, add the following properties application.properties file. Be sure to add your database connection url, database username, and database password.
     ```
       spring.application.name=GM
       spring.jpa.hibernate.ddl-auto=update
       spring.datasource.url=${DB_URL}
       spring.datasource.username=${USERNAME}
       spring.datasource.password=${PASSWORD}
       spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
       spring.jpa.show-sql=true
     ```
  4. You're all set! Click the run button in your IDE (the green play button in Eclipse) the application, and let's start logging your workouts!


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

The server will send a 200 status code response with your JSON Web Token (JWT) (valid for 3 hours).</br>
In Talend, add the JWT by clicking the "Add headers" button in the Headers section and add the following: </br>
Authorization | Bearer [your-JWT-here]


<a name="Workout"></a>
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
### 4. Get a Workout By Id
Retrieves the workout based on the Id provided. You can see all your created exercises and sets here as well.
```
URL: http://localhost:8080/workouts/workout?workoutId=[workout-id]
REQUEST TYPE: GET
```

### 5. Update a Workout 
The name and date fields are optional.
```
URL: http://localhost:8080/workouts/update?workoutId=[workout-id]
REQUEST TYPE: PUT
BODY:
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

<a name="Exercise"></a>
## Exercise Related Functionality
Looking for a GET method to see your exercises? Go to the [Get a Workout By Id](#Get-Workout-By-Id).
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

<a name="Set"></a>
## Set Related Functionality
Looking for a GET method to see your sets?  Go to the [Get a Workout By Id](#Get-Workout-By-Id).
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
BODY:
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

