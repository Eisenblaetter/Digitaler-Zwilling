package digitaltwin.commands;

import umlp.backendrte.command.StatusResult;

public class SetupMqtt extends SetupMqttTOP {
    @Override
    public StatusResult doAction() {
        return StatusResult.ok(getId());
    }
}
