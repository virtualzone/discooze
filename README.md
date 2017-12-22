# Discooze
Publish panels (text, HTML, video, image) and let users discuss it.

## Getting it up and running using Docker
1. Prepare a PostgreSQL database container, e.g.:
   ```
   docker run --name postgres \
	  -v /tmp/postgres:/var/lib/postgresql/data \
	  -e POSTGRES_PASSWORD=root \
	  -d \
	  postgres:10-alpine
   ```
1. Place the [application.yml](https://github.com/weweave/discooze/blob/master/backend/src/main/resources/application.yml) somewhere and customize it to your needs.
1. Run the container like this:
   ```
   docker run --name discooze \
     -v /tmp/application.yml:/opt/discooze/application.yml:ro \
     -p 8080:8080 \
     --link postgres:postgres \
     -d \
     weweave/discooze
   ```
1. Open your web browser and go to: http://localhost:8080

## Administration
The administration interface is available at: http://localhost:8080/_admin

Log in with admin/admin.

## Technology
Discooze is based on the following technologies and frameworks:
* Backend: Spring Boot, Spring Data REST
* Frontend: Angular 5, Bootstrap 4
* Docker