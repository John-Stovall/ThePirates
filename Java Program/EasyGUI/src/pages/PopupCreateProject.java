package pages;

import control.General;
import gui.*;
import project.InsulationProject;
import project.LightProject;
import user.UserManager;

import java.util.ArrayList;

/**
 * Created by Robert on 5/11/17.
 *
 * A generic page used to create a project.
 */
public class PopupCreateProject {

    /** The available types of projects. */
    private String[] projectTypes = {"Insulation", "Lights", "Refrigerator", "Washing Machine", "Dryer"};

    /** The starting project type. */
    private String type;

    /**
     * Create a project.
     *
     * @param type The project type clicked.
     */
    PopupCreateProject(String type) {
        ArrayList<GUIComponent> list = new ArrayList<>();

        this.type = type;
    }

    public GPopUp getPopUp() {

        ArrayList<GUIComponent> parts = new ArrayList<>();

        parts.add(new GText("Create A Project"));

        parts.add(new GSpacer(25));
        parts.add(new GText("Project Type:", Style.defaultFont));

        parts.add(new GSpacer(15));

        GDropdown dropdown = new GDropdown(projectTypes);
        dropdown.setSelection(type);
        parts.add(dropdown);

        parts.add(new GSpacer(15));
        parts.add(new GText("Project Name:", Style.defaultFont));
        parts.add(new GSpacer(15));

        int projectNum = UserManager.getLoadedUser().getMyProjects().size()
                + UserManager.getLoadedUser().getCompletedProject().size() + 1;

        while (!General.isProjectNameFree("Project " + projectNum)) {
            projectNum++;
        }
        GTextBox name = new GTextBox(40,"Project " + projectNum);
        parts.add(name);
        parts.add(new GSpacer(15));
        parts.add(new GButton(40, "Create Project", Style.defaultFont) {
            @Override
            public void clickAction() {
                if (name.getText().length() < 1) {
                    name.failed("A project name is required.");
                } else if (name.getText().length() > 15) {
                    name.failed("Project names can be at most 15 characters.");
                } else if (UserManager.getLoadedUser().getMyProjects().size() >= 10) {
                    name.failed("Sorry, you can have at most 10 projects. Either delete a project or complete one to make more room.");
                } else if (!General.isProjectNameFree(name.getText().trim())) {
                    name.failed("That project name is already taken.");
                } else {
                    //TODO: Program this to do stuff.

                    switch (dropdown.getSelection()) {
                        case ("Insulation"):
                            InsulationProject project = new InsulationProject(name.getText().trim());
                            UserManager.getLoadedUser().getMyProjects().add(project);
                            UserManager.save();
                            GUI.window.gotoPage(project.getEditPage());
                            GUI.getPopUp().destroy();
                            break;

                        case ("Lights"):
                            LightProject projectL = new LightProject(name.getText().trim());
                            UserManager.getLoadedUser().getMyProjects().add(projectL);
                            UserManager.save();
                            GUI.window.gotoPage(projectL.getEditPage());
                            GUI.getPopUp().destroy();
                            break;
                        default:
                            name.failed("That project is unavailable. Please purchase DIY App Pro for $2.99");
                    }

                }
            }
        });
        parts.add(new GSpacer(25));
        return new GPopUp(parts);
    }

}
