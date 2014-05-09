#ifndef PROTOC_TRANSENC_ENCODER_HPP
#define PROTOC_TRANSENC_ENCODER_HPP

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

#include <cstdlib>
#include <string>
#include <protoc/types.hpp>
#include <protoc/output.hpp>
#include <protoc/encoder_base.hpp>

namespace protoc
{
namespace transenc
{

class encoder : public protoc::encoder_base
{
public:
    typedef protoc::output<protoc::uint8_t> output;

    encoder(output&);
    encoder(const encoder&);

    std::size_t put(); // Null
    std::size_t put(bool);
    std::size_t put(int);
    std::size_t put(long long);
    std::size_t put(protoc::float32_t);
    std::size_t put(protoc::float64_t);
    std::size_t put(const char *);
    std::size_t put(const std::string&);
    std::size_t put(const char *, std::size_t);

    std::size_t put_record_begin();
    std::size_t put_record_end();
    std::size_t put_array_begin();
    std::size_t put_array_begin(std::size_t);
    std::size_t put_array_end();
    std::size_t put_map_begin();
    std::size_t put_map_begin(std::size_t);
    std::size_t put_map_end();

private:
    std::size_t put_int8(protoc::int8_t);
    std::size_t put_int16(protoc::int16_t);
    std::size_t put_int32(protoc::int32_t);
    std::size_t put_int64(protoc::int64_t);
    std::size_t put_token(output::value_type);
    std::size_t put_size_t(std::size_t);

    std::size_t write(protoc::int8_t);
    std::size_t write(protoc::uint8_t);
    std::size_t write(protoc::int16_t);
    std::size_t write(protoc::uint16_t);
    std::size_t write(protoc::int32_t);
    std::size_t write(protoc::uint32_t);
    std::size_t write(protoc::int64_t);

private:
    output& buffer;
};

}
}

#endif /* PROTOC_TRANSENC_ENCODER_HPP */
