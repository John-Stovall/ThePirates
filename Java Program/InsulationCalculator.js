

// +---------------------------------------------------------+
// |                                                         |
// |  Solar Analysis Tools - Insulation Upgrade Calculator   |
// |                                                         |
// +---------------------------------------------------------+
//  Copyright (C) 2006 Gary Reysa (gary@BuildItSolar.com)
//
//  This program is free software; you can redistribute it and/or modify it
//  under the terms of the GNU General Public License as published by the Free
//  Software Foundation; either version 2 of the License, or (at your option)
//  any later version.
//
//  This program is distributed in the hope that it will be useful and encourage
//  further development of solar tools, but   WITHOUT ANY WARRANTY; without even 
//  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
//  See the GNU General Public License   for more details.



// The things that might need updating over time are here:
// Natural Gas:
var HeatingValueNG = 100000.0;  // BTU/therm
var FuelCostNG = 1.50;          // cost per therm in US dollars
var FuelInflationNG = 1.1;
var GHGperBTUNG = 0.00012;  // GHG per BTU for Natural Gas
var FurnaceEficNG = 80.0;     // Efficiency of typical NG furnace

// Fuel Oil:
var HeatingValueOil = 142000.0;  //BTU/gal
var FuelCostOil = 2.30;          // cost per gallon in US dollars
var FuelInflationOil = 1.1;
var GHGperBTUOil = 0.00014;  // GHG per BTU for Fuel Oil
var FurnaceEficOil = 80.0;     // Efficiency of typical Oil furnace

// Propane:
var HeatingValuePro = 92000.0;  // BTU/gal
var FuelCostPro = 2.10;          // cost per gallon in US dollars
var FuelInflationPro = 1.1;
var GHGperBTUPro = 0.000141;  // GHG per BTU per gallon of Propane
var FurnaceEficPro = 80.0;     // Efficiency of typical Propane furnace

// Electricity:
var HeatingValueElec = 3412.0;   // BTU per KWH
var FuelCostElec = 0.12;          // cost per KWH in US dollars
var FuelInflationElec = 1.1;
var GHGperBTUElec = 0.00059;  // GHG per BTU for electricity generated at a coal fired power station
var FurnaceEficElec = 100.0;     // Efficiency of typical Elec heater/furnace


var FuelType = "ng";
var HeatingValue = HeatingValueNG;
var FuelCost = FuelCostNG;
var Efic = FurnaceEficNG;
var GHGperBTU = GHGperBTUNG;
var FuelInflation = FuelInflationNG;


