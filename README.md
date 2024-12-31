# StayDream 


StayDream is an Android application for hotel booking and management, offering a seamless experience for users to find and book their perfect stay.

<p align="center">
  <img src="https://github.com/user-attachments/assets/4b4c6db8-d58c-403a-9167-bdf3c8354d11" width="200">
  <img src="https://github.com/user-attachments/assets/fc3c045d-8944-4eb4-83b9-f188267800bf" width="200">
  <img src="https://github.com/user-attachments/assets/1b41a716-1c50-442b-95ec-8288be1a1d23" width="200">
</p>
<p align="center">
  <img src="https://github.com/user-attachments/assets/b7f73a51-277e-4e66-9ba7-caf4a92b1f2d" width="200">
   <img src="https://github.com/user-attachments/assets/423d1ca1-5bc7-4ade-b851-49f4814a7528" width="200">
  <img src="https://github.com/user-attachments/assets/d09fa634-54c8-4b24-b76c-1b2c24c89984" width="200">
</p>



### Video Showcase!
https://github.com/user-attachments/assets/07a18c47-9f6d-4dc1-8504-871d5166d662



## ✨ Features

- 🔐 User authentication using Firebase Authentication
- 🔍 Hotel listing and search functionality
- 🖼️ Detailed hotel view with images, description, and booking options
- ❤️ Favorite hotels functionality
- 👤 User profile management

## 🛠️ Technologies Used

- Android SDK
- Firebase (Authentication and Realtime Database)
- Material Design 3
- Dynamic UI components

## 🏗️ Project Structure

### Activities
- `MainActivity`: The main entry point of the application
- `HotelViewActivity`: Displays a list of hotels based on search criteria
- `AboutHotelActivity`: Shows detailed information about a specific hotel
- `ReserveHotelActivity`: Handles the hotel reservation process

### Fragments
- `HotelListFragment`: Displays a list of hotels
- `OverviewFragment`: Shows an overview of a hotel
- `DetailsFragment`: Provides detailed information about a hotel
- `ReviewFragment`: Displays and allows users to submit reviews
- `MapFragment`: Shows the hotel location on a map

### Adapters
- `HotelAdapter`: Handles the display of hotel items in a list
- `ImageSliderAdapter`: Manages the image slider in the hotel detail view
- `ViewNavAdapter`: Manages the navigation between different fragments in the hotel detail view

### Models
- `Hotel`: Represents a hotel entity with its properties
- `HotelList`: Represents a list of hotels

### Data Management
- `DataManager`: Handles data operations related to hotels, including saving and retrieving from Firebase

## 🔥 Firebase Integration

StayDream leverages Firebase for:
- 👤 User Authentication
- 💾 Realtime Database for storing hotel information

## 🚀 Getting Started

To get started with StayDream:

1. Clone this repository
2. Open the project in Android Studio
3. Set up a Firebase project and add the `google-services.json` file to the app directory
4. Build and run the application on an emulator or physical device





