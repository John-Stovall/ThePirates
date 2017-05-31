package project;

import gui.*;
import pages.ProjectChooser;
import user.UserManager;

import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Ryan Hansen
 * @author Robert Cordingly
 *
 * Insulation Project class.
 * Used for creating a new insulation project.
 */
public class InsulationProject extends Project implements Serializable {

	/**
	 * default version ID for serialization
	 */
	private static final long serialVersionUID = -1950641840941379037L;

	// Class specific variable used for determining the insulating properties
	// of the materials being used.
	private int curRValue = 10;
	private int newRValue = 20;
	private double wallArea = 1000;
	private int heatDegreeDays = 5000;
	private double ppu = 1.5; //price per unit
	private double furnaceEff = 80;
	private String gasType = "Natural Gas";

	private String priceValue;

    // Natural Gas:
    private double HeatingValueNG = 100000.0;  // BTU/therm
    private double FuelCostNG = 1.50;          // cost per therm in US dollars
    private double FuelInflationNG = 1.1;
    private double GHGperBTUNG = 0.00012;  // GHG per BTU for Natural Gas

    // Fuel Oil:
    private double HeatingValueOil = 142000.0;  //BTU/gal
    private double FuelCostOil = 2.30;          // cost per gallon in US dollars
    private double FuelInflationOil = 1.1;
    private double GHGperBTUOil = 0.00014;  // GHG per BTU for Fuel Oil

    // Propane:
    private double HeatingValuePro = 92000.0;  // BTU/gal
    private double FuelCostPro = 2.10;          // cost per gallon in US dollars
    private double FuelInflationPro = 1.1;
    private double GHGperBTUPro = 0.000141;  // GHG per BTU per gallon of Propane

    // Electricity:
    private double HeatingValueElec = 3412.0;   // BTU per KWH
    private double FuelCostElec = 0.12;          // cost per KWH in US dollars
    private double FuelInflationElec = 1.1;
    private double GHGperBTUElec = 0.00059;  // GHG per BTU for electricity generated at a coal fired power station


    private String FuelType = "ng";
    private double HeatingValue = HeatingValueNG;
    private double FuelCost = FuelCostNG;
    private double GHGperBTU = GHGperBTUNG;
    private double FuelInflation = FuelInflationNG;

    // constructor sets the water usage to -1.0 because there is no way for a insulation project
	// to have an impact on water consumption. This will be beneficial for our 'Math' class.
	public InsulationProject(final String name) {
	    this.name = name;
    }

    // Change Fuel -- changes fuel type, cost and furnace efficiency per user input
    private void ChangeFuel(int i){

	    System.out.print("changedFuel");
        //gasType = new GDropdown(new String[] {"Natural Gas", "Fuel Oil", "Propane", "Electricity"});
        if (i == 0) {
            priceValue = "$'s per therm";
            FuelType = "ng";
            HeatingValue = HeatingValueNG;  // BTU/therm
            GHGperBTU = GHGperBTUNG;  // lbs GHG per BTU of NG
            ppu = FuelCostNG;
        }
        if (i == 1) {
            priceValue = "$'s per gallon";
            FuelType = "oil";
            HeatingValue = HeatingValueOil;  // BTU/gallon
            GHGperBTU = GHGperBTUOil;  // lbs GHG per BTU of oil
            ppu = FuelCostOil;
        }
        if (i == 2) {
            priceValue = "$'s per gallon";
            FuelType = "pro";
            HeatingValue = HeatingValuePro;  // BTU/gallon
            GHGperBTU = GHGperBTUPro;  // lbs of GHG per BTU of Propane
            ppu = FuelCostPro;
        }
        if (i == 3) {
            priceValue = "$'s per KWH";
            FuelType = "elec";
            HeatingValue = HeatingValueElec;  // BTU/KWH
            GHGperBTU = GHGperBTUElec;  // lbs of GHG per BTU of electricity from a coal power station
            ppu = FuelCostElec;
        }
    }

