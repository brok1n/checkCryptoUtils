package com.brok1n.kotlin.fx.filemd5

import com.brok1n.kotlin.fx.filemd5.controller.MainPageController
import javafx.application.Application
import javafx.stage.Stage
import javafx.scene.Scene
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import org.apache.commons.codec.digest.DigestUtils


public class Main : Application() {

    @Throws(Exception::class)
    override fun start(primaryStage: Stage) {
//        val root = FXMLLoader.load<Parent>(javaClass.getResource("mainPage.fxml"))

        val fxmlLoader = FXMLLoader(javaClass.getResource("mainPage.fxml"))
        val root = fxmlLoader.load<Parent>()
        primaryStage.title = "文件MD5V1.0.0 - by brok1n"
        primaryStage.scene = Scene(root, 550.0, 400.0)

        val mainPageController = fxmlLoader.getController<MainPageController>()
        mainPageController.scene = primaryStage.scene
        mainPageController.stage = primaryStage

        primaryStage.show()

    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(Main::class.java)
        }
    }
}
