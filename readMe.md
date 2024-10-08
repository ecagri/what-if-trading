# What If Trading Application

## Overview
The "What If Trading" application allows users to simulate trading in the Borsa Istanbul stock market. This platform enables users to assess their trading performance and determine whether their strategies would be viable in the real world, all without the risk of losing money.

## Technical Stack

### Backend
- **Framework**: Spring Boot
- **Language**: Java
- **APIs**: RESTful APIs created to handle trading operations and user interactions.
- **Stock Prices**: Integrated with the [Financial Modeling Prep API](https://site.financialmodelingprep.com/) to fetch end-of-day stock prices (note: the free API does not provide real-time prices).
- **Scheduled Operations**: The application retrieves stock prices and performs operations every weekday at 18:30.
- **API Documentation**: Swagger is utilized for clear and user-friendly documentation of the APIs.
![img_1.png](swagger1.png)
![img_2.png](swagger2.png)
![img_3.png](swagger3.png)

### Frontend
- **Framework**: React.js
- **Development Stage**: The frontend will be developed after the completion of the backend.

## Testing
- **Unit Testing**: All services have been thoroughly unit tested using JUnit to verify the correct behavior of backend components.
- **Test Framework**: JUnit 5 and Mockito were used for writing and executing the unit tests.
![img.png](unitTest.png)

## Security
- **Authentication**: JWT (JSON Web Token) is used for authentication. Upon login, a token is generated and sent to the client, which is then included in the Authorization header for all API requests.
- **Authorization**: Most API endpoints require a valid JWT. Only authentication and stock price APIs are publicly accessible.
- **Token Expiry**: JWTs have an expiration time, after which reauthentication is needed to maintain security.
- **Data Encryption**: Passwords and sensitive data are securely encrypted and stored in the database.
- **Secrets**: Sensitive information such as database passwords, API keys, and JWT secret keys are stored securely in a config.properties file, which is included in .gitignore to prevent it from being exposed in version control.
![img.png](authentication.png)

## Features
- **Account Management**: Customers can open account within the app.
- **Multiple Portfolios**: Users can create multiple portfolios, giving each a unique name and applying different strategies.
- **Transaction Tracking**: Portfolio transactions and assets are saved for user reference.
- **Buy/Sell Functionality**: Customers can buy or sell stocks for their selected portfolios.
- **Simulated Trading**: Users can practice trading strategies without financial risk.
- **Performance Tracking**: Users can monitor their trading performance to make informed decisions.

## Future Enhancements
- ~~Unit testing for services~~ 
- ~~User authentication and authorization (using Spring Security)~~ 
- Logger
- Exception Handling Mechanism
- Additional analytics and reporting features