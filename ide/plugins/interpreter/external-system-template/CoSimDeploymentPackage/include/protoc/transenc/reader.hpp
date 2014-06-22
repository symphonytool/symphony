#ifndef PROTOC_TRANSENC_READER_HPP
#define PROTOC_TRANSENC_READER_HPP

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

#include <stack>
#include <protoc/reader.hpp>
#include <protoc/transenc/token.hpp>
#include <protoc/transenc/decoder.hpp>

namespace protoc
{
namespace transenc
{

class reader : public protoc::reader
{
public:
    template <typename ForwardIterator>
    reader(ForwardIterator begin, ForwardIterator end);
    reader(const reader&);

    virtual protoc::token::value type() const;
    virtual size_type size() const;

    virtual bool next();
    virtual bool next(protoc::token::value);
    virtual void next_sibling();

    virtual bool get_bool() const;
    virtual int get_int() const;
    virtual long long get_long_long() const;
    virtual double get_double() const;
    virtual std::string get_string() const;

private:
    transenc::detail::decoder decoder;
    std::stack<transenc::detail::token> stack;
};

} // namespace transenc
} // namespace protoc

#include <sstream>
#include <protoc/exceptions.hpp>

namespace protoc
{
namespace transenc
{

template <typename ForwardIterator>
reader::reader(ForwardIterator begin, ForwardIterator end)
    : decoder(begin, end)
{
}

inline reader::reader(const reader& other)
    : decoder(other.decoder)
{
}

inline protoc::token::value reader::type() const
{
    const transenc::detail::token current = decoder.type();
    switch (current)
    {
    case transenc::detail::token_null:
        return protoc::token::token_null;

    case transenc::detail::token_true:
    case transenc::detail::token_false:
        return protoc::token::token_boolean;

    case transenc::detail::token_int8:
    case transenc::detail::token_int16:
    case transenc::detail::token_int32:
    case transenc::detail::token_int64:
        return protoc::token::token_integer;

    case transenc::detail::token_float32:
    case transenc::detail::token_float64:
        return protoc::token::token_floating;

    case transenc::detail::token_string:
        return protoc::token::token_string;

    case transenc::detail::token_binary:
        return protoc::token::token_binary;

    case transenc::detail::token_record_begin:
        return protoc::token::token_record_begin;

    case transenc::detail::token_record_end:
        return protoc::token::token_record_end;

    case transenc::detail::token_array_begin:
        return protoc::token::token_array_begin;

    case transenc::detail::token_array_end:
        return protoc::token::token_array_end;

    case transenc::detail::token_map_begin:
        return protoc::token::token_map_begin;

    case transenc::detail::token_map_end:
        return protoc::token::token_map_end;

    case transenc::detail::token_eof:
        return protoc::token::token_eof;

    case transenc::detail::token_error:
        throw unexpected_token("token_error");

    default:
        std::ostringstream error;
        error << current;
        throw unexpected_token(error.str());
    }
}

inline reader::size_type reader::size() const
{
    return stack.size();
}

inline bool reader::next()
{
    const transenc::detail::token current = decoder.type();
    switch (current)
    {
    case transenc::detail::token_record_begin:
        stack.push(transenc::detail::token_record_end);
        break;

    case transenc::detail::token_array_begin:
        stack.push(transenc::detail::token_array_end);
        break;

    case transenc::detail::token_map_begin:
        stack.push(transenc::detail::token_map_end);
        break;

    default:
        break;
    }

    decoder.next();

    switch (current)
    {
    case transenc::detail::token_record_end:
        if (stack.top() != transenc::detail::token_record_end)
        {
            throw unexpected_token("expected record end");
        }
        stack.pop();
        break;

    case transenc::detail::token_array_end:
        if (stack.top() != transenc::detail::token_array_end)
        {
            throw unexpected_token("expected array end");
        }
        stack.pop();
        break;

    case transenc::detail::token_map_end:
        if (stack.top() != transenc::detail::token_map_end)
        {
            throw unexpected_token("expected map end");
        }
        stack.pop();
        break;

    default:
        break;
    }

    return (type() != protoc::token::token_eof);
}

inline bool reader::next(protoc::token::value expect)
{
    const protoc::token::value current = type();
    if (current != expect)
    {
        std::ostringstream error;
        error << current;
        throw unexpected_token(error.str());
    }
    return next();
}

inline void reader::next_sibling()
{
    // FIXME: Skip over children
    throw unexpected_token("not implemented");
}

inline bool reader::get_bool() const
{
    const transenc::detail::token current = decoder.type();
    switch (current)
    {
    case transenc::detail::token_true:
        return true;

    case transenc::detail::token_false:
        return false;

    default:
        std::ostringstream error;
        error << current;
        throw invalid_value(error.str());
    }
}

inline int reader::get_int() const
{
    const transenc::detail::token current = decoder.type();
    switch (current)
    {
    case transenc::detail::token_int8:
        return decoder.get_int8();

    case transenc::detail::token_int16:
        return decoder.get_int16();

    case transenc::detail::token_int32:
        return decoder.get_int32();

    case transenc::detail::token_int64:
        // FIXME: Use numeric_limits<T>::max() to check if value will fit
        // FIXME: But fall-through for now
    default:
        std::ostringstream error;
        error << current;
        throw invalid_value(error.str());
    }
}

inline long long reader::get_long_long() const
{
    const transenc::detail::token current = decoder.type();
    switch (current)
    {
    case transenc::detail::token_int8:
        return decoder.get_int8();

    case transenc::detail::token_int16:
        return decoder.get_int16();

    case transenc::detail::token_int32:
        return decoder.get_int32();

    case transenc::detail::token_int64:
        return decoder.get_int64();

    default:
        std::ostringstream error;
        error << current;
        throw invalid_value(error.str());
    }
}

inline double reader::get_double() const
{
    // FIXME: Use numeric_limits<T>::max() to check if value will fit
    const transenc::detail::token current = decoder.type();
    switch (current)
    {
    case transenc::detail::token_float32:
        return decoder.get_float32();

    case transenc::detail::token_float64:
        return decoder.get_float64();

    default:
        std::ostringstream error;
        error << current;
        throw invalid_value(error.str());
    }
}

inline std::string reader::get_string() const
{
    const transenc::detail::token current = decoder.type();
    switch (current)
    {
    case transenc::detail::token_string:
        return decoder.get_string();

    default:
        std::ostringstream error;
        error << current;
        throw invalid_value(error.str());
    }
}

} // namespace transenc
} // namespace protoc

#endif // PROTOC_TRANSENC_READER_HPP
