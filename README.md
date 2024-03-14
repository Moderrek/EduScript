<div align="center">
    <h1>🌎 EduScript</h1>

  [Playground](http://play.eduscript.pl)
  [Learn](http://nauka.eduscript.pl)
  [Download](http://play.eduscript.pl)
  
</div>

EduScript is not production language! 
The language is intended to help you learn programming in Polish.

## Features
 * Polish syntax
 * Variables
 * Builtin functions (wypisz)

## Required
 * Java 17
 * Knowledge of the Polish language :)

# Examples
### EduScript
```es
zmien promien = 5;
zmien pole = promien * promien * pi;
wypisz("Pole wynosi " + pole);
```
### Python
```py
promien = 5
pole = promien * promien * pi;
print(f"Pole wynosi {pole}")
```

## Roadmap
### Comments
```es
# comment
// comment
/*
multi
line
*/
"""
multi
line
"""
```
### if
```es
jezeli CONDITION {} albo {}
```
### if x % y == 0
```es
jezeli x podzielne y {}
```
### match | switch
```es
dopasuj VALUE {
  5 {
    wypisz("VALUE = 5");
  }
  ? {
    wypisz("VALUE UNKNOWN")
  }
}
```
### Short function
```es
fn add(a: calk, b: calk) = a + b;
fn add(a: calk, b: calk) {
  zwroc a + b;
}
```
### Str concat (C like)
```es
"123""345" -> "12345"
```
### While true loop
```es
petla {}
```
### Break, continue
```es
petla {
  stop; // break
  pomin; // continue (skip)
}
```
