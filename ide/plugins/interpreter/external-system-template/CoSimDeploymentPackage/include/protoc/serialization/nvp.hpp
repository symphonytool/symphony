#ifndef PROTOC_SERIALIZATION_NVP_HPP
#define PROTOC_SERIALIZATION_NVP_HPP

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

#include <boost/serialization/nvp.hpp>
#include <boost/serialization/split_free.hpp>
#include <protoc/serialization/serialization.hpp>

namespace boost
{
namespace serialization
{

template <typename Archive, typename T>
struct save_functor< Archive, typename boost::serialization::nvp<T> >
{
    void operator () (Archive& ar,
                      const boost::serialization::nvp<T>& data,
                      const unsigned int version)
    {
        // Drop the name
        ar << data.value();
    }
};

template <typename Archive, typename T>
struct load_functor< Archive, typename boost::serialization::nvp<T> >
{
    void operator () (Archive& ar,
                      boost::serialization::nvp<T>& data,
                      const unsigned int version)
    {
        // There is no the name
        ar >> data.value();
    }
};

template <typename T>
struct serialize_functor< typename boost::serialization::nvp<T> >
{
    template <typename Archive>
    typename boost::enable_if<typename Archive::is_loading, void>::type
    operator () (Archive& ar,
                 boost::serialization::nvp<T>& data,
                 const unsigned int version)
    {
        split_free(ar, data, version);
    }

    template <typename Archive>
    typename boost::enable_if<typename Archive::is_saving, void>::type
    operator () (Archive& ar,
                 const boost::serialization::nvp<T>& data,
                 const unsigned int version)
    {
        split_free(ar, data, version);
    }
};

} // namespace serialization
} // namespace boost

#endif // PROTOC_SERIALIZATION_NVP_HPP
