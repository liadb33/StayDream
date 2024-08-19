# StayDream

**StayDream** is an Android application for hotel booking and management. It uses Firebase for authentication and data storage.

## Features
- **User authentication** using Firebase Authentication
- **Hotel listing and search** functionality
- **Detailed hotel view** with images, description, and booking options
- **Favorite hotels functionality**
- **User profile management**

## Project Structure

The project is structured into several key components:

### Activities
- **MainActivity**: The main entry point of the application
- **HotelViewActivity**: Displays a list of hotels based on search criteria
- **AboutHotelActivity**: Shows detailed information about a specific hotel
- **ReserveHotelActivity**: Handles the hotel reservation process

### Fragments
- **HotelListFragment**: Displays a list of hotels
- **OverviewFragment**: Shows an overview of a hotel
- **DetailsFragment**: Provides detailed information about a hotel
- **ReviewFragment**: Displays and allows users to submit reviews
- **MapFragment**: Shows the hotel location on a map

### Adapters
- **HotelAdapter**: Handles the display of hotel items in a list
- **ImageSliderAdapter**: Manages the image slider in the hotel detail view
- **ViewNavAdapter**: Manages the navigation between different fragments in the hotel detail view

### Models
- **Hotel**: Represents a hotel entity with its properties
- **HotelList**: Represents a list of hotels

## Data Management
- **HotelDataManager**: Handles data operations related to hotels, including saving and retrieving from Firebase

## Firebase Integration
The project uses Firebase for:
- **User Authentication**
- **Realtime Database** for storing hotel information

<img width="2768" alt="StayDream_picture" src="https://github.com/user-attachments/assets/519fb03d-68e9-4661-9a80-ec774278d2af">

