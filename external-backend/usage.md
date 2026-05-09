# How to test MQTT broker/replay

Start replayer and broker using the following command:
```bash
docker compose up --build -d
```
If you want to be attached to the container's logs, omit the `-d` flag.

Simple command to see everything that's coming from broker:
```bash
docker exec -it mqtt-broker mosquitto_sub -h localhost -p 1883 -t "#" -v
```

TODO: use data analysis results to divide into different machines (write prototype python script with logic that we can move into monitoring backend later)