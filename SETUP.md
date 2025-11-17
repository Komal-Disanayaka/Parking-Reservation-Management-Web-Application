# Parking Reservation Management Web Application Setup Guide

## Prerequisites

1. **Java Development Kit (JDK) 17 or higher**
   - Download from [Oracle](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) or [OpenJDK](https://openjdk.org/)

2. **Apache Maven 3.6 or higher**
   - Download from [Maven Official Site](https://maven.apache.org/download.cgi)

3. **MySQL Database 8.0 or higher**
   - Download from [MySQL Official Site](https://dev.mysql.com/downloads/mysql/)

4. **IDE (Recommended)**
   - IntelliJ IDEA, Eclipse, or VS Code with Java extensions

## Database Setup

1. **Start MySQL Server**
   - Make sure MySQL is running on your system
   
2. **Create Database**
   ```sql
   CREATE DATABASE parking_db;
   ```

3. **Configure Database User (if needed)**
   ```sql
   -- The application is configured to use:
   -- Username: root
   -- Password: 12345
   -- If you want to use different credentials, update application.properties
   ```

4. **Optional: Run schema.sql**
   ```bash
   mysql -u root -p12345 parking_db < database/schema.sql
   ```

## Application Setup

1. **Clone/Download the project**
   ```bash
   git clone <repository-url>
   cd Parking-Reservation-Management-Web-Application
   ```

2. **Update Database Configuration (if needed)**
   Edit `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/parking_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
   spring.datasource.username=root
   spring.datasource.password=12345
   ```

3. **Build the Project**
   ```bash
   mvn clean compile
   ```

4. **Run the Application**
   ```bash
   mvn spring-boot:run
   ```

   Or you can run it from your IDE by running the main class:
   `com.parking.ParkingReservationApplication`

5. **Access the Application**
   - Open your web browser
   - Navigate to: `http://localhost:8080`

## Application Features

### User Management
- **User Registration**: Users can create accounts with username, email, name, and phone
- **User Login**: Secure authentication using Spring Security
- **Profile Management**: 
  - View profile information
  - Update profile (except username)
  - Change password with validation
  - Delete account with confirmation

### Security Features
- **Password Encryption**: All passwords are encrypted using BCrypt
- **Username Immutability**: Usernames cannot be changed for security
- **Session Management**: Secure session handling
- **CSRF Protection**: Built-in protection against CSRF attacks

## Testing the Application

1. **Register a New User**
   - Go to `http://localhost:8080/register`
   - Fill in the registration form
   - Submit to create account

2. **Login**
   - Go to `http://localhost:8080/login`
   - Use your registered credentials
   - Access the dashboard

3. **Profile Management**
   - View profile: `/profile`
   - Edit profile: `/profile/edit`
   - Change password: `/profile/change-password`
   - Delete account: `/profile/delete`

## Troubleshooting

### Common Issues

1. **Database Connection Error**
   - Ensure MySQL is running
   - Check database credentials in `application.properties`
   - Verify database exists

2. **Port Already in Use**
   - Change server port in `application.properties`:
     ```properties
     server.port=8081
     ```

3. **Build Errors**
   - Ensure Java 17+ is installed
   - Check Maven is properly configured
   - Run `mvn clean install`

4. **Login Issues**
   - Check if user exists in database
   - Verify password is correct
   - Check browser console for errors

### Logs
- Application logs are available in the console
- Enable debug logging by setting:
  ```properties
  logging.level.com.parking=DEBUG
  ```

## Project Structure

```
src/
├── main/
│   ├── java/com/parking/
│   │   ├── ParkingReservationApplication.java  # Main application class
│   │   ├── config/
│   │   │   └── SecurityConfig.java              # Security configuration
│   │   ├── controller/
│   │   │   ├── AuthController.java              # Authentication controller
│   │   │   └── ProfileController.java           # Profile management controller
│   │   ├── model/
│   │   │   └── User.java                        # User entity
│   │   ├── repository/
│   │   │   └── UserRepository.java              # User data access
│   │   └── service/
│   │       ├── UserService.java                 # User business logic
│   │       └── CustomUserDetailsService.java   # Spring Security user details
│   └── resources/
│       ├── application.properties               # Application configuration
│       └── templates/                           # Thymeleaf templates
│           ├── index.html                       # Home page
│           ├── login.html                       # Login page
│           ├── register.html                    # Registration page
│           ├── dashboard.html                   # User dashboard
│           └── profile/                         # Profile pages
│               ├── view.html                    # View profile
│               ├── edit.html                    # Edit profile
│               ├── change-password.html         # Change password
│               └── delete-confirm.html          # Delete confirmation
├── database/
│   └── schema.sql                               # Database schema
├── pom.xml                                      # Maven configuration
└── README.md                                    # Project documentation
```

## Next Steps

This application currently provides user management functionality. Future enhancements could include:

1. **Parking Reservation System**
   - View available parking spots
   - Make reservations
   - Cancel reservations
   - Reservation history

2. **Admin Panel**
   - Manage parking spots
   - View all users and reservations
   - System analytics

3. **Email Notifications**
   - Registration confirmation
   - Reservation reminders
   - Password reset

4. **Mobile Responsiveness**
   - Enhanced mobile UI
   - Progressive Web App features

## Support

If you encounter any issues:
1. Check the troubleshooting section above
2. Review application logs
3. Verify database connectivity
4. Ensure all prerequisites are met

For development questions, refer to:
- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
- [Spring Security Documentation](https://docs.spring.io/spring-security/reference/)
- [Thymeleaf Documentation](https://www.thymeleaf.org/documentation.html)