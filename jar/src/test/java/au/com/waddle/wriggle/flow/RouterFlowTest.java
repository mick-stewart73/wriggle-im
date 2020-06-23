/* 
 * $Id: SchedulerFactoryTest.java 3629 2011-04-18 10:00:52Z mitcje $
 * $URL: http://open.jira.com/svn/IKASAN/branches/ikasaneip-0.9.x/scheduler/src/test/java/org/ikasan/scheduler/SchedulerFactoryTest.java $
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
package au.com.waddle.wriggle.flow;

import au.com.waddle.wriggle.Application;
import au.com.waddle.wriggle.BaseTest;
import au.com.waddle.wriggle.WriggleConfiguration;
import org.ikasan.spec.error.reporting.ErrorReportingService;
import org.ikasan.spec.error.reporting.ErrorReportingServiceFactory;
import org.ikasan.spec.exclusion.ExclusionManagementService;
import org.ikasan.spec.flow.Flow;
import org.ikasan.spec.module.Module;
import org.ikasan.testharness.flow.jms.MessageListenerVerifier;
import org.ikasan.testharness.flow.rule.IkasanFlowTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.config.JmsListenerEndpointRegistry;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.assertEquals;

/**
 * This test class supports the <code>JmsSampleFlow</code> class.
 * 
 * @author Ikasan Development Team
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Application.class},
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RouterFlowTest extends BaseTest
{
    private static String INTERNAL_REQUEST_ACCOUNT_MESSAGE_FILE = "/data/internalRequestAccount.json";
    private static String INTERNAL_REQUEST_COMPANY_MESSAGE_FILE = "/data/internalRequestCompany.json";
    private static String INTERNAL_REQUEST_LEDGER_MESSAGE_FILE = "/data/internalRequestLedger.json";
    private static String INTERNAL_REQUEST_CUSTOMER_MESSAGE_FILE = "/data/internalRequestCustomer.json";
    private static String INTERNAL_REQUEST_INVOICE_MESSAGE_FILE = "/data/internalRequestInvoice.json";
    private static String INTERNAL_REQUEST_OTHER_MESSAGE_FILE = "/data/internalRequestOther.json";

    private Logger logger = LoggerFactory.getLogger(RouterFlowTest.class);
    @Resource
    private Module<Flow> moduleUnderTest;

    @Resource
    private JmsTemplate jmsTemplate;

    @Resource
    private WriggleConfiguration wriggleConfiguration;

    @Value("${jms.provider.url}")
    private String brokerUrl;

    @Resource
    private JmsListenerEndpointRegistry registry;

    @Resource
    private ErrorReportingServiceFactory errorReportingServiceFactory;

    private ErrorReportingService errorReportingService;

    @Resource
    private ExclusionManagementService exclusionManagementService;

    public IkasanFlowTestRule flowTestRule;

    @Before
    public void setup(){

        flowTestRule = new IkasanFlowTestRule();

        flowTestRule.withFlow(moduleUnderTest.getFlow("Wriggle Request Router Flow"));

        errorReportingService = errorReportingServiceFactory.getErrorReportingService();

    }

    @After
    public void teardown(){

        // consume messages from source queue if any were left
        MessageListenerVerifier mlv = new MessageListenerVerifier(brokerUrl, "source", registry);
        mlv.start();


        flowTestRule.stopFlow();
    }

    @Test
    public void test_router_flow_account_inbound_request() throws Exception
    {
        final MessageListenerVerifier messageListenerVerifier = new MessageListenerVerifier(brokerUrl
                , wriggleConfiguration.getWriggleRequestAccountPrivate(), registry);
        messageListenerVerifier.start();

        // Prepare test data
        String message = loadDataFile(INTERNAL_REQUEST_ACCOUNT_MESSAGE_FILE);
        logger.info("Sending a JMS message.[" + message + "]");
        jmsTemplate.convertAndSend(wriggleConfiguration.getInboundWriggleInternalRequestPrivate(), message);

        //Setup component expectations

        flowTestRule.consumer("JMS Consumer")
            .converter("JSON to Wriggle Inbound Request Converter")
            .router("Request Type Router")
            .converter("Wriggle Account Inbound Request to JSON Converter")
            .producer("Bank Request Producer");

        // start the flow and assert it runs
        flowTestRule.startFlow();

        // wait for a brief while to let the flow complete
        flowTestRule.sleep(1000L);

        flowTestRule.assertIsSatisfied();

        assertEquals(1, messageListenerVerifier.getCaptureResults().size());
    }

    @Test
    public void test_router_flow_company_inbound_request() throws Exception
    {
        final MessageListenerVerifier messageListenerVerifier = new MessageListenerVerifier(brokerUrl
                , wriggleConfiguration.getWriggleRequestCompanyPrivate(), registry);
        messageListenerVerifier.start();

        // Prepare test data
        String message = loadDataFile(INTERNAL_REQUEST_COMPANY_MESSAGE_FILE);
        logger.info("Sending a JMS message.[" + message + "]");
        jmsTemplate.convertAndSend(wriggleConfiguration.getInboundWriggleInternalRequestPrivate(), message);

        //Setup component expectations

        flowTestRule.consumer("JMS Consumer")
                .converter("JSON to Wriggle Inbound Request Converter")
                .router("Request Type Router")
                .converter("Wriggle Company Inbound Request to JSON Converter")
                .producer("Company Request Producer");

        // start the flow and assert it runs
        flowTestRule.startFlow();

        // wait for a brief while to let the flow complete
        flowTestRule.sleep(1000L);

        flowTestRule.assertIsSatisfied();

        assertEquals(1, messageListenerVerifier.getCaptureResults().size());
    }

    @Test
    public void test_router_flow_ledger_inbound_request() throws Exception
    {
        final MessageListenerVerifier messageListenerVerifier = new MessageListenerVerifier(brokerUrl
                , wriggleConfiguration.getWriggleRequestLedgerPrivate(), registry);
        messageListenerVerifier.start();

        // Prepare test data
        String message = loadDataFile(INTERNAL_REQUEST_LEDGER_MESSAGE_FILE);
        logger.info("Sending a JMS message.[" + message + "]");
        jmsTemplate.convertAndSend(wriggleConfiguration.getInboundWriggleInternalRequestPrivate(), message);

        //Setup component expectations

        flowTestRule.consumer("JMS Consumer")
                .converter("JSON to Wriggle Inbound Request Converter")
                .router("Request Type Router")
                .converter("Wriggle Ledger Inbound Request to JSON Converter")
                .producer("Ledger Request Producer");

        // start the flow and assert it runs
        flowTestRule.startFlow();

        // wait for a brief while to let the flow complete
        flowTestRule.sleep(1000L);

        flowTestRule.assertIsSatisfied();

        assertEquals(1, messageListenerVerifier.getCaptureResults().size());
    }

    @Test
    public void test_router_flow_customer_inbound_request() throws Exception
    {
        final MessageListenerVerifier messageListenerVerifier = new MessageListenerVerifier(brokerUrl
                , wriggleConfiguration.getWriggleRequestCustomerPrivate(), registry);
        messageListenerVerifier.start();

        // Prepare test data
        String message = loadDataFile(INTERNAL_REQUEST_CUSTOMER_MESSAGE_FILE);
        logger.info("Sending a JMS message.[" + message + "]");
        jmsTemplate.convertAndSend(wriggleConfiguration.getInboundWriggleInternalRequestPrivate(), message);

        //Setup component expectations

        flowTestRule.consumer("JMS Consumer")
                .converter("JSON to Wriggle Inbound Request Converter")
                .router("Request Type Router")
                .converter("Wriggle Customer Inbound Request to JSON Converter")
                .producer("Customer Request Producer");

        // start the flow and assert it runs
        flowTestRule.startFlow();

        // wait for a brief while to let the flow complete
        flowTestRule.sleep(2000L);

        flowTestRule.assertIsSatisfied();

        assertEquals(1, messageListenerVerifier.getCaptureResults().size());
    }

    @Test
    public void test_router_flow_invoice_inbound_request() throws Exception
    {
        final MessageListenerVerifier messageListenerVerifier = new MessageListenerVerifier(brokerUrl
                , wriggleConfiguration.getWriggleRequestInvoicePrivate(), registry);
        messageListenerVerifier.start();

        // Prepare test data
        String message = loadDataFile(INTERNAL_REQUEST_INVOICE_MESSAGE_FILE);
        logger.info("Sending a JMS message.[" + message + "]");
        jmsTemplate.convertAndSend(wriggleConfiguration.getInboundWriggleInternalRequestPrivate(), message);

        //Setup component expectations

        flowTestRule.consumer("JMS Consumer")
                .converter("JSON to Wriggle Inbound Request Converter")
                .router("Request Type Router")
                .converter("Wriggle Invoice Inbound Request to JSON Converter")
                .producer("Invoice Request Producer");

        // start the flow and assert it runs
        flowTestRule.startFlow();

        // wait for a brief while to let the flow complete
        flowTestRule.sleep(2000L);

        flowTestRule.assertIsSatisfied();

        assertEquals(1, messageListenerVerifier.getCaptureResults().size());
    }

    @Test
    public void test_router_flow_other_inbound_request() throws Exception
    {
        final MessageListenerVerifier messageListenerVerifier = new MessageListenerVerifier(brokerUrl
                , wriggleConfiguration.getWriggleRequestInvoicePrivate(), registry);
        messageListenerVerifier.start();

        // Prepare test data
        String message = loadDataFile(INTERNAL_REQUEST_OTHER_MESSAGE_FILE);
        logger.info("Sending a JMS message.[" + message + "]");
        jmsTemplate.convertAndSend(wriggleConfiguration.getInboundWriggleInternalRequestPrivate(), message);

        //Setup component expectations

        flowTestRule.consumer("JMS Consumer")
                .converter("JSON to Wriggle Inbound Request Converter")
                .router("Request Type Router")
                .producer("Ignore Unknown Request");

        // start the flow and assert it runs
        flowTestRule.startFlow();

        // wait for a brief while to let the flow complete
        flowTestRule.sleep(1000L);

        flowTestRule.assertIsSatisfied();
    }
}
