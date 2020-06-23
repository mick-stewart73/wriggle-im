# wriggle-im

####  Module Generated Metadata

The json data below is the actual runtime metadata of the module that is published to the
Ikasan Dashboard at the time the module is started.
```json
{
  "url": "http://localhost:8099/wriggle-im",
  "name": "wriggle-im",
  "description": "Wriggle Accounting",
  "version": null,
  "flows": [
    {
      "name": "Wriggle Request Inbound Flow",
      "consumer": {
        "componentName": "JMS Consumer",
        "description": null,
        "componentType": "org.ikasan.spec.component.endpoint.Consumer",
        "implementingClass": "org.ikasan.component.endpoint.jms.spring.consumer.JmsContainerConsumer",
        "configurationId": "wriggle-im-inboundRequestConsumer",
        "invokerConfigurationId": "wriggle-im_Wriggle Request Inbound Flow_JMS Consumer_1165847135_I",
        "decorators": null,
        "configurable": true
      },
      "transitions": [
        {
          "from": "Internal Request to JSON Converter",
          "to": "Internal Request JMS Producer",
          "name": "default"
        },
        {
          "from": "Inbound Request Splitter",
          "to": "Internal Request to JSON Converter",
          "name": "default"
        },
        {
          "from": "JSON to Wriggle Inbound Request Converter",
          "to": "Inbound Request Splitter",
          "name": "default"
        },
        {
          "from": "JMS Consumer",
          "to": "JSON to Wriggle Inbound Request Converter",
          "name": "default"
        }
      ],
      "flowElements": [
        {
          "componentName": "Internal Request JMS Producer",
          "description": null,
          "componentType": "org.ikasan.spec.component.endpoint.Producer",
          "implementingClass": "org.ikasan.component.endpoint.jms.spring.producer.ArjunaJmsTemplateProducer",
          "configurationId": "wriggle-im-splitInboundRequestProducer",
          "invokerConfigurationId": "wriggle-im_Wriggle Request Inbound Flow_Internal Request JMS Producer_1165847135_I",
          "decorators": null,
          "configurable": true
        },
        {
          "componentName": "Internal Request to JSON Converter",
          "description": null,
          "componentType": "org.ikasan.spec.component.transformation.Converter",
          "implementingClass": "au.com.waddle.wriggle.components.converter.WriggleInternalRequestToJsonConverter",
          "configurationId": null,
          "invokerConfigurationId": "wriggle-im_Wriggle Request Inbound Flow_Internal Request to JSON Converter_1165847135_I",
          "decorators": null,
          "configurable": false
        },
        {
          "componentName": "Inbound Request Splitter",
          "description": null,
          "componentType": "org.ikasan.spec.component.splitting.Splitter",
          "implementingClass": "au.com.waddle.wriggle.components.splitter.InboundRequestSplitter",
          "configurationId": null,
          "invokerConfigurationId": "wriggle-im_Wriggle Request Inbound Flow_Inbound Request Splitter_-91481914_I",
          "decorators": null,
          "configurable": false
        },
        {
          "componentName": "JSON to Wriggle Inbound Request Converter",
          "description": null,
          "componentType": "org.ikasan.spec.component.transformation.Converter",
          "implementingClass": "au.com.waddle.wriggle.components.converter.InboundJsonToWriggleInboundRequestConverter",
          "configurationId": null,
          "invokerConfigurationId": "wriggle-im_Wriggle Request Inbound Flow_JSON to Wriggle Inbound Request Converter_1165847135_I",
          "decorators": null,
          "configurable": false
        },
        {
          "componentName": "JMS Consumer",
          "description": null,
          "componentType": "org.ikasan.spec.component.endpoint.Consumer",
          "implementingClass": "org.ikasan.component.endpoint.jms.spring.consumer.JmsContainerConsumer",
          "configurationId": "wriggle-im-inboundRequestConsumer",
          "invokerConfigurationId": "wriggle-im_Wriggle Request Inbound Flow_JMS Consumer_1165847135_I",
          "decorators": null,
          "configurable": true
        }
      ],
      "configurationId": "wriggle-im-Wriggle Request Inbound Flow"
    },
    {
      "name": "Wriggle Request Router Flow",
      "consumer": {
        "componentName": "JMS Consumer",
        "description": null,
        "componentType": "org.ikasan.spec.component.endpoint.Consumer",
        "implementingClass": "org.ikasan.component.endpoint.jms.spring.consumer.JmsContainerConsumer",
        "configurationId": "wriggle-im-routerFlowConsumer",
        "invokerConfigurationId": "wriggle-im_Wriggle Request Router Flow_JMS Consumer_1165847135_I",
        "decorators": null,
        "configurable": true
      },
      "transitions": [
        {
          "from": "Wriggle Company Inbound Request to JSON Converter",
          "to": "Company Request Producer",
          "name": "default"
        },
        {
          "from": "Request Type Router",
          "to": "Wriggle Company Inbound Request to JSON Converter",
          "name": "company"
        },
        {
          "from": "Wriggle Account Inbound Request to JSON Converter",
          "to": "Bank Request Producer",
          "name": "default"
        },
        {
          "from": "Request Type Router",
          "to": "Wriggle Account Inbound Request to JSON Converter",
          "name": "bank"
        },
        {
          "from": "Wriggle Customer Inbound Request to JSON Converter",
          "to": "Customer Request Producer",
          "name": "default"
        },
        {
          "from": "Request Type Router",
          "to": "Wriggle Customer Inbound Request to JSON Converter",
          "name": "customer"
        },
        {
          "from": "Wriggle Invoice Inbound Request to JSON Converter",
          "to": "Invoice Request Producer",
          "name": "default"
        },
        {
          "from": "Request Type Router",
          "to": "Wriggle Invoice Inbound Request to JSON Converter",
          "name": "invoice"
        },
        {
          "from": "Wriggle Ledger Inbound Request to JSON Converter",
          "to": "Ledger Request Producer",
          "name": "default"
        },
        {
          "from": "Request Type Router",
          "to": "Wriggle Ledger Inbound Request to JSON Converter",
          "name": "ledger"
        },
        {
          "from": "Request Type Router",
          "to": "Ignore Unknown Request",
          "name": "default"
        },
        {
          "from": "Exception Generating Broker",
          "to": "Request Type Router",
          "name": "default"
        },
        {
          "from": "JSON to Wriggle Inbound Request Converter",
          "to": "Exception Generating Broker",
          "name": "default"
        },
        {
          "from": "JMS Consumer",
          "to": "JSON to Wriggle Inbound Request Converter",
          "name": "default"
        }
      ],
      "flowElements": [
        {
          "componentName": "Company Request Producer",
          "description": null,
          "componentType": "org.ikasan.spec.component.endpoint.Producer",
          "implementingClass": "org.ikasan.component.endpoint.jms.spring.producer.ArjunaJmsTemplateProducer",
          "configurationId": "wriggle-im-companyRequestProducer",
          "invokerConfigurationId": "wriggle-im_Wriggle Request Router Flow_Company Request Producer_1165847135_I",
          "decorators": [
            {
              "type": "Wiretap",
              "name": "BEFORE Company Request Producer",
              "configurationId": "4",
              "configurable": true
            }
          ],
          "configurable": true
        },
        {
          "componentName": "Wriggle Company Inbound Request to JSON Converter",
          "description": null,
          "componentType": "org.ikasan.spec.component.transformation.Converter",
          "implementingClass": "au.com.waddle.wriggle.components.converter.WriggleInternalRequestToJsonConverter",
          "configurationId": null,
          "invokerConfigurationId": "wriggle-im_Wriggle Request Router Flow_Wriggle Company Inbound Request to JSON Converter_1165847135_I",
          "decorators": null,
          "configurable": false
        },
        {
          "componentName": "Bank Request Producer",
          "description": null,
          "componentType": "org.ikasan.spec.component.endpoint.Producer",
          "implementingClass": "org.ikasan.component.endpoint.jms.spring.producer.ArjunaJmsTemplateProducer",
          "configurationId": "wriggle-im-bankRequestProducer",
          "invokerConfigurationId": "wriggle-im_Wriggle Request Router Flow_Bank Request Producer_1165847135_I",
          "decorators": [
            {
              "type": "Wiretap",
              "name": "BEFORE Bank Request Producer",
              "configurationId": "3",
              "configurable": true
            }
          ],
          "configurable": true
        },
        {
          "componentName": "Wriggle Account Inbound Request to JSON Converter",
          "description": null,
          "componentType": "org.ikasan.spec.component.transformation.Converter",
          "implementingClass": "au.com.waddle.wriggle.components.converter.WriggleInternalRequestToJsonConverter",
          "configurationId": null,
          "invokerConfigurationId": "wriggle-im_Wriggle Request Router Flow_Wriggle Account Inbound Request to JSON Converter_1165847135_I",
          "decorators": null,
          "configurable": false
        },
        {
          "componentName": "Customer Request Producer",
          "description": null,
          "componentType": "org.ikasan.spec.component.endpoint.Producer",
          "implementingClass": "org.ikasan.component.endpoint.jms.spring.producer.ArjunaJmsTemplateProducer",
          "configurationId": "wriggle-im-customerRequestProducer",
          "invokerConfigurationId": "wriggle-im_Wriggle Request Router Flow_Customer Request Producer_1165847135_I",
          "decorators": [
            {
              "type": "Wiretap",
              "name": "AFTER Customer Request Producer",
              "configurationId": "6",
              "configurable": true
            }
          ],
          "configurable": true
        },
        {
          "componentName": "Wriggle Customer Inbound Request to JSON Converter",
          "description": null,
          "componentType": "org.ikasan.spec.component.transformation.Converter",
          "implementingClass": "au.com.waddle.wriggle.components.converter.WriggleInternalRequestToJsonConverter",
          "configurationId": null,
          "invokerConfigurationId": "wriggle-im_Wriggle Request Router Flow_Wriggle Customer Inbound Request to JSON Converter_1165847135_I",
          "decorators": null,
          "configurable": false
        },
        {
          "componentName": "Invoice Request Producer",
          "description": null,
          "componentType": "org.ikasan.spec.component.endpoint.Producer",
          "implementingClass": "org.ikasan.component.endpoint.jms.spring.producer.ArjunaJmsTemplateProducer",
          "configurationId": "wriggle-im-invoiceRequestProducer",
          "invokerConfigurationId": "wriggle-im_Wriggle Request Router Flow_Invoice Request Producer_1165847135_I",
          "decorators": [
            {
              "type": "Wiretap",
              "name": "BEFORE Invoice Request Producer",
              "configurationId": "5",
              "configurable": true
            }
          ],
          "configurable": true
        },
        {
          "componentName": "Wriggle Invoice Inbound Request to JSON Converter",
          "description": null,
          "componentType": "org.ikasan.spec.component.transformation.Converter",
          "implementingClass": "au.com.waddle.wriggle.components.converter.WriggleInternalRequestToJsonConverter",
          "configurationId": null,
          "invokerConfigurationId": "wriggle-im_Wriggle Request Router Flow_Wriggle Invoice Inbound Request to JSON Converter_1165847135_I",
          "decorators": null,
          "configurable": false
        },
        {
          "componentName": "Ledger Request Producer",
          "description": null,
          "componentType": "org.ikasan.spec.component.endpoint.Producer",
          "implementingClass": "org.ikasan.component.endpoint.jms.spring.producer.ArjunaJmsTemplateProducer",
          "configurationId": "wriggle-im-ledgeRequestProducer",
          "invokerConfigurationId": "wriggle-im_Wriggle Request Router Flow_Ledger Request Producer_1165847135_I",
          "decorators": [
            {
              "type": "Wiretap",
              "name": "BEFORE Ledger Request Producer",
              "configurationId": "2",
              "configurable": true
            }
          ],
          "configurable": true
        },
        {
          "componentName": "Wriggle Ledger Inbound Request to JSON Converter",
          "description": null,
          "componentType": "org.ikasan.spec.component.transformation.Converter",
          "implementingClass": "au.com.waddle.wriggle.components.converter.WriggleInternalRequestToJsonConverter",
          "configurationId": null,
          "invokerConfigurationId": "wriggle-im_Wriggle Request Router Flow_Wriggle Ledger Inbound Request to JSON Converter_1165847135_I",
          "decorators": null,
          "configurable": false
        },
        {
          "componentName": "Ignore Unknown Request",
          "description": null,
          "componentType": "org.ikasan.spec.component.endpoint.Producer",
          "implementingClass": "org.ikasan.component.endpoint.util.producer.DevNull",
          "configurationId": null,
          "invokerConfigurationId": "wriggle-im_Wriggle Request Router Flow_Ignore Unknown Request_1165847135_I",
          "decorators": null,
          "configurable": false
        },
        {
          "componentName": "Request Type Router",
          "description": null,
          "componentType": "org.ikasan.spec.component.routing.SingleRecipientRouter",
          "implementingClass": "au.com.waddle.wriggle.components.router.RequestTypeRouter",
          "configurationId": null,
          "invokerConfigurationId": "wriggle-im_Wriggle Request Router Flow_Request Type Router_1165847135_I",
          "decorators": null,
          "configurable": false
        },
        {
          "componentName": "Exception Generating Broker",
          "description": null,
          "componentType": "org.ikasan.spec.component.endpoint.Broker",
          "implementingClass": "au.com.waddle.wriggle.ExceptionGeneratingBroker",
          "configurationId": null,
          "invokerConfigurationId": "wriggle-im_Wriggle Request Router Flow_Exception Generating Broker_1165847135_I",
          "decorators": null,
          "configurable": false
        },
        {
          "componentName": "JSON to Wriggle Inbound Request Converter",
          "description": null,
          "componentType": "org.ikasan.spec.component.transformation.Converter",
          "implementingClass": "au.com.waddle.wriggle.components.converter.JsonToWriggleInternalRequestConverter",
          "configurationId": null,
          "invokerConfigurationId": "wriggle-im_Wriggle Request Router Flow_JSON to Wriggle Inbound Request Converter_1165847135_I",
          "decorators": null,
          "configurable": false
        },
        {
          "componentName": "JMS Consumer",
          "description": null,
          "componentType": "org.ikasan.spec.component.endpoint.Consumer",
          "implementingClass": "org.ikasan.component.endpoint.jms.spring.consumer.JmsContainerConsumer",
          "configurationId": "wriggle-im-routerFlowConsumer",
          "invokerConfigurationId": "wriggle-im_Wriggle Request Router Flow_JMS Consumer_1165847135_I",
          "decorators": null,
          "configurable": true
        }
      ],
      "configurationId": "wriggle-im-Wriggle Request Router Flow"
    },
    {
      "name": "Wriggle Customer HTTP Request Flow",
      "consumer": {
        "componentName": "JMS Consumer",
        "description": null,
        "componentType": "org.ikasan.spec.component.endpoint.Consumer",
        "implementingClass": "org.ikasan.component.endpoint.jms.spring.consumer.JmsContainerConsumer",
        "configurationId": "wriggle-im-customerHttpRequestFlowConsumer",
        "invokerConfigurationId": "wriggle-im_Wriggle Customer HTTP Request Flow_JMS Consumer_1165847135_I",
        "decorators": null,
        "configurable": true
      },
      "transitions": [
        {
          "from": "HTTP Request Broker",
          "to": "Public Customer HTTP Request JMS Producer",
          "name": "default"
        },
        {
          "from": "JSON to Wriggle Inbound Request Converter",
          "to": "HTTP Request Broker",
          "name": "default"
        },
        {
          "from": "JMS Consumer",
          "to": "JSON to Wriggle Inbound Request Converter",
          "name": "default"
        }
      ],
      "flowElements": [
        {
          "componentName": "Public Customer HTTP Request JMS Producer",
          "description": null,
          "componentType": "org.ikasan.spec.component.endpoint.Producer",
          "implementingClass": "org.ikasan.component.endpoint.jms.spring.producer.ArjunaJmsTemplateProducer",
          "configurationId": "wriggle-im-customerHttpRequestFlowProducer",
          "invokerConfigurationId": "wriggle-im_Wriggle Customer HTTP Request Flow_Public Customer HTTP Request JMS Producer_1165847135_I",
          "decorators": [
            {
              "type": "Wiretap",
              "name": "BEFORE Public Customer HTTP Request JMS Producer",
              "configurationId": "7",
              "configurable": true
            }
          ],
          "configurable": true
        },
        {
          "componentName": "HTTP Request Broker",
          "description": null,
          "componentType": "org.ikasan.spec.component.endpoint.Broker",
          "implementingClass": "au.com.waddle.wriggle.components.broker.HttpRequestBroker",
          "configurationId": "wriggle-im-customerHttpRequestFlowHTTPRequestBroker",
          "invokerConfigurationId": "wriggle-im_Wriggle Customer HTTP Request Flow_HTTP Request Broker_1165847135_I",
          "decorators": null,
          "configurable": true
        },
        {
          "componentName": "JSON to Wriggle Inbound Request Converter",
          "description": null,
          "componentType": "org.ikasan.spec.component.transformation.Converter",
          "implementingClass": "au.com.waddle.wriggle.components.converter.JsonToWriggleInternalRequestConverter",
          "configurationId": null,
          "invokerConfigurationId": "wriggle-im_Wriggle Customer HTTP Request Flow_JSON to Wriggle Inbound Request Converter_1165847135_I",
          "decorators": null,
          "configurable": false
        },
        {
          "componentName": "JMS Consumer",
          "description": null,
          "componentType": "org.ikasan.spec.component.endpoint.Consumer",
          "implementingClass": "org.ikasan.component.endpoint.jms.spring.consumer.JmsContainerConsumer",
          "configurationId": "wriggle-im-customerHttpRequestFlowConsumer",
          "invokerConfigurationId": "wriggle-im_Wriggle Customer HTTP Request Flow_JMS Consumer_1165847135_I",
          "decorators": null,
          "configurable": true
        }
      ],
      "configurationId": "wriggle-im-Wriggle Customer HTTP Request Flow"
    },
    {
      "name": "Wriggle Ledger HTTP Request Flow",
      "consumer": {
        "componentName": "JMS Consumer",
        "description": null,
        "componentType": "org.ikasan.spec.component.endpoint.Consumer",
        "implementingClass": "org.ikasan.component.endpoint.jms.spring.consumer.JmsContainerConsumer",
        "configurationId": "wriggle-im-ledgerHttpRequestFlowConsumer",
        "invokerConfigurationId": "wriggle-im_Wriggle Ledger HTTP Request Flow_JMS Consumer_1165847135_I",
        "decorators": null,
        "configurable": true
      },
      "transitions": [
        {
          "from": "HTTP Request Broker",
          "to": "Public Ledger HTTP Request JMS Producer",
          "name": "default"
        },
        {
          "from": "JSON to Wriggle Inbound Request Converter",
          "to": "HTTP Request Broker",
          "name": "default"
        },
        {
          "from": "JMS Consumer",
          "to": "JSON to Wriggle Inbound Request Converter",
          "name": "default"
        }
      ],
      "flowElements": [
        {
          "componentName": "Public Ledger HTTP Request JMS Producer",
          "description": null,
          "componentType": "org.ikasan.spec.component.endpoint.Producer",
          "implementingClass": "org.ikasan.component.endpoint.jms.spring.producer.ArjunaJmsTemplateProducer",
          "configurationId": "wriggle-im-ledgerHttpRequestFlowProducer",
          "invokerConfigurationId": "wriggle-im_Wriggle Ledger HTTP Request Flow_Public Ledger HTTP Request JMS Producer_1165847135_I",
          "decorators": [
            {
              "type": "Wiretap",
              "name": "BEFORE Public Ledger HTTP Request JMS Producer",
              "configurationId": "8",
              "configurable": true
            }
          ],
          "configurable": true
        },
        {
          "componentName": "HTTP Request Broker",
          "description": null,
          "componentType": "org.ikasan.spec.component.endpoint.Broker",
          "implementingClass": "au.com.waddle.wriggle.components.broker.HttpRequestBroker",
          "configurationId": "wriggle-im-ledgerHttpRequestFlowHTTPRequestBroker",
          "invokerConfigurationId": "wriggle-im_Wriggle Ledger HTTP Request Flow_HTTP Request Broker_1165847135_I",
          "decorators": null,
          "configurable": true
        },
        {
          "componentName": "JSON to Wriggle Inbound Request Converter",
          "description": null,
          "componentType": "org.ikasan.spec.component.transformation.Converter",
          "implementingClass": "au.com.waddle.wriggle.components.converter.JsonToWriggleInternalRequestConverter",
          "configurationId": null,
          "invokerConfigurationId": "wriggle-im_Wriggle Ledger HTTP Request Flow_JSON to Wriggle Inbound Request Converter_1165847135_I",
          "decorators": null,
          "configurable": false
        },
        {
          "componentName": "JMS Consumer",
          "description": null,
          "componentType": "org.ikasan.spec.component.endpoint.Consumer",
          "implementingClass": "org.ikasan.component.endpoint.jms.spring.consumer.JmsContainerConsumer",
          "configurationId": "wriggle-im-ledgerHttpRequestFlowConsumer",
          "invokerConfigurationId": "wriggle-im_Wriggle Ledger HTTP Request Flow_JMS Consumer_1165847135_I",
          "decorators": null,
          "configurable": true
        }
      ],
      "configurationId": "wriggle-im-Wriggle Ledger HTTP Request Flow"
    },
    {
      "name": "Wriggle Company HTTP Request Flow",
      "consumer": {
        "componentName": "JMS Consumer",
        "description": null,
        "componentType": "org.ikasan.spec.component.endpoint.Consumer",
        "implementingClass": "org.ikasan.component.endpoint.jms.spring.consumer.JmsContainerConsumer",
        "configurationId": "wriggle-im-companyHttpRequestFlowConsumer",
        "invokerConfigurationId": "wriggle-im_Wriggle Company HTTP Request Flow_JMS Consumer_1165847135_I",
        "decorators": null,
        "configurable": true
      },
      "transitions": [
        {
          "from": "HTTP Request Broker",
          "to": "Public Company HTTP Request JMS Producer",
          "name": "default"
        },
        {
          "from": "JSON to Wriggle Inbound Request Converter",
          "to": "HTTP Request Broker",
          "name": "default"
        },
        {
          "from": "JMS Consumer",
          "to": "JSON to Wriggle Inbound Request Converter",
          "name": "default"
        }
      ],
      "flowElements": [
        {
          "componentName": "Public Company HTTP Request JMS Producer",
          "description": null,
          "componentType": "org.ikasan.spec.component.endpoint.Producer",
          "implementingClass": "org.ikasan.component.endpoint.jms.spring.producer.ArjunaJmsTemplateProducer",
          "configurationId": "wriggle-im-companyHttpRequestFlowProducer",
          "invokerConfigurationId": "wriggle-im_Wriggle Company HTTP Request Flow_Public Company HTTP Request JMS Producer_1165847135_I",
          "decorators": [
            {
              "type": "Wiretap",
              "name": "BEFORE Public Company HTTP Request JMS Producer",
              "configurationId": "9",
              "configurable": true
            }
          ],
          "configurable": true
        },
        {
          "componentName": "HTTP Request Broker",
          "description": null,
          "componentType": "org.ikasan.spec.component.endpoint.Broker",
          "implementingClass": "au.com.waddle.wriggle.components.broker.HttpRequestBroker",
          "configurationId": "wriggle-im-companyHttpRequestFlowHTTPRequestBroker",
          "invokerConfigurationId": "wriggle-im_Wriggle Company HTTP Request Flow_HTTP Request Broker_1165847135_I",
          "decorators": null,
          "configurable": true
        },
        {
          "componentName": "JSON to Wriggle Inbound Request Converter",
          "description": null,
          "componentType": "org.ikasan.spec.component.transformation.Converter",
          "implementingClass": "au.com.waddle.wriggle.components.converter.JsonToWriggleInternalRequestConverter",
          "configurationId": null,
          "invokerConfigurationId": "wriggle-im_Wriggle Company HTTP Request Flow_JSON to Wriggle Inbound Request Converter_1165847135_I",
          "decorators": null,
          "configurable": false
        },
        {
          "componentName": "JMS Consumer",
          "description": null,
          "componentType": "org.ikasan.spec.component.endpoint.Consumer",
          "implementingClass": "org.ikasan.component.endpoint.jms.spring.consumer.JmsContainerConsumer",
          "configurationId": "wriggle-im-companyHttpRequestFlowConsumer",
          "invokerConfigurationId": "wriggle-im_Wriggle Company HTTP Request Flow_JMS Consumer_1165847135_I",
          "decorators": null,
          "configurable": true
        }
      ],
      "configurationId": "wriggle-im-Wriggle Company HTTP Request Flow"
    },
    {
      "name": "Wriggle Account HTTP Request Flow",
      "consumer": {
        "componentName": "JMS Consumer",
        "description": null,
        "componentType": "org.ikasan.spec.component.endpoint.Consumer",
        "implementingClass": "org.ikasan.component.endpoint.jms.spring.consumer.JmsContainerConsumer",
        "configurationId": "wriggle-im-accountHttpRequestFlowConsumer",
        "invokerConfigurationId": "wriggle-im_Wriggle Account HTTP Request Flow_JMS Consumer_1165847135_I",
        "decorators": null,
        "configurable": true
      },
      "transitions": [
        {
          "from": "HTTP Request Broker",
          "to": "Public Account HTTP Request JMS Producer",
          "name": "default"
        },
        {
          "from": "JSON to Wriggle Inbound Request Converter",
          "to": "HTTP Request Broker",
          "name": "default"
        },
        {
          "from": "JMS Consumer",
          "to": "JSON to Wriggle Inbound Request Converter",
          "name": "default"
        }
      ],
      "flowElements": [
        {
          "componentName": "Public Account HTTP Request JMS Producer",
          "description": null,
          "componentType": "org.ikasan.spec.component.endpoint.Producer",
          "implementingClass": "org.ikasan.component.endpoint.jms.spring.producer.ArjunaJmsTemplateProducer",
          "configurationId": "wriggle-im-accountHttpRequestFlowProducer",
          "invokerConfigurationId": "wriggle-im_Wriggle Account HTTP Request Flow_Public Account HTTP Request JMS Producer_1165847135_I",
          "decorators": [
            {
              "type": "Wiretap",
              "name": "BEFORE Public Account HTTP Request JMS Producer",
              "configurationId": "10",
              "configurable": true
            }
          ],
          "configurable": true
        },
        {
          "componentName": "HTTP Request Broker",
          "description": null,
          "componentType": "org.ikasan.spec.component.endpoint.Broker",
          "implementingClass": "au.com.waddle.wriggle.components.broker.HttpRequestBroker",
          "configurationId": "wriggle-im-accountHttpRequestFlowHTTPRequestBroker",
          "invokerConfigurationId": "wriggle-im_Wriggle Account HTTP Request Flow_HTTP Request Broker_1165847135_I",
          "decorators": null,
          "configurable": true
        },
        {
          "componentName": "JSON to Wriggle Inbound Request Converter",
          "description": null,
          "componentType": "org.ikasan.spec.component.transformation.Converter",
          "implementingClass": "au.com.waddle.wriggle.components.converter.JsonToWriggleInternalRequestConverter",
          "configurationId": null,
          "invokerConfigurationId": "wriggle-im_Wriggle Account HTTP Request Flow_JSON to Wriggle Inbound Request Converter_1165847135_I",
          "decorators": null,
          "configurable": false
        },
        {
          "componentName": "JMS Consumer",
          "description": null,
          "componentType": "org.ikasan.spec.component.endpoint.Consumer",
          "implementingClass": "org.ikasan.component.endpoint.jms.spring.consumer.JmsContainerConsumer",
          "configurationId": "wriggle-im-accountHttpRequestFlowConsumer",
          "invokerConfigurationId": "wriggle-im_Wriggle Account HTTP Request Flow_JMS Consumer_1165847135_I",
          "decorators": null,
          "configurable": true
        }
      ],
      "configurationId": "wriggle-im-Wriggle Account HTTP Request Flow"
    },
    {
      "name": "Wriggle Invoice HTTP Request Flow",
      "consumer": {
        "componentName": "JMS Consumer",
        "description": null,
        "componentType": "org.ikasan.spec.component.endpoint.Consumer",
        "implementingClass": "org.ikasan.component.endpoint.jms.spring.consumer.JmsContainerConsumer",
        "configurationId": "wriggle-im-InvoiceHttpRequestFlowConsumer",
        "invokerConfigurationId": "wriggle-im_Wriggle Invoice HTTP Request Flow_JMS Consumer_1165847135_I",
        "decorators": null,
        "configurable": true
      },
      "transitions": [
        {
          "from": "HTTP Request Broker",
          "to": "Public Invoice HTTP Request JMS Producer",
          "name": "default"
        },
        {
          "from": "JSON to Wriggle Inbound Request Converter",
          "to": "HTTP Request Broker",
          "name": "default"
        },
        {
          "from": "JMS Consumer",
          "to": "JSON to Wriggle Inbound Request Converter",
          "name": "default"
        }
      ],
      "flowElements": [
        {
          "componentName": "Public Invoice HTTP Request JMS Producer",
          "description": null,
          "componentType": "org.ikasan.spec.component.endpoint.Producer",
          "implementingClass": "org.ikasan.component.endpoint.jms.spring.producer.ArjunaJmsTemplateProducer",
          "configurationId": "wriggle-im-InvoiceHttpRequestFlowProducer",
          "invokerConfigurationId": "wriggle-im_Wriggle Invoice HTTP Request Flow_Public Invoice HTTP Request JMS Producer_1165847135_I",
          "decorators": [
            {
              "type": "Wiretap",
              "name": "AFTER Public Invoice HTTP Request JMS Producer",
              "configurationId": "11",
              "configurable": true
            }
          ],
          "configurable": true
        },
        {
          "componentName": "HTTP Request Broker",
          "description": null,
          "componentType": "org.ikasan.spec.component.endpoint.Broker",
          "implementingClass": "au.com.waddle.wriggle.components.broker.HttpRequestBroker",
          "configurationId": "wriggle-im-InvoiceHttpRequestFlowHTTPRequestBroker",
          "invokerConfigurationId": "wriggle-im_Wriggle Invoice HTTP Request Flow_HTTP Request Broker_1165847135_I",
          "decorators": null,
          "configurable": true
        },
        {
          "componentName": "JSON to Wriggle Inbound Request Converter",
          "description": null,
          "componentType": "org.ikasan.spec.component.transformation.Converter",
          "implementingClass": "au.com.waddle.wriggle.components.converter.JsonToWriggleInternalRequestConverter",
          "configurationId": null,
          "invokerConfigurationId": "wriggle-im_Wriggle Invoice HTTP Request Flow_JSON to Wriggle Inbound Request Converter_1165847135_I",
          "decorators": null,
          "configurable": false
        },
        {
          "componentName": "JMS Consumer",
          "description": null,
          "componentType": "org.ikasan.spec.component.endpoint.Consumer",
          "implementingClass": "org.ikasan.component.endpoint.jms.spring.consumer.JmsContainerConsumer",
          "configurationId": "wriggle-im-InvoiceHttpRequestFlowConsumer",
          "invokerConfigurationId": "wriggle-im_Wriggle Invoice HTTP Request Flow_JMS Consumer_1165847135_I",
          "decorators": null,
          "configurable": true
        }
      ],
      "configurationId": "wriggle-im-Wriggle Invoice HTTP Request Flow"
    },
    {
      "name": "Scheduled Flow",
      "consumer": {
        "componentName": "Scheduled Consumer",
        "description": null,
        "componentType": "org.ikasan.spec.component.endpoint.Consumer",
        "implementingClass": "org.ikasan.component.endpoint.quartz.consumer.ScheduledConsumer",
        "configurationId": "wriggle-im-scheduledFlowConsumer",
        "invokerConfigurationId": "wriggle-im_Scheduled Flow_Scheduled Consumer_1165847135_I",
        "decorators": null,
        "configurable": true
      },
      "transitions": [
        {
          "from": "Scheduled Consumer",
          "to": "Scheduled Inbound JMS Producer",
          "name": "default"
        }
      ],
      "flowElements": [
        {
          "componentName": "Scheduled Inbound JMS Producer",
          "description": null,
          "componentType": "org.ikasan.spec.component.endpoint.Producer",
          "implementingClass": "org.ikasan.component.endpoint.jms.spring.producer.ArjunaJmsTemplateProducer",
          "configurationId": "wriggle-im-inboundJmsProducer",
          "invokerConfigurationId": "wriggle-im_Scheduled Flow_Scheduled Inbound JMS Producer_1165847135_I",
          "decorators": null,
          "configurable": true
        },
        {
          "componentName": "Scheduled Consumer",
          "description": null,
          "componentType": "org.ikasan.spec.component.endpoint.Consumer",
          "implementingClass": "org.ikasan.component.endpoint.quartz.consumer.ScheduledConsumer",
          "configurationId": "wriggle-im-scheduledFlowConsumer",
          "invokerConfigurationId": "wriggle-im_Scheduled Flow_Scheduled Consumer_1165847135_I",
          "decorators": null,
          "configurable": true
        }
      ],
      "configurationId": "wriggle-im-Scheduled Flow"
    }
  ]
}
```