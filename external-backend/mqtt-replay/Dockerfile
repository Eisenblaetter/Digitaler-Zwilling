FROM alpine
RUN apk update && apk add python3 py3-pip

RUN python3 -m venv /opt/venv
ENV PATH="/opt/venv/bin:$PATH"

RUN pip install --no-cache-dir mqtt-recorder

WORKDIR /
COPY simple_process.csv /

ENV MQTT_HOST=mqtt-broker
ENV MQTT_PORT=1883
ENV MQTT_FILE=simple_process.csv

# maybe make mode/loop configurable later, depending on use cases
CMD mqtt-recorder --host ${MQTT_HOST} --port ${MQTT_PORT} --mode replay --loop true --file ${MQTT_FILE}