package org.kirito666.sentiment;


/**
 * 情感标签
 *
 * @author Finger
 */
public class Sentiment {
    public String label;
    public String labelEn;
    public float predict;
    public String formatPredict;

    public Sentiment(String label, String labelEn, float predict, String formatPredict) {
        this.label = label;
        this.labelEn = labelEn;
        this.predict = predict;
        this.formatPredict = formatPredict;
    }

    public Sentiment() {
    }

    public String getLabel() {
        return this.label;
    }

    public String getLabelEn() {
        return this.labelEn;
    }

    public float getPredict() {
        return this.predict;
    }

    public String getFormatPredict() {
        return this.formatPredict;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setLabelEn(String labelEn) {
        this.labelEn = labelEn;
    }

    public void setPredict(float predict) {
        this.predict = predict;
    }

    public void setFormatPredict(String formatPredict) {
        this.formatPredict = formatPredict;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Sentiment)) return false;
        final Sentiment other = (Sentiment) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$label = this.getLabel();
        final Object other$label = other.getLabel();
        if (this$label == null ? other$label != null : !this$label.equals(other$label)) return false;
        final Object this$labelEn = this.getLabelEn();
        final Object other$labelEn = other.getLabelEn();
        if (this$labelEn == null ? other$labelEn != null : !this$labelEn.equals(other$labelEn)) return false;
        if (Float.compare(this.getPredict(), other.getPredict()) != 0) return false;
        final Object this$formatPredict = this.getFormatPredict();
        final Object other$formatPredict = other.getFormatPredict();
        if (this$formatPredict == null ? other$formatPredict != null : !this$formatPredict.equals(other$formatPredict))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Sentiment;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $label = this.getLabel();
        result = result * PRIME + ($label == null ? 43 : $label.hashCode());
        final Object $labelEn = this.getLabelEn();
        result = result * PRIME + ($labelEn == null ? 43 : $labelEn.hashCode());
        result = result * PRIME + Float.floatToIntBits(this.getPredict());
        final Object $formatPredict = this.getFormatPredict();
        result = result * PRIME + ($formatPredict == null ? 43 : $formatPredict.hashCode());
        return result;
    }

    public String toString() {
        return "Sentiment(label=" + this.getLabel() + ", labelEn=" + this.getLabelEn() + ", predict=" + this.getPredict() + ", formatPredict=" + this.getFormatPredict() + ")";
    }
}
