package org.brent.metallicspro.materials;

public class MaterialProperties {

    private HeatResistance heatResistance = HeatResistance.MODERATE;
    private HeatConduction heatConduction = HeatConduction.MODERATE;
    private boolean forageable = false;
    private boolean hydroExplosive = false;

    public HeatResistance getHeatResistance() {
        return heatResistance;
    }

    public MaterialProperties setHeatResistance(HeatResistance heatResistance) {
        this.heatResistance = heatResistance;
        return this;
    }

    public HeatConduction getHeatConduction() {
        return heatConduction;
    }

    public MaterialProperties setHeatConduction(HeatConduction heatConduction) {
        this.heatConduction = heatConduction;
        return this;
    }

    public boolean isForageable() {
        return forageable;
    }

    public MaterialProperties setForageable(boolean forageable) {
        this.forageable = forageable;
        return this;
    }

    public boolean isHydroExplosive() {
        return hydroExplosive;
    }

    public MaterialProperties setHydroExplosive(boolean hydroExplosive) {
        this.hydroExplosive = hydroExplosive;
        return this;
    }

    public enum HeatResistance {
        EXTREME(3.0),
        VERY_HIGH(2.0),
        HIGH(1.5),
        MODERATE(1.0),
        LOW(0.75),
        VERY_LOW(0.5),
        MINIMAL(0.25);

        private final double multiplier;

        HeatResistance(double multiplier) {
            this.multiplier = multiplier;
        }

        public double getMultiplier() {
            return multiplier;
        }
    }

    public enum HeatConduction {
        EXTREME(3.0),
        VERY_HIGH(2.0),
        HIGH(1.5),
        MODERATE(1.0),
        LOW(0.75),
        VERY_LOW(0.5),
        MINIMAL(0.25);

        private final double multiplier;

        HeatConduction(double multiplier) {
            this.multiplier = multiplier;
        }

        public double getMultiplier() {
            return multiplier;
        }
    }
}
