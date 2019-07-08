# Network Services - Exercise tracker
By Rick Harmelink,
327841

## The Assignment
To get familiar with the world of internet, the subject Network Services aims to teach the basis on which the internet is formed:
* HTML;
* CSS;
* Network calls.

The subject approaches these topics through the Spring, a Java backend framework, and Thymeleaf, an HTML templating framework.

The assignment consists of a webservice where the user should be able to manage things.
What these things are is up to the assignee to fill in.

### Exercise tracker
I have opted to build an Exercise tracker. I like working out, and being able to track your progress can be really helpful.

The exercise tracker is aimed at repetition based exercises.
To use the webapp properly the user should be familiar with the following terms:
* Repetition (or reps): The amount of repetitions an exercise has been done (e.g. in 5 push-ups, the amount of reps is 5);
* Sets: The exercise combined with the repetitions (e.g. 3 sets of 5 push-ups).

Although I like running as well, tracking a run is not in the scope of this application, as running is generally time based, and not repetition based.

## The Application
To properly accommodate the user, the application should be able to do the following things:
* Have a new user create an account;
* Have a user log in;
* Have a user save a new set containing an exercise and the amount of repetitions;
* Have a user add a new exercise to the application;
* Have a user update an already existing exercise;
* Have a user look at their own exercises;
* Have a user see a list of other users;
* Have a user take a look at the exercises of another user.

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

