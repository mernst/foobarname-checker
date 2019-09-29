# Foobar Name Checker

Some String variables contain Foobar Names.
Others do not.
The Foobar Name Checker helps you ensure that you are using Foobar Names
when you should be.

Every String variable whose name contains `foobarName` (case-insensitive)
is defaulted to type `@FoobarName String`.


## How to run the checker

First, publish the checker to your local Maven repository by running
`./gradlew publish` in this repository.

Then, if you use Gradle, add the following to the `build.gradle` file in
the project you wish to type-check (using Maven is similar):

```
repositories {
    mavenLocal()
    mavenCentral()
}
dependencies {
    annotationProcessor 'org.checkerframework.checker.foobarname:0.1-SNAPSHOT'
}
```

Now, when you build your project, the Foobar Name Checker will also run,
informing you of any potential errors related to foobar names.


## How to specify your code

At compile time, the Foobar NameChecker estimates what values the program
may compute at run time.  It issues a warning if the program may use a
foobar name incorrectly.
It works via a technique called pluggable typechecking.

You need to specify the contracts of methods and fields in your code --
that is, their requirements and their guarantees.  The Foobar NameChecker
ensures that your code is consistent with the contracts, and that the
contracts guarantee that foobar names are used consistently.

You specify your code by writing *qualifiers* such as `@FoobarName`
on types, to indicate more precisely what values the type represents.
Here are the type qualifiers that are supported by the Foobar NameChecker:

`@FoobarNameUnknown`:
The value might or might not be a foobar name.
This is the default type, so programmers usually do not need to write it.

`@FoobarName`:
The value is definitely a foobar name.


## How to build the checker

Run these commands from the top-level directory.

`./gradlew build`: build the checker

`./gradlew publish`: publish the checker to your local Maven repository.
This is useful for testing before you publish it elsewhere, such as to Maven Central.


## More information

The Foobar Name Checker is built upon the Checker Framework.  Please see
the [Checker Framework Manual](https://checkerframework.org/manual/) for
more information about using pluggable type-checkers, including this one.
