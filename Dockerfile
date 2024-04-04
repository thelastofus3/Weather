FROM ubuntu:latest
LABEL authors="slima"

ENTRYPOINT ["top", "-b"]