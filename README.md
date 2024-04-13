# Weather App

## Application Functionality

### User Management:
- Registration
- Authorization
- Logout

### Location Management:
- Search
- Add to list
- View list of locations, each location displays the name and temperature
- View temperature by hours and days
- Remove from list

## Application Interface

Bootstrap 5 is used for layout.

### Header

- For unauthorized users - logo and title
- For authorized users - logo, title, search, username, logout

### Main Page

- For unauthorized users - authorization and registration
- For authorized users - display of added locations, location search, weather view

### Location Search Results Page

Transition to this page is made as a result of filling in the input field on the main page, or on the search results page.

- Input field for searching by name - the same as on the main page, so as not to return there for each new search
- List of found locations with an "add" button. Clicking on the button leads to the main page

## Database

MySQL is used.

### Users Table
Column Name | Type | Comment
--- | --- | ---
ID | Int | Primary key, autoincrement
Login | Varchar | User login, username or email
Password | Varchar | Password is stored encrypted, for example using BCrypt

### Locations Table
Column Name | Type | Comment
--- | --- | ---
ID | Int | Location ID, autoincrement, primary key
Name | Varchar | Location name
UserId | Int | User who added this location
Latitude | Decimal | Location latitude
Longitude | Decimal | Location longitude

## Sessions

Redis is used.

## Weather Information Retrieval

OpenWeatherMap API is used to retrieve weather information.


## Интеграционные тесты

Покрытие тестами связки слоя данных с классами-сервисами, отвечающими за пользователей и локации.
