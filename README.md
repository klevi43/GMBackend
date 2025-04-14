# Welcome!
This is the backend for Growth Mindset, a REST API that allows a user to log and track their workouts.

## Table of Contents
### [1. Quick Start](#Quick-Start)
### [2. Install Guide](#Install-Guide)
### [3. How to Use Guide](#How-to-Use-Guide)

***Assuming you have Java 17, MySQL, an IDE, and an api tester installed, you can clone the repository and skip the Install Guide Section.***

### <a name="Quick-Start">1. Quick Start<a>

Command to clone this repository:

``` 
git clone https://github.com/klevi43/GMBackend
```


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
URL: http://localhost:8080/register</br>
REQUEST TYPE: POST</br>
FIELDS:</br>
{</br>
  "email": "your-email",</br>
  "password": "your-password"</br>
}</br>
```


### 2. Login 
```
URL: http://localhost:8080/login</br>
REQUEST TYPE: POST</br>
FIELDS:</br>
{</br>
  "email": "your-email", </br>
  "password": "your-password" </br>
}</br>
```

The server will send a 200 status code response with your JWT (valid for 3 hours).
***You will need this JWT to do any of the subsequent operations***

### 3. Adding the JWT
In Talend API Tester, go to the "Headers" section. Click "Add Authorizaion."
Enter "Authorization" for the key and then enter "Bearer [your_jwt_token]"

### 4. Create a Workout
```
URL: http://localhost:8080/workouts/create</br>
REQUEST TYPE: POST</br>
FIELDS:</br>
{</br>
  "name": "your-workout-name", </br>
  "date": "YYYY-MM-DD"</br>
}</br>
```

### 5. Get All Most Recent Workouts
```
URL: http://localhost:8080/workouts</br>
REQUEST TYPE: GET</br>
FIELDS:</br>
{</br>
  "name": "your-workout-name", </br>
  "date": "YYYY-MM-DD"</br>
}</br>
```

### 5. Get Workout History
```
URL: http://localhost:8080/workouts</br>
REQUEST TYPE: GET</br>
FIELDS:</br>
{</br>
  "name": "your-workout-name", </br>
  "date": "YYYY-MM-DD"</br>
}</br>
```
