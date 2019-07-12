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

For testing purposes, the accounts user (password: user) and admin (password: admin) are available.

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

As I have some experience building an API I started out with this as a basis.
The API had endpoints for endpoints for all these user actions, as well as a DELETE for a set.
But because HTML forms do not support the DELETE method this endpoint was scrapped.

There was also a PUT for updating an exercise, but since HTML froms do not support PUT either this was turned into a POST.

With the API I tried to add the proper HTTP codes, but getting this to work with templating cost too much time, and as the user would not receive these codes anyway this feature was scrapped.

The API specification looks as follows:

### API Specification
| Auth required | Method | Path |
| :------------ | ------ | ---: |
| **Users** |
| No | POST | ~~/api~~/users |
| Yes | GET | ~~/api~~/users |
| Yes | GET | ~~/api~~/users/{username} |
| **Login** |
| No | POST | ~~/api~~/login |
| **Exercises** |
| Yes | POST | ~~/api~~/exercises |
| No | GET | ~~/api~~/exercises |
| No | GET | ~~/api~~/exercises/{exerciseName} |
| Yes | ~~PUT~~ POST | ~~/api~~/exercises/{exerciseName} |
| **Sets** |
| Yes | POST | ~~/api~~/me |
| Yes | GET | ~~/api~~/me |
| Yes | GET | ~~/api~~/me/sets?limit={int} |
| ~~Yes~~ | ~~DELETE~~ | ~~/api/me/{setId}~~ |

