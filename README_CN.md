# 轻量级页面注册器

给页面快速注册用于demo展示的注入器。

过往的提供多个demo页面的方式是往绑定多个按钮，每个按钮处理点击跳转事件，过程较为繁琐臃肿，例如：

<img src="images/old_style.png" title="" alt="old" data-align="center">

```kotlin
button1.setOnClickListener {
    startActivity(Intent(this@MainActivity, Page1::class.java))
}
button2.setOnClickListener {
    startActivity(Intent(this@MainActivity, Page2::class.java))
}
button3.setOnClickListener {
    startActivity(Intent(this@MainActivity, Page3::class.java))
}
button4.setOnClickListener {
    startActivity(Intent(this@MainActivity, Page4::class.java))
}
//....
```

如果你希望快速完成页面注册，而不是处理这些繁琐的事情，simple-page-register将会帮助你快速完成。

<img src="images/new_style.png" title="" alt="new" data-align="center">

```kotlin
// Register your pages by loop, simple-page-register will help you collect the page 
DefaultHostPageRegister.getPages().forEach {
    binding.llSampleGroup1.addView(
        Button(this).apply {
            text = it.value // it.value is page description
            setOnClickListener { _->
                startActivity(Intent(this@MainActivity, it.key)) // it.key is page Class information
            }
        }
    )
}
```

## 用法

### 1. 给项目添加jitpack repository

```groovy
maven { url 'https://jitpack.io' }
```

### 2. 在对应的module中添加`simple-page-register`依赖

```groovy
// simple-page-register
implementation "com.github.HudsonAndroid.simple-page-register:page-annotation:1.0.0" 
kapt "com.github.HudsonAndroid.simple-page-register:page-annotation-processor:1.0.0"
```

更多请参考app这个示例。
