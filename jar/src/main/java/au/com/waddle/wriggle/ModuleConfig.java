/*
 * $Id$
 * $URL$
 *
 * ====================================================================
 * Ikasan Enterprise Integration Platform
 *
 * Distributed under the Modified BSD License.
 * Copyright notice: The copyright for this software and a full listing
 * of individual contributors are as shown in the packaged copyright.txt
 * file.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  - Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  - Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  - Neither the name of the ORGANIZATION nor the names of its contributors may
 *    be used to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE
 * USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * ====================================================================
 */
package au.com.waddle.wriggle;

import au.com.waddle.wriggle.components.broker.HttpRequestBroker;
import au.com.waddle.wriggle.components.broker.configuration.HttpRequestBrokerConfiguration;
import au.com.waddle.wriggle.components.converter.InboundJsonToWriggleInboundRequestConverter;
import au.com.waddle.wriggle.components.converter.JsonToWriggleInternalRequestConverter;
import au.com.waddle.wriggle.components.converter.WriggleInternalRequestToJsonConverter;
import au.com.waddle.wriggle.components.message.provider.ScheduledMessageProvider;
import au.com.waddle.wriggle.components.router.RequestTypeRouter;
import au.com.waddle.wriggle.components.splitter.InboundRequestSplitter;
import org.apache.activemq.ActiveMQXAConnectionFactory;
import org.ikasan.builder.BuilderFactory;
import org.ikasan.builder.FlowBuilder;
import org.ikasan.builder.ModuleBuilder;
import org.ikasan.builder.OnException;
import org.ikasan.component.endpoint.util.producer.DevNull;
import org.ikasan.spec.component.endpoint.Consumer;
import org.ikasan.spec.component.endpoint.EndpointException;
import org.ikasan.spec.component.endpoint.Producer;
import org.ikasan.spec.error.reporting.ErrorReportingServiceFactory;
import org.ikasan.spec.flow.Flow;
import org.ikasan.spec.module.Module;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;

@Configuration
@ImportResource( {
        "classpath:ikasan-transaction-pointcut-jms.xml",
        "classpath:ikasan-transaction-pointcut-resubmission.xml",
        "classpath:h2-datasource-conf.xml"
} )
public class ModuleConfig
{
    @Value("${module.name}")
    private String moduleName;

    @Resource
    private BuilderFactory builderFactory;

    @Resource
    private ErrorReportingServiceFactory errorReportingServiceFactory;

    @Resource
    private WriggleConfiguration wriggleConfiguration;

    @Value("${jms.provider.url}")
    private String brokerUrl;

    @Bean
    public Module getModule(){

        ModuleBuilder moduleBuilder = builderFactory.getModuleBuilder(moduleName);

        Module module = moduleBuilder.withDescription("Wriggle Accounting")
            .addFlow(this.getWriggleInboundRequestFlow(moduleBuilder))
            .addFlow(this.getRouterFlow(moduleBuilder))
            .addFlow(this.getWriggleCustomerHttpRequestFlow(moduleBuilder))
            .addFlow(this.getWriggleLedgerHttpRequestFlow(moduleBuilder))
            .addFlow(this.getWriggleCompanyHttpRequestFlow(moduleBuilder))
            .addFlow(this.getWriggleAccountHttpRequestFlow(moduleBuilder))
            .addFlow(this.getWriggleInvoiceHttpRequestFlow(moduleBuilder))
            .addFlow(this.createScheduledFlow(moduleBuilder))
            .build();
        return module;
    }


