#ifndef PROTOC_TOKEN_HPP
#define PROTOC_TOKEN_HPP

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

namespace protoc
{

struct token
{
    enum value
    {
        token_eof,
        token_null,
        token_boolean,
        token_integer,
        token_floating,
        token_string,
        token_binary,

        token_record_begin,
        token_record_end,
        token_array_begin,
        token_array_end,
        token_map_begin,
        token_map_end
    };
};

} // namespace protoc

#endif // PROTOC_TOKEN_HPP
