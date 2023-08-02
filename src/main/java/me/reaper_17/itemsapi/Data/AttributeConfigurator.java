package me.reaper_17.itemsapi.Data;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;

public class AttributeConfigurator {
    private Attribute attribute;
    private AttributeModifier attributeModifier;

    public AttributeConfigurator(Attribute attribute, AttributeModifier attributeModifier){
        this.attribute = attribute;
        this.attributeModifier = attributeModifier;
    }


    public Attribute getAttribute() {
        return attribute;
    }

    public AttributeModifier getAttributeModifier() {
        return attributeModifier;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public void setAttributeModifier(AttributeModifier attributeModifier) {
        this.attributeModifier = attributeModifier;
    }
}
