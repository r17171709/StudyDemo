void main() {
  ClassTest classTest = new ClassTest();

  Caculator caculator = Caculator.FromAnother(1, 2);

  // 工厂构造函数适合实现单例效果
  Shape shapeType1 = new Shape.Type("circle");
  Shape shapeType2 = new Shape.Type("circle");
  // hashcode相同
  print(shapeType1.hashCode);
  print(shapeType2.hashCode);

  // 级联符号.. 允许您在同一个对象上进行一系列操作。 除了函数调用之外，还可以访问同一对象上的字段。其实相当于java的链式调用
  CircleShape shape = new CircleShape()
    ..radius = 3
    ..color = 1;

  Asia asia = new Asia("China");
  asia.name;

  EventBus eventBus = new EventBus();

  SeniorExecutive seniorExecutive = new SeniorExecutive();
  seniorExecutive.showValue();

  LegalPerson legalPerson = new LegalPerson();
  legalPerson.showValue();

  NaturalPerson2 naturalPerson2 = new NaturalPerson2();
  naturalPerson2.showValue();
}

class ClassTest {
  // 如果没有声明构造函数，那么初始化的时候使用的是默认构造函数
  // Object类是所有类的父类
  // 在Dart语言中，子类构造函数必须继承父类的构造函数
  // 若调用的是默认构造函数，则无需显式声明继承关系
  ClassTest() : super() {}
}

class ClassParentTest {
  ClassParentTest(String value) {}

  ClassParentTest.FromString(String value) {}
}

class ClassChildTest extends ClassParentTest {
  // 子类可以自由选择继承父类的哪个构造函数，只需要在自身构造函数后加:号，在:后面指定父类的构造函数
  ClassChildTest(String value) : super.FromString(value) {}

  ClassChildTest.FromString(String value) : this(value);
}

class Caculator {
  // 类中未初始化的实例变量的默认值都为null
  int x;
  int y;

  void set setX(int x) {
    this.x = x;
  }

  int get getX {
    return x;
  }

  // 写法与Java如出一辙
//  Caculator(int x, int y) {
//    this.x = x;
//    this.y = y;
//  }

  // 来一个语法糖
//  Caculator(this.x, this.y) {}

  // 在构造函数体运行之前初始化实例变量
  Caculator(int x, int y)
      : this.x = x,
        this.y = y {}

  // 命名构造函数
  // 因为Dart没法重载构造函数，所以提供了命名构造函数来解决这个问题
  Caculator.FromAnother(this.x, this.y) {}

  int add() {
    return x + y;
  }

  int subract() {
    return x - y;
  }
}

class SubCaculator extends Caculator {
  SubCaculator(int x, int y) : super(x, y) {}

//  SubCaculator.FromAnother(int x, int y) : super.FromAnother(x, y);

  // 重定向到同类的另一个构造函数
  SubCaculator.FromAnother(int x, int y) : this(x, y);
}

class Shape {
  String desp;

  static final Map<String, Shape> _cache = new Map();

  // 工厂构造函数
  factory Shape.Type(String type) {
    if (_cache.containsKey(type)) {
      return _cache[type];
    }
    if (type == "circle") {
      Shape shape = new Shape.Circle("this is a circle");
      _cache[type] = shape;
      return shape;
    } else if (type == "square") {
      Shape shape = new Shape.Square("this is a square");
      _cache[type] = shape;
      return shape;
    } else {
      Shape shape = new Shape.Unknown("this is an unknown shape");
      _cache[type] = shape;
      return shape;
    }
  }

  Shape.Circle(this.desp) {}

  Shape.Square(this.desp) {}

  Shape.Unknown(this.desp) {}
}

// 静态类
class StaticClass {
  // 静态变量
  static String staticValue = "";

  const StaticClass();

  // 想让类生成的对象永远不会改变，可以让这些对象变成编译时常量，定义一个const构造函数并确保所有实例变量是final的
  static final StaticClass value = new StaticClass();

