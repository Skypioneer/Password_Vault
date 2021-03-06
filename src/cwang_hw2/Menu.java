package cwang_hw2;
 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Reference: <a href="https://www.java-forums.org/new-java/26584-menu-driven-console-help-please.html">A Simple Text-Based Menu System</a>
 * @author JosAH
 */
public class Menu extends MenuItem {
     
    private static final MenuItem quit = new MenuItem("quit", new Runnable() {
        public void run() {
            System.exit(0);
        }
    });
     
    private static final MenuItem back = new MenuItem("back");
 
    List<MenuItem> items;
 
    public Menu(String title, MenuItem ... items) { this(title, false, true, items); }
 
    public Menu(String title, boolean addBack, boolean addQuit, MenuItem ... items) {
        super(title);
        setExec(this);
 
        initialize(addBack, addQuit, items);
    }
     
    private void initialize(boolean addBack, boolean addQuit, MenuItem ... items) {
         
        this.items = new ArrayList<MenuItem>(Arrays.asList(items));
        if (addBack) this.items.add(back);
        if (addQuit) this.items.add(quit);
    }
     
    private void display() {
         
        int option = 0;
        System.out.println(getTitle() + ":");
        for (MenuItem item : items) {
            System.out.println((option++) + ": " + item.getTitle());
        }
        System.out.print("select option: ");
        System.out.flush();
    }
     
    private MenuItem prompt() throws IOException {
         
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
 
        while (true) {
 
            display();
         
            String line = br.readLine();
            try {
                int option= Integer.parseInt(line);
                if (option >= 0 && option < items.size())
                    return items.get(option);
            }
            catch (NumberFormatException e) { }
         
            System.out.println("not a valid menu option: "+line);
        } 
    }
     
    public void run() {
     
        try {
            for (MenuItem item = prompt(); item.isExec(); item = prompt())
                item.run();
        }
        catch (Throwable t) {
            t.printStackTrace(System.out);
        }
    }
}
