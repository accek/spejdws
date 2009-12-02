
/**
 * SpejdServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5.1  Built on : Oct 19, 2009 (10:59:00 EDT)
 */

    package ipipan.spejdws.client;

    /**
     *  SpejdServiceCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class SpejdServiceCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public SpejdServiceCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public SpejdServiceCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for formatXmlAsHtml method
            * override this method for handling normal response from formatXmlAsHtml operation
            */
           public void receiveResultformatXmlAsHtml(
                    ipipan.spejdws.client.SpejdServiceStub.FormatXmlAsHtmlResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from formatXmlAsHtml operation
           */
            public void receiveErrorformatXmlAsHtml(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for listPredefinedRuleSets method
            * override this method for handling normal response from listPredefinedRuleSets operation
            */
           public void receiveResultlistPredefinedRuleSets(
                    ipipan.spejdws.client.SpejdServiceStub.ListPredefinedRuleSetsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from listPredefinedRuleSets operation
           */
            public void receiveErrorlistPredefinedRuleSets(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getPredefinedText method
            * override this method for handling normal response from getPredefinedText operation
            */
           public void receiveResultgetPredefinedText(
                    ipipan.spejdws.client.SpejdServiceStub.GetPredefinedTextResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getPredefinedText operation
           */
            public void receiveErrorgetPredefinedText(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getPredefinedRuleSet method
            * override this method for handling normal response from getPredefinedRuleSet operation
            */
           public void receiveResultgetPredefinedRuleSet(
                    ipipan.spejdws.client.SpejdServiceStub.GetPredefinedRuleSetResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getPredefinedRuleSet operation
           */
            public void receiveErrorgetPredefinedRuleSet(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for parse method
            * override this method for handling normal response from parse operation
            */
           public void receiveResultparse(
                    ipipan.spejdws.client.SpejdServiceStub.ParseResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from parse operation
           */
            public void receiveErrorparse(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for parseAndFormatAsHtml method
            * override this method for handling normal response from parseAndFormatAsHtml operation
            */
           public void receiveResultparseAndFormatAsHtml(
                    ipipan.spejdws.client.SpejdServiceStub.ParseAndFormatAsHtmlResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from parseAndFormatAsHtml operation
           */
            public void receiveErrorparseAndFormatAsHtml(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for listPredefinedTexts method
            * override this method for handling normal response from listPredefinedTexts operation
            */
           public void receiveResultlistPredefinedTexts(
                    ipipan.spejdws.client.SpejdServiceStub.ListPredefinedTextsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from listPredefinedTexts operation
           */
            public void receiveErrorlistPredefinedTexts(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getVersion method
            * override this method for handling normal response from getVersion operation
            */
           public void receiveResultgetVersion(
                    ipipan.spejdws.client.SpejdServiceStub.GetVersionResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getVersion operation
           */
            public void receiveErrorgetVersion(java.lang.Exception e) {
            }
                


    }
    