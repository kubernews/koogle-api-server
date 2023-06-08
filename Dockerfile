FROM adoptopenjdk/openjdk11
WORKDIR /app/
COPY . .

ENV WHATAP_CONF ${WHATAP_CONF}
RUN echo ${WHATAP_CONF}>/app/whatap.conf

RUN ./mvnw clean package

FROM adoptopenjdk/openjdk11
WORKDIR /deploy/

ENV REPO_NAME koogle-api-server
ENV APP_NAME koogle-api-server
ENV APP_VERSION 1.0.0

COPY --from=0 /app/$REPO_NAME/target/$APP_NAME-$APP_VERSION.jar /deploy/$APP_NAME-$APP_VERSION.jar
COPY --from=0 /app/whatap.conf /deploy/

RUN mkdir -p /whatap
COPY --from=whatap/kube_mon /data/agent/micro/whatap.agent-*.jar /whatap
COPY ./whatap.conf /whatap/

ENV SPRING_OPTION=""
ENTRYPOINT exec java -javaagent:/whatap/whatap.agent-2.2.3.jar -Dwhatap.micro.enabled=true -jar ${SPRING_OPTION} $APP_NAME-$APP_VERSION.jar