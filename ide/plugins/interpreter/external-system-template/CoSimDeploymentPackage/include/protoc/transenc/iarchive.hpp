#ifndef PROTOC_TRANSENC_IARCHIVE_HPP
#define PROTOC_TRANSENC_IARCHIVE_HPP

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

#include <string>
#include <boost/optional.hpp>
#include <boost/archive/detail/common_iarchive.hpp>
#include <boost/archive/detail/register_archive.hpp>
#include <protoc/transenc/reader.hpp>

namespace protoc
{
namespace transenc
{

class iarchive
    : public boost::archive::detail::common_iarchive<iarchive>
{
    friend class boost::archive::load_access;

public:
    iarchive(const transenc::reader&);
    template <typename Iterator>
    iarchive(Iterator begin, Iterator end);

    template<typename value_type>
    void load_override(value_type& data, long /*version*/)
    {
        boost::archive::load(*this->This(), data);
    }

    void load();
    void load(bool&);
    void load(int&);
    void load(long long&);
    void load(float&);
    void load(double&);
    void load(std::string&);

    void load_record_begin();
    void load_record_end();

    boost::optional<std::size_t> load_array_begin();
    void load_array_end();
    bool at_array_end() const;

    boost::optional<std::size_t> load_map_begin();
    void load_map_end();
    bool at_map_end() const;

    protoc::token::value type() const;

    // Ignore these
    void load(boost::archive::version_type&) {}
    void load(boost::archive::object_id_type) {}
    void load(boost::archive::object_reference_type) {}
    void load(boost::archive::class_id_type) {}
    void load(boost::archive::class_id_optional_type) {}
    void load(boost::archive::class_id_reference_type) {}
    void load(boost::archive::tracking_type) {}
    void load(boost::archive::class_name_type&) {}

private:
    transenc::reader reader;
};

} // namespace transenc
} // namespace protoc

BOOST_SERIALIZATION_REGISTER_ARCHIVE(protoc::transenc::iarchive);

#include <sstream>
#include <protoc/exceptions.hpp>

namespace protoc
{
namespace transenc
{

inline iarchive::iarchive(const transenc::reader& reader)
    : reader(reader)
{
}

template <typename Iterator>
inline iarchive::iarchive(Iterator begin, Iterator end)
    : reader(begin, end)
{
}

inline void iarchive::load()
{
    if (reader.type() != protoc::token::token_null)
    {
        return; // FIXME: throw exception?
    }
    reader.next();
}

inline void iarchive::load(bool& value)
{
    value = reader.get_bool();
    reader.next();
}

inline void iarchive::load(int& value)
{
    value = reader.get_int();
    reader.next();
}

inline void iarchive::load(long long& value)
{
    value = reader.get_long_long();
    reader.next();
}

inline void iarchive::load(float& value)
{
    value = reader.get_double();
    reader.next();
}

inline void iarchive::load(double& value)
{
    value = reader.get_double();
    reader.next();
}

inline void iarchive::load(std::string& value)
{
    value = reader.get_string();
    reader.next();
}

inline void iarchive::load_record_begin()
{
    reader.next(protoc::token::token_record_begin);
}

inline void iarchive::load_record_end()
{
    reader.next(protoc::token::token_record_end);
}

inline boost::optional<std::size_t> iarchive::load_array_begin()
{
    boost::optional<std::size_t> result;
    reader.next(protoc::token::token_array_begin);
    switch (reader.type())
    {
    case protoc::token::token_null:
        reader.next();
        break;
    case protoc::token::token_integer:
        result = reader.get_int();
        reader.next();
        break;
    default:
        break; // FIXME: throw exception?
    }
    return result;
}

inline void iarchive::load_array_end()
{
    reader.next(protoc::token::token_array_end);
}

inline bool iarchive::at_array_end() const
{
    // FIXME: Handle nested containers
    return (reader.type() == protoc::token::token_array_end);
}

inline boost::optional<std::size_t> iarchive::load_map_begin()
{
    boost::optional<std::size_t> result;
    reader.next(protoc::token::token_map_begin);
    switch (reader.type())
    {
    case protoc::token::token_null:
        reader.next();
        break;
    case protoc::token::token_integer:
        result = reader.get_int();
        reader.next();
        break;
    default:
        break; // FIXME: throw exception?
    }
    return result;
}

inline void iarchive::load_map_end()
{
    reader.next(protoc::token::token_map_end);
}

inline bool iarchive::at_map_end() const
{
    // FIXME: Handle nested containers
    return (reader.type() == protoc::token::token_map_end);
}

inline protoc::token::value iarchive::type() const
{
    return reader.type();
}

} // namespace transenc
} // namespace protoc

#endif // PROTOC_TRANSENC_IARCHIVE_HPP
