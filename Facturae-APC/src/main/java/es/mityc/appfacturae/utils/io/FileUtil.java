/**
 * Copyright 2013 Ministerio de Industria, Energía y Turismo
 *
 * Este fichero es parte de "Facturae-APC".
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
package es.mityc.appfacturae.utils.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import es.mityc.appfacturae.exceptions.ParseException;

public class FileUtil {
	
	private static Log logger = LogFactory.getLog(FileUtil.class);
	
	public static String readBinaryFile(File f){
		FileInputStream fi = null;
		try {	
			fi = new FileInputStream(f);
			byte[] byteArray = new byte[(int)f.length()];
			StreamUtil.readStream(fi,byteArray);
			return Base64.encodeBytes(byteArray);
			
		} catch (FileNotFoundException fne) {
			logger.error("File not found: " + f.getAbsolutePath() + "\n" + fne.getMessage());
		} catch (IOException ioe) {
			logger.error("Unable to open file: " + f.getAbsolutePath() + "\n" + ioe.getMessage());
		} finally {
			if (fi != null)
				try {
					fi.close();
				} catch (IOException e) {}
		}
		
		return "";
	}
	
	public static void writeBinaryFile(String s, String fileName) {
		FileOutputStream o = null;
		try {
			byte[] b = Base64.decode(s);
			o = new FileOutputStream(fileName);
			StreamUtil.writeStream(o,b);
		} catch (FileNotFoundException fne) {
			logger.error("File not found: " + fileName + "\n" + fne.getMessage());
		} catch (IOException ioe) {
			logger.error("Unable to open file: " + fileName + "\n" + ioe.getMessage());
		} finally {
			if (o != null)
				try {
					o.close();
				} catch (IOException e) {}
		}	
	}
	
	public static void writeFile(String s, String fileName) {
		FileOutputStream o = null;
		try {
			byte[] b = s.getBytes();
			o = new FileOutputStream(fileName);
			StreamUtil.writeStream(o,b);
		} catch (FileNotFoundException fne) {
			logger.error("File not found: " + fileName + "\n" + fne.getMessage());
		} catch (IOException ioe) {
			logger.error("Unable to open file: " + fileName + "\n" + ioe.getMessage());
		} finally {
			if (o != null)
				try {
					o.close();
				} catch (IOException e) {}
		}	
	}
	
	public static String getExtension(File f) {
		if (f != null && f.getName().contains("."))
			return f.getName().substring(f.getName().lastIndexOf(".")).replace(".", "");
		else
			return "";
	}
	
	public static Document createDoc (File f) throws ParseException {
		if (f == null) {
			logger.warn("Document file is null. Unable to parse.");
			return null;
		}
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		try {
			doc = dbf.newDocumentBuilder().parse(f);
		} catch(FileNotFoundException fnfe){
			throw new ParseException(fnfe);
		} catch (ParserConfigurationException pce) {
			throw new ParseException(pce);
		} catch (SAXException se) {
			throw new ParseException(se);
		} catch (IOException ioe) {
			throw new ParseException(ioe);
		} catch (IllegalArgumentException iae) {
			throw new ParseException(iae);
		}
		return doc;
	}

	public static String getExtension(String fileName) throws IOException {
		String extension = "";
		int lastDot = fileName.lastIndexOf('.');
		if (lastDot != -1) {
			extension = fileName.substring(lastDot);
		}
		return extension;
	}

	public static void copy(File fromFile, File toFile) throws IOException {
		copy(fromFile, toFile, true);
	}

	public static void copy(File fromFile, File toFile, boolean overwrite) throws IOException {
		String fromFileName = fromFile.getAbsolutePath();
		String toFileName = toFile.getAbsolutePath();
		
		if (!fromFile.exists())
			throw new IOException("No such source file: " + fromFileName);
		if (!fromFile.isFile())
			throw new IOException("Can't copy directory: " + fromFileName);
		if (!fromFile.canRead())
			throw new IOException("Source file is unreadable: " + fromFileName);
	
		if (toFile.isDirectory())
			toFile = new File(toFile, fromFile.getName());
	
		if (toFile.exists()) {
			if (!toFile.canWrite())
				throw new IOException("Destination file is unwriteable: " + toFileName);
			if (!overwrite) {
				throw new IOException("Existing file was not overwritten.");
			}
		} else {
			String parent = toFile.getParent();
			if (parent == null) {
				parent = System.getProperty("user.dir");
			}
			File dir = new File(parent);
			if (!dir.exists()) {
				throw new IOException("Destination directory doesn't exist: " + parent);
			}
			if (dir.isFile()) {
				throw new IOException("Destination is not a directory: " + parent);
			}
			if (!dir.canWrite()) {
				throw new IOException("Destination directory is unwritable: " + parent);
			}
		}
	
		FileInputStream from = null;
		FileOutputStream to = null;
		try {
			from = new FileInputStream(fromFile);
			to = new FileOutputStream(toFile);
			byte[] buffer = new byte[4096];
			int bytesRead;
	
			while ((bytesRead = from.read(buffer)) != -1)
				to.write(buffer, 0, bytesRead); // write
		} finally {
			if (from != null)
				try {
					from.close();
				} catch (IOException e) {
					;
				}
			if (to != null)
				try {
					to.close();
				} catch (IOException e) {
					;
				}
		}
	}

	public static void copy(String fromFileName, String toFileName) throws IOException {
		copy(fromFileName, toFileName, true);
	}

	public static void copy(String fromFileName, String toFileName, boolean overwrite) throws IOException {
		File fromFile = new File(fromFileName);
		File toFile = new File(toFileName);
		copy(fromFile, toFile, overwrite);
	}
	
	public static File createNewDirectory(File path, String subDir, String datePattern) throws FileNotFoundException{
		File finalDirectory = null;
		if (!path.exists() || !path.canRead() || !path.isDirectory()) {
			logger.error("The directory does not exist or cannot be read");
			throw new FileNotFoundException("The destination directory does not exist or cannot be read");
		}
		Date d = new Date();
		DateFormat dateFormat = new SimpleDateFormat(datePattern);
		boolean exists = (new File(path.getAbsolutePath()+File.separator+subDir+"_"+dateFormat.format(d))).exists();
		if (!exists)
			finalDirectory = new File(path.getAbsolutePath()+File.separator+subDir+"_"+dateFormat.format(d));
		else{
			for (int i = 1 ; exists ; i++) {
				exists = (new File(path.getAbsolutePath()+File.separator+subDir+"_"+dateFormat.format(d)+"_"+i)).exists();
				if (!exists)
					finalDirectory = new File(path.getAbsolutePath()+File.separator+subDir+"_"+dateFormat.format(d)+"_"+i);
			}
		}
		if (finalDirectory.mkdirs())
			return finalDirectory;
		else
			throw new FileNotFoundException("Error creating the destination directory");
	}
	
	public static boolean existsDirectory(File path) throws FileNotFoundException{
		if (!path.exists() || !path.canRead() || !path.isDirectory()) {
			return false; 
		}else{
			return true;
		}
	}	
	
	public static boolean existsFile(File path) {
		if(path.exists() && !path.isDirectory()) {
			return true;
		}else{
			return false;
		}
	}
	
}
