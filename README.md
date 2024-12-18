# Aggregation Service

### How to test and run 

#### Prerequisites
- install maven [maven](https://maven.apache.org/install.html)
- install docker [Docker](https://docs.docker.com/engine/install/)

#### Build local
```shell
mvn clean package -DskipTests
```
#### Build and run in Docker
```shell
docker-compose up -d
```

### Task definition
The service should aggregate users data from different sources, such as the multiple databases (Postgres, ORACLE, MySQL, etc.) and support a custom API.  
#### Conditions
 - First of all the API must provide single rest endpoint for selecting data, selected from all databases
```
GET /users
```
Success response body example:
```json
[
  {
    "id": "example-user-id-1",
    "username": "John",
    "name": "jdoe",
    "surname": "Doe"
  },
  {
    "id": "example-user-id-2",
    "username": "Jane",
    "name": "jane_smith",
    "surname": "Smith"
  },
  {
    "id": "example-ldap_login-1",
    "username": "example-ldap_login-1",
    "name": "Alice Johnson",
    "surname": "Manager"
  },
 ...
]
```
- As optional the users endpoint may support filter by some fields and paging.
- Application must have declarative configuration for specification of data sources, maximal quality of data sources is infinite:
```yaml
data-sources:
  - name: data-base-1
    strategy: postgres #this property is optional, only if you implement multiple database type support (Postgres, ORACLE, MySQL...)
    url: jdbc://.....
    table: users
    user: testuser
    password: testpass
    mapping:
    id: user_id
    username: login
    name: first_name
    surname: last_name
  - name: data-base-2
    strategy: postgres
    url: jdbc://.....
    table: user_table
    user: testuser
    password: testpass
    mapping:
    id: ldap_login
    username: ldap_login
    name: name
    surname: surname
  - name: db_mysql_1
    strategy: mysql
    url: jdbc:mysql://localhost:3306/db_mysql_1
    table: users_mysql
    user: root
    password:
    mapping:
      id: user_id
      username: login
      name: first_name
      surname: last_name
```

### Strong requirements
• Use Spring Boot for writing this application
• Use OpenApi contracts specification for declare endpoint definition

### Testing
• Add integration test using h2 database
• Add integration test using testcontainers
• Add selecting filters in api and queries
