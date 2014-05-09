#ifndef PROTOC_JSON_PAIR_HPP
#define PROTOC_JSON_PAIR_HPP

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

#include <protoc/json/serialization.hpp>
#include <protoc/serialization/pair.hpp>

namespace boost
{
namespace serialization
{

template <typename T1, typename T2>
struct save_functor< protoc::json::oarchive, typename std::pair<T1, T2> >
{
    void operator () (protoc::json::oarchive& ar,
                      const std::pair<T1, T2>& data,
                      const unsigned int version)
    {
        ar.save_array_begin();
        ar << data.first;
        ar << data.second;
        ar.save_array_end();
    }
};

template <typename T1, typename T2>
struct load_functor< protoc::json::iarchive, typename std::pair<T1, T2> >
{
    void operator () (protoc::json::iarchive& ar,
                      std::pair<T1, T2>& data,
                      const unsigned int version)
    {
        ar.load_array_begin();
        ar >> data.first;
        ar >> data.second;
        ar.load_array_end();
    }
};

} // namespace serialization
} // namespace boost

#endif // PROTOC_JSON_PAIR_HPP
