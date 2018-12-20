void main() {
  // 函数调用
  func1(1, 2);

  print("");

  print("func3():${func3(1, 2)}");

  print("");

  // 可选位置参数
  print(func5(1, 2));
  print(func5(1, 2, 3));
  print(func5(1, 2, 3, 4));

  print("");

  // 可选命名参数
  print(func6(1, 2));
//  print(func6(1, 2, 3, 4));  错误，可选参数必须要指定对应的参数名
  print(func6(1, 2, c: 3));
  print(func6(1, 2, d: 4));
  print(func6(1, 2, c: 3, d: 4));

  print("");

  // 函数对象
  // Dart中函数也是一个对象，可以通过var或者Function来声明
  Function function = func9;
  function();
  // 函数对象可以作为一个入参，也可以作为一个返回值对象返回
  func10(func9);

  print("");

  // 闭包
  Function function11 = func11(1);
  /**
   *  function11相当于这个函数
      int temp(int value2) {
      return 1 + value2;
      }
   */
  print(function11(2));
}

// Dart的函数基本上跟Java是一样的，除了没有权限修饰符
int func1(int a, int b) {
  return a + b;
}

void func4() {
  print("fun4");
}

// 有语法糖可以让单行函数体变得更优雅
int func2(int a, int b) => a + b;

// 可以省略函数返回类型，默认返回null
func3(int a, int b) => a + b;

// 这个概念在Java跟Kotlin都是没有的。既然是可选，那就是参数可以不传
// 可选参数分为2种，可选位置参数和可选命名参数
// 可选位置参数严格根据函数的位置传入参数，它有个很明显的标志[]。来看下可选位置参数的写法，你可以选择只传c或者同时传c、d，但是不可以只传d不传c
int func5(int a, int b, [int c, int d]) {
  if (c != null && d != null) {
    return a + b + c + d;
  } else if (c != null) {
    return a + b + c;
  } else if (d != null) {
    return a + b + d;
  }
  return a + b;
}

// 可选命名参数相对灵活一点，你可以选择传递任何一个你想传的参数，它有个很明显的标志{}。在传入的时候只需要指定下对应的参数名，没有顺序限制也没有可选位置参数那样传参前置条件
int func6(int a, int b, {int c, int d}) {
  if (c != null && d != null) {
    return a + b + c + d;
  } else if (c != null) {
    return a + b + c;
  } else if (d != null) {
    return a + b + d;
  }
  return a + b;
}

// 默认参数值
// 在函数的参数上面使用=号给一个常量值。如果没有传入该值，代码在运行时就使用刚才给的值
void func7(int a, int b, [int c = 10, int d]) {}

void func8(int a, int b, {int c = 10, int d}) {}

// 函数对象
void func9() {
  print("func9");
}

// 函数对象可以作为一个入参，也可以作为一个返回值对象返回
void func10(Function function) {
  function();
}

// 闭包
// 闭包定义在其它函数内部，能够访问外部函数的局部变量，并持有其状态
Function func11(int value1) {
  return (int value2) {
    return value1 + value2;
  };
}
