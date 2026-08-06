# 🚀 AI Code Reviewer 

<p align="center">
  <img src="src/main/resources/templates/assets/codelens.png" alt="AI Code Reviewer" />
</p>

An AI-powered full-stack application that analyzes code for bugs, security issues, performance problems, and improvements using Google Gemini API.

---

## ✨ Features

- 🔍 Detects bugs and edge cases
- 🔐 Identifies security vulnerabilities
- ⚡ Highlights performance issues
- 💡 Suggests improvements
- 📊 Provides structured feedback (Summary, Bugs, Security, Performance, Improvements, Rating)
- 🧠 Supports multiple languages (Java, Python, JavaScript, etc.)
- 🕘 Stores review history using Hibernate JPA
- 🖥️ Interactive UI with Monaco Editor

---

## 🏗️ Tech Stack

| Layer      | Technology |
|-----------|-----------|
| Backend   | Java 17, Spring Boot |
| ORM       | Hibernate JPA |
| Database  | MySQL |
| AI        | Google Gemini API |
| Frontend  | HTML, CSS, JavaScript |
| Editor    | Monaco Editor |

---

## ⚙️ Setup Instructions

### 1️⃣ Clone the repository
git clone https://github.com/YOUR-USERNAME/YOUR-REPO.git<br>
cd YOUR-REPO

### 2️⃣ Configure database

Create MySQL database:

CREATE DATABASE codereview;

### 3️⃣ Update application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/codereview<br>
spring.datasource.username=YOUR_USERNAME<br>
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update

gemini.api.key=YOUR_API_KEY<br>
gemini.api.url=https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent

### 4️⃣ Run the application
mvn spring-boot:run

### 5️⃣ Open in browser
http://localhost:8080<br>

🧠 How it works<br>
User enters code in the editor<br>
Request is sent to Spring Boot backend<br>
Backend calls Gemini API<br>
AI analyzes code and returns structured response<br>
Result is displayed and stored in database


📁 Project Structure<br>
src/main/java/com/codereview/<br>
├── controller/       # REST + UI controllers<br>
├── service/          # Business logic + AI integration<br>
├── repository/       # JPA repositories<br>
├── model/            # Entity classes<br>
├── dto/              # Data transfer objects<br>
├── config/           # Configuration classes<br>


🚧 Future Improvements
🔐 Authentication (JWT)<br>
📊 Code quality score visualization<br>
🎯 Beginner vs Expert feedback mode<br>
📄 Export reports (PDF)<br>
☁️ Cloud deployment<br>

🤝 Contributing<br>
Contributions are welcome! Feel free to fork and improve.

📬 Contact<br>
If you have feedback or suggestions, feel free to connect!<br>
gmail: divyasonawane378@gmail.com<br>
linkedin: https://www.linkedin.com/in/divya-sonawane1/<br>

⭐ If you like this project<br>
Give it a star ⭐ on GitHub!<br>

