import 'package:flutter/material.dart';

// 日期、时间选择

void main() {
  runApp(new MyShowDatePickerAppDemo());
}

class MyShowDatePickerAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyShowDatePickerAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("MyShowDatePickerAppDemo"),
        ),
        body: new ShowDatePickerDemo(),
      ),
    );
  }
}

class ShowDatePickerDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new ShowDatePickerDemoState();
  }
}

class ShowDatePickerDemoState extends State<ShowDatePickerDemo> {
  DateTime _now = new DateTime.now();
  TimeOfDay _nowtime = new TimeOfDay.now();

  Future<void> _selectDate(BuildContext context) async {
    final DateTime picked = await showDatePicker(
        context: context,
        initialDate: _now,
        firstDate: new DateTime(2018, 1),
        lastDate: new DateTime(2020, 1));
    if (picked != null) {
      setState(() {
        _now = picked;
      });
    } else {
      _now = new DateTime.now();
    }
  }

  Future<void> _selectTime(BuildContext context) async {
    TimeOfDay picked =
        await showTimePicker(context: context, initialTime: _nowtime);
    if (picked != null) {
      setState(() {
        _nowtime = picked;
      });
    } else {
      _nowtime = new TimeOfDay.now();
    }
  }

  @override
  Widget build(BuildContext context) {
    return new Column(
      children: <Widget>[
        new RaisedButton(
          onPressed: () {
            _selectDate(context);
          },
          child: new Text("Date"),
        ),
        new RaisedButton(
          onPressed: () {
            _selectTime(context);
          },
          child: new Text("Time"),
        ),
      ],
    );
  }
}
