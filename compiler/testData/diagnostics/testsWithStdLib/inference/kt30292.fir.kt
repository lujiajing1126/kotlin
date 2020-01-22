// !LANGUAGE: +NewInference

fun test(ls: List<String>) {
    ls.<!INAPPLICABLE_CANDIDATE!>takeIf<!>(<!UNRESOLVED_REFERENCE!>Collection<*>::isNotEmpty<!>)
}