/// 数字类型
void numType() {
  // num是数字类型的父类
  num num1 = 1;
  num num2 = -1.0;
  int int1 = 3;
  double double1 = 1.1;

  // 绝对值
  num2.abs();
  // 类型转换
  num1.toInt();
  num1.toString();
  num1.toDouble();
}

/// 字符串类型
void stringType() {
  String str1 = "abc";
  String str2 = "abc", str3 = "cba";
  String str4 = "str2:$str2 str3:$str3";
  String str5 = str2 + str3;
  str5.substring(1, 2);
  str5.indexOf("a");
}

/// 布尔类型
void boolType() {
  bool success = true, fail = false;
}

/// 集合类型
void listType() {
  List list = [1, "a", 1.2];
  List<int> list2 = [1, 2, 3];
  list2.add(4);
  List list3 = List.generate(3, (index) => index.toString());
  for (var value in list3) {}
  list3.forEach((element) {});
  list3.remove("2");
  list3.insert(0, "element");
}
