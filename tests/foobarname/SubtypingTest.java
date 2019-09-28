import org.checkerframework.checker.foobarname.qual.*;

// Test basic subtyping relationships for the Foobar Name Checker.
class SubtypeTest {
  void allSubtypingRelationships(@FoobarNameUnknown int x, @FoobarName int y) {
    @FoobarNameUnknown int a = x;
    @FoobarNameUnknown int b = y;
    // :: error: assignment.type.incompatible
    @FoobarName int c = x; // expected error on this line
    @FoobarName int d = y;
  }
}
