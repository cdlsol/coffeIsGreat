FROM postgres:15

ENV POSTGRES_USER=admin
ENV POSTGRES_PASSWORD=admin
ENV POSTGRES_DB=coffeeData

EXPOSE 5434

# docker run -d --name pg-coffee-db -p 5434:5432 pg-coffee-db

