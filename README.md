# New Relic Coding Challenge
## Setup Backend
Assumes installation of [Docker](https://docs.docker.com/engine/install/).

Start application:
```console
cd backend/src/main/docker
docker-compose up
```
Application can be queried from http://localhost:8080.
#### Run Tests:
```console
./mvnw test
```
#### Update Docker image:
```console
./mvnw clean package -DskipTests
cp target/challenge-0.0.1-SNAPSHOP.jar src/main/docker
cd backend/src/main/docker
docker-compose down
docker rmi docker-spring-boot-postgres:latest
docker-compose up
```
## Setup Frontend
Assumes installation of [Node.js](https://nodejs.org/en/download/) and a running backend.

Start application:
```console
cd frontend
npm install
npm run start
```
Web page can be viewed from http://localhost:3000.

## Considerations
### Technology
Java + Spring + Postgres is a widely used backend stack choice. It allows for efficient scalability, structure, and support for the given use case. However, this tech stack should ideally be kept away from a serverless architecture as Spring experiences costly cold starts, and SQL databases may be overwhelmed by short-lived database connections from serverless functions in high-volume instances. A monorepo can be maintained until the business use cases are well understood and then can be broken out into a microservice architecture.

React on the frontend is the de-facto choice for web development. It is widely used and supported. Future improvements would see a refactor to Typescript for a more robust development experience, as well as a move to the Next.js framework for easy and structured page pre-rendering as well as many other benefits.

### Future improvements
In the interest of time, certain features/capabilities/designs were excluded from this project. These include (but are not limited to):

- Front end testing (cypress and jest could be used)
- Frontend UI/UX design (a react component framework could be used for standardized design: bootstrap, material ui, etc.)
- A more robust implementation of the /customers endpoint to support any combination of filtering, sorting, and pagination in a single call
- Rigid backend and frontend input validation
- Logical and useful backend error handling
- Authorization/Authentication
- Additional backend testing (integration tests were chosen for the most impactful coverage)
- Dev vs prod environment management
- Deployment to a cloud environment (AWS, GCP, Azure, etc.)
- CI/CD capabilities