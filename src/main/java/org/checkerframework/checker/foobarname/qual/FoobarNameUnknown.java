package org.checkerframework.checker.foobarname.qual;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import org.checkerframework.framework.qual.DefaultQualifierInHierarchy;
import org.checkerframework.framework.qual.SubtypeOf;

/**
 * The string might or might not be a foobar name.
 *
 * <p>This is the default type, so programmers usually do not need to write it.
 */
// Meta-annotations defined by Java.
@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
// Meta-annotations defined by the Checker Framework.
@SubtypeOf({})
@DefaultQualifierInHierarchy
public @interface FoobarNameUnknown {}
