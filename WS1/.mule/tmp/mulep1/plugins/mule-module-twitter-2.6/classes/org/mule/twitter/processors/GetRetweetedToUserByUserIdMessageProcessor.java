
package org.mule.twitter.processors;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.mule.api.MessagingException;
import org.mule.api.MuleContext;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.api.construct.FlowConstruct;
import org.mule.api.construct.FlowConstructAware;
import org.mule.api.context.MuleContextAware;
import org.mule.api.expression.ExpressionManager;
import org.mule.api.lifecycle.Disposable;
import org.mule.api.lifecycle.Initialisable;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.lifecycle.Startable;
import org.mule.api.lifecycle.Stoppable;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.registry.RegistrationException;
import org.mule.api.transformer.DataType;
import org.mule.api.transformer.Transformer;
import org.mule.api.transformer.TransformerException;
import org.mule.config.i18n.CoreMessages;
import org.mule.config.i18n.MessageFactory;
import org.mule.transformer.TransformerTemplate;
import org.mule.transformer.types.DataTypeFactory;
import org.mule.transport.NullPayload;
import org.mule.twitter.TwitterConnector;
import org.mule.twitter.adapters.TwitterConnectorLifecycleAdapter;
import org.mule.util.TemplateParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * GetRetweetedToUserByUserIdMessageProcessor invokes the {@link org.mule.twitter.TwitterConnector#getRetweetedToUserByUserId(long, int, int, int)} method in {@link TwitterConnector }. For each argument there is a field in this processor to match it.  Before invoking the actual method the processor will evaluate and transform where possible to the expected argument type.
 * 
 */
public class GetRetweetedToUserByUserIdMessageProcessor
    implements FlowConstructAware, MuleContextAware, Disposable, Initialisable, Startable, Stoppable, MessageProcessor
{

    private Object userId;
    private long _userIdType;
    private Object page;
    private int _pageType;
    private Object count;
    private int _countType;
    private Object sinceId;
    private int _sinceIdType;
    private static Logger logger = LoggerFactory.getLogger(GetRetweetedToUserByUserIdMessageProcessor.class);
    /**
     * Module object
     * 
     */
    private Object moduleObject;
    /**
     * Mule Context
     * 
     */
    private MuleContext muleContext;
    /**
     * Mule Expression Manager
     * 
     */
    private ExpressionManager expressionManager;
    /**
     * Mule Pattern Info
     * 
     */
    private TemplateParser.PatternInfo patternInfo;
    /**
     * Flow construct
     * 
     */
    private FlowConstruct flowConstruct;
    /**
     * Variable used to track how many retries we have attempted on this message processor
     * 
     */
    private AtomicInteger retryCount;
    /**
     * Maximum number of retries that can be attempted.
     * 
     */
    private int retryMax;

    /**
     * Obtains the expression manager from the Mule context and initialises the connector. If a target object  has not been set already it will search the Mule registry for a default one.
     * 
     * @throws InitialisationException
     */
    public void initialise()
        throws InitialisationException
    {
        retryCount = new AtomicInteger();
        expressionManager = muleContext.getExpressionManager();
        patternInfo = TemplateParser.createMuleStyleParser().getStyle();
        if (moduleObject == null) {
            try {
                moduleObject = muleContext.getRegistry().lookupObject(TwitterConnectorLifecycleAdapter.class);
                if (moduleObject == null) {
                    throw new InitialisationException(MessageFactory.createStaticMessage("Cannot find object"), this);
                }
            } catch (RegistrationException e) {
                throw new InitialisationException(CoreMessages.initialisationFailure("org.mule.twitter.adapters.TwitterConnectorLifecycleAdapter"), e, this);
            }
        }
        if (moduleObject instanceof String) {
            moduleObject = muleContext.getRegistry().lookupObject(((String) moduleObject));
            if (moduleObject == null) {
                throw new InitialisationException(MessageFactory.createStaticMessage("Cannot find object by config name"), this);
            }
        }
    }

    public void start()
        throws MuleException
    {
    }

    public void stop()
        throws MuleException
    {
    }

    public void dispose() {
    }

    /**
     * Set the Mule context
     * 
     * @param context Mule context to set
     */
    public void setMuleContext(MuleContext context) {
        this.muleContext = context;
    }

    /**
     * Sets flow construct
     * 
     * @param flowConstruct Flow construct to set
     */
    public void setFlowConstruct(FlowConstruct flowConstruct) {
        this.flowConstruct = flowConstruct;
    }

    /**
     * Sets the instance of the object under which the processor will execute
     * 
     * @param moduleObject Instace of the module
     */
    public void setModuleObject(Object moduleObject) {
        this.moduleObject = moduleObject;
    }

    /**
     * Sets retryMax
     * 
     * @param value Value to set
     */
    public void setRetryMax(int value) {
        this.retryMax = value;
    }

    /**
     * Sets sinceId
     * 
     * @param value Value to set
     */
    public void setSinceId(Object value) {
        this.sinceId = value;
    }

    /**
     * Sets count
     * 
     * @param value Value to set
     */
    public void setCount(Object value) {
        this.count = value;
    }

    /**
     * Sets page
     * 
     * @param value Value to set
     */
    public void setPage(Object value) {
        this.page = value;
    }

    /**
     * Sets userId
     * 
     * @param value Value to set
     */
    public void setUserId(Object value) {
        this.userId = value;
    }

    /**
     * Get all superclasses and interfaces recursively.
     * 
     * @param classes List of classes to which to add all found super classes and interfaces.
     * @param clazz   The class to start the search with.
     */
    private void computeClassHierarchy(Class clazz, List classes) {
        for (Class current = clazz; (current!= null); current = current.getSuperclass()) {
            if (classes.contains(current)) {
                return ;
            }
            classes.add(current);
            for (Class currentInterface: current.getInterfaces()) {
                computeClassHierarchy(currentInterface, classes);
            }
        }
    }

    /**
     * Checks whether the specified class parameter is an instance of {@link List }
     * 
     * @param clazz <code>Class</code> to check.
     * @return
     */
    private boolean isListClass(Class clazz) {
        List<Class> classes = new ArrayList<Class>();
        computeClassHierarchy(clazz, classes);
        return classes.contains(List.class);
    }

    /**
     * Checks whether the specified class parameter is an instance of {@link Map }
     * 
     * @param clazz <code>Class</code> to check.
     * @return
     */
    private boolean isMapClass(Class clazz) {
        List<Class> classes = new ArrayList<Class>();
        computeClassHierarchy(clazz, classes);
        return classes.contains(Map.class);
    }

    private boolean isList(Type type) {
        if ((type instanceof Class)&&isListClass(((Class) type))) {
            return true;
        }
        if (type instanceof ParameterizedType) {
            return isList(((ParameterizedType) type).getRawType());
        }
        if (type instanceof WildcardType) {
            Type[] upperBounds = ((WildcardType) type).getUpperBounds();
            return ((upperBounds.length!= 0)&&isList(upperBounds[ 0 ]));
        }
        return false;
    }

    private boolean isMap(Type type) {
        if ((type instanceof Class)&&isMapClass(((Class) type))) {
            return true;
        }
        if (type instanceof ParameterizedType) {
            return isMap(((ParameterizedType) type).getRawType());
        }
        if (type instanceof WildcardType) {
            Type[] upperBounds = ((WildcardType) type).getUpperBounds();
            return ((upperBounds.length!= 0)&&isMap(upperBounds[ 0 ]));
        }
        return false;
    }

    private boolean isAssignableFrom(Type expectedType, Class clazz) {
        if (expectedType instanceof Class) {
            if (((Class) expectedType).isPrimitive()) {
                if (((Class) expectedType).getName().equals("boolean")&&(clazz == Boolean.class)) {
                    return true;
                }
                if (((Class) expectedType).getName().equals("byte")&&(clazz == Byte.class)) {
                    return true;
                }
                if (((Class) expectedType).getName().equals("short")&&(clazz == Short.class)) {
                    return true;
                }
                if (((Class) expectedType).getName().equals("char")&&(clazz == Character.class)) {
                    return true;
                }
                if (((Class) expectedType).getName().equals("int")&&(clazz == Integer.class)) {
                    return true;
                }
                if (((Class) expectedType).getName().equals("float")&&(clazz == Float.class)) {
                    return true;
                }
                if (((Class) expectedType).getName().equals("long")&&(clazz == Long.class)) {
                    return true;
                }
                if (((Class) expectedType).getName().equals("double")&&(clazz == Double.class)) {
                    return true;
                }
                return false;
            } else {
                return ((Class) expectedType).isAssignableFrom(clazz);
            }
        }
        if (expectedType instanceof ParameterizedType) {
            return isAssignableFrom(((ParameterizedType) expectedType).getRawType(), clazz);
        }
        if (expectedType instanceof WildcardType) {
            Type[] upperBounds = ((WildcardType) expectedType).getUpperBounds();
            if (upperBounds.length!= 0) {
                return isAssignableFrom(upperBounds[ 0 ], clazz);
            }
        }
        return false;
    }

    private Object evaluate(MuleMessage muleMessage, Object source) {
        if (source instanceof String) {
            String stringSource = ((String) source);
            if (stringSource.startsWith(patternInfo.getPrefix())&&stringSource.endsWith(patternInfo.getSuffix())) {
                return expressionManager.evaluate(stringSource, muleMessage);
            } else {
                return expressionManager.parse(stringSource, muleMessage);
            }
        }
        return source;
    }

    private Object evaluateAndTransform(MuleMessage muleMessage, Type expectedType, String expectedMimeType, Object source)
        throws TransformerException
    {
        if (source == null) {
            return source;
        }
        Object target = null;
        if (isList(source.getClass())) {
            if (isList(expectedType)) {
                List newList = new ArrayList();
                Type valueType = ((ParameterizedType) expectedType).getActualTypeArguments()[ 0 ];
                ListIterator iterator = ((List) source).listIterator();
                while (iterator.hasNext()) {
                    Object subTarget = iterator.next();
                    newList.add(evaluateAndTransform(muleMessage, valueType, expectedMimeType, subTarget));
                }
                target = newList;
            } else {
                target = source;
            }
        } else {
            if (isMap(source.getClass())) {
                if (isMap(expectedType)) {
                    Type keyType = Object.class;
                    Type valueType = Object.class;
                    if (expectedType instanceof ParameterizedType) {
                        keyType = ((ParameterizedType) expectedType).getActualTypeArguments()[ 0 ];
                        valueType = ((ParameterizedType) expectedType).getActualTypeArguments()[ 1 ];
                    }
                    Map map = ((Map) source);
                    Map newMap = new HashMap();
                    for (Object entryObj: map.entrySet()) {
                        {
                            Map.Entry entry = ((Map.Entry) entryObj);
                            Object newKey = evaluateAndTransform(muleMessage, keyType, expectedMimeType, entry.getKey());
                            Object newValue = evaluateAndTransform(muleMessage, valueType, expectedMimeType, entry.getValue());
                            newMap.put(newKey, newValue);
                        }
                    }
                    target = newMap;
                } else {
                    target = source;
                }
            } else {
                target = evaluate(muleMessage, source);
            }
        }
        if ((target!= null)&&(!isAssignableFrom(expectedType, target.getClass()))) {
            DataType sourceDataType = DataTypeFactory.create(target.getClass());
            DataType targetDataType = null;
            if (expectedMimeType!= null) {
                targetDataType = DataTypeFactory.create(((Class) expectedType), expectedMimeType);
            } else {
                targetDataType = DataTypeFactory.create(((Class) expectedType));
            }
            Transformer t = muleContext.getRegistry().lookupTransformer(sourceDataType, targetDataType);
            return t.transform(target);
        } else {
            return target;
        }
    }

    /**
     * Invokes the MessageProcessor.
     * 
     * @param event MuleEvent to be processed
     * @throws MuleException
     */
    public MuleEvent process(MuleEvent event)
        throws MuleException
    {
        MuleMessage _muleMessage = event.getMessage();
        TwitterConnectorLifecycleAdapter _castedModuleObject = null;
        if (moduleObject instanceof String) {
            _castedModuleObject = ((TwitterConnectorLifecycleAdapter) muleContext.getRegistry().lookupObject(((String) moduleObject)));
            if (_castedModuleObject == null) {
                throw new MessagingException(CoreMessages.failedToCreate("getRetweetedToUserByUserId"), event, new RuntimeException("Cannot find the configuration specified by the config-ref attribute."));
            }
        } else {
            _castedModuleObject = ((TwitterConnectorLifecycleAdapter) moduleObject);
        }
        try {
            Long _transformedUserId = ((Long) evaluateAndTransform(_muleMessage, GetRetweetedToUserByUserIdMessageProcessor.class.getDeclaredField("_userIdType").getGenericType(), null, userId));
            Integer _transformedPage = ((Integer) evaluateAndTransform(_muleMessage, GetRetweetedToUserByUserIdMessageProcessor.class.getDeclaredField("_pageType").getGenericType(), null, page));
            Integer _transformedCount = ((Integer) evaluateAndTransform(_muleMessage, GetRetweetedToUserByUserIdMessageProcessor.class.getDeclaredField("_countType").getGenericType(), null, count));
            Integer _transformedSinceId = ((Integer) evaluateAndTransform(_muleMessage, GetRetweetedToUserByUserIdMessageProcessor.class.getDeclaredField("_sinceIdType").getGenericType(), null, sinceId));
            retryCount.getAndIncrement();
            Object resultPayload;
            resultPayload = _castedModuleObject.getRetweetedToUserByUserId(_transformedUserId, _transformedPage, _transformedCount, _transformedSinceId);
            TransformerTemplate.OverwitePayloadCallback overwritePayloadCallback = null;
            if (resultPayload == null) {
                overwritePayloadCallback = new TransformerTemplate.OverwitePayloadCallback(NullPayload.getInstance());
            } else {
                overwritePayloadCallback = new TransformerTemplate.OverwitePayloadCallback(resultPayload);
            }
            List<Transformer> transformerList;
            transformerList = new ArrayList<Transformer>();
            transformerList.add(new TransformerTemplate(overwritePayloadCallback));
            event.getMessage().applyTransformers(event, transformerList);
            retryCount.set(0);
            return event;
        } catch (Exception e) {
            throw new MessagingException(CoreMessages.failedToInvoke("getRetweetedToUserByUserId"), event, e);
        }
    }

}
