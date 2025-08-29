# Bajaj Finserv Webhook Application

This Spring Boot application automatically generates a webhook, solves a SQL problem based on registration number, and submits the solution using JWT authentication.

## Features

- Automatically executes on application startup
- Generates webhook using POST request
- Determines SQL question based on registration number (odd/even)
- Submits SQL solution with JWT authentication
- Comprehensive logging for debugging

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/bajajfinserv/webhook/
│   │       ├── WebhookApplication.java      # Main application class
│   │       └── WebhookService.java          # Service handling webhook logic
│   └── resources/
│       └── application.yml                  # Application configuration
├── pom.xml                                  # Maven dependencies
└── README.md                               # This file
```

## Prerequisites

- Java 17 or higher
- Maven 3.6+ 
- Internet connection for API calls

## Setup Instructions

### 1. Clone the Repository
```bash
git clone <your-repository-url>
cd bajaj-webhook-app
```

### 2. Build the Application
```bash
mvn clean compile
```

### 3. Create JAR File
```bash
mvn clean package
```
This will create `target/webhook-app-1.0.0.jar`

### 4. Run the Application
```bash
java -jar target/webhook-app-1.0.0.jar
```

Or using Maven:
```bash
mvn spring-boot:run
```

## How It Works

1. **Application Startup**: The app uses `@EventListener(ApplicationReadyEvent.class)` to trigger the workflow automatically on startup.

2. **Webhook Generation**: 
   - Sends POST request to generate webhook endpoint
   - Uses hardcoded registration details: `REG12347`
   - Receives webhook URL and access token

3. **Question Selection**:
   - Based on last two digits of regNo (`47` - odd number)
   - Selects Question 1 from the Google Drive link
   - **Note**: You need to manually access the Google Drive link and implement the actual SQL solution

4. **Solution Submission**:
   - Submits the final SQL query using the received access token
   - Uses JWT authentication in Authorization header

## Important Notes

### SQL Problem Solution
The current implementation includes a **placeholder SQL query**. You must:

1. Access the Google Drive link for Question 1 (since regNo ends with odd digits)
2. Solve the actual SQL problem
3. Replace the placeholder query in `solveSQLProblem()` method

### Registration Details
Current hardcoded values:
- Name: "Naveen Chandu"
- RegNo: "22BCE20014" 
- Email: "naveenchandu200401@gmail.com"

**Replace these with your actual details** before submission.

## Configuration

The application can be configured via `application.yml`:

- Server port: 8080 (default)
- Logging levels for debugging
- Spring Boot web application settings

## API Endpoints Used

1. **Generate Webhook**: `POST https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA`
2. **Submit Solution**: `POST https://bfhldevapigw.healthrx.co.in/hiring/testWebhook/JAVA`

## Building for Submission

### Create Downloadable JAR
```bash
mvn clean package
# JAR will be created at: target/webhook-app-1.0.0.jar
```

### GitHub Repository Requirements
Ensure your repository includes:
- [ ] Complete source code
- [ ] Final JAR file in `/target` directory
- [ ] This README.md
- [ ] Raw downloadable GitHub link to JAR

### Example GitHub Structure
```
your-repo/
├── src/
├── target/
│   └── webhook-app-1.0.0.jar    # Built JAR file
├── pom.xml
└── README.md
```

## Submission Checklist

- [ ] Replace placeholder registration details with actual values
- [ ] Access Google Drive link and solve the actual SQL problem  
- [ ] Test the application end-to-end
- [ ] Create public GitHub repository
- [ ] Include downloadable JAR file
- [ ] Submit via: https://forms.office.com/r/5Kzb1h7fre

## Logging

The application provides detailed logging to help debug issues:
- INFO level for main flow events
- DEBUG level for HTTP client details
- Error logging with stack traces

## Dependencies

- Spring Boot 3.2.0
- Spring Web (for RestTemplate)
- Jackson (for JSON processing)
- Spring Boot Test (for testing)

## Troubleshooting

1. **Connection Issues**: Check internet connection and API endpoint availability
2. **Authentication Errors**: Ensure JWT token is correctly passed in Authorization header
3. **JSON Parsing Issues**: Verify request/response object structures match API expectations
4. **Build Issues**: Ensure Java 17+ and Maven 3.6+ are installed

## License

This project is created for the Bajaj Finserv Health hiring process.
