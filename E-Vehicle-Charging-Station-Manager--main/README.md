# EV Charging Station Manager

A Java-based console application for managing electric vehicle charging stations, charging slots, users, bookings, cancellations, charging costs, and usage reports.

## Features

- Manage EV charging stations
- Manage charging slots
- Register and manage users
- Book available charging slots
- Cancel charging bookings
- Calculate charging costs
- Check real-time charging slot availability
- Automatically release expired charging slots
- Update user profiles
- Update station charging rates and power ratings
- Generate detailed usage reports
- View overall network statistics
- Safe integer input validation
- Case-insensitive station and user ID lookup
- Console-based menu system

## Project Structure

| File | Description |
|------|-------------|
| `EVChargingStationManager.java` | Main application and console menu |
| `ChargingStation.java` | Charging station model and booking operations |
| `ChargingSlot.java` | Charging slot and booking management |
| `ChargingStationManager.java` | Station, user, and network management |
| `User.java` | User information, validation, and profile management |
| `UsageReportGenerator.java` | Detailed usage and revenue reports |

## Technologies Used

- Java
- Object-Oriented Programming
- Collections Framework
- Java Date and Time API
- Console-based application

## Core Concepts

This project demonstrates several Java programming concepts:

- Classes and Objects
- Encapsulation
- Constructors
- Method Overloading
- Collections such as `HashMap` and `ArrayList`
- Exception Handling
- Input Validation
- String Processing
- Date and Time Handling
- Basic application-level reporting

## How to Run

### 1. Clone the repository

```bash
git clone <your-github-repository-url>