function Calc() 
{

// Validate inputs
	if (document.InsulForm.AreaTbx.value.length == 0 || isNaN(document.InsulForm.AreaTbx.value))
		{
		alert("A numeric value for Area is required");
		document.InsulForm.AreaTbx.focus();
		return false;
		}
	if (document.InsulForm.HDDTbx.value.length == 0 || isNaN(document.InsulForm.HDDTbx.value))
		{
		alert("A numeric value for Heating Degree Days is required");
		document.InsulForm.HDDTbx.focus();
		return false;
		}
	if (document.InsulForm.CurRTbx.value.length == 0 || isNaN(document.InsulForm.CurRTbx.value))
		{
		alert("A numeric value of 1 or more is required for the current R value");
		document.InsulForm.CurRTbx.focus();
		return false;
		}
	if (document.InsulForm.NewRTbx.value.length == 0 || isNaN(document.InsulForm.NewRTbx.value))
		{
		alert("A numeric value of 1 or more is required for the upgraded R value");
		document.InsulForm.NewRTbx.focus();
		return false;
		}
	if (document.InsulForm.FuelCostTbx.value.length == 0 || isNaN(document.InsulForm.FuelCostTbx.value))
		{
		alert("A numeric value for fuel cost is required ");
		document.InsulForm.FuelCostTbx.focus();
		return false;
		}
	if (document.InsulForm.EficTbx.value.length == 0 || isNaN(document.InsulForm.EficTbx.value))
		{
		alert("A numeric value between 1 and 100 is required for the Furnace Efficienty");
		document.InsulForm.NewRTbx.focus();
		return false;
		}
		
	// read inputs and convert to numbers
	var Area = parseFloat(document.InsulForm.AreaTbx.value);
	var HDD  = parseFloat(document.InsulForm.HDDTbx.value);
	var CurR = parseFloat(document.InsulForm.CurRTbx.value);
	var NewR = parseFloat(document.InsulForm.NewRTbx.value);
	FuelCost = parseFloat(document.InsulForm.FuelCostTbx.value);
	Efic = parseFloat(document.InsulForm.EficTbx.value);
	Efic = Efic/100.0;
	
	// Do some further checking
	if (Area < 1.0 || Area > 20000){
		alert("Area must be between 1 and 20,000")
		document.InsulForm.AreaTbx.focus();
	}
	if (CurR < 0.9 || CurR > 400) {
		alert("Current R value must be between 0.9 and 400")
		document.InsulForm.CurRTbx.focus();
	}
	if (HDD < 10 || HDD > 25000) {
		alert("HDD value must be between 10 and 25000")
		document.InsulForm.CurRTbx.focus();
	}
	if (NewR < CurR || NewR > 400) {
		alert("Upgraded R value must be greater than Current R value and less than 400")
		document.InsulForm.NewRTbx.focus();
	}
	if (FuelCost < 0.01 || FuelCost > 1000) {
		alert("Fuel Cost must be breater than 0.01 and less than 1000")
		document.InsulForm.FuelCostTbx.focus();
	}
	if (Efic < 0.10 || Efic > 1.00) {
		alert("Efficiency must be between 10% and 100%")
		document.InsulForm.EficTbx.focus();
	}
	
	// Calculate the heat losses and fuel saving
	// Current and New heat loss:
	var Qcur, Qnew;
	Qcur = Area*HDD*24.0/CurR;
	Qnew = Area*HDD*24.0/NewR;
		
	// calc $ saving in fuel
	var Saving;
	Saving = ((Qcur-Qnew)/(HeatingValue*Efic))*FuelCost;
	//10 year saving
	Saving10Year = 0.0;
	var Infla = 1.0;
	var yrSaving;
	for(i=0; i<10;i++){
		yrSaving = Saving*Infla;
		Infla = Infla*FuelInflation;
		Saving10Year = Saving10Year + yrSaving;
	}		
	
	// Calc greenhouse gas 
	var GHgas;
	GHgas = (Qcur-Qnew)*GHGperBTU/Efic;

	// write outputs
	document.InsulForm.Savings1yrLab.value = Math.round(Saving*100)/100;
	document.InsulForm.Savings10yrLab.value = Math.round(Saving10Year*100)/100;
	document.InsulForm.GreenHouseGasLab.value = Math.round(GHgas);
} // Calc

// Change Fuel -- changes fuel type, cost and furnace efficiency per user input
function ChangeFuel(){

	for (i=0; i < 4; i++)
	{
		if (document.InsulForm.FuelCbx[i].checked)
		{
			if (i == 0) {
				document.InsulForm.FuelCostTbx.value = FuelCostNG;
				document.InsulForm.FuelCostLab.value = "$'s per therm";
				document.InsulForm.EficTbx.value = FurnaceEficNG;
				FuelType = "ng";
				HeatingValue = HeatingValueNG;  // BTU/therm
				GHGperBTU = GHGperBTUNG;  // lbs GHG per BTU of NG
			}
			if (i == 1) {
				document.InsulForm.FuelCostTbx.value = FuelCostOil;
				document.InsulForm.FuelCostLab.value = "$'s per gallon";
				document.InsulForm.EficTbx.value = FurnaceEficOil;
				FuelType = "oil";
				HeatingValue = HeatingValueOil;  // BTU/gallon
				GHGperBTU = GHGperBTUOil;  // lbs GHG per BTU of oil
			}
			if (i == 2) {
				document.InsulForm.FuelCostTbx.value = FuelCostPro;
				document.InsulForm.FuelCostLab.value = "$'s per gallon";
				document.InsulForm.EficTbx.value = FurnaceEficPro;
				FuelType = "pro";
				HeatingValue = HeatingValuePro;  // BTU/gallon
				GHGperBTU = GHGperBTUPro;  // lbs of GHG per BTU of Propane
			}
			if (i == 3) {
				document.InsulForm.FuelCostTbx.value = FuelCostElec;
				document.InsulForm.FuelCostLab.value = "$'s per KWH";
				document.InsulForm.EficTbx.value = FurnaceEficElec;
				FuelType = "elec";
				HeatingValue = HeatingValueElec;  // BTU/KWH
				GHGperBTU = GHGperBTUElec;  // lbs of GHG per BTU of electricity from a coal power station
			}
		} //if
		
	} // for

} // Change Fuel
