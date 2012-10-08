import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.mule.api.*;
import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.FunctionalTestCase;
//import org.mule.tck.FunctionalTestCase;

public class FTC1  extends FunctionalTestCase
{
    @Override
    protected String getConfigResources()
    {
      //return "mule-workmanager-config.xml,mule-test-transports-config.xml";
    	return "C:\\MuleStudio\\WS1\\.mule\\apps\\mulep1\\wa.xml, C:\\MuleStudio\\WS1\\.mule\\apps\\mulep1\\mttc.xml";
    }
    
    /*
    public void testValidJob() throws Exception
    {
      MuleClient client = new MuleClient(muleContext);
      MuleMessage result = client.send("vm://work.new", "<valid_xml />", null);
      //assertTrue(result.getPayloadAsString().startsWith("OK:"));

      MuleMessage dispatched = client.request("vm://work.ok", 5000L);
      assertEquals("<valid_xml />", dispatched.getPayloadAsString());
      
      //MuleMessage dispatched2 = client.request("vm://work.second_ok", 5000L);
      //assertEquals("<valid_xml />", dispatched2.getPayloadAsString());
    }

    //@Test(expected=FileNotFoundException.class)
    public void testInvalidJob() throws Exception
    {
      MuleClient client = new MuleClient(muleContext);
      MuleMessage result = client.send("vm://work.new", "not_xml", null);
      //assertTrue(result.getPayloadAsString().startsWith("NOT_XML"));

      MuleMessage dispatched = client.request("vm://work.ok", 5000L);
      System.out.println(dispatched);
      assertNull(dispatched);
    }
    
    //@Test(expected=FileNotFoundException.class)
    public void testInvalidJob2() throws Exception
    {
      MuleClient client = new MuleClient(muleContext);
      MuleMessage result = client.send("vm://work.new", "not_xml", null);
      //assertTrue(result.getPayloadAsString().startsWith("NOT_XML"));

      MuleMessage dispatched = client.request("vm://work.ok2", 5000L);
      System.out.println(dispatched);
      assertNull(dispatched);
    }*/
    
    public void testValidXMLJob() throws Exception
    {
      MuleClient client = new MuleClient(muleContext);
      MuleMessage result = client.send("vm://work.new", "<valid_xml />", null);
      //assertTrue(result.getPayloadAsString().startsWith("OK:"));

      MuleMessage dispatched = client.request("vm://work.ok3", 5000L);
      //assertEquals("<valid_xml />", dispatched.getPayloadAsString());
      System.out.println(dispatched.getPayloadAsString());
      System.out.println(dispatched.getCorrelationId());
      //System.out.println("<" + dispatched.toString() + ">");
      assertTrue(dispatched.getPayloadAsString().contains("Jan"));
      
      dispatched = client.request("vm://work.ok3", 5000L);
      //assertEquals("<valid_xml />", dispatched.getPayloadAsString());
      System.out.println(dispatched.getPayloadAsString());
      System.out.println(dispatched.getCorrelationId());
      //System.out.println("<" + dispatched.toString() + ">");
      assertTrue(dispatched.getPayloadAsString().contains("Jan"));
      
      //MuleMessage dispatched2 = client.request("vm://work.second_ok", 5000L);
      //assertEquals("<valid_xml />", dispatched2.getPayloadAsString());
    }

}