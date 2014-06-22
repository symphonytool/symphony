#ifndef CO_SIMULATION_TRANSPORT_LAYER_READER_WIRTER_LIB_HPP
#define CO_SIMULATION_TRANSPORT_LAYER_READER_WIRTER_LIB_HPP

#include<STRING>
#include <VECTOR>
#include<map>
#include "ICoSimulationTransportLayer.hpp"

namespace CoSimulationTransportLayer
{
	class CoSimMessageReaderWriterExecption : public std::runtime_error
    {
     public:
		 explicit CoSimMessageReaderWriterExecption (const std::string& what_arg):std::runtime_error(what_arg){}
    };


	class CoSimMessageReaderWriter
	{
	   public:
		   typedef enum {InspectMessage,ExecuteMessage,FinishedMessage,DisconnectMessage,registerSubSystemMessage} CoSimMessage;

		   CoSimMessageReaderWriter ():msg_data(""){}

		   friend std::ostream& operator<<(std::ostream& stream,const CoSimMessage& obj)
		   {
			   switch (obj)
			   {
			    case InspectMessage:
				{
					stream<<"InspectMessage";
					break;
				}
			    case ExecuteMessage:
				{
					stream<<"ExecuteMessage";
					break;
				}

			    case FinishedMessage:
				{
					stream<<"FinishedMessage";
					break;
				}			   
				case DisconnectMessage:
				{
					stream<<"DisconnectMessage";
					break;
				}
				case registerSubSystemMessage:
				{
					stream<<"registerSubSystemMessage";
					break;
				}	
				default:
					break;
			 };

			   return stream;
		   }

	  protected:
		  std::string msg_data;

	};

	class CoSimMessageReader:public CoSimMessageReaderWriter 
	{
	    public:
			CoSimMessageReader();
			void ReadRawMessage(const std::string& data);
			CoSimMessageReaderWriter::CoSimMessage geMessagetHeader()const;
			IChannelEventObject::ChannelEventObjectSmartPtr getExecuteMessageObject(IChannelEventObject::ChannelEventObjectSet& evns);

	    private:
			typedef std::map <std::string,CoSimMessageReaderWriter::CoSimMessage> MessageMappingMap;
			typedef std::pair<std::string,CoSimMessageReaderWriter::CoSimMessage> MessageMappingPair;
			MessageMappingMap msg_map;
	};

	 class CoSimMessageWriter: public CoSimMessageReaderWriter
	{
	    public:
			 std::string createRegisterSubSystemMessageReply(const std::string& n);
			 std::string CreateExecuteCompletedMessageReply(const std::string& n);
			 std::string createFinishedRequestMessageReply (const bool& status,const std::string& n);
			 std::string createInspectReplyMessage(const std::string& pname,const IChannelEventObject::ChannelEventObjectSet& evns);
	     
	     private:		 

	};

}

#endif