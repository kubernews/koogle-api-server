FROM adoptopenjdk/openjdk11
WORKDIR /app/
COPY . .

RUN ./mvnw clean package -DskipTests

FROM adoptopenjdk/openjdk11
WORKDIR /deploy/

ENV APP_NAME koogle-api-server
ENV APP_VERSION 1.0.0

COPY --from=0 /app/target/$APP_NAME-$APP_VERSION.jar /deploy/$APP_NAME-$APP_VERSION.jar

RUN mkdir -p /whatap
COPY --from=whatap/kube_mon /data/agent/micro/whatap.agent-*.jar /whatap
RUN ls /whatap

ARG WHATAP_CONF
ENV WHATAP_CONF ${WHATAP_CONF}

RUN echo -e "\n\
${WHATAP_CONF}\n\
whatap.okind=qwerqwer\n\
whatap.server.host=15.165.146.117\n\
whatap_micro_enabled=true">/whatap/whatap.conf

ARG SPRING_OPTION
ENV SPRING_OPTION=${SPRING_OPTION}

ENTRYPOINT exec java -javaagent:/whatap/whatap.agent-2.2.12.jar -Dwhatap.micro.enabled=true -jar ${SPRING_OPTION} $APP_NAME-$APP_VERSION.jar