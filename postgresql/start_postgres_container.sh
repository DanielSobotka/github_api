#!/bin/bash

docker run -it --rm -p 5432:5432 github_api/postgresql:1 -c log_statement=all
