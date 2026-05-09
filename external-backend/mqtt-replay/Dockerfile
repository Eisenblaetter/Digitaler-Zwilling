FROM alpine
RUN apk update && apk add python3 py3-pip
# Optional: mqtt-recorder may need a virtual environment or --break-system-packages depending on Alpine/Pip version, 
# but simply pip3 install is the start. Depending on the python version, --break-system-packages is needed.
RUN pip3 install --break-system-packages mqtt-recorder

WORKDIR /
COPY simple_process.csv /

ENV MQTT_HOST=mqtt-broker
ENV MQTT_PORT=1883
ENV MQTT_FILE=simple_process.csv

# maybe make mode/loop configurable later, depending on use cases
CMD mqtt-recorder --host ${MQTT_HOST} --port ${MQTT_PORT} --mode replay --loop true --file ${MQTT_FILE}