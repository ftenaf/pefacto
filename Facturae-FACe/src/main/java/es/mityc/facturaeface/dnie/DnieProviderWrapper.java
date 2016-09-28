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

import java.security.Provider;

import es.gob.jmulticard.jse.provider.DnieProvider;

public final class DnieProviderWrapper extends Provider {
    public DnieProviderWrapper()
    {
        super("DNIeJCAProviderWrapper", 0.10000000000000001D, "Wrapper Proveedor para el DNIe");
        new DnieProvider();
        
        put("KeyStore.DNIw", "es.mityc.facturaeface.dnie.DnieKeyStoreImplWrapper");
        put("Signature.SHA1withRSA", "es.gob.jmulticard.jse.provider.DnieSignatureImpl$Sha1");
        put("Signature.SHA256withRSA", "es.gob.jmulticard.jse.provider.DnieSignatureImpl$Sha256");
        put("Signature.SHA384withRSA", "es.gob.jmulticard.jse.provider.DnieSignatureImpl$Sha384");
        put("Signature.SHA512withRSA", "es.gob.jmulticard.jse.provider.DnieSignatureImpl$Sha512");
        put("Signature.SHA1withRSA SupportedKeyClasses", "es.gob.jmulticard.jse.provider.DniePrivateKey");
        put("Signature.SHA256withRSA SupportedKeyClasses", "es.gob.jmulticard.jse.provider.DniePrivateKey");
        put("Signature.SHA384withRSA SupportedKeyClasses", "es.gob.jmulticard.jse.provider.DniePrivateKey");
        put("Signature.SHA512withRSA SupportedKeyClasses", "es.gob.jmulticard.jse.provider.DniePrivateKey");
        put("Alg.Alias.Signature.1.2.840.113549.1.1.5", "SHA1withRSA");
        put("Alg.Alias.Signature.OID.1.2.840.113549.1.1.5", "SHA1withRSA");
        put("Alg.Alias.Signature.1.3.14.3.2.29", "SHA1withRSA");
        put("Alg.Alias.Signature.SHAwithRSA", "SHA1withRSA");
        put("Alg.Alias.Signature.SHA-1withRSA", "SHA1withRSA");
        put("Alg.Alias.Signature.SHA1withRSAEncryption", "SHA1withRSA");
        put("Alg.Alias.Signature.SHA-1withRSAEncryption", "SHA1withRSA");
        put("Alg.Alias.Signature.1.2.840.113549.1.1.11", "SHA256withRSA");
        put("Alg.Alias.Signature.OID.1.2.840.113549.1.1.11", "SHA256withRSA");
        put("Alg.Alias.Signature.SHA-256withRSA", "SHA256withRSA");
        put("Alg.Alias.Signature.SHA-256withRSAEncryption", "SHA256withRSA");
        put("Alg.Alias.Signature.SHA256withRSAEncryption", "SHA256withRSA");
        put("Alg.Alias.Signature.1.2.840.113549.1.1.12", "SHA384withRSA");
        put("Alg.Alias.Signature.OID.1.2.840.113549.1.1.12", "SHA384withRSA");
        put("Alg.Alias.Signature.SHA-384withRSA", "SHA384withRSA");
        put("Alg.Alias.Signature.SHA-384withRSAEncryption", "SHA384withRSA");
        put("Alg.Alias.Signature.SHA384withRSAEncryption", "SHA384withRSA");
        put("Alg.Alias.Signature.1.2.840.113549.1.1.13", "SHA512withRSA");
        put("Alg.Alias.Signature.OID.1.2.840.113549.1.1.13", "SHA512withRSA");
        put("Alg.Alias.Signature.SHA-512withRSA", "SHA512withRSA");
        put("Alg.Alias.Signature.SHA-512withRSAEncryption", "SHA512withRSA");
        put("Alg.Alias.Signature.SHA512withRSAEncryption", "SHA512withRSA");
    }
}
