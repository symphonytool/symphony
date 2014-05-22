#ifndef PROTOC_JSON_OARCHIVE_HPP
#define PROTOC_JSON_OARCHIVE_HPP

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
#include <boost/archive/detail/common_oarchive.hpp>
#include <boost/archive/detail/register_archive.hpp>
#include <protoc/json/writer.hpp>

namespace protoc
{
namespace json
{

class oarchive
    : public boost::archive::detail::common_oarchive<oarchive>
{
    friend class boost::archive::save_access;

public:
    oarchive(json::writer::output_type&);

    void save();
    void save(bool);
    void save(int);
    void save(long long);
    void save(float);
    void save(double);
    void save(const char *);
    void save(const std::string&);
    void save_binary(const char *, std::size_t);

    void save_record_begin();
    void save_record_end();
    void save_array_begin();
    void save_array_begin(std::size_t);
    void save_array_end();
    void save_map_begin();
    void save_map_begin(std::size_t);
    void save_map_end();

    template<typename T>
    void save_override(const T& data, long /*version*/)
    {
        boost::archive::save(*this->This(), data);
    }

    // String literal
    void save_override(const char *data, int /*version*/)
    {
        save(data);
    }

    // Ignore these
    void save_override(const boost::archive::version_type, int) {}
    void save_override(const boost::archive::object_id_type, int) {}
    void save_override(const boost::archive::object_reference_type, int) {}
    void save_override(const boost::archive::class_id_type, int) {}
    void save_override(const boost::archive::class_id_optional_type, int) {}
    void save_override(const boost::archive::class_id_reference_type, int) {}
    void save_override(const boost::archive::tracking_type, int) {}
    void save_override(const boost::archive::class_name_type&, int) {}

protected:
    json::writer writer;
};

} // namespace json
} // namespace protoc

BOOST_SERIALIZATION_REGISTER_ARCHIVE(protoc::json::oarchive);

namespace protoc
{
namespace json
{

inline oarchive::oarchive(json::writer::output_type& output)
    : writer(output)
{
}

inline void oarchive::save()
{
    writer.write();
}

inline void oarchive::save(bool value)
{
    writer.write(value);
}

inline void oarchive::save(int value)
{
    writer.write(value);
}

inline void oarchive::save(long long value)
{
    writer.write(value);
}

inline void oarchive::save(float value)
{
    writer.write(value);
}

inline void oarchive::save(double value)
{
    writer.write(value);
}

inline void oarchive::save(const char *value)
{
    writer.write(value);
}

inline void oarchive::save(const std::string& value)
{
    writer.write(value);
}

inline void oarchive::save_binary(const char *data, std::size_t size)
{
    writer.write(data, size);
}

inline void oarchive::save_record_begin()
{
    writer.write_record_begin();
}

inline void oarchive::save_record_end()
{
    writer.write_record_end();
}

inline void oarchive::save_array_begin()
{
    writer.write_array_begin();
}

inline void oarchive::save_array_begin(std::size_t size)
{
    writer.write_array_begin(size);
}

inline void oarchive::save_array_end()
{
    writer.write_array_end();
}

inline void oarchive::save_map_begin()
{
    writer.write_map_begin();
}

inline void oarchive::save_map_begin(std::size_t size)
{
    writer.write_map_begin(size);
}

inline void oarchive::save_map_end()
{
    writer.write_map_end();
}

} // namespace json
} // namespace protoc

#endif // PROTOC_JSON_OARCHIVE_HPP
