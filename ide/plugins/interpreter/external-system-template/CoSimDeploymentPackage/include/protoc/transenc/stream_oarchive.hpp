#ifndef PROTOC_TRANSENC_STREAM_OARCHIVE_HPP
#define PROTOC_TRANSENC_STREAM_OARCHIVE_HPP

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
#include <protoc/transenc/oarchive.hpp>

namespace protoc
{
namespace transenc
{

// base_from_member is needed because we want to add a member that must be
// initialized before oarchive because it is passed as an argument to its
// constructor
class stream_oarchive
    : private boost::base_from_member< protoc::output_stream<protoc::uint8_t> >,
      private boost::base_from_member<transenc::encoder>,
      public oarchive
{
    typedef protoc::output_stream<protoc::uint8_t> member1_type;
    typedef transenc::encoder member2_type;
    typedef boost::base_from_member<member1_type> base_member1_type;
    typedef boost::base_from_member<member2_type> base_member2_type;

public:
    stream_oarchive(std::ostream& stream)
        : base_member1_type(member1_type(stream)),
          base_member2_type(boost::ref(base_member1_type::member)),
          oarchive(base_member2_type::member)
    {}
};

} // namespace transenc
} // namespace protoc

BOOST_SERIALIZATION_REGISTER_ARCHIVE(protoc::transenc::stream_oarchive);

#endif // PROTOC_TRANSENC_STREAM_OARCHIVE_HPP
