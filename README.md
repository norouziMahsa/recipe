# Recipe App

An application to manage recipes

# Description

Using the recipe application, you can create, modify and search recipes

## Requirements

1. Java - 21.x.x

2. Maven - 3.8.x

3. Npm - 10.x.x

4. Node - v20.x.x

5. Angular CLI - 14.2.x

## Steps to Setup Backend

**1. Clone the application**

```bash
git clone https://github.com/norouziMahsa/recipe.git
```

**2. Then switch to backend folder**

``` cd recipe-backend ```

**3. Build and run the app using maven**

you can run the app using maven as follows

```bash
mvn spring-boot:run
```

The app will start running at <http://localhost:8080>

## Rest API Documentation

You can find the APIs documentation for this application on

<http://localhost:8080/swagger-ui.html/>

## Steps to Setup Frontend

**1. Clone the application**

```bash
git clone https://github.com/norouziMahsa/recipe.git
```

**2. Then switch to frontend folder**

``` cd recipe-frontend ```

**3. Build and run the app using ng**

you can run the app using ng as follows

```bash
ng serve
```
The app will start running at <http://localhost:4200>


## Explore Rest APIs

The app defines following APIs.

    GET /recipe/all (get a list of recipes)
    
    GET /recipe/{id} (find a recipe by id)

    POST /recipe (create a new recipe)
    
    PUT /recipe/{id} (update a recipe)

    GET /recipe/search (search recipes by name or partial name)
