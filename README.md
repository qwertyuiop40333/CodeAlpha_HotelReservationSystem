# CodeAlpha_HotelReservationSystem(Java)
A console-based Hotel Reservation System developed in Java that allows users to create accounts, search available rooms, book rooms through a PIN-based payment verification system, cancel reservations, and view booking details. The application uses file handling to store room availability and booking records, ensuring data persistence between program executions.

## Features

* User account creation with secure payment PIN setup
* Search and view available hotel rooms
* Room booking with payment verification
* Booking cancellation
* View user booking history
* Persistent storage using text files (`rooms.txt` and `bookings.txt`)
* Room availability updates in real time

## Technologies Used

* Java
* Object-Oriented Programming (OOP)
* File Handling (File, FileWriter, Scanner)
* Collections Framework (ArrayList)

## Project Structure

* `HotelReservationSystem` – Main application logic
* `User` – Stores user account information
* `Room` – Manages room details and availability

## How It Works

1. Create a user account and set a payment PIN.
2. Browse available rooms by category (Standard, Deluxe, Suite).
3. Select a room and complete payment verification.
4. Booking details are stored in a file for future reference.
5. Users can view or cancel their reservations anytime.

## Learning Outcomes

This project demonstrates:

* Java fundamentals
* Object-Oriented Programming concepts
* File handling and data persistence
* User input validation
* Menu-driven console application development
