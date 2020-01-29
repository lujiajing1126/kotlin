/*
 * Copyright 2010-2020 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.daemon.client

import org.jetbrains.kotlin.daemon.common.LoopbackNetworkInterface
import org.jetbrains.kotlin.daemon.common.SOCKET_ANY_FREE_PORT
import org.jetbrains.kotlin.daemon.common.ScriptCompilationConfigurationFacade
import java.rmi.server.UnicastRemoteObject
import kotlin.script.experimental.api.*

class ScriptCompilationConfigurationFacadeServer(
    port: Int = SOCKET_ANY_FREE_PORT
) :
    ScriptCompilationConfigurationFacade,
    UnicastRemoteObject(port, LoopbackNetworkInterface.clientLoopbackSocketFactory, LoopbackNetworkInterface.serverLoopbackSocketFactory)
{

    override fun refineBeforeParsing(context: ScriptConfigurationRefinementContext): ResultWithDiagnostics<ScriptCompilationConfiguration> =
        context.compilationConfiguration.refineAll(ScriptCompilationConfiguration.refineConfigurationBeforeParsing) { config, refineData ->
            refineData.handler.invoke(context)
        }

    override fun refineOnAnnotations(context: ScriptConfigurationRefinementContext): ResultWithDiagnostics<ScriptCompilationConfiguration> =
        context.compilationConfiguration.refineAll(ScriptCompilationConfiguration.refineConfigurationOnAnnotations) { config, refineData ->
            refineData.handler.invoke(context)
        }

    override fun refineBeforeCompiling(context: ScriptConfigurationRefinementContext): ResultWithDiagnostics<ScriptCompilationConfiguration> =
        context.compilationConfiguration
            .refineAll(ScriptCompilationConfiguration.refineConfigurationBeforeCompiling) { config, refineData ->
                refineData.handler.invoke(context)
            }
}