/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s2;

import java.util.HashMap;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

/**
 *
 * @author neal
 */
public class SceneController {
    private static Scene main;

    /**
     * @param main the main to set
     */
    public static void setMain(Scene main) {
        SceneController.main = main;
    }

    protected static void load(Pane pane) {
        main.setRoot(pane);
    }
}