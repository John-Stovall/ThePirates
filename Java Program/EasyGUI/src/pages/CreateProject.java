package pages;

import gui.Style;
import gui.*;
import project.InsulationProject;
import project.LightProject;
import user.UserManager;

/**
 * Created by Robert on 5/11/17.
 *
 * A generic page used to create a project.
 */
public class CreateProject extends GUIPage {

    /** The available types of projects. */
    private String[] projectTypes = {"Insulation", "Lights", "Refrigerator", "Washing Machine", "Dryer"};

    /** The starting project type. */
    private String type;

    /**
     * Create a project.
     *
     * @param type The project type clicked.
     */
    CreateProject(String type) {
        super("Create");
        this.type = type;
    }

    @Override
    public void build() {
        GUI.window.add(new GSpacer(65));
        GUI.window.add(new GText("Create A Project"));

        GUI.window.add(new GSpacer(25));
        GUI.window.add(new GText("Project Type:", Style.defaultFont));

        GUI.window.add(new GSpacer(15));

        GDropdown dropdown = new GDropdown(projectTypes);
        dropdown.setSelection(type);
        GUI.window.add(dropdown);

        GUI.window.add(new GSpacer(15));
        GUI.window.add(new GText("Project Name:", Style.defaultFont));
        GUI.window.add(new GSpacer(15));

        int projectNum = UserManager.getLoadedUser().getMyProjects().size()
                + UserManager.getLoadedUser().getCompletedProject().size() + 1;

        GTextBox name = new GTextBox(40,"Project " + projectNum);
        GUI.window.add((GUIComponent) name);
        GUI.window.add(new GSpacer(15));
        GUI.window.add(new GButton(40, "Create Project", Style.defaultFont) {
            @Override
            public void clickAction() {
                if (name.getText().length() < 1) {
                    name.failed("A project name is required.");
                } else if (name.getText().length() > 15) {
                    name.failed("Project names can be at most 15 characters.");
                } else {
                    //TODO: Program this to do stuff.

                    switch (dropdown.getSelection()) {
                        case ("Insulation"):
                            InsulationProject project = new InsulationProject(name.getText());
                            UserManager.getLoadedUser().getMyProjects().add(project);
                            UserManager.save();
                            GUI.window.gotoPage(project.getEditPage());
                            break;

                        case ("Lights"):
                            LightProject projectL = new LightProject(name.getText());
                            UserManager.getLoadedUser().getMyProjects().add(projectL);
                            UserManager.save();
                            GUI.window.gotoPage(projectL.getEditPage());
                            break;
                        default:
                            name.failed("That project is unavailable. Please purchase DIY App Pro for $2.99");
                    }

                }
            }
        });
        GUI.window.add(new GSpacer(25));
        GUI.window.showMenu();
    }

}
