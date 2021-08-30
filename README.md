# Smart Hardware Shop

This is a small e-commerce application sample for evaluation purposes.

## How to run the application

You will need [Docker](https://www.docker.com/) and [docker-compose](https://docs.docker.com/compose/).
If you do not have them already installed, please follow
[these](https://docs.docker.com/engine/install/) and [these](https://docs.docker.com/compose/install/)
setup instructions.

### 1. Clone this repository in your local machine

```sh
git clone REPO_URL
```

### 2. Build and start the development containers

The following command will download the required images, prepare, and start the development containers.
The command will run in background.

```sh
docker-compose up -d
```

From now on, we will execute commands in the application container:

```sh
docker exec -it smart-hardware-shop-app echo "running in container"
```

If you are running a unix compatible environment, you can use the `ric.sh` script:

```sh
sh ric.sh echo "running in container"
```

or:

```sh
chmod +x ric.sh
./ric.sh echo "running in container"
```

We will use the `ric.sh` script in the following examples.

### 3. Test the application (optional)

```sh
sh ric.sh mvn clean test
```

### 4. Install the application

```sh
sh ric.sh mvn clean install
```

### 4. Start the application

```sh
sh ric.sh mvn clean install
```

### 5. Start the application

```sh
sh ric.sh mvn spring-boot:run
```

## Accessing the application

The application API is available at [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html).

Follow the instructions below to create valid users:

1. Find the endpoint `/users/signup`
2. Press "Try it out"
3. Use the following example in the request body and press "Execute". The password **must** be at least 8 characters
   long:

```json
{
  "username": "admin",
  "enabled": true,
  "role": "ADMIN",
  "password": "p@$$w0rd"
}
```

4. Repeat (3) for the following request body:

```json
{
  "username": "customer",
  "enabled": true,
  "role": "CUSTOMER",
  "password": "p@$$w0rd"
}
```
You now have a valid admin and a valid customer in your database.
Follow the steps below to login:

1. Find the endpoint `/users/login`
2. Press "Try it out"
3. Insert the credentials of the user you want to login in the request body as in the example below:

```json
{
  "username": "customer",
  "password": "p@$$w0rd"
}
```

4. Press "Execute". You will see a response below in the format `username TOKEN`.
5. Copy the token (do **not** copy the username or the space)
6. Press the button "Authorize" on top of the Swagger interface 
7. Paste the copied token in the value field and press "Authorize"

You are now logged in. Your token is valid for 10 minutes.
You can interact with the endpoints as follows:

1. Admin users do not have access to orders
2. Only admins can add, modify or delete news and products
3. Only admins have access to operations with users
