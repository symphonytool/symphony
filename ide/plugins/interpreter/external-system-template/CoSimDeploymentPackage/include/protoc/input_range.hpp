#ifndef PROTOC_INPUT_RANGE_HPP
#define PROTOC_INPUT_RANGE_HPP

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

namespace protoc
{

template<typename Value>
class input_range
{
public:
    typedef Value value_type;
    typedef std::size_t size_type;
    typedef value_type* iterator;
    typedef const value_type* const_iterator;

    // Default constructor/destructor
    input_range();
    virtual ~input_range();
    // Copy constructor
    input_range(const input_range&);
    // Constructor
    input_range(const_iterator first, const_iterator last);
    // Assignment operator
    input_range& operator = (const input_range&);

    input_range& operator += (size_type);

    bool empty() const;

    const value_type& operator * () const;

    input_range& operator ++ ();
    input_range operator ++ (int);

    const_iterator begin() const;
    const_iterator end() const;

    size_type size() const;

protected:
    const_iterator current;
    const_iterator last;
};

} // namespace protoc

#include <protoc/impl/input_range.ipp>

#endif /* PROTOC_INPUT_RANGE_HPP */
