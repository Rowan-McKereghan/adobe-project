# Adobe GenStudio Project: Integer to Roman Numeral Server

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#devops-capabilities">DevOps Capabilities</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started-and-building-the-project">Getting Started</a>
      <ul>
        <li><a href="#building-and-running-locally">Running Locally</a></li>
      </ul>
    </li>
    <li>
      <a href="#packaging-layout">Packaging Layout</a>
      <ul>
        <li><a href="#code-organization">Code Organization</a></li>
      </ul>
    </li>
    <li>
      <a href="#engineering-and-testing-methodologies">Engineering and Testing Methodologies</a>
      <ul>
        <li><a href="#java-http-server">HTTP Server</a></li>
        <li><a href="#react-frontend-app">React App</a></li>
        <li><a href="#possible-extensions">Possible Extensions</a></li>
      </ul>
    </li>
    <li><a href="#built-with">Dependency Attribution</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

This is a basic HTTP server written in Java with a React frontend interface that converts integers to Roman numerals when queried. 

Once running, users can request data from it with commands such as:
```sh
curl "http://localhost:8080/romannumeral?query={integer}"
```
where `{integer}` is some number in the range `[1, 3999]`. Roman numerals with values of 4000 and above have different notations than just letters. See specification for [Roman numerals here](https://en.wikipedia.org/wiki/Roman_numerals) and their [conversion to Arabic numerals here.](https://www.britannica.com/topic/Roman-numeral)

If the request URL is formatted correctly, the server will respond with a JSON object with two values, `"input"` and `"output"`. For example, executing ```curl "http://localhost:8080/romannumeral?query=3"``` would have the server respond with this JSON object:
```json
{
    "output": "III",
    "input": "3"
}
```

You can also access this functionality from the React frontend app, hosted at [`http://localhost:3000`]().

![light-mode]

Entering an integer between (and including) 1 and 3999 in the text field and clicking the button will send a correctly formatted URL to the HTTP server, and update the page accordingly with the returned data. In the top right corner, users can click the switch to swap between light and dark color themes as they see fit. 

![dark-mode]

### DevOps Capabilities

The server also has basic DevOps capabilities in order to monitor server health, log and debug potential issues, and keep track of basic metrics. Inside the Docker container, users will be able to find a file titled `application-{yyyymmdd}.log`, where `{yyyymmdd}` is today. This log file will have records of what the server is doing on a moment-to-moment basis – writing HTTP requests, serving 400, fatal IO errors, etc. You can access this file by entering the compose stack and executing commands into the `int-to-roman-backend` Docker image directly through Docker Desktop, or by using the command:
```sh
docker exec -it int-to-roman-backend sh -c "{your command here}"
```
where the shell command could be something like `cat application-{yyyymmddd}.log`. 

For metrics and monitoring, the server is using Prometheus to periodically scrape metrics data and Grafana to monitor and visualize that data. If users wish, they can query Prometheus directly at [`http://localhost:9090`](). 

Users should visit Grafana at [`http://localhost:3001/dashboards`]() to see the default dashboards the have been set up for the server – a statistic panel and a graph and table panel combination. These two dashboards periodically monitor server health (i.e., cannot visualize data if the server is down) and keep track of the total HTTP requests sent to the server. This number resets if the Docker containers are stopped. 

See example of default dashboard here when server was stopped, then down, then restarted:

![dashboards]

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Getting Started and Building the Project

<b>Once the repo is cloned and Docker is running, open the main directory and simply run: </b>
```sh
docker compose up --build
```
in order to start the server. You can interrupt the process on the command line or hit stop in Docker Desktop to stop the composed
containers from running. 

<b>Users who are using `arm64` architecture or have a newer version of Docker can build the images from the published packages instead of the source code by running: </b>
```sh
docker compose up
```
This is recommended if you are running your Docker images on `arm64` architecture, since it eliminates some build time. If you have a new enough version of Docker, you can use this command and emulate `arm64` containers on `amd64` architecture, trading build time for a minor performance hit.

To continue executing commands from your CLI instead of viewing Docker logs, add the `-d` tag to your command.

If you wish to run the server locally without using and/or installing Docker, see below.


<b>To remove the Docker containers, run: </b>
```sh
docker compose down
```

### Building and Running Locally

You can alternatively build and run the server locally, which will require you to have Node, `npm`, and a JDK runtime installed. <b>NOTE:</b> Certain monitoring and metrics collection functionalities will not be available if you choose to build and run the server locally, because they rely on Docker to containerize them and serve them on localhost endpoints.

1. First, build your `target` directory and `.jar` file using the Maven wrapper executable. Enter the `http-server/int-to-roman` directory, then:
     - On Linux or MacOS, run `./mvnw clean package`
     - On Windows, run `mvnw.cmd clean package`
2. Once you have a `target` directory, enter it and locate the `int-to-roman-1.0-SNAPSHOT.jar` file, then run `java -jar int-to-roman-1.0-SNAPSHOT.jar`
3. The server should now be running. If you wish to run the React app locally as well, you can execute the following commands inside `frontend-app/react-frontend`:
   1. `npm install`
   2. `npm install -g serve`
   3. `npm run full-build`
   4. `serve -s dist`

Both the server and its frontend interface should now be running locally, on your machine.

## Packaging Layout

This server is packaged into four main containers:

1. Java HTTP Server (Backend)
  * [This container is published through GitHub Packages](https://github.com/Rowan-McKereghan/adobe-project/pkgs/container/int-to-roman-backend) in an `arm64` image.
  * It's constructed using official Docker images for [OpenJDK Alpine](https://hub.docker.com/_/amazoncorretto) and [Maven Alpine](https://hub.docker.com/_/maven), since they're lightweight and cross-platform.
  * It runs on `http://localhost:8080`, with `/romannumeral` serving the integer conversion functionality, and `/metrics` allowing for Prometheus to scrape metrics data.
 
2. React App (Frontend)
  * [This container is published through GitHub Packages](https://github.com/Rowan-McKereghan/adobe-project/pkgs/container/int-to-roman-frontend) in an `arm64` image.
  * It's constructed using the official Docker image for [Node Alpine](https://hub.docker.com/_/node), since it's lightweight and cross-platform.
  * It runs on `http://localhost:3000` and sends HTTP requests to the Java backend, and updates its state when it receives JSON data accordingly.

3. Prometheus
  * This container is constructed using the official Docker image for [Prometheus.](https://hub.docker.com/r/prom/prometheus)
  * It runs on `http://localhost:9090` and queries the Java backend to scrape metrics.

4. Grafana
  * This container is constructed using the official Docker image for [Grafana.](https://hub.docker.com/r/grafana/grafana)
  * It hosts dashboards on `http://localhost:3001/dashboards` and visualizes the metrics scraped by Prometheus.

### Code Organization

The source code for this project is organized thusly:
```
.
├─ README.md
├─ docker-compose.yml   # Automates building/pulling and running images and containers
├─ http-server          # Java HTTP Server dir
│   ├─ Dockerfile         # Creates backend image
│   └─ int-to-roman       # Maven project dir               
│       ├─ mvnw             # Generates build files for MacOS and Linux
│       ├─ mvnw.cmd         # Generates build files for Windows
│       ├─ pom.xml          # Manages Maven dependencies and plugins
│       ├─ .mvn/wrapper     # Maven Wrapper files
│       └─ src              # HTTP Server source code
│           ├─ main/java/adobe_project  # main Maven package
│           │   ├─ HttpResponse.java            # Basic lightweight HTTP response class
│           │   ├─ Main.java                    # Main method here, starts server
│           │   ├─ MetricsHandler.java          # Serves scraped metrics data to "/metrics"
│           │   ├─ RomanHandler.java            # Handles integer conversion requests, 
│           │   │                                serves JSONs or 400 to "/romannumeral".
│           │   └─ RomanNumeralHTTPServer.java  # Int conversion logic, server port binding
│           └─ main/resources
│           │       └─ log4j2.xml               # Logger Config File
│           └─ test/java/adobe_project  # maven testing package
│               ├─ HttpResponseTest.java        # Basic .equals() tests
│               ├─ IntegrationTests.java        # Pings server, tests with expected responses
│               ├─ RomanHandlerTest.java        # Tests if correct HTTP Response is served
│               └─ RomanNumeralHTTPServerTest.java # Tests integer conversion logic
│
├─ react-frontend       # React app dir
│   ├─ Dockerfile            # Creates frontend image
│   ├─ index.html            # Skeleton HTML file (required by Vite)
│   ├─ package-lock.json     # Manages all node dependencies
│   ├─ package.json          # Manages and sets up dependencies and builds for production
│   ├─ public                      
│   │   └─ roman-numerals.png       # Icon for browser tab
│   ├─ src                   # React app source code
│   │   ├─ App.css                  # Styling for React App
│   │   ├─ App.jsx                  # Renders react app and updates state
│   │   ├─ App.test.js              # Tests that App state is updated
│   │   ├─ fetchRoman.jsx           # Fetches JSON data from backend
│   │   ├─ index.css                # Styles index.html file
│   │   ├─ main.jsx                 # Main function
│   │   └─ ...                            
│   └─ ...                         # Other default config files
├─ prometheus.yml
├─ provisioning            # Grafana config files
│   ├─ dashboards                # JSONs and config files for setting up default dashboards
│   │   └─ ...
│   └─ datasources               # Config files for connecting to Prometheus
│       └─ ...                   
└─ readme-images
    └─ ...
```

## Engineering and Testing Methodologies

### Java HTTP Server

This Java HTTP Server operates by first setting up a localhost endpoint at port 8080, then listening for requests and responding with JSON data. I used the built-in `com.sun.net.httpserver` Java library to facilitate this, as it is lightweight, easy-to-use, and covers the needs of a simple, locally-hosted server more than adequately. It writes to sockets using the Java `OutputStream` object. 

The `Main` class starts the server and injects DevOps dependencies into the HTTP handlers through `RomanNumeralHTTPServer` static methods. I decided to split up the code this way to be able to mock these dependencies during testing, but also because it let me split up each HTTP handler into its own class, which allows the server to remain fairly modular – adding or removing a page URL and its respective handler does not affect other handlers. It also allows the logs to direct a user to debug where an IO error occurred, since they propagate upward from where they happen. 

The server has two handler classes at the moment, `MetricsHandler` for Prometheus scraping and `RomanHandler` for writing 400 responses and JSON objects with integers and Roman numerals. I wrote my own small `HttpResponse` class since I didn't need more data than response body, code, and content type, and it made testing for correctness a lot easier when overriding the `equals()` and `hashCode()` methods since I could directly compare objects by value. 

I used JUnit 5 to write the unit and integration tests for the HTTP server in. I organized the unit tests by class, and ensured that their logic was executed as expected using `assert()` statements. For the integration tests, I used Mockito to inject mock DevOps objects (logger, registry, etc.) into a server running on a separate port, then pinged that server and tested the HTTP responses and JSON objects against what was expected. I also manually tested the server with `curl` to see if the correct response was sent.

I used log4j2 and Prometheus for logging and metrics collection, respectively, because they both integrate easily with Maven/Java and React/JavaScript. The logger writes to a `.log` file with info, error, and fatal messages in order to facilitate debugging and server upkeep in the Docker container. I used a counter and a registry from the Prometheus API to keep track of the number of HTTP requests coming into the server. This information could be used for scalability or throughput calculation purposes, if the server was deployed to the cloud.

### React Frontend App

I designed the React app using the Adobe React Spectrum component library for a few reasons:
- Theming allowed for very easy implementation of light and dark mode switch
- Built-in `<Flex>` component allowed for easy bootstrapping
- The `<TextField>` component made updating user input state for fetch requests simple
- It was recommended to me in the specification

The app consists of a just a few major elements: a text input field, a button, a light and dark mode switch, and a text element that updates with a Roman numeral when the user clicks the button after inputting an integer. The `fetch()` works by having an event listener tied to the button's `onPress` event, which then formats a URL with the user's input and pings the HTTP server backend. The resulting JSON object (or error message, if the user inputted their number incorrectly) is then parsed and its data displayed on the page.

I tested updating the app's state using the Jest framework in `App.test.js`, because it allowed me to mock fetch requests and check the update state logic without actually requesting data from the backend. Jest also allows asynchronous checks on the app's state when combined with the React testing library's `userState` and `render` imports. Overall, I used it because it makes implementing frontend components a lot more reliable. 

### Containerization and DevOps

I encapsulated, containerized, and orchestrated my entire project using Docker, for three main reasons:

  1. <b> Ease of Use. </b> It allows users to either build or pull images, then run them inside containers and get the server working with a single command.
  2. <b> Cross-Platform. </b> Docker containers can be built for every major OS on every major architecture, so the user's platform is negligible unless they are using something rare and unsupported.
  3. <b> Modular Orchestration. </b> Docker containers only contain exactly what they need for runtime, and use minimal resources to communicate to each other if they need to. For my project, it was extremely simple to use Docker and have my React app and my DevOps capabilities connect to my Java backend.

Grafana and Prometheus work together in order to collect and visualize metrics. I added them because, if this server were to be deployed to the cloud, it would be important to keep track of server health and calculate server performance costs and scalability based on user activity. They are also extremely easy to integrate with Docker, since they both have official images that can be containerized and connect to my frontend and backend with just a few lines in my `.yml` file. Also, Grafana allows for custom dashboards to be saved and reused, which allows users to access default dashboards of my choosing instead of having to set them up themselves.

### Possible Extensions

What are some potential next steps for this project? 

  1. The server could be deployed to the cloud and updated and maintained using CI/CD. I've already uploaded some Docker images using GitHub Packages, so using GitHub Actions and Jenkins to deploy them would be relatively simple if there was a server to host them on. 
  2. Since I kept the handlers modular, and used dependency injection to separate the server logic and DevOps capabilities, adding more functionalities to both would be relatively simple. Implementing a Roman numeral to integer converter would be quick, and then a service keeping track of the ratio between requests to both handlers using additional Prometheus objects and Grafana dashboards could be added. This would not affect any of the current server functionalities either.
  3. I could implement a custom UI to handle integers above 3999. According to the [Roman numeral specification](https://en.wikipedia.org/wiki/Roman_numerals), digits in the thousands and hundreds of thousands were notated by using lines above and boxes around those digits, respectively. This would allow users to enter a far greater range of positive integers, and would pose an interesting logic and design challenge. 

## Built With

* [Amazon Corretto OpenJDK](https://hub.docker.com/_/amazoncorretto)
* [React](https://react.dev/)
* [Node](https://nodejs.org/en)
* [Apache Maven](https://maven.apache.org/)
  * [Maven Shade](https://maven.apache.org/plugins/maven-shade-plugin/)
  * [Maven Surefire](https://maven.apache.org/surefire/maven-surefire-plugin/)
  * [Maven Jar](https://maven.apache.org/plugins/maven-jar-plugin/)
* [JUnit 5](https://junit.org/junit5/)
* [Mockito](https://site.mockito.org/)
* [org.json](https://mvnrepository.com/artifact/org.json/json)
* [log4j2](https://logging.apache.org/log4j/2.12.x/)
* [Micrometer](https://micrometer.io/)
* [Prometheus](https://prometheus.io/)
* [Grafana](https://grafana.com/grafana/)
* [Vite](https://vite.dev/)
* [Jest](https://jestjs.io/)
* [Adobe React Spectrum](https://react-spectrum.adobe.com/react-spectrum/index.html)
* [Eslint](https://eslint.org/)
* [Babel](https://babeljs.io/)
* [identity-obj-proxy](https://www.npmjs.com/package/identity-obj-proxy)
* [Docker](https://www.docker.com/)

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- CONTACT -->
## Contact

Rowan McKereghan - [LinkedIn](www.linkedin.com/in/rowan-mckereghan-2595b2223) - rowanmckereghan@gmail.com

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[light-mode]: ./readme-images/light%20mode.png
[dark-mode]: ./readme-images/dark%20mode.png
[dashboards]: ./readme-images/dashboards.png