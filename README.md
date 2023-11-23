# Trading Project

## Project Overview

The trading project implements a trading system that allows registered users to manage their watchlists, access symbol information, add buy or sell orders, cancel orders, execute orders, pause orders, resume orders, retrieve their portfolio, and view trade history. The system consists of various modules and APIs that provide functionalities for user registration, login, watchlist management, symbol details retrieval, order management, and more.
## API Endpoints

### User Registration

- **POST /trading/register/user-registration**: Register a new user.
    - Input Object:
      ```json
      {
        "userInfo": {
          "userName": "exampleuser",
          "email": "user@example.com",
          "password": "P@ssw0rd"
        }
      }
      ```

### User Session

- **POST /session/user-login**: Log in a user and generate a session token.
    - Input Object:
      ```json
      {
        "authRequest": {
          "username": "exampleuser",
          "password": "P@ssw0rd"
        }
      }
      ```

- **POST /session/user-logout**: Log out a user and invalidate the session.
    - Input Object:
      ```json
      {
        "authRequest": {
          "username": "exampleuser",
          "password": "P@ssw0rd"
        }
      }
      ```

### Dashboard

- **POST /dashboard/watchlist/add-groups**: Add a watchlist group to the user's watchlist.
    - Input Object:
      ```json
      {
        "groupName": "My Watchlist"
      }
      ```

- **POST /dashboard/watchlist/add-symbols**: Add symbols to a user's watchlist group.
    - Input Object:
      ```json
      {
        "symbol": {
          "symbol": "STK_ADANIPORTS_EQ_NSE",
          "groupId": 1
        }
      }
      ```

- **POST /dashboard/add-order**: Add a buy or sell order to the system for the specified stock symbol.
    - Input Object:
      ```json
      {
        "order": {
          "stockSymbol": "STK_ADANIPORTS_EQ_NSE",
          "orderType": "BUY",
          "quantity": 10,
          "price": 150.0
        }
      }
      ```

- **POST /dashboard/cancel-order**: Cancel a buy or sell order for the specified stock symbol.
    - Input Object:
      ```json
      {
        "orderId": "1"
      }
      ```

- **GET /dashboard/get-portfolio**: Get the current portfolio, including stock symbols, quantities, and order statuses.
    - No input object required.

- **GET /dashboard/get-trade-history**: Get trade history, including executed transactions.
    - No input object required.

- **GET /dashboard/watchlist/get-groups**: Get all watchlist groups for the current user.
    - No input object required.

- **GET /dashboard/watchlist/get-symbols**: Get all symbols from a user's watchlist group.
    - Input Object:
      ```json
      {
        "groupId": 1
      }
      ```
- **POST /dashboard/pause-order:** Pause an existing order in the system.
     - Input Object:
        ```json
        {
         "orderId": 1
        }
        ```
- **POST /dashboard/resume-order:** Resume a paused order in the system.
     - Input Object:
        ```json
        {
         "orderId": 1
        }
        ```

### Quote

- **GET /quote/get-symbol**: Get details about a specific symbol.
    - Input Object:
      ```json
      {
        "symbolName": "STK_ADANIPORTS_EQ_NSE"
      }
      ```

- **GET /quote/get-symbol/all**: Get details about all symbols.
    - No input object required.

## Database Entities

The system manages the following database entities:

- `UserInfo`: Stores user registration details, including username, email, and password.
- `UserSession`: Stores user session information and tokens.
- `WatchlistGroup`: Represents a group of watchlists associated with a user.
- `Watchlist`: Represents a user's watchlist with symbols.
- `Order`: Stores buy or sell order details, including stock symbol, order type, quantity, price, and status.
- `Trade`: Stores trade history details, including order type, stock symbol, quantity, price, and timestamp.

## Modules and Services

The project consists of the following modules and services:

- `UserRegisterController`: Handles user registration.
- `UserSessionController`: Manages user sessions, logins, and logouts.
- `DashboardController`: Manages watchlist groups, symbols, orders, pause orders, resume orders, portfolios, and trade history.
- `QuoteController`: Retrieves details about symbols.
- `RegisterService`: Implements user registration logic.
- `UserSessionService`: Implements user session management.
- `DashboardService`: Implements watchlist and symbol management, as well as portfolio, trade history retrieval,order-related functionalities, including adding, canceling, pausing, resuming, and executing orders.
- `QuoteService`: Provides symbol-related functionalities.

## Usage

To use the trading system:

1. Clone the repository to your local machine.
2. Configure your database settings in the application properties.
3. Build and run the application.
4. Use Postman or a similar tool to test the provided API endpoints.

Feel free to explore the various features of the trading system and test its functionalities.
