package domain.monitoring.machines;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MachineTopicPartsTest {

    @Test
    void parsesRegularEventTopic() {
        MachineTopicParts parts = new MachineTopicParts("factory/Island 1/Oven/OV-01/events/temperature");

        assertFalse(parts.isStandaloneEvent);
        assertEquals("factory", parts.baseTopic);
        assertEquals(1, parts.islandNumber);
        assertEquals("Oven", parts.machineType);
        assertEquals("OV-01", parts.machineIdentifier);
        assertEquals(TopicMessageType.EVENT, parts.messageType);
        assertEquals("temperature", parts.remainingTopic);
    }

    @Test
    void parsesRegularMeasurementTopic() {
        MachineTopicParts parts = new MachineTopicParts("factory/Island 2/Conveyor/CV-03/measurements/speed");

        assertFalse(parts.isStandaloneEvent);
        assertEquals("factory", parts.baseTopic);
        assertEquals(2, parts.islandNumber);
        assertEquals("Conveyor", parts.machineType);
        assertEquals("CV-03", parts.machineIdentifier);
        assertEquals(TopicMessageType.MEASUREMENT, parts.messageType);
        assertEquals("speed", parts.remainingTopic);
    }

    @Test
    void parsesStandaloneEvent() {
        MachineTopicParts parts = new MachineTopicParts("factory/Island 1/events");

        assertTrue(parts.isStandaloneEvent);
    }

    @Test
    void parsesMultiSegmentRemainingTopic() {
        MachineTopicParts parts = new MachineTopicParts("factory/Island 3/Robot/RB-02/events/arm/position/x");

        assertEquals("factory", parts.baseTopic);
        assertEquals(3, parts.islandNumber);
        assertEquals("Robot", parts.machineType);
        assertEquals("RB-02", parts.machineIdentifier);
        assertEquals(TopicMessageType.EVENT, parts.messageType);
        assertEquals("arm/position/x", parts.remainingTopic);
    }

    @Test
    void parsesIslandNumberWithoutPrefix() {
        MachineTopicParts parts = new MachineTopicParts("factory/Island 10/Press/PR-05/measurements/force");

        assertEquals(10, parts.islandNumber);
    }

    @Test
    void throwsOnTooFewParts() {
        assertThrows(IllegalArgumentException.class,
                () -> new MachineTopicParts("factory/Island 1/Oven/OV-01"));
    }

    @Test
    void throwsOnInvalidMessageType() {
        assertThrows(IllegalArgumentException.class,
                () -> new MachineTopicParts("factory/Island 1/Oven/OV-01/unknown/temp"));
    }

    @Test
    void parsesMinimalEventTopicWithEmptyRemaining() {
        MachineTopicParts parts = new MachineTopicParts("base/Island 0/Type/ID/events");

        assertEquals("base", parts.baseTopic);
        assertEquals(0, parts.islandNumber);
        assertEquals("Type", parts.machineType);
        assertEquals("ID", parts.machineIdentifier);
        assertEquals(TopicMessageType.EVENT, parts.messageType);
        assertEquals("", parts.remainingTopic);
    }
}
