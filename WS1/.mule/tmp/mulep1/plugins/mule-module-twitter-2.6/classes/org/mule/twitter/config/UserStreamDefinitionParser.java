
package org.mule.twitter.config;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.mule.config.spring.MuleHierarchicalBeanDefinitionParserDelegate;
import org.mule.config.spring.util.SpringXMLUtils;
import org.mule.twitter.sources.UserStreamMessageSource;
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

public class UserStreamDefinitionParser
    implements BeanDefinitionParser
{

    /**
     * Mule Pattern Info
     * 
     */
    private TemplateParser.PatternInfo patternInfo;

    public UserStreamDefinitionParser() {
        patternInfo = TemplateParser.createMuleStyleParser().getStyle();
    }

    public BeanDefinition parse(Element element, ParserContext parserContent) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(UserStreamMessageSource.class.getName());
        String configRef = element.getAttribute("config-ref");
        if ((configRef!= null)&&(!StringUtils.isBlank(configRef))) {
            builder.addPropertyValue("moduleObject", configRef);
        }
        Element keywordsListElement = null;
        keywordsListElement = DomUtils.getChildElementByTagName(element, "keywords");
        List<Element> keywordsListChilds = null;
        if (keywordsListElement!= null) {
            String keywordsRef = keywordsListElement.getAttribute("ref");
            if ((keywordsRef!= null)&&(!StringUtils.isBlank(keywordsRef))) {
                if ((!keywordsRef.startsWith(patternInfo.getPrefix()))&&(!keywordsRef.endsWith(patternInfo.getSuffix()))) {
                    builder.addPropertyValue("keywords", new RuntimeBeanReference(keywordsRef));
                } else {
                    builder.addPropertyValue("keywords", keywordsRef);
                }
            } else {
                ManagedList keywords = new ManagedList();
                keywordsListChilds = DomUtils.getChildElementsByTagName(keywordsListElement, "keyword");
                if (keywordsListChilds!= null) {
                    for (Element keywordsChild: keywordsListChilds) {
                        String valueRef = keywordsChild.getAttribute("value-ref");
                        if ((valueRef!= null)&&(!StringUtils.isBlank(valueRef))) {
                            if ((!valueRef.startsWith(patternInfo.getPrefix()))&&(!valueRef.endsWith(patternInfo.getSuffix()))) {
                                keywords.add(new RuntimeBeanReference(valueRef));
                            } else {
                                keywords.add(valueRef);
                            }
                        } else {
                            keywords.add(keywordsChild.getTextContent());
                        }
                    }
                }
                builder.addPropertyValue("keywords", keywords);
            }
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
