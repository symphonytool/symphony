#ifndef EXTERNAL_SYSTEM_HPP
#define EXTERNAL_SYSTEM_HPP


#include <iostream>
#include <CoSimulationTransportLayer.hpp>
#include <CoSimulationFramework.hpp>


namespace ExternalSystem
{
	template <typename S, typename D>
	void LOG(S& stream, const D& data)
	{
		stream << data << std::endl;
	}

	//callback class, will be called by the cosim framework
	class ExternalSystemBasicImpl : public CoSimulationFramework::ACoSimulationCallback<>
	{
	public:

		CoSimulationTransportLayer::IChannelEventObject::ChannelEventObjectSet inspect()
		{
			LOG(std::cout, "ExternalSystemBasicImpl::inspect()");

			CoSimulationTransportLayer::IChannelEventObject::ChannelEventObjectSet eventOptions;
			return eventOptions;
		}


		void execute(CoSimulationTransportLayer::IChannelEventObject::ChannelEventObjectSmartPtr evt)
		{
			LOG(std::cout, "ExternalSystemBasicImpl::execute()");
		}

		bool finished()const
		{
			LOG(std::cout, "ExternalSystemBasicImpl::finished()");
			return false;
		}
		void init()
		{
			LOG(std::cout, "ExternalSystemBasicImpl::init()");
		}
		void deInit()
		{
			LOG(std::cout, "ExternalSystemBasicImpl::deInit()");
		}
	};

	// create frontend object by using as typedef
	typedef CoSimulationFramework::ACoSimulationModel<ExternalSystemBasicImpl> ExternalSystemBasicImplCoSimulationModel;

}



#endif