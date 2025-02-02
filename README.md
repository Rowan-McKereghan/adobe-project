# Adobe GenStudio Integer to Roman Numeral Server

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

This is a basic HTTP server written in Java with a React frontend interface that converts integers to Roman numerals when queried. 

Once running, users can request data from it with commands such as ```curl "http://localhost:8080/romannumeral?query={integer}"```, where ```{integer}``` is some number in the range `[1, 3999]`. Roman numerals with values of 4000 and above have different notations than just letters. [See specification for Roman numerals here.](https://www.britannica.com/topic/Roman-numeral)

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

The server also has basic DevOps capabilities in order to monitor server health, log and debug potential issues, and keep track of basic metrics. Inside the Docker container, users will be able to find a file titled `application-{yyyymmdd}.log`, where `{yyyymmdd}` is today. This log file will have records of what the server is doing on a moment-to-moment basis – writing HTTP requests, serving 400, fatal IO errors, etc. You can access this file by entering the compose stack and executing commands into the `int-to-roman-backend` Docker image directly through Docker Desktop, or by using the command `docker exec -it int-to-roman-backend sh -c "{your command here}"`, where the shell command could be something like `cat application-{yyyymmddd}.log`. 

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



## Built With (Dependency Attr.)

* [Amazon Corretto OpenJDK]()
* [React]()
* [Node]()
* [Apache Maven]()
  * [Maven Shade]()
  * [Maven Surefire]()
  * [Maven Jar]()
* [JUnit]()
* [Mockito]()
* [org.json]()
* [log4j2]()
* [Micrometer]()
* [Prometheus]()
* [Grafana]()
* [Vite]()
* [Jest]()
* [Adobe Spectrum React]()
* [Eslint]()
* [Babel]()
* [identity-obj-proxy](https://www.npmjs.com/package/identity-obj-proxy)
* [Docker]()

<p align="right">(<a href="#readme-top">back to top</a>)</p>








### Installation

1. Get a free API Key at [https://example.com](https://example.com)
2. Clone the repo
   ```sh
   git clone https://github.com/github_username/repo_name.git
   ```
3. Install NPM packages
   ```sh
   npm install
   ```
4. Enter your API in `config.js`
   ```js
   const API_KEY = 'ENTER YOUR API';
   ```
5. Change git remote url to avoid accidental pushes to base project
   ```sh
   git remote set-url origin github_username/repo_name
   git remote -v # confirm the changes
   ```

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- USAGE EXAMPLES -->
## Usage

Use this space to show useful examples of how a project can be used. Additional screenshots, code examples and demos work well in this space. You may also link to more resources.

_For more examples, please refer to the [Documentation](https://example.com)_

<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- LICENSE -->
## License

Distributed under the project_license. See `LICENSE.txt` for more information.

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- CONTACT -->
## Contact

Your Name - [@twitter_handle](https://twitter.com/twitter_handle) - email@email_client.com

Project Link: [https://github.com/github_username/repo_name](https://github.com/github_username/repo_name)

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- ACKNOWLEDGMENTS -->
## Acknowledgments

* []()
* []()
* []()

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[light-mode]: ./readme_images/light%20mode.png
[dark-mode]: ./readme_images/dark%20mode.png
[dashboards]: ./readme_images/dashboards.png
[contributors-shield]: https://img.shields.io/github/contributors/github_username/repo_name.svg?style=for-the-badge
[contributors-url]: https://github.com/github_username/repo_name/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/github_username/repo_name.svg?style=for-the-badge
[forks-url]: https://github.com/github_username/repo_name/network/members
[stars-shield]: https://img.shields.io/github/stars/github_username/repo_name.svg?style=for-the-badge
[stars-url]: https://github.com/github_username/repo_name/stargazers
[issues-shield]: https://img.shields.io/github/issues/github_username/repo_name.svg?style=for-the-badge
[issues-url]: https://github.com/github_username/repo_name/issues
[license-shield]: https://img.shields.io/github/license/github_username/repo_name.svg?style=for-the-badge
[license-url]: https://github.com/github_username/repo_name/blob/master/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://linkedin.com/in/linkedin_username
[product-screenshot]: images/screenshot.png
[Next.js]: https://img.shields.io/badge/next.js-000000?style=for-the-badge&logo=nextdotjs&logoColor=white
[Next-url]: https://nextjs.org/
[React.js]: https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB
[React-url]: https://reactjs.org/
[Vue.js]: https://img.shields.io/badge/Vue.js-35495E?style=for-the-badge&logo=vuedotjs&logoColor=4FC08D
[Vue-url]: https://vuejs.org/
[Angular.io]: https://img.shields.io/badge/Angular-DD0031?style=for-the-badge&logo=angular&logoColor=white
[Angular-url]: https://angular.io/
[Svelte.dev]: https://img.shields.io/badge/Svelte-4A4A55?style=for-the-badge&logo=svelte&logoColor=FF3E00
[Svelte-url]: https://svelte.dev/
[Laravel.com]: https://img.shields.io/badge/Laravel-FF2D20?style=for-the-badge&logo=laravel&logoColor=white
[Laravel-url]: https://laravel.com
[Bootstrap.com]: https://img.shields.io/badge/Bootstrap-563D7C?style=for-the-badge&logo=bootstrap&logoColor=white
[Bootstrap-url]: https://getbootstrap.com
[JQuery.com]: https://img.shields.io/badge/jQuery-0769AD?style=for-the-badge&logo=jquery&logoColor=white
[JQuery-url]: https://jquery.com 
