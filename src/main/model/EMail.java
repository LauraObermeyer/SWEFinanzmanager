package main.model;

public class EMail {
    private String vordererTeil;
    private String domain;
    private String laenderkennzeichen;

    public EMail(String pVordererTeil, String pDomain, String pLaenderkennzeichen) {
        this. vordererTeil = pVordererTeil;
        this.domain = pDomain;
        this. laenderkennzeichen = pLaenderkennzeichen;
    }

    public String getVordererTeil() {
        return vordererTeil;
    }

    public void setVordererTeil(String vordererTeil) {
        this.vordererTeil = vordererTeil;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getLaenderkennzeichen() {
        return laenderkennzeichen;
    }

    public void setLaenderkennzeichen(String laenderkennzeichen) {
        this.laenderkennzeichen = laenderkennzeichen;
    }

    public String toString() {
        return vordererTeil + "@" + domain + "." + laenderkennzeichen;
    }
}
