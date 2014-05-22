#ifndef PROTOC_OUTPUT_VECTOR_HPP
#define PROTOC_OUTPUT_VECTOR_HPP

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
#include <vector>
#include <protoc/output.hpp>

namespace protoc
{

template<typename Value>
class output_vector : public output<Value>
{
public:
    typedef typename output<Value>::value_type value_type;
    typedef typename output<Value>::size_type size_type;

    typedef typename std::vector<value_type>::iterator iterator;
    typedef typename std::vector<value_type>::const_iterator const_iterator;
    typedef value_type& reference;
    typedef const value_type& const_reference;

public:
    const_iterator begin() const;
    const_iterator end() const;

    size_type size() const;
    size_type capacity() const;

    const_reference operator [] (size_type ix) const;

private:
    // Implementation of protoc::output interface
    virtual bool grow(size_type delta);
    virtual void write(value_type value);

private:
    std::vector<value_type> buffer;
};

} // namespace protoc

#include <protoc/impl/output_vector.ipp>

#endif /* PROTOC_OUTPUT_VECTOR_HPP */
