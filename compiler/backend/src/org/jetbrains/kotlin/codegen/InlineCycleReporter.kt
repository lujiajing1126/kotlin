/*
 * Copyright 2010-2015 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.kotlin.codegen

import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.codegen.inline.InlineCall
import org.jetbrains.kotlin.descriptors.CallableDescriptor
import org.jetbrains.kotlin.diagnostics.DiagnosticSink
import org.jetbrains.kotlin.diagnostics.Errors

class InlineCycleReporter(private val diagnostics: DiagnosticSink) {

    private val processingFunctions = linkedMapOf<PsiElement, CallableDescriptor>()

    fun enterIntoInlining(call: InlineCall?): Boolean {
        // null call for default method inlining
        if (call != null) {
            val callElement = call.callElement
            if (processingFunctions.contains(callElement)) {
                val cycle = processingFunctions.asSequence().dropWhile { it.key != callElement }
                for ((cycleElement, cycleCallee) in cycle) {
                    diagnostics.report(Errors.INLINE_CALL_CYCLE.on(cycleElement, cycleCallee))
                }
                return false
            }
            processingFunctions[callElement] = call.calleeDescriptor
        }
        return true
    }

    fun exitFromInliningOf(call: InlineCall?) {
        if (call != null) {
            val callElement = call.callElement
            processingFunctions.remove(callElement)
        }
    }
}
