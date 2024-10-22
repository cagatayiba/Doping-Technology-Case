# Doping Technology Case

## Services

---------

### Solving Tests
- PUT /api/v1/student-test/{studentId}/{testId}/start (start test)
- GET /api/v1/student-test/{studentId}/{testId}/get-question/{questionNumber} (fetch question)
- POST /api/v1/student-test/{studentId}/{testId}/answer-question (answer question) 
- GET /api/v1/student-test/{studentId}/{testId}/continue (continue test)
- PUT /api/v1/student-test/{studentId}/{testId}/submit (submit test)

In this flow users first starts the test which creates a db record (student_test)
with state of STARTED. Tests dont have time limit but keeping this states helpful
for validations and continuing test use case.

User can submit the test after answering questions or can prematurely exits. If 
he/she submits a report will bi generated for him/her and be persisted under
test_result table. More detailed reports (including question details)can be 
obtained by the endpoints below. If user exits without submitting hid/her test progress
stays as STARTED so he/she can come back and continue the test. 

### Reports

- GET /api/v1/test-result/{studentId} (limited test result)
- GET /api/v1/test-result/{studentId}/detail/{testId} (detailed test result)

I build a two phase reporting system where in first phase users
can see limited information about the tests they have submitted.
If they pursue they are able to see the information down to each questoun.

### CRUD for admin

- POST /api/v1/test (creates test without question)
- POST /api/v1/test/{testId}/add-question (add questions)
- GET /api/v1/test/{testId}/set-ready (tests are available after this call)

For admins i make question adding operation one by one to prevent
potential dta loss. After initial test record they can add questions and when 
the test is ready it can be solved by students but not before. Since at DRAFT
stage tests are likely to change.

you can look the all endpoints from http://localhost:8080/swagger-ui/index.html

## Validations

------------
Validations are performed in a separate layer to keep them distinct from the core 
business logic. This allows them to be tested independently, although I haven't found 
enough time to do so. Sequential private method calls may not be very elegant, but 
using spies for testing should make this less of a challenge.

## Cache

-------

- Spring cache is used.
- Questions are cached when they are accessed.

"There were other options for caching, particularly for reports. However, although reports are unlikely to change due to their nature, individual reports don’t seem to be accessed frequently enough to justify caching, especially considering that each report is unique to a user. On the other hand, questions are shared among users, remain immutable once created, and are accessed frequently. More complex caching strategies could be considered, such as asynchronously caching the next question when a user accesses one.

## Data Model

------
![schema](/schema.png)

## Exception Handling

--------
Global exception handling is used. I tried to implement user-friendly exception messaging, but in some cases, the messages are bit off. i18n should be considered for better message handling, allowing the inclusion of parameters and supporting multiple languages.







