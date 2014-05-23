#ifndef PROTOC_SERIALIZATION_SERIALIZATION_HPP
#define PROTOC_SERIALIZATION_SERIALIZATION_HPP

///////////////////////////////////////////////////////////////////////////////
//
// http://protoc.sourceforge.net/
//
// Copyright (C) 2013 Bjorn Reese <breese@users.sourceforge.net>
//
// Permission to use, copy, modify, and distribute this software for any
// purpose with or without fee is hereby granted, provided that the above
// copyright notice and this permission notice appear in all copies.
//
// THIS SOFTWARE IS PROVIDED ``AS IS'' AND WITHOUT ANY EXPRESS OR IMPLIED
// WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF
// MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE AUTHORS AND
// CONTRIBUTORS ACCEPT NO RESPONSIBILITY IN ANY CONCEIVABLE MANNER.
//
///////////////////////////////////////////////////////////////////////////////

#include <boost/utility/enable_if.hpp>
#include <boost/serialization/split_free.hpp>

// Non-intrusive serialization

namespace boost
{
namespace serialization
{

template <typename Archive, typename Value>
struct save_functor
{
    void operator () (Archive& ar,
                      const Value& data,
                      const unsigned int)
    {
        ar.save_record_begin();
        data.save(ar);
        ar.save_record_end();
    }
};

template <typename Archive, typename Value>
struct load_functor
{
    void operator () (Archive& ar,
                      const Value& data,
                      const unsigned int)
    {
        ar.load_record_begin();
        data.load(ar);
        ar.load_record_end();
    }
};

template <typename Value>
struct serialize_functor
{
    template <typename Archive>
    typename boost::enable_if<typename Archive::is_loading, void>::type
    operator () (Archive& ar,
                 Value& data,
                 const unsigned int version)
    {
        ar.load_record_begin();
        data.serialize(ar, version);
        ar.load_record_end();
    }

    template <typename Archive>
    typename boost::enable_if<typename Archive::is_saving, void>::type
    operator () (Archive& ar,
                 Value& data,
                 const unsigned int version)
    {
        ar.save_record_begin();
        data.serialize(ar, version);
        ar.save_record_end();
    }
};

} // namespace serialization
} // namespace boost

#endif // PROTOC_SERIALIZATION_SERIALIZATION_HPP
