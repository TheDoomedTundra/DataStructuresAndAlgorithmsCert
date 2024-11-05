# Value/Reference Equality with Primitives and Strings
Reference equality is done with (==)
Value equality is done with .equals(), by default decays to (==)

## Primitives
Primitives have no difference between == and .equals()

## String Literals
* String literals/constants are stored in a string pool for faster access, thus are similar to primitives.
* You can use `new` to allow for differentiation with .equals() and ==

## Wrapper Objects
Wrappers are required to create collections, which leads to a change in the expected behavior of == and isequal(), since we are no longer using the primitive.

However, a wrapper can be compared with a primitive using == 

```
Integer one = 1;
Integer one_2 = 1;

primitive == object; // => false
primitive == 1; // => true
```

# Pass by Value/Reference
Java is a **pass by value** language.

When passing a "reference variable" to a function, you get a copy of that reference which still points to
the same object. Therefore, you can use the object to modify itself by calling member methods, but not by assigning new objects to it.

# Generics
These are just templates, so we get compile-time checking instead of runtime and avoid casts.

Uses the same \<T> syntax as C++, but no template\<typename T> required. You don't need duplicate timing at definition time though:
```
ArrayList<Integer> listOne = new ArrayList<Integer>();

ArrayList<Integer> listTwo = newArrayList<>();
```

You can create generic arrays, albeit not directly. You must cast them:
```
T[] backingArray = (T[]) new Object[10];
```