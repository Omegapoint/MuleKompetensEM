package org.ordermgmt;

import java.util.Arrays;
import java.util.List;

public class DummyListCreator {

	public java.util.Collection processMessage(String s) {
		String[] arr = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", 
          "July", "Aug", "Sep", "Oct", "Nov", "Dec"};
		List list = Arrays.asList(arr);
		return list;
	}
}
