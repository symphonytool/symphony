#ifndef PROTOC_SERIALIZATION_PAIR_HPP
#define PROTOC_SERIALIZATION_PAIR_HPP

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

#include <utility> // std::pair
#include <boost/serialization/split_free.hpp>
#include <protoc/serialization/serialization.hpp>

namespace boost
{
namespace serialization
{

template <typename Archive, typename T1, typename T2>
struct save_functor< Archive, typename std::pair<T1, T2> >
{
    void operator () (Archive& ar,
                      const std::pair<T1, T2>& data,
                      const unsigned int version)
    {
        ar.save_record_begin();
        ar << data.first;
        ar << data.second;
        ar.save_record_end();
    }
};

template <typename Archive, typename T1, typename T2>
struct load_functor< Archive, typename std::pair<T1, T2> >
{
    void operator () (Archive& ar,
                      std::pair<T1, T2>& data,
                      const unsigned int version)
    {
        ar.load_record_begin();
        ar >> data.first;
        ar >> data.second;
        ar.load_record_end();
    }
};

template <typename T1, typename T2>
struct serialize_functor< typename std::pair<T1, T2> >
{
    template <typename Archive>
    typename boost::enable_if<typename Archive::is_loading, void>::type
    operator () (Archive& ar,
                 std::pair<T1, T2>& data,
                 const unsigned int version)
    {
        split_free(ar, data, version);
    }

    template <typename Archive>
    typename boost::enable_if<typename Archive::is_saving, void>::type
    operator () (Archive& ar,
                 const std::pair<T1, T2>& data,
                 const unsigned int version)
    {
        split_free(ar, data, version);
    }
};

} // namespace serialization
} // namespace boost

#endif // PROTOC_SERIALIZATION_PAIR_HPP