  // 静态函数
  static void staticFunction() {}
}

class CircleShape {
  int radius = 0;
  int color = 0;
  final int size;
  final int price;

  CircleShape()
      : size = 3,
        price = 1; // 通过此种方式对final值进行初始化
}

class Club {
  String clubName;
  int _year;
  int color;

  // getter和setter是特殊的函数，可以读写访问对象的属性，每个实例变量都有一个隐式的getter，适当的加上一个setter，可以通过实现getter和setter创建附加属性
  void set year(int year) {
    this._year = year;
  }

  int get year {
    return this._year;
  }

  void cFunction() {}
}

class Sponsor {
  String sponsorName;
  int _year; // 如果相同变量或者函数名类型不一致，不可以mixin

  void set year(int year) {
    this._year = year;
  }

  int get year {
    return this._year;
  }

  void sFunction() {}
}

// 扩展类(mixins)
// mixins的中文意思是混入，就是在类中混入其他功能。它是一种在多个类层次结构中重用类代码的方法。mixins要重用的代码，不是方法或者是接口，而是类！
// mixins要用到的关键字with，with关键字后面跟着一个或多个扩展类名
// 这里，应该这样描述Person类：类Club想使用类Sponsor的sFunction()方法，那么这时候就需要用到mixins，而类Sponsor就是mixins类(混入类)，类Club就是要被mixins的类。最后Person继承这个Club与Sponsor mixins后的类(Club with Sponsor)
class Person extends Club with Sponsor {
  void a() {
    color;
    clubName;
    cFunction();
    sFunction();
  }
}
// 一个类可以mixins多个mixins类

class Company1 {
  void name() {
    print("Company1");
  }
}
class Company2 {
  void name() {
    print("Company2");
  }
}
// Company1与Company2同时有相同名称的函数类型，那么在这种情况下应该选择哪一个呢
class SeniorExecutive with Company1, Company2 {
  void showValue() {
    // 打印出来的是Company2，相当于(SeniorExecutive with Company1) with Company2
    name();
  }
}

class NaturalPerson {
  void name() {
    print("NaturalPerson");
  }
}
// 如果继承的NaturalPerson与mixins的Company1同时有相同名称的函数类型，那么在这种情况下应该选择哪一个呢
class LegalPerson extends NaturalPerson with Company1 {
  void showValue() {
    // 打印出来的是Company1，相当于LegalPerson extends (NaturalPerson with Company1)
    name();
  }
}
// 如果当前类NaturalPerson2与mixins的Company1、Company2同时有相同名称的函数类型，那么在这种情况下应该选择哪一个呢
class NaturalPerson2 with Company1, Company2 {
  void name() {
    print("NaturalPerson");
  }
  void showValue() {
    // 打印出来的是NaturalPerson，相当于LegalPerson extends (NaturalPerson with Company1)
    name();
  }
}

// 抽象类
abstract class Continent {
  String name = "Continent";

  Continent(String name) {
    this.name = name;
  }

  // 抽象函数
  String getContinentName();

  void desp() {
    print("This is Continent ${getContinentName()}");
  }
}

class Asia extends Continent {
  Asia(String name) : super(name) {}

  @override
  String get name => super.name;

  @override
  String getContinentName() {
    return "Asia";
  }

  @override
  void desp() {
    print("Hello");
    super.desp();
  }
}

// 隐式接口
// 每个类都有一个隐式定义的接口，包含所有类和实例成员。Java里面接口就是接口，与Dart不同
class Europe implements Continent {
  @override
  String name;

  @override
  void desp() {
    print("This is Continent ${getContinentName()}");
  }

  @override
  String getContinentName() {
    return "Europe";
  }
}

// 泛型
class Utils<T, R> {
  T valueA;
  R valueB;
}

class EventBus {
  EventBus._singleInstance();

  static EventBus _instance = new EventBus._singleInstance();

  factory EventBus() {
    return _instance;
  }
}