### **README.md**

# MongoDB CRUD Project with Spring Boot 3.4.0 and Java 21

## **Project Overview**

This is a simple CRUD (Create, Read, Update, Delete) project that demonstrates how to use MongoDB as the database with Spring Boot 3.4.0 and Java 21. The application allows you to register, update, retrieve, and delete pet records. It's designed to be beginner-friendly project.

---

## **Technologies Used**
- **Java**: Version 21
- **Spring Boot**: Version 3.4.0
- **MongoDB**: NoSQL Database version 8.0
- **Maven**: Dependency Management

---

## **Key Notes**

### **Deprecation of `@MockBean`**
As of Spring Boot 3.4.0, the `@MockBean` annotation for tests has been deprecated, and it will be removed in 3.6.0 . The replacement is the `@MockitoBean` annotation provided by `org.springframework.test.context.bean.override.mockito.MockitoBean`.

**Example:**
```java
// Deprecated
@MockBean
private MyService myService;

// Replacement
@MockitoBean
private MyService myService;
```

---

## **Advantages of NoSQL Databases**

1. **Schema Flexibility**: MongoDB allows you to store data without a fixed schema, making it easier to adapt to changing application requirements.
2. **Horizontal Scalability**: NoSQL databases like MongoDB scale out easily by adding more servers.
3. **High Performance**: Optimized for read and write operations, especially for large volumes of unstructured data.
4. **Support for Complex Data Types**: MongoDB natively handles hierarchical relationships and embedded documents.
5. **Cost Efficiency**: Open-source nature and flexibility often reduce infrastructure costs compared to traditional databases.

---

## **Relational vs. MongoDB Command Comparison**

| **Operation**           | **Relational Database**                     | **MongoDB**                                    |
|--------------------------|---------------------------------------------|-----------------------------------------------|
| **Find**                | `SELECT * FROM table WHERE id = ?`          | `db.collection.find({ _id: value })`          |
| **Insert**              | `INSERT INTO table (columns) VALUES (?)`    | `db.collection.insertOne({ field: value })`   |
| **Update**              | `UPDATE table SET field = ? WHERE id = ?`   | `db.collection.updateOne({ _id: value }, { $set: { field: value } })` |
| **Delete**              | `DELETE FROM table WHERE id = ?`            | `db.collection.deleteOne({ _id: value })`     |
| **Join**                | `SELECT * FROM A JOIN B ON A.key = B.key`   | MongoDB uses `$lookup` for joins: `{ $lookup: { from: "B", localField: "key", foreignField: "key", as: "joinedData" } }` |

---

## **How to Run**

### **Prerequisites**
1. Java 21 installed
2. MongoDB running locally or in a Docker container
3. Maven installed

### **Steps**
1. Clone the repository:
   ```bash
   git clone https://github.com/lmaruyama/mongodb-crud.git
   ```
2. Navigate to the project directory:
   ```bash
   cd mongodb-crud
   ```
3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```
4. The application will be available at `http://localhost:8080/api/v1/pets`.

---

## **API Endpoints**

| **Method** | **Endpoint**               | **Description**            |
|------------|----------------------------|----------------------------|
| POST       | `/api/v1/pets`             | Create a new pet           |
| GET        | `/api/v1/pets`             | Get all pets               |
| GET        | `/api/v1/pets/{id}`        | Get a pet by ID            |
| GET        | `/api/v1/pets/{type}/type` | Get a pet by type [DOG or CAT] |
| PUT        | `/api/v1/pets/{id}`        | Update a pet by ID         |
| DELETE     | `/api/v1/pets/{id}`        | Delete a pet by ID         |

---

## **Testing**

The application includes unit tests for the controller layer. Mocking is handled using the new `@MockitoBean` annotation.

To run tests:
```bash
./mvnw test
```

---

## **Why MongoDB?**

MongoDB is an excellent choice for modern applications due to its scalability, performance, and flexibility. It fits particularly well in applications where the data structure evolves frequently or hierarchical relationships are common.

