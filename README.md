# GMBackend

## 📌 [GMBackend 한국어 README 바로가기](#GMBackend-한국어-readme)

This is the Spring Boot backend for **Growth Mindset**, a full-stack fitness tracking app. It provides RESTful endpoints for managing users, workouts, exercises, and sets. It also supports role-based authentication and secure session management via HTTP-only cookie JWTs and CSRF tokens.
You can visit the site here at [growthmindsetproject.com](https://growthmindsetproject.com/).
## Features

- User authentication, role-based authorization, & protected routes (User/Admin)
- Full CRUD support for user profile management
- Full CRUD support for workout management
- JWT-based stateless auth (1 hour token lifespan)
- **CSRF protection enabled for all authenticated routes**
- MySQL database integration
- Efficient entity fetching for deeply nested entities (exercises, sets) with Blaze-Persistence, minimizing N+1 query problems
- CORS support for frontend communication

## Tech Stack

- Java 17
- Spring Boot
- Spring Security
- Hibernate + JPA
- Blaze-Persistence
- MySQL
- Maven

## Security

- **Authentication:** Uses stateless JWTs stored in HTTP-only cookies for security against XSS.
- **CSRF Protection:** CSRF attacks are mitigated by issuing a csrf token to the frontend which is then exchanged in subsequent cross-origin requests.
- **CORS Support**: Configured to allow secure cross-origin communication between the frontend and backend.


## Getting Started

1. **Clone the repo**

```
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

Also, please be sure to set the frontend url environment variables: 
```
FRONTEND_URL: your_frontend_url

```
Lastly, when running the application locally on your machine, you'll need to comment out these lines (Uncomment when making a commit to this repository):
```
 CookieCsrfTokenRepository csrfTokenRepository = CookieCsrfTokenRepository.withHttpOnlyFalse();
        csrfTokenRepository.setCookieCustomizer(csrfTokenRepo -> csrfTokenRepo
                .sameSite("None")
                .secure(true)
                .path("/")
                .domain(System.getenv("DOMAIN_NAME")));
        csrfTokenRepository.setCookieName("XSRF-TOKEN");
        csrfTokenRepository.setCookiePath("/");
```
Be sure to disable csrf when running locally with the code below (Remove this when making a commit to this repository):
```
    http.csrf(CsrfConfigurer::disable);
```
**Database tables will be created automatically upon application startup if they don't already exist.**
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
   GET    /workouts                 // Get most recently completed workout entries  
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
   UPDATE /users/update/email
   UPDATE /users/update/password
   DELETE /users/delete  
    
6. Admin
   GET    /admin/users                              // Get all users  
   PUT    /admin/users/promote?userId={userId}      // Promote user to admin  
   PUT    /admin/users/demote?userId={userId}       // Demote admin to user  
   DELETE /admin/users/delete?userID={userId}       // delete a user's account  
</pre>

## Frontend
This backend connects to a React frontend. You can find the frontend repo here:
[GMFrontend](https://github.com/klevi43/GMFrontend)

## Known Issues

- Currently there is no graceful handling of the token expiring after the 1 hour time limit has been reached. To resolve this, simply refresh your page on the frontend and go to the login screen to login again. Improved handling is planned for a future update.
- Logging out removes the token from cookies subsequent requests; however, the token is not invalidated by the backend. This is not a security concern because the jwt is stored in an HTTP-only cookie and has a short lifespan (1 hour). A refresh token functionality is planned for a future update.
- Thank you for your patience and understanding.

# GMBackend 한국어 README

## 소개

GMBackend는 **Growth Mindset**의 백엔드 애플리케이션으로, Spring Boot를 기반으로 개발되었습니다.  
사용자, 운동, 운동 세트 등의 데이터를 관리할 수 있는 RESTful API를 제공하며, 역할 기반 인증과  
HTTP-only 쿠키 기반 JWT, CSRF 토큰을 통한 보안 세션 관리를 지원합니다.  
웹사이트는 [growthmindsetproject.com](https://growthmindsetproject.com/)에서 확인할 수 있습니다.

---

## 주요 기능

- 사용자 인증 및 역할 기반 권한 제어 (일반 사용자/관리자)
- 사용자 프로필에 대한 CRUD 기능
- 운동 기록에 대한 CRUD 기능
- JWT 기반 무상태 인증 (1시간 유효 토큰)
- **모든 인증된 경로에 대해 CSRF 보호 적용**
- MySQL 데이터베이스 연동
- Blaze-Persistence를 통한 중첩 엔티티 효율적 조회 (N+1 문제 최소화)
- 프론트엔드와의 CORS 통신 지원

---

## 기술 스택

- Java 17
- Spring Boot
- Spring Security
- Hibernate + JPA
- Blaze-Persistence
- MySQL
- Maven

---

## 보안

- **인증:** HTTP-only 쿠키에 저장된 JWT를 이용한 무상태 인증으로 XSS 공격에 대한 보안 강화
- **CSRF 보호:** 프론트엔드에 CSRF 토큰을 발급하고, 이후 요청 시 해당 토큰을 함께 전송하여 CSRF 공격 방지
- **CORS 지원:** 프론트엔드와의 안전한 교차 출처 요청을 위한 설정 완료

---

## 시작하기

1. **레포지토리 클론**

```
git clone https://github.com/klevi43/GMBackend.git
```

2. **데이터베이스 설정**  
   MySQL에서 예: `growth_mindset_db` 이름의 데이터베이스/스키마를 생성하세요.

3. **환경 설정**  
   `src/main/resources/application.properties`에 다음을 추가하세요:

```
spring.datasource.url=jdbc:mysql://localhost:3306/your_db_name
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

추가로 프론트엔드 URL 환경 변수도 설정해야 합니다:

```
FRONTEND_URL=your_frontend_url
```

로컬 환경에서 실행할 경우 아래 설정은 주석 처리하세요 (커밋 전에는 다시 주석 해제):

```
CookieCsrfTokenRepository csrfTokenRepository = CookieCsrfTokenRepository.withHttpOnlyFalse();
csrfTokenRepository.setCookieCustomizer(csrfTokenRepo -> csrfTokenRepo
        .sameSite("None")
        .secure(true)
        .path("/")
        .domain(System.getenv("DOMAIN_NAME")));
csrfTokenRepository.setCookieName("XSRF-TOKEN");
csrfTokenRepository.setCookiePath("/");
```

그리고 로컬에서 실행 시 CSRF 비활성화 코드를 추가하세요 (커밋 시 제거):

```
http.csrf(CsrfConfigurer::disable);
```

💡 애플리케이션 실행 시 데이터베이스 테이블이 자동 생성됩니다.

4. **앱 실행**  
   사용 중인 IDE (예: IntelliJ, Eclipse)에서 `GmApplication.java`를 실행하세요.

---

## API 개요

> `/auth/login`과 `/auth/register`를 제외한 모든 경로는 HTTP-only 쿠키에 저장된 JWT가 필요합니다.

<pre>
1. 인증
   POST   /auth/login  
   POST   /auth/logout
   GET    /auth/me

2. 운동
   GET    /workouts                 
   GET    /workouts/history         
   POST   /workouts/create  
   PUT    /workouts/update?workoutId={id}  
   DELETE /workouts/delete?workoutId={id}  

3. 운동 세부
   POST   /workouts/exercises/create?workoutId={workoutId}  
   PUT    /workouts/exercises/update?workoutId={workoutId}&exerciseId={exerciseId}  
   DELETE /workouts/exercises/delete?workoutId={workoutId}&exerciseId={exerciseId}  

4. 세트
   POST   /workouts/exercises/sets/create?workoutId={workoutId}&exerciseId={exerciseId}  
   PUT    /workouts/exercises/sets/update?workoutId={workoutId}&exerciseId={exerciseId}&setId={setId}  
   DELETE /workouts/exercises/sets/delete?workoutId={workoutId}&exerciseId={exerciseId}&setId={setId} 

5. 사용자
   GET    /users          
   POST   /register 
   UPDATE /users/update/email
   UPDATE /users/update/password
   DELETE /users/delete  

6. 관리자
   GET    /admin/users                              
   PUT    /admin/users/promote?userId={userId}      
   PUT    /admin/users/demote?userId={userId}       
   DELETE /admin/users/delete?userID={userId}       
</pre>

---

## 프론트엔드

이 백엔드는 React 기반 프론트엔드와 연동됩니다.  
프론트엔드 레포지토리는 [GMFrontend](https://github.com/klevi43/GMFrontend)에서 확인할 수 있습니다.

---

## 알려진 이슈

- JWT 토큰 만료(1시간) 시 사용자에게 명확한 안내가 없습니다. 페이지를 새로고침하고 로그인 페이지로 이동 후 다시 로그인하면 됩니다. 추후 개선 예정입니다.
- 로그아웃 시 쿠키에서 토큰은 제거되지만 백엔드에서는 무효화되지 않습니다.  
  그러나 해당 JWT는 HTTP-only 쿠키에 저장되며 수명이 짧기 때문에 보안 문제는 아닙니다.  
  향후 리프레시 토큰 기능이 도입될 예정입니다.

감사합니다!