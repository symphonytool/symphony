#ifndef GENERIC_TCP_TRANSPORT_STRATEGYLIB_HPP
#define GENERIC_TCP_TRANSPORT_STRATEGYLIB_HPP

#include "SyncTcpClient.hpp"
#include<STRING>
#include <VECTOR>
#include <boost/shared_ptr.hpp>

namespace CoSimulationTransportLayer
{
	
	class  CoSimulationTransportLayerExecption : public std::runtime_error
    {
     public:
		 explicit  CoSimulationTransportLayerExecption (const std::string& what_arg):std::runtime_error(what_arg){}
    };

	class GenericTcpTransportStrategy
	{
	    public:
			GenericTcpTransportStrategy();
			//  will throw if the endpoint is not correct
			void connect(const std::string& end_point, const int& port);
			
			void dis_connect();
			void send (const std::string& data);
			std::string read();

			inline bool isconnected()const {return is_connected;}

	   private:

		   //this object must not be copied
		   GenericTcpTransportStrategy(const GenericTcpTransportStrategy&);
		   GenericTcpTransportStrategy& operator = (GenericTcpTransportStrategy&);

		   bool is_connected;
		   SyncTCPClient tcpClient;
	};


}

#endif