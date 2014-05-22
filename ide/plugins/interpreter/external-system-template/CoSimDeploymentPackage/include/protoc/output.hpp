#ifndef PROTOC_OUTPUT_HPP
#define PROTOC_OUTPUT_HPP

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

// Interface for encoder output
template<typename Value>
class output
{
public:
    typedef Value value_type;
    typedef std::size_t size_type;

    virtual ~output() {}

    virtual bool grow(size_type) = 0;
    virtual void write(value_type) = 0;
};

}

#endif /* PROTOC_OUTPUT_HPP */
