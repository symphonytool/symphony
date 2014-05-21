#ifndef PROTOC_JSON_READER_HPP
#define PROTOC_JSON_READER_HPP

///////////////////////////////////////////////////////////////////////////////
//
// http://protoc.sourceforge.net/
//
// Copyright (C) 2014 Bjorn Reese <breese@users.sourceforge.net>
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
#include <protoc/json/token.hpp>
#include <protoc/json/decoder.hpp>

namespace protoc
{
namespace json
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
    detail::decoder decoder;

    struct frame
    {
        frame(detail::token token);

        bool is_array() const;
        bool is_object() const;

        void next(detail::decoder&);

        detail::token token;
        std::size_t counter;
    };
    std::stack<frame> stack;
};

} // namespace json
} // namespace protoc

#include <sstream>
#include <protoc/exceptions.hpp>

namespace protoc
{
namespace json
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
    const detail::token current = decoder.type();
    switch (current)
    {
    case detail::token_null:
        return protoc::token::token_null;

    case detail::token_true:
    case detail::token_false:
        return protoc::token::token_boolean;

    case detail::token_integer:
        return protoc::token::token_integer;

    case detail::token_float:
        return protoc::token::token_floating;

    case detail::token_string:
        return protoc::token::token_string;

    case detail::token_array_begin:
        return protoc::token::token_array_begin;

    case detail::token_array_end:
        return protoc::token::token_array_end;

    case detail::token_object_begin:
        return protoc::token::token_map_begin;

    case detail::token_object_end:
        return protoc::token::token_map_end;

    case detail::token_eof:
        return protoc::token::token_eof;

    case detail::token_error:
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
    const detail::token current = decoder.type();
    switch (current)
    {
    case detail::token_array_begin:
        stack.push(detail::token_array_end);
        break;

    case detail::token_array_end:
        if (stack.empty())
        {
            throw unexpected_token("unbalanced array end");
        }
        if (!stack.top().is_array())
        {
            throw unexpected_token("expected array end");
        }
        stack.pop();
        break;

    case detail::token_object_begin:
        stack.push(detail::token_object_end);
        break;

    case detail::token_object_end:
        if (stack.empty())
        {
            throw unexpected_token("unbalanced object end");
        }
        if (!stack.top().is_object())
        {
            throw unexpected_token("expected object end");
        }
        stack.pop();
        break;

    default:
        break;
    }

    if (stack.empty())
    {
        decoder.next();
    }
    else
    {
        stack.top().next(decoder);
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
    const detail::token current = decoder.type();
    switch (current)
    {
    case detail::token_true:
        return true;

    case detail::token_false:
        return false;

    default:
        std::ostringstream error;
        error << current;
        throw invalid_value(error.str());
    }
}

inline int reader::get_int() const
{
    const detail::token current = decoder.type();
    switch (current)
    {
    case detail::token_integer:
        return decoder.get_integer();

    default:
        std::ostringstream error;
        error << current;
        throw invalid_value(error.str());
    }
}

inline long long reader::get_long_long() const
{
    const detail::token current = decoder.type();
    switch (current)
    {
    case detail::token_integer:
        return decoder.get_integer();

    default:
        std::ostringstream error;
        error << current;
        throw invalid_value(error.str());
    }
}

inline double reader::get_double() const
{
    const detail::token current = decoder.type();
    switch (current)
    {
    case detail::token_float:
        return decoder.get_float();

    default:
        std::ostringstream error;
        error << current;
        throw invalid_value(error.str());
    }
}

inline std::string reader::get_string() const
{
    const detail::token current = decoder.type();
    switch (current)
    {
    case detail::token_string:
        return decoder.get_string();

    default:
        std::ostringstream error;
        error << current;
        throw invalid_value(error.str());
    }
}

inline reader::frame::frame(detail::token token)
    : token(token),
      counter(0)
{
}

inline bool reader::frame::is_array() const
{
    return token == detail::token_array_end;
}

inline bool reader::frame::is_object() const
{
    return token == detail::token_object_end;
}

inline void reader::frame::next(detail::decoder& decoder)
{
    //   container = array / object
    //   array = "[" *element "]"
    //   element = value *( "," value )
    //   object = "{" *member "}"
    //   member = pair *( "," pair )
    //   pair = string ":" value

    decoder.next();
    ++counter;

    const detail::token current = decoder.type();

    // After the increment, odd tokens are values and even tokens are separators
    if (counter % 2 != 0)
        return;

    if (token == detail::token_array_end)
    {
        if (current == detail::token_array_end)
            return;
        if (current == detail::token_comma)
        {
            decoder.next();
            ++counter;
            switch (decoder.type())
            {
            case detail::token_array_end:
            case detail::token_object_end:
                goto error;
            default:
                return;
            }
        }
    }
    else if (token == detail::token_object_end)
    {
        if (counter % 4 == 0)
        {
            if (current == detail::token_object_end)
                return;
            if (current == detail::token_comma)
            {
                decoder.next();
                ++counter;
                return;
            }
        }
        else if (counter % 4 == 2)
        {
            if (current == detail::token_colon)
            {
                decoder.next();
                ++counter;
                switch (decoder.type())
                {
                case detail::token_array_end:
                case detail::token_object_end:
                    goto error;
                default:
                    return;
                }
            }
        }
    }

 error:
    std::ostringstream error;
    error << current;
    throw unexpected_token(error.str());
}

} // namespace json	
} // namespace protoc

#endif // PROTOC_JSON_READER_HPP
