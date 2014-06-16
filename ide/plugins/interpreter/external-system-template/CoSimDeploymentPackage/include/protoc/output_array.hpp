#ifndef PROTOC_OUTPUT_ARRAY_HPP
#define PROTOC_OUTPUT_ARRAY_HPP

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

#include <cassert>
#include <protoc/output.hpp>

namespace protoc
{

template<typename Value, std::size_t N>
class output_array : public output<Value>
{
public:
    typedef typename output<Value>::value_type value_type;
    typedef typename output<Value>::size_type size_type;
    typedef value_type* iterator;
    typedef const value_type* const_iterator;
    typedef value_type& reference;
    typedef const value_type& const_reference;

public:
    output_array()
        : current(content)
    {
    }

    const_iterator begin() const
    {
        return content;
    }

    const_iterator end() const
    {
        return current;
    }

    // Returns the number of elements that has been inserted into the array
    size_type size() const
    {
        return (current - content);
    }

    size_type capacity() const
    {
        return N;
    }

    const_reference operator [] (size_type ix) const
    {
        assert(ix < N);
        return content[ix];
    }

private:
    // Implementation of protoc::output interface

    virtual bool grow(size_type delta)
    {
        return (N - size() >= delta);
    }

    virtual void write(value_type value)
    {
        assert(grow(sizeof(value_type)));
        *current = value;
        ++current;
    }

private:
    value_type content[N]; // Fixed-size array
    iterator current;
};


}

#endif /* PROTOC_OUTPUT_ARRAY_HPP */
