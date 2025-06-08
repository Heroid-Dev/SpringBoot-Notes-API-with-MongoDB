# Spring Notes API

A simple Notes API built using Spring Boot and Kotlin.  
This project demonstrates how to create a RESTful API with MongoDB integration, user and note management, and basic security configuration.

---

## 📦 Features

- Create, retrieve, and delete notes
- Filter notes by user (owner) ID
- MongoDB integration using Spring Data
- Stateless API configuration (CSRF disabled, no sessions)
- Basic user repository

---

## 📂 Project Structure

```
src/
├── main/
│   ├── kotlin/
│   │   └── com/example/springsecondproject/
│   │       ├── model/
│   │       │   ├── Note.kt
│   │       │   └── User.kt
│   │       ├── repository/
│   │       │   ├── NoteRepository.kt
│   │       │   └── UserRepository.kt
│   │       ├── controller/
│   │       │   └── NoteController.kt
│   │       ├── config/
│   │       │   └── SecurityConf.kt
│   │       └── SpringSecondProjectApplication.kt
```

---

## 🧪 API Endpoints

### `POST /notes`
Create or update a note.

**Request Body:**
```json
{
  "id": "optional_note_id",
  "title": "Sample Note",
  "content": "This is the content.",
  "color": 123456
}
```

---

### `GET /notes/id?ownerId=USER_ID`
Retrieve all notes for a specific user.

---

### `DELETE /notes/id?ownerId=USER_ID`
Delete all notes for a specific user.

---

### `DELETE /notes/{id}`
Delete a specific note by its ID.

---

### `GET /notes/all`
Retrieve all notes in the database.

---

## 🛡 Security

- CSRF is disabled.
- Stateless session policy using `SessionCreationPolicy.STATELESS`.

---

## 🛠 Tech Stack

- Kotlin
- Spring Boot
- Spring Web
- Spring Data MongoDB
- MongoDB
- Gradle

---

## 🚀 Getting Started

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/spring-notes-api.git
   ```

2. Update your MongoDB configuration in `application.properties`:
   ```properties
   spring.data.mongodb.uri=mongodb://localhost:27017/your-db
   ```

3. Run the application:
   ```bash
   ./gradlew bootRun
   ```

---

## 📌 TODO

- Add authentication and JWT support
- Add unit and integration tests
- Add validation to request bodies

---

## 📝 License

This project is licensed under the MIT License.
