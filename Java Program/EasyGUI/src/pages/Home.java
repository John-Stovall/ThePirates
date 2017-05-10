package pages;

import gui.*;
import main.User;

import java.util.ArrayList;

/**
 * Created by Robert on 5/10/17.
 *
 * This is the home page.
 */
public class Home extends GUIPage {

    public Home() {
        super("Home");
    }

    @Override
    public void build() {
        GUI.window.add(new GSpacer(40));
        GUI.window.add(new GSpacer(25));
        GUI.window.add(new GText("Projected Earnings:"));
        GUI.window.add(new GSpacer(25));
        ArrayList<double[]> data = new ArrayList<>();
        data.add(new double[] {5, 2});
        data.add(new double[] {10, 7});
        data.add(new double[] {8, 5});
        data.add(new double[] {1, 9});
        data.add(new double[] {4, 6});
        data.add(new double[] {1, 3});
        data.add(new double[] {5, 2});
        data.add(new double[] {10, 7});
        data.add(new double[] {8, 5});
        data.add(new double[] {1, 9});
        data.add(new double[] {4, 6});
        data.add(new double[] {1, 3});
        GUI.window.add(new GGraph(data));
        GUI.window.add(new GSpacer(25));
        GUI.window.add(new GText("Hello, " + User.getLoadedUser().getName() + "! Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec quis tortor id est facilisis sodales pulvinar congue lectus. Nullam suscipit vulputate ligula quis congue. Ut consectetur fringilla lacinia. Aenean in ornare magna, tristique lacinia est. Aenean at elit vehicula, tincidunt leo at, convallis tellus. Nam mollis, odio quis efficitur porttitor, ante mi tincidunt ligula, quis ornare mauris nunc sed quam. Donec molestie enim odio, id viverra risus convallis a. Praesent et mi mauris. Nam sagittis eu sapien non accumsan.", defaultFont));
        GUI.window.add(new GSpacer(25));
        GUI.window.add(new GSpacer(5));
        GUI.window.add(new GSpacer(5));
        GUI.window.add(menu);
    }

}