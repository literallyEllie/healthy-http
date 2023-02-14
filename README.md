# healthy-http
A Javalin server supported Avaje DI, with health checks ready to be used.

_very much a bit of a beta time in need of some refactoring_

## Server
### Health
#### GET /health
Response codes:
- 200 OK - If top level "state" is UP.
- 503 Service Unavailable - If top level "state" is DOWN.

**Example response**
</br>
```json
{
  "state": "DOWN",
  "components": {
    "datasource": {
      "state": "DOWN",
      "critical": true,
      "subComponents": {
        "mongodb": {
          "state": "DOWN",
          "details": {
            "error": "<a connection error>"
          }
        },
        "redis": {
          "state": "UP"
        }
      }
    },
    "funFactGenerator": {
      "state": "UP",
      "critical": true,
      "details": {
        "funDisneyFact": "Mickey hasn't forgotten what you did!",
        "waitWhat": "ho ho!",
        "funPixarFact": "Pizza Planet was originally Pizza Putt."
      }
    }
  }
}
```

#### GET /health/{componentId}
Response codes:
- 200 OK - If top level "state" is UP.
- 404 Not Found - if there is no such component Id.
- 503 Service Unavailable - If top level "state" is DOWN.

**Example response:**
</br>
```json
{
  "state": "DOWN",
  "critical": true,
  "subComponents": {
    "mongodb": {
      "state": "DOWN",
      "details": {
        "error": "<a connection error>"
      }
    },
    "redis": {
      "state": "UP"
    }
  }
}
```

## Client
soonTM