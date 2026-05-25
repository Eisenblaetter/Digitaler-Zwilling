package domain;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import umlp.jsweet.extension.annotation.BackendOnly;
import umlp.jsweet.extension.annotation.BackendOnlyModes;

public class ConveyorBelt extends ConveyorBeltTOP {

    @BackendOnly(BackendOnlyModes.THROW_ERROR)
    @Override
    protected void handleValue(Object value) {
        JsonNode node = (JsonNode) value;
        Logger logger = LoggerFactory.getLogger(ConveyorBelt.class);
        logger.info("ConveyorBelt {} received sensor value: {}", getName(), node);

        // Specific handling for ConveyorBelt sensors can go here
    }
}
