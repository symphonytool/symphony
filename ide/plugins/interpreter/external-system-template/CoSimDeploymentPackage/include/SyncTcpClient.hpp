#ifndef SYNCTCP_CLIENT_LIB_HPP
#define SYNCTCP_CLIENT_LIB_HPP

// Copyright 2014 - Bang & Olufsen A/S

#include <iostream>
#include <fstream>
#include <boost/asio.hpp>

#include <boost/bind.hpp>
#include <boost/smart_ptr.hpp>
#include <boost/thread/thread.hpp>


namespace CoSimulationTransportLayer
{


	class SyncTCPClient
	{
	 public:
		    SyncTCPClient();
		    void connectToSocket (const std::string& host, const int& port);
		    std::string readFromSocket();
		    void writeToSocket (const std::string& data);

			void disconnectSocket();
	 
	 private: //dont copy object
		SyncTCPClient(const SyncTCPClient&);
		SyncTCPClient& operator = (const SyncTCPClient&);

		boost::asio::io_service io_service;
		boost::asio::ip::tcp::socket socket;
	};

	

} //end namespace
#endif
