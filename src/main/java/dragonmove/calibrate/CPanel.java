package dragonmove.calibrate;

import com.googlecode.lanterna.gui2.Component;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;

public class CPanel extends Panel{

    int count=0;

    public CPanel(){
        super();
    }

    public CPanel inComponent(Component component){
        addComponent(count,component);
        count++;
        return this;
    }

    public CPanel inSpace(){
        addSpace(count);
        count++;
        return this;
    }

    public CPanel inText(String text){
        addComponent(count,text);
        count++;
        return this;
    }

    public CPanel addComponent(int index ,String text){
        addComponent(index,new Label(text));
        return this;
    }

    public CPanel addSpace(int index){
        addComponent(index,new EmptySpace());
        return this;
    }

    public CPanel addComponent(int index, Component component){
        super.addComponent(index,component);
        return this;
    }

    public CPanel addComponent(Component component){
        super.addComponent(component);
        return this;
    }
}
