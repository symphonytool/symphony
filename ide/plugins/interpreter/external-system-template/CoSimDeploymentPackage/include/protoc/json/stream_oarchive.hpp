#ifndef PROTOC_JSON_STREAM_OARCHIVE_HPP
#define PROTOC_JSON_STREAM_OARCHIVE_HPP

///////////////////////////////////////////////////////////////////////////////
//
// http://protoc.sourceforge.net/
//
// Copyright (C) 2014 Bjorn Reese <breese@users.sourceforge.net>
//
// Distributed under the Boost Software License, Version 1.0.
//    (See accompanying file LICENSE_1_0.txt or copy at
//          http://www.boost.org/LICENSE_1_0.txt)
//
///////////////////////////////////////////////////////////////////////////////

#include <ostream>
#include <boost/utility/base_from_member.hpp>
#include <protoc/output_stream.hpp>
#include <protoc/json/oarchive.hpp>

namespace protoc
{
namespace json
{

class stream_oarchive
    : private boost::base_from_member< protoc::output_stream<char> >,
      public oarchive
{
    typedef protoc::output_stream<char> member1_type;
    typedef boost::base_from_member<member1_type> base_member1_type;

public:
    stream_oarchive(std::ostream& stream)
        : base_member1_type(member1_type(stream)),
          oarchive(base_member1_type::member)
    {}
};

} // namespace json
} // namespace protoc

BOOST_SERIALIZATION_REGISTER_ARCHIVE(protoc::json::stream_oarchive);

#endif // PROTOC_JSON_STREAM_OARCHIVE_HPP
