# Internship Tracker Backend

Spring Boot backend for the internship tracker app.

## Run locally

```bash
./mvnw spring-boot:run
```

By default, the app uses an in-memory H2 database for local development.

## PostgreSQL setup (Render / Railway / local Postgres)

Set these environment variables:

- `SPRING_DATASOURCE_URL` (JDBC format): `jdbc:postgresql://<host>:<port>/<database>`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`
- `JWT_SECRET`
- `CORS_ALLOWED_ORIGINS` (comma-separated frontend origins)

Example:

```bash
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/internshiptracker
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=postgres
JWT_SECRET=replace-with-a-strong-secret
CORS_ALLOWED_ORIGINS=http://localhost:5173,https://your-frontend.vercel.app
```

Then run:

```bash
./mvnw spring-boot:run
```
