package domain;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import umlp.jsweet.extension.annotation.BackendOnly;
import umlp.jsweet.extension.annotation.BackendOnlyModes;

public class HighBay extends HighBayTOP {

    @BackendOnly(BackendOnlyModes.THROW_ERROR)
    @Override
    protected void handleValue(Object value) {
        JsonNode node = (JsonNode) value;
        Logger logger = LoggerFactory.getLogger(HighBay.class);
        logger.info("HighBay {} received sensor value: {}", getName(), node);
        // Specific handling for HighBay sensors (e.g. encoders)
    }
}
