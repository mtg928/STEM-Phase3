/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.testgraph;

/**
 *
 * @author Murali
 */
public class JComponentCell {
    
private JcomponentType cellType;
private String label = "";

public JComponentCell(JcomponentType type, String label)
{
this.label = label;
this.cellType = type;
}

public JcomponentType getType()
{
return cellType;
}

public String getLabel()
{
return label;
}

public void setLabel(String label)
{
this.label = label;
}
}
