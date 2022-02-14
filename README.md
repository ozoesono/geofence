# Getting Started

### Reference Documentation
This is a basic reference guide on how to run the Gofence Application.

The Gofence application is an application written in Java 8 using the Springboot framework. It has been implemented, 
mostly following the guided instructions provided. 

### Things you will need to have installed
1. Java 8 / An active JVM
2. Docker
3. Docker-compose (It should come with Docker if you are on a newer version, if not you will need to install this separately)
4. Maven (optional - if you don't want to use the maven wrapper)

### Running the Application
1. Clone the application to your local directory
2. In the root of the project issue the following command:
* ```./mvnw clean package```
3. Once the build is complete run:
* ```docker build --tag=geofences:latest .```
4. Once the image has been built run:
* ```docker-compose up```
5. The application should be up and running now you can go ahead and perform the following actions:
   1. Create a new Geofence:
   * Make a POST request to: http://localhost:8080/api/v1/geofence, with the data below
   * ```
     { 
       "lat": 20.001,
       "lng": -23.32,
       "radius": 2.0
     }
   
    2. Create an Advert in a Geofence:
   * Make a POSt request to: http://localhost:8080/api/v1/geofence/adverts, with the data below
   * ```aidl
     {
       "lat": 20.001,
       "lng": -23.32,
       "href": "https://yahoo.com"
     }
   
    3. Get the Entrance given a Geofence:
   * Make a POST request to: http://localhost:8080/api/v1/geofence/entrance, with data
   * ```aidl
      {
        "lat": 20.001,
        "lng": -23.32
      }

### Assumptions made in this implementation
A number of assumptions have been made on this exercise:
1. An Advert can be saved multiple times, as each advert has its only unique key as the primary key which is an auto-incremented number
2. The modelling of the Advert was such that it is under the Geofence. It has been architected in such a way the persisting of the Advert happens as an embedded entity to the Geofence

### Improvement Areas
1. I could have brought in testcontainers since we are using docker, and had full integration tests. Due to time limitations I left this out, but this is typically what I would have done in a normal implementation.
2. I could have made the Exception handling more extensive, but for the sake of keeping it simple, most errors are returned as bad data - 400
3. I could have brought in swagger documentation, but again was trying to keep the solution not overly bloated 
