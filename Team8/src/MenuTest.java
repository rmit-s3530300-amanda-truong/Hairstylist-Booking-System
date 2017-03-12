import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class MenuTest {
	Menu m1 = new Menu();
	String[] actualArray = m1.registerCustomer();
	
	@Test
	public void TestRegisterCustomer() {
		String[] expectedArray = {"cust0002", "john", "snow"};
		assertEquals(expectedArray, actualArray);
	}
}
