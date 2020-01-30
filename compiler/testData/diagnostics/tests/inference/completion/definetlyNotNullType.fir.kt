// !LANGUAGE: +NewInference
// !DIAGNOSTICS: -UNUSED_PARAMETER

fun <T : Derived?> test(derived: T) {
    <!INAPPLICABLE_CANDIDATE!>id<!><Out<Base>>(
        ")!>makeOut(<!SYNTAX!><!>
            <!UNRESOLVED_REFERENCE!>makeDnn<!>(derived)
        )
    <!SYNTAX!>)<!>
    id<In<Base>>(
        makeIn(
            makeDnn(derived)
        )
    )
    id<Inv<Base>>(
        makeInv(
            makeDnn(derived)
        )
    )
    <!INAPPLICABLE_CANDIDATE!>id<!><In<In<Base>>>(
        <!SYNTAX!><!SYNTAX!><!>><!><!SYNTAX!>")!>makeInIn(<!>
            makeDnn(derived)
        <!SYNTAX!>)<!>
    <!SYNTAX!>)<!>
}

interface Base
open class Derived : Base

class Inv<T>
class Out<out O>
class In<in I>

fun <K> id(arg: K) = arg
fun <T : Any> makeDnn(arg: T?): T = TODO()
fun <T> makeInv(arg: T): Inv<T> = TODO()
fun <T> makeOut(arg: T): Out<T> = TODO()
fun <T> makeIn(arg: T): In<T> = TODO()
fun <T> makeInIn(arg: T): In<In<T>> = TODO()
