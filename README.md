# microservices

This is a simple, small project with no tests, that is made of 4 branches, where:

  -client is only making rest calls to the rest controller,
  
  -rest controller is handling calls from client, sending and receiving data from movie database via Spring and JPA Database, also it is sending calls to rating service           using Eureka discovery server,
  
  -rating service is handling calls from rest controller, also receiving and sendeing data to separate from movies, rating database.
  
  Both microservices are using Spring WEB, Spring Rest Repositories and Spring Data JPA.
  
  Spring version 2.3.1, Java version 11.
