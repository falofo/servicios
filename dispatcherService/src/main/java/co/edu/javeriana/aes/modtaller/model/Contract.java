package co.edu.javeriana.aes.modtaller.model;

public class Contract {
    private String endPoint;
    private String esquema;
    private String tipoServicio;
    private String idServicio;
    private String rutaContrato;
    private String wsdlJson;
    private String headBody;
    private String respExt;

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getEsquema() {
        return esquema;
    }

    public void setEsquema(String esquema) {
        this.esquema = esquema;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public String getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(String idServicio) {
        this.idServicio = idServicio;
    }

    public String getRutaContrato() {
        return rutaContrato;
    }

    public void setRutaContrato(String rutaContrato) {
        this.rutaContrato = rutaContrato;
    }

    public String getWsdlJson() {
        return wsdlJson;
    }

    public void setWsdlJson(String wsdlJson) {
        this.wsdlJson = wsdlJson;
    }

    public String getHeadBody() {
        return headBody;
    }

    public void setHeadBody(String headBody) {
        this.headBody = headBody;
    }

    public String getRespExt() {
        return respExt;
    }

    public void setRespExt(String respExt) {
        this.respExt = respExt;
    }
}