    /**
     * Create the inbound request flow.
     *
     * @param moduleBuilder
     * @return
     */
    private Flow getWriggleInboundRequestFlow(ModuleBuilder moduleBuilder){
        FlowBuilder flowBuilder = moduleBuilder.getFlowBuilder("Wriggle Request Inbound Flow");

        Flow flow = flowBuilder
                .withDescription("Receive a Wriggle Inbound request and split it into a number of internal requests.")
                .withExceptionResolver(builderFactory.getExceptionResolverBuilder()
                        .addExceptionToAction(SampleGeneratedException.class, OnException.excludeEvent())
                        .addExceptionToAction(EndpointException.class, OnException.retryIndefinitely(10000)))
                .withErrorReportingServiceFactory(errorReportingServiceFactory)
                .withMonitor(builderFactory.getMonitorBuilder().withFlowStateChangeMonitor().withDashboardNotifier())
                .consumer("JMS Consumer", this.createJmsConsumer(wriggleConfiguration.getInboundWriggleRequestPublic()
                        , "inboundRequestConsumer"))
                .converter("JSON to Wriggle Inbound Request Converter", new InboundJsonToWriggleInboundRequestConverter())
                .splitter( "Inbound Request Splitter", new InboundRequestSplitter())
                .converter("Internal Request to JSON Converter", new WriggleInternalRequestToJsonConverter())
                .producer("Internal Request JMS Producer", this.createJmsProducer(wriggleConfiguration.getInboundWriggleInternalRequestPrivate(),
                        "splitInboundRequestProducer"))
                .build();

        return flow;
    }

    /**
     * Create the request router flow.
     *
     * @param moduleBuilder
     * @return
     */
    private Flow getRouterFlow(ModuleBuilder moduleBuilder){
        FlowBuilder flowBuilder = moduleBuilder.getFlowBuilder("Wriggle Request Router Flow");

        Flow flow = flowBuilder
                .withDescription("Route a Wriggle Internal request to the relevant channel.")
                .withExceptionResolver(builderFactory.getExceptionResolverBuilder()
                        .addExceptionToAction(SampleGeneratedException.class, OnException.excludeEvent())
                        .addExceptionToAction(EndpointException.class, OnException.retryIndefinitely(10000)))
                .withErrorReportingServiceFactory(errorReportingServiceFactory)
                .withMonitor(builderFactory.getMonitorBuilder().withFlowStateChangeMonitor().withDashboardNotifier())
                .consumer("JMS Consumer", this.createJmsConsumer(wriggleConfiguration.getInboundWriggleInternalRequestPrivate()
                        , "routerFlowConsumer"))
                .converter("JSON to Wriggle Inbound Request Converter", new JsonToWriggleInternalRequestConverter())
                .singleRecipientRouter("Request Type Router", new RequestTypeRouter())
                    .when("company", builderFactory.getRouteBuilder()
                            .converter("Wriggle Company Inbound Request to JSON Converter", new WriggleInternalRequestToJsonConverter())
                            .producer("Company Request Producer",this.createJmsProducer(wriggleConfiguration.getWriggleRequestCompanyPrivate(),
                                    "companyRequestProducer")) )
                    .when("bank", builderFactory.getRouteBuilder()
                            .converter("Wriggle Account Inbound Request to JSON Converter", new WriggleInternalRequestToJsonConverter())
                            .producer("Bank Request Producer",this.createJmsProducer(wriggleConfiguration.getWriggleRequestAccountPrivate(),
                                    "bankRequestProducer")) )
                    .when("customer", builderFactory.getRouteBuilder()
                            .converter("Wriggle Customer Inbound Request to JSON Converter", new WriggleInternalRequestToJsonConverter())
                            .producer("Customer Request Producer",this.createJmsProducer(wriggleConfiguration.getWriggleRequestCustomerPrivate(),
                                    "customerRequestProducer")) )
                    .when("invoice", builderFactory.getRouteBuilder()
                            .converter("Wriggle Invoice Inbound Request to JSON Converter", new WriggleInternalRequestToJsonConverter())
                            .producer("Invoice Request Producer",this.createJmsProducer(wriggleConfiguration.getWriggleRequestInvoicePrivate(),
                                    "invoiceRequestProducer")) )
                    .when("ledger", builderFactory.getRouteBuilder()
                            .converter("Wriggle Ledger Inbound Request to JSON Converter", new WriggleInternalRequestToJsonConverter())
                            .producer("Ledger Request Producer",this.createJmsProducer(wriggleConfiguration.getWriggleRequestLedgerPrivate(),
                                    "ledgeRequestProducer")) )
                    .otherwise(builderFactory.getRouteBuilder().producer("Ignore Unknown Request", new DevNull()) )
                .build();

        return flow;
    }

