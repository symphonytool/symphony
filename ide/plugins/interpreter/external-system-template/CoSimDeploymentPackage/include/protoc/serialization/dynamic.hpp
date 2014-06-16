#ifndef PROTOC_SERIALIZATION_DYNAMIC_HPP
#define PROTOC_SERIALIZATION_DYNAMIC_HPP

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

#include <dynamic/var.hpp> // http://dynamic-cpp.googlecode.com/
#include <boost/serialization/split_free.hpp>
#include <protoc/serialization/serialization.hpp>
#include <protoc/serialization/pair.hpp>

namespace boost
{
namespace serialization
{

template <typename Archive>
struct save_functor<Archive, dynamic::var>
{
    void operator () (Archive& ar,
                      const dynamic::var& data,
                      const unsigned int version)
    {
        if (data.is_null())
        {
            ar.save();
        }
        else if (data.is_bool())
        {
            ar.save(static_cast<bool>(data));
        }
        else if (data.is_int())
        {
            ar.save(static_cast<int>(data));
        }
        else if (data.is_double())
        {
            ar.save(static_cast<double>(data));
        }
        else if (data.is_string())
        {
            ar.save(static_cast<std::string>(data));
        }
        else if (data.is_vector())
        {
            ar.save_array_begin();
            for (dynamic::var::const_iterator it = data.begin();
                 it != data.end();
                 ++it)
            {
                ar.save_override(*it, version);
            }
            ar.save_array_end();
        }
        else if (data.is_map())
        {
            ar.save_map_begin();
            for (dynamic::var::const_iterator it = data.begin();
                 it != data.end();
                 ++it)
            {
                ar.save_override(it.pair(), version); // .pair() returns the key-value pair of the current element
            }
            ar.save_map_end();
        }
        else // FIXME: add wstring, list, and set
        {
        }
    }
};

template <typename Archive>
struct load_functor<Archive, dynamic::var>
{
    void operator () (Archive& ar,
                      dynamic::var& data,
                      const unsigned int version)
    {
        switch (ar.type())
        {
        case protoc::token::token_null:
            ar.load();
            break;

        case protoc::token::token_boolean:
            {
                bool value = false;
                ar.load(value);
                data = value;
            }
            break;

        case protoc::token::token_integer:
            {
                int value = 0;
                ar.load(value);
                data = value;
            }
            break;

        case protoc::token::token_floating:
            {
                double value = 0.0;
                ar.load(value);
                data = value;
            }
            break;

        case protoc::token::token_string:
            {
                std::string value;
                ar.load(value);
                data = value;
            }
            break;

        case protoc::token::token_map_begin:
            {
                ar.load_map_begin();
                data = dynamic::make_map();
                while (!ar.at_map_end())
                {
                    std::pair<dynamic::var, dynamic::var> value;
                    ar.load_override(value, version);
                    data(value.first, value.second); // Append value to data
                }
                ar.load_map_end();
            }
            break;

        default:
            // FIXME: Add remaining types
            break;
        }
    }
};

template <>
struct serialize_functor<dynamic::var>
{
    template <typename Archive>
    typename boost::enable_if<typename Archive::is_loading, void>::type
    operator () (Archive& ar,
                 dynamic::var& data,
                 const unsigned int version)
    {
        split_free(ar, data, version);
    }

    template <typename Archive>
    typename boost::enable_if<typename Archive::is_saving, void>::type
    operator () (Archive& ar,
                 const dynamic::var& data,
                 const unsigned int version)
    {
        split_free(ar, data, version);
    }
};

} // namespace serialization
} // namespace boost

#endif // PROTOC_SERIALIZATION_DYNAMIC_HPP
