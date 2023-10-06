# STEM-Phase3

# STEM Upgrade Phase 3

**Project Description:**

The STEM Upgrade Project is a comprehensive initiative aimed at enhancing the user experience of our software by
modernising its graphical user interface (GUI) while retaining the core functionality that interacts seamlessly with an
SQL backend. Our primary goal is to deliver a cleaner, more responsive, and user-friendly interface.

**Project Components:**

**1. Front-End Transformation:**

The project centers around transforming the front-end of our software, known as "STEM", which is currently Java-based.
The upgraded GUI will offer users an intuitive and visually appealing interface, making it easier to interact with our
software.

The project design will revamp the existing user interface elements, such as menus, forms, and navigation, to provide a
smoother and more efficient user experience.

By incorporating modern design principles, we will improve the software's overall look and feel, enhancing user
satisfaction and productivity.

**2. Enhanced Functionality:**

The upgraded STEM software will retain its key features, including the ability to issue licenses, modify license
periods, block users, and assign applications to individual users for a single package. These essential operations will
be seamlessly integrated into the new interface.

The software will have the capability to issue licenses with a streamlined and user-friendly form that guides them
through the process efficiently.

**3. Database Interaction:**

To support these operations, the software will continue to rely on an SQL backend, utilising MySQL as the underlying
database management system. The database tables dedicated to license management and user access will be optimized for
efficient data storage and retrieval.

The software's interaction with the MySQL database will be designed for seamless data synchronization, providing
real-time updates to users. It will fine-tune the database schema to improve performance and ensure data integrity when
handling license issuance and management.

**Expected Outcomes:**

- A modern and responsive GUI that enhances user satisfaction and usability.
- Improved user engagement and efficiency when performing license-related operations.
- Continued reliability and stability by leveraging MySQL as the backend database system.
- A software upgrade that aligns with the evolving needs and expectations of our user base.

The STEM Upgrade Project is committed to delivering a more polished, user-centric software experience.

## Project Structure

- `backend/src`: Contains the source code files for backend.
- `frontend/src`: Contains the source code files for frontend.
- `backend/docs`: Documentation and user guides for backend.

## Installation and Dependencies

- `mvn clean install`
- `docker-compose up`
- `java -jar stem.jar`

## Documentation

### Backend

#### Authentication API

| Method | URL                 | Description                                                                            | Request                                                                                                 | Response OK                                                                      | Response Error                                                                                                |
|--------|---------------------|----------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------|
| POST   | /auth/login         | User login with email and password and create an access_token, for all other requests. | <pre lang="json">{<br>   "email":"exampleUsername",<br>   "password":"examplePasswordHashed"<br>}</pre> | <pre>{<br>   "success":"true",<br>   "access_token":"exampleUsername"<br>}</pre> | <pre>{<br>   "success":"false",<br>   "error":"anyError",<br>   "error_message":"messageForClientToShow"<br>} |
| POST   | /auth/register      | User register with an email. Activation Email is then sent with a password.            | <pre>{<br>   "email":"exampleUsername"<br>}</pre>                                                       | <pre>{<br>   "success":"true"<br>}</pre>                                         | <pre>{<br>   "success":"false",<br>   "error":"anyError",<br>   "error_message":"messageForClientToShow"<br>} |
| POST   | /auth/resetPassword | User reset the password. Email is then sent with a new password.                       | <pre>{<br>   "email":"exampleUsername"<br>}</pre>                                                       | <pre>{<br>   "success":"true"<br>}</pre>                                         | <pre>{<br>   "success":"false",<br>   "error":"anyError",<br>   "error_message":"messageForClientToShow"<br>} |

#### Project Management API

Project Management API is secured by an access_token. The access_token can be generated only by the Authentication API.
The access_token has to be set in a Request as a header.

Header name should be `Authorization` and a value starts with a `Bearer` appended by an access_token.

**Example**

```Authorization:Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c```

