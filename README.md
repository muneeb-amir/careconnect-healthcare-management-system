# 🏥 CARECONNECT
### Connecting Patients and Healthcare Professionals

CARECONNECT is a Healthcare Management System developed using **Java**, **JavaFX**, **FXML**, **JDBC**, and **MySQL**. The system provides a centralized platform for patients, doctors, and administrators to efficiently manage healthcare services, appointment scheduling, patient interactions, and clinic operations.

The application follows a role-based architecture where patients can book appointments, doctors can manage requests and consultations, and administrators can oversee the entire healthcare ecosystem.

---

## ✨ Features

### 🔐 Authentication & Role-Based Access

- Secure Login System
- Patient Registration
- Doctor Registration
- Administrator Access
- Role-Specific Dashboards

---

## 👨‍⚕️ Doctor Module

Doctors can:

- Create accounts and register
- Manage professional profiles
- Update specialization and location
- View all appointments
- Review pending appointment requests
- Accept or reject appointment requests
- Track earnings
- View ratings and reviews from patients

---

## 🧑 Patient Module

Patients can:

- Create accounts and register
- Browse doctor profiles
- Search doctors by specialization
- Book appointments
- Select preferred dates
- Select appointment time slots
- Track appointment status
- View appointment history
- Submit ratings and reviews

---

## 🛡️ Administrator Module

Administrators can:

- View all registered doctors
- View all registered patients
- View all appointments
- Manage doctor accounts
- Manage patient accounts
- Edit account information
- Delete accounts when required
- Monitor system activities

---

## 📅 Appointment Management

CARECONNECT implements a complete appointment workflow:

```text
Patient Books Appointment
           ↓
Status = Pending
           ↓
Doctor Reviews Request
           ↓
Accept / Reject
           ↓
Patient Receives Updated Status
```

### Appointment Features

- Date Selection
- Time Slot Selection
- Status Tracking
- Appointment History
- Doctor Approval Workflow

Available statuses:

```text
Pending
Accepted
Rejected
Completed
```

---

## ⭐ Ratings & Reviews System

Patients can provide:

- Numerical Ratings
- Written Reviews

Doctors can:

- View Patient Feedback
- Monitor Service Quality
- Improve Healthcare Experience

---

## 💰 Earnings Tracking

The system allows doctors to:

- Monitor Earnings
- View Revenue Information
- Track Professional Performance

---

# 🛠 Technology Stack

| Technology | Purpose |
|------------|----------|
| Java | Core Application Logic |
| JavaFX | Desktop User Interface |
| FXML | UI Layout Design |
| CSS | Styling |
| JDBC | Database Connectivity |
| MySQL | Data Persistence |
| Scene Builder | JavaFX UI Development |

---

# 🏗 System Architecture

## Patient Workflow

```text
Register/Login
       ↓
Browse Doctors
       ↓
Book Appointment
       ↓
Pending Approval
       ↓
Doctor Accepts Appointment
       ↓
Consultation
       ↓
Rate & Review Doctor
```

---

## Doctor Workflow

```text
Register/Login
       ↓
Manage Profile
       ↓
Review Appointment Requests
       ↓
Accept / Reject Requests
       ↓
Consult Patients
       ↓
Track Earnings
       ↓
View Reviews
```

---

## Administrator Workflow

```text
Login
      ↓
Monitor System
      ↓
Manage Doctors
      ↓
Manage Patients
      ↓
View Appointments
```

---

# 📂 Project Structure

```text
CARECONNECT
│
├── Main.java
├── HealthClinicSystem.java
├── HealthClinicSystemTest.java
│
├── Authentication
│   ├── LoginController.java
│   ├── DoctorRegisterController.java
│   └── PatientRegisterController.java
│
├── Dashboards
│   ├── AdminDashboardController.java
│   ├── DoctorDashboardController.java
│   └── PatientDashboardController.java
│
├── Appointment Management
│   ├── BookAppointmentController.java
│   ├── ViewAppointmentsController.java
│   ├── PendingAppointmentsController.java
│   └── AllAppointmentsController.java
│
├── Doctor Management
│   ├── ViewDoctorProfilesController.java
│   ├── UpdateDoctorProfileController.java
│   ├── DoctorEarningsController.java
│   ├── DoctorRatingsViewController.java
│   └── DoctorReviewController.java
│
├── FXML Views
│   ├── Login.fxml
│   ├── AdminDashboard.fxml
│   ├── DoctorDashboard.fxml
│   ├── PatientDashboard.fxml
│   ├── BookAppointment.fxml
│   ├── ViewAppointments.fxml
│   └── ...
│
└── Database
    └── MySQL Database
```

---

# 🗄 Database Entities

The application manages the following core entities:

### Patient

- Name
- Email
- Password
- Appointments

### Doctor

- Name
- Email
- Password
- Specialization
- Location
- Earnings
- Ratings

### Appointment

- Patient
- Doctor
- Date
- Time
- Status

### Rating

- Patient Name
- Score
- Review

### Administrator

- Name
- Email
- Password

---

# 🚀 Getting Started

## Prerequisites

- Java JDK 17+
- JavaFX SDK
- MySQL Server
- MySQL Workbench
- IntelliJ IDEA / Eclipse / NetBeans

---

## 1. Clone Repository

```bash
git clone https://github.com/yourusername/careconnect-healthcare-management-system.git
```

---

## 2. Create Database

```sql
CREATE DATABASE HealthClinicSystem;
```

Create the required tables and configure your MySQL environment.

---

## 3. Configure Database Connection

Update database credentials inside:

```java
HealthClinicSystem.java
```

and

```java
databaseconnection.java
```

Example:

```java
private static final String URL =
"jdbc:mysql://localhost:3306/HealthClinicSystem";

private static final String USER = "root";
private static final String PASSWORD = "your_password";
```

---

## 4. Configure JavaFX

Add JavaFX SDK to your IDE.

VM Options:

```bash
--module-path "PATH_TO_FX" --add-modules javafx.controls,javafx.fxml
```

---

## 5. Run Application

Run:

```text
Main.java
```

The application launches through the Login interface and loads all persisted data into memory before starting.

---

# 📸 Major Screens

- Login Screen
- Patient Registration
- Doctor Registration
- Patient Dashboard
- Doctor Dashboard
- Admin Dashboard
- Book Appointment
- View Appointments
- Pending Appointments
- Doctor Profile Management
- Doctor Earnings
- Ratings & Reviews

---

# 🎯 Future Enhancements

- Online Consultation Support
- Video Calling Integration
- E-Prescriptions
- Medical Record Management
- Email Notifications
- SMS Appointment Reminders
- Online Payments
- Multi-Clinic Support
- AI-Based Doctor Recommendations
- Mobile Application Version

---

# 💡 Project Objective

CARECONNECT was developed to simplify healthcare service management by providing a unified platform where patients can easily connect with healthcare professionals, schedule appointments, provide feedback, and manage healthcare interactions efficiently.

The system promotes better communication, improved appointment management, and streamlined healthcare administration.

---

# 👨‍💻 Author

**CARECONNECT – Healthcare Management System**

A JavaFX and MySQL based desktop application designed to modernize healthcare appointment management and patient-doctor interactions.

---

# 📜 License

This project is intended for educational and academic purposes. Feel free to fork, modify, and extend it for learning and personal projects.

---

# 🏥 CARECONNECT

### *Connecting Patients and Healthcare Professionals*

Delivering a smarter, more organized, and accessible healthcare experience.
