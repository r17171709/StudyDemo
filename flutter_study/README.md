flutter build web时提示"Missing index.html."

控制台输入flutter config --enable-web
在项目根目录执行flutter create .注意有个英文符号点！
最后执行flutter build web就能看到web目录创建成功了。
执行flutter run -d chrome就能在Chrome浏览器中运行Flutter应用了！




Flutter web打包后,访问页面显示空白

flutter build web --web-renderer html