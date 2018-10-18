/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lz.rxrequestlib.http;


public interface ConfigModule {
    /**
     * 使用{@link GlobalConfigBuild.Builder}给框架配置一些配置参数
     *
     * @param builder
     */
    void applyOptions(GlobalConfigBuild.Builder builder);

}