    /**
     * Create the customer http request flow.
     *
     * @param moduleBuilder
     * @return
     */
    private Flow getWriggleCustomerHttpRequestFlow(ModuleBuilder moduleBuilder){
        return getWriggleHttpRequestFlow(moduleBuilder, "Customer", "customerHttpRequestFlow", wriggleConfiguration.getHttpRequestUsername(),
                wriggleConfiguration.getHttpRequestPassword(), wriggleConfiguration.getHttpCustomerRequestUrl(), wriggleConfiguration.getWriggleRequestCustomerPrivate(),
                wriggleConfiguration.getWriggleResponseCustomerPublic());
    }

    /**
     * Create the ledger http request flow.
     *
     * @param moduleBuilder
     * @return
     */
    private Flow getWriggleLedgerHttpRequestFlow(ModuleBuilder moduleBuilder){
        return getWriggleHttpRequestFlow(moduleBuilder, "Ledger", "ledgerHttpRequestFlow", wriggleConfiguration.getHttpRequestUsername(),
                wriggleConfiguration.getHttpRequestPassword(), wriggleConfiguration.getHttpLedgerRequestUrl(), wriggleConfiguration.getWriggleRequestLedgerPrivate(),
                wriggleConfiguration.getWriggleResponseLedgerPublic());
    }

    /**
     * Create the company http request flow.
     *
     * @param moduleBuilder
     * @return
     */
    private Flow getWriggleCompanyHttpRequestFlow(ModuleBuilder moduleBuilder){
        return getWriggleHttpRequestFlow(moduleBuilder, "Company", "companyHttpRequestFlow", wriggleConfiguration.getHttpRequestUsername(),
                wriggleConfiguration.getHttpRequestPassword(), wriggleConfiguration.getHttpCompanyRequestUrl(), wriggleConfiguration.getWriggleRequestCompanyPrivate(),
                wriggleConfiguration.getWriggleResponseCompanyPublic());
    }

    /**
     * Create the account http request flow.
     *
     * @param moduleBuilder
     * @return
     */
    private Flow getWriggleAccountHttpRequestFlow(ModuleBuilder moduleBuilder){
        return getWriggleHttpRequestFlow(moduleBuilder, "Account", "accountHttpRequestFlow", wriggleConfiguration.getHttpRequestUsername(),
                wriggleConfiguration.getHttpRequestPassword(), wriggleConfiguration.getHttpAccountRequestUrl(), wriggleConfiguration.getWriggleRequestAccountPrivate(),
                wriggleConfiguration.getWriggleResponseAccountPublic());
    }

    /**
     * Create the invoice http request flow.
     *
     * @param moduleBuilder
     * @return
     */
    private Flow getWriggleInvoiceHttpRequestFlow(ModuleBuilder moduleBuilder){
        return getWriggleHttpRequestFlow(moduleBuilder, "Invoice", "InvoiceHttpRequestFlow", wriggleConfiguration.getHttpRequestUsername(),
                wriggleConfiguration.getHttpRequestPassword(), wriggleConfiguration.getHttpInvoiceRequestUrl(), wriggleConfiguration.getWriggleRequestInvoicePrivate(),
                wriggleConfiguration.getWriggleResponseInvoicePublic());
    }

