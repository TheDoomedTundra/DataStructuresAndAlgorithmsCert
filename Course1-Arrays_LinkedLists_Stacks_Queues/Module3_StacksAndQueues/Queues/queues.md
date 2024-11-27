# Stacks
Like queues, are a Linear ADT
Unlike queues, are FIFO <u>not</u> LIFO
    <br>-> Like a wait list or line, first there is first served

## Supported Ops
* `void enqueue(x)`
    * Add element to back
* `x dequeue()`
    * Return/remove element at front
* `x peek() / x top()`
    * Returns data at front of queue
* `boolean isEmpty()`
* `void clear()`

## Unsupported Ops
* Searching for data
* Arbitrary index access
* Arbitrary add/remove

## Linked List Backed Queues
* Single
    * Enqueue tail, dequeue head
* Double
    * No real need, just higher memory consumption

## Array Backed Queues
* Use wrap-around behavior to improve efficiency of enqueue and dequeue

## Why use arrays?
* Arrays benefit from **spatial locality** while LinkedLists do not since the processor cannot know what the adjacent data is and fetch it ahead of time.