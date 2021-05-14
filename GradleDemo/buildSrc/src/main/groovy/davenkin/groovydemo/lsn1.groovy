package davenkin.groovydemo

class LSN1 {
    // 变量定义
    int x = 10
    double y = 3.14

    def x1 = 10
    def y1 = 3.14
    def s1 = "Hello Groovy"
    def s2 = "BBB"
    def s3 = "Hello"

    def print1() {
        // 可以随意修改def定义的变量类型
        s1 = 12
        return s1
    }

    def string1() {
        // 字符串从中间填充
        def strNew = s1.center(18, "a")
        // 字符串从左边填充
        strNew = s1.padLeft(18, "a")

        // 字符串比较
//        def tmp = s1 > (s2)

        // 根据下标获取字符串中若干字符内容
//        def tmp = s1[0]
//        def tmp = s1[0..2]

        // 移除字符串中相同的部分
//        def tmp = s1.minus(s3)

        // 添加字符串中相同的部分
//        def tmp = s1 + s2

        // 倒序
        def tmp = s1.reverse()

        return tmp
    }

    def switchcase() {
        // 可以直接判断类型、数值等
        def x = "str"
        def result = ""
        switch (x) {
            case Integer:
                result = "Integer $x"
                break
            case "1":
                result = "1"
                break
            case [1, 2, "str"]:
                result = "list"
                break
            case 10..30:
                result = "range"
                break
            case BigDecimal:
                result = x
                break
        }
        return result
    }

    def fordemo() {
//        for (i in 0..9) {
//            println("$i")
//        }

        // 对list进行循环
//        for (i in [1, 2, 3, 4, 5, 6]) {
//            println("list $i")
//        }

        // 对map进行循环
        for (kv in ["a": 1, "b": 2]) {
            println("${kv.key} ${kv.value}")
        }

        return ""
    }

    def clouserDemo() {
        def clouser1 = {
            println("Hello Clouser")
        }

        // 闭包调动方式
//        clouser1.call()
//        clouser1()

        def clouser2 = { name ->
            println("Hello Clouser $name")
        }
//        clouser2.call("PQ")
//        clouser2("PQ")

        def clouser3 = { name, age ->
            println("Hello Clouser $name $age")
        }
//        clouser3.call("PQ", 1)
//        clouser3("PQ", 2)

        def clouser4 = {
            println("Hello Clouser $it")
        }
        println(clouser4.call("PQ"))

        // 字符串和闭包的结合使用
//        s3.each {
//
//        }

//        s3.find {
//            it == "l"
//        }.each {
//            println it
//        }

//        s3.findAll {
//            it == "l"
//        }.each {
//            println it
//        }

//        def anyTmp = s3.any {
//            it == "l"
//        }
//        println anyTmp

//        def everyTmp = s3.every {
//            it == "l"
//        }
//        println everyTmp

        def collectTmp = s3.collect {
            it.toUpperCase()
        }.toListString()

        return ""
    }

    def clouserExtraDemo() {
//        def clouser = {
//            // 代表闭包定义的类
//            println("this:$this")
//            // 代表闭包定义处的类或者对象
//            println("owner:$owner")
//            // 代表任意对象，默认与owner一致
//            println("delegate:$delegate")
//        }
//        clouser.call()

        A a = new A()

        ClouserInner clouserInner = new ClouserInner()
        clouserInner.clouser1.call()
//        clouserInner.clouserExtra2Demo()
//        clouserInner.clouser5()

        // 修改委托
//        ClouserInner clouserInner = new ClouserInner()
//        clouserInner.clouser1.delegate = a
//        clouserInner.clouser1.resolveStrategy = Closure.DELEGATE_FIRST
//        clouserInner.clouser1.call()
    }

