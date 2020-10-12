package in.ibridge.aizawl.loneitu;
public class CameraModel {
    public String locationm,reportDetailsm,reportImageUrlm,checkstatusm,replym,reporternos;

    public String getReporternos() {
        return reporternos;
    }

    public void setReporternos(String reporternos) {
        this.reporternos = reporternos;
    }

    public String getLocationm() {
        return locationm;
    }

    public String getCheckstatusm() {
        return checkstatusm;
    }

    public void setCheckstatusm(String checkstatusm) {
        this.checkstatusm = checkstatusm;
    }

    public String getReplym() {
        return replym;
    }

    public void setReplym(String replym) {
        this.replym = replym;
    }

    public void setLocationm(String locationm) {
        this.locationm = locationm;
    }

    public String getReportDetailsm() {
        return reportDetailsm;
    }

    public void setReportDetailsm(String reportDetailsm) {
        this.reportDetailsm = reportDetailsm;
    }

    public String getReportImageUrlm() {
        return reportImageUrlm;
    }

    public void setReportImageUrlm(String reportImageUrlm) {
        this.reportImageUrlm = reportImageUrlm;
    }

    public CameraModel(){}

    public  CameraModel(String locationss,String reportDetailsss,String downloadUrl,String checkstatusmm,String replymm,String reporters)
    {
        this.locationm=locationss;
        this.reportDetailsm=reportDetailsss;
        this.reportImageUrlm=downloadUrl;
        this.checkstatusm=checkstatusmm;
        this.replym=replymm;
        this.reporternos=reporters;
    }
}
