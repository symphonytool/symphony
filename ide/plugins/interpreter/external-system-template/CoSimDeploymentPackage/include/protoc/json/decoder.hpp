#ifndef PROTOC_JSON_DECODER_HPP
#define PROTOC_JSON_DECODER_HPP

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
#include <protoc/json/token.hpp>

namespace protoc
{
namespace json
{
namespace detail
{

class decoder
{
    typedef protoc::input_range<char> input_range;

public:
    typedef input_range::value_type value_type;

    decoder(const char *begin, const char *end);

    token type() const;
    void next();

    std::string get_string() const;
    protoc::int64_t get_integer() const;
    protoc::float64_t get_float() const;

private:
    token next_f_keyword();
    token next_n_keyword();
    token next_t_keyword();
    token next_number();
    token next_string();

    token struct_state();
    token end_state();
    token bad_state();

    void skip_whitespaces();
    bool at_keyword_end() const;

private:
    input_range input;
    struct
    {
        token type;
        input_range range;
    } current;
};

} // namespace detail
} // namespace json
} // namespace protoc

#endif /* PROTOC_JSON_DECODER_HPP */
