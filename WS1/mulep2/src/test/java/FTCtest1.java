import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.mule.api.*;
import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.FunctionalTestCase;
//import org.mule.tck.FunctionalTestCase;

public class FTCtest1  extends FunctionalTestCase
{
    @Override
    protected String getConfigResources()
    {
    	return "C:\\MuleStudio\\WS1\\.mule\\apps\\mulep2\\clientInfo.xml";
    }
    
      public void testValidJob3() throws Exception
      {
      	
        MuleClient client = new MuleClient(muleContext);
        client.dispatch("vm://work.new", "<?xml version=\"1.0\" encoding=\"UTF-8\"?><client><person><name>sven</name></person><wallet><saldo>45</saldo></wallet></client>", null);
        MuleMessage result = client.request("vm://work.result2", 20000);
   
        System.out.println("result:\r\n" + result.getPayloadAsString());

        assertTrue(result.getPayloadAsString().contains("job"));
        assertTrue(result.getPayloadAsString().contains("accountType"));
        assertTrue(result.getPayloadAsString().contains("client"));
     
    }
}
