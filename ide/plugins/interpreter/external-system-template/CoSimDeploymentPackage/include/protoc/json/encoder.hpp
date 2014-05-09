#ifndef PROTOC_JSON_ENCODER_HPP
#define PROTOC_JSON_ENCODER_HPP

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
#include <protoc/output.hpp>
#include <protoc/encoder_base.hpp>

namespace protoc
{
namespace json
{
namespace detail
{

class encoder : protoc::encoder_base
{
public:
    typedef protoc::output<char> output_type;

    encoder(output_type&);

    std::size_t put(); // Null
    std::size_t put(bool);
    std::size_t put(int);
    std::size_t put(long long);
    std::size_t put(float);
    std::size_t put(double);
    std::size_t put(const char *);
    std::size_t put(const std::string&);

    std::size_t put_record_begin();
    std::size_t put_record_end();
    std::size_t put_array_begin();
    std::size_t put_array_begin(std::size_t);
    std::size_t put_array_end();
    std::size_t put_map_begin();
    std::size_t put_map_begin(std::size_t);
    std::size_t put_map_end();

    std::size_t put(const char *, std::size_t);

    std::size_t put_comma();
    std::size_t put_colon();

private:
    std::size_t put_text(const char *, std::size_t);
    std::size_t put_value(output_type::value_type);

private:
    output_type& buffer;
};

} // namespace detail
} // namespace json
} // namespace protoc

#endif // PROTOC_JSON_ENCODER_HPP
