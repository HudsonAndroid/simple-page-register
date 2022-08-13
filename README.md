# simple-page-register

[中文](README_CN.md)

Simple page reigster for android demo page's display.

Maybe your old code like this:

<img src="images/old_style.png" title="" alt="old_style.png" data-align="center">

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

If you want to register the page quickly and show your demo content, `simple-page-register` is the most appropriate.

<img src="images/new_style.png" title="" alt="new_style.png" data-align="center">

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

## Usage

### 1. Add dependencies

#### 1.1 Add the JitPack repository to your build file（Higher Version on settings.gradle）

```groovy
maven { url 'https://jitpack.io' }
```

#### 1.2 Add the dependency on target module

```groovy
// simple-page-register
implementation "com.github.HudsonAndroid.simple-page-register:page-annotation:1.0.0" 
kapt "com.github.HudsonAndroid.simple-page-register:page-annotation-processor:1.0.0"
```

### 2. Config pages

Config your detail page like this:

```kotlin
@PageAnnotation("Yellow Page")
class ColorYellowActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_color_yellow)
    }
}
```

After you rebuild project, the pages will be collected in `XXRegister`:

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

So you might use it this way:

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

More sample, see app example.
