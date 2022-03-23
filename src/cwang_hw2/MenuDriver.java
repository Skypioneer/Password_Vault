package cwang_hw2;

/**
 * Reference: <a href="https://www.java-forums.org/new-java/26584-menu-driven-console-help-please.html">A Simple Text-Based Menu System</a>
 * @author JosAH
 */
public class MenuDriver {
    
    private static MenuItem item1 = new MenuItem("item 1", new Runnable() {
        public void run() {
            System.out.println("running item 1");
        }
    });
     
    private static MenuItem item2 = new MenuItem("item 2", new Runnable() {
        public void run() {
            System.out.println("running item 2");
        }
    });
 
    private static MenuItem item3 = new MenuItem("item 3", new Runnable() {
        public void run() {
            System.out.println("running item 3");
        }
    });
 
    private static Menu nestedMenu = new Menu("nested menu", true, false, item2, item3);
    private static Menu topMenu = new Menu("top menu", false, true, item1, nestedMenu);
 
    public static void main(String[] args) {
         
        topMenu.run();
    }
}
