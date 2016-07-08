/**
 * Created by Naresh Jois on 19/11/15.
 */
class DimensionItem {
    private String level;
    private String caption;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public DimensionItem(String level, String caption) {
        this.level = level;
        this.caption = caption;
    }
}

class MeasureInfo{
    private String caption;
    private String uniqueName;

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getUniqueName() {
        return uniqueName;
    }

    public void setUniqueName(String uniqueName) {
        this.uniqueName = uniqueName;
    }

    public MeasureInfo(String caption, String uniqueName) {
        this.caption = caption;
        this.uniqueName = uniqueName;
    }
}

class DimensionInfo{
    private String dimensionName;
    private String hierarchyName;
    private String caption;
    private String uniqueName;

    public boolean isDate() {  return isDate; }

    public void setDate(boolean isDate) { this.isDate = isDate; }

    private boolean isDate;

    public String getDimensionName() {
        return dimensionName;
    }

    public void setDimensionName(String dimensionName) {
        this.dimensionName = dimensionName;
    }

    public String getHierarchyName() {
        return hierarchyName;
    }

    public void setHierarchyName(String hierarchyName) {
        this.hierarchyName = hierarchyName;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getUniqueName() {
        return uniqueName;
    }

    public void setUniqueName(String uniqueName) {
        this.uniqueName = uniqueName;
    }

    public DimensionInfo(String dimensionName, String hierarchyName, String caption, String uniqueName, boolean isDate) {
        this.dimensionName = dimensionName;
        this.hierarchyName = hierarchyName;
        this.caption = caption;
        this.uniqueName = uniqueName;
        this.isDate = isDate;
    }
}


class Error {
    public String getError() {
        return errorMessage;
    }

    public void setError(String error) {
        errorMessage = error;
    }

    private String errorMessage;

    public Error(String message){
        this.errorMessage = message;
    }
    public String toString(){
        return  this.errorMessage;
    }
}