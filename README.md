# JetpackMvvm 架构
封装了 Base 组件、LiveDataEvent、多状态布局管理、Navigation、网络变化监听等等。

[![](https://jitpack.io/v/FPhoenixCorneaE/JetpackMvvm.svg)](https://jitpack.io/#FPhoenixCorneaE/JetpackMvvm)

### How to include it in your project:
**Step 1.** Add the JitPack repository to your build file

**groovy**
```groovy
allprojects {
	repositories {
        google()
        mavenCentral()
		maven { url 'https://jitpack.io' }
	}
}
```
**kotlin**
```kotlin
allprojects {
	repositories {
        google()
        mavenCentral()
		maven { setUrl("https://jitpack.io") }
	}
}
```

**Step 2.** Add the dependency

```kotlin
dependencies {
	implementation("com.github.FPhoenixCorneaE:JetpackMvvm:1.0.5")
}
```

### 多状态布局管理：StatusLayoutManager
```kotlin
    override fun initUiState() {
        mStatusLayoutManager = StatusLayoutManager.Builder()
            .addNoNetWorkClickListener {
                onNoNetWorkClick()
            }
            .addErrorClickListener {
                onErrorClick()
            }
            .addEmptyClickListener {
                onEmptyClick()
            }
            .register(viewBinding!!.root)
    }
    
    override fun showLoading(loadingMsg: String?) {
        mStatusLayoutManager?.showLoadingView(loadingMsg)
    }

    override fun showContent() {
        mStatusLayoutManager?.showContentView()
    }

    override fun showEmpty(emptyMsg: String?) {
        mStatusLayoutManager?.showEmptyView(emptyMsg)
    }

    override fun showNoNetwork(noNetworkMsg: String?) {
        mStatusLayoutManager?.showNoNetWorkView(noNetworkMsg)
    }

    override fun showError(errorMsg: String?) {
        mStatusLayoutManager?.showErrorView(errorMsg)
    }
```

### LiveDataEvent：MutableLiveData<Event<T>>()
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
