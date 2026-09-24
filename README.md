# QuickCheck backend

QuickCheck is a multilingual English/Arabic REST backend for configurable health-screening assessments. It contains no user accounts, authentication, JWT, or authorization; `/admin` path segments only distinguish content-management endpoints during the MVP.

## Stack and setup

Java 17, Spring Boot, Spring Web, Spring Data JPA/Hibernate, PostgreSQL, Lombok, Bean Validation, and Maven. Create a PostgreSQL database named `quickcheck`, then use `src/main/resources/application-dev.yml` (port `8081`). Hibernate uses `ddl-auto: update`; Flyway remains disabled and existing data is never reset.

## API structure

- Assessments: `GET /assessments`, `GET /assessments/{id}`, `GET /assessments/admin`, `POST /assessments`, `PUT /assessments/admin/{id}`, `PATCH /assessments/admin/{id}/enabled`, `DELETE /assessments/admin/{id}`.
- Questions: `GET /questions/assessment/{assessmentId}`, `GET /questions/{id}`, `GET /questions/admin/assessment/{assessmentId}`, `POST /questions`, `PUT /questions/admin/{id}`, `PATCH /questions/admin/{id}/enabled`, `DELETE /questions/admin/{id}`.
- Outcomes: `GET /outcomes/assessment/{assessmentId}`, `POST /outcomes`, `PUT /outcomes/admin/{id}`, `DELETE /outcomes/admin/{id}`.
- First aid: `GET /first-aid/assessment/{assessmentId}`, `POST /first-aid`, `PUT /first-aid/admin/{id}`, `DELETE /first-aid/admin/{id}`.
- Execution: `POST /assessment-executions`.
- Feedback: `POST /feedback`, `GET /feedback/admin/assessment/{assessmentId}`.

All mutation payloads are validated. Parent translations reject duplicate languages before persistence and updates replace child records under cascade/orphan removal. Errors use a clean JSON body with status, message, path, and validation-field details.

## Workflow and scoring

An administrator configures assessments, translated Yes/No questions, score/red-flag behavior, and translated score-band or red-flag outcomes. The app reads enabled content, submits one answer per enabled question, and receives the calculated score, red-flag question IDs, and selected outcome. Medical result and first-aid text are content-managed, not hardcoded in Java.

## Postman examples

```json
POST /assessments
{"code":"BABY_COLIC","enabled":true,"price":2.99,"translations":[{"language":"EN","name":"Baby Colic Assessment","description":"Screening questionnaire"},{"language":"AR","name":"تقييم مغص الأطفال","description":"استبيان فحص"}]}
```

```json
POST /questions
{"assessmentId":1,"questionOrder":1,"enabled":true,"required":true,"questionType":"YES_NO","yesScore":2,"noScore":0,"redFlagOnYes":false,"redFlagOnNo":false,"translations":[{"language":"EN","text":"Does the baby cry for more than 3 hours per day?"},{"language":"AR","text":"هل يبكي الطفل لأكثر من 3 ساعات يومياً؟"}]}
```

```json
POST /outcomes
{"assessmentId":1,"minimumScore":0,"maximumScore":5,"redFlagOutcome":false,"severity":"HOME_CARE","premium":false,"translations":[{"language":"EN","title":"Home care","description":"Monitor symptoms and follow the guide."},{"language":"AR","title":"رعاية منزلية","description":"راقب الأعراض واتبع الدليل."}]}
```

```json
POST /assessment-executions
{"assessmentId":1,"answers":[{"questionId":1,"value":true}]}
```

The execution result has `assessmentId`, `score`, `redFlag`, `redFlagQuestionIds`, and a translated `outcome`.

```json
POST /feedback
{"assessmentId":1,"rating":5,"comment":"Clear and useful."}
```
