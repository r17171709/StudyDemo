import 'package:flutter/material.dart';

class TimeWidget extends StatefulWidget {
  TimeWidget({Key? key}) : super(key: key);

  var time = "";

  @override
  State<TimeWidget> createState() => _TimeWidgetState();
}

class _TimeWidgetState extends State<TimeWidget> {
  @override
  Widget build(BuildContext context) {
    return Container(
      child: ElevatedButton(
        onPressed: () async {
          var date = await showDatePicker(
              context: context,
              initialDate: DateTime.now(),
              firstDate: DateTime(DateTime.now().year - 1),
              lastDate: DateTime(DateTime.now().year + 1));
          var time = await showTimePicker(
              context: context, initialTime: TimeOfDay.now());

          setState(() {
            if (date != null && time != null) {
              widget.time = "${date.year}-"
                  "${date.month < 10 ? "0${date.month}" : date.month}-"
                  "${date.day < 10 ? "0${date.day}" : date.day} "
                  "${time.hour < 10 ? "0${time.hour}" : time.hour}:"
                  "${time.minute < 10 ? "0${time.minute}" : time.minute}";
            }
          });
        },
        child: Text(widget.time.isEmpty ? "请选择时间" : widget.time),
      ),
    );
  }
}
