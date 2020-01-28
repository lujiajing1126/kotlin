/*
 * Copyright 2010-2020 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.scripting.gradle.importing

import com.intellij.openapi.util.Pair
import org.gradle.api.Project
import org.gradle.tooling.model.kotlin.dsl.KotlinDslModelsParameters
import org.gradle.tooling.model.kotlin.dsl.KotlinDslModelsParameters.*
import org.gradle.tooling.model.kotlin.dsl.KotlinDslScriptsModel
import org.gradle.tooling.provider.model.ToolingModelBuilder
import org.jetbrains.kotlin.idea.scripting.gradle.kotlinDslScriptsModelImportSupported

class KotlinDslScriptModelBuilderService : ToolingModelBuilder {
    override fun canBuild(modelName: String): Boolean =
        modelName == KotlinDslScriptsModel::class.java.name

    override fun buildAll(modelName: String, project: Project): Any? {
        if (kotlinDslScriptsModelImportSupported(project.gradle.gradleVersion)) {
            val startParameter = project.gradle.startParameter

            val tasks = hashSetOf<String>(*startParameter.taskNames.toTypedArray())
            tasks.add(PREPARATION_TASK_NAME)
            startParameter.setTaskNames(tasks)

            val properties = HashMap<String, String>(startParameter.systemPropertiesArgs)
            properties["-D$CORRELATION_ID_GRADLE_PROPERTY_NAME"] = System.nanoTime().toString()
            properties["-D$PROVIDER_MODE_SYSTEM_PROPERTY_NAME"] = STRICT_CLASSPATH_MODE_SYSTEM_PROPERTY_VALUE
            startParameter.systemPropertiesArgs = properties
        }
        return null
    }
}