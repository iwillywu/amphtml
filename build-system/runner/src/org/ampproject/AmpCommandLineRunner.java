/**
 * Copyright 2016 The AMP HTML Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS-IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ampproject;


import com.google.common.collect.ImmutableSet;
import com.google.javascript.jscomp.AbstractCommandLineRunner.FlagUsageException;
import com.google.javascript.jscomp.CommandLineRunner;
import com.google.javascript.jscomp.CompilerOptions;
import com.google.javascript.jscomp.CustomPassExecutionTime;
import com.google.javascript.jscomp.PropertyRenamingPolicy;
import com.google.javascript.jscomp.VariableRenamingPolicy;

import java.io.IOException;


/**
 * Adds a custom pass for Tree shaking `dev.fine` and `dev.assert` calls.
 */
public class AmpCommandLineRunner extends CommandLineRunner {

  /**
   * List of string suffixes to eliminate from the AST.
   */
  ImmutableSet<String> suffixTypes = ImmutableSet.of(
      "dev.fine");

  protected AmpCommandLineRunner(String[] args) {
    super(args);
  }

  @Override protected CompilerOptions createOptions() {
    CompilerOptions options = super.createOptions();
    options.setCollapseProperties(true);
    AmpPass ampPass = new AmpPass(getCompiler(), suffixTypes);
    options.addCustomPass(CustomPassExecutionTime.BEFORE_OPTIMIZATIONS, ampPass);
    options.setDevirtualizePrototypeMethods(true);
    options.setExtractPrototypeMemberDeclarations(true);
    options.setSmartNameRemoval(true);
    options.optimizeParameters = true;
    options.optimizeReturns = true;
    options.optimizeCalls = true;
    // Have to turn this off because we cannot know whether sub classes
    // might override a method. In the future this might be doable
    // with using a more complete extern file instead.
    options.setRemoveUnusedPrototypeProperties(false);
    options.setInlineProperties(false);
    options.setComputeFunctionSideEffects(false);
    // Waiting for upstream change to stop renaming exported properties.
    // options.setRenamingPolicy(VariableRenamingPolicy.ALL,
    //    PropertyRenamingPolicy.ALL_UNQUOTED);
    // options.setDisambiguatePrivateProperties(true);
    return options;
  }

  @Override protected void setRunOptions(CompilerOptions options)
      throws IOException, FlagUsageException {
    super.setRunOptions(options);
    options.setCodingConvention(new AmpCodingConvention());
  }

  public static void main(String[] args) {
    AmpCommandLineRunner runner = new AmpCommandLineRunner(args);
    if (runner.shouldRunCompiler()) {
      runner.run();
    }
    if (runner.hasErrors()) {
      System.exit(-1);
    }
  }
}
