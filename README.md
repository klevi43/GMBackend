# GMBackend

This is the Spring Boot backend for **Growth Mindset**, a full-stack fitness tracking app. It provides RESTful endpoints for managing users, workouts, exercises, and sets. It also supports role-based authentication and secure session management via JWT.

## Features

- User authentication & role-based authorization (User/Admin)
- Full CRUD support for user profile management
- Full CRUD support for workout management
- JWT-based stateless auth
- MySQL database integration
- Efficient entity fetching with Blaze-Persistence
- CORS support for frontend communication

## Tech Stack

- Java 17
- Spring Boot
- Spring Security
- Hibernate + JPA
- Blaze-Persistence
- MySQL
- Maven

## Getting Started

1. **Clone the repo**

```bash
git clone https://github.com/klevi43/GMBackend.git
```
2. **Set up your database**
Create a MySQL database/schema (E.G. growth_mindest_db)
3. **Configure environment**
In src/main/resources/application.properties:
```
    spring.datasource.url=jdbc:mysql://localhost:3306/your_db_name
    spring.datasource.username=your_username
    spring.datasource.password=your_password
    spring.jpa.hibernate.ddl-auto=update
```
4. **Run the app**
Use your IDE(e.g. Eclipse or IntelliJ) to run the application in GmApplication.java.

## API Overview
All routes except for /auth/login and /auth/register require a valid JWT stored in an HTTP-only cookie header.
<pre>
1. Auth
   POST   /auth/login  
   POST   /auth/logout
   GET    /auth/me                  // Get authenticated user (used to check if user is logged in)
    
2. Workouts
   GET    /workouts                 // Get recent workouts  
   GET    /workouts/history         // Get full workout history  
   POST   /workouts/create  
   PUT    /workouts/update?workoutId={id}  
   DELETE /workouts/delete?workoutId={id}  
    
3. Exercises
   POST   /workouts/exercises/create?workoutId={workoutId}  
   PUT    /workouts/exercises/update?workoutId={workoutId}&exerciseId={exerciseId}  
   DELETE /workouts/exercises/delete?workoutId={workoutId}&exerciseId={exerciseId}  

4. Sets
   POST   /workouts/exercises/sets/create?workoutId={workoutId}&exerciseId={exerciseId}  
   PUT    /workouts/exercises/sets/update?workoutId={workoutId}&exerciseId={exerciseId}&setId={setId}  
   DELETE /workouts/exercises/sets/delete?workoutId={workoutId}&exerciseId={exerciseId}&setId={setId} 
    
5. Users
   GET    /users          
   POST   /register 
   UPDATE /users/update  
   DELETE /users/delete  
    
6. Admin
   GET    /admin/users                              // Get all users  
   PUT    /admin/users/promote?userId={userId}      // Promote user to admin  
   Put    /admin/users/demote?userId={userId}       // Demote admin to user  
   DELETE /admin/users/delete?userID={userId}       // delete a user's account  
</pre>

## Backend
This backend connects to a React frontend. You can find the frontend repo here:
[GMFrontend](https://github.com/klevi43/GMFrontend)
    
