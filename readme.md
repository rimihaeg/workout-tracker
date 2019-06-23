# Exercise tracker

## API
### API calls
| Auth required | Method | Path |
| :------------ | ------ | ---: |
| **Users** |
| No | POST | /api/users |
| Yes | GET | /api/users |
| Yes | GET | /api/users/{username} |
| **Login** |
| No | POST | /api/login |
| **Exercise** |
| Yes | POST | /api/exercises |
| No | GET | /api/exercises |
| No | GET | /api/exercises/{exerciseName} |
| Yes | PUT | /api/exercises/{exerciseName} |
| **Set** |
| Yes | POST | /api/me |
| Yes | GET | /api/me |
| Yes | GET | /api/me/sets?limit={int} |


## Website
### Website URL's
| Path |
| ---- |
| **Users** |
| */users* |
| */users/{username}* |
| **Login** |
| */* |
| **Exercise** |
| */exercises* |
| */exercises/{exerciseName}* |
| **Set** |
| */me* |
| */me/sets?limit={int}* |

