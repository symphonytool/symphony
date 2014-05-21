#ifndef PROTOC_READER_HPP
#define PROTOC_READER_HPP

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
#include <string>
#include <protoc/token.hpp>

namespace protoc
{

class reader
{
public:
    typedef std::size_t size_type;

    virtual ~reader() {}

    virtual token::value type() const = 0;
    virtual size_type size() const = 0;
    virtual bool next() = 0;
    virtual bool next(token::value) = 0;
    virtual void next_sibling() = 0;

    // FIXME: Consider a visitor instead (and standalone get() function ala variant::get)
    virtual bool get_bool() const = 0;
    virtual int get_int() const = 0;
    virtual long long get_long_long() const = 0;
    virtual double get_double() const = 0;
    virtual std::string get_string() const = 0;
};

} // namespace protoc

#endif // PROTOC_READER_HPP
