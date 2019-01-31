import 'package:flutter/material.dart';
import 'package:flutter_demo/material_components/app_structure_and_navigation/AppBarDemo.dart';

//  Brightness brightness,
//  MaterialColor primarySwatch,                              主题颜色
//    Color primaryColor,                                     顶部导航栏和状态栏的颜色。此颜色会覆盖primarySwatch
//    Brightness primaryColorBrightness,                      primaryColor采用Dark还是Light模式，影响顶部导航栏和状态栏上文字的颜色
//    Color primaryColorLight,
//    Color primaryColorDark,
//    Color accentColor,                                      强调颜色。例如FloatActionButton中背景色
//    Brightness accentColorBrightness,                       accentColor采用Dark还是Light模式。影响诸如FloatActionButton中Icon的颜色
//    Color canvasColor,
//    Color scaffoldBackgroundColor,                          scaffold的背景颜色
//    Color bottomAppBarColor,                                bottomAppBar的背景色
//    Color cardColor,                                        card的背景颜色
//    Color dividerColor,                                     divider的颜色
//    Color highlightColor,                                   被选中时高亮时候的颜色。例如按下button时候的颜色
//    Color splashColor,                                      涟漪时候的颜色。例如按下button涟漪时候的颜色
//    InteractiveInkFeatureFactory splashFactory,             定义InkWall和InkResponse涟漪时候的外观
//    Color selectedRowColor,
//    Color unselectedWidgetColor,                            可切换widget的未选中时边框的颜色，例如checkbox
//    Color disabledColor,                                    禁用时候的颜色。例如button被禁用
//    Color buttonColor,                                      button背景色
//    ButtonThemeData buttonTheme,                            button主题配置数据集
//    Color secondaryHeaderColor,
//    Color textSelectionColor,                               文本字段中选中文本的颜色。例如TextField
//    Color cursorColor,                                      光标的颜色
//    Color textSelectionHandleColor,
//    Color backgroundColor,                                  与primaryColor对比的颜色。例如进度条的剩余部分
//    Color dialogBackgroundColor,                            dialog背景颜色
//    Color indicatorColor,                                   TabBar中选项选中的指示器颜色
//    Color hintColor,                                        提示文本或占位符文本的颜色。例如TextField
//    Color errorColor,                                       验证错误的文本的颜色。例如TextField
//    Color toggleableActiveColor,                            可切换widget的背景颜色，例如Switch，Radio和Checkbox
//    String fontFamily,                                      默认字体
//    TextTheme textTheme,
//    TextTheme primaryTextTheme,
//    TextTheme accentTextTheme,
//    InputDecorationTheme inputDecorationTheme,              TextField和TextFormField的默认InputDecoration值基于此主题
//    IconThemeData iconTheme,                                iconTheme的默认主题
//    IconThemeData primaryIconTheme,
//    IconThemeData accentIconTheme,
//    SliderThemeData sliderTheme,                            slider的默认主题
//    TabBarTheme tabBarTheme,                                tabBar的默认主题
//    ChipThemeData chipTheme,                                chip的默认主题
//    TargetPlatform platform,
//    MaterialTapTargetSize materialTapTargetSize,            点击区域 padded：向四周扩展48px区域 shrinkWrap：控件区域
//    PageTransitionsTheme pageTransitionsTheme,
//    ColorScheme colorScheme,
//    DialogTheme dialogTheme,                                dialog的默认主题
//    Typography typography,

void main() {
  runApp(new MyThemeAppDemo());
}

class MyThemeAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyThemeAppDemo",
      theme: new ThemeData(
          primarySwatch: Colors.red,
          primaryColor: Colors.blue,
          primaryColorBrightness: Brightness.dark,
          primaryColorDark: Colors.black,
          primaryColorLight: Colors.black,
          accentColor: Colors.purple,
          accentColorBrightness: Brightness.dark,
          scaffoldBackgroundColor: Colors.green[100],
          bottomAppBarColor: Colors.red,
          cardColor: Colors.green,
          dividerColor: Colors.black,
          highlightColor: Colors.blue[700],
          splashColor: Colors.black,
          splashFactory: InkRipple.splashFactory,
          selectedRowColor: Colors.red,
          unselectedWidgetColor: Colors.green,
          disabledColor: Colors.black,
          buttonColor: Colors.blue,
          buttonTheme: new ButtonThemeData(height: 20),
          textSelectionColor: Colors.red[900],
          cursorColor: Colors.red[900],
          backgroundColor: Colors.green,
          dialogBackgroundColor: Colors.green[300],
          indicatorColor: Colors.red,
          hintColor: Colors.blue,
          errorColor: Colors.green,
          toggleableActiveColor: Colors.yellow,
          inputDecorationTheme: new InputDecorationTheme(
              border: new OutlineInputBorder(
                  borderSide: new BorderSide(color: Colors.blue))),
          iconTheme: new IconThemeData(color: Colors.yellow),
          primaryIconTheme: new IconThemeData(color: Colors.black),
          sliderTheme: new SliderThemeData.fromPrimaryColors(
              primaryColor: Colors.blue[200],
              primaryColorDark: Colors.red[700],
              primaryColorLight: Colors.yellow[200],
              valueIndicatorTextStyle: new TextStyle()),
          tabBarTheme: new TabBarTheme(
              labelColor: Colors.blue[700],
              unselectedLabelColor: Colors.blue[200]),
          chipTheme: new ChipThemeData.fromDefaults(
              primaryColor: Colors.green[700],
              secondaryColor: Colors.green[200],
              labelStyle: new TextStyle(color: Colors.black)),
          dialogTheme: new DialogTheme(
              shape: new RoundedRectangleBorder(
                  borderRadius: new BorderRadius.all(Radius.circular(30))))),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("ThemeDemo"),
        ),
        floatingActionButton: new FloatingActionButton(
          onPressed: () {},
          child: new Icon(Icons.add),
        ),
        bottomNavigationBar: new BottomAppBarDemo(),
        body: new ThemeDemo(),
      ),
    );
  }
}

class TabBarDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _TabBarDemoState();
  }
}

class _TabBarDemoState extends State<TabBarDemo>
    with SingleTickerProviderStateMixin {
  TabController _tabController;

  @override
  void initState() {
    super.initState();
    _tabController = new TabController(length: 3, vsync: this);
    _tabController.addListener(() {});
  }

  @override
  Widget build(BuildContext context) {
    return new TabBar(
      tabs: <Tab>[
        new Tab(
          icon: new Icon(Icons.account_box),
          child: new Text("A"),
        ),
        new Tab(
          icon: new Icon(Icons.accessibility_new),
          child: new Text("B"),
        ),
        new Tab(
          icon: new Icon(Icons.accessible_forward),
          child: new Text("C"),
        )
      ],
      controller: _tabController,
    );
  }
}

class ThemeDemo extends StatelessWidget {
  Future<void> clickButtonDialog(BuildContext context) async {
    Future<void> dialog = await showDialog(
        context: context,
        builder: (BuildContext context) {
          return new AlertDialog(
            title: new Text("Title"),
            content: new Text("contente"),
            actions: <Widget>[
              new SimpleDialogOption(
                child: new Text("确定"),
                onPressed: () {
                  Navigator.of(context).pop();
                },
              )
            ],
          );
        });
    return dialog;
  }

  @override
  Widget build(BuildContext context) {
    return new Column(
      children: <Widget>[
        new TabBarDemo(),
        new Text("123"),
        new Flex(
          direction: Axis.horizontal,
          children: <Widget>[
            new Expanded(
              child: new TextFormField(
                decoration:
                    new InputDecoration(hintText: "Hint", errorText: "Error"),
              ),
              flex: 1,
            ),
            new Expanded(
              child: new TextFormField(),
              flex: 1,
            )
          ],
        ),
        new Row(
          children: <Widget>[
            new RaisedButton(
              onPressed: () {
                clickButtonDialog(context);
              },
              child: new Text("Button"),
            ),
            new RaisedButton(
              onPressed: null,
              child: new Text("Button2"),
            )
          ],
        ),
        new Card(
          child: new Padding(
            padding: EdgeInsets.all(4),
            child: new Text("Card"),
          ),
        ),
        new Divider(),
        new InkWell(
          child: new Text("InkWell"),
          onTap: () {},
        ),
        new Row(
          children: <Widget>[
            new SizedBox(
              width: 30,
              height: 30,
              child: new Checkbox(value: true, onChanged: (bool value) {}),
            ),
            new SizedBox(
              width: 30,
              height: 30,
              child: new Checkbox(value: false, onChanged: (bool value) {}),
            )
          ],
        ),
        new LinearProgressIndicator(
          value: 0.5,
        ),
        new Slider(value: 0.5, onChanged: (double value) {}),
        new Chip(
          label: new Text("Chip"),
          deleteIcon: new Icon(Icons.delete),
          onDeleted: () {},
        ),
        new Radio(value: "1", groupValue: "NO.1", onChanged: (String value) {})
      ],
    );
  }
}

//class _MyThemeAppDemoState extends State<MyThemeAppDemo> {
//  Color _color = Colors.red;
//
//  @override
//  Widget build(BuildContext context) {
//    ThemeData themeData = Theme.of(context);
//
//    return new MaterialApp(
//      title: "MyThemeAppDemo",
//      theme: new ThemeData(
//          primarySwatch: _color, iconTheme: new IconThemeData(color: _color)),
//      home: new Scaffold(
//        appBar: new AppBar(
//          title: new Text("ThemeDemo"),
//        ),
//        body: new Container(
//          child: new Row(
//            children: <Widget>[
//              new Icon(Icons.add),
//              new Theme(
//                  data: themeData.copyWith(
//                      iconTheme:
//                          themeData.iconTheme.copyWith(color: Colors.yellow)),
//                  child: new Icon(Icons.add))
//            ],
//          ),
//        ),
//        floatingActionButton: new FloatingActionButton(
//          onPressed: () {
//            setState(() {
//              _color = Colors.blue;
//            });
//          },
//          child: new Icon(Icons.change_history),
//        ),
//      ),
//    );
//  }
//}
