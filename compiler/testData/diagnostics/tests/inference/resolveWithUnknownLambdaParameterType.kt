// !WITH_NEW_INFERENCE
// !DIAGNOSTICS: -UNUSED_PARAMETER -UNUSED_ANONYMOUS_PARAMETER

fun baz(f: (Int) -> String) {}

object Foo {
    fun baz(vararg anys: Any?) {}

    fun testResolvedToMember() {
        baz({ <!OI;CANNOT_INFER_PARAMETER_TYPE!>x<!> -> "" }) // should be an error
    }
}
