package cn.gyyx.action.service.client.account;

import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

public class SOAPEnvelopeHandler implements SOAPHandler<SOAPMessageContext> {
	
	public boolean handleMessage(SOAPMessageContext ctx) {

	    //出站，即客户端发出请求前，添加表头信息
	    Boolean request_p=(Boolean)ctx.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
	    if(request_p){
	        try {
	            SOAPMessage msg=ctx.getMessage();
	            SOAPEnvelope env=msg.getSOAPPart().getEnvelope();
	            
	            env.removeNamespaceDeclaration("S");
	            env.setPrefix("soap");
	            
	            SOAPBody body=env.getBody(); 
	            body.setPrefix("soap");
	            msg.saveChanges();
	            return true;    
	        } catch (Exception e) {    
	           e.printStackTrace();    
	        }
	    }    
	    return false;    
	    }
	    @Override
	    public boolean handleFault(SOAPMessageContext context) {

	        return false;
	    }
	    @Override
	    public void close(MessageContext context) {
	              
	    }
	    @Override
	    public Set<QName> getHeaders() {
	        return null;
	    }
}
