package JUnitTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
	AvailabilityDatabaseTest.class,
	BookingTest.class,
	CalendarTest.class,
	CompanyDatabaseTest.class,
	CompanyTest.class,
	CustomerDatabaseTest.class,
	CustomerTest.class,
	EmployeeTest.class,
	MenuTest.class,
})

public class TestSuite {

}
