package com.brok1n.kotlin.fx.filemd5.controller

import com.brok1n.kotlin.fx.filemd5.DigestUtil
import javafx.application.Platform
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.ProgressBar
import javafx.scene.control.ProgressIndicator
import javafx.scene.control.TextArea
import javafx.scene.input.DragEvent
import javafx.scene.input.TransferMode
import java.io.File
import kotlin.concurrent.thread

public class MainPageController: SimpleController() {

    @FXML
    lateinit var dataEdit: TextArea

    @FXML
    lateinit var progressCtl: ProgressBar

    @FXML
    lateinit var progressIndicator: ProgressIndicator

    @FXML
    lateinit var circleBtn: Button

    @FXML
    lateinit var strInp:TextArea

    //文件路径
    var filePath = ""

    @FXML
    fun onDataPathDragDropped( event: DragEvent ) {
        val dragboard = event.dragboard;
        if (dragboard.hasFiles()){
            try {
                val file = dragboard.files[0];
                if (file != null) {
                    filePath = file.getAbsolutePath();
                    processFile()
                }
            }catch (e:Exception ){
                e.printStackTrace()
            }
        }
    }

    /**
     * 处理这个加载的文件
     * */
    fun processFile() {

        val file = File(filePath)

        dataEdit.text = ""

        dataEdit.appendText("文件：$filePath\n")

        dataEdit.disableProperty().value = true
        strInp.disableProperty().value = true
        circleBtn.disableProperty().value = true

        thread {
            DigestUtil.getMd5ByFile(File(filePath), object : DigestUtil.IProcessingProgressListener {
                override fun onStart(fileSize: Long) {
                    Platform.runLater {
                        dataEdit.appendText("大小：$fileSize 字节\n")
                    }
                }

                override fun onProgressChanged(progress: Double) {
                    if ( progress != progressCtl.progress ) {
                        Platform.runLater {
                            progressCtl.progress = progress
                            progressIndicator.progress = progress
                        }
                    }
                }

                override fun onCompleted(result: String) {
                    Platform.runLater {
                        if ( result.length > 5 ) {
                            dataEdit.appendText("MD5 32位 小写：${result.toLowerCase()}\n")
                            dataEdit.appendText("MD5 32位 大写：${result.toUpperCase()}\n")
                            dataEdit.appendText("MD5 16位 小写：${result.substring(8, 24).toLowerCase()}\n")
                            dataEdit.appendText("MD5 16位 大写：${result.substring(8, 24).toUpperCase()}\n")
                        } else {
                            dataEdit.appendText("计算MD5失败！")
                        }
                    }
                }

            })

            DigestUtil.getSha1ByFile(File(filePath), object : DigestUtil.SimpleProcessingProgressListener() {

                override fun onProgressChanged(progress: Double) {
                    if ( progress != progressCtl.progress ) {
                        Platform.runLater {
                            progressCtl.progress = progress
                            progressIndicator.progress = progress
                        }
                    }
                }

                override fun onCompleted(result: String) {
                    Platform.runLater {
                        if ( result.length > 5 ) {
                            dataEdit.appendText("SHA1 小写：${result.toLowerCase()}\n")
                            dataEdit.appendText("SHA1 大写：${result.toUpperCase()}\n")
                        } else {
                            dataEdit.appendText("计算SHA1失败！")
                        }
                    }
                }

            })

            DigestUtil.getCrc32ByFile(File(filePath), object : DigestUtil.SimpleProcessingProgressListener() {
                override fun onCompleted(result: String) {
                    Platform.runLater {
                        dataEdit.disableProperty().value = false
                        strInp.disableProperty().value = false
                        circleBtn.disableProperty().value = false
                        if ( result.length > 5 ) {
                            dataEdit.appendText("CRC32 小写：${result.toLowerCase()}\n")
                            dataEdit.appendText("CRC32 大写：${result.toUpperCase()}\n")
                        } else {
                            dataEdit.appendText("计算CRC32失败！")
                        }
                    }
                }

            })
        }

    }


    @FXML
    fun onCircleBtnClicked() {
        val str = strInp.text

        if ( dataEdit.text.length < 30 ) {
            dataEdit.text = ""
        } else {
            dataEdit.appendText("\n")
        }

        dataEdit.appendText("字符串：$str\n")

        dataEdit.disableProperty().value = true
        strInp.disableProperty().value = true
        circleBtn.disableProperty().value = true

        thread {
            DigestUtil.getMd5ByStr(str, object : DigestUtil.IProcessingProgressListener {
                override fun onStart(fileSize: Long) {
                    Platform.runLater {
                        dataEdit.appendText("大小：$fileSize 字节\n")
                    }
                }

                override fun onProgressChanged(progress: Double) {
                    if ( progress != progressCtl.progress ) {
                        Platform.runLater {
                            progressCtl.progress = progress
                            progressIndicator.progress = progress
                        }
                    }
                }

                override fun onCompleted(result: String) {
                    Platform.runLater {
                        if ( result.length > 5 ) {
                            dataEdit.appendText("MD5 32位 小写：${result.toLowerCase()}\n")
                            dataEdit.appendText("MD5 32位 大写：${result.toUpperCase()}\n")
                            dataEdit.appendText("MD5 16位 小写：${result.substring(8, 24).toLowerCase()}\n")
                            dataEdit.appendText("MD5 16位 大写：${result.substring(8, 24).toUpperCase()}\n")
                        } else {
                            dataEdit.appendText("计算MD5失败！")
                        }
                    }
                }

            })

            DigestUtil.getSha1ByStr(str, object : DigestUtil.SimpleProcessingProgressListener() {

                override fun onProgressChanged(progress: Double) {
                    if ( progress != progressCtl.progress ) {
                        Platform.runLater {
                            progressCtl.progress = progress
                            progressIndicator.progress = progress
                        }
                    }
                }

                override fun onCompleted(result: String) {
                    Platform.runLater {
                        if ( result.length > 5 ) {
                            dataEdit.appendText("SHA1 小写：${result.toLowerCase()}\n")
                            dataEdit.appendText("SHA1 大写：${result.toUpperCase()}\n")
                        } else {
                            dataEdit.appendText("计算SHA1失败！")
                        }
                    }
                }

            })

            DigestUtil.getCrc32ByStr(str, object : DigestUtil.SimpleProcessingProgressListener() {
                override fun onCompleted(result: String) {
                    Platform.runLater {
                        dataEdit.disableProperty().value = false
                        strInp.disableProperty().value = false
                        circleBtn.disableProperty().value = false
                        if ( result.length > 5 ) {
                            dataEdit.appendText("CRC32 小写：${result.toLowerCase()}\n")
                            dataEdit.appendText("CRC32 大写：${result.toUpperCase()}\n")
                        } else {
                            dataEdit.appendText("计算CRC32失败！")
                        }
                    }
                }

            })
        }

    }

    @FXML
    fun onDataPathDragOver( event: DragEvent ) {
        if (event.gestureSource != dataEdit){
            event.acceptTransferModes(TransferMode.COPY, TransferMode.MOVE, TransferMode.LINK);
        }
    }


}