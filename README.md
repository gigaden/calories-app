![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.0-green) ![Java](https://img.shields.io/badge/Java-21-blue) ![Monolith](https://img.shields.io/badge/Architecture-Monolith-orange)

Calories App – REST API сервис для отслеживания дневной нормы калорий пользователя и учета съеденных блюд..

## 🏗 Архитектура

Проект построен на монолитной архитектуре и включает следующие сервисы:

1. **User Service** – управление пользователями (регистрация, удаление и т. д.).
   * На основе данных автоматически рассчитывается дневная норма калорий (по формуле Харриса-Бенедикта). 
2. **Dish Service** – отвечает за добавление блюд и их управлением.
3. **Eating Service** – обеспечивает добавление приёмов пищи со списком бюд.
4. **Report Service** – формирует необходимые отчёты:
   * Отчёт за текущий день с суммой всех калорий и приемов пищи.
   * Проверяет уложился ли пользователь в свою дневную норму калорий.
   * История питания по дням за выбранный период.

## 📌 Стек технологий

- **Java 21**
- **Spring Boot** (Web, Data JPA)
- **Liquibase** (Выполняет миграции)
- **PostgreSQL** (основная БД)
- **Docker, Docker Compose** (контейнеризация)
- **SpringDoc** (документация API со SwaggerUI)

## 🚀 Запуск проекта

### 1. Клонирование репозитория

```bash
git clone https://github.com/gigaden/calories-app.git
cd calories-app
```

### 2. Запуск через Docker Compose

```bash
mvn clean install
docker-compose up -d
```

### 3. Запуск вручную

```bash
cd calories-app
mvn spring-boot:run
```

### 4. Доступные эндпоинты

#### 📌 Приложение отвечает на localhost:8080. После запуска доступна схема API

| URL                                                      | Описание                       |
|----------------------------------------------------------|--------------------------------|
| [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)| Доступ к документации|
| [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)      | Спецификация OAS|


## 🛠 Разработка и улучшение

0. Добавить постмановские тесты и дописать юниты.
1. Добавление security, сейчас все эндпоинты публичные.
2. Добавление сервиса уведомлений с добавлением Kafka.
3. Фронт.
