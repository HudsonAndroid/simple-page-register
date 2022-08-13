# 轻量级页面注册器

给页面快速注册用于demo展示的注入器。

过往的提供多个demo页面的方式是绑定多个按钮，每个按钮处理点击跳转事件，过程较为繁琐臃肿，例如：

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

## 1. 添加依赖

#### 1.1 给项目添加jitpack repository

```groovy
maven { url 'https://jitpack.io' }
```

#### 1.2 在对应的module中添加`simple-page-register`依赖

```groovy
// simple-page-register
implementation "com.github.HudsonAndroid.simple-page-register:page-annotation:1.0.0" 
kapt "com.github.HudsonAndroid.simple-page-register:page-annotation-processor:1.0.0"
```

### 2. 配置页面

配置你的详情页面，例如：

```kotlin
@PageAnnotation("Yellow Page")
class ColorYellowActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_color_yellow)
    }
}
```

重新build工程，将会产生 `XXRegister`文件，该文件收集了所有配置了PageAnnotation的页面信息：

```java
public class DefaultHostPageRegister {
  public static Map<Class, String> getPages() {
    Map<Class,String> pages = new HashMap<>();
    pages.put(ColorGreenActivity.class, "Green Page");
    pages.put(ColorYellowActivity.class, "Yellow Page");
    return pages;
  }
}
```

所以你可以这样使用：

```kotlin
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        // Register for color group
        DefaultHostPageRegister.getPages().forEach {
            binding.llSampleGroup1.addView(
                Button(this).apply {
                    text = it.value
                    setOnClickListener { _->
                        startActivity(Intent(this@MainActivity, it.key))
                    }
                }
            )
        }
    }
}
```

更多请参考app这个示例。
