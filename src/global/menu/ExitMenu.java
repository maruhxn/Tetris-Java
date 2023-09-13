package global.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExitMenu extends Menu {

    public ExitMenu(String text) {
        super(text);
    }

    @Override
    public void setActionListener() {
        super.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
}
