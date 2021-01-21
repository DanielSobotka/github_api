FROM postgres:13

COPY init_schema.sql /docker-entrypoint-initdb.d/init_data.sql
ENV POSTGRES_HOST_AUTH_METHOD=trust

EXPOSE 5432

