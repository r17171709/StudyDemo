package davenkin

import java.util.jar.JarEntry
import java.util.jar.JarFile

class RouteMappingCollect {
    private ArrayList<String> tmp = new ArrayList<>()

    ArrayList<String> getAll() {
        return tmp
    }

    // 两种场景下返回的name是不同的

    /**
     * 收集class或者class文件目录中的映射表类
     * @param classFile
     */
    void collect(File classFile) {
        if (classFile.isDirectory()) {
            classFile.listFiles().each {
                collect(it)
            }
        } else {
            if (classFile.name.startsWith("RouteMapping_") && classFile.name.endsWith(".class")) {
                String value = classFile.name.replace(".class", "")
                tmp.add(value)
            }
        }
    }

    /**
     * 收集Jar包中的映射表类
     * @param jarFile
     */
    void collectFromJarFile(File jarFile) {
        Enumeration enumeration = new JarFile(jarFile).entries()
        while (enumeration.hasMoreElements()) {
            JarEntry jarEntry = enumeration.nextElement()
            if (jarEntry.name.contains("RouteMapping_") && jarEntry.name.contains(".class")) {
                // 将包名、后缀名移除
                String value = jarEntry.name.replace("com/renyu/gradledemo/mapping/", "").replace(".class", "")
                tmp.add(value)
            }
        }
    }

}