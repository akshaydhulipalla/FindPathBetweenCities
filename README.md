# FindPathBetweenCities

This is a standard Spring Boot project created from Spring Boot Initializr with Java 1.8, Maven, and default parent, Rest and it's dependences.

Tools used: IntelliJ, GIT

Here's the approach used:

Because we need to find path between two cities, it can be traversed with Breadth-first and Depth-first search algorithms.

I have chosen to used BFS because it is faster than DFS. It can be made more faster with Bi-directional search algorithm.

To do a BFS, the input is processed into adjacency list which is created a hashmap with (city, list of neighbors). HashMap is used for faster lookup of cities.

The project files are structured in standard MVC format, and code implementation OOPS principles. 

Maven commands can be run against the project for build, unit tests etc.

All important unit test cases are implemented and executed successfully. 

Swagger UI is also included in the project. It can be accessed using path: /rest-api-documentation.html

Default port used: 8080
Example URL: http://localhost:8080/connected?origin=Boston&destination=Providence

 
