FROM adoptopenjdk/openjdk11
WORKDIR /app/
COPY . .

RUN ./mvnw clean package -DskipTests

FROM adoptopenjdk/openjdk11
WORKDIR /deploy/

RUN apt-get update && apt-get install -y wget curl && rm -rf /var/lib/apt/lists/*


ENV APP_NAME=koogle-api-server
ENV APP_VERSION=1.0.0

COPY --from=0 /app/target/$APP_NAME-$APP_VERSION.jar /deploy/$APP_NAME-$APP_VERSION.jar

RUN wget "https://api.whatap.io/agent/whatap.agent.java.tar.gz" &&  \
    tar -zxvf whatap.agent.java.tar.gz && \
    rm -f whatap.agent.java.tar.gz

RUN cd whatap &&  \
    AGENT=$(find . -maxdepth 1 -type f -name "whatap.agent-*.jar") && \
    echo "Found agent: $AGENT" && \
    java -cp ${AGENT} whatap.agent.setup.Rename -from ${AGENT} -to whatap.agent.kube.jar && \
    rm whatap.conf

ARG WHATAP_CONF
ENV WHATAP_CONF=${WHATAP_CONF}

RUN mkdir -p /deploy/whatap && \
    bash -c "if [ ! -f /deploy/whatap/whatap.conf ]; then \
echo \"\$WHATAP_CONF\" > /deploy/whatap/whatap.conf && \
echo 'logsink_rt_enabled=true' >> /deploy/whatap/whatap.conf && \
echo 'logsink_enabled=true' >> /deploy/whatap/whatap.conf && \
echo 'hook_service_patterns=com.kubenews.koogleapiserver.VirtualScheduler.virtualRead' >> /deploy/whatap/whatap.conf && \
echo 'whatap_micro_enabled=true' >> /deploy/whatap/whatap.conf; fi"





ARG SPRING_OPTION
ENV SPRING_OPTION=${SPRING_OPTION}

ENTRYPOINT exec java -javaagent:/deploy/whatap/whatap.agent.kube.jar -Dwhatap.micro.enabled=true -jar ${SPRING_OPTION} $APP_NAME-$APP_VERSION.jar