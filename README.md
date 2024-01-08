 # Final Project for the course

**Subject:** Development of Applications for Data Science

**Course:** 2nd year

**Degree:** Engineering and Data Science

**School:** School of Computer Engineering

**University:** University of Las Palmas de Gran Canaria

## Instructions before running the program
1. Download the latest release from the Git repository, tag called "proyecto-dacd", and unzip folders ("hotel-provider", "prediction-provider", "datalake-builder" and "weather-hotel-recommender"). Open four terminal tabs, each pointing to each unzipped folder.<br><br>
2. In the "datalake-builder" tab, execute the command `java -jar datalake-builder-1.0-SNAPSHOT.jar argument`, replacing "argument" with the desired output files location. For example, if you want to save them in your Downloads folder on macOS, the command should resemble this: "/Users/nameofUser/Downloads".<br><br>
3. Similar with the "weather-hotel-recommender" tab, execute the command `java -jar weather-hotel-recommender-1.0-SNAPSHOT.jar argument`, where "argument" is the desired output datamart database location. For example, if you want to save it in your Downloads folder on macOS, the command should resemble this: "/Users/nameofUser/Downloads".<br><br>
4. Next, "hotel-provider" must be executed by using this command `java -jar hotel-provider-1.0-SNAPSHOT.jar`.<br><br>
5. Once completed, proceed to the "prediction-provider" tab and run `java -jar prediction-provider-1.0-SNAPSHOT.jar argument`, replacing "argument" with the OpenWeather API key for "Call 5 day / 3-hour forecast data." Execute the command after writing it to complete the process. These steps ensure a successful program execution. Link to OpenWeatherMap API: https://openweathermap.org/forecast5<br><br>

IMPORTANT INFORMATION BEFORE RUNNING: The program can only be continued when both "hotel-provider" and "prediction-provider" show in the their respective console the message: `-----[MESSAGE]: ALL DATA SENT.-----`

<img width="650" alt="Only accept when both show the message that data has been sent." src="https://github.com/alejandroalemanaleman/Memoria/assets/145342887/840d5d1a-37c5-4c38-bec6-cdf638b8db22">

## Summary of Functionality
Firstly, the program serves the purpose of gathering data from two distinct sources. The "hotel-provider" module periodically retrieves hotel data for the 8 Canary Islands from the TripAdvisor website, including crucial details such as the name, location, price range per night, and rating. The second data source is the OpenWeatherMap API, which provides weather data for the same eight islands every six hours, encompassing temperature, precipitation probability, humidity, cloud coverage, and wind speed. All collected data is transmitted as events to a message broker into their respective topics.

These events are systematically saved into a datalake, organized into folders and individual files named after their timestamps, a task handled by the "datalake-builder" component. The "weather-hotel-recommender" module offers a user interface that recommends the most suitable Canary Island based on a five-day weather prediction. Users can explore predictions for other islands if they are considering different destinations. Additionally, users can choose from a selection of hotels for each island, enabling them to compare options based on the price range per night and overall rating. This program is designed to assist users planning a trip to the Canary Islands by providing insights into the best island and hotel options based on weather predictions and user preferences. All data displayed in the GUI is stored in a datamart, acquired by subscribing to the respective topic in the broker. This ensures that the information presented in the graphical user interface is sourced directly from the events transmitted and organized through the message broker, providing real-time and accurate insights for users.

## Resources Used

- **Development Environments:**
  The application was developed using IntelliJ IDEA, a popular integrated development environment (IDE) for Java. IntelliJ IDEA stands out for Java development. Its user-friendly interface and seamless integration with version control systems ensure a streamlined and efficient development process.

- **Version Control Tools:**
  Git, a crucial version control system, managed the project's source code, ensuring a reliable change history. Git commands, including commit, played a important role in maintaining an organized and collaborative development process.

- **Dependencies management:**
  The provided Maven Project Object Model (POM) file is essential for managing the project. In this file, key details like project organization, name, and version are defined. The properties section specifies Java versions and encoding.
  Dependencies are crucial external libraries needed for the project. Notably, it includes Jsoup for HTML manipulation, and Gson for JSON processing and for the Java Message Service (JMS), Apache ActiveMQ library. 

- **Documentation Tools:**
  The project documentation was crafted using Markdown, a lightweight markup language that offers simplicity and readability. Markdown provides an easy-to-learn syntax for formatting text, making it an efficient choice for creating clear and well-organized documentation.

