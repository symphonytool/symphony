//package eu.compassresearch.ide.cml.interpreter_plugin.model;
//
//import eu.compassresearch.ide.cml.interpreter_plugin.messaging.CmlDbgStatusMessage;
//import eu.compassresearch.ide.cml.interpreter_plugin.messaging.CmlMessage;
//import eu.compassresearch.ide.cml.interpreter_plugin.messaging.CmlMessageContainer;
//import eu.compassresearch.ide.cml.interpreter_plugin.messaging.CmlRequestMessage;
//
//interface Event
//{
//	public String getKey();
//}
//
//interface EventHandler<T>
//{
//	public boolean handleMessage(T message);
//};
//
//public class MessageHandlerDispatcher {
//
//	Map<String,Pair<> >
//	
//	
//	public boolean processMessage(CmlMessageContainer messageContainer)
//	{
//		boolean result = false;
//		
//		switch(messageContainer.getType())
//		{
//		case STATUS:
//			return dispatchMessageHandler(statusHandlers,messageContainer.getMessage(CmlDbgStatusMessage.class));
//		case REQUEST:
//			return dispatchMessageHandler(requestHandlers,messageContainer.getMessage(CmlRequestMessage.class));
//		default:
//			break;
//		}
//
//		return result;
//	}
//	
////	
////	public boolean processEvent(Event ev)
////	{
////		
////	}
//	
//}
