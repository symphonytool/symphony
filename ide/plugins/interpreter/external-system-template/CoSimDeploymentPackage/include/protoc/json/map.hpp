#ifndef PROTOC_JSON_MAP_HPP
#define PROTOC_JSON_MAP_HPP

///////////////////////////////////////////////////////////////////////////////
//
// http://protoc.sourceforge.net/
//
// Copyright (C) 2014 Bjorn Reese <breese@users.sourceforge.net>
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

#include <protoc/json/serialization.hpp>
#include <protoc/serialization/map.hpp>

namespace boost
{
namespace serialization
{

template <typename Key, typename T, typename Compare, typename Allocator>
struct save_functor< protoc::json::oarchive, typename std::map<Key, T, Compare, Allocator> >
{
    void operator () (protoc::json::oarchive& ar,
                      const std::map<Key, T, Compare, Allocator>& data,
                      const unsigned int version)
    {
        ar.save_array_begin();
        for (typename std::map<Key, T, Compare, Allocator>::const_iterator it = data.begin();
             it != data.end();
             ++it)
        {
            ar.save_override(*it, version);
        }
        ar.save_array_end();
    }
};

template <typename Key, typename T, typename Compare, typename Allocator>
struct load_functor< protoc::json::iarchive, typename std::map<Key, T, Compare, Allocator> >
{
    void operator () (protoc::json::iarchive& ar,
                      std::map<Key, T, Compare, Allocator>& data,
                      const unsigned int version)
    {
        ar.load_array_begin();
        while (!ar.at_array_end())
        {
            // We cannot use std::map<Key, T>::value_type because it has a const key
            std::pair<Key, T> value;
            ar.load_override(value, version);
            data.insert(value);
        }
        ar.load_array_end();
    }
};

// Specialization for map<string, T>
template <typename CharT, typename Traits, typename StringAllocator,
          typename T, typename Compare, typename MapAllocator>
struct save_functor< protoc::json::oarchive,
                     typename std::map<std::basic_string<CharT, Traits, StringAllocator>, T, Compare, MapAllocator> >
{
    typedef std::basic_string<CharT, Traits, StringAllocator> key_type;
    void operator () (protoc::json::oarchive& ar,
                      const std::map<key_type, T, Compare, MapAllocator>& data,
                      const unsigned int version)
    {
        ar.save_map_begin();
        for (typename std::map<key_type, T, Compare, MapAllocator>::const_iterator it = data.begin();
             it != data.end();
             ++it)
        {
            ar << it->first;
            ar << it->second;
        }
        ar.save_map_end();
    }
};

template <typename CharT, typename Traits, typename StringAllocator,
          typename T, typename Compare, typename MapAllocator>
struct load_functor< protoc::json::iarchive,
                     typename std::map<std::basic_string<CharT, Traits, StringAllocator>, T, Compare, MapAllocator> >
{
    typedef std::basic_string<CharT, Traits, StringAllocator> key_type;
    void operator () (protoc::json::iarchive& ar,
                      std::map<key_type, T, Compare, MapAllocator>& data,
                      const unsigned int version)
    {
        ar.load_map_begin();
        while (!ar.at_map_end())
        {
            // We cannot use std::map<Key, T>::value_type because it has a const key
            std::pair<key_type, T> value;
            ar >> value.first;
            ar >> value.second;
            data.insert(value);
        }
        ar.load_map_end();
    }
};

} // namespace serialization
} // namespace boost

#endif // PROTOC_JSON_MAP_HPP
