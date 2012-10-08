
package org.mule.twitter.adapters;

import org.mule.api.Capabilities;
import org.mule.api.Capability;


/**
 * A <code>TwitterConnectorCapabilitiesAdapter</code> is a wrapper around {@link org.mule.twitter.TwitterConnector } that implements {@link org.mule.api.Capabilities} interface.
 * 
 */
public class TwitterConnectorCapabilitiesAdapter
    extends org.mule.twitter.TwitterConnector
    implements Capabilities
{


    /**
     * Returns true if this module implements such capability
     * 
     */
    public boolean isCapableOf(Capability capability) {
        if (capability == Capability.LIFECYCLE_CAPABLE) {
            return true;
        }
        return false;
    }

}