    def listDemo() {
        def list = [1, 2, 3, 4, 5]
        list.size()
        def array = [1, 2, 3, 4, 5] as int[]
        int[] array2 = [1, 2, 3, 4, 5]

        Comparator comparator = { a, b ->
            a == b ? 0 : Math.abs(a) > Math.abs(b) ? -1 : 1
        }
        list.sort(comparator)
        println list

        list.add(6)
        list << 7

        list.removeAt(0)
        list.removeAll {

        }

        def stringlist = ["abc", "z", "Hello", "groovy", "Java"]
        stringlist.sort { a, b ->
            a.size() == b.size() ? 0 : a.size() > b.size() ? 1 : -1
        }
        println stringlist

        // 列表调用闭包
        def findTmp = stringlist.findAll {
            it.size() % 2 != 0
        }
        println(findTmp)

        // 找出最大值最小值
        println(list.min())
        println(list.max())

        return null
    }

    def mapDemo() {
        def map1 = ["key1": "value1", "key2": "value2"]

        map1["key"]
        map1.key1

        map1."key3" = "value3"

        println(map1)

        map1.each {

        }

        map1.each { k, v ->

        }

        map1.eachWithIndex { Map.Entry<String, String> entry, int i ->

        }

        // 分组
        def map2 = map1.groupBy {
            it.key.contains("key1")
        }
        println(map2)

        return null
    }

    def rangeDemo() {
        def range = 0..10
        range[0]
        range.contains(8)
        range.from
        range.to
        range.each {

        }
    }

    def objectDemo() {
        // 3

        // 为类动态添加一个属性
        Person.metaClass.sex = "male"

        Person person = new Person(name: "PQ", age: 32)

        person.sex = "female"
        println(person.sex)

        // 为类动态添加一个方法
        Person.metaClass.cry = { ->
            println("cry")
        }
        person.cry()

        // 为类动态添加一个静态方法
        Person.metaClass.static.createPerson = {
            String name, int age ->
                new Person(name: name, age: age)
        }

        Person person1 = Person.createPerson("PQ", 30)
        println(person1.age)

        // 全局可以使用添加后的内容
        ExpandoMetaClass.enableGlobally()

        return ""
    }
}

class ClouserInner {
    String name = "ClouserInner"

    def clouser1 = {
        // 代表闭包定义的类
        println("this1:$name " + this)
        // 代表闭包定义处的类或者对象
        println("owner1:$name " + owner)
        // 代表任意对象，默认与owner一致
        println("delegate1:$name " + delegate)
    }

    def clouserExtra2Demo() {
        def clouser2 = {
            // 代表闭包定义的类
            println("this2:$this")
            // 代表闭包定义处的类或者对象
            println("owner2:$owner")
            // 代表任意对象，默认与owner一致
            println("delegate2:$delegate")
        }
        clouser2.call()
    }

    def clouser3 = {
        def clouser4 = {
            // 代表闭包定义的类
            println("this4:" + this)
            // 代表闭包定义处的类或者对象
            println("owner4:" + owner)
            // 代表任意对象，默认与owner一致
            println("delegate4:" + delegate)
        }
        clouser4.call()
    }

    def clouser5 = {
        def clouser6 = {
            // 代表闭包定义的类
            println("this6:" + this)
            // 代表闭包定义处的类或者对象
            println("owner6:" + owner)
            // 代表任意对象，默认与owner一致
            println("delegate6:" + delegate)
        }
        clouser6.call()
    }
}

class A {
    String name = "A"
}

class Person {
    String name = ""
    int age = 0

    // 3
//    def invokeMethod(String name, Object args) {
//        return "the method is $name, this params is $args"
//    }

    // 2
    def methodMissing(String name, Object args) {
        return "the method is $name, this params is $args"
    }
}

// 与接口做比较
trait ActionTrait {
    void up() {
        println("Up")
    }

    abstract void down()
}

interface ActionInterface {
    void up()

    void down()
}

class Action implements ActionTrait {
    @Override
    void down() {

    }

    def annotherUp() {
        up()
    }
}

class Action2 implements ActionInterface {
    @Override
    void up() {

    }

    @Override
    void down() {

    }
}