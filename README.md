Simple github api
====================

## How to start
Run:
`./postgresql/build_postgres_image.sh && ./postgresql/start_postgres_container.sh`
to build and run database

Then `./gradlew build bootRun` to build and run a service.

Example request: `wget localhost:8080/users/DanielSobotka`

## License
Licensed under the Apache License, Version 2.0
