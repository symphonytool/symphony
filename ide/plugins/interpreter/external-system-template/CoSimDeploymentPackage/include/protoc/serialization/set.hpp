#ifndef PROTOC_SERIALIZATION_SET_HPP
#define PROTOC_SERIALIZATION_SET_HPP

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

#include <set>
#include <boost/serialization/split_free.hpp>
#include <protoc/serialization/serialization.hpp>

namespace boost
{
namespace serialization
{

template <typename Archive, typename Key, typename Compare, typename Allocator>
struct save_functor< Archive, typename std::set<Key, Compare, Allocator> >
{
    void operator () (Archive& ar,
                      const std::set<Key, Compare, Allocator>& data,
                      const unsigned int version)
    {
        ar.save_array_begin();
        for (typename std::set<Key, Compare, Allocator>::const_iterator it = data.begin();
             it != data.end();
             ++it)
        {
            ar.save_override(*it, version);
        }
        ar.save_array_end();
    }
};

template <typename Archive, typename Key, typename Compare, typename Allocator>
struct load_functor< Archive, typename std::set<Key, Compare, Allocator> >
{
    void operator () (Archive& ar,
                      std::set<Key, Compare, Allocator>& data,
                      const unsigned int version)
    {
        ar.load_array_begin();
        while (!ar.at_array_end())
        {
            Key value;
            ar.load_override(value, version);
            data.insert(value);
        }
        ar.load_array_end();
    }
};

template <typename Key, typename Compare, typename Allocator>
struct serialize_functor< typename std::set<Key, Compare, Allocator> >
{
    template <typename Archive>
    typename boost::enable_if<typename Archive::is_loading, void>::type
    operator () (Archive& ar,
                 std::set<Key, Compare, Allocator>& data,
                 const unsigned int version)
    {
        split_free(ar, data, version);
    }

    template <typename Archive>
    typename boost::enable_if<typename Archive::is_saving, void>::type
    operator () (Archive& ar,
                 const std::set<Key, Compare, Allocator>& data,
                 const unsigned int version)
    {
        split_free(ar, data, version);
    }
};

} // namespace serialization
} // namespace boost

#endif // PROTOC_SERIALIZATION_SET_HPP
