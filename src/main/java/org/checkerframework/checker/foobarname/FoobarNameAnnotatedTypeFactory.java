package org.checkerframework.checker.foobarname;

import java.util.Iterator;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import org.checkerframework.checker.foobarname.qual.FoobarName;
import org.checkerframework.checker.foobarname.qual.FoobarNameUnknown;
import org.checkerframework.common.basetype.BaseAnnotatedTypeFactory;
import org.checkerframework.common.basetype.BaseTypeChecker;
import org.checkerframework.framework.type.AnnotatedTypeFactory;
import org.checkerframework.framework.type.AnnotatedTypeMirror;
import org.checkerframework.framework.type.AnnotatedTypeMirror.AnnotatedExecutableType;
import org.checkerframework.framework.type.typeannotator.ListTypeAnnotator;
import org.checkerframework.framework.type.typeannotator.TypeAnnotator;
import org.checkerframework.javacutil.AnnotationBuilder;
import org.checkerframework.javacutil.TypesUtils;

/**
 * Adds @FoobarName annotation to conventionally-named variables and methods.
 *
 * <p>Does so for variables whose name is "foobar", starts with "foobarName", or ends with "Foobar"
 * or "FoobarName".
 *
 * <p>Does so for methods whose name starts with "get" and ends with "FoobarName".
 */
public class FoobarNameAnnotatedTypeFactory extends BaseAnnotatedTypeFactory {

  /** Set to true for verbose debugging output. */
  private static final boolean DEBUG = false;

  /** The @FoobarName annotation. */
  private AnnotationMirror foobarNameAnnotation;
  /** The @FoobarNameUnknown annotation. */
  private AnnotationMirror foobarNameUnknownAnnotation;

  /** Creates a FoobarNameAnnotatedTypeFactory. */
  public FoobarNameAnnotatedTypeFactory(BaseTypeChecker checker) {
    super(checker);
    this.postInit();
    foobarNameAnnotation = AnnotationBuilder.fromClass(elements, FoobarName.class);
    foobarNameUnknownAnnotation = AnnotationBuilder.fromClass(elements, FoobarNameUnknown.class);
    // To avoid SpotBugs warning about unused variables.
    if (foobarNameAnnotation == foobarNameUnknownAnnotation) {
      System.out.println("That is surprising!");
    }
  }

  /**
   * {@inheritDoc}
   *
   * <p>This implementation adds the @FoobarName type to String variables with certain names.
   */
  @Override
  public void addComputedTypeAnnotations(Element elt, AnnotatedTypeMirror type) {
    if (DEBUG) {
      printElement(elt, type);
    }

    switch (elt.getKind()) {
      case FIELD:
      case LOCAL_VARIABLE:
      case PARAMETER:
        String varName = elt.getSimpleName().toString();
        if (isFoobarNameVariable(varName, type)) {
          if (!type.hasAnnotation(foobarNameUnknownAnnotation)) {
            type.replaceAnnotation(foobarNameAnnotation);
          }
        }
        break;

      case METHOD:
        String methodName = elt.getSimpleName().toString();
        AnnotatedTypeMirror returnType = ((AnnotatedExecutableType) type).getReturnType();
        if (isFoobarNameMethod(methodName, returnType)) {
          if (!returnType.hasAnnotation(foobarNameUnknownAnnotation)) {
            returnType.replaceAnnotation(foobarNameAnnotation);
          }
        }
        break;

      default:
        break;
    }
    super.addComputedTypeAnnotations(elt, type);
  }

  @Override
  protected TypeAnnotator createTypeAnnotator() {
    return new ListTypeAnnotator(new FoobarNameTypeAnnotator(this), super.createTypeAnnotator());
  }

  /**
   * {@inheritDoc}
   *
   * <p>This implementation adds the @FoobarName type to formal paremeters, because
   * addComputedTypeAnnotations(Element) is not called.
   */
  class FoobarNameTypeAnnotator extends TypeAnnotator {

    FoobarNameTypeAnnotator(AnnotatedTypeFactory typeFactory) {
      super(typeFactory);
    }

    @Override
    public Void visitExecutable(AnnotatedExecutableType type, Void aVoid) {
      Iterator<AnnotatedTypeMirror> paramTypes = type.getParameterTypes().iterator();
      ExecutableElement element = type.getElement();

      for (VariableElement paramElt : element.getParameters()) {
        String paramName = paramElt.getSimpleName().toString();
        AnnotatedTypeMirror paramType = paramTypes.next();
        if (isFoobarNameVariable(paramName, paramType)) {
          if (!paramType.hasAnnotation(foobarNameUnknownAnnotation)) {
            paramType.replaceAnnotation(foobarNameAnnotation);
          }
        }
      }

      String methodName = element.getSimpleName().toString();
      AnnotatedTypeMirror returnType = type.getReturnType();
      if (isFoobarNameMethod(methodName, returnType)) {
        if (!returnType.hasAnnotation(foobarNameUnknownAnnotation)) {
          returnType.replaceAnnotation(foobarNameAnnotation);
        }
      }

      return null;
    }
  }

  /** Print a lot of verbose information about the given element and type. */
  void printElement(Element elt, AnnotatedTypeMirror type) {

    System.out.printf("%n");
    System.out.printf("Element: %s  [%s]%n", elt, elt.getClass());
    Element enclosing = elt.getEnclosingElement();
    System.out.printf(
        "getEnclosingElement(): %s  [%s]  [%s]%n",
        enclosing, enclosing.getClass(), enclosing.getKind());
    System.out.printf("Kind: %s%n", elt.getKind());
    System.out.printf("Type: %s  [%s]%n", type, type.getClass());

    switch (elt.getKind()) {
      case FIELD:
        break;
      case PARAMETER:
        break;

      case CLASS:
      case INTERFACE:
        // Iterable<Symbol> elements = ((Symbol.ClassSymbol) elt).members().getElements();
        // for (Symbol sym : elements) {
        //   System.out.printf("member: %s  [%s]%n", sym, sym.getClass());
        // }
        break;

      default:
        break;
    }

    System.out.printf("%n");
  }

  /**
   * Returns true if the given variable name and type indicates that the variable contains a foobar
   * name.
   */
  private boolean isFoobarNameVariable(String varName, AnnotatedTypeMirror type) {
    return (TypesUtils.isString(type.getUnderlyingType())
        && varName.toLowerCase().contains("foobarname"));
  }

  /**
   * Returns true if the given method name and return type indicates that the method returns a
   * foobar name.
   */
  private boolean isFoobarNameMethod(String methodName, AnnotatedTypeMirror returnType) {
    return (TypesUtils.isString(returnType.getUnderlyingType())
        && methodName.startsWith("get")
        && methodName.endsWith("FoobarName"));
  }
}
