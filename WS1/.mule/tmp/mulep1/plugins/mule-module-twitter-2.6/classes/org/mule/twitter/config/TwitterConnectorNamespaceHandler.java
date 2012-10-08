
package org.mule.twitter.config;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;


/**
 * Registers bean definitions parsers for handling elements in <code>http://www.mulesoft.org/schema/mule/twitter</code>.
 * 
 */
public class TwitterConnectorNamespaceHandler
    extends NamespaceHandlerSupport
{


    /**
     * Invoked by the {@link DefaultBeanDefinitionDocumentReader} after construction but before any custom elements are parsed. 
     * @see NamespaceHandlerSupport#registerBeanDefinitionParser(String, BeanDefinitionParser)
     * 
     */
    public void init() {
        registerBeanDefinitionParser("config", new TwitterConnectorConfigDefinitionParser());
        registerBeanDefinitionParser("search", new SearchDefinitionParser());
        registerBeanDefinitionParser("get-public-timeline", new GetPublicTimelineDefinitionParser());
        registerBeanDefinitionParser("get-home-timeline", new GetHomeTimelineDefinitionParser());
        registerBeanDefinitionParser("get-user-timeline-by-screen-name", new GetUserTimelineByScreenNameDefinitionParser());
        registerBeanDefinitionParser("get-user-timeline-by-user-id", new GetUserTimelineByUserIdDefinitionParser());
        registerBeanDefinitionParser("get-user-timeline", new GetUserTimelineDefinitionParser());
        registerBeanDefinitionParser("get-mentions", new GetMentionsDefinitionParser());
        registerBeanDefinitionParser("get-retweeted-by-me", new GetRetweetedByMeDefinitionParser());
        registerBeanDefinitionParser("get-retweeted-to-me", new GetRetweetedToMeDefinitionParser());
        registerBeanDefinitionParser("get-retweets-of-me", new GetRetweetsOfMeDefinitionParser());
        registerBeanDefinitionParser("get-retweeted-to-user-by-screen-name", new GetRetweetedToUserByScreenNameDefinitionParser());
        registerBeanDefinitionParser("get-retweeted-to-user-by-user-id", new GetRetweetedToUserByUserIdDefinitionParser());
        registerBeanDefinitionParser("get-retweeted-by-user-by-screen-name", new GetRetweetedByUserByScreenNameDefinitionParser());
        registerBeanDefinitionParser("get-retweeted-by-user-by-user-id", new GetRetweetedByUserByUserIdDefinitionParser());
        registerBeanDefinitionParser("show-status", new ShowStatusDefinitionParser());
        registerBeanDefinitionParser("show-user", new ShowUserDefinitionParser());
        registerBeanDefinitionParser("update-status", new UpdateStatusDefinitionParser());
        registerBeanDefinitionParser("destroy-status", new DestroyStatusDefinitionParser());
        registerBeanDefinitionParser("retweet-status", new RetweetStatusDefinitionParser());
        registerBeanDefinitionParser("get-retweets", new GetRetweetsDefinitionParser());
        registerBeanDefinitionParser("get-retweeted-by", new GetRetweetedByDefinitionParser());
        registerBeanDefinitionParser("get-retweeted-by-i-ds", new GetRetweetedByIDsDefinitionParser());
        registerBeanDefinitionParser("set-oauth-verifier", new SetOauthVerifierDefinitionParser());
        registerBeanDefinitionParser("request-authorization", new RequestAuthorizationDefinitionParser());
        registerBeanDefinitionParser("reverse-geo-code", new ReverseGeoCodeDefinitionParser());
        registerBeanDefinitionParser("search-places", new SearchPlacesDefinitionParser());
        registerBeanDefinitionParser("get-geo-details", new GetGeoDetailsDefinitionParser());
        registerBeanDefinitionParser("create-place", new CreatePlaceDefinitionParser());
        registerBeanDefinitionParser("get-available-trends", new GetAvailableTrendsDefinitionParser());
        registerBeanDefinitionParser("get-location-trends", new GetLocationTrendsDefinitionParser());
        registerBeanDefinitionParser("get-daily-trends", new GetDailyTrendsDefinitionParser());
        registerBeanDefinitionParser("get-weekly-trends", new GetWeeklyTrendsDefinitionParser());
        registerBeanDefinitionParser("send-direct-message-by-screen-name", new SendDirectMessageByScreenNameDefinitionParser());
        registerBeanDefinitionParser("send-direct-message-by-user-id", new SendDirectMessageByUserIdDefinitionParser());
        registerBeanDefinitionParser("filtered-stream", new FilteredStreamDefinitionParser());
        registerBeanDefinitionParser("sample-stream", new SampleStreamDefinitionParser());
        registerBeanDefinitionParser("firehose-stream", new FirehoseStreamDefinitionParser());
        registerBeanDefinitionParser("link-stream", new LinkStreamDefinitionParser());
        registerBeanDefinitionParser("user-stream", new UserStreamDefinitionParser());
        registerBeanDefinitionParser("site-stream", new SiteStreamDefinitionParser());
    }

}
