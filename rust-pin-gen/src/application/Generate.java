package application;

import org.apache.commons.lang3.RandomStringUtils;

public class Generate
{
	public static String genPin()
	{
		return RandomStringUtils.randomNumeric(4);
	}
}
