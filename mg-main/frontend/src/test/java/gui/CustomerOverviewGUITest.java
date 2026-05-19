package gui;

import carrental.CarrentalManager;
import carrental.Customer;
import carrental.test_infrastructure.CarrentalMockTestSetup;
import org.junit.jupiter.api.Test;
import umlp.test_rte.ClientSetup;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomerOverviewGUITest extends CarrentalMockTestSetup {

  public CustomerOverviewGUITest() {
    super(ClientSetup.of(1));
  }

  @Override
  public void _init() throws Exception {
    super._init();
    initEntities();
  }

  protected void initEntities() {
    CarrentalManager
        .customerBuilder()
        .name("Alice Allison")
        .email("alice.allison@rwth-aachen.de")
        .age(18)
        .build()
        .orElseThrow();
    CarrentalManager
        .customerBuilder()
        .name("Bob Banks")
        .email("bob.banks@rwth-aachen.de")
        .age(62)
        .build()
        .orElseThrow();
    CarrentalManager
        .customerBuilder()
        .name("Carla Clasen")
        .email("carla.clasen@rwth-aachen.de")
        .age(42)
        .build()
        .orElseThrow();
    CarrentalManager
        .customerBuilder()
        .name("Dieter Deutz")
        .email("dieter.deutz@rwth-aachen.de")
        .age(27)
        .build()
        .orElseThrow();
  }

  @Test
  void testOverview() {
    getClients(0).activate();

    /* ADD YOUR IMPLEMENTATION HERE:
     * - instantiate a CustomerOverview (don't forget to call init() on it!)
     * - assert that it shows all customers which are defined in initEntities()
     */

    /*SOL start e2eGUI1*/
    var overview = new CustomerOverview();
    overview.init();

    var customers = overview.getCustomers();
    assertEquals(4, customers.size());

    var alice = customers.get(0);

    assertEquals("Alice Allison", overview.getNameValue(alice));
    assertEquals("alice.allison@rwth-aachen.de", overview.getEmailValue(alice));
    /*SOL end e2eGUI1*/
  }

  @Test
  void testAgeDistribution() {
    getClients(0).activate();

    /* ADD YOUR SOLUTION HERE:
     * - Instantiate a new CustomerOverview (don't forget to call init() on it!)
     * - Fetch the data of the ageDistribution chart
     * - Assert that the data matches what you would expect from the test data
     */

    /*SOL start e2eGUI2*/
    var overview = new CustomerOverview();
    overview.init();

    var data = overview.getAgeDistribtionData();
    var entries = data.getEntriesList();

    assertEquals(3, entries.size());
    var entry18 = entries.get(0);
    var entryOther = entries.get(1);
    var entry60 = entries.get(2);

    assertEquals(1, entry18.getValue());
    assertEquals(2, entryOther.getValue());
    assertEquals(1, entry60.getValue());
    /*SOL end e2eGUI2*/
  }

  @Test
  void testRegister() {
    getClients(0).activate();

    /* ADD YOUR SOLUTION HERE:
     * - Instantiate a new CustomerCreate (don't forget to call init() on it!)
     * - Create a new customer with it
     * - Switch to the backend context (getBackend().activate())
     * - Assert that your customer can be found in CarrentalManager.getCustomerList()
     */

    /*SOL start e2eGUI3*/
    var customerOverview = new CustomerOverview();
    customerOverview.init();

    var customerCreate = new CustomerCreate();
    customerCreate.init();

    var name = "Eugene Engel";
    var email = "eugene.engel@rwth-aachen.de";
    var age = 32;

    customerCreate.setNameEntry(name);
    customerCreate.setEmailEntry(email);
    customerCreate.setAgeEntry(String.valueOf(age));
    customerCreate.registerLeftClick(null);

    getBackend().activate();

    boolean foundNewUser = false;
    for (Customer customer : CarrentalManager.getCustomerList()) {
      if((customer.isBuildFinished())
      && (name.equals(customer.getName()))
      && (email.equals(customer.getEmail()))
      && (age == customer.getAge())){
        foundNewUser = true;
      }
    }

    assertTrue(foundNewUser, "Can not find new user!");

    var otherEntry = customerOverview.getAgeDistribtionData().getEntries(1);
    assertEquals(3, otherEntry.getValue());
    /*SOL end e2eGUI3*/
  }
}
