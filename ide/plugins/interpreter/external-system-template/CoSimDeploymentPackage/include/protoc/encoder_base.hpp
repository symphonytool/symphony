#ifndef PROTOC_ENCODER_BASE_HPP
#define PROTOC_ENCODER_BASE_HPP

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

namespace protoc
{

class encoder_base
{
public:
    virtual ~encoder_base() {}

    // Primitive types
    virtual std::size_t put() = 0; // Null
    virtual std::size_t put(bool) = 0;
    virtual std::size_t put(int) = 0;
    virtual std::size_t put(long long) = 0;
    virtual std::size_t put(float) = 0;
    virtual std::size_t put(double) = 0;
    virtual std::size_t put(const char *) = 0;
    virtual std::size_t put(const std::string&) = 0;

    // Containers
    virtual std::size_t put_record_begin() = 0;
    virtual std::size_t put_record_end() = 0;
    virtual std::size_t put_array_begin() = 0;
    virtual std::size_t put_array_begin(std::size_t) = 0;
    virtual std::size_t put_array_end() = 0;
    virtual std::size_t put_map_begin() = 0;
    virtual std::size_t put_map_begin(std::size_t) = 0;
    virtual std::size_t put_map_end() = 0;
    // FIXME: put_separator(const char *) ?

    // Binary data
    virtual std::size_t put(const char *, std::size_t) = 0;
};

} // namespace protoc

#endif // PROTOC_ENCODER_BASE_HPP
