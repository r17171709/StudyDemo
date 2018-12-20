void main() {
  // Dart提供了3种核心的集合类型List(列表)、Set(集合)和Map(映射)。

  // 几种构造函数

  // 使用[]创建列表。这个可不是Java里的数组
  List list1 = ["Ronaldo", 33, "Messi", 30];
  // 使用泛型来限制列表可添加数据类型
  List list3 = <int>[33, 30];
//  list3.add(true);  错误，编译通过，运行出错

  // 创建固定长度的列表
  List<String> list5 = new List(5);

  // 创建可改变长度的列表。
  List<String> list7 = new List();
  list7.add("1");
  list7.add("2");
  list7.add("3");
  list7.add("4");
  list7.add("5");
  list7.add("6");
  list7.length = 10;
  list7[9] = "9";
//  list7[99] = "9"; 错误，长度只有10
  list7.length = 13;
  list7[12] = "12";
  list7.length = 15;
  list7[14] = "14";

  // 在初始化固定长度后的List中添加数据
  List<String> list6 = new List()..length = 10;
  list6.add("1");
  list6.add("2");
  list6.add("3");
  list6.add("4");
  list6.add("5");
  list6.add("6");
  list6.add("7");
  list6.add("8");

  List<String> list8 = List<String>.from(["Ronaldo", "Messi"]);

  // 为所有元素统一赋值
  List<String> list9 = List<String>.filled(3, "");

  // 用生成器给所有元素赋初始值
  List<String> list10 = List<String>.generate(3, (int index) {
    return "";
  });

  // 遍历列表的方式。
  // 虽然之前指定了添加的数据类型，但是泛型在前与在后效果是不一样的
  list3.forEach((dynamic value) {
    print("$value");
  });
  // 泛型在前的话就可以直接使用指定类型进行遍历了
  List<String> list2 = ["Ronaldo", "Messi"];
  list2.forEach((String value) {
    print("$value");
  });

  // 列表相关函数使用范例
  list2.add("Dybala");

  list2.length;

  list2.contains("Dybala");

//  list2.clear();

  list2.elementAt(0);

  // 对现有元素进行扩展
  List<String> temp = ["Piatek", "Mandzukic"];
  temp.expand((String element) {
    return [element, element];
  });

  // 判断列表中的任一元素是否满足指定条件
  bool any = list2.any((String element) {
    return element == "Dybala";
  });

  // 判断列表中的全部元素是否满足指定条件
  bool every = list2.every((String element) {
    return element == "Dybala";
  });

  list2.first;

  // 获取列表中第一个满足指定条件的元素
  try {
    var firstWhere = list2.firstWhere((String element) {
      return element == "Dybala1";
    });
  } catch (e) {}

  var range = list2.getRange(0, 2);

  list2.insert(2, "Messi");

  print(list2.indexOf("Griezmann"));
  // 从第几个元素开始查找
  print(list2.indexOf("Griezmann", 1));

  list2.isEmpty;

  // 查找满足指定条件的元素索引
  int index = list2.indexWhere((String element) {
    return element == "Messi";
  });

  // 转成字符串
  print(list2.join());

  list2.map((String value) {
    return "person $value";
  }).forEach((String value) {
    print(value);
  });

  list2.removeLast();

  list2.remove("Messi");

  List<String> iterable = <String>["Pogba", "Griezmann"];
//  List iterable = <String>["Pogba", "Griezmann"];  错误，类型不一致不能添加
  list2.addAll(iterable);
  list2.addAll(<String>["Harry Kane", "Modric"]);

  // 列表按照指定逻辑进行拼接
  var reduce = list2.reduce((String value, String element) {
    return "${value} + ${element}";
  });
  print(reduce);

  // 获取列表中start、end索引间的集合
  var tempList = list2.sublist(2);

  // 查找列表中唯一一条满足指定条件的元素，如果元素数量大于1则报错
  String singleWhere = list2.singleWhere((String string) {
    return "Harry Kane" == string;
  });
  print(singleWhere);

  // 查找列表中所有满足指定条件的元素
  list2.where((String string) {
    return string.substring(0, 1).toLowerCase() == 'm';
  }).forEach((String string) {
    print(string);
  });

  // 查找列表中所有满足指定条件的元素。与where不同的是，retainWhere直接将不满足的元素从原始List中去除，而where则不会破坏原数据
  list2.retainWhere((String string) {
    return string.substring(1, 2).toLowerCase() == 'o';
  });

  list2.removeAt(1);

  // 排序
  list2.sort((String a, String b) {
    return a.codeUnitAt(0) > b.codeUnitAt(0) ? 1 : 0;
  });

  print("");

  // 使用{}创建Map。基本使用方式与Java的映射类似
  Map<String, int> map = {
    "Juventus": 1,
    "Napoli": 2,
    "Inter": 3,
    "A.C. Milan": 4
  };

  Map<String, int> map2 = new Map.fromIterables(["a", "b"], [1, 2]);

  Map<String, String> map3 = new Map.fromIterable(["a", "b"], key: (element) {
    return element;
  }, value: (element) {
    return element;
  });

  map["Lazio"] = 5;

  print(map["Juventus"]);

  Map<String, int> tempMap = {"Roma": 8};
  map.addAll(tempMap);

  map.containsKey("Real Madrid");

  map.isNotEmpty;
  map.isEmpty;

  map.keys;
  map.values;

  map.length;

  map.map((String key, int value) {
    return MapEntry("team: $key", value);
  }).forEach((String key, int value) {
    print("key: $key value: $value");
  });

  map.remove("Inter");

  map.removeWhere((String key, int value) {
    return key == "Napoli";
  });

  map.update("Roma", (int value) {
    return 2;
  });

  map.clear();

  print("");

  // Set是没有顺序且不能重复的集合，所以不能通过索引去获取值
  Set<String> set = new Set.from(["Ronaldo", "Messi"]);

  Set<String> set2 = new Set();
  set2.add("Italy");
  set2.add("Italy");
  set2.addAll(["England", "France"]);

  set2.forEach((String value) {
    print("value: ${value}");
  });

  set.first;
  set.last;

  set.contains("Ronaldo");
  set.containsAll(["Ronaldo", "Messi"]);

  set.difference(set2).forEach((dynamic value) {
    print("$value");
  });

//  set.clear();

  set.elementAt(0);

  set.length;

  set.take(2).toList();

  set.union(set2).forEach((dynamic value) {
    print("$value");
  });

  // list转换为set
  List<String> list4 = ["a", "b", "a", "b"];
  Set<String> set4 = list4.toSet();

  // 只有一个元素就返回元素，否则异常
//  set4.single;  错误

  // List、Set和Map有一些通用的函数。其中的一些通用函数都是来自于类Iterable。List和Set是Iterable类的实现。虽然Map没有实现Iterable, 但是Map的属性keys和values都是Iterable对象
  // 例如isNotEmpty，isEmpty，forEach，map，where，any，Every
}
