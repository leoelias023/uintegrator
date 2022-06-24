# UIntegrator API

Manage all ecosystem through the REST API.

## Usage

### Requirements

- [MySQL Database 8.0.29](../database/README.md#using-mysql-from-docker)

### 1. Configuring

#### 1.1. Gradle repository credentials

To get dependencies from artifactory, please follow [these steps](../README.md#configuraes-globais) for configure credentials.

#### 1.2. Database properties

Ensuring connection with the mysql database, configure credentials in `appplication-dev.properties`.

```properties
spring.datasource.username=user
spring.datasource.password=password
```

### 2. Executing

````