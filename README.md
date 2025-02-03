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
    <li><a href="#packaging-layout">Packaging Layout</a></li>
    <li>
      <a href="#engineering-and-testing-methodology">Engineering and Testing Methodology</a>
      <ul>
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

For metrics and monitoring, the server is using Prometheus to scrape metrics data and Grafana to monitor and visualize that data. If users wish, they can query Prometheus directly at [`http://localhost:9090`](). 

Users should visit Grafana at [`http://localhost:3001/dashboards`]() to see the default dashboards the have been set up for the server – a statistic panel and a graph and table panel combination. These two dashboards monitor server health (i.e., cannot visualize data if the server is down) and keep track of the total HTTP requests sent to the server. This number resets if the Docker containers are stopped. 

See example of default dashboard here when server was stopped, then down, then restarted:

![dashboards]

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Getting Started and Building the Project

<b>1. Once the repo is cloned and Docker is running, open the main directory and simply run: </b>
```sh
docker compose up --build
```
in order to start the server. You can interrupt the process on the command line or hit stop in Docker Desktop to stop the composed
containers from running. To restart the containers without rebuilding, remove the `--build` tag from your command.
If you wish to run the server locally without using and/or installing Docker, see below.


<b>2. To remove the Docker containers, run: </b>
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

graph of packaging, explanations of file locations

## Engineering and Testing Methodology

- http-server: How the server works, what purpose each file/class/method serves, why and how I tested what I did (unit + integration), how logging and metrics collection works and why it's useful
- react-frontend: Page elements (button, etc.), how I fetch from server and update state, tests, why adobe spectrum react
- docker containerization, prometheus queries, grafana monitoring

### Possible Extensions




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