# JetpackMVVM 
> JetpackMVVM 是为了提高项目开发效率、减少开发时间的同时，降低耦合，提高可复用性，并使开发人员可以专注于业务逻辑和数据的开发这样一个 Android 项目基础框架组件库。



[![](https://jitpack.io/v/FPhoenixCorneaE/JetpackMvvm.svg)](https://jitpack.io/#FPhoenixCorneaE/JetpackMvvm)

### 一、How to include it in your project?
**Step 1.** Add the JitPack repository to your build file

```groovy
allprojects {
	repositories {
        google()
        mavenCentral()
		maven { url 'https://jitpack.io' }
	}
}
```
**Step 2.** Add the dependency

```groovy
dependencies {
	implementation("com.github.FPhoenixCorneaE:JetpackMvvm:$latest")
}
```



### 二、什么是MVVM？？？

> MVVM(Model-View-ViewModel)是一种软件架构设计模式，它是一种简化用户界面的事件驱动编程方式。

- M：    Model (实体模型) 

  V：     View (对应于Activity和Xml，负责View的绘制以及与用户交互) 

  VM： ViewModel (负责Model与View间交互，业务逻辑)

​		![MVVM架构](https://github.com/FPhoenixCorneaE/JetpackMvvm/blob/main/images/pic_mvvm.png)

- **DataBinding**与**LiveData**/**StateFlow**结合实现数据绑定/双向绑定

​		![MVVM架构-数据绑定](https://github.com/FPhoenixCorneaE/JetpackMvvm/blob/main/images/pic_mvvm_databinding.png)



### 三、为什么要使用MVVM？？？

- ##### 低耦合

> 视图（View）可以独立于Model变化和修改，一个ViewModel可以绑定到不同的View上，当View变化的时候Model可以不变，当Model变化的时候，View也可以不变。

- ##### 可复用

> 可以把一些视图逻辑放到一个ViewModel里面，让很多View重用这段视图逻辑。

- ##### 独立开发

> 开发人员可以专注于业务逻辑和数据的开发（ViewModel），设计人员可以专注于页面设计。

- ##### 可单元测试

> 界面素来是比较难于测试的，而现在测试可以针对ViewModel来写。



### 四、特性

1. ##### MVVM框架设计模式基础组件

2. ##### Data Binding 自定义属性

3. ##### 生命周期回调监听

4. ##### Navigation导航

5. ##### 多状态布局管理

6. ##### 网络连接状态LiveData

7. ##### LiveDataEvent



### 五、开始使用

1. #### LiveDataEvent：MutableLiveData<Event<T>>()

```kotlin
// 显示加载框
val showDialog by lazy { MutableLiveData<Event<String>>() }
```
```kotlin
// 显示弹窗
viewModel.loadingChange.showDialog.observe(this, EventObserver {
    showLoading(it)
})
```
