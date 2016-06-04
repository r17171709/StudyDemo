package com.example.annotation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * Created by RG on 2016/6/2.
 */
@SupportedAnnotationTypes("com.example.annotation.ViewInject")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class ViewInjectProcessor extends AbstractProcessor {

    Elements elementUtils;

    //存储需要循环生成的注解类信息
    HashMap<String, ProxyInfo> proxyInfoHashMap;

    int num=0;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        proxyInfoHashMap=new HashMap<>();

        elementUtils=processingEnv.getElementUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        String fqClassName, className, packageName;
        //遍历所有ViewInject注解下的节点
        for (Element element : roundEnv.getElementsAnnotatedWith(ViewInject.class)) {
            //注: element=com.rg.annotationdemo.MainActivity
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "element="+element);
            //如果是类上面的注解，这里应该是执行setContetView()
            if (element.getKind()== ElementKind.CLASS) {
                //类元素
                TypeElement classElement= (TypeElement) element;
                //包元素
                PackageElement packageElement= (PackageElement) classElement.getEnclosingElement();
                fqClassName=classElement.getQualifiedName().toString();
                className=classElement.getSimpleName().toString();
                packageName=packageElement.getQualifiedName().toString();
                int layoutId=classElement.getAnnotation(ViewInject.class).value();
                //注: fqClassName=com.rg.annotationdemo.MainActivity  className=MainActivity  packageName=com.rg.annotationdemo
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "fqClassName="+fqClassName+"  className="+className+"  packageName="+packageName);
                if (proxyInfoHashMap.containsKey(fqClassName)) {
                    proxyInfoHashMap.get(fqClassName).setLayoutId(layoutId);
                }
                else {
                    ProxyInfo proxyInfo=new ProxyInfo(packageName, className);
                    proxyInfo.setClassElement(classElement);
                    proxyInfo.setLayoutId(layoutId);
                    proxyInfoHashMap.put(fqClassName, proxyInfo);
                }
            }
        }
        if (num!=0) {
            return true;
        }
        num++;
        Iterator iterator=proxyInfoHashMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry= (Map.Entry) iterator.next();
            ProxyInfo value= (ProxyInfo) entry.getValue();
            try {
                JavaFileObject object=processingEnv.getFiler().createSourceFile(value.getProxyClassName(), value.getClassElement());
                Writer writer=object.openWriter();
                writer.write(value.generateJavaCode());
                writeLog(value.generateJavaCode());
                writer.flush();
                writer.close();
            } catch (IOException e) {

            }
        }
        return true;
    }

    private void writeLog(String str) {
        try {
            FileWriter fw = new FileWriter(new File("E:/process2.txt"), true);
            fw.write(str + "\n");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
