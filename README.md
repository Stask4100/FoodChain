# FoodChain

**FoodChain** is a Spring Boot project that simulates a **food chain**.  
You can create animals (lions, goats, cows) and grass, as well as perform actions:
- feed animals
- view all created animals
- find animals by name
- handle errors (e.g., dead animals cannot be fed, grass cannot be eaten twice).

---

##  Technologies Used
- **Java 21**
- **Spring Boot** (Web, Data JPA)
- **Hibernate**
- **H2 Database** (in-memory, for testing)
- **MySQL** (main database)
- **JUnit 5 + Mockito** (testing)

---

##  Getting Started

1. Clone the repository:
   ```bash
   git clone https://github.com/Stask4100/foodchain.git
   ```

2. Configure `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/foodchain?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
   spring.datasource.username=root
   spring.datasource.password=password
   ```

   > Replace **foodchain**, **username**, and **password** with your own database configuration.

3. Start the application:
   - Run `FoodChainApplication`
   - Ensure MySQL is running and properly connected
   - Open **Postman** (or similar tool)
   - Test endpoints, e.g.:
     ```
     http://localhost:8080/lions
     ```

---

##  Endpoints

###  Lion
- **Create a lion**
  ```http
  POST /lions
  Content-Type: application/json

  {
    "name": "Simba"
  }
  ```

  **Response**:
  ```json
  {
    "name": "Simba",
    "alive": true
  }
  ```

- **Get all lions**
  ```http
  GET /lions
  ```

- **Get lion by name**
  ```http
  GET /lions/{name}
  ```

- **Feed a lion**
  ```http
  PUT /lions/{lionName}?foodName={animalName}&foodType={cow|goat}
  ```

  Example:
  ```http
  PUT /lions/Simba?foodName=Goaty&foodType=goat
  ```

  **Response**:
  ```json
  {
    "lion": "Simba",
    "ate": "Goaty",
    "status": "successful"
  }
  ```

---

###  Cow
- **Create a cow**
  ```http
  POST /cows
  Content-Type: application/json

  {
    "name": "Bessie"
    "alive": true
  }
  ```

- **Get all cows**
  ```http
  GET /cows
  ```

- **Get cow by name**
  ```http
  GET /cows/{name}
  ```

  - **Feed cow by name**
  ```http
  PUT /cows/{name}/eat-grass
  ```

---

###  Goat
- **Create a goat**
  ```http
  POST /goats
  Content-Type: application/json

  {
    "name": "Goaty"
    "alive": true
  }
  ```

- **Get all goats**
  ```http
  GET /goats
  ```

- **Get goat by name**
  ```http
  GET /goats/{name}
  ```

  - **Feed goat by name**
  ```http
  PUT /goats/{name}/eat-grass
  ```

---

##  Rules & Restrictions
1. To feed a lion → create a goat or cow.  
2. Once an animal/grass is eaten, it **cannot** be eaten again.  
3. Names of animals must be **unique**.  
4. Invalid requests will return appropriate error messages.  