	@Override
    public double getMonthlySavings() {
	    //TODO: Program this to properly calculate the monthly savings.
        //"Natural Gas", "Fuel Oil", "Propane", "Electricity"
        //System.out.println("gasType: " + gasType);

        //if(gasType.equals("Natural Gas")) ChangeFuel(0);
        //else if(gasType.equals("Fuel Oil")) ChangeFuel(1);
        //else if(gasType.equals("Propane")) ChangeFuel(2);
        //else if(gasType.equals("Electricity")) ChangeFuel(3);

        System.out.println("furnaceEff: "+ furnaceEff);
        double Efic = furnaceEff/100.0;

        System.out.println("Current R " + curRValue);
        System.out.println("New R " + newRValue);
        System.out.println("Wall Area" + wallArea);
        System.out.println("HDD " + heatDegreeDays);

        // Calculate the heat losses and fuel saving
        // Current and New heat loss:
        double Qcur, Qnew;
        Qcur = (wallArea*heatDegreeDays*24.0)/(double)curRValue;
        Qnew = (wallArea*heatDegreeDays*24.0)/(double)newRValue;
        System.out.println("Qcur: " + Qcur);
        System.out.println("Qnew: " + Qnew);
        // calc $ saving in fuel
        double Saving;
        System.out.println("ppu: " + ppu);
        System.out.println("HeatingValue: " + HeatingValue);
        Saving = ((Qcur-Qnew)/(HeatingValue*Efic))*ppu;
        System.out.println("Saving:" + Saving);
        //10 year saving
        double Saving10Year = 0.0;
        double Infla = 1.0;
        double yrSaving;
        for(int i=0; i < 10 ;i++){
            yrSaving = Saving*Infla;
            Infla = Infla*FuelInflation;
            Saving10Year = Saving10Year + yrSaving;
        }

        // Calc greenhouse gas
        double GHgas;
        GHgas = (Qcur-Qnew)*GHGperBTU/Efic;

        // write outputs
        System.out.println("Savings: " + Saving);

        System.out.println("Savings: "+ Math.round(Saving*100)/100);
        System.out.println("10 year Savings: "+ Math.round(Saving10Year*100)/100);
        System.out.println("GHgas: "+ Math.round(GHgas));


	    return Saving / 12.0;
    }

    @Override
    public double getInitialCost() {
	    return initialCost;
    }

    @Override
    public GUIPage getSummaryPage() {
        return new GUIPage(name) {
            @Override
            public void build() {
                GUI.window.add(new GSpacer(40));
                GUI.window.add(new GSpacer(25));
                GUI.window.add(new GText(name + " Summary"));
                GUI.window.add(new GSpacer(25));ArrayList<double[]> data = new ArrayList<>();

                data.add(new double[] {0, 0});

                boolean payedFor = false;
                int payedDate = 0;
                double potential = -getInitialCost();
                for (int i = 0; i < 12; i++) {
                    potential += getMonthlySavings();
                    if (potential >= 0 && !payedFor) {
                        payedDate = i + 1;
                        payedFor = true;
                    }
                    data.add(new double[] {potential});
                }

                if (potential > 0) {
                    GUI.window.add(new GGraph(data));
                    GUI.window.add(new GSpacer(20));

                    GUI.window.add(new GText("Details:"));
                    GUI.window.add(new GSpacer(20));
                    GUI.window.add(new GText("Nice! After one year this project will save you $" + Math.round(potential) + ".", Style.defaultFont));

                    if (payedDate != 1) {
                        GUI.window.add(new GSpacer(10));
                        GUI.window.add(new GText("This project will pay for itself in " + payedDate + " months.", Style.defaultFont));
                    }
                } else {
                    GUI.window.add(new GText("Oh no! This project won't save you any money this year. Try reducing the initial costs and change other settings to make the project more profitable.", Style.defaultFont));
                }

                GUI.window.add(new GSpacer(20));

                GDivider buttons = new GDivider(240, 3);
                if (UserManager.getLoadedUser().getMyProjects().contains(InsulationProject.this)) {

                    GDivider innerDiv1 = new GDivider(240, 1);
                    GDivider innerDiv2 = new GDivider(240, 1);
                    GDivider innerDiv3 = new GDivider(240, 1);

                    innerDiv1.add(new GButton(40, "Edit Project", Style.defaultFont, 8) {
                        @Override
                        public void clickAction() {
                            GUI.window.gotoPage(getEditPage());
                        }
                    });
                    innerDiv1.add(new GSpacer(10));
                    buttons.add(innerDiv1);
                    if (UserManager.getLoadedUser().getMyProjects().size() > 1) {
                        innerDiv3.add(new GButton(40, "Compare To...", Style.defaultFont, 8) {
                            @Override
                            public void clickAction() {
                                GUI.window.gotoPage(new ProjectChooser(InsulationProject.this));
                            }
                        });
                        innerDiv3.add(new GSpacer(10));
                        buttons.add(innerDiv3);
                    }
                    innerDiv2.add(new GButton(40, "Completed", Style.defaultFont, 8) {
                        @Override
                        public void clickAction() {
                            UserManager.getLoadedUser().projectComplete(InsulationProject.this);
                            GUI.window.gotoPage("Home");
                        }
                    });
                    innerDiv2.add(new GSpacer(10));
                    buttons.add(innerDiv2);


                } else {
                    GUI.window.add(new GButton(40, "Resume Project", Style.defaultFont, 8) {
                        @Override
                        public void clickAction() {
                            UserManager.getLoadedUser().projectResume(InsulationProject.this);
                            GUI.window.gotoPage(getSummaryPage());
                        }
                    });
                }
                GUI.window.add(buttons);
                GUI.window.add(new GSpacer(40));
                GUI.window.showMenu();
            }
        };
    }

