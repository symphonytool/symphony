#ifndef PROTOC_JSON_WRITER_HPP
#define PROTOC_JSON_WRITER_HPP

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
#include <boost/move/move.hpp>
#include <protoc/output.hpp>
#include <protoc/json/token.hpp>
#include <protoc/json/encoder.hpp>

namespace protoc
{
namespace json
{

class writer
{
public:
    typedef protoc::output<char> output_type;
    typedef std::size_t size_type;

    writer(output_type& output);

    size_type size() const;

    void write(); // Null

    template <typename T>
    void write(BOOST_FWD_REF(T) value);

    void write(const output_type::value_type *data, size_type size);

    void write_array_begin();
    void write_array_begin(size_type);
    void write_array_end();

    void write_record_begin();
    void write_record_end();

    void write_map_begin();
    void write_map_begin(size_type);
    void write_map_end();

private:
    inline void validate();
    
private:
    detail::encoder encoder;

    struct frame
    {
        frame(detail::encoder& encoder, detail::token token);

        bool is_array() const;
        bool is_object() const;

        void write_separator();

        detail::encoder& encoder;
        detail::token token;
        std::size_t counter;
    };
    std::stack<frame> stack;
};

} // namespace json
} // namespace protoc

#include <protoc/exceptions.hpp>

namespace protoc
{
namespace json
{

inline writer::writer(output_type& output)
    : encoder(output)
{
    // Push outer scope
    stack.push(frame(encoder, detail::token_array_end));
}

inline writer::size_type writer::size() const
{
    return stack.size() - 1;
}

inline void writer::write()
{
    validate();
    stack.top().write_separator();
    encoder.put();
}

template <typename T>
void writer::write(BOOST_FWD_REF(T) value)
{
    validate();
    stack.top().write_separator();
    encoder.put(boost::forward<T>(value));
}

inline void writer::write(const output_type::value_type *data, size_type size)
{
    validate();
    stack.top().write_separator();
    encoder.put(data, size);
}

inline void writer::write_array_begin()
{
    validate();
    stack.top().write_separator();
    stack.push(frame(encoder, detail::token_array_end));
    encoder.put_array_begin();
}

inline void writer::write_array_begin(size_type)
{
    write_array_begin();
}

inline void writer::write_array_end()
{
    validate();
    if (!stack.top().is_array())
    {
        throw unexpected_token("expected array end");
    }
    encoder.put_array_end();
    stack.pop();
}

inline void writer::write_record_begin()
{
    write_array_begin();
}

inline void writer::write_record_end()
{
    write_array_end();
}

inline void writer::write_map_begin()
{
    stack.push(frame(encoder, detail::token_object_end));
    encoder.put_map_begin();
}

inline void writer::write_map_begin(size_type)
{
    write_map_begin();
}

inline void writer::write_map_end()
{
    validate();
    if (!stack.top().is_object())
    {
        throw unexpected_token("expected array end");
    }
    encoder.put_map_end();
    stack.pop();
}

inline void writer::validate()
{
    if (stack.empty())
    {
        throw invalid_scope("stack empty");
    }
}

inline writer::frame::frame(detail::encoder& encoder, detail::token token)
    : encoder(encoder),
      token(token),
      counter(0)
{
}

inline bool writer::frame::is_array() const
{
    return token == detail::token_array_end;
}

inline bool writer::frame::is_object() const
{
    return token == detail::token_object_end;
}

inline void writer::frame::write_separator()
{
    if (counter != 0)
    {
        if (is_array())
        {
            encoder.put_comma();
        }
        else if (is_object())
        {
            if (counter % 2 == 0)
            {
                encoder.put_comma();
            }
            else
            {
                encoder.put_colon();
            }
        }
    }
    ++counter;
}

} // namespace json
} // namespace protoc

#endif // PROTOC_JSON_WRITER_HPP
