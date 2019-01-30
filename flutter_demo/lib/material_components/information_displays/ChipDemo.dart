import 'package:flutter/material.dart';

//  this.avatar,                          标签前面的小widget
//  this.label,                           标签widget
//  this.labelStyle,                      标签widget的style
//  this.labelPadding,                    标签widget间距
//  this.deleteIcon,                      删除按钮图片
//  this.onDeleted,                       删除功能
//  this.deleteIconColor,                 删除按钮的颜色
//  this.deleteButtonTooltipMessage,      长按删除按钮提示文字
//  this.shape,                           标签形状
//  this.clipBehavior = Clip.none,        剪辑窗口widget内容
//  this.backgroundColor,                 标签背景颜色
//  this.padding,
//  this.materialTapTargetSize,           处理点击范围

void main() {
  runApp(new MyChipAppDemo());
}

class MyChipAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyChipAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("ChipDemo"),
        ),
        body: new Column(
          children: <Widget>[
            new ChipDemo(),
            new ChoiceChipDemo(),
            new ChipThemeDemo(),
            new ChipThemeDataDemo(),
            new FilterChipDemo(),
            new InputChipDemo(),
            new ActionChipDemo()
          ],
        ),
      ),
    );
  }
}

class ChipDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Chip(
      label: new Text("Chip"),
      labelPadding: EdgeInsets.all(4),
      labelStyle: new TextStyle(color: Colors.red),
      // Chip的背景颜色
      backgroundColor: Colors.black12,
      avatar: new Icon(
        Icons.add,
        color: Colors.red,
      ),
      deleteIcon: new Icon(Icons.delete, color: Colors.black),
      deleteIconColor: Colors.black,
      deleteButtonTooltipMessage: "手下留情",
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(5.0)),
      onDeleted: () {},
      clipBehavior: Clip.antiAlias,
      materialTapTargetSize: MaterialTapTargetSize.padded,
    );
  }
}

class ChoiceChipDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new ChoiceChipDemoState();
  }
}

class ChoiceChipDemoState extends State<ChoiceChipDemo> {
  List<String> text = <String>["Android", "IOS", "Web"];

  int currentChoice = -1;

  Iterable<Widget> choiceChips() sync* {
    for (int i = 0; i < text.length; i++) {
      yield new Padding(
        padding: EdgeInsets.all(4),
        child: new ChoiceChip(
          label: new Text(
            text[i],
            style: new TextStyle(color: Colors.grey),
          ),
          backgroundColor: Colors.white,
          selected: currentChoice == i,
          onSelected: (bool value) {
            setState(() {
              currentChoice = i;
            });
          },
        ),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return new Row(
      children: choiceChips().toList(),
    );
  }
}

class ChipThemeDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new ChipThemeDemoState();
  }
}

class ChipThemeDemoState extends State<ChipThemeDemo> {
  @override
  Widget build(BuildContext context) {
    return new ChipTheme(
        data: ChipTheme.of(context).copyWith(backgroundColor: Colors.blue),
        child: new Chip(
          label: new Text(
            "Hello",
          ),
          avatar: new CircleAvatar(
            child: new Icon(Icons.add),
          ),
        ));
  }
}

class ChipThemeDataDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new ChipThemeDataDemoState();
  }
}

class ChipThemeDataDemoState extends State<ChipThemeDataDemo> {
  bool choice = false;

  @override
  Widget build(BuildContext context) {
    return new ChipTheme(
        data: ChipThemeData.fromDefaults(
            // 未选中的颜色
            primaryColor: Colors.yellow,
            // 选中后的背景颜色
            secondaryColor: Colors.green,
            labelStyle: new TextStyle(fontSize: 30)),
        child: new ChoiceChip(
          label: new Text(
            "Hello",
          ),
          avatar: new CircleAvatar(
            child: new Icon(Icons.add),
          ),
          selected: choice,
          onSelected: (bool value) {
            setState(() {
              choice = value;
            });
          },
        ));
  }
}

class FilterChipDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _FilterChipDemoState();
  }
}

class _FilterChipDemoState extends State<FilterChipDemo> {
  List<String> selectText = <String>[];

  List<String> text = <String>["Android", "IOS", "Web"];

  Iterable<Padding> getChips() sync* {
    for (int i = 0; i < text.length; i++) {
      yield new Padding(
        padding: EdgeInsets.all(4.0),
        child: new FilterChip(
            label: new Text(text[i]),
            selected: selectText.contains(text[i]),
            onSelected: (bool value) {
              setState(() {
                if (value) {
                  selectText.add(text[i]);
                } else {
                  selectText.retainWhere((String element) {
                    return element == text[i];
                  });
                }
              });
            }),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return new Row(
      children: getChips().toList(),
    );
  }
}

class InputChipDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _InputChipDemoState();
  }
}

class _InputChipDemoState extends State<InputChipDemo> {
  List<String> text = <String>["Android", "IOS", "Web"];

  Iterable<Padding> getChips() sync* {
    for (int i = 0; i < text.length; i++) {
      yield new Padding(
        padding: EdgeInsets.all(4),
        child: new InputChip(
          label: new Text(text[i]),
          avatar: new CircleAvatar(
            child: new IconButton(
                icon: new Icon(Icons.add),
                iconSize: 18,
                padding: EdgeInsets.all(0),
                onPressed: () {}),
          ),
          onDeleted: () {},
        ),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return new Row(
      children: getChips().toList(),
    );
  }
}

class ActionChipDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _ActionChipDemoState();
  }
}

class _ActionChipDemoState extends State<ActionChipDemo> {
  Iterable<Padding> getChips() sync* {
    for (int i = 0; i < 3; i++) {
      yield new Padding(
        padding: EdgeInsets.all(4),
        child:
            new ActionChip(label: new Text("ActionChipDemo"), onPressed: () {}),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return new Wrap(
      // 主轴方向上的间距
      spacing: 10,
      // run的间距。run可以理解为新的行或者列，如果是水平方向布局的话，run可以理解为新的一行
      runSpacing: 20,
      children: getChips().toList(),
    );
  }
}
