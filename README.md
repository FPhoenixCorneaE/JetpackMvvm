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

### 二、什么是Jetpack？？？

> Jetpack 是一个由多个库组成的套件，可帮助开发者遵循最佳做法、减少样板代码并编写可在各种 Android 版本和设备中一致运行的代码，让开发者可将精力集中于真正重要的编码工作。

### 三、什么是MVVM？？？

> MVVM(Model-View-ViewModel)是一种软件架构设计模式，它是一种简化用户界面的事件驱动编程方式，本质上就是 MVC 的改进版。

- M： Model (实体模型)

  V： View (对应于Activity和Xml，负责View的绘制以及与用户交互)

  VM： ViewModel (负责Model与View间交互，业务逻辑)

​        ![MVVM架构](https://github.com/FPhoenixCorneaE/JetpackMvvm/blob/main/images/pic_mvvm.png)

- **DataBinding**与**LiveData**/**StateFlow**结合实现数据绑定/双向绑定

​        ![MVVM架构-数据绑定](https://github.com/FPhoenixCorneaE/JetpackMvvm/blob/main/images/pic_mvvm_databinding.png)

[AndroidMVC、MVP、MVVM模式](https://github.com/FPhoenixCorneaE/SomeDevelopmentSkills/blob/master/AndroidMVC%E3%80%81MVP%E3%80%81MVVM%E6%A8%A1%E5%BC%8F.md)

### 四、为什么要使用MVVM？？？

- ##### 低耦合

> 视图（View）可以独立于Model变化和修改，一个ViewModel可以绑定到不同的View上，当View变化的时候Model可以不变，当Model变化的时候，View也可以不变。

- ##### 可复用

> 可以把一些视图逻辑放到一个ViewModel里面，让很多View重用这段视图逻辑。

- ##### 独立开发

> 开发人员可以专注于业务逻辑和数据的开发（ViewModel），设计人员可以专注于页面设计。

- ##### 可单元测试

> 界面素来是比较难于测试的，而现在测试可以针对ViewModel来写。

### 五、特性

1. ##### MVVM框架设计模式基础组件：Application、Activity、Fragment、Dialog、ViewModel、Adapter。

2. ##### Jetpack Data Binding：声明性将布局中的界面组件绑定到应用中的数据源。

3. ##### Jetpack Lifecycle：Activity、Fragment、Dialog、View等生命周期感知。

4. ##### Jetpack Navigation：Fragment、Activity之间导航。

5. ##### Jetpack LiveData：Event、NetworkState。

6. ##### Jetpack Startup：应用启动时简单高效地初始化组件。

7. ##### 多状态布局管理：Empty、Error、Loading、NoNetwork、Content。

### 六、开始使用

1. 自定义Application继承**BaseApplication**。

```kotlin
class WanAndroidApp : BaseApplication() {
}
```

2. Activity继承**BaseActivity<VB>**，实现抽象方法：**VB.initViewBinding()**，可重写BaseView<VB>中的其他方法。

```kotlin
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun ActivityMainBinding.initViewBinding() {
    }
}
```

3. Fragment继承**BaseFragment<VB>**，实现抽象方法：**VB.initViewBinding()**，可重写BaseView<VB>中的其他方法。

```kotlin
class MainFragment : BaseFragment<FragmentMainBinding>() {

    override fun FragmentMainBinding.initViewBinding() {
    }
}
```

4. Dialog继承**BaseDialog<VB>**，实现抽象方法：**VB.initViewBinding()**，可重写BaseView<VB>中的其他方法。

```kotlin
class SplashDialog : BaseDialog<DialogSplashBinding>() {

    override fun DialogSplashBinding.initViewBinding() {
    }
}
```

5. ViewModel继承**BaseViewModel**。<font color="#ff5200">状态（State）用 <b>StateFlow</b> ； 事件（Event）用 <b>SharedFlow</b>。</font>

```kotlin
class SplashViewModel : BaseViewModel() {

    private val _splashResult = MutableStateFlow<SplashBean?>(null)
    val splashResult = _splashResult.asStateFlow()

    private val _splashImg = MutableStateFlow<String?>(null)
    val splashImg = _splashImg.asStateFlow()

    private val _logoVisible = MutableStateFlow(true)
    val logoVisible = _logoVisible.asStateFlow()

    /**
     * 获取每日一图
     */
    fun getDailyImage() {
        requestNoCheck(
            block = {
                apiService.getDailyImage()
            },
            success = {
                _splashResult.value = it
            },
            error = {
                _splashResult.value = null
            }
        )
    }

    fun setSplashImg(data: String?) {
        launch({
            _splashImg.emit(data)
            _logoVisible.emit(data.isNullOrEmpty())
        })
    }
}
```

6. Http网络请求

1. #### live data event：SingleLiveEvent

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

2. #### flow collect with lifecycle

> ```
> Runs the block of code in a coroutine when the lifecycle is at least STARTED.
> The coroutine will be cancelled when the ON_STOP event happens and will
> restart executing if the lifecycle receives the ON_START event again.
> ```

* ```kotlin
  launchRepeatOnLifecycle {
      repeatOnLifecycleFlow.collect {
          "launchRepeatOnLifecycle: $it".logd()
      }
  }
  ```

* ```kotlin
  repeatOnLifecycleFlow.collectWithLifecycle(this@MainActivity) {
      "collectWithLifecycle: $it".logd()
  }
  ```


