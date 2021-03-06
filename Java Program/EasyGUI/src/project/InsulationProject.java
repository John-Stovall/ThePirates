package project;

import control.General;
import gui.*;
import user.UserManager;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Ryan Hansen
 * @author Robert Cordingly
 * @author Reagan Stovall
 * @author Gary Reysa
 *         <p>
 *         Insulation Project class.
 *         Used for creating a new insulation project.
 *         <p>
 *         Savings calulcated by:
 *         <p>
 *         +---------------------------------------------------------+
 *         |                                                         |
 *         |  Solar Analysis Tools - Insulation Upgrade Calculator   |
 *         |                                                         |
 *         +---------------------------------------------------------+
 *         Copyright (C) 2006 Gary Reysa (gary@BuildItSolar.com)
 *         <p>
 *         This program is free software; you can redistribute it and/or modify it
 *         under the terms of the GNU General Public License as published by the Free
 *         Software Foundation; either version 2 of the License, or (at your option)
 *         any later version.
 *         <p>
 *         This program is distributed in the hope that it will be useful and encourage
 *         further development of solar tools, but   WITHOUT ANY WARRANTY; without even
 *         the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *         See the GNU General Public License   for more details.
 */
public class InsulationProject extends Project implements Serializable {

    /**
     * Default version ID for serialization
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
    private String gasValue = "Natural Gas";

    // Natural Gas:
    private double HeatingValueNG = 100000.0;  // BTU/therm
    private double FuelCostNG = 1.50;          // cost per therm in US dollars
    private double FuelInflationNG = 1.1;
    private double GHGperBTUNG = 0.00012;  // GHG per BTU for Natural Gas

    // Fuel Oil:
    private double FuelCostOil = 2.30;          // cost per gallon in US dollars

    // Propane:
    private double FuelCostPro = 2.10;          // cost per gallon in US dollars

    // Electricity:
    private double FuelCostElec = 0.12;          // cost per KWH in US dollars


    private String FuelType = "ng";
    private double HeatingValue = HeatingValueNG;
    private double FuelCost = FuelCostNG;
    private double GHGperBTU = GHGperBTUNG;
    private double FuelInflation = FuelInflationNG;

    /**
     * Creates insulation project.
     *
     * @param name The name of the project.
     * @author Ryan
     */
    public InsulationProject(final String name) {
        this.name = name;
        this.type = "Insulation";
    }

    @Override
    /**
     * This method get's the monthly savings of this insulation project.
     *
     * @author Reagan
     */
    public double getMonthlySavings() {
        double Efic = furnaceEff / 100.0;

        // Calculate the heat losses and fuel saving
        // Current and New heat loss:
        double Qcur, Qnew;
        Qcur = (wallArea * heatDegreeDays * 24.0) / (double) curRValue;
        Qnew = (wallArea * heatDegreeDays * 24.0) / (double) newRValue;
        // calc $ saving in fuel
        double Saving;
        Saving = ((Qcur - Qnew) / (HeatingValue * Efic)) * ppu;
        //10 year saving
        double Saving10Year = 0.0;
        double Infla = 1.0;
        double yrSaving;
        for (int i = 0; i < 10; i++) {
            yrSaving = Saving * Infla;
            Infla = Infla * FuelInflation;
            Saving10Year = Saving10Year + yrSaving;
        }

        return Saving / 12.0;
    }

    @Override
    public double getInitialCost() {
        return initialCost;
    }

