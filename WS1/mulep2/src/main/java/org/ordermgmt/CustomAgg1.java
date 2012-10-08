package org.ordermgmt;

import java.util.Comparator;

import org.mule.api.MuleContext;
import org.mule.api.MuleEvent;
import org.mule.routing.AbstractAggregator;
import org.mule.routing.AggregationException;
import org.mule.routing.EventGroup;
import org.mule.routing.correlation.CollectionCorrelatorCallback;
import org.mule.routing.correlation.CorrelationSequenceComparator;
import org.mule.routing.correlation.EventCorrelatorCallback;

public class CustomAgg1 extends AbstractAggregator {

	
	 protected Comparator eventComparator;

	    public CustomAgg1()
	    {
	        super();
	        eventComparator = new CorrelationSequenceComparator();
	    }
	
	@Override
	protected EventCorrelatorCallback getCorrelatorCallback(
			MuleContext muleContext) {

	        return new CollectionCorrelatorCallback(muleContext, persistentStores, storePrefix)
	        {
	            /**
	             * This method is invoked if the shouldAggregate method is called and
	             * returns true. Once this method returns an aggregated message the event
	             * group is removed from the router
	             * 
	             * @param events the event group for this request
	             * @return an aggregated message
	             * @throws org.mule.routing.AggregationException if the aggregation
	             *             fails. in this scenario the whole event group is removed
	             *             and passed to the exception handler for this componenet
	             */
	            @Override
	            public MuleEvent aggregateEvents(EventGroup events) throws AggregationException
	            {
	            	return null;
	            }

	};
}
}


