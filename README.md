# Parking Reservation Management System

A comprehensive web-based parking reservation management system built with Spring Boot and Thymeleaf. This system provides efficient management of parking lots and user authentication with role-based access control.

## 🚀 Features

### User Management
- **User Registration & Authentication**: Secure user registration and login system
- **Role-Based Access Control**: Three user roles (USER, ADMIN, LOT_MANAGER)
- **Profile Management**: Users can view and update their profiles
- **Password Management**: Secure password change functionality

### Lot Management
- **Parking Lot Creation**: Create and configure new parking lots
- **Lot Information Management**: Manage lot details including name, location, capacity, and description
- **Lot Status Tracking**: Monitor lot availability (AVAILABLE, MAINTENANCE, CLOSED)
- **Capacity Management**: Track occupied slots vs. total capacity
- **Lot Operations**: Edit, view, and delete parking lots

## 🛠️ Technology Stack

- **Backend**: Spring Boot 3.2.0
- **Frontend**: Thymeleaf, HTML5, CSS3
- **Database**: MySQL 8.0
- **Security**: Spring Security
- **Build Tool**: Maven
- **Java Version**: 17

## 📋 Prerequisites

Before running this application, make sure you have the following installed:

- **Java 17** or higher
- **Maven 3.6+**
- **MySQL 8.0+**
- **Git** (for cloning the repository)

## 🔧 Installation & Setup

### 1. Clone the Repository
```bash
git clone https://github.com/your-username/Parking-Reservation-Management-Web-Application.git
cd Parking-Reservation-Management-Web-Application
```

### 2. Database Setup
1. **Install MySQL** and start the MySQL service
2. **Create Database**: The application will automatically create the database `parking_nest_db`
3. **Update Database Configuration** (if needed):
   - Open `src/main/resources/application.properties`
   - Update the database URL, username, and password:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/parking_nest_db
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

### 3. Build and Run the Application

#### Using Maven:
```bash
# Build the project
mvn clean compile

# Run the application
mvn spring-boot:run
```

#### Using Maven Wrapper (if available):
```bash
# On Windows
./mvnw.cmd spring-boot:run

# On Unix/Linux/Mac
./mvnw spring-boot:run
```

#### Using IDE:
- Open the project in your preferred IDE (IntelliJ IDEA, Eclipse, VS Code)
- Run the `ParkingReservationApplication.java` class

### 4. Access the Application
Once the application is running, open your web browser and navigate to:
```
http://localhost:8080
```

## 👥 User Roles & Permissions

### USER
- Register and login to the system
- View and edit personal profile
- Change password

### LOT_MANAGER
- All USER permissions
- Create new parking lots
- Edit existing parking lots
- View all parking lots
- Delete parking lots
- Manage lot capacity and status

### ADMIN
- All LOT_MANAGER permissions
- Full system administration capabilities

## 🏗️ Project Structure

```
src/
├── main/
│   ├── java/com/parking/
│   │   ├── ParkingReservationApplication.java  # Main application class
│   │   ├── config/
│   │   │   ├── DataInitializer.java            # Initial data setup
│   │   │   └── SecurityConfig.java             # Security configuration
│   │   ├── controller/
│   │   │   ├── AuthController.java             # Authentication endpoints
│   │   │   ├── LotManagerController.java       # Lot management endpoints
│   │   │   └── ProfileController.java          # Profile management endpoints
│   │   ├── model/
│   │   │   ├── ParkingLot.java                # Parking lot entity
│   │   │   └── User.java                      # User entity
│   │   ├── repository/
│   │   │   ├── ParkingLotRepository.java      # Parking lot data access
│   │   │   └── UserRepository.java            # User data access
│   │   └── service/
│   │       ├── CustomUserDetailsService.java  # Custom user details
│   │       ├── DataInitializationService.java # Data initialization
│   │       ├── ParkingLotService.java         # Parking lot business logic
│   │       └── UserService.java               # User business logic
│   └── resources/
│       ├── application.properties              # Application configuration
│       ├── static/css/style.css               # Styling
│       └── templates/                         # Thymeleaf templates
│           ├── dashboard.html
│           ├── index.html
│           ├── login.html
│           ├── register.html
│           ├── lot-manager/                   # Lot management pages
│           └── profile/                       # Profile management pages
```

