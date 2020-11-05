java -jar bundletool.jar build-apks --bundle=app-debug.aab --output=bundle.apks --ks=debug.keystore --ks-pass=pass:android --ks-key-alias=androiddebugkey --key-pass=pass:android

java -jar bundletool.jar install-apks --apks=bundle.apks

java -jar bundletool.jar install-apks --apks=bundle.apks --modules="dynamicfeature"