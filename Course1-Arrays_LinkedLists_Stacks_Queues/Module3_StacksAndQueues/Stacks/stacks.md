# Stacks
## Linear Abstract Data Types (ADTs)
### What makes it linear?
1. Each object has one immediate successor and predecessor (except the first/last objects)

* The DT is a container with a finite number of objects.
* There is a relationship between the objects being stored.
* Defined by the operations or behavior of operations.
* Can be backed by various data structures, typically an array or linked list

## What is a stack?
* Like a can of pringles
    <br/>-> Push items on, pop them off (LIFO)

### Supported Operations
* `void push(x)`
* `x pop()`
* `x peek() / x top()`
    * Just reads the first, doesn't pop it
* `isEmpty()`
* `clear()`
* `size()`

### Unsupported operations
* Searching for data
* Arbitrary index access
* Arbitrary add/remove

## Performance of a Singly Linked-List Backed Stack
* No tail pointer
* Lightweight
* Singly Linked List is best