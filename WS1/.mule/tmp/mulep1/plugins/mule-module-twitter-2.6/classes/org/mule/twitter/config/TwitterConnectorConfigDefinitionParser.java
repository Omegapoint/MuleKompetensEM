
package org.mule.twitter.config;

import org.apache.commons.lang.StringUtils;
import org.mule.api.lifecycle.Disposable;
import org.mule.api.lifecycle.Initialisable;
import org.mule.config.spring.MuleHierarchicalBeanDefinitionParserDelegate;
import org.mule.config.spring.parsers.generic.AutoIdUtils;
import org.mule.twitter.adapters.TwitterConnectorLifecycleAdapter;
import org.mule.util.TemplateParser;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public class TwitterConnectorConfigDefinitionParser
    implements BeanDefinitionParser
{

    /**
     * Mule Pattern Info
     * 
     */
    private TemplateParser.PatternInfo patternInfo;

    public TwitterConnectorConfigDefinitionParser() {
        patternInfo = TemplateParser.createMuleStyleParser().getStyle();
    }

    public BeanDefinition parse(Element element, ParserContext parserContent) {
        String name = element.getAttribute("name");
        if ((name == null)||StringUtils.isBlank(name)) {
            element.setAttribute("name", AutoIdUtils.getUniqueName(element, "mule-bean"));
        }
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(TwitterConnectorLifecycleAdapter.class.getName());
        if (Initialisable.class.isAssignableFrom(TwitterConnectorLifecycleAdapter.class)) {
            builder.setInitMethodName(Initialisable.PHASE_NAME);
        }
        if (Disposable.class.isAssignableFrom(TwitterConnectorLifecycleAdapter.class)) {
            builder.setDestroyMethodName(Disposable.PHASE_NAME);
        }
        if ((element.getAttribute("consumerKey")!= null)&&(!StringUtils.isBlank(element.getAttribute("consumerKey")))) {
            builder.addPropertyValue("consumerKey", element.getAttribute("consumerKey"));
        }
        if ((element.getAttribute("consumerSecret")!= null)&&(!StringUtils.isBlank(element.getAttribute("consumerSecret")))) {
            builder.addPropertyValue("consumerSecret", element.getAttribute("consumerSecret"));
        }
        if ((element.getAttribute("accessKey")!= null)&&(!StringUtils.isBlank(element.getAttribute("accessKey")))) {
            builder.addPropertyValue("accessKey", element.getAttribute("accessKey"));
        }
        if ((element.getAttribute("accessSecret")!= null)&&(!StringUtils.isBlank(element.getAttribute("accessSecret")))) {
            builder.addPropertyValue("accessSecret", element.getAttribute("accessSecret"));
        }
        if ((element.getAttribute("useSSL")!= null)&&(!StringUtils.isBlank(element.getAttribute("useSSL")))) {
            builder.addPropertyValue("useSSL", element.getAttribute("useSSL"));
        }
        if ((element.getAttribute("proxyHost")!= null)&&(!StringUtils.isBlank(element.getAttribute("proxyHost")))) {
            builder.addPropertyValue("proxyHost", element.getAttribute("proxyHost"));
        }
        if ((element.getAttribute("proxyPort")!= null)&&(!StringUtils.isBlank(element.getAttribute("proxyPort")))) {
            builder.addPropertyValue("proxyPort", element.getAttribute("proxyPort"));
        }
        if ((element.getAttribute("proxyUsername")!= null)&&(!StringUtils.isBlank(element.getAttribute("proxyUsername")))) {
            builder.addPropertyValue("proxyUsername", element.getAttribute("proxyUsername"));
        }
        if ((element.getAttribute("proxyPassword")!= null)&&(!StringUtils.isBlank(element.getAttribute("proxyPassword")))) {
            builder.addPropertyValue("proxyPassword", element.getAttribute("proxyPassword"));
        }
        BeanDefinition definition = builder.getBeanDefinition();
        definition.setAttribute(MuleHierarchicalBeanDefinitionParserDelegate.MULE_NO_RECURSE, Boolean.TRUE);
        return definition;
    }

}
