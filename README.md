# Project: Practice 1 Data acquisition from external sources

**Subject:** Development of Applications for Data Science

**Course:** 2nd year

**Degree:** Engineering and Data Science

**School:** School of Computer Engineering

**University:** University of Las Palmas de Gran Canaria

## Instructions before running the program
After downloading the project from the Git repository, to execute the program, you need to provide two variables as command-line arguments in the Main class. The first argument in the args array should be the API key for the OpenWeather "Call 5 day / 3 hour forecast data" API. The second argument in the array should be the path where the database will be stored, along with the desired name for the database. The path must have this format: "\<nameoffolder>/\<databasename>.db". Once these arguments have been specified and stored in the args String array, you can run the program.

<img width="400" alt="First Step" src="https://github.com/alejandroalemanaleman/Memoria/assets/145342887/ac2456f1-1ddf-43cf-95fd-460d8e3684b4">
<img width="480" alt="Second Step" src="https://github.com/alejandroalemanaleman/Memoria/assets/145342887/8678f81d-9ab7-4534-ad1e-d9989ef13331">

Link to OpenWeatherMap API: https://openweathermap.org/forecast5


## Summary of Functionality

The program is designed to periodically fetch weather data for eight different islands in the Canary Islands archipelago for the 5 next days. It utilizes the OpenWeatherMap API to retrieve meteorological information, including temperature, precipitation probability, humidity, cloud coverage, and wind speed. The data is obtained and updated in the database every six hours, focusing on the weather forecast for the next five days at 12 p.m. each day.



## Resources Used

- **Development Environments:**
  The application was developed using IntelliJ IDEA, a popular integrated development environment (IDE) for Java. IntelliJ IDEA stands out for Java development. Its user-friendly interface and seamless integration with version control systems ensure a streamlined and efficient development process.

- **Version Control Tools:**
  Git, a crucial version control system, managed the project's source code, ensuring a reliable change history. Git commands, including commit, played a important role in maintaining an organized and collaborative development process.

- **Dependencies management:**
  The provided Maven Project Object Model (POM) file is essential for managing the project. In this file, key details like project organization, name, and version are defined. The properties section specifies Java versions and encoding.
  Dependencies are crucial external libraries needed for the project. Notably, it includes SQLite JDBC for database connectivity, Jsoup for HTML manipulation, and Gson for JSON processing.

- **Documentation Tools:**
  The project documentation was crafted using Markdown, a lightweight markup language that offers simplicity and readability. Markdown provides an easy-to-learn syntax for formatting text, making it an efficient choice for creating clear and well-organized documentation.

## Design

The following design patterns and principles have been applied:

**Single Responsibility Principle (SRP):** Each class should have only one reason to change. For example, the `OpenWeatherMapSupplier` class is responsible for interacting with the OpenWeatherMap API, while the `SQLiteWeatherStore` class is responsible for storing and retrieving data in the SQLite database.

**Open/Closed Principle (OCP):** Classes should be designed to be extended without modifying their original source code. For example, new locations (`Location`) or weather providers (`WeatherSupplier`) can be added without changing existing classes.

**Observer Pattern:**
The use of Timer and TimerTask in the Main class indicates a form of periodic observation or event scheduling. The WeatherController extends TimerTask, and its run method is periodically executed, triggering weather-related tasks.

### Class Diagram

![DiagramaClases](https://github.com/alejandroalemanaleman/Practice1/assets/145342887/596ce23b-6c63-44d6-9acc-80ee9cd5384b)

### Dependency Relationships

There are several classes that work together to achieve the functionality of retrieving weather information from OpenWeatherMap API, storing it in an SQLite database, and managing the overall application flow. Let's analyze the dependency relationships between these classes:

1. **Main:**
   - Dependencies:
     - `WeatherController`: It creates an instance of the `WeatherController` class and schedules it to run periodically.

2. **WeatherController:**
   - Dependencies:
     - `OpenWeatherMapSupplier`: It depends on this class to obtain weather information.
     - `SQLiteWeatherStore`: It depends on this class to store the retrieved weather information in an SQLite database.
   - Execution:
     - Calls the `execute` method, which in turn calls methods from `OpenWeatherMapSupplier` and `SQLiteWeatherStore`.

3. **OpenWeatherMapSupplier:**
   - Dependencies:
     - `Location`: It works with instances of the `Location` class to build API URLs and manage location-related data.
     - External libraries: It uses Gson for JSON parsing and Jsoup for connecting to the OpenWeatherMap API.

4. **SQLiteWeatherStore:**
   - Dependencies:
     - `Weather`: It interacts with instances of the `Weather` class to store weather data in the database.
     - External libraries: Uses JDBC for database connectivity.

5. **Location:**
   - Dependencies: None (simple data-holding class).

6. **Weather:**
   - Dependencies:
     - `Location`: It holds information about the location associated with the weather data.
     - `Instant`: It uses `Instant` for timestamps associated with when the weather was checked.

7. **WeatherStore:**
   - Dependencies:
     - `Weather`: It works with instances of the `Weather` class to define the contract for storing weather information.

8. **WeatherSupplier:**
   - Dependencies:
     - `Weather`: It works with instances of the `Weather` class to define the contract for obtaining weather information.

These relationships illustrate how the classes collaborate to achieve the objectives of retrieving, processing, and storing weather information in the specified Java application.
