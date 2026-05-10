import paho.mqtt.client as mqtt

# broker settings
broker_ip = "127.0.0.1"
broker_port = 1883
keepalive = 30
client_id="pyclient"
topic = 'PLC/Island 1/HighBay/#' # subscribe to topics with varying specificity (by machine, by island, etc.)

def on_message(client, userdata, message):
    payload = str(message.payload.decode("utf-8"))
    topic = message.topic
    print("Message: ", payload)
    print("Topic: ", topic)
 
def on_connect(client, userdata, flags, rc):
    print("Connected with result code " + str(rc))
    client.subscribe(topic)

client = mqtt.Client(client_id=client_id, clean_session=True, userdata=None, protocol=mqtt.MQTTv311, transport="tcp")
client.on_connect = on_connect
client.on_message = on_message


client.connect(broker_ip, broker_port, keepalive)
 
print("Connected to MQTT Broker: " + broker_ip + ":" + str(broker_port))

client.loop_forever()