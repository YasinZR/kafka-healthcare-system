# Kafka Healthcare System

## 📌 Описание

Проект представляет собой микросервисную систему для хранения записей к врачу.  
Данные обрабатываются через Kafka и сохраняются в PostgreSQL.  
Система реализована с использованием Spring Boot, Docker и Kafka.

---

## 🧱 Архитектура

Система состоит из следующих компонентов:

- **API Service** (`api-service`) — REST API, принимает HTTP-запросы и отправляет данные в Kafka.
- **Data Service** (`data-service`) — получает сообщения из Kafka, сохраняет данные в PostgreSQL и предоставляет отчёты.
- **Kafka + Zookeeper** — брокер сообщений.
- **PostgreSQL** — база данных для хранения записей.
- Всё развернуто в Docker-контейнерах через `docker-compose`.

---

## 🚀 Как запустить

1. Клонируйте репозиторий и перейдите в корневую директорию проекта:
   ```bash
   git clone https://github.com/YasinZR/kafka-healthcare-system.git
   
   cd kafka-healthcare-system
   ```

2. Запустите систему:
   ```bash
   docker-compose up --build
   ```

3. Готово! Сервисы будут доступны по адресам:
    - API Service: http://localhost:8080
    - Data Service: http://localhost:8081
    - PostgreSQL: localhost:5432 (user: `admin`, pass: `admin`, db: `healthcare_db`)

---

## 📬 Примеры запросов

### ➕ Добавить запись (POST)
```http
POST http://localhost:8080/appointments
Content-Type: application/json

{
  "patientName": "Иван Иванов",
  "doctorName": "Доктор Ясин",
  "date": "2025-05-14",
  "time": "10:00"
}
```

### 📄 Получить все записи
```http
GET http://localhost:8081/api/appointments/search
```

### 📊 Топ-10 популярных врачей
```http
GET http://localhost:8081/api/reports/popular-doctors
```

### 📆 Записи по дням
```http
GET http://localhost:8081/api/reports/appointments-by-day
```

---

## ⚙️ Стек технологий

- Java 17 + Spring Boot 3
- Apache Kafka
- PostgreSQL 15
- Docker + Docker Compose
- Maven

---

## 🗃 Структура проекта

```
kafka-healthcare-system/
├── api-service/          # Сервис для приёма данных
├── data-service/         # Сервис для обработки и сохранения данных
├── docker-compose.yml    # Docker orchestration
```

---

