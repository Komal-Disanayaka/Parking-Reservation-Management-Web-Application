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

#### Using IDE:
- Open the project in your preferred IDE (IntelliJ IDEA, Eclipse, VS Code)
- Run the `ParkingReservationApplication.java` class

### 3. Access the Application
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

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
