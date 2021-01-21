#!/bin/bash
set -e
DIR=$(dirname "$0")

docker build -t github_api/postgresql:1 "$DIR"
