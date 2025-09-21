# BookInn (Backend) 🏨

[![Java](https://img.shields.io/badge/Java-18-blue)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0-green)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue)](https://www.postgresql.org/)
[![Build](https://img.shields.io/badge/Build-Maven-red)](https://maven.apache.org/)

BookInn is a **modern hotel booking backend API** built with **Spring Boot** and **PostgreSQL**.  
It provides all backend services for a hotel booking platform: user authentication, hotel/room management, bookings, and payment integration.

---

## 🚀 Features

- **User Authentication**: JWT-based login and secure access  

- **Hotel Management**: Admin can add, update, and manage hotels  
- **Room Availability**: Check room availability by dates & number of guests  
- **Booking Management**: Create, view, and manage bookings  
- **Payment Integration**: Stripe API for secure payments  
- **API Versioning**: `/api/v1` for future-proof versioning  

---

## 🛠️ Tech Stack

![Java](https://img.shields.io/badge/Java-18-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0-green)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue)
![Hibernate](https://img.shields.io/badge/Hibernate-ORM-orange)
![JWT](https://img.shields.io/badge/JWT-Security-purple)
![Lombok](https://img.shields.io/badge/Lombok-yes-red)

---
## DFD Diagram
<img width="1097" height="767" alt="DataFlowDiagram" src="https://github.com/user-attachments/assets/eba276eb-b302-4a62-aed4-50fd28dbabca" />
<img width="4936" height="3566" alt="E-RDiagram" src="https://github.com/user-attachments/assets/52f86eda-e2bf-41b1-a41d-a2c3d32475be" />


## 📦 Installation & Setup

1. **Clone the repository**
```bash
git clone https://github.com/JayeshChaudhari0408/BookInn.git
cd BookInn
```

2.Configure Database & Stripe

Update src/main/resources/application.properties with your PostgreSQL credentials and Stripe keys:
spring.datasource.url=jdbc:postgresql://localhost:5432/bookinn

spring.datasource.username=your_db_username

spring.datasource.password=your_db_password

stripe.api.key=your_stripe_api_key

3. **Build the project**

mvn clean install

4. **Run the application**

mvn spring-boot:run

5. **API Base URL**

http://localhost:8080/api/v1

### Example Endpoints
- `POST /auth/login` - User login
- `GET /hotels` - List all hotels
- `POST /bookings` - Create a booking
- `PUT /admin/hotels/{hotelId}` - Update Hotel
