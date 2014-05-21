#ifndef PROTOC_OUTPUT_STREAM_HPP
#define PROTOC_OUTPUT_STREAM_HPP

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

#include <cstddef> // std::size_t
#include <ostream>
#include <protoc/output.hpp>

namespace protoc
{

template<typename Value>
class output_stream : public output<Value>
{
public:
    typedef typename output<Value>::value_type value_type;
    typedef typename output<Value>::size_type size_type;

    typedef value_type& reference;
    typedef const value_type& const_reference;

    output_stream(std::ostream& stream)
        : stream(stream)
    {
    }

private:
    // Implementation of protoc::output interface
    virtual bool grow(size_type delta)
    {
        return stream.good();
    }

    virtual void write(value_type value)
    {
        stream << value;
    }

private:
    std::ostream& stream;
};

}

#endif /* PROTOC_OUTPUT_STREAM_HPP */
