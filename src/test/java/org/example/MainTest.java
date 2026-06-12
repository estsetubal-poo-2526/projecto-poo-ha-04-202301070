package org.example;

import javafx.application.Application;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MainTest {
    @Test
    void mainIsJavaFxApplicationEntryPoint() {
        assertTrue(Application.class.isAssignableFrom(Main.class));
        assertDoesNotThrow(Main::new);
    }
}
