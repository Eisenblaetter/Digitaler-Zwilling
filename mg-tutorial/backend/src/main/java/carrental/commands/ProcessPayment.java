/*SOL start Class Services*/
package carrental.commands;

import umlp.backendrte.command.StatusResult;

public class ProcessPayment extends ProcessPaymentTOP {
  
  public ProcessPayment(long rentalTransactionGemId) {
    super(rentalTransactionGemId);
  }
  
  @Override
  public StatusResult doAction() {
    return StatusResult.error(getId());
  }
}
/*SOL end Class Services*/