    @Override
    public GUIPage getEditPage() {
        return new GUIPage("Edit " + name) {
            @Override
            public void build() {
                GUI.window.add(new GSpacer(40));
                GUI.window.add(new GSpacer(25));
                GUI.window.add(new GText("Edit " + name));
                GUI.window.add(new GSpacer(25));

                //Create all of the text boxes and drop down explanations for this page.
                GTextBox currentR = new GTextBox(40, curRValue + "", "R-Value determines how well the insulation resists heat flow.");
                GTextBox newR = new GTextBox(40, newRValue +"", "Recommended R-Value depends on where you live. If you live in a hot area you would want a lower R-Value around 2-3. If you live in a colder area you will want a higher R-Value around 5-6.");
                GTextBox areaToBeUpgraded = new GTextBox(40,  wallArea + "", "The surface area, in square feet, of the room where insulation is being upgraded.");
                GTextBox heatingDegreeDays = new GTextBox(40, heatDegreeDays + "",
                        "Anual Heating degree days depends on how much often you use your heater.");
                GTextBox pricePerUnit = new GTextBox(40, ppu + "", "For natural gas and fuel oil use dollars per therm, for propane use dollars per gallon and for electricity enter dollars per KWH.");
                GTextBox furnaceEfficency = new GTextBox(40, furnaceEff + "", "How efficiently your heater turns energy into heat. On average, use 100 for electric heaters and 80 for others.");
                GTextBox insulationPrice = new GTextBox(40, initialCost + "", "The price of the new insulation and any costs of installation.");
                GDropdown gasType = new GDropdown(new String[] {"Natural Gas", "Fuel Oil", "Propane", "Electricity"}) {
                    @Override
                    public void clickAction() {
                        switch (this.getSelection()) {
                            case ("Natural Gas"):
                                furnaceEff = 80;
                                ppu = FuelCostNG;
                                break;
                            case ("Fuel Oil"):
                                furnaceEff = 80;
                                ppu = FuelCostOil;
                                break;
                            case ("Propane"):
                                furnaceEff = 80;
                                ppu = FuelCostPro;
                                break;
                            case ("Electricity"):
                                furnaceEff = 100;
                                ppu = FuelCostElec;
                                break;
                        }
                        pricePerUnit.setValue(ppu + "");
                        furnaceEfficency.setValue(furnaceEff + "");
                    }
                };
                //TODO: Action listener that calls ChangeFuel(String action)

                GUI.window.add(new GText("Area to be upgraded:", Style.defaultFont));
                GUI.window.add(new GSpacer(5));
                GUI.window.add((GUIComponent) areaToBeUpgraded);

                GUI.window.add(new GSpacer(5));
                GUI.window.add(new GText("Heating Degree Days:", Style.defaultFont));
                GUI.window.add(new GSpacer(5));
                GUI.window.add((GUIComponent) heatingDegreeDays);

                GUI.window.add(new GSpacer(5));
                GUI.window.add(new GSpacer(5));
                GUI.window.add(new GText("Current Insulation R-Value:", Style.defaultFont));
                GUI.window.add(new GSpacer(5));
                GUI.window.add((GUIComponent) currentR);

                GUI.window.add(new GSpacer(5));
                GUI.window.add(new GText("New Insulation R-Value:", Style.defaultFont));
                GUI.window.add(new GSpacer(5));
                GUI.window.add((GUIComponent) newR);

                GUI.window.add(new GSpacer(5));
                GUI.window.add(new GText("Fuel Type:", Style.defaultFont));
                GUI.window.add(new GSpacer(5));
                GUI.window.add(gasType);



                //gasType.mouseReleased(ChangeFuel(gasType.getSelection()));

                GUI.window.add(new GSpacer(20));
                GUI.window.add(new GText("Fuel Price", Style.defaultFont));
                GUI.window.add(new GSpacer(5));
                GUI.window.add((GUIComponent) pricePerUnit);

                GUI.window.add(new GSpacer(5));
                GUI.window.add(new GText("Furnace Efficiency Percentage:", Style.defaultFont));
                GUI.window.add(new GSpacer(5));
                GUI.window.add((GUIComponent) furnaceEfficency);

                GUI.window.add(new GSpacer(5));
                GUI.window.add(new GText("Initial Costs:", Style.defaultFont));
                GUI.window.add(new GSpacer(5));
                GUI.window.add((GUIComponent) insulationPrice);
                GUI.window.add(new GSpacer(5));


                //Program what the button does when it is clicked.
                GUI.window.add(new GButton(40, "Save Changes", Style.defaultFont, 8) {
                    @Override
                    public void clickAction() {
                        boolean good = true;

                        //currentR
                        try {
                            curRValue = Integer.parseInt(currentR.getText());
                            if (curRValue < 0) {
                                good = false;
                                currentR.failed("Current R value cannot be negative.");
                            } else if (curRValue > 400) {
                                good = false;
                                currentR.failed("Current R cannot exceed 400.");
                            }
                        } catch (NumberFormatException e) {
                            currentR.failed("Current R value must be a number.");
                            good = false;
                        }

                        //newR
                        try {
                            newRValue = Integer.parseInt(newR.getText());
                            if (newRValue < 0) {
                                good = false;
                                newR.failed("New R value cannot be negative.");
                            } else if (newRValue > 400) {
                                good = false;
                                newR.failed("New R cannot exceed 400.");
                            }
                        } catch (NumberFormatException e) {
                            newR.failed("New R value must be a number.");
                            good = false;
                        }

                        //areaToBeUpgraded
                        try {
                            wallArea = Double.parseDouble(areaToBeUpgraded.getText());
                            if (wallArea < 0) {
                                good = false;
                                areaToBeUpgraded.failed("Wall Area cannot be negative.");
                            } else if (wallArea > 999999) {
                                good = false;
                                areaToBeUpgraded.failed("Damn Donald, Back at it again with the Giant Wall!");
                            }
                        } catch (NumberFormatException e) {
                            areaToBeUpgraded.failed("Wall Area must be a number.");
                            good = false;
                        }

                        //heatingDegreeDays
                        try {
                            heatDegreeDays = Integer.parseInt(heatingDegreeDays.getText());
                            if (heatDegreeDays < 0) {
                                good = false;
                                heatingDegreeDays.failed("Heating Degree Days cannot be negative.");
                            } else if (heatDegreeDays > 25000){
                                good = false;
                                heatingDegreeDays.failed("Heating Days can't exceed 25000 unless you live on Mars in which case it can.");
                            }
                        } catch (NumberFormatException e) {
                            heatingDegreeDays.failed("heating Degree Days must be a number.");
                            good = false;
                        }

                        //pricePerUnit;
                        try {
                            ppu = Double.parseDouble(pricePerUnit.getText());
                            if (ppu < 0.01) {
                                good = false;
                                pricePerUnit.failed("Price Per Unit cannot be negative.");
                            } else if (ppu > 999999) {
                                good = false;
                                pricePerUnit.failed("Damn Donald, Back at it again with the Giant Wall!");
                            }
                        } catch (NumberFormatException e) {
                            pricePerUnit.failed("Price Per Unit must be a number.");
                            good = false;
                        }


                        //furnaceEfficency
                        try {
                            furnaceEff = Double.parseDouble(furnaceEfficency.getText());
                            if (furnaceEff < 0) {
                                good = false;
                                furnaceEfficency.failed("Furnace Efficiency cannot be negative.");
                            } else if (furnaceEff > 100) {
                                good = false;
                                furnaceEfficency.failed("Furnace Efficiency cannot cannont exceed 100%.");
                            }
                        } catch (NumberFormatException e) {
                            furnaceEfficency.failed("Furnace Efficiency must be a number.");
                            good = false;
                        }

                        //insulationPrice
                        try {
                            initialCost = Double.parseDouble(insulationPrice.getText());
                            if (initialCost < 0) {
                                good = false;
                                insulationPrice.failed("Insulation Price cost cannot be negative.");
                            }
                        } catch (NumberFormatException e) {
                            insulationPrice.failed("Initial cost must be a number.");
                            good = false;
                        }

                        if (good) {
                            System.out.println("gasType Selection; " + gasType.getSelection());
                            GUI.window.gotoPage(getSummaryPage());
                        }
                    }
                });

                GUI.window.add(new GSpacer(40));
                GUI.window.add(new GText("Other Settings"));
                GUI.window.add(new GSpacer(25));

                GUI.window.add(new GButton(40, Style.redButtonColor, Style.redHoverColor, "Delete Project", Style.defaultFont) {
                    @Override
                    public void clickAction() {
                        UserManager.getLoadedUser().getMyProjects().remove(InsulationProject.this);
                        GUI.window.gotoPage("Home");
                    }
                });
                GUI.window.add(new GSpacer(40));
                GUI.window.showMenu();
            }
        };
    }
}
