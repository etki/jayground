FROM niaquinto/gradle
MAINTAINER Etki etki@etki.name

ENV spring_profiles_active dev

EXPOSE 8001

ADD . /app

ENTRYPOINT ["gradle"]
CMD ["clean", "build", "jettyRunWar", "--info"]
