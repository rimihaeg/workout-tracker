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

As I have some experience building an API I started out with this as a basis.
The API had endpoints for endpoints for all these user actions, as well as a DELETE for a set.
But because HTML forms do not support the DELETE method I decided to not use this endpoint.

There was also a PUT for updating an exercise, but since HTML froms do not support PUT either I turned this into a POST.

With the API I tried to add the proper HTTP codes, but getting this to work with templating cost too much time, and as the user would not receive these codes anyway I decided to forgo this feature.

The API specification looks as follows:

// todo: add input and output
### API Specification
| Auth required | Method | Path |
| :------------ | ------ | ---: |
| **Users** |
| No | POST | /api/users |
| Yes | GET | /api/users |
| Yes | GET | /api/users/{username} |
| **Login** |
| No | POST | /api/login |
| **Exercise** |
| Yes | POST | ~~/api~~/exercises |
| No | GET | ~~/api~~/exercises |
| No | GET | ~~/api~~/exercises/{exerciseName} |
| Yes | ~~PUT~~ POST | ~~/api~~/exercises/{exerciseName} |
| **Set** |
| Yes | POST | /api/me |
| Yes | GET | /api/me |
| Yes | GET | /api/me/sets?limit={int} |
| ~~Yes~~ | ~~DELETE~~ | ~~/api/me/{setId}~~ |

During implementation of the templating there was an issue with the API calls. The endpoints were moved from `/...` to `/api/...`.
A new HTML controller was created to support all the calls to the website itself.

In the end this caused issues with the Exercises path, the HTML form was only able to call to the current page, `/exercises`, and its its subpages.
It was not able to redirect to `/api/exercises`.
To remove this issue the `/api/exercises` controller was moved back to `/exercises` and the reroute from the HTML controller was turned off.

The other pages are still being routed through the HTML controller.
If this was a production application the HTML controller would not be used like this, but because it is a learning point for me, I decided to keep it in.

The mail function of the HTML controllers is redirecting already logged in users to their own page, this functionality would be kept for production. 

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



## Issues
### Context-relative vs Server-relative urls
There was a problem loading css files due to a missing `/` in front of the css name.
This caused the website to try and load a context-relative url.
So when going to `/me` Thymeleaf would try to send `/me/me.css` instead of `/me.css`.

### Overflow-x
There is an element that uses animation to float away from the screen before disappearing.
This adds a little extra to the app, but without intervention this also adds extra unused space in the direction the element floats off screen, in this case the right.

To fix this adding of whitespace, and the sudden appearance of a horizontal scrolling bar, the `overflow-x` of the body had to be set to `hidden`.

As the name `overflow-x` suggests, it regulates overflow, a child taking up more space than the parent element, on the x-axis.

### Stack and subList()
The plan to present the user's newest sets first sounded like the perfect place to use a stack, it both operates on the FILO principal.
The Stack ahd to be turned into a list for proper thymeleaf handling.

The `Stack.subList()` function was able to do this, whilst also providing the option of filtering x amount of elements from the stack.
The problem with this approach was that the `subList()` function does not use the stack's intended order, LIFO, but uses a regular FIFO order.

A possible solution was to simply call `subList()` and pass along the elements at the end, but in this case the stack is made obsolete, a normal ArrayList would serve the same purpose.

The solution picked was turning the Stack into a LinkedList. The LinkedList also supports the push function, putting the new element at the start of the list.
The first element now is the newest element, so calling `subList()` now returns a list with the intended order.

### background colour and linux
Some linux distros using themes have the tendency to fill in colours that were not explicitly stated in the css.
Often this leads to form input boxes to take on the colour of the theme.
A dark theme causes black characters on a black background, not very user friendly.
Some websites, like Quora, do not have a stated background colour.

Since the background colour of the website is not white, the issue is therefor limited to input boxes.
To remove this issue the input boxes have therefor also been given a background colour.