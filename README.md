# AppJointDemo
基于AppJoint的组件化demo，接入了Bugly的Tinker。<br>
#### 【组件间的独立调试】：
组件的独立运行方式目前主要有三种方式，<br>
- (1) 通过gradle插件配置的一些骚操作，配置成功的情况下是挺方便的，但是对gradle版本依赖较高，不利于gradle版本的升级，因为开发环境的不一样，比如开发工具版本，gradle版本之类的，一旦产生问题很可能是无解的，不推荐<br>
- (2) 在properties中定义一个变量，通过改变变量来区分Lib和Application，需要编写两套manifest，在gradle中会有很多if-else判断的代码，不利于维护，同时像ButterKnife在Lib使用R2和Application中使用R1，使用方式不一样的开源库就没法用了，不推荐<br>
- (3) 给每个model配置一个modelRun的app。模块会比较多，但是我们可以单独放到一个standarRun文件夹下，只在setting.gradle文件中声明，不在gradle中引用，做到生产代码和测试代码隔离，gradle中也没有if-else判断，同时在从独立app切换为单独运行组件的时候也不需要Gradle Sync，目前比较推荐的方案<br>
#### 【组件间通信】：
AppJoint提供了组件间通信的方案，抽取公共接口，在具体的实现模块实现接口时添加@ServiceProvider注解，AppJoint会在编译期解析注解并把相应的实现类以接口的Class为key值存入Map集合，我们就可以通过AppJoint.service获取具体的实现类

#### 【组件间跳转】：
采用的是接口的形式，我们的项目早期每个人在编写自己的模块代码的都是基本都对外提供了入口方法。没采用ARouter，ARouter不支持高版本的gradle，4.6版本的gradle可以跳转，但是改为5.6.4版本(U品目前使用的版本)会出现路径找不到的bug，并且使用ARouter容易出现接口提供方修改了参入导致两边联调的失败问题，而使用接口能把这种这种问题由运行时提前到编译器

#### 【统一版本管理】：
抽取了subprojects.gradle，在里面进行了android工程和android lib的统一公共配置

#### 【资源文件命名冲突】：
模块的gradle中配置resourcePrefix统一规范资源名称前缀，防止多个 module 之间资源冲突
