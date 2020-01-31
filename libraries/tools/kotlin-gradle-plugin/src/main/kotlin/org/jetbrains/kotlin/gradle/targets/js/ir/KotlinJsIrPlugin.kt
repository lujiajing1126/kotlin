/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.gradle.targets.js.ir

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaBasePlugin
import org.jetbrains.kotlin.gradle.dsl.KotlinJsIrProjectExtension
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension
import org.jetbrains.kotlin.gradle.plugin.configureDefaultVersionsResolutionStrategy
import org.jetbrains.kotlin.gradle.utils.checkGradleCompatibility

open class KotlinJsIrPlugin(
    private val kotlinPluginVersion: String
) : Plugin<Project> {

    override fun apply(project: Project) {
        // TODO get rid of this plugin, too? Use the 'base' plugin instead?
        // in fact, the attributes schema of the Java base plugin may be required to consume non-MPP Kotlin/JS libs,
        // so investigation is needed
        project.plugins.apply(JavaBasePlugin::class.java)

        checkGradleCompatibility()

        val kotlinExtension = project.kotlinExtension as KotlinJsIrProjectExtension
        configureDefaultVersionsResolutionStrategy(project, kotlinPluginVersion)

        val target = KotlinJsIrSingleTargetPreset(project, kotlinPluginVersion, false).createTarget("js")

        kotlinExtension.target = target

        project.components.addAll(target.components)
    }
}