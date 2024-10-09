# RoleFit Project

RoleFit is a web application designed to help users match their skills with suitable job roles, take assessments based on those roles, and receive insights for improving their eligibility for those roles.

---

## Features
- **Skill Matching Algorithm**: Matches user skills to relevant job profiles.
- **Assessment Engine**: Generates assessments based on job roles and user skills.
- **Scoring System**: Calculates eligibility percentage based on assessment results.
- **Insights Generator**: Provides recommendations for skill improvement based on assessment performance.
- **Job Application Suggestion**: Suggests platforms where users can apply for jobs based on their skills and assessment scores.

---

## API Endpoints

### Job Role Controller

#### **GET** `/api/jobroles/{id}`
- **Description**: Retrieve a job role by its ID.
- **Response**: Returns the details of the job role.

#### **PUT** `/api/jobroles/{id}`
- **Description**: Update a job role by its ID.
- **Request Body**: JSON object containing the updated job role details.
- **Response**: Returns the updated job role.

#### **DELETE** `/api/jobroles/{id}`
- **Description**: Delete a job role by its ID.
- **Response**: Returns a success message upon deletion.

#### **POST** `/api/jobroles/recommend`
- **Description**: Recommend job roles based on user skills.
- **Request Body**: JSON object containing user skills.
- **Response**: Returns a list of recommended job roles.

#### **POST** `/api/jobroles/create`
- **Description**: Create a new job role.
- **Request Body**: JSON object containing the new job role details.
- **Response**: Returns the created job role.

#### **GET** `/api/jobroles/all`
- **Description**: Retrieve all job roles.
- **Response**: Returns a list of all job roles.

---

### User Controller

#### **POST** `/api/users/create`
- **Description**: Create a new user.
- **Request Body**: JSON object containing user information.
- **Response**: Returns the created user profile.

#### **GET** `/api/users/{id}`
- **Description**: Retrieve a user by their ID.
- **Response**: Returns the user profile.

#### **GET** `/api/users/all`
- **Description**: Retrieve all users.
- **Response**: Returns a list of all users.

---

### Skill Controller

#### **POST** `/api/skills/create`
- **Description**: Add new skills.
- **Request Body**: JSON object containing skill details.
- **Response**: Returns the created skill.

#### **GET** `/api/skills/all`
- **Description**: Retrieve all skills.
- **Response**: Returns a list of available skills.

---

### Assessment Controller

#### **POST** `/api/assessments/submit`
- **Description**: Submit assessment answers.
- **Request Body**: JSON object containing the user's answers.
- **Response**: Returns the assessment score and insights.

#### **POST** `/api/assessments/create`
- **Description**: Create an assessment.
- **Request Body**: JSON object containing assessment details.
- **Response**: Returns the created assessment.

#### **GET** `/api/assessments/result`
- **Description**: Retrieve the result of an assessment.
- **Response**: Returns the user's score and insights.

---

## How to Run the Project

### Backend (Spring Boot)

1. Clone the repository:
   ```bash
   git clone https://github.com/amansoni7389/Role_Fit.git
