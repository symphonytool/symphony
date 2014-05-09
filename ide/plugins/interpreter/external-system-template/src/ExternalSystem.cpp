// TestCoSimDeploymentLIBs.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include "CoSimConfig.hpp"
#include"ExternalSystem.hpp"


int _tmain(int argc, _TCHAR* argv[])
{

	using namespace  ExternalSystem;

	try{
		//TODO replace this typedef with model specific implementation
		typedef CoSimulationFramework::ACoSimulationModel<ExternalSystemBasicImpl> ExternalSystemCoSimulationModel;
		ExternalSystemCoSimulationModel model(EXTERNAL_PROCESS);
		model.initModel(SYMPHONY_HOST, SYMPHONY_PORT);
	}
	catch (std::exception& e)
	{
		std::cerr << "Exception: " << e.what() << "\n";
	}
	catch (...)
	{
		std::cerr << " Unknowed error \n";
	}

	getchar();
	return 0;
}

