
# Datawise Test Api

Create a product management system integrating user authentication and authorization.
The finalized product should include REST endpoints for user interaction with the
system.




## Tech Stack

**Client:** Postman

**Server:** Spring Boot, PostGresql, Docker


## Installation

Create a container of Postgresql on docker 

```bash
  docker pull postgres
  docker run --name datawise -e POSTGRES_PASSWORD=12345 -d postgres -p 5432:5432
```
After that run the project.   
## API Reference

#### Register

```http
  POST /api/v1/auth/register
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `username` | `string` | username |
| `email` | `string` | email |
| `password` | `string` | password |
| `authorities` | `list` | list of ADMIN or CLIENT |

#### Authenticate

```http
  POST api/v1/auth/authenticate
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `username`      | `string` | username |
| `password`      | `string` | password |

You get a token in case of succeful login to copy for your requests
#### Create Products
```http
  POST api/v1/products/create
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `name`      | `string` | name of the product |
| `price`      | `float` | price of the product |

#### Get All Products
```http
  GET api/v1/products/all
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |

#### Update a Product
```http
  PUT api/v1/products/update/{id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `int` | id of the product |
| `name`      | `string` | new name of the product |
| `price`      | `float` | new price of the product |

#### Delete a Product
```http
  DELETE /api/v1/products/{id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `int` | id of the product |

#### Fetch a Product with its Name
```http
  GET /api/v1/products/product
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `name`      | `string` | name of the product |


## Security

- Client view/add products Admin update/delete.
- Jwt Token


