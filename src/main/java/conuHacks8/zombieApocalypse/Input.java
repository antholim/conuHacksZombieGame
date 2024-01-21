package conuHacks8.zombieApocalypse;

import javafx.scene.input.KeyCode;

import java.util.HashMap;

public class Input {
    private static HashMap<KeyCode, Boolean> touches = new HashMap<>();// Personnage.update() va demander si certaines touches sont
    public static boolean isKeyPressed(KeyCode code) {
        return touches.getOrDefault(code, false);
    }
    public static void setKeyPressed(KeyCode code, boolean isPressed) {
        touches.put(code, isPressed);
    }
}
