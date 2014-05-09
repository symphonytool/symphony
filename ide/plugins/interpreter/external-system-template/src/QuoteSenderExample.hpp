#ifndef CO_SIMULATION_QUOTE_HPP
#define CO_SIMULATION_QUOTE_HPP


#include <iostream>
#include <CoSimulationTransportLayer.hpp>
#include <CoSimulationFramework.hpp>
#include "ExternalSystem.hpp"


namespace ExternalSystem
{
	//callback class, will be called by the cosim framework
	class QuoteSenderExampleImpl : public CoSimulationFramework::ACoSimulationCallback<>
	{
	public:


		CoSimulationTransportLayer::IChannelEventObject::ChannelEventObjectSet inspect()
		{
			LOG(std::cout, "QuoteSenderExampleBasicImpl::inspect()");

			CoSimulationTransportLayer::IChannelEventObject::ChannelEventObjectSet eventOptions;

			eventOptions.push_back(ACoSimulationCallback<>::createSyncEventObject("i"));
			eventOptions.push_back(createReadEventObject<std::string>("n"));
			eventOptions.push_back(createWriteSyncOnEventObject<std::string>("n", CoSimulationFramework::ChannelOperation::WRITE, b));

			return eventOptions;
		}


		void execute(CoSimulationTransportLayer::IChannelEventObject::ChannelEventObjectSmartPtr evt)
		{
			LOG(std::cout, "QuoteSenderExampleBasicImpl::execute()");

			if (evt->getChannelName() == "i")
			{
				exitTrue = true;
			}
			else
			{
				CoSimulationFramework::ChannelEventObject<std::string>* robj = static_cast<CoSimulationFramework::ChannelEventObject<std::string>*> (evt.get());
				std::cout << evt->getChannelName() << " \n" << robj->getOperationType() << "\n" << robj->action.type() << std::endl;

				if (robj->action.type() == "SOURCE_NODE")
					b = "SINK_NODE";
			}


		}

		bool finished()const
		{
			LOG(std::cout, "QuoteSenderExampleBasicImpl::finished()");
			return exitTrue;
		}
		void init()
		{
			LOG(std::cout, "QuoteSenderExampleBasicImpl::init()");
			exitTrue = false;
			b = "SOURCE_NODE";
		}
		void deInit()
		{
			LOG(std::cout, "QuoteSenderExampleBasicImpl::deInit()");
		}

	private:

		bool exitTrue;
		std::string b;
	};

	// create frontend object by using as typedef
	typedef CoSimulationFramework::ACoSimulationModel<QuoteSenderExampleImpl> ExternalSystemCoSimulationModel;

}



#endif