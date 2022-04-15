```java
@Slf4j
public class XoHttpClientRibbonCommandFactory extends HttpClientRibbonCommandFactory {
    
    private final SpringClientFactory clientFactory;
    private final ZuulProperties zuulProperties;

    private final static String servicePrefix = "x-exName-";

    public XoHttpClientRibbonCommandFactory(SpringClientFactory clientFactory, ZuulProperties zuulProperties) {
        super(clientFactory, zuulProperties);
        this.clientFactory = clientFactory;
        this.zuulProperties = zuulProperties;
    }

    @Override
    public HttpClientRibbonCommand create(final RibbonCommandContext context) {
        FallbackProvider zuulFallbackProvider = getFallbackProvider(
                context.getServiceId());
        final String serviceId = context.getServiceId();
        String realServiceName = serviceId;

        // serviceId like x-exName-realServiceName
        if(StringUtils.startsWithIgnoreCase(serviceId, servicePrefix)){
            realServiceName = serviceId.replaceFirst(servicePrefix, "");
            log.info("serviceId [{}] change to [{}]", serviceId, realServiceName);
        }

        final RibbonLoadBalancingHttpClient client = this.clientFactory
                .getClient(realServiceName, RibbonLoadBalancingHttpClient.class);
        client.setLoadBalancer(this.clientFactory.getLoadBalancer(realServiceName));

        return new HttpClientRibbonCommand(serviceId, client, context, zuulProperties,
                zuulFallbackProvider, clientFactory.getClientConfig(serviceId));
    }

}
```