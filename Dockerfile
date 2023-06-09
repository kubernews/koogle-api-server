FROM adoptopenjdk/openjdk11
WORKDIR /app/
COPY . .

RUN ./mvnw clean package

FROM adoptopenjdk/openjdk11
WORKDIR /deploy/

ENV REPO_NAME koogle-api-server
ENV APP_NAME koogle-api-server
ENV APP_VERSION 1.0.0
ENV WHATAP_CONF ${WHATAP_CONF}

COPY --from=0 /app/target/$APP_NAME-$APP_VERSION.jar /deploy/$APP_NAME-$APP_VERSION.jar

RUN mkdir -p /whatap
COPY --from=whatap/kube_mon /data/agent/micro/whatap.agent-*.jar /whatap

RUN echo -e "${WHATAP_CONF}\n\
whatap.server.host=15.165.146.117\n\
whatap_micro_enabled=true\n"\
>/whatap/whatap.conf

ENV SPRING_OPTION=""
ENTRYPOINT exec java -javaagent:/whatap/whatap.agent-2.2.7.jar -Dwhatap.micro.enabled=true -jar ${SPRING_OPTION} $APP_NAME-$APP_VERSION.jar