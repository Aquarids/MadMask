# Kunkka
Sample kotlin project to mock api request which user OKHttp.

### Import

```groovy
//Add it in your root build.gradle at the end of repositories:
maven { url 'https://jitpack.io' }
```
```groovy
//Add the dependency
compile 'com.github.Aquarids:MadMask:0.0.1'
```
[![import](https://jitpack.io/v/Aquarids/Kunkka.svg)](https://jitpack.io/#Aquarids/Kunkka)

### Usage
1. Put your json file at assets/mask/{block_name}/{json_file_name}
2. Add interceptor on your OkHttpBuilder
```kotlin
SimpleMockApi simpleMockApi = new SimpleMockApi(MockApi.GET, "/example_request");
simpleMockApi.setSuccessJsonPath("example.json");
List<SimpleMockApi> mockApis = new ArrayList<>();
mockApis.add(simpleMockApi);
ApiBlock apiBlock = new ApiBlock("exampleBlock", mockApis);
MaskInterceptor maskInterceptor = new MaskInterceptor(App.INSTANCE);
maskInterceptor.addApiBlock(apiBlock);
builder.addInterceptor(maskInterceptor);
```
License
--------

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
       http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
