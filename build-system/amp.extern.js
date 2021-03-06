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

// Node.js global
var process = {};
process.env;

// Exposed to ads.
window.context = {};

// Exposed to custom ad iframes.
window.draw3p = function() {};

// AMP's globals
window.AMP_TEST;
window.AMP_TAG;
window.AMP_CONFIG;
window.AMP = {};

// Externed explicitly because we do not export Class shaped names
// by default.
window.AMP.BaseElement;
window.AMP.BaseTemplate;

// Externed explicitly because this private property is read across
// binaries.
Element.implementation_ = {};
