# QuickCheck Backend

Spring Boot backend scaffold for the QuickCheck mobile application.

## Stack

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA / Hibernate
- PostgreSQL
- Flyway
- Spring Security
- JWT
- Stripe
- OpenAPI / Swagger

## Package architecture

The project uses feature-based modularization:

- `auth` - authentication
- `user` - users
- `assessment` - assessment modules
- `question` - dynamic questionnaire/content
- `answer` - submitted answers
- `scoring` - scoring and decision engine
- `result` - assessment results
- `firstaid` - first-aid content
- `payment` - premium unlocks and Stripe
- `feedback` - ratings and comments
- `admin` - admin dashboard APIs
- `report` - CSV/Excel reports
- `security` - authentication/authorization infrastructure
- `common` - shared infrastructure
- `config` - application configuration

## Important design requirement

Questionnaires, scoring rules, thresholds, first-aid content, translations, assessment categories,
body parts, and pricing should eventually be database-driven rather than hardcoded.

## Run

Configure PostgreSQL and then run:

    ./mvnw spring-boot:run

or:

    mvn spring-boot:run

The current classes are intentionally scaffolds. Business logic, entities, repository methods,
controllers, DTO fields, migrations, and security rules are left for implementation.
