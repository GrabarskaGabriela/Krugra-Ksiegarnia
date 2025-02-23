package Library;

import javax.swing.*;

public class Router extends JFrame {
    private Route[] routes;

    public Router(Route[] routes) {
        this.routes = routes;
    }

    public void navigateTo(String path) throws Exception {
        for (Route route : routes) {
            if (route.getPath().equals(path)) {
                if (path.equals("displaybookshelfpage")) {
                    Display_bookshelf_page page = new Display_bookshelf_page(Database_connector.getAvailableBooks());
                    page.setVisible(true);
                } else if (path.equals("borrowedbookspage")) {
                    Borrowed_books_page page = new Borrowed_books_page();
                    page.setVisible(true);
                } else {
                    JFrame page = (JFrame) route.getActionClass().getConstructor().newInstance();
                    page.setVisible(true);
                }
                return;
            }
        }
        throw new Exception("Route not found: " + path);
    }
}