| Method | URL            | Description               | Request                                                                                                                                                                                                                                                                                                  | Response OK                                                                                                                                                                                                                                                                                                                                                                                                                                            | Response Error                                                                                                |
|--------|----------------|---------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------|
| POST   | /projects      | Create project            | <pre>{<br>   "name":"newProject",<br>   "type":"project type",<br>   "abbreviation":"abbreviation",<br>   "description":"projects description",<br>   "client":"clientsname", <br>   "owner":"ownersname",<br>   "comments":"comments, any length of text"<br>}</pre>                                    | <pre>{<br>   "id":4,<br>   "name":"newProject",<br>   "type":"project type",<br>   "abbreviation":"abbreviation",<br>   "description":"projects description",<br>   "client":"clientsname", <br>   "owner":"ownersname",<br>   "comments":"comments, any length of text", <br>   "status":"status of the project DONE \| IN PROGRESS",<br>   "creationDate":"2023-10-05T18:53:16.017976",<br>   "lastUpdated":"2023-10-05T18:54:06.4907768"<br>}</pre> | <pre>{<br>   "success":"false",<br>   "error":"anyError",<br>   "error_message":"messageForClientToShow"<br>} |
| PUT    | /projects/{id} | Update project            | <pre>{<br>  "id":4,<br>   name":"newProject",<br>   "type":"project type",<br>   "abbreviation":"abbreviation",<br>   "description":"projects description",<br>   "client":"clientsname", <br>   "owner":"ownersname",<br>   "status":"DONE",<br>   "comments":"comments, any length of text"<br>}</pre> | <pre>{<br>   "id":4,<br>   "name":"newProject",<br>   "type":"project type",<br>   "abbreviation":"abbreviation",<br>   "description":"projects description",<br>   "client":"clientsname", <br>   "owner":"ownersname",<br>   "comments":"comments, any length of text", <br>   "status":"status of the project DONE \| IN PROGRESS",<br>   "creationDate":"2023-10-05T18:53:16.017976",<br>   "lastUpdated":"2023-10-05T18:54:06.4907768"<br>}</pre> | <pre>{<br>   "success":"false",<br>   "error":"anyError",<br>   "error_message":"messageForClientToShow"<br>} |
| GET    | /projects      | Get all projects for user |                                                                                                                                                                                                                                                                                                          | <pre>[{representation of a project}]</pre>                                                                                                                                                                                                                                                                                                                                                                                                             | <pre>{<br>   "success":"false",<br>   "error":"anyError",<br>   "error_message":"messageForClientToShow"<br>} |
| GET    | /projects/{id} | Get a project by id       |                                                                                                                                                                                                                                                                                                          | <pre>{<br>   "id":4,<br>   "name":"newProject",<br>   "type":"project type",<br>   "abbreviation":"abbreviation",<br>   "description":"projects description",<br>   "client":"clientsname", <br>   "owner":"ownersname",<br>   "comments":"comments, any length of text", <br>   "status":"status of the project DONE \| IN PROGRESS",<br>   "creationDate":"2023-10-05T18:53:16.017976",<br>   "lastUpdated":"2023-10-05T18:54:06.4907768"<br>}</pre> | <pre>{<br>   "success":"false",<br>   "error":"anyError",<br>   "error_message":"messageForClientToShow"<br>} |
| DELETE | /projects/{id} | Delete a project by id    |                                                                                                                                                                                                                                                                                                          |                                                                                                                                                                                                                                                                                                                                                                                                                                                        | <pre>{<br>   "success":"false",<br>   "error":"anyError",<br>   "error_message":"messageForClientToShow"<br>} |

## License

This project is licensed under the [GNU General Public License v3.0]

## Contact

If you have questions or need assistance, please feel free to contact us at
malcolm.harris@topfieldconsultancy.co.uk![image](https://github.com/mtg928/STEM-Phase3/assets/41808296/b8b80ce3-7e74-49c9-9162-25466ccf1601)