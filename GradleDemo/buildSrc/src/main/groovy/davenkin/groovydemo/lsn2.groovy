package davenkin.groovydemo


import groovy.json.JsonSlurper

class LSN2 {
    def jsonDemo() {
        def list = [new Person(name: "PQ1", age: 1), new Person(name: "PQ2", age: 2)]
//        println JsonOutput.toJson(list)
//        println JsonOutput.prettyPrint(JsonOutput.toJson(list))

        def response = request("https://run.mocky.io/v3/349f82af-5011-4573-a037-b38c8243d54d")
        println response.data.access_token
    }

    /**
     * 网络请求
     * @param url
     * @return
     */
    def request(String url) {
        HttpURLConnection connection = new URL(url).openConnection()
        connection.setRequestMethod("GET")
        connection.connect()
        def response = connection.content.text
        def jsonSlpuer = new JsonSlurper()
        return jsonSlpuer.parseText(response)
    }

    def fileDemo() {
        // 打印完整的当前目录路径
//        println new File(System.getProperty("user.dir")).name

        File file = new File("app/destination.txt")

//        println(file.exists())

//        file.eachLine {
//            println(it)
//        }

//        println file.getText()

//        println file.readLines()

//        println file.withReader {
//            // 读取文件部分内容
//            char[] buffer = new char[3]
//            it.read(buffer)
//            return buffer
//        }

        copy("app/destination.txt", "app/destination2.txt")
    }

    def copy(String srcPath, String destPath) {
        File dstFile = new File(destPath)
        if (!dstFile.exists()) {
            dstFile.createNewFile()
        }
        new File(srcPath).withReader {
            def lines = it.readLines()
            dstFile.withWriter { writer ->
                lines.each { line ->
                    writer.append(line)
                }
            }
        }
    }
}