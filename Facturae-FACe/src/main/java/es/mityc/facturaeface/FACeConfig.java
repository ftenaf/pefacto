/**
 * Copyright 2013 Ministerio de Industria, Energía y Turismo
 *
 * Este fichero es parte de "Facturae-FAce".
 *
 * Licencia con arreglo a la EUPL, Versión 1.1 o –en cuanto sean aprobadas por la Comisión Europea– versiones posteriores de la EUPL (la Licencia);
 * Solo podrá usarse esta obra si se respeta la Licencia.
 *
 * Puede obtenerse una copia de la Licencia en:
 *
 * http://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 *
 * Salvo cuando lo exija la legislación aplicable o se acuerde por escrito, el programa distribuido con arreglo a la Licencia se distribuye «TAL CUAL»,
 * SIN GARANTÍAS NI CONDICIONES DE NINGÚN TIPO, ni expresas ni implícitas.
 * Véase la Licencia en el idioma concreto que rige los permisos y limitaciones que establece la Licencia.
 */

package es.mityc.facturaeface;


public class FACeConfig {
	public enum FACeCertificateProvider {WINDOWS, JAVA, DNIE};
	
	private static FACeConfig instance;
	
	private String urlFACeEndpoint; 

	private String storeFile;
	private char[] storePassword;
	private String certificateAlias;
	private char[] certificatePassword;
	private boolean useProxy = false;
	private String proxyHost;
	private String proxyPort;
	private boolean authProxy =  false;
	private String proxyUser;
	private String proxyPassword;
	private FACeCertificateProvider certificateProvider = FACeCertificateProvider.JAVA;
	private int timeoutMilis = -1; //Si no está configurado en el fichero de configuración no hay timeout, es decir, timeout -1
	
	private FACeConfig() {
	}
	
	public static FACeConfig getInstance() {
		if(instance == null) {
			instance = new FACeConfig();
		}
		return instance;
	}
	
	public String getUrlFACeEndpoint() {
		return urlFACeEndpoint;
	}

	public void setUrlFACeEndpoint(String urlFACeEndpoint) {
		this.urlFACeEndpoint = urlFACeEndpoint;
	}

	public String getCertificateAlias() {
		return certificateAlias;
	}

	public void setCertificateAlias(String certificateAlias) {
		this.certificateAlias = certificateAlias;
	}

	public char[] getCertificatePassword() {
		return certificatePassword;
	}

	public void setCertificatePassword(char[] certificatePassword) {
		this.certificatePassword = certificatePassword;
	}

	public boolean isUseProxy() {
		return useProxy;
	}

	public void setUseProxy(boolean useProxy) {
		this.useProxy = useProxy;
	}

	public String getProxyHost() {
		return proxyHost;
	}

	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}

	public String getProxyPort() {
		return proxyPort;
	}

	public void setFACETimeoutMilis(int timeoutMilis) {
		this.timeoutMilis = timeoutMilis;
	}
	
	public int getFACETimeoutMilis() {
		return timeoutMilis;
	}

	public void setProxyPort(String proxyPort) {
		this.proxyPort = proxyPort;
	}

	public void setAuthProxy ( boolean authProxy ) {
		this.authProxy = authProxy;
	}
	public boolean isAuthProxy ( ) {
		return this.authProxy;
	}
	public void setProxyUser(String proxyUser) {
		this.proxyUser = proxyUser;
	}
	public String getProxyUser() {
		return this.proxyUser;
	}
	public void setProxyPassword(String proxyPassword) {
		this.proxyPassword = proxyPassword;
	}
	public String getProxyPassword() {
		return this.proxyPassword;
	}

	public String getStoreFile() {
		return storeFile;
	}

	public void setStoreFile(String storeFile) {
		this.storeFile = storeFile;
	}

	public char[] getStorePassword() {
		return storePassword;
	}

	public void setStorePassword(char[] storePassword) {
		this.storePassword = storePassword;
	}

	public FACeCertificateProvider getCertificateProvider() {
		return certificateProvider;
	}

	public void setCertificateProvider(FACeCertificateProvider certificateProvider) {
		this.certificateProvider = certificateProvider;
	}
}
