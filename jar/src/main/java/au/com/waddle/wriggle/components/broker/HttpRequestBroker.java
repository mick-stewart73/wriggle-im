package au.com.waddle.wriggle.components.broker;

import au.com.waddle.wriggle.components.broker.configuration.HttpRequestBrokerConfiguration;
import au.com.waddle.wriggle.model.WriggleInternalRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;
import org.ikasan.spec.component.endpoint.Broker;
import org.ikasan.spec.component.endpoint.EndpointException;
import org.ikasan.spec.configuration.ConfiguredResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.Collections;

public class HttpRequestBroker implements Broker<WriggleInternalRequest, String>, ConfiguredResource<HttpRequestBrokerConfiguration> {
    private Logger logger = LoggerFactory.getLogger(HttpRequestBroker.class);

    private String configurationId;
    private HttpRequestBrokerConfiguration configuration;

    private RestTemplate restTemplate;

    private ObjectMapper objectMapper;

    /**
     * Constructor
     */
    public HttpRequestBroker() {
        this.restTemplate = new RestTemplate();
        this.objectMapper =  new ObjectMapper();
    }

    @Override
    public String invoke(WriggleInternalRequest wriggleInternalRequest) throws EndpointException {
//        return executeGet(configuration.getBaseUrl(), configuration.getRequestUrlContext(), wriggleInternalRequest);

//        if(System.currentTimeMillis()%2 == 0) {
//            return "\n" +
//                    "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
//                    "<!--View is confirmation-->\n" +
//                    "<!--Version is 5-11-->\n" +
//                    "<!--NS is http://www.fpml.org/FpML-5/confirmation-->\n" +
//                    "<!--\n" +
//                    "  == Copyright (c) 2018-2020 All rights reserved.\n" +
//                    "  == Financial Products Markup Language is subject to the FpML public license.\n" +
//                    "  == A copy of this license is available at http://www.fpml.org/license/license.html\n" +
//                    "  -->\n" +
//                    "<!--5.0:Message type is a Root of the message-->\n" +
//                    "<dataDocument xmlns=\"http://www.fpml.org/FpML-5/confirmation\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" fpmlVersion=\"5-11\" xsi:schemaLocation=\"http://www.fpml.org/FpML-5/confirmation ../../fpml-main-5-11.xsd http://www.w3.org/2000/09/xmldsig# ../../xmldsig-core-schema.xsd\">\n" +
//                    "  <trade>\n" +
//                    "    <tradeHeader>\n" +
//                    "      <partyTradeIdentifier>\n" +
//                    "        <partyReference href=\"party1\" />\n" +
//                    "        <tradeId tradeIdScheme=\"http://www.swapswire.com/spec/2001/trade-id-1-0\">37209</tradeId>\n" +
//                    "      </partyTradeIdentifier>\n" +
//                    "      <partyTradeIdentifier>\n" +
//                    "        <partyReference href=\"party2\" />\n" +
//                    "        <tradeId tradeIdScheme=\"http://www.swapswire.com/spec/2001/trade-id-1-0\">37209</tradeId>\n" +
//                    "      </partyTradeIdentifier>\n" +
//                    "      <tradeDate>2002-12-04</tradeDate>\n" +
//                    "    </tradeHeader>\n" +
//                    "    <creditDefaultSwap>\n" +
//                    "      <generalTerms>\n" +
//                    "        <effectiveDate>\n" +
//                    "          <unadjustedDate>2002-12-05</unadjustedDate>\n" +
//                    "          <dateAdjustments>\n" +
//                    "            <businessDayConvention>NONE</businessDayConvention>\n" +
//                    "          </dateAdjustments>\n" +
//                    "        </effectiveDate>\n" +
//                    "        <scheduledTerminationDate>\n" +
//                    "          <unadjustedDate>2007-12-05</unadjustedDate>\n" +
//                    "          <dateAdjustments>\n" +
//                    "            <businessDayConvention>MODFOLLOWING</businessDayConvention>\n" +
//                    "            <businessCenters>\n" +
//                    "              <businessCenter>GBLO</businessCenter>\n" +
//                    "              <businessCenter>USNY</businessCenter>\n" +
//                    "              <businessCenter>JPTO</businessCenter>\n" +
//                    "            </businessCenters>\n" +
//                    "          </dateAdjustments>\n" +
//                    "        </scheduledTerminationDate>\n" +
//                    "        <buyerPartyReference href=\"party2\" />\n" +
//                    "        <sellerPartyReference href=\"party1\" />\n" +
//                    "        <dateAdjustments>\n" +
//                    "          <businessDayConvention>MODFOLLOWING</businessDayConvention>\n" +
//                    "          <businessCenters>\n" +
//                    "            <businessCenter>GBLO</businessCenter>\n" +
//                    "            <businessCenter>USNY</businessCenter>\n" +
//                    "            <businessCenter>JPTO</businessCenter>\n" +
//                    "          </businessCenters>\n" +
//                    "        </dateAdjustments>\n" +
//                    "        <referenceInformation>\n" +
//                    "          <referenceEntity id=\"referenceEntity\">\n" +
//                    "            <entityName>ACOM CO., LTD.</entityName>\n" +
//                    "            <entityId entityIdScheme=\"http://www.fpml.org/coding-scheme/external/entity-id-RED\">004CC9</entityId>\n" +
//                    "          </referenceEntity>\n" +
//                    "          <referenceObligation>\n" +
//                    "            <bond>\n" +
//                    "              <instrumentId instrumentIdScheme=\"http://www.fpml.org/coding-scheme/external/instrument-id-ISIN\">JP310860A032</instrumentId>\n" +
//                    "              <couponRate>0.0213</couponRate>\n" +
//                    "              <maturity>2007-03-08</maturity>\n" +
//                    "            </bond>\n" +
//                    "            <primaryObligorReference href=\"referenceEntity\" />\n" +
//                    "          </referenceObligation>\n" +
//                    "          <referencePrice>1.0</referencePrice>\n" +
//                    "        </referenceInformation>\n" +
//                    "      </generalTerms>\n" +
//                    "      <feeLeg>\n" +
//                    "        <periodicPayment>\n" +
//                    "          <paymentFrequency>\n" +
//                    "            <periodMultiplier>3</periodMultiplier>\n" +
//                    "            <period>M</period>\n" +
//                    "          </paymentFrequency>\n" +
//                    "          <firstPaymentDate>2003-03-05</firstPaymentDate>\n" +
//                    "          <rollConvention>5</rollConvention>\n" +
//                    "          <fixedAmountCalculation>\n" +
//                    "            <calculationAmount>\n" +
//                    "              <currency>JPY</currency>\n" +
//                    "              <amount>500000000.0</amount>\n" +
//                    "            </calculationAmount>\n" +
//                    "            <fixedRate>0.007</fixedRate>\n" +
//                    "            <dayCountFraction>ACT/360</dayCountFraction>\n" +
//                    "          </fixedAmountCalculation>\n" +
//                    "        </periodicPayment>\n" +
//                    "      </feeLeg>\n" +
//                    "      <protectionTerms>\n" +
//                    "        <calculationAmount>\n" +
//                    "          <currency>JPY</currency>\n" +
//                    "          <amount>500000000.0</amount>\n" +
//                    "        </calculationAmount>\n" +
//                    "        <creditEvents>\n" +
//                    "          <bankruptcy>true</bankruptcy>\n" +
//                    "          <failureToPay>\n" +
//                    "            <applicable>true</applicable>\n" +
//                    "            <paymentRequirement>\n" +
//                    "              <currency>JPY</currency>\n" +
//                    "              <amount>100000000.0</amount>\n" +
//                    "            </paymentRequirement>\n" +
//                    "          </failureToPay>\n" +
//                    "          <restructuring>\n" +
//                    "            <applicable>true</applicable>\n" +
//                    "            <restructuringType>R</restructuringType>\n" +
//                    "          </restructuring>\n" +
//                    "          <defaultRequirement>\n" +
//                    "            <currency>JPY</currency>\n" +
//                    "            <amount>1000000000.0</amount>\n" +
//                    "          </defaultRequirement>\n" +
//                    "          <creditEventNotice>\n" +
//                    "            <notifyingParty>\n" +
//                    "              <buyerPartyReference href=\"party2\" />\n" +
//                    "              <sellerPartyReference href=\"party1\" />\n" +
//                    "            </notifyingParty>\n" +
//                    "            <publiclyAvailableInformation>\n" +
//                    "              <standardPublicSources>true</standardPublicSources>\n" +
//                    "              <specifiedNumber>2</specifiedNumber>\n" +
//                    "            </publiclyAvailableInformation>\n" +
//                    "          </creditEventNotice>\n" +
//                    "        </creditEvents>\n" +
//                    "        <obligations>\n" +
//                    "          <category>BorrowedMoney</category>\n" +
//                    "          <notSubordinated>true</notSubordinated>\n" +
//                    "        </obligations>\n" +
//                    "      </protectionTerms>\n" +
//                    "      <physicalSettlementTerms>\n" +
//                    "        <settlementCurrency>JPY</settlementCurrency>\n" +
//                    "        <physicalSettlementPeriod>\n" +
//                    "          <businessDays>30</businessDays>\n" +
//                    "        </physicalSettlementPeriod>\n" +
//                    "        <deliverableObligations>\n" +
//                    "          <accruedInterest>false</accruedInterest>\n" +
//                    "          <category>BondOrLoan</category>\n" +
//                    "          <notSubordinated>true</notSubordinated>\n" +
//                    "          <specifiedCurrency>\n" +
//                    "            <applicable>true</applicable>\n" +
//                    "          </specifiedCurrency>\n" +
//                    "          <notContingent>true</notContingent>\n" +
//                    "          <assignableLoan>\n" +
//                    "            <applicable>true</applicable>\n" +
//                    "          </assignableLoan>\n" +
//                    "          <consentRequiredLoan>\n" +
//                    "            <applicable>true</applicable>\n" +
//                    "          </consentRequiredLoan>\n" +
//                    "          <transferable>true</transferable>\n" +
//                    "          <maximumMaturity>\n" +
//                    "            <periodMultiplier>30</periodMultiplier>\n" +
//                    "            <period>Y</period>\n" +
//                    "          </maximumMaturity>\n" +
//                    "          <notBearer>true</notBearer>\n" +
//                    "        </deliverableObligations>\n" +
//                    "        <escrow>true</escrow>\n" +
//                    "      </physicalSettlementTerms>\n" +
//                    "    </creditDefaultSwap>\n" +
//                    "    <calculationAgent>\n" +
//                    "      <calculationAgentPartyReference href=\"party1\" />\n" +
//                    "    </calculationAgent>\n" +
//                    "    <calculationAgentBusinessCenter>GBLO</calculationAgentBusinessCenter>\n" +
//                    "    <documentation>\n" +
//                    "      <masterAgreement>\n" +
//                    "        <masterAgreementType>ISDA</masterAgreementType>\n" +
//                    "        <masterAgreementVersion>1992</masterAgreementVersion>\n" +
//                    "      </masterAgreement>\n" +
//                    "      <contractualDefinitions>ISDA1999Credit</contractualDefinitions>\n" +
//                    "      <contractualTermsSupplement>\n" +
//                    "        <type>ISDA1999CreditSuccessorAndCreditEvents</type>\n" +
//                    "      </contractualTermsSupplement>\n" +
//                    "      <contractualTermsSupplement>\n" +
//                    "        <type>ISDA1999CreditConvertibleExchangeableAccretingObligations</type>\n" +
//                    "      </contractualTermsSupplement>\n" +
//                    "    </documentation>\n" +
//                    "  </trade>\n" +
//                    "  <party id=\"party1\">\n" +
//                    "    <partyId partyIdScheme=\"http://www.fpml.org/coding-scheme/external/iso17442\">254900BIAQJIUV6DLE92</partyId>\n" +
//                    "    <partyName>XYZ Bank</partyName>\n" +
//                    "  </party>\n" +
//                    "  <party id=\"party2\">\n" +
//                    "    <partyId partyIdScheme=\"http://www.fpml.org/coding-scheme/external/iso17442\">549300VBWWV6BYQOWM67</partyId>\n" +
//                    "    <partyName>ABC Bank</partyName>\n" +
//                    "  </party>\n" +
//                    "</dataDocument>\n" +
//                    "\n";
//        }
//        else
        {
            return "\tSeries_reference,Period,Data_value,Suppressed,STATUS,UNITS,Magnitude,Subject,Group,Series_title_1,Series_title_2,Series_title_3,Series_title_4,Series_title_5\n" +
                    "ECTA.S19A1,2001.03,2462.5,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,RTS total industries,,,\n" +
                    "ECTA.S19A1,2002.03,17177.2,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,RTS total industries,,,\n" +
                    "ECTA.S19A1,2003.03,22530.5,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,RTS total industries,,,\n" +
                    "ECTA.S19A1,2004.03,28005.1,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,RTS total industries,,,\n" +
                    "ECTA.S19A1,2005.03,30629.6,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,RTS total industries,,,\n" +
                    "ECTA.S19A1,2006.03,33317.4,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,RTS total industries,,,\n" +
                    "ECTA.S19A1,2007.03,36422,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,RTS total industries,,,\n" +
                    "ECTA.S19A1,2008.03,39198,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,RTS total industries,,,\n" +
                    "ECTA.S19A1,2009.03,40629.4,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,RTS total industries,,,\n" +
                    "ECTA.S19A1,2010.03,41815.4,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,RTS total industries,,,\n" +
                    "ECTA.S19A1,2011.03,43848.6,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,RTS total industries,,,\n" +
                    "ECTA.S19A1,2012.03,47004.9,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,RTS total industries,,,\n" +
                    "ECTA.S19A1,2013.03,49188.8,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,RTS total industries,,,\n" +
                    "ECTA.S19A1,2014.03,52108.2,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,RTS total industries,,,\n" +
                    "ECTA.S19A1,2015.03,54410.5,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,RTS total industries,,,\n" +
                    "ECTA.S19A1,2016.03,57389.5,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,RTS total industries,,,\n" +
                    "ECTA.S19A1,2017.03,60616.6,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,RTS total industries,,,\n" +
                    "ECTA.S19A1,2018.03,64238.6,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,RTS total industries,,,\n" +
                    "ECTA.S19A1,2019.03,66777.7,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,RTS total industries,,,\n" +
                    "ECTA.S19A1,2020.03,68844.3,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,RTS total industries,,,\n" +
                    "ECTA.S19A2,2001.03,,,C,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,RTS core industries,,,\n" +
                    "ECTA.S19A2,2002.03,,,C,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,RTS core industries,,,\n" +
                    "ECTA.S19A2,2003.03,,,C,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,RTS core industries,,,\n" +
                    "ECTA.S19A2,2004.03,24641.7,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,RTS core industries,,,\n" +
                    "ECTA.S19A2,2005.03,26469.8,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,RTS core industries,,,\n" +
                    "ECTA.S19A2,2006.03,28502,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,RTS core industries,,,\n" +
                    "ECTA.S19A2,2007.03,31000.7,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,RTS core industries,,,\n" +
                    "ECTA.S19A2,2008.03,33293.2,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,RTS core industries,,,\n" +
                    "ECTA.S19A2,2009.03,34258.5,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,RTS core industries,,,\n" +
                    "ECTA.S19A2,2010.03,35712.7,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,RTS core industries,,,\n" +
                    "ECTA.S19A2,2011.03,37132.3,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,RTS core industries,,,\n" +
                    "ECTA.S19A2,2012.03,39561.9,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,RTS core industries,,,\n" +
                    "ECTA.S19A2,2013.03,40945.5,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,RTS core industries,,,\n" +
                    "ECTA.S19A2,2014.03,42895.6,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,RTS core industries,,,\n" +
                    "ECTA.S19A2,2015.03,45331.2,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,RTS core industries,,,\n" +
                    "ECTA.S19A2,2016.03,48446,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,RTS core industries,,,\n" +
                    "ECTA.S19A2,2017.03,51519.4,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,RTS core industries,,,\n" +
                    "ECTA.S19A2,2018.03,54817.6,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,RTS core industries,,,\n" +
                    "ECTA.S19A2,2019.03,57151.8,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,RTS core industries,,,\n" +
                    "ECTA.S19A2,2020.03,59701.6,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,RTS core industries,,,\n" +
                    "ECTA.S19A9,2001.03,,,C,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,Total,,,\n" +
                    "ECTA.S19A9,2002.03,,,C,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,Total,,,\n" +
                    "ECTA.S19A9,2003.03,,,C,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,Total,,,\n" +
                    "ECTA.S19A9,2004.03,36878.7,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,Total,,,\n" +
                    "ECTA.S19A9,2005.03,40756.2,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,Total,,,\n" +
                    "ECTA.S19A9,2006.03,44303.8,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,Total,,,\n" +
                    "ECTA.S19A9,2007.03,48616.7,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,Total,,,\n" +
                    "ECTA.S19A9,2008.03,52669.1,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,Total,,,\n" +
                    "ECTA.S19A9,2009.03,54475.5,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,Total,,,\n" +
                    "ECTA.S19A9,2010.03,55977.4,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,Total,,,\n" +
                    "ECTA.S19A9,2011.03,58624.9,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,Total,,,\n" +
                    "ECTA.S19A9,2012.03,62614.7,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,Total,,,\n" +
                    "ECTA.S19A9,2013.03,65296.1,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,Total,,,\n" +
                    "ECTA.S19A9,2014.03,69247.1,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,Total,,,\n" +
                    "ECTA.S19A9,2015.03,72320.3,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,Total,,,\n" +
                    "ECTA.S19A9,2016.03,76488.6,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,Total,,,\n" +
                    "ECTA.S19A9,2017.03,81334.1,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,Total,,,\n" +
                    "ECTA.S19A9,2018.03,87684.4,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,Total,,,\n" +
                    "ECTA.S19A9,2019.03,91445.8,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,Total,,,\n" +
                    "ECTA.S19A9,2020.03,93968.7,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,Total,,,\n" +
                    "ECTA.S19AW,2001.03,,,C,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,Credit,,,\n" +
                    "ECTA.S19AW,2002.03,,,C,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,Credit,,,\n" +
                    "ECTA.S19AW,2003.03,,,C,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,Credit,,,\n" +
                    "ECTA.S19AW,2004.03,16307.6,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,Credit,,,\n" +
                    "ECTA.S19AW,2005.03,18481.2,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,Credit,,,\n" +
                    "ECTA.S19AW,2006.03,20210.6,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,Credit,,,\n" +
                    "ECTA.S19AW,2007.03,22272.1,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,Credit,,,\n" +
                    "ECTA.S19AW,2008.03,23982.4,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,Credit,,,\n" +
                    "ECTA.S19AW,2009.03,24381.1,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,Credit,,,\n" +
                    "ECTA.S19AW,2010.03,24135.6,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,Credit,,,\n" +
                    "ECTA.S19AW,2011.03,24851.6,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,Credit,,,\n" +
                    "ECTA.S19AW,2012.03,26411.2,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,Credit,,,\n" +
                    "ECTA.S19AW,2013.03,27504.1,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,Credit,,,\n" +
                    "ECTA.S19AW,2014.03,29840,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,Credit,,,\n" +
                    "ECTA.S19AW,2015.03,31607.8,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,Credit,,,\n" +
                    "ECTA.S19AW,2016.03,34681.6,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,Credit,,,\n" +
                    "ECTA.S19AW,2017.03,39191.6,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,Credit,,,\n" +
                    "ECTA.S19AW,2018.03,46065.7,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,Credit,,,\n" +
                    "ECTA.S19AW,2019.03,50302.3,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,Credit,,,\n" +
                    "ECTA.S19AW,2020.03,52670.7,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,Credit,,,\n" +
                    "ECTA.S19AX,2001.03,,,C,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,Debit,,,\n" +
                    "ECTA.S19AX,2002.03,,,C,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,Debit,,,\n" +
                    "ECTA.S19AX,2003.03,,,C,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,Debit,,,\n" +
                    "ECTA.S19AX,2004.03,20571.1,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,Debit,,,\n" +
                    "ECTA.S19AX,2005.03,22274.9,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,Debit,,,\n" +
                    "ECTA.S19AX,2006.03,24093.1,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,Debit,,,\n" +
                    "ECTA.S19AX,2007.03,26344.6,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,Debit,,,\n" +
                    "ECTA.S19AX,2008.03,28686.8,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,Debit,,,\n" +
                    "ECTA.S19AX,2009.03,30094.4,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,Debit,,,\n" +
                    "ECTA.S19AX,2010.03,31841.8,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,Debit,,,\n" +
                    "ECTA.S19AX,2011.03,33773.3,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,Debit,,,\n" +
                    "ECTA.S19AX,2012.03,36203.5,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,Debit,,,\n" +
                    "ECTA.S19AX,2013.03,37792,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,Debit,,,\n" +
                    "ECTA.S19AX,2014.03,39407.1,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,Debit,,,\n" +
                    "ECTA.S19AX,2015.03,40712.5,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,Debit,,,\n" +
                    "ECTA.S19AX,2016.03,41807,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,Debit,,,\n" +
                    "ECTA.S19AX,2017.03,42142.5,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,Debit,,,\n" +
                    "ECTA.S19AX,2018.03,41618.6,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,Debit,,,\n" +
                    "ECTA.S19AX,2019.03,41143.5,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,Debit,,,\n" +
                    "ECTA.S19AX,2020.03,41298,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Total values - Electronic card transactions A/S/T by division,Actual,Debit,,,\n" +
                    "ECTA.S1GA1,2001.03,,,C,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Consumables,,,\n" +
                    "ECTA.S1GA1,2002.03,,,C,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Consumables,,,\n" +
                    "ECTA.S1GA1,2003.03,,,C,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Consumables,,,\n" +
                    "ECTA.S1GA1,2004.03,9753.9,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Consumables,,,\n" +
                    "ECTA.S1GA1,2005.03,10471.4,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Consumables,,,\n" +
                    "ECTA.S1GA1,2006.03,11360.4,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Consumables,,,\n" +
                    "ECTA.S1GA1,2007.03,12358.4,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Consumables,,,\n" +
                    "ECTA.S1GA1,2008.03,13299.6,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Consumables,,,\n" +
                    "ECTA.S1GA1,2009.03,14203.1,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Consumables,,,\n" +
                    "ECTA.S1GA1,2010.03,15223.4,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Consumables,,,\n" +
                    "ECTA.S1GA1,2011.03,16075.6,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Consumables,,,\n" +
                    "ECTA.S1GA1,2012.03,17325,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Consumables,,,\n" +
                    "ECTA.S1GA1,2013.03,17972.9,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Consumables,,,\n" +
                    "ECTA.S1GA1,2014.03,18492.6,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Consumables,,,\n" +
                    "ECTA.S1GA1,2015.03,19560.4,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Consumables,,,\n" +
                    "ECTA.S1GA1,2016.03,20709.1,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Consumables,,,\n" +
                    "ECTA.S1GA1,2017.03,21756,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Consumables,,,\n" +
                    "ECTA.S1GA1,2018.03,23315.5,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Consumables,,,\n" +
                    "ECTA.S1GA1,2019.03,24215.6,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Consumables,,,\n" +
                    "ECTA.S1GA1,2020.03,25697.5,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Consumables,,,\n" +
                    "ECTA.S1GA2,2001.03,,,C,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Durables,,,\n" +
                    "ECTA.S1GA2,2002.03,,,C,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Durables,,,\n" +
                    "ECTA.S1GA2,2003.03,,,C,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Durables,,,\n" +
                    "ECTA.S1GA2,2004.03,8794.3,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Durables,,,\n" +
                    "ECTA.S1GA2,2005.03,9357.2,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Durables,,,\n" +
                    "ECTA.S1GA2,2006.03,9983.8,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Durables,,,\n" +
                    "ECTA.S1GA2,2007.03,10713.8,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Durables,,,\n" +
                    "ECTA.S1GA2,2008.03,11331.1,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Durables,,,\n" +
                    "ECTA.S1GA2,2009.03,11199.7,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Durables,,,\n" +
                    "ECTA.S1GA2,2010.03,11290.6,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Durables,,,\n" +
                    "ECTA.S1GA2,2011.03,11361.6,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Durables,,,\n" +
                    "ECTA.S1GA2,2012.03,11752.5,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Durables,,,\n" +
                    "ECTA.S1GA2,2013.03,12149.1,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Durables,,,\n" +
                    "ECTA.S1GA2,2014.03,12814.3,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Durables,,,\n" +
                    "ECTA.S1GA2,2015.03,13329.6,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Durables,,,\n" +
                    "ECTA.S1GA2,2016.03,14129.8,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Durables,,,\n" +
                    "ECTA.S1GA2,2017.03,14768.9,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Durables,,,\n" +
                    "ECTA.S1GA2,2018.03,15575.5,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Durables,,,\n" +
                    "ECTA.S1GA2,2019.03,16299.5,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Durables,,,\n" +
                    "ECTA.S1GA2,2020.03,17215.3,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Durables,,,\n" +
                    "ECTA.S1GA3,2001.03,,,C,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Hospitality,,,\n" +
                    "ECTA.S1GA3,2002.03,,,C,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Hospitality,,,\n" +
                    "ECTA.S1GA3,2003.03,,,C,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Hospitality,,,\n" +
                    "ECTA.S1GA3,2004.03,3798.7,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Hospitality,,,\n" +
                    "ECTA.S1GA3,2005.03,4219.6,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Hospitality,,,\n" +
                    "ECTA.S1GA3,2006.03,4572.6,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Hospitality,,,\n" +
                    "ECTA.S1GA3,2007.03,5157.3,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Hospitality,,,\n" +
                    "ECTA.S1GA3,2008.03,5714.4,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Hospitality,,,\n" +
                    "ECTA.S1GA3,2009.03,5898.5,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Hospitality,,,\n" +
                    "ECTA.S1GA3,2010.03,6145.8,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Hospitality,,,\n" +
                    "ECTA.S1GA3,2011.03,6470,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Hospitality,,,\n" +
                    "ECTA.S1GA3,2012.03,7070.6,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Hospitality,,,\n" +
                    "ECTA.S1GA3,2013.03,7438.5,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Hospitality,,,\n" +
                    "ECTA.S1GA3,2014.03,8135.5,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Hospitality,,,\n" +
                    "ECTA.S1GA3,2015.03,8964.9,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Hospitality,,,\n" +
                    "ECTA.S1GA3,2016.03,9952.2,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Hospitality,,,\n" +
                    "ECTA.S1GA3,2017.03,11184.3,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Hospitality,,,\n" +
                    "ECTA.S1GA3,2018.03,12024.1,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Hospitality,,,\n" +
                    "ECTA.S1GA3,2019.03,12777.4,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Hospitality,,,\n" +
                    "ECTA.S1GA3,2020.03,13018.3,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Hospitality,,,\n" +
                    "ECTA.S1GA4,2001.03,,,C,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Services,,,\n" +
                    "ECTA.S1GA4,2002.03,,,C,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Services,,,\n" +
                    "ECTA.S1GA4,2003.03,,,C,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Services,,,\n" +
                    "ECTA.S1GA4,2004.03,981.4,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Services,,,\n" +
                    "ECTA.S1GA4,2005.03,1129.4,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Services,,,\n" +
                    "ECTA.S1GA4,2006.03,1269.5,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Services,,,\n" +
                    "ECTA.S1GA4,2007.03,1412.3,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Services,,,\n" +
                    "ECTA.S1GA4,2008.03,1563.6,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Services,,,\n" +
                    "ECTA.S1GA4,2009.03,1671.6,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Services,,,\n" +
                    "ECTA.S1GA4,2010.03,1762.2,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Services,,,\n" +
                    "ECTA.S1GA4,2011.03,1846.8,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Services,,,\n" +
                    "ECTA.S1GA4,2012.03,1975,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Services,,,\n" +
                    "ECTA.S1GA4,2013.03,2096.4,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Services,,,\n" +
                    "ECTA.S1GA4,2014.03,2358.2,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Services,,,\n" +
                    "ECTA.S1GA4,2015.03,2591.7,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Services,,,\n" +
                    "ECTA.S1GA4,2016.03,2796.7,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Services,,,\n" +
                    "ECTA.S1GA4,2017.03,3023.5,,F,Dollars,6,Electronic Card Transactions (ANZSIC06) - ECT,Values - Electronic card transactions A/S/T by industry group,Actual,Services,,,\n" +
                    "ECTQ.S4AXP,2019.12,43.6,,F,Percent,0,Electronic Card Transactions (ANZSIC06) - ECT,Electronic card transactions by mean and proportion,Actual,Debit card usage as a proportion of total ECT value,Proportion (%),,\n" +
                    "ECTQ.S4AXP,2020.03,42.8,,F,Percent,0,Electronic Card Transactions (ANZSIC06) - ECT,Electronic card transactions by mean and proportion,Actual,Debit card usage as a proportion of total ECT value,Proportion (%),,\n";
        }


    }

    

    private HttpHeaders createHttpHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        if (token != null) {
            headers.add("Authorization", "Bearer " + token);
        }

        return headers;
    }

    HttpHeaders createHeaders(String username, String password){
        return new HttpHeaders() {{
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(Charset.forName("US-ASCII")) );
            String authHeader = "Basic " + new String( encodedAuth );
            set( "Authorization", authHeader );
        }};
    }

    @Override
    public String getConfiguredResourceId() {
        return this.configurationId;
    }

    @Override
    public void setConfiguredResourceId(String id) {
        this.configurationId = id;
    }

    @Override
    public HttpRequestBrokerConfiguration getConfiguration() {
        return this.configuration;
    }

    @Override
    public void setConfiguration(HttpRequestBrokerConfiguration configuration) {
        this.configuration = configuration;
    }
}
