package CSC346HW1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CSC346HW1_JUnit {
	static String allowedSpecials = "!@#$%^&*()_+-=,./[]{}|;:\\\"\'<>?";
	//s[1] has 30 empty spaces between the Asd, and s[2] has 30 special characters between the Asd's
	//three special chars, " and ' and \, are escaped with \, which aren't actually interpreted in functions like length()
	static String s[] = {	" Asd                              Asd \t\n\r\0",
							" asd!@#$%^&*()_+-=,./[]{}|;:\\\"\'<>?asd ",
							"AsdAsd",
							"asdasdasdasdasd",	//standardized length strings with varying combinations of lowercase
							"ASDASDASDASDASD",	//uppercase, numbers, and special characters here
							"asdasd123asd123",
							"ASDASD123ASD123",
							"AsdAsd123Asd123",
							"AsdAsd123!@#$%^",
							"As1!@#$%^&*()!@"
						};
	static String numbers = "0123456789";
	static String lowerCase = "abcdefghijklmnopqrstuvwxyz";
	static String upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	@Test
	void countSpecial () {
		assertEquals(CSC346HW1.countSpecial(s[0], allowedSpecials), 0);
		assertEquals(CSC346HW1.countSpecial(s[1], allowedSpecials), allowedSpecials.length());
	}
	@Test
	void hasDigit() {
		assertTrue(CSC346HW1.hasDigit(upperCase+lowerCase+numbers));
		assertFalse(CSC346HW1.hasDigit(upperCase+lowerCase));
		assertTrue(CSC346HW1.hasDigit(upperCase+numbers));
		assertFalse(CSC346HW1.hasDigit(upperCase));
		assertFalse(CSC346HW1.hasDigit(lowerCase));
		assertTrue(CSC346HW1.hasDigit(lowerCase+numbers));
		assertTrue(CSC346HW1.hasDigit(numbers));
	}
	@Test
	void hasUpperCase() {
		assertTrue(CSC346HW1.hasUpperCase(upperCase+lowerCase+numbers));
		assertTrue(CSC346HW1.hasUpperCase(upperCase+lowerCase));
		assertTrue(CSC346HW1.hasUpperCase(upperCase+numbers));
		assertTrue(CSC346HW1.hasUpperCase(upperCase));
		assertFalse(CSC346HW1.hasUpperCase(lowerCase));
		assertFalse(CSC346HW1.hasUpperCase(lowerCase+numbers));
		assertFalse(CSC346HW1.hasUpperCase(numbers));
	}
	@Test
	void hasLowerCase() {
		assertTrue(CSC346HW1.hasLowerCase(upperCase+lowerCase+numbers));
		assertTrue(CSC346HW1.hasLowerCase(upperCase+lowerCase));
		assertFalse(CSC346HW1.hasLowerCase(upperCase+numbers));
		assertFalse(CSC346HW1.hasLowerCase(upperCase));
		assertTrue(CSC346HW1.hasLowerCase(lowerCase));
		assertTrue(CSC346HW1.hasLowerCase(lowerCase+numbers));
		assertFalse(CSC346HW1.hasLowerCase(numbers));
	}
	@Test
	void trimmedLength() {
		assertEquals( s[0].trim().length(), CSC346HW1.trimmedLength(s[0]));
		assertEquals( s[0].substring(0, 19).trim().length() + s[0].substring(19, s[0].length()).trim().length(), CSC346HW1.trimmedLength(s[2]));
	}
	@Test
	void truncate() {
		int n=20;
		for(int i=0; i<s.length; i++) {
			if(s[i].length()>n) {
				assertTrue(CSC346HW1.truncate(s[i], n).length() == n+"...".length());
			} else {
				assertTrue(CSC346HW1.truncate(s[i], n).length() == s[i].length());
			}
		}
	}
	
	@Test
	void log2() {
		Long j = (long)2;
		for(Long i = (long)1;i<25; i++) {
			assertEquals(CSC346HW1.log2(j), (long)i);
			j*=2;
		}
	}
	@Test
	void calculateEntropy() {
		for(int i=0; i<2;i++) {
			assertNotEquals(CSC346HW1.calculateEntropy(s[i], allowedSpecials), Long.valueOf(0));
		}
		for(int i=3; i<5;i++) {
			Long a = CSC346HW1.calculateEntropy(s[i], allowedSpecials);
			Long b = CSC346HW1.calculateEntropy(s[i+1], allowedSpecials);
			assertEquals(a, b);
		}
		for(int i=7; i<s.length-1;i++) {
			Long a = CSC346HW1.calculateEntropy(s[i], allowedSpecials);
			Long b = CSC346HW1.calculateEntropy(s[i+1], allowedSpecials);
			assertTrue(a <= b);
		}
	}
	@Test
	void evaluateEntropy() {
		for(int i=3; i<s.length; i++) {
			Long entropy = CSC346HW1.calculateEntropy(s[i], allowedSpecials);
			assertNotEquals(CSC346HW1.evaluateEntropy(entropy), "");
		}
	}

}