## 🗄️ Database Schema

The application uses the following main entities:

### Users Table
- `id` - Primary key
- `username` - Unique username
- `password` - Encrypted password
- `email` - User email address
- `first_name`, `last_name` - User's full name
- `phone_number` - Contact number
- `role` - User role (USER, ADMIN, LOT_MANAGER)
- `created_at`, `updated_at` - Timestamps

### Parking Lots Table
- `id` - Primary key
- `lot_name` - Unique lot name
- `location` - Lot location
- `description` - Lot description
- `capacity` - Total parking capacity
- `occupied_slots` - Currently occupied slots
- `status` - Lot status (AVAILABLE, MAINTENANCE, CLOSED)
- `created_at`, `updated_at` - Timestamps

## 🔐 Security Features

- **Password Encryption**: Uses BCrypt for secure password hashing
- **Session Management**: Secure session handling with Spring Security
- **CSRF Protection**: Cross-Site Request Forgery protection enabled
- **Role-Based Authorization**: Method-level security with role checking
- **Input Validation**: Server-side validation for all user inputs

## 🚦 API Endpoints

### Authentication
- `GET /` - Home page
- `GET /login` - Login page
- `POST /login` - Process login
- `GET /register` - Registration page
- `POST /register` - Process registration
- `POST /logout` - Logout

### Profile Management
- `GET /profile` - View profile
- `GET /profile/edit` - Edit profile form
- `POST /profile/edit` - Update profile
- `GET /profile/change-password` - Change password form
- `POST /profile/change-password` - Update password

### Lot Management (LOT_MANAGER/ADMIN only)
- `GET /lot-manager/dashboard` - Lot manager dashboard
- `GET /lot-manager/lots` - View all lots
- `GET /lot-manager/lots/create` - Create lot form
- `POST /lot-manager/lots/create` - Save new lot
- `GET /lot-manager/lots/{id}` - View lot details
- `GET /lot-manager/lots/{id}/edit` - Edit lot form
- `POST /lot-manager/lots/{id}/edit` - Update lot
- `POST /lot-manager/lots/{id}/delete` - Delete lot

## 🧪 Testing

To run tests (when available):
```bash
mvn test
```

## 📦 Building for Production

To create a production-ready JAR file:
```bash
mvn clean package
```

The JAR file will be created in the `target/` directory and can be run with:
```bash
java -jar target/parking-reservation-0.0.1-SNAPSHOT.jar
```

## 🔧 Configuration

### Database Configuration
Update `application.properties` for different environments:

```properties
# Development
spring.jpa.hibernate.ddl-auto=create-drop

# Production
spring.jpa.hibernate.ddl-auto=validate
```

### Server Configuration
```properties
# Change port
server.port=8081

# Context path
server.servlet.context-path=/parking
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🐛 Troubleshooting

### Common Issues

1. **Database Connection Error**
   - Ensure MySQL is running
   - Check database credentials in `application.properties`
   - Verify database exists or enable auto-creation

2. **Port Already in Use**
   - Change the port in `application.properties`: `server.port=8081`
   - Or kill the process using port 8080

3. **Build Failures**
   - Ensure Java 17 is installed and configured
   - Run `mvn clean` before building
   - Check for dependency conflicts

## 🚧 Future Enhancements

- Parking spot reservation functionality
- Real-time parking availability
- Payment integration
- Mobile application
- Notification system
- Reporting and analytics
- QR code integration

## ✨ Screenshots

*Add screenshots of your application here*

## 📞 Support

For support and questions, please open an issue in the GitHub repository or contact the development team.

---

**Happy Parking! 🚗**