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

 package es.mityc.facturaeface.dnie;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.KeyStoreException;
import java.security.KeyStoreSpi;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.util.Date;
import java.util.Enumeration;

import es.gob.jmulticard.jse.provider.DnieKeyStoreImpl;

public final class DnieKeyStoreImplWrapper extends KeyStoreSpi {
	DnieKeyStoreImpl store;

	public DnieKeyStoreImplWrapper() {
		store = new DnieKeyStoreImpl();
	}
	@Override
	public Key engineGetKey(String alias, char[] password)
			throws NoSuchAlgorithmException, UnrecoverableKeyException {
		return store.engineGetKey(alias, null);
	}

	@Override
	public Certificate[] engineGetCertificateChain(String alias) {
		return store.engineGetCertificateChain(alias);
	}

	@Override
	public Certificate engineGetCertificate(String alias) {
		return store.engineGetCertificate(alias);
	}

	@Override
	public Date engineGetCreationDate(String alias) {
		return store.engineGetCreationDate(alias);
	}

	@Override
	public void engineSetKeyEntry(String alias, Key key, char[] password,
			Certificate[] chain) throws KeyStoreException {
		store.engineSetKeyEntry(alias, key, password, chain);
	}

	@Override
	public void engineSetKeyEntry(String alias, byte[] key,
			Certificate[] chain) throws KeyStoreException {
		store.engineSetKeyEntry(alias, key, chain);

	}

	@Override
	public void engineSetCertificateEntry(String alias, Certificate cert)
			throws KeyStoreException {
		store.engineSetCertificateEntry(alias, cert);

	}

	@Override
	public void engineDeleteEntry(String alias) throws KeyStoreException {
		store.engineDeleteEntry(alias);

	}

	@SuppressWarnings("unchecked")
	@Override
	public Enumeration<String> engineAliases() {
		return store.engineAliases();
	}

	@Override
	public boolean engineContainsAlias(String alias) {
		return store.engineContainsAlias(alias);
	}

	@Override
	public int engineSize() {
		return store.engineSize();
	}

	@Override
	public boolean engineIsKeyEntry(String alias) {
		return store.engineIsKeyEntry(alias);
	}

	@Override
	public boolean engineIsCertificateEntry(String alias) {
		return store.engineIsCertificateEntry(alias);
	}

	@Override
	public String engineGetCertificateAlias(Certificate cert) {
		return store.engineGetCertificateAlias(cert);
	}

	@Override
	public void engineStore(OutputStream stream, char[] password)
			throws IOException, NoSuchAlgorithmException,
			java.security.cert.CertificateException {
		store.engineStore(stream, password);
	}

	@Override
	public void engineLoad(InputStream stream, char[] password)
			throws IOException, NoSuchAlgorithmException,
			java.security.cert.CertificateException {
		store.engineLoad(stream, null);

	}
	@Override
	public void engineLoad(java.security.KeyStore.LoadStoreParameter loadstoreparameter)
	{
		store.engineLoad(null);
	}


}
