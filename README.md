# JetpackMvvm 架构
封装了 Base 组件、LiveDataEvent、多状态布局管理、Navigation、网络变化监听等等。


### How to include it in your project:
**Step 1.** Add the JitPack repository to your build file

**groovy**
```groovy
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
**kotlin**
```kotlin
allprojects {
	repositories {
		...
		maven { setUrl("https://jitpack.io") }
	}
}
```

**Step 2.** Add the dependency

**groovy**
```groovy
dependencies {
	implementation("com.github.FPhoenixCorneaE:JetpackMvvm:1.0.0")
}
```
**kotlin**
```kotlin
dependencies {
	implementation("com.github.FPhoenixCorneaE:JetpackMvvm:1.0.0")
}
```

### 多状态布局管理：MultiStatusLayoutManager
```kotlin
    override fun createMultiStatusLayoutManager() {
        mMultiStatusLayoutManager = MultiStatusLayoutManager.Builder()
            .addNoNetWorkClickListener(View.OnClickListener {
                onNoNetWorkClick()
            })
            .addErrorClickListener(View.OnClickListener {
                onErrorClick()
            })
            .addEmptyClickListener(View.OnClickListener {
                onEmptyClick()
            })
            .register(this)
    }
    
    override fun showLoading() {
        mMultiStatusLayoutManager?.showLoadingView()
    }

    override fun showContent() {
        mMultiStatusLayoutManager?.showContentView()
    }

    override fun showEmpty() {
        mMultiStatusLayoutManager?.showEmptyView()
    }

    override fun showNoNetwork() {
        mMultiStatusLayoutManager?.showNoNetWorkView()
    }

    override fun showError() {
        mMultiStatusLayoutManager?.showErrorView()
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
