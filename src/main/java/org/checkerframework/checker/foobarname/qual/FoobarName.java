package org.checkerframework.checker.foobarname.qual;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import org.checkerframework.framework.qual.SubtypeOf;
import org.checkerframework.framework.qual.TargetLocations;
import org.checkerframework.framework.qual.TypeUseLocation;

/** The string is a foobar name. */
// Annotations defined by Java
@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
// Annotations defined by the Checker Framework
@SubtypeOf(FoobarNameUnknown.class)
@TargetLocations({TypeUseLocation.EXPLICIT_LOWER_BOUND, TypeUseLocation.EXPLICIT_UPPER_BOUND})
public @interface FoobarName {}
