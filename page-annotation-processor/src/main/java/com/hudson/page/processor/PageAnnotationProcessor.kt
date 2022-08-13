package com.hudson.page.processor

import com.google.auto.service.AutoService
import com.hudson.page.annotation.PageAnnotation
import com.squareup.javapoet.*
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements
import javax.lang.model.util.Types

@SupportedAnnotationTypes("com.hudson.page.annotation.PageAnnotation")
@AutoService(Processor::class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
class PageAnnotationProcessor: AbstractProcessor() {
    private var elements: Elements? = null
    private var messager: Messager? = null
    private var types: Types? = null
    private var filer: Filer? = null

    // host-List<PageInfo>
    private val pageMaps = mutableMapOf<String, MutableList<PageInfo>>()

    override fun init(processingEnv: ProcessingEnvironment?) {
        super.init(processingEnv)
        elements = processingEnv?.elementUtils
        types = processingEnv?.typeUtils
        messager = processingEnv?.messager
        filer = processingEnv?.filer
    }

    override fun process(
        annotations: MutableSet<out TypeElement>?,
        roundEnv: RoundEnvironment?
    ): Boolean {
        val elements = roundEnv?.getElementsAnnotatedWith(PageAnnotation::class.java) ?: emptySet()
        if(elements.isEmpty()) return false
        for(element in elements){
            if(element is TypeElement){
                // get page clazz
                val pageClazz = ClassName.get(element)
                // get annotation
                val annotation = element.getAnnotation(PageAnnotation::class.java)

                val host = annotation.targetHostPage
                val pageInfo = PageInfo(pageClazz, annotation.pageDesc)
                if(pageMaps[host] == null){
                    pageMaps[host] = mutableListOf(pageInfo)
                }else{
                    pageMaps[host]!!.add(pageInfo)
                }
            }
        }

        processGenFile()
        return true
    }

    /**
     * Target file sample:
     *
     * class PageCenter {
        Map<Class, String> getPages(){
            Map<Class, String> pages = new HashMap<>();
            pages.put(Object.class, "");
            ....
            return pages;
        }
       }
     */
    private fun processGenFile(){
        if(pageMaps.isNotEmpty()){
            pageMaps.keys.forEach {
                val pages = pageMaps[it]
                if(pages?.isNotEmpty() == true){
                    val methodSpec = generateMethodCode(pages)
                    // generate file
                    val finalClassName = "${it}Register"

                    JavaFile.builder(
                        "com.hudson.apt",
                        TypeSpec.classBuilder(finalClassName)
                            .addModifiers(Modifier.PUBLIC)
                            .addMethod(methodSpec)
                            .build())
                        .build()
                        .writeTo(filer)
                }
            }
        }
    }

    private fun generateMethodCode(pages: List<PageInfo>): MethodSpec {
        // return type:  Map<Class, String>
        val methodReturn = ParameterizedTypeName.get(
            ClassName.get(MutableMap::class.java),
            ClassName.get(Class::class.java),
            ClassName.get(String::class.java)
        )
        // method info
        val methodSpecBuilder = MethodSpec.methodBuilder("getPages")
            .addModifiers(Modifier.PUBLIC)
            .addModifiers(Modifier.STATIC)
            .returns(methodReturn)

        // method body

        // Map<Class, String> pages = new HashMap<>();
        methodSpecBuilder.addStatement(
            "\$T<\$T,\$T> \$N = new \$T<>()",
            ClassName.get(MutableMap::class.java),
            ClassName.get(Class::class.java),
            ClassName.get(String::class.java),
            "pages",
            ClassName.get(HashMap::class.java)
        )

        // pages.put(Object.class, "");
        pages.forEach {
            methodSpecBuilder.addStatement(
                "\$N.put(\$T.class, \$S)",
                "pages",
                it.pageClazz,
                it.desc
            )
        }

        // return pages;
        methodSpecBuilder.addStatement("return \$N", "pages")

        return methodSpecBuilder.build()
    }
}