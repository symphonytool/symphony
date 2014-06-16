#ifndef PROTOC_MSGPACK_DECODER_HPP
#define PROTOC_MSGPACK_DECODER_HPP

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
#include <vector>
#include <protoc/types.hpp>
#include <protoc/input_range.hpp>
#include <protoc/msgpack/token.hpp>

namespace protoc
{
namespace msgpack
{

class decoder
{
    typedef protoc::input_range<char> input_range;

public:
    decoder(const char *begin, const char *end);

    token next();

    protoc::int8_t get_int8() const;
    protoc::int16_t get_int16() const;
    protoc::int32_t get_int32() const;
    protoc::int64_t get_int64() const;
    protoc::uint8_t get_uint8() const;
    protoc::uint16_t get_uint16() const;
    protoc::uint32_t get_uint32() const;
    protoc::uint64_t get_uint64() const;
    protoc::float32_t get_float32() const;
    protoc::float64_t get_float64() const;
    std::vector<protoc::uint8_t> get_raw() const;
    protoc::uint32_t get_count() const;

private:
    token next_int8();
    token next_int16();
    token next_int32();
    token next_int64();
    token next_uint8();
    token next_uint16();
    token next_uint32();
    token next_uint64();
    token next_float32();
    token next_float64();
    token next_raw8();
    token next_raw16();
    token next_raw32();
    token next_array8();
    token next_array16();
    token next_array32();
 
private:
    input_range input;
    struct
    {
        token type;
        input_range range;
    } current;
};

}
}

#endif /* PROTOC_MSGPACK_DECODER_HPP */
