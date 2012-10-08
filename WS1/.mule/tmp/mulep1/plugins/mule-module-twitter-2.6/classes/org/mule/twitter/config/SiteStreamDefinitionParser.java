
package org.mule.twitter.config;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.mule.config.spring.MuleHierarchicalBeanDefinitionParserDelegate;
import org.mule.config.spring.util.SpringXMLUtils;
import org.mule.twitter.sources.SiteStreamMessageSource;
import org.mule.util.TemplateParser;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

public class SiteStreamDefinitionParser
    implements BeanDefinitionParser
{

    /**
     * Mule Pattern Info
     * 
     */
    private TemplateParser.PatternInfo patternInfo;

    public SiteStreamDefinitionParser() {
        patternInfo = TemplateParser.createMuleStyleParser().getStyle();
    }

    public BeanDefinition parse(Element element, ParserContext parserContent) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(SiteStreamMessageSource.class.getName());
        String configRef = element.getAttribute("config-ref");
        if ((configRef!= null)&&(!StringUtils.isBlank(configRef))) {
            builder.addPropertyValue("moduleObject", configRef);
        }
        Element userIdsListElement = null;
        userIdsListElement = DomUtils.getChildElementByTagName(element, "user-ids");
        List<Element> userIdsListChilds = null;
        if (userIdsListElement!= null) {
            String userIdsRef = userIdsListElement.getAttribute("ref");
            if ((userIdsRef!= null)&&(!StringUtils.isBlank(userIdsRef))) {
                if ((!userIdsRef.startsWith(patternInfo.getPrefix()))&&(!userIdsRef.endsWith(patternInfo.getSuffix()))) {
                    builder.addPropertyValue("userIds", new RuntimeBeanReference(userIdsRef));
                } else {
                    builder.addPropertyValue("userIds", userIdsRef);
                }
            } else {
                ManagedList userIds = new ManagedList();
                userIdsListChilds = DomUtils.getChildElementsByTagName(userIdsListElement, "user-id");
                if (userIdsListChilds!= null) {
                    for (Element userIdsChild: userIdsListChilds) {
                        String valueRef = userIdsChild.getAttribute("value-ref");
                        if ((valueRef!= null)&&(!StringUtils.isBlank(valueRef))) {
                            if ((!valueRef.startsWith(patternInfo.getPrefix()))&&(!valueRef.endsWith(patternInfo.getSuffix()))) {
                                userIds.add(new RuntimeBeanReference(valueRef));
                            } else {
                                userIds.add(valueRef);
                            }
                        } else {
                            userIds.add(userIdsChild.getTextContent());
                        }
                    }
                }
                builder.addPropertyValue("userIds", userIds);
            }
        }
        if ((element.getAttribute("withFollowings")!= null)&&(!StringUtils.isBlank(element.getAttribute("withFollowings")))) {
            builder.addPropertyValue("withFollowings", element.getAttribute("withFollowings"));
        }
        BeanDefinition definition = builder.getBeanDefinition();
        definition.setAttribute(MuleHierarchicalBeanDefinitionParserDelegate.MULE_NO_RECURSE, Boolean.TRUE);
        MutablePropertyValues propertyValues = parserContent.getContainingBeanDefinition().getPropertyValues();
        propertyValues.addPropertyValue("messageSource", definition);
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
