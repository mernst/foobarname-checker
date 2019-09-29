import org.checkerframework.checker.foobarname.qual.*;

class LocalVarTest {

  void requiresFoobarName(@FoobarName String arg) {};

  @FoobarNameUnknown
  String producesUnknown() {
    return null;
  }

  void localVar1Unannotated() {
    // :: error: (assignment.type.incompatible)
    String foobarName = producesUnknown();
    requiresFoobarName(foobarName);
  }

  void localVar1Top() {
    @FoobarNameUnknown String foobarName = producesUnknown();
    // :: error: (argument.type.incompatible)
    requiresFoobarName(foobarName);
  }

  void localVar1Bottom() {
    // :: error: (assignment.type.incompatible)
    @FoobarName String foobarName = producesUnknown();
    requiresFoobarName(foobarName);
  }

  void localVar2Unannotated() {
    String foobarName;
    // :: error: (assignment.type.incompatible)
    foobarName = producesUnknown();
    requiresFoobarName(foobarName);
  }

  void localVar2Top() {
    @FoobarNameUnknown String foobarName;
    foobarName = producesUnknown();
    // :: error: (argument.type.incompatible)
    requiresFoobarName(foobarName);
  }

  void localVar2Bottom() {
    @FoobarName String foobarName;
    // :: error: (assignment.type.incompatible)
    foobarName = producesUnknown();
    requiresFoobarName(foobarName);
  }

  void localVar3Unannotated() {
    String s = producesUnknown();
    // :: error: (argument.type.incompatible)
    requiresFoobarName(s);
  }

  void localVar3Top() {
    @FoobarNameUnknown String s = producesUnknown();
    // :: error: (argument.type.incompatible)
    requiresFoobarName(s);
  }

  void localVar3Bottom() {
    // :: error: (assignment.type.incompatible)
    @FoobarName String s = producesUnknown();
    requiresFoobarName(s);
  }
}
