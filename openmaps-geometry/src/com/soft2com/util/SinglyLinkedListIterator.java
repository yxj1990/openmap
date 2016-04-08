/*-外部代码
 *出处:McGraw-Hill
 *摘录者:
 *摘录日期:
 *修改者:
 *修改日期:
 -*/
package com.soft2com.util;

class SinglyLinkedListIterator implements CIterator
{
    protected SinglyLinkedListElement current;
    protected SinglyLinkedListElement head;

    public SinglyLinkedListIterator( SinglyLinkedListElement t )
    // post: returns an iterator that traverses a linked list.
    {
        head = t;
        reset();
    }

    public void reset()
    // post: resets the iterator to point to the head of the list
    {
        current = head;
    }

    public boolean hasNext()
    // post: returns true iff there are unvisited elements
    {
        return current != null;
    }

    public Object next()
    // pre: hasMoreElements()
    // post: returns value and advances iterator
    {
        Object temp = current.value();
        current = current.next();
        return temp;
    }

    public Object value()
    // pre: hasMoreElements()
    // post: returns the current element referenced by the iterator.
    {
        return current.value();
    }
}