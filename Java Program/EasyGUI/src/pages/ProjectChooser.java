package pages;

import gui.*;
import project.InsulationProject;
import project.Project;
import user.UserManager;

/**
 * Created by Robert on 5/25/17.
 *
 * A page used to select another project.
 */
public class ProjectChooser extends GUIPage {

    /**
     * The first project to compare to.
     */
    private Project project;

    /**
     * Create a project chooser page.
     *
     * @param p The first selected page.
     * @author Robert
     */
    public ProjectChooser(Project p) {
        super("Choose");
        this.project = p;
    }

    @Override
    public void build() {
        GUI.window.add(new GText("Compare To..."));
        GUI.window.add(new GSpacer(32));
        for (Project p : UserManager.getLoadedUser().getMyProjects()) {
            if (p != project) {
                GUI.window.add(new GButton(40, p.getName(), Style.defaultFont) {
                    @Override
                    public void clickAction() {
                        //GUI.window.gotoPage(new CompareProject(project, p));
                        GUI.getPopUp().destroy();
                        GUI.showPopUp(new CompareProject(project, p));
                    }
                });
                GUI.window.add(new GSpacer(10));
            }
        }
        GUI.window.add(new GSpacer(-10));
    }
}