    /**
     * Helper method to create a general HTTP request flow.
     *
     * @param moduleBuilder
     * @param entity
     * @param configurationIdPrefix
     * @param httpUserName
     * @param httpPassword
     * @param requestUrl
     * @param sourceDestinationName
     * @param targetDestinationName
     * @return
     */
    private Flow getWriggleHttpRequestFlow(ModuleBuilder moduleBuilder, String entity, String configurationIdPrefix,
                                           String httpUserName, String httpPassword, String requestUrl, String sourceDestinationName,
                                           String targetDestinationName){
        FlowBuilder flowBuilder = moduleBuilder.getFlowBuilder("Wriggle " + entity + " HTTP Request Flow");

        HttpRequestBrokerConfiguration configuration = new HttpRequestBrokerConfiguration();
        configuration.setUsername(httpUserName);
        configuration.setPassword(httpPassword);
        configuration.setRequestUrlContext(requestUrl);
        configuration.setBaseUrl(wriggleConfiguration.getHttpRequestBaseUrl());

        HttpRequestBroker httpRequestBroker = new HttpRequestBroker();
        httpRequestBroker.setConfiguration(configuration);
        httpRequestBroker.setConfiguredResourceId(moduleName + "-" + configurationIdPrefix + "HTTPRequestBroker");

        Flow flow = flowBuilder
                .withDescription(String.format("Make a Wriggle HTTP (%s) request and publish it to the relevant public channel.", entity))
                .withExceptionResolver(builderFactory.getExceptionResolverBuilder()
                        .addExceptionToAction(SampleGeneratedException.class, OnException.excludeEvent())
                        .addExceptionToAction(EndpointException.class, OnException.retryIndefinitely(10000)))
                .withErrorReportingServiceFactory(errorReportingServiceFactory)
                .withMonitor(builderFactory.getMonitorBuilder().withFlowStateChangeMonitor().withDashboardNotifier())
                .consumer("JMS Consumer", this.createJmsConsumer(sourceDestinationName,
                        configurationIdPrefix + "Consumer"))
                .converter("JSON to Wriggle Inbound Request Converter", new JsonToWriggleInternalRequestConverter())
                .broker("HTTP Request Broker", httpRequestBroker)
                .producer("Public " + entity + " HTTP Request JMS Producer", this.createJmsProducer(targetDestinationName,
                        configurationIdPrefix + "Producer"))
                .build();

        return flow;
    }

    private Flow createScheduledFlow(ModuleBuilder moduleBuilder){
        FlowBuilder flowBuilder = moduleBuilder.getFlowBuilder("Scheduled FLow");

        Consumer scheduledConsumer = this.builderFactory.getComponentBuilder()
                .scheduledConsumer()
                .setConfiguredResourceId(moduleName + "-scheduledFlowConsumer")
                .setCronExpression("0/5 * * * * ? *")
                .setMessageProvider(new ScheduledMessageProvider())
                .build();

        Flow flow = flowBuilder
                .withDescription("Scheduled FLow")
                .withExceptionResolver(builderFactory.getExceptionResolverBuilder()
                        .addExceptionToAction(SampleGeneratedException.class, OnException.excludeEvent())
                        .addExceptionToAction(EndpointException.class, OnException.retryIndefinitely(10000)))
                .withErrorReportingServiceFactory(errorReportingServiceFactory)
                .withMonitor(builderFactory.getMonitorBuilder().withFlowStateChangeMonitor().withDashboardNotifier())
                .consumer("Scheduled Consumer", scheduledConsumer)
                .producer("Scheduled Inbound JMS Producer" , this.createJmsProducer(wriggleConfiguration.getInboundWriggleRequestPublic(),
                        "inboundJmsProducer"))
                .build();

        return flow;
    }

    /**
     * Helper method to create a JMS Producer
     * @param destinationName
     * @param configurationId
     * @return
     */
    private Producer createJmsProducer(String destinationName, String configurationId){
        ConnectionFactory producerConnectionFactory = new ActiveMQXAConnectionFactory(brokerUrl);

        Producer jmsProducer = builderFactory.getComponentBuilder().jmsProducer()
                .setConnectionFactory(producerConnectionFactory)
                .setDestinationJndiName(destinationName)
                .setConfiguredResourceId(moduleName + "-" + configurationId)
                .build();

        return jmsProducer;
    }

    /**
     * Helper method to create a JMS Consumer
     * @param destinationName
     * @param configurationId
     * @return
     */
    private Consumer createJmsConsumer(String destinationName, String configurationId){
        ConnectionFactory consumerConnectionFactory = new ActiveMQXAConnectionFactory(brokerUrl);

        Consumer jmsConsumer = builderFactory.getComponentBuilder().jmsConsumer()
                .setConnectionFactory(consumerConnectionFactory)
                .setDestinationJndiName(destinationName)
                .setAutoContentConversion(true)
                .setConfiguredResourceId(moduleName + "-" + configurationId)
                .build();

        return jmsConsumer;
    }
}
