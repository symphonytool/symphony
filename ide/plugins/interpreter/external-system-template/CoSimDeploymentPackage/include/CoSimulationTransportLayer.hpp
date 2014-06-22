#ifndef IMPL_CO_SIMULATION_TRANSPORT_LAYER_LIB_HPP
#define IMPL_CO_SIMULATION_TRANSPORT_LAYER_LIB_HPP

#include <boost/function.hpp>
#include <boost/thread/mutex.hpp>
#include <boost/thread/condition.hpp>
#include <boost/thread.hpp>
#include "ICoSimulationTransportLayer.hpp"
#include "CoSimMessageReaderWriter.hpp"
#include "GenericTcpTransportStrategy.hpp"


namespace CoSimulationTransportLayer
{

	class ComErrorCallback : public IComErrorCallback 
	{
	  public:
		  ~ComErrorCallback();

	};

	class DefaultCoSimulationTransportLayer:public ICoSimulationTransportLayer
	{
	  public:

		  typedef boost::mutex::scoped_lock ScopeLock;
		  typedef boost::function<void( CoSimMessageReader&)> DelegateObject;
		  typedef std::map<CoSimMessageReaderWriter::CoSimMessage, DelegateObject> DelegateMap;
		  typedef std::pair<CoSimMessageReaderWriter::CoSimMessage, DelegateObject> DelegatePair;

		  DefaultCoSimulationTransportLayer();
		  ~DefaultCoSimulationTransportLayer();
		   
		  void connect(const std::string& endpoint,const int& port,const std::string& modelName);
		  void disConnect();
		  void setCallbackObject(TransportLayerCallback cbObj);
		  void setComErrorCallbackObject(IComErrorCallback* cdObj);

		  void start();
		  void stop();
	
	protected:
		TransportLayerCallback tlCallbackObj;
		IComErrorCallback* tlErrorCallbackObj;
		mutable boost::mutex mut;
		boost::thread* taskThread;
		volatile bool active;
		GenericTcpTransportStrategy transportObject;
		DelegateMap delegate_map;
		IChannelEventObject::ChannelEventObjectSet currentEventOptions;
		std::string cmlProcess;

        void coSimProtocol_statemachine();

	private:
		// set of state functions reagrding the CoSim appplication layer protocol
		void registerSubSystemMessage();
		void inspectMessage(CoSimMessageReader&);
		void executeMessage(CoSimMessageReader&);
		void finishedRequestMessage(CoSimMessageReader&);
		void disconnectRequestMessage(CoSimMessageReader&);

	};


}

#endif