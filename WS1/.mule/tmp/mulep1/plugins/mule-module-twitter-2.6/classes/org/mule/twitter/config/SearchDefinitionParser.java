
package org.mule.twitter.config;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.mule.config.spring.MuleHierarchicalBeanDefinitionParserDelegate;
import org.mule.config.spring.util.SpringXMLUtils;
import org.mule.twitter.processors.SearchMessageProcessor;
import org.mule.util.TemplateParser;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public class SearchDefinitionParser
    implements BeanDefinitionParser
{

    /**
     * Mule Pattern Info
     * 
     */
    private TemplateParser.PatternInfo patternInfo;

    public SearchDefinitionParser() {
        patternInfo = TemplateParser.createMuleStyleParser().getStyle();
    }

    public BeanDefinition parse(Element element, ParserContext parserContent) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(SearchMessageProcessor.class.getName());
        String configRef = element.getAttribute("config-ref");
        if ((configRef!= null)&&(!StringUtils.isBlank(configRef))) {
            builder.addPropertyValue("moduleObject", configRef);
        }
        if ((element.getAttribute("query")!= null)&&(!StringUtils.isBlank(element.getAttribute("query")))) {
            builder.addPropertyValue("query", element.getAttribute("query"));
        }
        if ((element.getAttribute("lang")!= null)&&(!StringUtils.isBlank(element.getAttribute("lang")))) {
            builder.addPropertyValue("lang", element.getAttribute("lang"));
        }
        if ((element.getAttribute("locale")!= null)&&(!StringUtils.isBlank(element.getAttribute("locale")))) {
            builder.addPropertyValue("locale", element.getAttribute("locale"));
        }
        if ((element.getAttribute("maxId")!= null)&&(!StringUtils.isBlank(element.getAttribute("maxId")))) {
            builder.addPropertyValue("maxId", element.getAttribute("maxId"));
        }
        if ((element.getAttribute("rpp")!= null)&&(!StringUtils.isBlank(element.getAttribute("rpp")))) {
            builder.addPropertyValue("rpp", element.getAttribute("rpp"));
        }
        if ((element.getAttribute("page")!= null)&&(!StringUtils.isBlank(element.getAttribute("page")))) {
            builder.addPropertyValue("page", element.getAttribute("page"));
        }
        if ((element.getAttribute("since")!= null)&&(!StringUtils.isBlank(element.getAttribute("since")))) {
            builder.addPropertyValue("since", element.getAttribute("since"));
        }
        if ((element.getAttribute("sinceId")!= null)&&(!StringUtils.isBlank(element.getAttribute("sinceId")))) {
            builder.addPropertyValue("sinceId", element.getAttribute("sinceId"));
        }
        if ((element.getAttribute("geocode")!= null)&&(!StringUtils.isBlank(element.getAttribute("geocode")))) {
            builder.addPropertyValue("geocode", element.getAttribute("geocode"));
        }
        if ((element.getAttribute("radius")!= null)&&(!StringUtils.isBlank(element.getAttribute("radius")))) {
            builder.addPropertyValue("radius", element.getAttribute("radius"));
        }
        if ((element.getAttribute("unit")!= null)&&(!StringUtils.isBlank(element.getAttribute("unit")))) {
            builder.addPropertyValue("unit", element.getAttribute("unit"));
        }
        if ((element.getAttribute("until")!= null)&&(!StringUtils.isBlank(element.getAttribute("until")))) {
            builder.addPropertyValue("until", element.getAttribute("until"));
        }
        if ((element.getAttribute("resultType")!= null)&&(!StringUtils.isBlank(element.getAttribute("resultType")))) {
            builder.addPropertyValue("resultType", element.getAttribute("resultType"));
        }
        BeanDefinition definition = builder.getBeanDefinition();
        definition.setAttribute(MuleHierarchicalBeanDefinitionParserDelegate.MULE_NO_RECURSE, Boolean.TRUE);
        MutablePropertyValues propertyValues = parserContent.getContainingBeanDefinition().getPropertyValues();
        if (parserContent.getContainingBeanDefinition().getBeanClassName().equals("org.mule.config.spring.factories.PollingMessageSourceFactoryBean")) {
            propertyValues.addPropertyValue("messageProcessor", definition);
        } else {
            if (parserContent.getContainingBeanDefinition().getBeanClassName().equals("org.mule.enricher.MessageEnricher")) {
                propertyValues.addPropertyValue("enrichmentMessageProcessor", definition);
            } else {
                PropertyValue messageProcessors = propertyValues.getPropertyValue("messageProcessors");
                if ((messageProcessors == null)||(messageProcessors.getValue() == null)) {
                    propertyValues.addPropertyValue("messageProcessors", new ManagedList());
                }
                List listMessageProcessors = ((List) propertyValues.getPropertyValue("messageProcessors").getValue());
                listMessageProcessors.add(definition);
            }
        }
        return definition;
    }

    protected String getAttributeValue(Element element, String attributeName) {
        if (!StringUtils.isEmpty(element.getAttribute(attributeName))) {
            return element.getAttribute(attributeName);
        }
        return null;
    }

    private String generateChildBeanName(Element element) {
        String id = SpringXMLUtils.getNameOrId(element);
        if (StringUtils.isBlank(id)) {
            String parentId = SpringXMLUtils.getNameOrId(((Element) element.getParentNode()));
            return ((("."+ parentId)+":")+ element.getLocalName());
        } else {
            return id;
        }
    }

}
