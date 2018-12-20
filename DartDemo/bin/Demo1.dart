import 'package:DartDemo/PrivateLibrary.dart';
import '../lib/PrivateLibrary.dart';
import 'Demo5.dart';

void main() {
  // print可以打印任意Object类型的对象，String、double、int、bool的父类都是Object。
  print("Hello World");
  print(3);
  print(1.1);
  print(true);
  print(null);

  print("");

  // Dart的几种内置的数据类型如下：数值型-num、布尔型-bool、字符串-String、列表-List、键值对-Map、其他类型-Runes、Symbols。而数值型仅有int与double，不像其他语言分的很细
  num num1 = 1;
  num num2 = 1.1;
  int int1 = 1;
  double double1 = 1.1;
  // 如果变量使用num进行声明，则可以随意在使用中转换为int或double类型；但如果使用int或者double进行明确的声明，那么就不能随意转换了
  num1 = 1.1;
  num2 = 1;
//  int1 = 1.1;  错误
  // 除了以上所说的数据类型，Dart还有var、dynamic与const三种数据类型
  // var可以用来声明任意类型，Dart会根据其被赋予的数值的数据类型进行自动推导
  var var1 = "String";
  var var2 = 1;
  var var3 = 1.1;
  var var4 = true;
  // 如果你仅使用var声明一个变量但是并未对其进行赋值，那么你可以在使用过程中将其更改为任意数据类型的值
  var var5;
  var5 = 1;
  var5 = 1.1;
  // 但如果你使用var声明变量的时候已经对其赋予指定数据类型的值，那么其数据类型就不可以更改了，因为此时已经决定了它是什么类型
  var var6 = 1;
//  var6 = 1.1;  错误
  // var修饰的变量一旦被编译，则会自动匹配var变量的实际类型，并用实际类型来替换该变量的申明

  // dynamic被编译后，实际是一个Object类型，只不过编译器会对dynamic类型进行特殊处理，让它在编译期间不进行任何的类型检查，而是将类型检查放到了运行期
  dynamic dynamic1 = 1;
  dynamic1 = 1.1;
  dynamic1 = "";
  dynamic1 = true;
  // 正因为类型检查放到了运行期，所以在使用dynamic的时候需要倍加小心
//  dynamic1++;  错误，编译期可以通过，但是运行时报错

  // 再来看看const。Dart中有两种数据常量数据类型，const和final。与final不同的是，const是编译时常量。什么是编译时常量，来看这么个例子
  // 在String类里面有一个判断其是不是为空的函数isEmpty。在代码编译过程中是没法知道字符串""是不是为空的，只有当代码运行到此行之后，才能通过isEmpty函数的调用知道。所以const就不满足这一类型的常量声明，只能使用final
  final int int3 = "".isEmpty ? 10 : 6;
//  const int int4 = "".isEmpty ? 10 : 6;  错误
  // 同理，下面这个例子就可以。因为代码在编译期就知道8是肯定大于6的，所以这个值就能被确定下来
  const int int4 = 8 > 6 ? 8 : 6;
  // 可以使用const进行初始化的对象也可以使用final进行赋值，反之则不行
  final int5 = 8 > 6 ? 8 : 6;
  // 任何数据类型，包括int或者double这种看似基本类型的，如果没有赋默认值，Dart都会用null来作为其默认值。这是不是比Java还要面向对象？
  int int7;
  print("int7:$int7");

  print("");

  // 操作符
  // Dart中操作符大体上与其他语言差不多，这里只介绍一些不常见的操作符
  // ~/。Java里面如果int/int的话，得到的也是int，这就是通常所说的取整。如果要得到浮点型数值的结果，则需要将其中一个数值变成浮点型数值才行，但是Dart不需要这样
  var double2 = 7 / 3;
  print("double2:$double2");
  // 有什么办法可以得到int呢？那就需要用到 ~/了
  var int2 = 7 ~/ 3;
  print("int2:$int2");

  // as强转操作符。这个跟Kotlin是一样的。这边要是转换格式不匹配，则会报错
//  int int8 = num1 as int;  运行时报错
  double double3 = num1 as double;
  // 所以在使用之前最好判断一下
  if (num1 is int) {}

  // ??=空赋值操作符
  int int9;
  int9 ??= 11;
  print("int9:$int9");

  // ?. 类似于Kotlin的非空判断
  int a10;
  print("a10:${a10?.toString()}");
  a10 = 10;
  print("a10:${a10?.toString()}");
  // 级联符号.. 允许您在同一个对象上进行一系列操作。 除了函数调用之外，还可以访问同一对象上的字段。其实相当于Java的链式调用
  CircleShape shape = new CircleShape()
    ..radius = 3
    ..color = 1;

  print("");

  // 权限修饰符
  // Dart里没有private/protected/public等权限修饰符，这就意味着默认情况下函数或者常量、变量都是可访问的。但是Dart还是有私有权限设置的办法的，只需要将需要修饰的函数或者常量、变量加上_前缀即可
  // 但是这里比较坑的一点是，_并不是从class级别去限制，而是从package级别去限制
  print(PrivateTest()._private);
//  print(PrivateTest2()._private2);  错误，无法访问private

  print("");

  // 字符串的使用
  // 在任何一门语言中，字符串都是被单独拿出来的小重点，因为他启到一个承上启下的作用，后面我们将开始接触到类与函数的概念。刚才我们知道Dart中String跟int同属对象，虽然情况与Java等有所不同，但是我们依然重点单独讲一下它
  //  Dart中可以使用单引号或双引号声明字符串。在Java和Kotlin中都不可以这样，单引号只能声明为一个char
  String string2 = "Hello World";
  String string3 = 'Hello World';
  // 字符串拼接
  // 使用空格来拼接
  String string4 = "Hello" "Hello" "Hello";
  // 使用+来拼接
  String string5 = "Hello" + "Hello" + "Hello";
  // 使用换行来拼接
  String string6 = "Hello"
      "Hello"
      "Hello";
  // 使用${表达式}来拼接。这个跟Kotlin是一样的
  String string7 = "$string2";
  // 剩下的，如何使用String中的函数，倒是没什么好说的，大家都能看得懂
  String string1 = "Hello World";
  string1.contains("Hello");
  string1.endsWith("World");
  string1.indexOf("e");
  string1.isEmpty;
  string1.length;
  string1.lastIndexOf("l");
  string1.replaceRange(0, 5, "Hi");
  string1.substring(0, 5);
  string1.split(" ").length;
  string1.trim();
  string1.toLowerCase();
  string1.toUpperCase();
  string1.toString();
}

class PrivateTest {
  String _private = "private";
}
