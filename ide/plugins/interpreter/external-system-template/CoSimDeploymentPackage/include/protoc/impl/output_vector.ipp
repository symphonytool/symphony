#ifndef PROTOC_IMPL_OUTPUT_VECTOR_IPP
#define PROTOC_IMPL_OUTPUT_VECTOR_IPP

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

namespace protoc
{

template<typename Value>
typename output_vector<Value>::const_iterator output_vector<Value>::begin() const
{
    return buffer.begin();
}

template<typename Value>
typename output_vector<Value>::const_iterator output_vector<Value>::end() const
{
    return buffer.end();
}

template<typename Value>
typename output_vector<Value>::size_type output_vector<Value>::size() const
{
    return buffer.size();
}

template<typename Value>
typename output_vector<Value>::size_type output_vector<Value>::capacity() const
{
    return buffer.capacity();
}

template<typename Value>
typename output_vector<Value>::const_reference output_vector<Value>::operator [] (size_type ix) const
{
    assert(ix < buffer.size());
    return buffer[ix];
}

template<typename Value>
bool output_vector<Value>::grow(size_type delta)
{
    const size_type size = buffer.size() + delta;
    if (size > buffer.capacity())
    {
        if (size > buffer.max_size())
            return false;
        buffer.reserve(size);
    }
    return true;
}

template<typename Value>
void output_vector<Value>::write(value_type value)
{
    buffer.push_back(value);
}

} // namespace protoc

#endif // PROTOC_IMPL_OUTPUT_VECTOR_IPP
