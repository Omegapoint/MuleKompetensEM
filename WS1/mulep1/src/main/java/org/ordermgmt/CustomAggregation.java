package org.ordermgmt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.mule.api.MuleContext;
import org.mule.routing.AbstractAggregator;
import org.mule.routing.correlation.EventCorrelatorCallback;

public class CustomAggregation extends AbstractAggregator{
	
	public String processMessage(Collection c) {
		String output = "";
		
		if (c == null || c.size() == 0) 
			return output;
		else if(c.size() > 0) {
			Object[] elements = c.toArray();
			/*
			 * search for matching correlation id withing messages. i guess return the message 
			 * */
			for(Object element : elements) {
				output = element.toString();
			}
		}
		return output;
	}

	@Override
	protected EventCorrelatorCallback getCorrelatorCallback(
			MuleContext muleContext) {
		// TODO Auto-generated method stub
		return null;
	}
	

	
}
/*
public class MyService {
	 
	public String processMessage(Collection c) {
		Object[] elements = c.toArray();
		return "Received Collection containing " + c.size()
			+ " elements which are \"" + elements[0] + "\" 
			and \"" + elements[1] + "\"";
	}
}
*/