## Design

The following design patterns and principles have been applied:

**Single Responsibility Principle (SRP):** Each class should have only one reason to change. For example, the `SQLDatamartStore` class is responsible for storing and updating the datamart, while the `TopicSubscriber` class is responsible for consuming the events from the broker.

**Open/Closed Principle (OCP):** Classes should be designed to be extended without modifying their original source code. For example, new locations (`Location`) or weather providers (`WeatherSupplier`) can be added without changing existing classes.

**Observer Pattern:**
The use of Timer and TimerTask in the Main class indicates a form of periodic observation or event scheduling. The HotelController and WeatherController extends TimerTask, and its run method is periodically executed, triggering tasks.

## Class Diagram
#### Hotel-provider
<img width="650" alt="Hotel-provider" src="https://github.com/alejandroalemanaleman/FinalProject/assets/145342887/9df00046-ab9a-444a-8bdd-1d905d72ed3b">

#### Prediction-provider
<img width="650" alt="Predcition-provider" src="https://github.com/alejandroalemanaleman/Memoria/assets/145342887/959ed319-3d18-4b23-aa9b-fae9c64fb499">

#### Datalake-builder
<img width="650" alt="Datalake-builder" src="https://github.com/alejandroalemanaleman/Memoria/assets/145342887/2ff1caef-d9e1-43df-ba5a-701419401e5c">

#### Weather-hotel-recommender
<img width="650" alt="weather-hotel-recommender" src="https://github.com/alejandroalemanaleman/Memoria/assets/145342887/6739b171-d781-477f-9759-bda35883a91a">



### Dependency Relationships

### **Hotel-provider**

#### `Main`:

**Dependencies:**
- **`Timer`:** It schedules tasks and is utilized to execute the `HotelController` periodically.
- **`HotelController`:** It orchestrates the flow of the application and manages the periodic execution of hotel-related tasks.

#### `HotelController`:

**Dependencies:**
- **`TripadvisorHotelSupplier`:** It depends on this class to obtain hotel information.
- **`JMSHotelStore`:** It depends on this class to store the retrieved hotel information using JMS.

**Execution:**
- Calls the `execute` method, which, in turn, triggers the retrieval and storage of weather data.

#### `TripadvisorHotelSupplier`:

**Dependencies:**
- It works with strings of places to construct file paths and get files with links of tripadvisor of each place.

#### `JMSHotelStore`:

**Dependencies:**
- **`Hotel`:** It interacts with instances of the `Hotel` class to convert and store hotel data in JMS.
- **External libraries (`ActiveMQ`, `JMS`):** It uses ActiveMQ and JMS for interacting with the messaging system.

#### `Hotel`:

**Dependencies:**
- **`Instant`:** It uses `Instant` for timestamps associated with when the hotel was checked.

#### `HotelStore`:

**Dependencies:**
- **`Hotel`:** It defines the contract for storing hotel information.

#### `HotelSupplier`:

**Dependencies:**
- **`Hotel`:** It defines the contract for obtaining hotel information.

<br><br>


### **Prediction-provider**

#### `Main`:

**Dependencies:**
- **`Timer`:** It schedules tasks and is utilized to execute the `WeatherController` periodically.
- **`WeatherController`:** It orchestrates the flow of the application and manages the periodic execution of weather-related tasks.

#### `WeatherController`:

**Dependencies:**
- **`OpenWeatherMapSupplier`:** It depends on this class to obtain weather information.
- **`JMSWeatherStore`:** It depends on this class to store the retrieved weather information using JMS.

**Execution:**
- Calls the `execute` method, which, in turn, triggers the retrieval and storage of weather data.

#### `OpenWeatherMapSupplier`:

**Dependencies:**
- **`Location`:** It works with instances of the `Location` class to construct API URLs and manage location-related data.
- **External libraries (`Gson`, `Jsoup`):** It utilizes Gson for JSON parsing and Jsoup for connecting to the OpenWeatherMap API.

#### `JMSWeatherStore`:

**Dependencies:**
- **`Weather`:** It interacts with instances of the `Weather` class to convert and store weather data in JMS.
- **External libraries (`ActiveMQ`, `JMS`):** It uses ActiveMQ and JMS for interacting with the messaging system.

