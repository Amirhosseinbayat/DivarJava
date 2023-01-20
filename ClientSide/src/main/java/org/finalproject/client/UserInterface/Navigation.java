package org.finalproject.client.UserInterface;

import java.util.Stack;

public class Navigation {

    public static Stack<UIScreen> backStack = new Stack<>();

    public static void navigateTo(UIScreen uiScreen) {
        backStack.push(uiScreen);
        uiScreen.startScreen();
    }

    public static void popNavigate(UIScreen uiScreen) {
        if (!backStack.isEmpty()) backStack.pop();
        navigateTo(uiScreen);
    }

    public static void clearRootNavigate(UIScreen uiScreen) {
        backStack.clear();
        navigateTo(uiScreen);
    }

    public static void popBackStack() {
        UIScreen currentScreen = backStack.pop();
        if (currentScreen == null) return;
        if (backStack.isEmpty()) {
            System.exit(0);
            return;
        }
        UIScreen previousScreen = backStack.pop();
        if (previousScreen == null) return;
        navigateTo(previousScreen);
    }


}
