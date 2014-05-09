###############################################################################
#
# http://protoc.sourceforge.net/
#
# Copyright (C) 2013 Bjorn Reese <breese@users.sourceforge.net>
#
# Permission to use, copy, modify, and distribute this software for any
# purpose with or without fee is hereby granted, provided that the above
# copyright notice and this permission notice appear in all copies.
#
# THIS SOFTWARE IS PROVIDED ``AS IS'' AND WITHOUT ANY EXPRESS OR IMPLIED
# WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF
# MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE AUTHORS AND
# CONTRIBUTORS ACCEPT NO RESPONSIBILITY IN ANY CONCEIVABLE MANNER.
#
###############################################################################

# Debug build
#   cmake -DCMAKE_BUILD_TYPE=Debug .

###############################################################################

cmake_minimum_required(VERSION 2.8)
project(SymphonyExternalSystem)
find_package(PkgConfig)

###############################################################################
# Boost package
###############################################################################
#MESSAGE( "Your Unix home directory is ${MSVC_VERSION}." )
#MESSAGE( "Your Unix home directory is ${MSVC_IDE}." )
#MESSAGE( "Your Unix home directory is ${MSVC80}." )
#MESSAGE( "Your Unix home directory is ${MSVC}." )
#set(a "$ENV{Boost_INCLUDE_DIR}")
#message(FATAL_ERROR ${a})
#set(Boost_INCLUDE_DIR "$ENV{Boost_INCLUDE_DIR}")
if((MSVC_VERSION GREATER 1800) OR (MSVC_VERSION EQUAL 1800))
	set(SymphonyLibs "CoSimDeploymentPackage/libs/vs120")
endif()

if(MSVC_VERSION EQUAL 1600)
	set(SymphonyLibs "CoSimDeploymentPackage/libs/vs100")
endif()


#CMAKE_USING_VC_FREE_TOOLS

find_package(Boost 1.55.0 COMPONENTS thread)
if (NOT ${Boost_FOUND})
  message(FATAL_ERROR "Boost not found (or too old) please set ENV: Boost_INCLUDE_DIR to boost home")
endif()
include_directories(BEFORE ${Boost_INCLUDE_DIR})
#set(EXTRA_LIBS ${EXTRA_LIBS} ${Boost_LIBRARIES})
link_directories(${Boost_LIBRARY_DIR})



include_directories("CoSimDeploymentPackage/include")



#-------------------------------------------------------------
# setup protoc
#-------------------------------------------------------------
find_library(PROTOC_LIBRARY NAMES protoc
             HINTS ${SymphonyLibs} )
			 
find_package_handle_standard_args(PROTOC_LIBRARY  DEFAULT_MSG
                                  PROTOC_LIBRARY)

GET_FILENAME_COMPONENT(PROTOC_LIBRARY_PATH "${PROTOC_LIBRARY}" PATH)


#-------------------------------------------------------------
# setup cosimulation framework
#-------------------------------------------------------------
find_library(CSF_LIBRARY NAMES CoSimulationFrameworkLIB
             HINTS ${SymphonyLibs} )
			 
find_package_handle_standard_args(CSF_LIBRARY  DEFAULT_MSG
                                  CSF_LIBRARY)


#-------------------------------------------------------------
# setup cosimulation transport
#-------------------------------------------------------------								  
GET_FILENAME_COMPONENT(CSF_LIBRARY_PATH "${CSF_LIBRARY}" PATH)

find_library(CST_LIBRARY NAMES CoSimulationTransportLIB
             HINTS ${SymphonyLibs} )
			 
find_package_handle_standard_args(CST_LIBRARY  DEFAULT_MSG
                                  CST_LIBRARY)

GET_FILENAME_COMPONENT(CST_LIBRARY_PATH "${CST_LIBRARY}" PATH)


# create list of libs
set(libs ${PROTOC_LIBRARY_PATH} ${CSF_LIBRARY_PATH} ${CST_LIBRARY_PATH})
# remove dublicates
list(REMOVE_DUPLICATES libs)
# set the libs in vs as additional directories
link_directories(${libs})


  

  
add_executable(ExternalSystem 	
	src/ExternalSystem.cpp 
	src/ExternalSystem.hpp
	src/stdafx.h
	src/stdafx.cpp
	src/QuoteSenderExample.hpp
	generates/CoSimConfig.hpp
)




#set_target_properties(ExternalSystem PROPERTIES RUNTIME_OUTPUT_DIRECTORY bin)
#set_target_properties(ExternalSystem PROPERTIES LIBRARY_OUTPUT_DIRECTORY lib)
#target_link_libraries(ExternalSystem ${Boost_LIBRARIES}) 
target_link_libraries(ExternalSystem protoc.lib ${EXTRA_LIBS})
target_link_libraries(ExternalSystem CoSimulationTransportLIB.lib ${EXTRA_LIBS})
target_link_libraries(ExternalSystem CoSimulationFrameworkLIB.lib ${EXTRA_LIBS})


#if(WIN32)
#   set_target_properties(ExternalSystem PROPERTIES LINK_FLAGS_DEBUG "/SUBSYSTEM:CONSOLE")
#   set_target_properties(ExternalSystem PROPERTIES COMPILE_DEFINITIONS_DEBUG "_CONSOLE")
#   set_target_properties(ExternalSystem PROPERTIES LINK_FLAGS_RELWITHDEBINFO "/SUBSYSTEM:CONSOLE")
#   set_target_properties(ExternalSystem PROPERTIES COMPILE_DEFINITIONS_RELWITHDEBINFO "_CONSOLE")
#   set_target_properties(ExternalSystem PROPERTIES LINK_FLAGS_RELEASE "/SUBSYSTEM:WINDOWS")
#   set_target_properties(ExternalSystem PROPERTIES LINK_FLAGS_MINSIZEREL "/SUBSYSTEM:WINDOWS")
#endif(WIN32)


#add_library(protoc STATIC
#  ,
#)
#set(CMAKE_CXX_FLAGS_DEBUG "-g -Wall")
#add_definitions(-fmax-errors=1) # gcc
#add_definitions(-ferror-limit=1) # clang
#add_definitions(-ftemplate-backtrace-limit=0) # noisy clang

###############################################################################
# Boost package
###############################################################################


###############################################################################
# protoc package
###############################################################################

#set(PROTOC_LIBRARY_DIR ${CMAKE_CURRENT_SOURCE_DIR}/lib)


#link_directories(${PROTOC_LIBRARY_DIR})
#set(LIBRARY_OUTPUT_PATH lib)
#set(EXTRA_LIBS -lprotoc ${EXTRA_LIBS})

#add_library(protoc STATIC
#  src/json/decoder.cpp
#  src/json/encoder.cpp
#  src/msgpack/decoder.cpp
#  src/msgpack/decoder.cpp
#  src/msgpack/encoder.cpp
#  src/transenc/decoder.cpp
#  src/transenc/encoder.cpp
#  src/ubjson/decoder.cpp
#  src/ubjson/encoder.cpp
#  src/ubjson/iarchive.cpp
#  src/ubjson/oarchive.cpp
#)

###############################################################################
# Test
###############################################################################


