# Introduction

This program is for the Fetch Backend Internship Challenge.
This is a Springboot backend project

## Project tree structure
```fetch-backend-oa
├── .mvn  #maven build tool file
├── src  #source file of the project
│   ├── main
│   │   └── me
│   │       └── jackson
│   │           └── fetchbackendoa
│   │               ├── controllers  #controller layer of this project
│   │               │   └── UserController.java #The controller to manage all user apis
│   │               ├── models  #the data object
│   │               │   ├── User.java  #the mock user object
│   │               │   └── Transaction.java  #transaction object
│   │               ├── requests  #request and response body class
│   │               │   ├── AddPointsRequest.java
│   │               │   ├── PostSpendPointsRequest.java
│   │               │   └── SpendPointsResponse.java
│   │               ├── services  #service layer
│   │               │   ├── impl  #implementation of service layer
│   │               │   │   └── UserServiceImpl.java
│   │               │   └── UserService.java  #user serivce layer
│   │               └── FetchBackendOaApplication.java  #the entry class in this project
│   └── test  #test part of program
├── mvnw  #maven wrapper unix shell script
├── mvnw.cmd  #maven wrapper windows batch script
├── pom.xml  #project and dependencies manage file
├── README.MD  #introduction of this project
└── summary.txt  #the part 2 of fetch```