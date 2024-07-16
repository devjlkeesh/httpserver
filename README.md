# Simple Todo HTTP Server in Java

This project is a simple HTTP server for managing todo items, built using Java's standard HttpServer API. It allows users to create, read, update, and delete (CRUD) todo items through a RESTful API.

## Features

- Create a new todo item
- Read all todo items or a specific item
- Update an existing todo item
- Delete a todo item

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- postgresql
- ```sql
  create database httpserver;
  
  create table if not exists todos(
    id bigserial primary key,
    title varchar not null,
    description varchar not null,
    user_id bigint not null,
    priority varchar not null default 'LOW',
    done boolean default 'f' not null,
    created_at timestamp not null default current_timestamp 
  );
    ```
---
# build jar

```shell
mvn clean package
cd target
java -jar httpserverexec.jar
```

---

## API

- reads all todos
```
GET localhost:8080/todo
```

-  reads todo which has id 1
```
GET localhost:8080/todo/1 
```

- create todo
```
POST localhost:8080/todo

Content-Type : application-json
{
    "title":"Title for todo",
    "description":"Description for todo",
    "user_id":"User id which todo belongs to",
    "priority":"HIGH"
}
```

- update todo
```
PUT localhost:8080/todo # update todo 

Content-Type : application-json
{
    "id":1,
    "title":"Title for todo",
    "description":"Description for todo",
    "priority":"HIGH",
    "completed":true
}
```