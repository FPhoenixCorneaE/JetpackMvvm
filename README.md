# JetpackMvvm 架构
封装了 Base 组件、EventLiveData、多状态布局管理、Navigation、网络变化监听等等。

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