    @Override
    /**
     * This method returns the Edit page GUIPage.
     *
     * @author Robert
     * @author Reagan
     */
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
                GTextBox newR = new GTextBox(40, newRValue + "", "Recommended R-Value depends on where you live. If you live in a hot area you would want a lower R-Value. If you live in a colder area you will want a higher R-Value.");
                GTextBox areaToBeUpgraded = new GTextBox(40, wallArea + "", "The surface area, in square feet, of the room where insulation is being upgraded.");
                GTextBox heatingDegreeDays = new GTextBox(40, heatDegreeDays + "", "Annual heating degree days depends on how often you use your heater.");
                GTextBox pricePerUnit = new GTextBox(40, ppu + "", "For natural gas and fuel oil use dollars per therm, for propane use dollars per gallon and for electricity enter dollars per KWH.");
                GTextBox furnaceEfficency = new GTextBox(40, furnaceEff + "", "How efficiently your heater turns energy into heat. On average, use 100 for electric heaters and 80 for others.");
                GTextBox insulationPrice = new GTextBox(40, initialCost + "", "The price of the new insulation and any costs of installation.");
                GDropdown gasType = new GDropdown(new String[]{"Natural Gas", "Fuel Oil", "Propane", "Electricity"}) {
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
                gasType.setSelection(gasValue);

                GUI.window.add(new GText("Area to be Upgraded:", Style.defaultFont));
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
                                currentR.failed("Current R-Value cannot be negative.");
                            } else if (curRValue > 400) {
                                good = false;
                                currentR.failed("Current R-Value cannot exceed 400.");
                            }
                        } catch (NumberFormatException e) {
                            currentR.failed("Current R-Value must be a number.");
                            good = false;
                        }

                        //newR
                        try {
                            newRValue = Integer.parseInt(newR.getText());
                            if (newRValue < 0) {
                                good = false;
                                newR.failed("New R-Value cannot be negative.");
                            } else if (newRValue > 400) {
                                good = false;
                                newR.failed("New R-Value cannot exceed 400.");
                            }
                        } catch (NumberFormatException e) {
                            newR.failed("New R-Value must be a number.");
                            good = false;
                        }

                        //areaToBeUpgraded
                        try {
                            wallArea = Double.parseDouble(areaToBeUpgraded.getText());
                            if (wallArea < 0) {
                                good = false;
                                areaToBeUpgraded.failed("Wall area cannot be negative.");
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
                                heatingDegreeDays.failed("Heating degree days cannot be negative.");
                            } else if (heatDegreeDays > 25000) {
                                good = false;
                                heatingDegreeDays.failed("Heating degree days cannot exceed 25000 unless you live on Mars in which case it can.");
                            }
                        } catch (NumberFormatException e) {
                            heatingDegreeDays.failed("Heating degree days must be a number.");
                            good = false;
                        }

                        //pricePerUnit;
                        try {
                            ppu = Double.parseDouble(pricePerUnit.getText());
                            if (ppu < 0.01) {
                                good = false;
                                pricePerUnit.failed("Price per unit cannot be negative.");
                            } else if (ppu > 999999) {
                                good = false;
                                pricePerUnit.failed("Damn Donald, Back at it again with the Giant Wall!");
                            }
                        } catch (NumberFormatException e) {
                            pricePerUnit.failed("Price per unit must be a number.");
                            good = false;
                        }


                        //furnaceEfficency
                        try {
                            furnaceEff = Double.parseDouble(furnaceEfficency.getText());
                            if (furnaceEff < 0) {
                                good = false;
                                furnaceEfficency.failed("Furnace efficiency cannot be negative.");
                            } else if (furnaceEff > 100) {
                                good = false;
                                furnaceEfficency.failed("Furnace efficiency cannot exceed 100%.");
                            }
                        } catch (NumberFormatException e) {
                            furnaceEfficency.failed("Furnace efficiency must be a number.");
                            good = false;
                        }

                        //insulationPrice
                        try {
                            initialCost = Double.parseDouble(insulationPrice.getText());
                            if (initialCost < 0) {
                                good = false;
                                insulationPrice.failed("Initial cost cannot be negative.");
                            }
                        } catch (NumberFormatException e) {
                            insulationPrice.failed("Initial cost must be a number.");
                            good = false;
                        }

                        if (good) {
                            gasValue = gasType.getSelection();
                            UserManager.save();
                            GUI.window.gotoPage(getSummaryPage());
                            GUI.getPopUp().destroy();
                        }
                    }
                });

                GUI.window.add(new GSpacer(40));
                GUI.window.add(new GText("Other Settings"));
                GUI.window.add(new GSpacer(25));

                //Code to rename a project.
                GUI.window.add(new GButton(40,"Rename Project", Style.defaultFont) {
                    @Override
                    public void clickAction() {

                        ArrayList<GUIComponent> parts = new ArrayList<>();
                        parts.add(new GText("Rename " + InsulationProject.this.getName()));
                        parts.add(new GSpacer(20));
                        GTextBox name = new GTextBox(40, InsulationProject.this.getName());
                        parts.add(name);
                        parts.add(new GSpacer(10));
                        GDivider div = new GDivider(1, 2);
                        div.add(new GButton(40,"Rename", Style.defaultFont, 16) {
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
                                    setName(name.getText().trim());
                                    UserManager.save();
                                    GUI.window.refresh();
                                    GUI.getPopUp().destroy();
                                    GUI.showPopUp(getEditPage());
                                }
                            }
                        });
                        div.add(new GButton(40, "Cancel", Style.defaultFont, 16) {
                            @Override
                            public void clickAction() {
                                GUI.getPopUp().destroy();
                                GUI.showPopUp(getEditPage());
                            }
                        });
                        parts.add(div);
                        GUI.getPopUp().destroy();
                        GUI.showPopUp(new GPopUp(parts));
                    }
                });
                GUI.window.add(new GSpacer(25));

                //Code to delete a project.
                GUI.window.add(new GButton(40, Style.redButtonColor, Style.redHoverColor, "Delete Project", Style.defaultFont) {
                    @Override
                    public void clickAction() {

                        ArrayList<GUIComponent> parts = new ArrayList<>();
                        parts.add(new GText("Warning!"));
                        parts.add(new GSpacer(20));
                        parts.add(new GText("Are you sure you want to delete this project? You will not be able to recover it.", Style.defaultFont));
                        parts.add(new GSpacer(20));
                        GDivider div = new GDivider(1, 2);
                        div.add(new GButton(40, Style.redButtonColor, Style.redHoverColor, "Delete", Style.defaultFont, 16) {
                            @Override
                            public void clickAction() {
                                UserManager.getLoadedUser().getMyProjects().remove(InsulationProject.this);
                                UserManager.save();
                                GUI.getPopUp().destroy();
                                GUI.window.gotoPage("Home");
                            }
                        });
                        div.add(new GButton(40, "Cancel", Style.defaultFont, 16) {
                            @Override
                            public void clickAction() {
                                GUI.getPopUp().destroy();
                                GUI.showPopUp(getEditPage());
                            }
                        });
                        parts.add(div);
                        GUI.getPopUp().destroy();
                        GUI.showPopUp(new GPopUp(parts));
                    }
                });
            }
        };
    }

    /**
     * This method is used to test if inputs are tested for properly.
     *
     * @param currentR The current R
     * @param newR The new R
     * @param Area Area to be upgrades
     * @param HDD Heating degree days
     * @param PPU Price per unit
     * @param EFF Efficiency
     * @param init Initial Price
     * @return If values are all good.
     * @author Reagan
     */
    public boolean testInsulation(String currentR, String newR, String Area, String HDD, String PPU, String EFF, String init) {
        boolean good = true;
        try {
            curRValue = Integer.parseInt(currentR);
            if (curRValue < 0) {
                good = false;
            } else if (curRValue > 400) {
                good = false;
            }
        } catch (NumberFormatException e) {
            good = false;
        }

        //newR
        try {
            newRValue = Integer.parseInt(newR);
            if (newRValue < 0) {
                good = false;
            } else if (newRValue > 400) {
                good = false;
            }
        } catch (NumberFormatException e) {
            good = false;
        }

        //areaToBeUpgraded
        try {
            wallArea = Double.parseDouble(Area);
            if (wallArea < 0) {
                good = false;
            } else if (wallArea > 999999) {
                good = false;
            }
        } catch (NumberFormatException e) {
            good = false;
        }

        //heatingDegreeDays
        try {
            heatDegreeDays = Integer.parseInt(HDD);
            if (heatDegreeDays < 0) {
                good = false;
            } else if (heatDegreeDays > 25000) {
                good = false;
            }
        } catch (NumberFormatException e) {
            good = false;
        }

        //pricePerUnit;
        try {
            ppu = Double.parseDouble(PPU);
            if (ppu < 0.01) {
                good = false;
            } else if (ppu > 999999) {
                good = false;
            }
        } catch (NumberFormatException e) {
            good = false;
        }


        //furnaceEfficency
        try {
            furnaceEff = Double.parseDouble(EFF);
            if (furnaceEff < 0) {
                good = false;
            } else if (furnaceEff > 100) {
                good = false;
            }
        } catch (NumberFormatException e) {
            good = false;
        }

        //insulationPrice
        try {
            initialCost = Double.parseDouble(init);
            if (initialCost < 0) {
                good = false;
            }
        } catch (NumberFormatException e) {
            good = false;
        }

        return good;
    }
}
