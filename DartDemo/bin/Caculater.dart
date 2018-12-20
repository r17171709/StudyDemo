// 这是使用Dart语言来实现最基础的计算器加减乘除功能的范例
// 如果你之前学习过Java或者Kotlin语言（当然，部分Dart语法可能与其他语言类似，不过我没学过，不在此深究），那么Dart给你的第一感觉便是：哇，这个看上去似曾相识
// Dart语言的基本类型也是诸如int、String等寻常关键字。同Kotlin一样，也可以使用${表达式}将表达式放入字符串中。Dart的访问控制符没有显式的展现出来，这个我们会在稍后进行详细介绍
// 同Kotlin一样，在Dart中我们可把一个函数当做一个变量传入到另外一个函数中

void main() {
  print("加法结果为：${operation(10, 5, add)}");
  print("减法结果为：${operation(10, 5, sub)}");
  print("乘法结果为：${operation(10, 5, multi)}");
  print("除法结果为：${operation(10, 5, div)}");
}

int add(int a, int b) {
  return a+b;
}

int sub(int a, int b) {
  return a-b;
}

int multi(int a, int b) {
  return a*b;
}

int div(int a, int b) {
  return a~/b;
}

int operation(int a, int b, int method(int a, int b)) {
  return method(a, b);
}