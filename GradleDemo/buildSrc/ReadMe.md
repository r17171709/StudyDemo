## Android Studio 加载自定义Plugin

因为`groovy`插件和`android`插件不兼容，所以不能在原始项目上使用`groovy`。

1. 新建`module`，选择`Java Library`，取名`buildSrc`。**虽然`buildSrc`是Gradle在项目中配置自定义插件的默认目录，但它并不是标准的Android工程目录，所以使用这种方式需要我们事先手动创建**
2. 修改`buildSrc`中的`build.gradle`文件，内容如下
	``` groovy
	plugins {
    	id 'java-library'
    	id 'groovy'
    	id 'maven'
	}

	java {
    	sourceCompatibility = JavaVersion.VERSION_1_8
    	targetCompatibility = JavaVersion.VERSION_1_8
	}

	dependencies {
    	implementation localGroovy()
    	implementation gradleApi()
	}
	```
	添加`groovy`插件以及`gradle`、`groovy`库
	
3. 在`buildSrc/src/main/groovy`目录下创建自定义plugin

4. 由于`buildSrc`目录是gradle默认的目录之一，该目录下的代码会在构建时自动编译打包，并被添加到buildScript中的classpath下，所以不需要任何额外的配置，就可以直接被其他模块的构建脚本所引用

5. 通过简单的id的方式，我们可以隐藏类名等细节，使的引用更加容易。映射方式很简单，在buildSrc目录下创建`resources/META-INF/gradle-plugins/xxx.properties`,这里的xxx也就是所映射的id，这里我们假设取名CustomPluginP
	
	`implementation-class=davenkin.DateAndTimePlugin`
	
	此时就可以通过id来引用对于的插件了
	
	``` groovy
	plugins {
    	id 'CustomPluginP'
	}
	```
	
6. 在独立工程下，需要在一个单独的工程中编写插件，将编译后的jar包上传maven仓库	

## 其他

1. 使用`gradle -q xxx`执行命令。 `-q`代表quiet模式。它不会生成Gradle的日志信息(log messages),所以用户只能看到tasks的输出，使得的输出更加清晰

2. `doFirst`和`doLast`可以被执行许多次. 他们分别可以在任务动作列表的开始和结束加入动作。当任务执行的时候, 在动作列表里的动作将被按顺序执行

3. 使用`gradle tasks`来列出项目的所有任务

4. 使用`gradle properties`命令来列出项目的所有属性

5. 使用`gradle uploadArchives`命令来发布文件

6. 使用命令行选项 -x 来排除某些任务，`gradle dist -x test`运行dist任务但不包含test任务。
   
7. 使用`gradle dependencies`会列出项目的依赖列表，所有依赖会根据任务区分，以树型结构展示出来;
    可以通过`--configuration`参数来查看指定构建任务的依赖情况，例如`gradle -q app:dependencies --configuration implementation`

8. 使用`--profile`参数可以收集一些构建期间的信息并保存到`build/reports/profile`目录下并且以构建时间命名这些文件

## 使用异常

我们可能会遇到

`buildSrc cannot be used as a project name as it is a reserved name`

这个错误，原因是因为我们在`setting.gradle`中配置了`buildSrc`，把`setting.gradle`中配置的
`buildSrc`删掉就OK了

## 参考文章
[Gradle插件的所有使用方式](https://blog.csdn.net/qq_34681580/article/details/106725644)