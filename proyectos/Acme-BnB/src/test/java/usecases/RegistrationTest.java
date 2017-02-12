/*
 * BulletinServiceTest.java
 * 
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package usecases;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.CustomerService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class RegistrationTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private CustomerService	customerService;


	// Tests ------------------------------------------------------------------

	@Test
	public void driver() {
		Object testingData[][] = {
			{
				"customer1", 33, null
			}, {
				null, 33, IllegalArgumentException.class
			}, {
				"reviewer1", 33, IllegalArgumentException.class
			}, {
				"customer1", 34, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			template((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	// Ancillary methods ------------------------------------------------------

	protected void template(String username, int announcementId, Class<?> expectedThrowable) {
		Class<?> caughtThrowable;

		caughtThrowable = null;
		try {
			authenticate(username);
			customerService.registerPrincipal(announcementId);
			unauthenticate();
		} catch (Throwable oops) {
			caughtThrowable = oops.getClass();
		}

		if (expectedThrowable != null && caughtThrowable == null)
			throw new RuntimeException(expectedThrowable.getName() + " was expected");
		else if (expectedThrowable == null && caughtThrowable != null)
			throw new RuntimeException(caughtThrowable.getName() + " was unexpected");
		else if (expectedThrowable != null && caughtThrowable != null && !expectedThrowable.equals(caughtThrowable))
			throw new RuntimeException(expectedThrowable.getName() + " was expected, but " + caughtThrowable.getName() + "was thrown");
	}

}
