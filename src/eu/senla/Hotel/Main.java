// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com

package eu.senla.hotel;

import eu.senla.hotel.ui.menu.MenuController;

public class Main {
    public static void main(String[] args) {
        MenuController.getInstance().run();
    }
}