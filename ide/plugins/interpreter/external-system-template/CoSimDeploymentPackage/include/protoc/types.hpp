#ifndef PROTOC_TYPES_HPP
#define PROTOC_TYPES_HPP

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
#include <boost/cstdint.hpp>

namespace protoc
{

typedef boost::int8_t int8_t;
typedef boost::int16_t int16_t;
typedef boost::int32_t int32_t;
typedef boost::int64_t int64_t;

typedef boost::uint8_t uint8_t;
typedef boost::uint16_t uint16_t;
typedef boost::uint32_t uint32_t;
typedef boost::uint64_t uint64_t;

typedef float float32_t;
typedef double float64_t;

}

#endif /* PROTOC_TYPES_HPP */
