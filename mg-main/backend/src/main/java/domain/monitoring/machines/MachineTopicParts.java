package domain.monitoring.machines;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MachineTopicParts {
    public String baseTopic;
    public int islandNumber;

    public String machineType;
    public String machineIdentifier;

    public TopicMessageType messageType;
    public MeasurementType measurement;
    public String remainingTopic;

    public boolean isStandaloneEvent;

    public MachineTopicParts(String fullTopic) {
        String[] parts = fullTopic.split("/");

        if (parts[2].equals("events")) {
            parseStandaloneEvent();
        } else {
            parseRegularTopic(fullTopic, parts);
        }
    }

    public void parseStandaloneEvent() {
        isStandaloneEvent = true;
    }

    public void parseRegularTopic(String fullTopic, String[] parts) {
        if (parts.length < 5) {
            throw new IllegalArgumentException("Invalid topic format: " + fullTopic);
        }

        baseTopic = parts[0];
        islandNumber = Integer.parseInt(parts[1].replace("Island ", ""));
        machineType = parts[2];
        machineIdentifier = parts[3];

        if (parts[4].equals("events")) {
            messageType = TopicMessageType.EVENT;
        } else if (parts[4].equals("measurements")) {
            messageType = TopicMessageType.MEASUREMENT;
        } else {
            throw new IllegalArgumentException("Invalid message type: " + parts[4]);
        }

        // TODO: see what's the most efficient way to achieve this parsing, since we do this on every message!!
        parts[0] = parts[1] = parts[2] = parts[3] = parts[4] = null;

        remainingTopic = Stream.of(parts)
                .filter(s -> s != null && !s.isEmpty())
                .collect(Collectors.joining("/"));
    }

    public String getIdentifyingTopic() {
        return baseTopic + "/Island " + islandNumber + "/" + machineType + "/" + machineIdentifier;
    }

    public TopicMessageType getMessageType() {
        return messageType;
    }

    public enum MeasurementType {
        CONVEYOR_RUNNING,
        COMPRESSOR_RUNNING,
        ACT_RED_EJECTOR,
    }

    public MeasurementType getMeasurement() {
        return measurement;
    }
}
