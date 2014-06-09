#ifndef PROTOC_JSON_IARCHIVE_HPP
#define PROTOC_JSON_IARCHIVE_HPP

///////////////////////////////////////////////////////////////////////////////
//
// http://protoc.sourceforge.net/
//
// Copyright (C) 2013 Bjorn Reese <breese@users.sourceforge.net>
//
// Distributed under the Boost Software License, Version 1.0.
//    (See accompanying file LICENSE_1_0.txt or copy at
//          http://www.boost.org/LICENSE_1_0.txt)
//
///////////////////////////////////////////////////////////////////////////////

#include <string>
#include <boost/optional.hpp>
#include <boost/archive/detail/common_iarchive.hpp>
#include <boost/archive/detail/register_archive.hpp>
#include <protoc/types.hpp>
#include <protoc/json/reader.hpp>

namespace protoc
{
namespace json
{

class iarchive
    : public boost::archive::detail::common_iarchive<iarchive>
{
    friend class boost::archive::load_access;

public:
    iarchive(const json::reader&);
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
    json::reader reader;
};

} // namespace json
} // namespace protoc

#include <boost/archive/detail/register_archive.hpp>

BOOST_SERIALIZATION_REGISTER_ARCHIVE(protoc::json::iarchive);

#include <protoc/exceptions.hpp>

namespace protoc
{
namespace json
{

inline iarchive::iarchive(const json::reader& reader)
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
    reader.next(protoc::token::token_array_begin);
}

inline void iarchive::load_record_end()
{
    reader.next(protoc::token::token_array_end);
}

inline boost::optional<std::size_t> iarchive::load_array_begin()
{
    reader.next(protoc::token::token_array_begin);
    return boost::optional<std::size_t>();
}

inline void iarchive::load_array_end()
{
    reader.next(protoc::token::token_array_end);
}

inline bool iarchive::at_array_end() const
{
    return (reader.type() == protoc::token::token_array_end);
}

inline boost::optional<std::size_t> iarchive::load_map_begin()
{
    reader.next(protoc::token::token_map_begin);
    return boost::optional<std::size_t>();
}

inline void iarchive::load_map_end()
{
    reader.next(protoc::token::token_map_end);
}

inline bool iarchive::at_map_end() const
{
    return (reader.type() == protoc::token::token_map_end);
}

inline protoc::token::value iarchive::type() const
{
    return reader.type();
}

} // namespace json
} // namespace protoc

#endif // PROTOC_JSON_IARCHIVE_HPP
