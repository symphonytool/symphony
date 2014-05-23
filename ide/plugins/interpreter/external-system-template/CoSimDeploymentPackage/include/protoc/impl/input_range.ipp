#ifndef PROTOC_IMPL_INPUT_RANGE_IPP
#define PROTOC_IMPL_INPUT_RANGE_IPP

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

template<typename Value>
input_range<Value>::input_range()
    : current(0),
      last(0)
{
}

template<typename Value>
input_range<Value>::~input_range()
{
}

template<typename Value>
input_range<Value>::input_range(const input_range& other)
    : current(other.current),
      last(other.last)
{
}

template<typename Value>
input_range<Value>::input_range(const_iterator first,
                                const_iterator last)
    : current(const_cast<iterator>(first)),
      last(const_cast<iterator>(last))
{
}

template<typename Value>
input_range<Value>& input_range<Value>::operator = (const input_range& other)
{
    current = other.current;
    last = other.last;
    return *this;
}

template<typename Value>
input_range<Value>& input_range<Value>::operator += (size_type delta)
{
    if (delta < size())
    {
        current += delta;
    }
    else
    {
        current = last;
    }
    return *this;
}

template<typename Value>
bool input_range<Value>::empty() const
{
    return (current == last);
}

template<typename Value>
const typename input_range<Value>::value_type& input_range<Value>::operator * () const
{
    return *current;
}

template<typename Value>
input_range<Value>& input_range<Value>::operator ++ ()
{
    ++current;
    return *this;
}

template<typename Value>
input_range<Value> input_range<Value>::operator ++ (int)
{
    input_range result(*this);
    ++current;
    return result;
}

template<typename Value>
typename input_range<Value>::const_iterator input_range<Value>::begin() const
{
    return current;
}

template<typename Value>
typename input_range<Value>::const_iterator input_range<Value>::end() const
{
    return last;
}

template<typename Value>
typename input_range<Value>::size_type input_range<Value>::size() const
{
    return (last - current);
}

} // namespace protoc


#endif // PROTOC_IMPL_INPUT_RANGE_IPP
