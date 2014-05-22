#ifndef PROTOC_TRANSENC_DECODER_HPP
#define PROTOC_TRANSENC_DECODER_HPP

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
#include <protoc/types.hpp>
#include <protoc/input_range.hpp>
#include <protoc/transenc/token.hpp>

namespace protoc
{
namespace transenc
{
namespace detail
{

class decoder
{
public:
    typedef protoc::input_range<protoc::uint8_t> input_range;

    decoder(input_range::const_iterator begin, input_range::const_iterator end);
    decoder(const decoder&);

    token type() const;
    void next();

    protoc::int8_t get_int8() const;
    protoc::int16_t get_int16() const;
    protoc::int32_t get_int32() const;
    protoc::int64_t get_int64() const;
    protoc::float32_t get_float32() const;
    protoc::float64_t get_float64() const;
    std::string get_binary() const;
    std::string get_string() const;

private:
    token next_int8();
    token next_int16();
    token next_int32();
    token next_int64();
    token next_float32();
    token next_float64();
    token next_binary();
    token next_string();

    token next_unknown();

    token next(token, std::size_t);

private:
    input_range input;
    struct
    {
        token type;
        input_range range;
    } current;
};

} // namespace detail
} // namespace transenc
} // namespace protoc

#endif /* PROTOC_TRANSENC_DECODER_HPP */
