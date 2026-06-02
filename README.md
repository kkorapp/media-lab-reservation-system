# MediaLab Equipment Reservation System

Java console application for managing equipment reservations in a university MediaLab.

## Classes

### Student
Represents a student borrowing equipment.

### Equipment (abstract)
Base class for all equipment types.

### LaptopSet
Equipment type representing a laptop set.

### CameraKit
Equipment type representing a camera kit.

### Reservation
Represents a reservation connecting a student and equipment.

### ReservationService
Contains the main reservation business logic.

### LoyaltyDiscountPolicy
Applies loyalty discounts to reservations.

### Main
Starts the application and provides the console menu.

## Interfaces

### Displayable
Implemented by objects that can display readable information.

Implemented by:
- Equipment
- Reservation

### DiscountPolicy
Defines how discounts are calculated.

Implemented by:
- LoyaltyDiscountPolicy

## Polymorphism Example

The application stores different equipment types in:

List<Equipment>

Both LaptopSet and CameraKit are treated as Equipment objects while each class calculates its daily price differently through overridden methods.

## Features

- Display students
- Display equipment
- Create reservations
- Return equipment
- Loyalty points system
- Revenue reports
- Active reservation tracking

## Technologies

- Java 17
- OOP
- Inheritance
- Polymorphism
- Interfaces
- Collections
