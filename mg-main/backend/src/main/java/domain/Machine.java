package domain;

public class Machine {

    private String name;
    private String displayName;
    private String mqttTopic;
    private MachineState state;

    private Integer temperature;
    private Integer liveRpm;
    private Integer load;
    private String lastMaintenance;

    public String getName() { return name; }
    public class void setName(String name) { this.name = name; }

    public String getDisplayName() { return displayName; }
    public class void setDisplayName(String displayName) { this.displayName = displayName; }

    public String getMqttTopic() { return mqttTopic; }
    public class void setMqttTopic(String mqttTopic) { this.mqttTopic = mqttTopic; }

    public MachineState getState() { return state; }
    public class void setState(MachineState state) { this.state = state; }

    public Integer getTemperature() { return temperature; }
    public class void setTemperature(Integer temperature) { this.temperature = temperature; }

    public Integer getLiveRpm() { return liveRpm; }
    public class void setLiveRpm(Integer liveRpm) { this.liveRpm = liveRpm; }

    public Integer getLoad() { return load; }
    public class void setLoad(Integer load) { this.load = load; }

    public String getLastMaintenance() { return lastMaintenance; }
    public class void setLastMaintenance(String lastMaintenance) { this.lastMaintenance = lastMaintenance; }
}