#### `Location`:

**Dependencies:** None (simple data-holding class).

#### `Weather`:

**Dependencies:**
- **`Location`:** It contains information about the location associated with the weather data.
- **`Instant`:** It uses `Instant` for timestamps associated with when the weather was checked.

#### `WeatherStore`:

**Dependencies:**
- **`Weather`:** It defines the contract for storing weather information.

#### `WeatherSupplier`:

**Dependencies:**
- **`Weather`:** It defines the contract for obtaining weather information.

These dependency relationships illustrate how each class collaborates to fulfill its specific role in the overall weather information retrieval and storage process .<br><br>


### **Datalake-builder**

#### `Main`:

**Dependencies:**
- **`TopicSubscriber`:** It relies on this class to handle the subscription and processing of messages from a JMS topic.

#### `TopicSubscriber`:

**Dependencies:**
- **`EventStoreBuilder`:** It depends on this abstraction to save the received JMS messages.
- **Semaphore:** TopicSubscriber depends on Semaphore to coordinate concurrency when processing messages.
- **External libraries (`ActiveMQ`, `JMS`):** It utilizes ActiveMQ and JMS for interacting with the messaging system.

**Execution:**
- Calls the `start` method, which initiates the JMS connection, creates a durable subscriber, and processes incoming messages.

#### `FileEventStoreBuilder`:

**Dependencies:**
- **`SaveException`:** It throws this exception when there's an issue saving the event.

#### `FileEventStoreBuilder`:

**Dependencies:**
- **`EventStoreBuilder`:** It implements this interface to provide the functionality of saving events.
- **External libraries (`Gson`):** It uses Gson for JSON parsing.

**Execution:**
- Calls methods from `Gson` to parse and extract information from the incoming JMS messages.
- Utilizes Java I/O operations for storing events in files.

These dependency relationships illustrate how each class collaborates to fulfill its specific role in the overall event subscription and processing flow.

### **Weather-hotel-recommender**
#### `Main:`

**Dependencies:**
- **Semaphore:** Main uses Semaphore for controlling concurrency during message processing.
- **TopicSubscriber:** Main relies on TopicSubscriber to subscribe to message topics.
- **SwingGUIController:** Main utilizes SwingGUIController to execute the graphical interface after TopicSubscriber completes its work.
- **DatamartProvider:** Main relies on DatamartProvider for providing data to the Swing GUI.

#### `TopicSubscriber:`

**Dependencies:**
- **Semaphore:** TopicSubscriber depends on Semaphore to coordinate concurrency when processing messages.
- **SQLiteDatamartStore:** TopicSubscriber utilizes SQLiteDatamartStore for storing data in an SQLite database.
- **External libraries (`ActiveMQ`, `JMS`):** It utilizes ActiveMQ and JMS for interacting with the messaging system.


#### `SQLiteDatamartStore:`

**Dependencies:**
- **Connection, DriverManager, PreparedStatement, ResultSet, Statement:** SQLiteDatamartStore utilizes these JDBC classes to interact with the SQLite database.
- **External libraries (`JsonParser`, `JsonObject`):** SQLiteDatamartStore depends on these Gson classes for JSON parsing.

#### `DatamartProvider:`

**Dependencies:**

- **SQLiteDatamartStore:** DatamartProvider relies on SQLiteDatamartStore for fetching weather and hotel data.
- **Weather:** DatamartProvider uses Weather class to represent weather information.
- **Hotel:** DatamartProvider uses Hotel class to represent hotel information.


#### `PlaceRecommendationCalculator:`

**Dependencies:**
- **Weather:** PlaceRecommendationCalculator uses Weather class for calculating recommendations based on weather data.

#### `SwingGUIController:`

**Dependencies:**

- **DatamartProvider:** SwingGUIController relies on DatamartProvider for fetching weather and hotel data.
- **PlaceRecommendationCalculator:** SwingGUIController uses PlaceRecommendationCalculator for calculating recommendations based on weather data.
- **JFrame, JComboBox, JOptionPane, JLabel, Font, JPanel, JButton, SwingUtilities, ImageIcon, URL, GridLayout, Insets, JScrollPane:** SwingGUIController relies on these Swing classes for creating and managing the graphical user interface.
- **Weather and Hotel:** SwingGUIController uses Weather and Hotel classes for displaying weather and hotel information.

