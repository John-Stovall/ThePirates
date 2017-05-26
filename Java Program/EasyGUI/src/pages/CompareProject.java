package pages;

import gui.*;
import project.Project;
import user.UserManager;

import java.util.ArrayList;

/**
 * Created by Robert on 5/25/17.
 *
 * This page compares two project
 */
public class CompareProject extends GUIPage {

    /** The first project to compare. */
    private Project project1;

    /** The second project to compare. */
    private Project project2;

    /**
     * This creates a page that is used to compare two projects.
     *
     * @param p1 The first project to compare.
     * @param p2 The second project to compare.
     */
    public CompareProject(Project p1, Project p2) {
        super("Compare");
        project1 = p1;
        project2 = p2;
    }

    @Override
    public void build() {
        GUI.window.add(new GSpacer(40));
        GUI.window.add(new GSpacer(25));
        GUI.window.add(new GText(project1.getName() + " vs. " + project2.getName()));
        GUI.window.add(new GSpacer(25));

        ArrayList<double[]> data = new ArrayList<>();

        data.add(new double[] {0, 0});

        double potential = -project1.getInitialCost(), completed = -project2.getInitialCost();

        for (int i = 0; i < 12; i++) {
            potential += project1.getMonthlySavings();
            completed += project2.getMonthlySavings();
            data.add(new double[] {potential, completed});
        }

        GUI.window.add(new GGraph(data));
        GUI.window.add(new GSpacer(10));

        GDivider rule = new GDivider(160, 2);
        rule.add(new GText("*" + project1.getName(), Style.graphTicks));
        rule.add(new GText("*" + project2.getName(), Style.graphTicks, Style.redButtonColor));
        GUI.window.add(rule);

        GUI.window.add(new GSpacer(20));
        GUI.window.add(new GText("Tips:"));
        GUI.window.add(new GSpacer(10));

        if (potential > completed) {
            if (project1.getMonthlySavings() > project2.getMonthlySavings()) {
                GUI.window.add(new GText(project1.getName() + " will make you more money in one year, and makes more money per month" +
                        " than " + project2.getName() + ". " + project1.getName() + " is the obvious choice.", Style.defaultFont));
            } else if (project1.getMonthlySavings() == project2.getMonthlySavings()) {
                GUI.window.add(new GText(project1.getName() + " will make you more money in one year" +
                        " than " + project2.getName() + ". " + project1.getName() + " is the obvious choice.", Style.defaultFont));
            } else {
                    GUI.window.add(new GText("In one year, " + project1.getName() + " will save you more money. " +
                            "Although, " + project2.getName() + " earns you $" + (project2.getMonthlySavings() - project1.getMonthlySavings()) +
                            " more every month. If time is not a issue, " + project2.getName() + " will make you more money in the long run.",  Style.defaultFont));
            }
        } else if (potential == completed) {
            GUI.window.add(new GText("In one year, both of these projects will make the same amount of money.", Style.defaultFont));
            if (project1.getMonthlySavings() > project2.getMonthlySavings()) {
                GUI.window.add(new GText("Although, " + project1.getName() + " makes more money per month. In the long run, it is the best choice.", Style.defaultFont));
            } else if (project1.getMonthlySavings() != project2.getMonthlySavings()) {
                GUI.window.add(new GText("Although, " + project2.getName() + " makes more money per month. In the long run, it is the best choice.", Style.defaultFont));
            }
        } else {
            if (project2.getMonthlySavings() > project1.getMonthlySavings()) {
                GUI.window.add(new GText(project2.getName() + " will make you more money in one year, and makes more money per month" +
                        " than " + project1.getName() + ". " + project2.getName() + " is the obvious choice.", Style.defaultFont));
            } else if (project2.getMonthlySavings() == project1.getMonthlySavings()) {
                GUI.window.add(new GText(project2.getName() + " will make you more money in one year" +
                        " than " + project1.getName() + ". " + project2.getName() + " is the obvious choice.", Style.defaultFont));
            } else {
                GUI.window.add(new GText("In one year, " + project2.getName() + " will save you more money. " +
                        "Although, " + project1.getName() + " earns you $" + (project1.getMonthlySavings() - project2.getMonthlySavings()) +
                        " more every month. If time is not a issue, " + project1.getName() + " will make you more money in the long run.",  Style.defaultFont));
            }
        }


        GUI.window.add(new GSpacer(25));
        GUI.window.showMenu();
    }
}
