#ifndef I_CO_SIMULATION_TRANSPORT_LAYER_LIB_HPP
#define I_CO_SIMULATION_TRANSPORT_LAYER_LIB_HPP

#include<STRING>
#include <VECTOR>
#include <boost/shared_ptr.hpp>

namespace CoSimulationTransportLayer
{

	class IChannelEventObject
	{
	  public:

		  typedef boost::shared_ptr<IChannelEventObject> ChannelEventObjectSmartPtr;
		  typedef std::vector<ChannelEventObjectSmartPtr>ChannelEventObjectSet;
		  
		   virtual ~IChannelEventObject(){}

		  virtual std::string getChannelName()const = 0;
		  virtual std::string getOperationType () const = 0;
		  virtual std::string getTypedefintionName()const = 0;
		  virtual std::string getInterpreterValueTypeName () const = 0;
		  virtual std::string getTypeName () const = 0;
		  virtual std::string getTypeValue () const = 0;
		  virtual void upDateTypeValue (const std::string& newValue) = 0;
		  virtual std::string getTypeNameAndValue () const = 0;

	};


	class ICoSimulationCallback
	{
	  public:
		  virtual ~ICoSimulationCallback(){}
		  virtual IChannelEventObject::ChannelEventObjectSet inspect()= 0;
		  virtual void execute( IChannelEventObject::ChannelEventObjectSmartPtr evt)= 0;
		  virtual bool finished()const = 0;
		  virtual void init() = 0;
		  virtual void deInit()= 0;

	};

	class IComErrorCallback
	{
	  public:
		  typedef enum {DIS_CONNECT_ERROR,READ_ERROR,SEND_ERROR,NONE_ERROR}TRANSPORT_COM_ERROR;
		  virtual ~IComErrorCallback(){}
		  virtual void TransportLayerStopped(const TRANSPORT_COM_ERROR& evt = NONE_ERROR)= 0;


	};
	class ICoSimulationTransportLayer
	{
	  public:
		  
		  typedef boost::shared_ptr<ICoSimulationCallback> TransportLayerCallback;
		  typedef boost::shared_ptr<IComErrorCallback> TransportLayerErrorCallback;
		 
		  virtual ~ICoSimulationTransportLayer(){}
          
		  virtual void connect(const std::string& endpoint, const int& port,const std::string& modelName)= 0;
		  virtual void disConnect()= 0;

		  virtual void setCallbackObject(TransportLayerCallback cbObj)=0;
		  //virtual void setComErrorCallbackObject(TransportLayerErrorCallback cdObj)= 0;
		   virtual void setComErrorCallbackObject(IComErrorCallback* cdObj)= 0;

		  virtual void start()= 0;
		  virtual void stop()= 0;

	};

}

#endif