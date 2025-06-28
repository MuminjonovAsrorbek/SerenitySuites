# SerenitySuites - Hotel Management System

A Spring Boot-based hotel management system built for learning purposes, focusing on modern Java development practices and Spring Boot fundamentals.

## 📋 Project Overview

SerenitySuites implements a complete hotel management workflow:

1. **Search** → Guests search for available rooms by date and preferences
2. **Reservation** → Create and manage room reservations
3. **Check-in** → Process guest arrivals and room assignments
4. **Service Orders** → Handle additional services (room service, laundry, etc.)
5. **Check-out** → Process departures and billing
6. **Room Cleaning** → Manage housekeeping tasks and room status

## 🎯 Key Learning Points

- **Long-based Currency Handling**: All monetary values are stored as `Long` representing the smallest currency unit (cents/tiyin) to avoid floating-point precision issues
- **JWT Security**: Stateless authentication with role-based access control
- **MapStruct Integration**: Automated DTO ↔ Entity mapping for clean separation of concerns
- **Spring Boot Best Practices**: Proper layered architecture (Controller → Service → Repository)
- **RESTful API Design**: Following REST conventions for consistent API endpoints

## 🛠️ Tech Stack

- **Framework**: Spring Boot 3.x
- **Database**: PostgreSQL
- **Security**: Spring Security + JWT
- **Password Hashing**: BCrypt
- **Object Mapping**: MapStruct
- **Build Tool**: Maven
- **Java Version**: 17+

## 🚀 How to Run the Project

### Prerequisites
- Java 17 or higher
- PostgreSQL database
- Maven 3.6+

### Setup Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/MuminjonovAsrorbek/SerenitySuites.git
   cd SerenitySuites
   ```

2. **Configure database**
   ```bash
   # Create PostgreSQL database
   createdb serenity_suites
   ```

3. **Update application properties**
   ```properties
   # src/main/resources/application.properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/serenity_suites
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

4. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```

5. **Access the application**
   - API Base URL: `http://localhost:8080/api`
   - Health Check: `http://localhost:8080/actuator/health`

## 🔗 API Overview

### Authentication
- `POST /api/auth/login` - User authentication
- `POST /api/auth/register` - User registration

### Room Management
- `GET /api/rooms/available` - Search available rooms
- `GET /api/rooms/{id}` - Get room details
- `PUT /api/rooms/{id}/status` - Update room status (HOUSEKEEPING role)

### Reservations
- `POST /api/reservations` - Create new reservation
- `GET /api/reservations/{id}` - Get reservation details
- `PUT /api/reservations/{id}/cancel` - Cancel reservation

### Check-in/Check-out
- `POST /api/checkin` - Process guest check-in (FRONT_DESK role)
- `POST /api/checkout` - Process guest check-out (FRONT_DESK role)

### Service Orders
- `POST /api/services` - Create service order
- `GET /api/services/guest/{guestId}` - Get guest services
- `PUT /api/services/{id}/complete` - Mark service as completed

### Housekeeping
- `GET /api/housekeeping/tasks` - Get cleaning tasks (HOUSEKEEPING role)
- `PUT /api/housekeeping/{taskId}/complete` - Complete cleaning task

### Reports (Manager Only)
- `GET /api/reports/occupancy` - Room occupancy report
- `GET /api/reports/revenue` - Revenue report

## 🗄️ Database Design Summary

### Core Entities

- **User**: System users with roles (GUEST, FRONT_DESK, HOUSEKEEPING, MANAGER)
- **Room**: Individual hotel rooms with status tracking
- **RoomType**: Room categories (Standard, Deluxe, Suite) with pricing
- **Reservation**: Guest bookings with date ranges and status
- **ServiceOrder**: Additional services with pricing (stored as Long in cents)
- **HousekeepingLog**: Room cleaning tasks and completion tracking

### Key Relationships
- User → Reservation (One-to-Many)
- Room → RoomType (Many-to-One)
- Reservation → ServiceOrder (One-to-Many)
- Room → HousekeepingLog (One-to-Many)

## 🔐 User Roles & Permissions

- **GUEST**: Create reservations, view own bookings, order services
- **FRONT_DESK**: Manage check-ins/check-outs, view all reservations
- **HOUSEKEEPING**: View and complete cleaning tasks, update room status
- **MANAGER**: Access all features + reporting and system administration

## 💰 Currency Handling

All monetary values are stored as `Long` representing cents/tiyin:
```java
// Example: $25.99 is stored as 2599L
private Long priceInCents;

// Utility methods for conversion
public BigDecimal getPriceInDollars() {
    return BigDecimal.valueOf(priceInCents).divide(BigDecimal.valueOf(100));
}
```

## 📝 Notes

- This project is created for **educational purposes** and Spring Boot practice
- **Not production-ready** - missing features like payment processing, email notifications, etc.
- Focus is on learning Spring Boot fundamentals, security, and clean architecture patterns

## 🤝 Contributing

This is a learning project. Feel free to fork and experiment with additional features!

---

**Happy Learning! 🎓**
