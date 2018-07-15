# Linker
Demo project - simple redirection and link shortener service

## How does it work?

This is a REST API, but it also has a user interface so you can test the API from the Front End. There is a running version (I will shut it down in few days) in the following link: **http://167.99.33.217:8080/**

Once the app is running, you can send requests to it to the following end points:

### /generateLink    -   POST request
The body should contain a JSON with the original Link: {"originalLink":"http://sanfranciscoboljottem.com"}
The reponse will contain the short version of the link.

### /findLink    -   POST request
The body should contain a JSON in the following format: {"shortLink":"asdksi31"} - The response will give you back the original link along the short link.

### /findLinks    -   POST request
The body should contain a JSON Array in the following format: [{"shortLink":"actest31"},{"shortLink":"arvtest1"}] -The response will give you back an array containing the original links along the short links.

## Where are the main files?
**Source Code of the logic:**

\src\main\java\com\linker

**Front End Logic:**

src\main\resources\static\assets\js\procedure.js

## How can it be improved ##
Since it was a task with a limited on it, I used frameworks which I could work quickly with. The project uses JPA over JDBC, and H2 embedded database over an external DB. If that would go live, I would have used Stored Procedures on PostgreSQL, and I would just call them from the server. There is no security on this service at the moment, but on a live system I would build a user authentication (so the links would not be stored in the Local Storage of the browser, but rather it would be attached to the user itself) using JWT tokens. On the Front End I would be using React over jQuery, but again: for a small task like that, that was much quicker to build.

