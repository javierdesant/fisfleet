FROM ubuntu:latest
LABEL authors="javierdesant, gonzalo, rosalblue, araceli_02, erportulg"

RUN apt-get update && apt-get install -y iputils-ping

ENTRYPOINT ["top", "-b"]