[A more detailed API specification](#full-api-specs) can be found at the end of this document.

During implementation of the templating there was an issue with the API calls. The endpoints were moved from `/...` to `/api/...`.
A new HTML controller was created to support all the calls to the website itself.

In the end this caused issues with some of the paths not being able to retrieve the right CSS files.
This seemed to fix the issue for the `/exercises` path, but the problem was found later on other paths as well.
It turned out to be a problem with the CSS files in the HTML. The references were written context-relative, `exercises.css`.
Turning the references into server-relative urls, `/exercises.css`, solved the issue.

The HTML controller now mainly functions to redirect the user from the homepage, either to `/me` if the user is logged in, or `/login` if the user is not logged in.

## Server description
![Class diagram](https://github.com/rimihaeg/workout-tracker/blob/master/DataController.png)

The server is initiated through the WorkoutTrackerApplication.java class. This class starts the Spring application.
The Spring framework automagically finds the controllers through the use of the `@Controller` attribute.
These controllers are HtmlServerController, LoginController, UsersController, UserController and ExerciseController.
Normally an application like this connects to a database, but since this is out of the scope of this assignment there is an extra DataController which is in control of all the data saved in the application.

### Data
The DataController is available through a singleton construction.
The users and exercises are saved in a HashMap. This makes it easy to find the required object, if it exists.

If the DataController receives a request for, for example, an object that does not exist (yet), the DataController will throw an exception.
It is upto the caller to properly handle this exception.

The User object is in control of the sets from the user.
This collection was supposed to be saved as a Stack, but it became a LinkedList in the end. More on this in the [Issues chapter](#issues).

### Models
Because the Controllers have to process request inputs, they need to verify the input to some class.
The requestModels package contains these classes.
These classes contain a function to be turned into the class it is associated with.

A users password should not be accessible to the outside world.
Because of this the User class has a verifyPassword function which returns `true` if the password is correct and `false` if incorrect.
This way there does not have to be a getter for the password, allowing it to stay private.

## Website URL's
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

## HTML, CSS and Thymeleaf
The CSS consists of one main file, styles.css, and an extra file for each specific page.
This way CSS attributes used on every page do not have to be duplicated in separate style sheets whilst reducing the need for one big sheet for the whole application.

The styles.css file also contains a selector that resets some basic styles a browser might add, like white borders around the edges.

To navigate the website, the HTML makes use of a nav tag.
This element contains links to the homepage (login if the user is not logged in or the users personal page if the user is logged in),
the exercises page, and, if the user is logged in, the users page.

The form text inputs make use of placeholder texts to inform the user of the type of input expected to be used. 

If a user signs up for the application there will be an extra element added to the page.
If the user succeeds in creating an account the user will be told that the account has been created.
If the account name already exists the user will be told this as well.

If the user has added an exercise to the application and visits the exercises page, the user will be shown what exercise he/she added last.
This element will stay for a few seconds before floating off screen and disappearing. More on this in the [Issues chapter](#issues).

If a user that has not been signed in visits the exercises page, the form for adding a new exercise will not be shown.

## Issues
Here are some issues I ran into while creating the application.

### Overflow-x
There is an element that uses animation to float away from the screen before disappearing.
TWithout intervention this adds extra unused space in the direction that the element floats off screen, in this case the right.

To fix this sudden increase in whitespace, and the appearance of a horizontal scrolling bar, the `overflow-x` of the body had to be set to `hidden`.

As the name `overflow-x` suggests, it regulates overflow, a child taking up more space than the parent element, on the x-axis.

### Stack and subList()
The plan to present the user's newest sets first sounded like the perfect place to use a stack, it both operates on the FILO principal.
The Stack had to be turned into a list for proper thymeleaf handling.

The `Stack.subList()` function was able to do this, whilst also providing the option of filtering x amount of elements from the stack.
The problem with this approach was that the `subList()` function does not use the stack's intended order, LIFO, but uses a regular FIFO order.

A possible solution was to simply call `subList()` and pass along the elements at the end, but in this case the stack is made obsolete, a normal ArrayList would serve the same purpose.

The solution picked was turning the Stack into a LinkedList. The LinkedList also supports the push function, putting the new element at the start of the list.
The first element now is the newest element, so calling `subList()` now returns a list with the intended order.

### background colour and linux
Some linux distributions that can use themes have the tendency to fill in colours that were not explicitly stated in the CSS.
Often this leads to form input boxes to take on the colour of the theme.
A dark theme causes black characters on a black background, not very user friendly.
Some websites, like Quora, do not have a stated background colour.

Since the background colour of the website is not white, the issue is therefor limited to input boxes.
To remove this issue the input boxes have therefor also been given a background colour.

## Full API specs
\* = optional

### Users
| Path | Auth required |
| :----- | ------- |
| POST /users | NO |
| **Input** | username: String |
| | password: String |
| **Returns page** | / |

| Path | Auth required |
| :----- | ------- |
| GET /users | YES |
| **Returns page** | /users |
| | 401 |

| Path | Auth required |
| :----- | ------- |
| GET /users/{username} | YES |
| **Returns page** | /users |
| | 401 |
| | 404 |

### Login
| Path | Auth required |
| :----- | ------- |
| POST /login | NO |
| **Input** | username: String |
| | password: String |
| **Returns page** | / |

### Exercises
| Path | Auth required |
| :----- | ------- |
| POST /exercises | YES |
| **Input** | name: String |
| | description*: String |
| **Returns page** | /exercises |

| Path | Auth required |
| :----- | ------- |
| POST /exercises/{exerciseName} | YES |
| **Input** | description*: String |
| | targets*: String[] |
| **Returns page** | /exercises/{exerciseName} |

| Path | Auth required |
| :----- | ------- |
| GET /exercises | NO |
| **Returns page** | /exercises |

| Path | Auth required |
| :----- | ------- |
| GET /exercises/{exerciseName} | NO |
| **Returns page** | /exercises/{exerciseName} |
| | 404 |

### Sets
| Path | Auth required |
| :----- | ------- |
| POST /me | YES |
| **Input** | exerciseName: String |
| | reps: int |
| **Returns page** | /me |
| | 401 |

| Path | Auth required |
| :----- | ------- |
| GET /me | YES |
| **Returns page** | /me |
| | 401 |

| Path | Auth required |
| :----- | ------- |
| GET /me/sets | YES |
| **Request parameter** | limit*: int |
| **Returns page** | /me/sets |
| | 401 |

This documentation was written in Markdown and converted to PDF.