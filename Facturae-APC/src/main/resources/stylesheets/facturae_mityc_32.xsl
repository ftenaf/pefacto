<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet  xmlns:java="http://xml.apache.org/xslt/java"
	extension-element-prefixes="java" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:ds="http://www.w3.org/2000/09/xmldsig#" xmlns:tns="http://schemas.xmlsoap.org/soap/envelope/">
	<xsl:output method="html" indent="yes"/>
	<xsl:decimal-format grouping-separator="." decimal-separator=","/>
	<xsl:template match="/">	
	<html>
	<script id="ASW_status">window.status=' WIGEXSLT Plantilla xslt de facturas '</script>
	<head>
		<xsl:variable name="lang2" select="/*/Invoices/Invoice/InvoiceIssueData/LanguageName"/>
		<xsl:variable name="show" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('MoreData','',concat('lang_',$lang2))" />
		<xsl:variable name="hide" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Hide','',concat('lang_',$lang2))" />
		<script>
			function mostrarCapa(capa)
			{
				var datos = document.getElementById(capa).style.display;
				var show='<xsl:value-of select="$show"/>';
				var hide='<xsl:value-of select="$hide"/>';
				if (datos != "none")
				{
					document.getElementById(capa + "Link").innerHTML = show;
					document.getElementById(capa).style.display="none";
				}
				else
				{
					document.getElementById(capa + "Link").innerHTML = hide;
					document.getElementById(capa).style.display="";
				}
			}
			function mostrarFactura(numFactura)
			{
				var datos = document.getElementById(numFactura).style.display;
				if (datos != "none")
				{
					document.getElementById("lote").style.display="";
					document.getElementById("importesLote").style.display="";
					if (document.getElementById("tercero")!=null)
					{
						document.getElementById("tercero").style.display="";
					}
					document.getElementById("listadoFacturas").style.display="";
					document.getElementById("factura" + numFactura).style.display="none";
					document.getElementById(numFactura).style.display="none";
				}
				else
				{
					document.getElementById("lote").style.display="none";
					document.getElementById("importesLote").style.display="none";
					if (document.getElementById("tercero")!=null)
					{
						document.getElementById("tercero").style.display="none";
					}
					document.getElementById("listadoFacturas").style.display="none";
					document.getElementById("factura" + numFactura).style.display="";
					document.getElementById(numFactura).style.display="";
				}
			}
			function mostrarDetalle(numFactura,desDetalle)
			{
				var datos = document.getElementById(numFactura + "_" +
				desDetalle).style.display;
				if (datos != "none")
				{
					document.getElementById("emisor").style.display="";
					document.getElementById("receptor").style.display="";
					if (document.getElementById("cesionario")!=null)
					{
						document.getElementById("cesionario").style.display="";
					}
					document.getElementById("factura" + numFactura).style.display="";
					document.getElementById(numFactura).style.display="";
					document.getElementById(numFactura + "_" +
					desDetalle).style.display="none";
				}
				else
				{
					document.getElementById("lote").style.display="none";
					document.getElementById("importesLote").style.display="none";
					document.getElementById("emisor").style.display="none";
					document.getElementById("receptor").style.display="none";
					if (document.getElementById("tercero")!=null)
					{
						document.getElementById("tercero").style.display="none";
					}
					if (document.getElementById("cesionario")!=null)
					{
						document.getElementById("cesionario").style.display="none";
					}
					document.getElementById("listadoFacturas").style.display="none";
					document.getElementById("factura" + numFactura).style.display="none";
					document.getElementById(numFactura).style.display="none";
					document.getElementById(numFactura + "_" +
					desDetalle).style.display="";
				}
			}
		</script>
		<xsl:variable name="lang" select="/*/Invoices/Invoice/InvoiceIssueData/LanguageName"/>
		<xsl:variable name="titletraduccion" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('BatchSummaryLowcase','',concat('lang_',$lang))" />
		<title><xsl:value-of select="$titletraduccion"/></title>
		<style>
		/* CSS de Facturae */ 
		body {mso-style-parent:Normal; color:#135b8a; font-family:Arial; font-size:8.0pt; font-weight:normal; margin:0mm; margin-bottom:.0001pt; mso-pagination:widow-orphan; mso-bidi-font-size:12.0pt; mso-fareast-font-family:"Times New Roman"; mso-bidi-font-family:"Times New Roman";}
		h3 {mso-style-parent:Normal; color:#135b8a; font-family:Arial; font-size:10.0pt; font-weight:normal; margin:0mm;margin-bottom:.0001pt; mso-pagination:widow-orphan; mso-bidi-font-size:12.0pt; mso-fareast-font-family:"Times New Roman";mso-bidi-font-family:"Times New Roman";}
		h1 {mso-style-next:Normal; color:#0096c8; font-family:Arial; font-size:14.0pt; font-weight:bold; margin:0mm;margin-bottom:.0001pt; text-align:center; mso-pagination:widow-orphan;page-break-after:avoid; mso-outline-level:1; mso-bidi-font-family:"Times New Roman"; mso-font-kerning:0pt;}
		h2 {mso-style-next:Normal; color:#7dbee1; font-family:Arial; font-size:12.0pt; font-weight:normal; size:595.3pt 841.9pt; margin:10.0mm 19.85pt 30.05pt 20.0mm; mso-header-margin:5.65pt;mso-footer-margin:5.65pt; mso-paper-source:0;}	
		a:link , a:visited , a:active {font-size:10.0pt; display:block; font-weight:bold; background-color:#a0d7a0; color:#ffffff; width:120px; text-align:center; padding:4px; text-decoration:none;}
		a:hover {font-size:10.0pt; background-color:#619861; color:#ffffff;}
		</style>
	</head>
	<body>
		<xsl:variable name="lang" select="/*/Invoices/Invoice/InvoiceIssueData/LanguageName"/>
		<div id="principal">
			<center>
				<table border="0" width="90%" cellpadding="2" cellspacing="0">
					<tr id="lote">
						<td width="100%">
							<table border="0" cellpadding="2" cellspacing="0" width="100%">
								<tr>
									<td align="center" colspan="2">
										<xsl:variable name="title2traduccion" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('BatchSummary','',concat('lang_',$lang))" />
										<h1><xsl:value-of select="$title2traduccion"/></h1>
									</td>
								</tr>
								<tr>
									<td>
										<font color="FFFFFF">_</font>
									</td>
								</tr>
								<tr>
									<td width="100%">
										<table bordercolor="#e6f0fa" border="1" cellpadding="2" cellspacing="0" width="100%">
											<tr>
												<td>
													<table border="0" cellpadding="2" cellspacing="0"
														width="100%">
														<tr>
															<td align="center" width="33%">
																<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Number','',concat('lang_',$lang))" />
																<h3><xsl:value-of select="$traduccion"/></h3>
																<br />
																<xsl:value-of select="/*/FileHeader/Batch/BatchIdentifier" />
															</td>
															<td align="center" width="34%">
																<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Version','',concat('lang_',$lang))" />
																<h3><xsl:value-of select="$traduccion"/></h3>
																<br />
																<xsl:value-of select="/*/FileHeader/SchemaVersion" />
															</td>
															<td align="center" width="33%">
																<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Modality','',concat('lang_',$lang))" />
																<h3><xsl:value-of select="$traduccion"/></h3>
																<br />
																<xsl:variable name="toTranslate" select="/*/FileHeader/Modality" />
																<xsl:variable name="traduccion2"
																	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Modality',$toTranslate,concat('lang_',$lang))" />
																<xsl:value-of select="$traduccion2" />
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr>
												<td>
													<table border="0" cellpadding="2" cellspacing="0"
														width="100%">
														<tr>
															<td align="center" width="33%">
																<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('InvoiceIssuer','',concat('lang_',$lang))" />
																<h3><xsl:value-of select="$traduccion"/></h3>
																<br />
																<xsl:variable name="toTranslate" select="/*/FileHeader/InvoiceIssuerType" />
																<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('InvoiceIssuerType',$toTranslate,concat('lang_',$lang))" />
																<xsl:value-of select="$traduccion2" />
															</td>
															<td align="center" width="33%">
																<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('NumberOfInvoices','',concat('lang_',$lang))" />
																<h3><xsl:value-of select="$traduccion"/></h3>
																<br />
																<xsl:value-of select="/*/FileHeader/Batch/InvoicesCount" />
															</td>
															<td align="center" width="34%">
																<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('InvoiceCurrency','',concat('lang_',$lang))" />
																<h3><xsl:value-of select="$traduccion"/></h3>
																<br />
																<xsl:value-of select="/*/FileHeader/Batch/InvoiceCurrencyCode" />
																<xsl:if test='/*/FileHeader/Batch/InvoiceCurrencyCode="EUR"'>
																	<font color="#ffffff">_</font>
																	<xsl:variable name="traduccioneuro"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('EuroSimbol','',concat('lang_',$lang))" />
																	<xsl:value-of select="$traduccioneuro"/>
																</xsl:if>
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<xsl:for-each select="/*/Invoices/Invoice">
						<xsl:variable name="nFactura" select="InvoiceHeader/InvoiceNumber" />
						<tr id="factura{$nFactura}" style="display:none">
							<td width="100%">
								<table border="0" cellpadding="2" cellspacing="0" width="100%">
									<tr>
										<td align="center">
											<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('InvoiceNumber','',concat('lang_',$lang))" />
											<h1>
												<xsl:value-of select="$traduccion"/>
												<font color="FFFFFF">_</font>
												<xsl:value-of select="$nFactura" />
											</h1>
										</td>
									</tr>
									<tr>
										<td align="right">
											<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('BackLote','',concat('lang_',$lang))" />
											<a href="#" onclick="mostrarFactura('{$nFactura}')"><xsl:value-of select="$traduccion"/></a>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</xsl:for-each>
					<tr id="importesLote">
						<td width="100%">
							<table border="0" cellpadding="2" cellspacing="0" width="100%">
								<tr>
									<td>
										<font color="FFFFFF">_</font>
									</td>
								</tr>
								<tr>
									<td>
										<hr color="a0d7a0"/>
									</td>
								</tr>
								<tr>
									<td>
									<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Amounts','',concat('lang_',$lang))" />
									<h2><xsl:value-of select="$traduccion"/></h2>
									</td>
								</tr>
								<tr>
									<td>
										<font color="FFFFFF">_</font>
									</td>
								</tr>
								<tr>
									<td align="right">
										<xsl:if test='/*/FileHeader/Batch/InvoiceCurrencyCode!="EUR"'>
											<table bordercolor="#e6f0fa" border="1" cellpadding="2" cellspacing="0"
												width="60%">
												<tr>
													<td width="50%" valign="top" align="center">
														<font color="FFFFFF">___</font>
													</td>
													<td width="25%" valign="top" align="center">
														<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Amount','',concat('lang_',$lang))" />
														<h3><xsl:value-of select="$traduccion"/></h3>
													</td>
													<td width="25%" valign="top" align="center">
														<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('EquivalentAmount','',concat('lang_',$lang))" />
														<h3><xsl:value-of select="$traduccion"/></h3>
													</td>
												</tr>
												<tr>
													<td width="50%" valign="top" align="right">
														<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('InvoiceTotalAmount','',concat('lang_',$lang))" />
														<h3><xsl:value-of select="$traduccion"/><font color="FFFFFF">___</font></h3>
													</td>
													<td width="25%" valign="top">
														<table border="0" cellpadding="2" cellspacing="0"
															width="100%">
															<tr>
																<td width="100%" align="right">
																	<xsl:variable name="decimales" select="/*/FileHeader/Batch/TotalInvoicesAmount/TotalAmount" />
																	<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)" />
																</td>
															</tr>
														</table>
													</td>
													<td width="25%" valign="top">
														<table border="0" cellpadding="2" cellspacing="0"
															width="100%">
															<tr>
																<td align="right">
																	<xsl:variable name="decimales" select="/*/FileHeader/Batch/TotalInvoicesAmount/EquivalentInEuros" />
																	<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)" />
																</td>
															</tr>
														</table>
													</td>
												</tr>
												<tr>
													<td width="50%" valign="top" align="right">
														<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TotalAmountToPay','',concat('lang_',$lang))" />
														<h3><xsl:value-of select="$traduccion"/><font color="FFFFFF">___</font>
														</h3>
													</td>
													<td width="25%" valign="top">
														<table border="0" cellpadding="2" cellspacing="0"
															width="100%">
															<tr>
																<td width="100%" align="right">
																	<xsl:variable name="decimales" select="/*/FileHeader/Batch/TotalOutstandingAmount/TotalAmount" />
																	<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)" />
																</td>
															</tr>
														</table>
													</td>
													<td width="25%" valign="top">
														<table border="0" cellpadding="2" cellspacing="0"
															width="100%">
															<tr>
																<td align="right">
																	<xsl:variable name="decimales" select="/*/FileHeader/Batch/TotalOutstandingAmount/EquivalentInEuros" />
																	<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)" />
																</td>
															</tr>
														</table>
													</td>
												</tr>
												<tr>
													<td width="50%" valign="top" align="right">
														<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TotalAmountToExecute','',concat('lang_',$lang))" />
														<h3><xsl:value-of select="$traduccion"/><font color="FFFFFF">___</font></h3>
													</td>
													<td width="25%" valign="top">
														<table border="0" cellpadding="2" cellspacing="0"
															width="100%">
															<tr>
																<td width="100%" align="right">
																	<xsl:variable name="decimales" select="/*/FileHeader/Batch/TotalExecutableAmount/TotalAmount" />
																	<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)" />
																</td>
															</tr>
														</table>
													</td>
													<td width="25%" valign="top">
														<table border="0" cellpadding="2" cellspacing="0"
															width="100%">
															<tr>
																<td align="right">
																	<xsl:variable name="decimales" select="/*/FileHeader/Batch/TotalExecutableAmount/EquivalentInEuros" />
																	<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)" />
																</td>
															</tr>
														</table>
													</td>
												</tr>
											</table>
										</xsl:if>
										<xsl:if test='/*/FileHeader/Batch/InvoiceCurrencyCode="EUR"'>
											<table border="0" cellpadding="2" cellspacing="0"
												width="100%">
												<tr>
													<td width="80%" align="right">
														<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('InvoiceTotalAmount','',concat('lang_',$lang))" />
														<h3><xsl:value-of select="$traduccion"/><font color="FFFFFF">___</font>
														</h3>
													</td>
													<td width="20%" align="center">
														<table bordercolor="#e6f0fa" border="1" cellpadding="2" cellspacing="0"
															width="100%">
															<tr>
																<td align="right">
																	<xsl:variable name="decimales" select="/*/FileHeader/Batch/TotalInvoicesAmount/TotalAmount" />
																	<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)" />
																</td>
															</tr>
														</table>
													</td>
												</tr>
											</table>
											<table border="0" cellpadding="2" cellspacing="0"
												width="100%">
												<tr>
													<td width="80%" align="right">
														<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TotalAmountToPay','',concat('lang_',$lang))" />
														<h3><xsl:value-of select="$traduccion"/><font color="FFFFFF">___</font>
														</h3>
													</td>
													<td width="20%" align="center">
														<table bordercolor="#e6f0fa" border="1" cellpadding="2" cellspacing="0"
															width="100%">
															<tr>
																<td align="right">
																	<xsl:variable name="decimales" select="/*/FileHeader/Batch/TotalOutstandingAmount/TotalAmount" />
																	<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)" />
																</td>
															</tr>
														</table>
													</td>
												</tr>
											</table>
											<table border="0" cellpadding="2" cellspacing="0"
												width="100%">
												<tr>
													<td width="80%" align="right">
														<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TotalAmountToExecute','',concat('lang_',$lang))" />
														<h3><xsl:value-of select="$traduccion"/><font color="FFFFFF">___</font></h3>
													</td>
													<td width="20%" align="center">
														<table bordercolor="#e6f0fa" border="1" cellpadding="2" cellspacing="0"
															width="100%">
															<tr>
																<td align="right">
																	<xsl:variable name="decimales" select="/*/FileHeader/Batch/TotalExecutableAmount/TotalAmount" />
																	<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)" />
																</td>
															</tr>
														</table>
													</td>
												</tr>
											</table>
										</xsl:if>
									</td>
								</tr>
								<tr>
									<td>
										<font color="FFFFFF">_</font>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr id="emisor">
						<td width="100%">
							<table border="0" cellpadding="2" cellspacing="0" width="100%">
								<tr>
									<td width="100%">
										<table border="0" cellpadding="2" cellspacing="0" width="100%">
											<tr>
												<td colspan="3">
													<hr color="a0d7a0"/>
												</td>
											</tr>
											<tr>
												<td colspan="2">
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('IssuerData','',concat('lang_',$lang))" />
													<h2><xsl:value-of select="$traduccion"/></h2>
												</td>
												<td align="right">
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('MoreData','',concat('lang_',$lang))" />
													<a id="datosEmisorLink" href="javascript:mostrarCapa('datosEmisor')"><xsl:value-of select="$traduccion"/></a>
												</td>
											</tr>
											<tr>
												<td colspan="3">
													<font color="FFFFFF">_</font>
												</td>
											</tr>
											<xsl:if test='/*/Parties/SellerParty/LegalEntity!=""'>
												<tr>
													<td width="50%" colspan="2">
														<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('CorporateName','',concat('lang_',$lang))" />
														<h3><xsl:value-of select="$traduccion"/></h3>
														<font color="FFFFFF">__</font>
														<xsl:value-of select="/*/Parties/SellerParty/LegalEntity/CorporateName"/>
													</td>
													<td width="50%">
														<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TaxIdentificationNumber','',concat('lang_',$lang))" />
														<h3><xsl:value-of select="$traduccion"/>:</h3>
														<font color="FFFFFF">__</font>
														<xsl:value-of select="/*/Parties/SellerParty/TaxIdentification/TaxIdentificationNumber" />
													</td>
												</tr>
											</xsl:if>
											<xsl:if test='/*/Parties/SellerParty/Individual!=""'>
												<tr>
													<td width="50%" colspan="2">
														<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('NameAndSurnames','',concat('lang_',$lang))" />
														<h3><xsl:value-of select="$traduccion"/>:</h3>
														<font color="FFFFFF">__</font>
														<xsl:value-of select="/*/Parties/SellerParty/Individual/Name" />
														<font color="FFFFFF">_</font>
														<xsl:value-of select="/*/Parties/SellerParty/Individual/FirstSurname" />
														<font color="FFFFFF">_</font>
														<xsl:value-of select="/*/Parties/SellerParty/Individual/SecondSurname" />
														<font color="FFFFFF">_</font>
													</td>
													<td width="50%">
														<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TaxIdentificationNumber','',concat('lang_',$lang))" />
														<h3><xsl:value-of select="$traduccion"/>:</h3>
														<font color="FFFFFF">__</font>
														<xsl:value-of select="/*/Parties/SellerParty/TaxIdentification/TaxIdentificationNumber" />
													</td>
												</tr>
											</xsl:if>
										</table>
									</td>
								</tr>
								<tr id="datosEmisor" style="display:none">
									<td width="100%">
										<table border="0" cellpadding="2" cellspacing="0" width="100%">
											<tr>
												<td width="50%" colspan="2">
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('PersonType','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/>:</h3>
													<font color="FFFFFF">__</font>
													<xsl:variable name="toTranslate" select="/*/Parties/SellerParty/TaxIdentification/PersonTypeCode" />
													<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('PersonTypeCode',$toTranslate,concat('lang_',$lang))" />
													<xsl:value-of select="$traduccion2" />
												</td>
												<td width="50%">
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('ResidenceType','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/>:</h3>
													<font color="FFFFFF">__</font>
													<xsl:variable name="toTranslate" select="/*/Parties/SellerParty/TaxIdentification/ResidenceTypeCode" />
													<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('ResidenceTypeCode',$toTranslate,concat('lang_',$lang))" />
													<xsl:value-of select="$traduccion2" />
												</td>
											</tr>
											<xsl:if test='/*/Parties/SellerParty/LegalEntity!=""'>
												<tr>
													<td valign="top" width="10%">
														<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Address','',concat('lang_',$lang))" />
														<h3><xsl:value-of select="$traduccion"/>:</h3>
													</td>
													<td valign="top" width="40%">
														<xsl:if test='/*/Parties/SellerParty/LegalEntity/AddressInSpain!=""'>
															<xsl:value-of select="/*/Parties/SellerParty/LegalEntity/AddressInSpain/Address" />
															<br />
															<xsl:value-of select="/*/Parties/SellerParty/LegalEntity/AddressInSpain/PostCode" />
															<font color="FFFFFF">__</font>
															<xsl:value-of select="/*/Parties/SellerParty/LegalEntity/AddressInSpain/Town" />
															<br />
															<xsl:value-of select="/*/Parties/SellerParty/LegalEntity/AddressInSpain/Province" />
															<br />
															<xsl:value-of select="/*/Parties/SellerParty/LegalEntity/AddressInSpain/CountryCode" />
														</xsl:if>
														<xsl:if test='/*/Parties/SellerParty/LegalEntity/OverseasAddress!=""'>
															<xsl:value-of select="/*/Parties/SellerParty/LegalEntity/OverseasAddress/Address" />
															<br />
															<xsl:value-of select="/*/Parties/SellerParty/LegalEntity/OverseasAddress/PostCodeAndTown" />
															<br />
															<xsl:value-of select="/*/Parties/SellerParty/LegalEntity/OverseasAddress/Province" />
															<br />
															<xsl:value-of select="/*/Parties/SellerParty/LegalEntity/OverseasAddress/CountryCode" />
														</xsl:if>
													</td>
													<xsl:if test='/*/Parties/SellerParty/LegalEntity/TradeName!=""'>
														<td valign="top" width="50%">
															<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TradeName','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion"/>:</h3>
															<font color="FFFFFF">__</font>
															<xsl:value-of select="/*/Parties/SellerParty/LegalEntity/TradeName" />
														</td>
													</xsl:if>
												</tr>
												<xsl:if test='/*/Parties/SellerParty/LegalEntity/RegistrationData!=""'>
													<tr>
														<td width="30%" colspan="3">
															<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('RegistryData','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion"/>:</h3>
														</td>
													</tr>
													<tr>
														<td colspan="3">
															<table border="0" cellpadding="2" cellspacing="0"
																width="100%">
																<tr>
																	<td width="25%">
																		<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Book','',concat('lang_',$lang))" />
																		<h3><xsl:value-of select="$traduccion"/>:</h3>
																		<font color="FFFFFF">__</font>
																		<xsl:value-of select="/*/Parties/SellerParty/LegalEntity/RegistrationData/Book" />
																	</td>
																	<td width="25%">
																		<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('RegisterOfCompaniesLocation','',concat('lang_',$lang))" />
																		<h3><xsl:value-of select="$traduccion"/>:</h3>
																		<font color="FFFFFF">___</font>
																		<xsl:value-of select="/*/Parties/SellerParty/LegalEntity/RegistrationData/RegisterOfCompaniesLocation" />
																	</td>
																	<td width="25%">
																		<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Sheet','',concat('lang_',$lang))" />
																		<h3><xsl:value-of select="$traduccion"/>:</h3>
																		<font color="FFFFFF">___</font>
																		<xsl:value-of select="/*/Parties/SellerParty/LegalEntity/RegistrationData/Sheet" />
																	</td>
																	<td width="25%">
																		<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Folio','',concat('lang_',$lang))" />
																		<h3><xsl:value-of select="$traduccion"/>:</h3>
																		<font color="FFFFFF">___</font>
																		<xsl:value-of select="/*/Parties/SellerParty/LegalEntity/RegistrationData/Folio" />
																	</td>
																</tr>
																<tr>
																	<td width="25%">
																		<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Section','',concat('lang_',$lang))" />
																		<h3><xsl:value-of select="$traduccion"/>:</h3>
																		<font color="FFFFFF">__</font>
																		<xsl:value-of select="/*/Parties/SellerParty/LegalEntity/RegistrationData/Section" />
																	</td>
																	<td width="25%">
																		<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Volume','',concat('lang_',$lang))" />
																		<h3><xsl:value-of select="$traduccion"/>:</h3>
																		<font color="FFFFFF">___</font>
																		<xsl:value-of select="/*/Parties/SellerParty/LegalEntity/RegistrationData/Volume" />
																	</td>
																	<td width="25%">
																		<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('AdditionalRegistrationData','',concat('lang_',$lang))" />
																		<h3><xsl:value-of select="$traduccion"/>:</h3>
																		<font color="FFFFFF">___</font>
																		<xsl:value-of select="/*/Parties/SellerParty/LegalEntity/RegistrationData/AdditionalRegistrationData" />
																	</td>
																</tr>
															</table>
														</td>
													</tr>
												</xsl:if>
												<xsl:if test='/*/Parties/SellerParty/LegalEntity/ContactDetails!=""'>
													<tr>
														<td width="30%" colspan="3">
															<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('ContactData','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion"/>:</h3>
														</td>
													</tr>
													<tr>
														<td colspan="3">
															<table border="0" cellpadding="2" cellspacing="0"
																width="100%">
																<tr>
																	<td width="25%">
																		<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Telephone','',concat('lang_',$lang))" />
																		<h3><xsl:value-of select="$traduccion"/>:</h3>
																		<font color="FFFFFF">__</font>
																		<xsl:value-of	select="/*/Parties/SellerParty/LegalEntity/ContactDetails/Telephone" />
																	</td>
																	<td width="25%">
																		<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TeleFax','',concat('lang_',$lang))" />
																		<h3><xsl:value-of select="$traduccion"/>:</h3>
																		<font color="FFFFFF">___</font>
																		<xsl:value-of select="/*/Parties/SellerParty/LegalEntity/ContactDetails/TeleFax" />
																	</td>
																	<td width="25%">
																		<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('WebAddress','',concat('lang_',$lang))" />
																		<h3><xsl:value-of select="$traduccion"/>:</h3>
																		<font color="FFFFFF">___</font>
																		<xsl:value-of select="/*/Parties/SellerParty/LegalEntity/ContactDetails/WebAddress" />
																	</td>
																	<td width="25%">
																		<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('ElectronicMail','',concat('lang_',$lang))" />
																		<h3><xsl:value-of select="$traduccion"/>:</h3>
																		<font color="FFFFFF">___</font>
																		<xsl:value-of select="/*/Parties/SellerParty/LegalEntity/ContactDetails/ElectronicMail" />
																	</td>
																</tr>
																<tr>
																	<td width="25%">
																		<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('ContactPersons','',concat('lang_',$lang))" />
																		<h3><xsl:value-of select="$traduccion"/>:</h3>
																		<font color="FFFFFF">__</font>
																		<xsl:value-of select="/*/Parties/SellerParty/LegalEntity/ContactDetails/ContactPersons" />
																	</td>
																	<td width="25%">
																		<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('CnoCnae','',concat('lang_',$lang))" />
																		<h3><xsl:value-of select="$traduccion"/>:</h3>
																		<font color="FFFFFF">___</font>
																		<xsl:value-of select="/*/Parties/SellerParty/LegalEntity/ContactDetails/CnoCnae" />
																	</td>
																	<td width="25%">
																		<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('INETownCode','',concat('lang_',$lang))" />
																		<h3><xsl:value-of select="$traduccion"/>:</h3>
																		<font color="FFFFFF">___</font>
																		<xsl:value-of select="/*/Parties/SellerParty/LegalEntity/ContactDetails/INETownCode" />
																	</td>
																	<td width="25%">
																		<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('AdditionalContactDetails','',concat('lang_',$lang))" />
																		<h3><xsl:value-of select="$traduccion"/>:</h3>
																		<font color="FFFFFF">___</font>
																		<xsl:value-of select="/*/Parties/SellerParty/LegalEntity/ContactDetails/AdditionalContactDetails" />
																	</td>
																</tr>
															</table>
														</td>
													</tr>
												</xsl:if>
											</xsl:if>
											<xsl:if test='/*/Parties/SellerParty/Individual!=""'>
												<tr>
													<td valign="top" width="10%">
														<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Address','',concat('lang_',$lang))" />
														<h3><xsl:value-of select="$traduccion"/>:</h3>
													</td>
													<td valign="top" width="40%">
														<xsl:if test='/*/Parties/SellerParty/Individual/AddressInSpain!=""'>
															<xsl:value-of select="/*/Parties/SellerParty/Individual/AddressInSpain/Address" />
															<br />
															<xsl:value-of select="/*/Parties/SellerParty/Individual/AddressInSpain/PostCode" />
															<font color="FFFFFF">_</font>
															<font color="FFFFFF">_</font>
															<xsl:value-of select="/*/Parties/SellerParty/Individual/AddressInSpain/Town" />
															<br />
															<xsl:value-of select="/*/Parties/SellerParty/Individual/AddressInSpain/Province" />
															<br />
															<xsl:value-of select="/*/Parties/SellerParty/Individual/AddressInSpain/CountryCode" />
														</xsl:if>
														<xsl:if
															test='/*/Parties/SellerParty/Individual/OverseasAddress!=""'>
															<xsl:value-of select="/*/Parties/SellerParty/Individual/OverseasAddress/Address" />
															<br />
															<xsl:value-of select="/*/Parties/SellerParty/Individual/OverseasAddress/PostCodeAndTown" />
															<br />
															<xsl:value-of select="/*/Parties/SellerParty/Individual/OverseasAddress/Province" />
															<br />
															<xsl:value-of select="/*/Parties/SellerParty/Individual/OverseasAddress/CountryCode" />
														</xsl:if>
													</td>
												</tr>
												<xsl:if test='/*/Parties/SellerParty/Individual/ContactDetails!=""'>
													<tr>
														<td width="30%" colspan="3">
															<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('ContactData','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion"/>:</h3>
														</td>
													</tr>
													<tr>
														<td colspan="3">
															<table border="0" cellpadding="2" cellspacing="0"
																width="100%">
																<tr>
																	<td width="25%">
																		<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Telephone','',concat('lang_',$lang))" />
																		<h3><xsl:value-of select="$traduccion"/>:</h3>
																		<font color="FFFFFF">__</font>
																		<xsl:value-of	select="/*/Parties/SellerParty/Individual/ContactDetails/Telephone" />
																	</td>
																	<td width="25%">
																		<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TeleFax','',concat('lang_',$lang))" />
																		<h3><xsl:value-of select="$traduccion"/>:</h3>
																		<font color="FFFFFF">___</font>
																		<xsl:value-of select="/*/Parties/SellerParty/Individual/ContactDetails/TeleFax" />
																	</td>
																	<td width="25%">
																		<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('WebAddress','',concat('lang_',$lang))" />
																		<h3><xsl:value-of select="$traduccion"/>:</h3>
																		<font color="FFFFFF">___</font>
																		<xsl:value-of select="/*/Parties/SellerParty/Individual/ContactDetails/WebAddress" />
																	</td>
																	<td width="25%">
																		<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('ElectronicMail','',concat('lang_',$lang))" />
																		<h3><xsl:value-of select="$traduccion"/>:</h3>
																		<font color="FFFFFF">___</font>
																		<xsl:value-of select="/*/Parties/SellerParty/Individual/ContactDetails/ElectronicMail" />
																	</td>
																</tr>
																<tr>
																	<td width="25%">
																		<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('ContactPersons','',concat('lang_',$lang))" />
																		<h3><xsl:value-of select="$traduccion"/>:</h3>
																		<font color="FFFFFF">__</font>
																		<xsl:value-of select="/*/Parties/SellerParty/Individual/ContactDetails/ContactPersons" />
																	</td>
																	<td width="25%">
																		<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('CnoCnae','',concat('lang_',$lang))" />
																		<h3><xsl:value-of select="$traduccion"/>:</h3>
																		<font color="FFFFFF">___</font>
																		<xsl:value-of select="/*/Parties/SellerParty/Individual/ContactDetails/CnoCnae" />
																	</td>
																	<td width="25%">
																		<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('INETownCode','',concat('lang_',$lang))" />
																		<h3><xsl:value-of select="$traduccion"/>:</h3>
																		<font color="FFFFFF">___</font>
																		<xsl:value-of select="/*/Parties/SellerParty/Individual/ContactDetails/INETownCode" />
																	</td>
																	<td width="25%">
																		<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('AdditionalContactDetails','',concat('lang_',$lang))" />
																		<h3><xsl:value-of select="$traduccion"/>:</h3>
																		<font color="FFFFFF">___</font>
																		<xsl:value-of select="/*/Parties/SellerParty/Individual/ContactDetails/AdditionalContactDetails" />
																	</td>
																</tr>
															</table>
														</td>
													</tr>
												</xsl:if>
											</xsl:if>
											<xsl:if test='/*/Parties/SellerParty/AdministrativeCentres!=""'>
												<tr>
													<td colspan="3">
														<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Centers','',concat('lang_',$lang))" />
														<h3><xsl:value-of select="$traduccion"/></h3>
														<table bordercolor="#e6f0fa" border="1" cellpadding="2" cellspacing="0"
															width="100%">
															<tr>
																<td width="8%" valign="top" align="center">
																	<xsl:variable name="traduccion2"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('CentreNumber','',concat('lang_',$lang))" />
																	<h3><xsl:value-of select="$traduccion2"/></h3>
																</td>
																<td width="8%" valign="top" align="center">
																	<xsl:variable name="traduccion2"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('RoleType','',concat('lang_',$lang))" />
																	<h3><xsl:value-of select="$traduccion2"/></h3>
																</td>
																<td width="17%" valign="top" align="center">
																	<xsl:variable name="traduccion2"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('CentreName','',concat('lang_',$lang))" />
																	<h3><xsl:value-of select="$traduccion2"/></h3>
																</td>
																<td width="17%" valign="top" align="center">
																	<xsl:variable name="traduccion2"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('CentreDescription','',concat('lang_',$lang))" />
																	<h3><xsl:value-of select="$traduccion2"/></h3>
																</td>
																<td width="17%" valign="top" align="center">
																	<xsl:variable name="traduccion2"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('CentreAddress','',concat('lang_',$lang))" />
																	<h3><xsl:value-of select="$traduccion2"/></h3>
																</td>
																<td width="17%" valign="top" align="center">
																	<xsl:variable name="traduccion2"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('CentreContactData','',concat('lang_',$lang))" />
																	<h3><xsl:value-of select="$traduccion2"/></h3>
																</td>
																<td width="8%" valign="top" align="center">
																	<xsl:variable name="traduccion2"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('PhysicalGLN','',concat('lang_',$lang))" />
																	<h3><xsl:value-of select="$traduccion2"/></h3>
																</td>
																<td width="8%" valign="top" align="center">
																	<xsl:variable name="traduccion2"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('OperationalPoint','',concat('lang_',$lang))" />
																	<h3><xsl:value-of select="$traduccion2"/></h3>
																</td>
															</tr>
															<xsl:for-each select="/*/Parties/SellerParty/AdministrativeCentres/AdministrativeCentre">
																<tr>
																	<td valign="top">
																		<xsl:choose>
																			<xsl:when test='CentreCode!=""'>
																				<xsl:apply-templates select="CentreCode" />
																			</xsl:when>
																			<xsl:otherwise>
																				<font color="FFFFFF">_</font>
																			</xsl:otherwise>
																		</xsl:choose>
																	</td>
																	<td valign="top">
																		<xsl:choose>
																			<xsl:when test='RoleTypeCode!=""'>
																				<xsl:variable name="toTranslate" select="RoleTypeCode" />
																				<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('RoleTypeCodeType',$toTranslate,concat('lang_',$lang))" />
																				<xsl:value-of select="$traduccion2" />
																			</xsl:when>
																			<xsl:otherwise>
																				<font color="FFFFFF">_</font>
																			</xsl:otherwise>
																		</xsl:choose>
																	</td>
																	<td valign="top">
																		<xsl:choose>
																			<xsl:when
																				test='(Name!="") or (FirstSurname!="") or (SecondSurname!="")'>
																				<xsl:value-of select="Name" />
																				<font color="FFFFFF">_</font>
																				<xsl:value-of select="FirstSurname" />
																				<font color="FFFFFF">_</font>
																				<xsl:value-of select="SecondSurname" />
																				<font color="FFFFFF">_</font>
																			</xsl:when>
																			<xsl:otherwise>
																				<font color="FFFFFF">_</font>
																			</xsl:otherwise>
																		</xsl:choose>
																	</td>
																	<td valign="top">
																		<xsl:choose>
																			<xsl:when test='CentreDescription!=""'>
																				<xsl:value-of select="CentreDescription" />
																			</xsl:when>
																			<xsl:otherwise>
																				<font color="FFFFFF">_</font>
																			</xsl:otherwise>
																		</xsl:choose>
																	</td>
																	<td valign="top">
																		<xsl:choose>
																			<xsl:when
																				test='(AddressInSpain!="") or (OverseasAddress!="")'>
																				<xsl:if test='AddressInSpain!=""'>
																					<xsl:value-of select="AddressInSpain/Address" />
																					<br />
																					<xsl:value-of select="AddressInSpain/PostCode" />
																					<font color="FFFFFF">__</font>
																					<xsl:value-of select="AddressInSpain/Town" />
																					<br />
																					<xsl:value-of select="AddressInSpain/Province" />
																					<br />
																					<xsl:value-of select="AddressInSpain/CountryCode" />
																				</xsl:if>
																				<xsl:if test='OverseasAddress!=""'>
																					<xsl:value-of select="OverseasAddress/Address" />
																					<br />
																					<xsl:value-of select="OverseasAddress/PostCodeAndTown" />
																					<br />
																					<xsl:value-of select="OverseasAddress/Province" />
																					<br />
																					<xsl:value-of select="OverseasAddress/CountryCode" />
																				</xsl:if>
																			</xsl:when>
																			<xsl:otherwise>
																				<font color="FFFFFF">_</font>
																			</xsl:otherwise>
																		</xsl:choose>
																	</td>
																	<td valign="top">
																		<xsl:choose>
																			<xsl:when test='ContactDetails!=""'>
																				<table border="0" cellpadding="2" cellspacing="0"
																					width="100%">
																					<tr>
																						<td width="50%">
																							<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Telephone','',concat('lang_',$lang))" />
																							<h3><xsl:value-of select="$traduccion2"/>:</h3>
																							<font color="FFFFFF">_</font>
																							<xsl:value-of select="ContactDetails/Telephone" />
																						</td>
																						<td width="50%">
																							<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TeleFax','',concat('lang_',$lang))" />
																							<h3><xsl:value-of select="$traduccion2"/>:</h3>
																							<font color="FFFFFF">_</font>
																							<xsl:value-of select="ContactDetails/TeleFax" />
																						</td>
																					</tr>
																					<tr>
																						<td width="50%">
																							<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('WebAddress','',concat('lang_',$lang))" />
																							<h3><xsl:value-of select="$traduccion2"/>:</h3>
																							<font color="FFFFFF">_</font>
																							<xsl:value-of select="ContactDetails/WebAddress" />
																						</td>
																						<td width="50%">
																							<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('ElectronicMail','',concat('lang_',$lang))" />
																							<h3><xsl:value-of select="$traduccion2"/>:</h3>
																							<font color="FFFFFF">_</font>
																							<xsl:value-of select="ContactDetails/ElectronicMail" />
																						</td>
																					</tr>
																					<tr>
																						<td width="50%">
																							<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('ContactPersons','',concat('lang_',$lang))" />
																							<h3><xsl:value-of select="$traduccion2"/>:</h3>
																							<font color="FFFFFF">_</font>
																							<xsl:value-of select="ContactDetails/ContactPersons" />
																						</td>
																						<td width="50%">
																							<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('CnoCnae','',concat('lang_',$lang))" />
																							<h3><xsl:value-of select="$traduccion2"/>:</h3>
																							<font color="FFFFFF">_</font>
																							<xsl:value-of select="ContactDetails/CnoCnae" />
																						</td>
																					</tr>
																					<tr>
																						<td width="50%">
																							<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('INETownCode','',concat('lang_',$lang))" />
																							<h3><xsl:value-of select="$traduccion2"/>:</h3>
																							<font color="FFFFFF">_</font>
																							<xsl:value-of select="ContactDetails/INETownCode" />
																						</td>
																						<td width="50%">
																							<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('AdditionalContactDetails','',concat('lang_',$lang))" />
																							<h3><xsl:value-of select="$traduccion2"/>:</h3>
																							<font color="FFFFFF">_</font>
																							<xsl:value-of select="ContactDetails/AdditionalContactDetails" />
																						</td>
																					</tr>
																				</table>
																			</xsl:when>
																			<xsl:otherwise>
																				<font color="FFFFFF">_</font>
																			</xsl:otherwise>
																		</xsl:choose>
																	</td>
																	<td valign="top">
																		<xsl:choose>
																			<xsl:when test='PhysicalGLN!=""'>
																				<xsl:apply-templates select="PhysicalGLN" />
																			</xsl:when>
																			<xsl:otherwise>
																				<font color="FFFFFF">_</font>
																			</xsl:otherwise>
																		</xsl:choose>
																	</td>
																	<td valign="top">
																		<xsl:choose>
																			<xsl:when test='LogicalOperationalPoint!=""'>
																				<xsl:apply-templates select="LogicalOperationalPoint" />
																			</xsl:when>
																			<xsl:otherwise>
																				<font color="FFFFFF">_</font>
																			</xsl:otherwise>
																		</xsl:choose>
																	</td>
																</tr>
															</xsl:for-each>
														</table>
													</td>
												</tr>
											</xsl:if>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr id="receptor">
						<td width="100%">
							<table border="0" cellpadding="2" cellspacing="0" width="100%">
								<tr>
									<td width="100%">
										<table border="0" cellpadding="2" cellspacing="0" width="100%">
											<tr>
												<td colspan="3">
													<hr color="a0d7a0"/>
												</td>
											</tr>
											<tr>
												<td colspan="2">
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('ReceiverData','',concat('lang_',$lang))" />
													<h2><xsl:value-of select="$traduccion"/></h2>
												</td>
												<td align="right">
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('MoreData','',concat('lang_',$lang))" />
													<a id="datosReceptorLink" href="javascript:mostrarCapa('datosReceptor')"><xsl:value-of select="$traduccion"/></a>
												</td>
											</tr>
											<tr>
												<td colspan="3">
													<font color="FFFFFF">_</font>
												</td>
											</tr>
											<xsl:if test='/*/Parties/BuyerParty/LegalEntity!=""'>
												<tr>
													<td width="50%" colspan="2">
														<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('CorporateName','',concat('lang_',$lang))" />
														<h3><xsl:value-of select="$traduccion"/></h3>
														<font color="FFFFFF">__</font>
														<xsl:value-of select="/*/Parties/BuyerParty/LegalEntity/CorporateName"/>
													</td>
													<td width="50%">
														<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TaxIdentificationNumber','',concat('lang_',$lang))" />
														<h3><xsl:value-of select="$traduccion"/>:</h3>
														<font color="FFFFFF">__</font>
														<xsl:value-of select="/*/Parties/BuyerParty/TaxIdentification/TaxIdentificationNumber" />
													</td>
												</tr>
											</xsl:if>
											<xsl:if test='/*/Parties/BuyerParty/Individual!=""'>
												<tr>
													<td width="50%" colspan="2">
														<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('NameAndSurnames','',concat('lang_',$lang))" />
														<h3><xsl:value-of select="$traduccion"/>:</h3>
														<font color="FFFFFF">__</font>
														<xsl:value-of select="/*/Parties/BuyerParty/Individual/Name" />
														<font color="FFFFFF">_</font>
														<xsl:value-of select="/*/Parties/BuyerParty/Individual/FirstSurname" />
														<font color="FFFFFF">_</font>
														<xsl:value-of select="/*/Parties/BuyerParty/Individual/SecondSurname" />
														<font color="FFFFFF">_</font>
													</td>
													<td width="50%">
														<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TaxIdentificationNumber','',concat('lang_',$lang))" />
														<h3><xsl:value-of select="$traduccion"/>:</h3>
														<font color="FFFFFF">__</font>
														<xsl:value-of select="/*/Parties/BuyerParty/TaxIdentification/TaxIdentificationNumber" />
													</td>
												</tr>
											</xsl:if>
										</table>
									</td>
								</tr>
								<tr id="datosReceptor" style="display:none">
									<td width="100%">
										<table border="0" cellpadding="2" cellspacing="0" width="100%">
											<tr>
												<td width="50%" colspan="2">
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('PersonType','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/>:</h3>
													<font color="FFFFFF">__</font>
													<xsl:variable name="toTranslate" select="/*/Parties/BuyerParty/TaxIdentification/PersonTypeCode" />
													<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('PersonTypeCode',$toTranslate,concat('lang_',$lang))" />
													<xsl:value-of select="$traduccion2" />
												</td>
												<td width="50%">
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('ResidenceType','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/>:</h3>
													<font color="FFFFFF">__</font>
													<xsl:variable name="toTranslate" select="/*/Parties/BuyerParty/TaxIdentification/ResidenceTypeCode" />
													<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('ResidenceTypeCode',$toTranslate,concat('lang_',$lang))" />
													<xsl:value-of select="$traduccion2" />
												</td>
											</tr>
											<xsl:if test='/*/Parties/BuyerParty/LegalEntity!=""'>
												<tr>
													<td valign="top" width="10%">
														<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Address','',concat('lang_',$lang))" />
														<h3><xsl:value-of select="$traduccion"/>:</h3>
													</td>
													<td valign="top" width="40%">
														<xsl:if test='/*/Parties/BuyerParty/LegalEntity/AddressInSpain!=""'>
															<xsl:value-of select="/*/Parties/BuyerParty/LegalEntity/AddressInSpain/Address" />
															<br />
															<xsl:value-of select="/*/Parties/BuyerParty/LegalEntity/AddressInSpain/PostCode" />
															<font color="FFFFFF">__</font>
															<xsl:value-of select="/*/Parties/BuyerParty/LegalEntity/AddressInSpain/Town" />
															<br />
															<xsl:value-of select="/*/Parties/BuyerParty/LegalEntity/AddressInSpain/Province" />
															<br />
															<xsl:value-of select="/*/Parties/BuyerParty/LegalEntity/AddressInSpain/CountryCode" />
														</xsl:if>
														<xsl:if test='/*/Parties/BuyerParty/LegalEntity/OverseasAddress!=""'>
															<xsl:value-of select="/*/Parties/BuyerParty/LegalEntity/OverseasAddress/Address" />
															<br />
															<xsl:value-of select="/*/Parties/BuyerParty/LegalEntity/OverseasAddress/PostCodeAndTown" />
															<br />
															<xsl:value-of select="/*/Parties/BuyerParty/LegalEntity/OverseasAddress/Province" />
															<br />
															<xsl:value-of select="/*/Parties/BuyerParty/LegalEntity/OverseasAddress/CountryCode" />
														</xsl:if>
													</td>
													<xsl:if test='/*/Parties/BuyerParty/LegalEntity/TradeName!=""'>
														<td valign="top" width="50%">
															<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TradeName','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion"/>:</h3>
															<font color="FFFFFF">__</font>
															<xsl:value-of select="/*/Parties/BuyerParty/LegalEntity/TradeName" />
														</td>
													</xsl:if>
												</tr>
												<xsl:if test='/*/Parties/BuyerParty/LegalEntity/RegistrationData!=""'>
													<tr>
														<td width="30%" colspan="3">
															<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('RegistryData','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion"/>:</h3>
														</td>
													</tr>
													<tr>
														<td colspan="3">
															<table border="0" cellpadding="2" cellspacing="0"
																width="100%">
																<tr>
																	<td width="25%">
																		<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Book','',concat('lang_',$lang))" />
																		<h3><xsl:value-of select="$traduccion"/>:</h3>
																		<font color="FFFFFF">__</font>
																		<xsl:value-of select="/*/Parties/BuyerParty/LegalEntity/RegistrationData/Book" />
																	</td>
																	<td width="25%">
																		<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('RegisterOfCompaniesLocation','',concat('lang_',$lang))" />
																		<h3><xsl:value-of select="$traduccion"/>:</h3>
																		<font color="FFFFFF">___</font>
																		<xsl:value-of select="/*/Parties/BuyerParty/LegalEntity/RegistrationData/RegisterOfCompaniesLocation" />
																	</td>
																	<td width="25%">
																		<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Sheet','',concat('lang_',$lang))" />
																		<h3><xsl:value-of select="$traduccion"/>:</h3>
																		<font color="FFFFFF">___</font>
																		<xsl:value-of select="/*/Parties/BuyerParty/LegalEntity/RegistrationData/Sheet" />
																	</td>
																	<td width="25%">
																		<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Folio','',concat('lang_',$lang))" />
																		<h3><xsl:value-of select="$traduccion"/>:</h3>
																		<font color="FFFFFF">___</font>
																		<xsl:value-of select="/*/Parties/BuyerParty/LegalEntity/RegistrationData/Folio" />
																	</td>
																</tr>
																<tr>
																	<td width="25%">
																		<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Section','',concat('lang_',$lang))" />
																		<h3><xsl:value-of select="$traduccion"/>:</h3>
																		<font color="FFFFFF">__</font>
																		<xsl:value-of select="/*/Parties/BuyerParty/LegalEntity/RegistrationData/Section" />
																	</td>
																	<td width="25%">
																		<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Volume','',concat('lang_',$lang))" />
																		<h3><xsl:value-of select="$traduccion"/>:</h3>
																		<font color="FFFFFF">___</font>
																		<xsl:value-of select="/*/Parties/BuyerParty/LegalEntity/RegistrationData/Volume" />
																	</td>
																	<td width="25%">
																		<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('AdditionalRegistrationData','',concat('lang_',$lang))" />
																		<h3><xsl:value-of select="$traduccion"/>:</h3>
																		<font color="FFFFFF">___</font>
																		<xsl:value-of select="/*/Parties/BuyerParty/LegalEntity/RegistrationData/AdditionalRegistrationData" />
																	</td>
																</tr>
															</table>
														</td>
													</tr>
												</xsl:if>
												<xsl:if test='/*/Parties/BuyerParty/LegalEntity/ContactDetails!=""'>
													<tr>
														<td width="30%" colspan="3">
															<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('ContactData','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion"/>:</h3>
														</td>
													</tr>
													<tr>
														<td colspan="3">
															<table border="0" cellpadding="2" cellspacing="0"
																width="100%">
																<tr>
																	<td width="25%">
																		<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Telephone','',concat('lang_',$lang))" />
																		<h3><xsl:value-of select="$traduccion"/>:</h3>
																		<font color="FFFFFF">__</font>
																		<xsl:value-of	select="/*/Parties/BuyerParty/LegalEntity/ContactDetails/Telephone" />
																	</td>
																	<td width="25%">
																		<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TeleFax','',concat('lang_',$lang))" />
																		<h3><xsl:value-of select="$traduccion"/>:</h3>
																		<font color="FFFFFF">___</font>
																		<xsl:value-of select="/*/Parties/BuyerParty/LegalEntity/ContactDetails/TeleFax" />
																	</td>
																	<td width="25%">
																		<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('WebAddress','',concat('lang_',$lang))" />
																		<h3><xsl:value-of select="$traduccion"/>:</h3>
																		<font color="FFFFFF">___</font>
																		<xsl:value-of select="/*/Parties/BuyerParty/LegalEntity/ContactDetails/WebAddress" />
																	</td>
																	<td width="25%">
																		<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('ElectronicMail','',concat('lang_',$lang))" />
																		<h3><xsl:value-of select="$traduccion"/>:</h3>
																		<font color="FFFFFF">___</font>
																		<xsl:value-of select="/*/Parties/BuyerParty/LegalEntity/ContactDetails/ElectronicMail" />
																	</td>
																</tr>
																<tr>
																	<td width="25%">
																		<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('ContactPersons','',concat('lang_',$lang))" />
																		<h3><xsl:value-of select="$traduccion"/>:</h3>
																		<font color="FFFFFF">__</font>
																		<xsl:value-of select="/*/Parties/BuyerParty/LegalEntity/ContactDetails/ContactPersons" />
																	</td>
																	<td width="25%">
																		<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('CnoCnae','',concat('lang_',$lang))" />
																		<h3><xsl:value-of select="$traduccion"/>:</h3>
																		<font color="FFFFFF">___</font>
																		<xsl:value-of select="/*/Parties/BuyerParty/LegalEntity/ContactDetails/CnoCnae" />
																	</td>
																	<td width="25%">
																		<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('INETownCode','',concat('lang_',$lang))" />
																		<h3><xsl:value-of select="$traduccion"/>:</h3>
																		<font color="FFFFFF">___</font>
																		<xsl:value-of select="/*/Parties/BuyerParty/LegalEntity/ContactDetails/INETownCode" />
																	</td>
																	<td width="25%">
																		<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('AdditionalContactDetails','',concat('lang_',$lang))" />
																		<h3><xsl:value-of select="$traduccion"/>:</h3>
																		<font color="FFFFFF">___</font>
																		<xsl:value-of select="/*/Parties/BuyerParty/LegalEntity/ContactDetails/AdditionalContactDetails" />
																	</td>
																</tr>
															</table>
														</td>
													</tr>
												</xsl:if>
											</xsl:if>
											<xsl:if test='/*/Parties/BuyerParty/Individual!=""'>
												<tr>
													<td valign="top" width="10%">
														<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Address','',concat('lang_',$lang))" />
														<h3><xsl:value-of select="$traduccion"/>:</h3>
													</td>
													<td valign="top" width="40%">
														<xsl:if test='/*/Parties/BuyerParty/Individual/AddressInSpain!=""'>
															<xsl:value-of select="/*/Parties/BuyerParty/Individual/AddressInSpain/Address" />
															<br />
															<xsl:value-of select="/*/Parties/BuyerParty/Individual/AddressInSpain/PostCode" />
															<font color="FFFFFF">_</font>
															<font color="FFFFFF">_</font>
															<xsl:value-of select="/*/Parties/BuyerParty/Individual/AddressInSpain/Town" />
															<br />
															<xsl:value-of select="/*/Parties/BuyerParty/Individual/AddressInSpain/Province" />
															<br />
															<xsl:value-of select="/*/Parties/BuyerParty/Individual/AddressInSpain/CountryCode" />
														</xsl:if>
														<xsl:if
															test='/*/Parties/BuyerParty/Individual/OverseasAddress!=""'>
															<xsl:value-of select="/*/Parties/BuyerParty/Individual/OverseasAddress/Address" />
															<br />
															<xsl:value-of select="/*/Parties/BuyerParty/Individual/OverseasAddress/PostCodeAndTown" />
															<br />
															<xsl:value-of select="/*/Parties/BuyerParty/Individual/OverseasAddress/Province" />
															<br />
															<xsl:value-of select="/*/Parties/BuyerParty/Individual/OverseasAddress/CountryCode" />
														</xsl:if>
													</td>
												</tr>
												<xsl:if test='/*/Parties/BuyerParty/Individual/ContactDetails!=""'>
													<tr>
														<td width="30%" colspan="3">
															<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('ContactData','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion"/>:</h3>
														</td>
													</tr>
													<tr>
														<td colspan="3">
															<table border="0" cellpadding="2" cellspacing="0"
																width="100%">
																<tr>
																	<td width="25%">
																		<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Telephone','',concat('lang_',$lang))" />
																		<h3><xsl:value-of select="$traduccion"/>:</h3>
																		<font color="FFFFFF">__</font>
																		<xsl:value-of	select="/*/Parties/BuyerParty/Individual/ContactDetails/Telephone" />
																	</td>
																	<td width="25%">
																		<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TeleFax','',concat('lang_',$lang))" />
																		<h3><xsl:value-of select="$traduccion"/>:</h3>
																		<font color="FFFFFF">___</font>
																		<xsl:value-of select="/*/Parties/BuyerParty/Individual/ContactDetails/TeleFax" />
																	</td>
																	<td width="25%">
																		<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('WebAddress','',concat('lang_',$lang))" />
																		<h3><xsl:value-of select="$traduccion"/>:</h3>
																		<font color="FFFFFF">___</font>
																		<xsl:value-of select="/*/Parties/BuyerParty/Individual/ContactDetails/WebAddress" />
																	</td>
																	<td width="25%">
																		<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('ElectronicMail','',concat('lang_',$lang))" />
																		<h3><xsl:value-of select="$traduccion"/>:</h3>
																		<font color="FFFFFF">___</font>
																		<xsl:value-of select="/*/Parties/BuyerParty/Individual/ContactDetails/ElectronicMail" />
																	</td>
																</tr>
																<tr>
																	<td width="25%">
																		<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('ContactPersons','',concat('lang_',$lang))" />
																		<h3><xsl:value-of select="$traduccion"/>:</h3>
																		<font color="FFFFFF">__</font>
																		<xsl:value-of select="/*/Parties/BuyerParty/Individual/ContactDetails/ContactPersons" />
																	</td>
																	<td width="25%">
																		<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('CnoCnae','',concat('lang_',$lang))" />
																		<h3><xsl:value-of select="$traduccion"/>:</h3>
																		<font color="FFFFFF">___</font>
																		<xsl:value-of select="/*/Parties/BuyerParty/Individual/ContactDetails/CnoCnae" />
																	</td>
																	<td width="25%">
																		<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('INETownCode','',concat('lang_',$lang))" />
																		<h3><xsl:value-of select="$traduccion"/>:</h3>
																		<font color="FFFFFF">___</font>
																		<xsl:value-of select="/*/Parties/BuyerParty/Individual/ContactDetails/INETownCode" />
																	</td>
																	<td width="25%">
																		<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('AdditionalContactDetails','',concat('lang_',$lang))" />
																		<h3><xsl:value-of select="$traduccion"/>:</h3>
																		<font color="FFFFFF">___</font>
																		<xsl:value-of select="/*/Parties/BuyerParty/Individual/ContactDetails/AdditionalContactDetails" />
																	</td>
																</tr>
															</table>
														</td>
													</tr>
												</xsl:if>
											</xsl:if>
											<xsl:if test='/*/Parties/BuyerParty/AdministrativeCentres!=""'>
												<tr>
													<td colspan="3">
														<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Centers','',concat('lang_',$lang))" />
														<h3><xsl:value-of select="$traduccion"/></h3>
														<table bordercolor="#e6f0fa" border="1" cellpadding="2" cellspacing="0" width="100%">
															<tr>
																<td width="8%" valign="top" align="center">
																	<xsl:variable name="traduccion2"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('CentreNumber','',concat('lang_',$lang))" />
																	<h3><xsl:value-of select="$traduccion2"/></h3>
																</td>
																<td width="8%" valign="top" align="center">
																	<xsl:variable name="traduccion2"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('RoleType','',concat('lang_',$lang))" />
																	<h3><xsl:value-of select="$traduccion2"/></h3>
																</td>
																<td width="17%" valign="top" align="center">
																	<xsl:variable name="traduccion2"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('CentreName','',concat('lang_',$lang))" />
																	<h3><xsl:value-of select="$traduccion2"/></h3>
																</td>
																<td width="17%" valign="top" align="center">
																	<xsl:variable name="traduccion2"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('CentreDescription','',concat('lang_',$lang))" />
																	<h3><xsl:value-of select="$traduccion2"/></h3>
																</td>
																<td width="17%" valign="top" align="center">
																	<xsl:variable name="traduccion2"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('CentreAddress','',concat('lang_',$lang))" />
																	<h3><xsl:value-of select="$traduccion2"/></h3>
																</td>
																<td width="17%" valign="top" align="center">
																	<xsl:variable name="traduccion2"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('CentreContactData','',concat('lang_',$lang))" />
																	<h3><xsl:value-of select="$traduccion2"/></h3>
																</td>
																<td width="8%" valign="top" align="center">
																	<xsl:variable name="traduccion2"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('PhysicalGLN','',concat('lang_',$lang))" />
																	<h3><xsl:value-of select="$traduccion2"/></h3>
																</td>
																<td width="8%" valign="top" align="center">
																	<xsl:variable name="traduccion2"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('OperationalPoint','',concat('lang_',$lang))" />
																	<h3><xsl:value-of select="$traduccion2"/></h3>
																</td>
															</tr>
															<xsl:for-each select="/*/Parties/BuyerParty/AdministrativeCentres/AdministrativeCentre">
																<tr>
																	<td valign="top">
																		<xsl:choose>
																			<xsl:when test='CentreCode!=""'>
																				<xsl:apply-templates select="CentreCode" />
																			</xsl:when>
																			<xsl:otherwise>
																				<font color="FFFFFF">_</font>
																			</xsl:otherwise>
																		</xsl:choose>
																	</td>
																	<td valign="top">
																		<xsl:choose>
																			<xsl:when test='RoleTypeCode!=""'>
																				<xsl:variable name="toTranslate" select="RoleTypeCode" />
																				<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('RoleTypeCodeType',$toTranslate,concat('lang_',$lang))" />
																				<xsl:value-of select="$traduccion2" />
																			</xsl:when>
																			<xsl:otherwise>
																				<font color="FFFFFF">_</font>
																			</xsl:otherwise>
																		</xsl:choose>
																	</td>
																	<td valign="top">
																		<xsl:choose>
																			<xsl:when
																				test='(Name!="") or (FirstSurname!="") or (SecondSurname!="")'>
																				<xsl:value-of select="Name" />
																				<font color="FFFFFF">_</font>
																				<xsl:value-of select="FirstSurname" />
																				<font color="FFFFFF">_</font>
																				<xsl:value-of select="SecondSurname" />
																				<font color="FFFFFF">_</font>
																			</xsl:when>
																			<xsl:otherwise>
																				<font color="FFFFFF">_</font>
																			</xsl:otherwise>
																		</xsl:choose>
																	</td>
																	<td valign="top">
																		<xsl:choose>
																			<xsl:when test='CentreDescription!=""'>
																				<xsl:value-of select="CentreDescription" />
																			</xsl:when>
																			<xsl:otherwise>
																				<font color="FFFFFF">_</font>
																			</xsl:otherwise>
																		</xsl:choose>
																	</td>
																	<td valign="top">
																		<xsl:choose>
																			<xsl:when
																				test='(AddressInSpain!="") or (OverseasAddress!="")'>
																				<xsl:if test='AddressInSpain!=""'>
																					<xsl:value-of select="AddressInSpain/Address" />
																					<br />
																					<xsl:value-of select="AddressInSpain/PostCode" />
																					<font color="FFFFFF">__</font>
																					<xsl:value-of select="AddressInSpain/Town" />
																					<br />
																					<xsl:value-of select="AddressInSpain/Province" />
																					<br />
																					<xsl:value-of select="AddressInSpain/CountryCode" />
																				</xsl:if>
																				<xsl:if test='OverseasAddress!=""'>
																					<xsl:value-of select="OverseasAddress/Address" />
																					<br />
																					<xsl:value-of select="OverseasAddress/PostCodeAndTown" />
																					<br />
																					<xsl:value-of select="OverseasAddress/Province" />
																					<br />
																					<xsl:value-of select="OverseasAddress/CountryCode" />
																				</xsl:if>
																			</xsl:when>
																			<xsl:otherwise>
																				<font color="FFFFFF">_</font>
																			</xsl:otherwise>
																		</xsl:choose>
																	</td>
																	<td valign="top">
																		<xsl:choose>
																			<xsl:when test='ContactDetails!=""'>
																				<table border="0" cellpadding="2" cellspacing="0"
																					width="100%">
																					<tr>
																						<td width="50%">
																							<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Telephone','',concat('lang_',$lang))" />
																							<h3><xsl:value-of select="$traduccion2"/>:</h3>
																							<font color="FFFFFF">_</font>
																							<xsl:value-of select="ContactDetails/Telephone" />
																						</td>
																						<td width="50%">
																							<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TeleFax','',concat('lang_',$lang))" />
																							<h3><xsl:value-of select="$traduccion2"/>:</h3>
																							<font color="FFFFFF">_</font>
																							<xsl:value-of select="ContactDetails/TeleFax" />
																						</td>
																					</tr>
																					<tr>
																						<td width="50%">
																							<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('WebAddress','',concat('lang_',$lang))" />
																							<h3><xsl:value-of select="$traduccion2"/>:</h3>
																							<font color="FFFFFF">_</font>
																							<xsl:value-of select="ContactDetails/WebAddress" />
																						</td>
																						<td width="50%">
																							<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('ElectronicMail','',concat('lang_',$lang))" />
																							<h3><xsl:value-of select="$traduccion2"/>:</h3>
																							<font color="FFFFFF">_</font>
																							<xsl:value-of select="ContactDetails/ElectronicMail" />
																						</td>
																					</tr>
																					<tr>
																						<td width="50%">
																							<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('ContactPersons','',concat('lang_',$lang))" />
																							<h3><xsl:value-of select="$traduccion2"/>:</h3>
																							<font color="FFFFFF">_</font>
																							<xsl:value-of select="ContactDetails/ContactPersons" />
																						</td>
																						<td width="50%">
																							<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('CnoCnae','',concat('lang_',$lang))" />
																							<h3><xsl:value-of select="$traduccion2"/>:</h3>
																							<font color="FFFFFF">_</font>
																							<xsl:value-of select="ContactDetails/CnoCnae" />
																						</td>
																					</tr>
																					<tr>
																						<td width="50%">
																							<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('INETownCode','',concat('lang_',$lang))" />
																							<h3><xsl:value-of select="$traduccion2"/>:</h3>
																							<font color="FFFFFF">_</font>
																							<xsl:value-of select="ContactDetails/INETownCode" />
																						</td>
																						<td width="50%">
																							<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('AdditionalContactDetails','',concat('lang_',$lang))" />
																							<h3><xsl:value-of select="$traduccion2"/>:</h3>
																							<font color="FFFFFF">_</font>
																							<xsl:value-of select="ContactDetails/AdditionalContactDetails" />
																						</td>
																					</tr>
																				</table>
																			</xsl:when>
																			<xsl:otherwise>
																				<font color="FFFFFF">_</font>
																			</xsl:otherwise>
																		</xsl:choose>
																	</td>
																	<td valign="top">
																		<xsl:choose>
																			<xsl:when test='PhysicalGLN!=""'>
																				<xsl:apply-templates select="PhysicalGLN" />
																			</xsl:when>
																			<xsl:otherwise>
																				<font color="FFFFFF">_</font>
																			</xsl:otherwise>
																		</xsl:choose>
																	</td>
																	<td valign="top">
																		<xsl:choose>
																			<xsl:when test='LogicalOperationalPoint!=""'>
																				<xsl:apply-templates select="LogicalOperationalPoint" />
																			</xsl:when>
																			<xsl:otherwise>
																				<font color="FFFFFF">_</font>
																			</xsl:otherwise>
																		</xsl:choose>
																	</td>
																</tr>
															</xsl:for-each>
														</table>
													</td>
												</tr>
											</xsl:if>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<xsl:apply-templates select="/*/FileHeader/ThirdParty" />
					<xsl:apply-templates select="/*/Invoices/Invoice" />
					<tr id="listadoFacturas">
						<td width="100%">
							<table border="0" cellpadding="2" cellspacing="0" width="100%">
								<tr>
									<td>
										<font color="FFFFFF">_</font>
									</td>
								</tr>
								<tr>
									<td>
										<hr color="a0d7a0"/>
									</td>
								</tr>
								<tr>
									<td>
										<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('InvoicesList','',concat('lang_',$lang))" />
										<h2><xsl:value-of select="$traduccion"/></h2>
									</td>
								</tr>
								<tr>
									<td>
										<font color="FFFFFF">_</font>
									</td>
								</tr>
								<tr>
									<td width="100%">
										<table bordercolor="#e6f0fa" border="1" cellpadding="2" cellspacing="0" width="100%">
											<tr>
												<td width="20%" align="center">
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Number','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/></h3>
												</td>
												<td width="20%" align="center">
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Serie','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/></h3>
												</td>
												<td width="20%" align="center">
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('ExpedDate','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/></h3>
												</td>
												<td width="20%" align="center">
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('GrossAmount','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/></h3>
												</td>
												<td width="20%" align="center">
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TotalEuros','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/></h3>
												</td>
											</tr>
											<xsl:for-each select="/*/Invoices/Invoice">
												<tr style="cursor:hand"
													onclick="mostrarFactura('{InvoiceHeader/InvoiceNumber}')"
													onMouseOver="this.style.background='#e6f0fa'" onMouseOut="this.style.background='#FFFFFF'">
													<td align="center">
														<xsl:value-of select="InvoiceHeader/InvoiceNumber" />
													</td>
													<td align="center">
														<xsl:choose>
															<xsl:when test='InvoiceHeader/InvoiceSeriesCode!=""'>
																<xsl:value-of select="InvoiceHeader/InvoiceSeriesCode" />
															</xsl:when>
															<xsl:otherwise>
																<font color="FFFFFF">_</font>
															</xsl:otherwise>
														</xsl:choose>
													</td>
													<td align="center">
														<xsl:value-of select="substring(InvoiceIssueData/IssueDate,9,2)" />
														-
														<xsl:value-of select="substring(InvoiceIssueData/IssueDate,6,2)" />
														-
														<xsl:value-of select="substring(InvoiceIssueData/IssueDate,1,4)" />
													</td>
													<td align="right">
														<xsl:variable name="decimales" select="InvoiceTotals/TotalGrossAmount" />
														<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)" />
													</td>
													<td align="right">
														<xsl:variable name="decimales" select="InvoiceTotals/TotalExecutableAmount" />
														<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)" />
													</td>
												</tr>
											</xsl:for-each>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<xsl:apply-templates select="/*/FileHeader/FactoringAssignmentData" />
					<tr>
						<td>
							<font color="FFFFFF">_</font>
						</td>
					</tr>
					<tr>
						<td>
							<hr color="a0d7a0"/>
						</td>
					</tr>
					<tr>
						<td>
							<font color="FFFFFF">_</font>
						</td>
					</tr>
					<tr>
						<td>
							<font color="FFFFFF">_</font>
						</td>
					</tr>
					<tr>
						<td>
							<font color="FFFFFF">_</font>
						</td>
					</tr>
				</table>
			</center>
		</div>
	</body>
	</html>
	</xsl:template>
	<xsl:template match="/*/FileHeader/ThirdParty">
		<xsl:variable name="lang" select="/*/Invoices/Invoice/InvoiceIssueData/LanguageName"/>
		<tr id="tercero">
			 <td width="100%">
				<table border="0" cellpadding="2" cellspacing="0" width="100%">
					<tr>
						 <td width="100%">
							<table border="0" cellpadding="2" cellspacing="0" width="100%">	
								<tr>
									<td colspan="3"><font color="FFFFFF">_</font></td>
								</tr>
								<tr>
									<td colspan="3">
										<hr color="#a0d7a0"/>
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('ThirdPartyData','',concat('lang_',$lang))" />
										<h2><xsl:value-of select="$traduccion"/></h2>
									</td>
									<td align="right">
										<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('MoreData','',concat('lang_',$lang))" />
										<a id="datosTerceroLink" href="javascript:mostrarCapa('datosTercero')"><xsl:value-of select="$traduccion"/></a>
									</td>         	
								</tr>
								<tr>
									<td colspan="3"><font color="FFFFFF">_</font></td>        	
								</tr>
									<xsl:if test='/*/FileHeader/ThirdParty/LegalEntity!=""'>
									<tr>
										<td width="50%" colspan="2">
											<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('CorporateName','',concat('lang_',$lang))" />
											<h3><xsl:value-of select="$traduccion"/></h3>
											<font color="FFFFFF">__</font>
											<xsl:value-of select="/*/FileHeader/ThirdParty/LegalEntity/CorporateName"/>
											</td>
											<td width="50%">
												<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TaxIdentificationNumber','',concat('lang_',$lang))" />
												<h3><xsl:value-of select="$traduccion"/>:</h3>
												<font color="FFFFFF">__</font>
												<xsl:value-of select="/*/FileHeader/ThirdParty/TaxIdentification/TaxIdentificationNumber" />
											</td>
									</tr>
									</xsl:if>
									<xsl:if test='/*/FileHeader/ThirdParty/Individual!=""'>
									<tr>
										<td width="50%" colspan="2">
											<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('NameAndSurnames','',concat('lang_',$lang))" />
											<h3><xsl:value-of select="$traduccion"/>:</h3>
											<font color="FFFFFF">__</font>
											<xsl:value-of select="/*/FileHeader/ThirdParty/Individual/Name" />
											<font color="FFFFFF">_</font>
											<xsl:value-of select="/*/FileHeader/ThirdParty/Individual/FirstSurname" />
											<font color="FFFFFF">_</font>
											<xsl:value-of select="/*/FileHeader/ThirdParty/Individual/SecondSurname" />
											<font color="FFFFFF">_</font>
										</td>
										<td width="50%">
											<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TaxIdentificationNumber','',concat('lang_',$lang))" />
											<h3><xsl:value-of select="$traduccion"/>:</h3>
											<font color="FFFFFF">__</font>
											<xsl:value-of select="/*/FileHeader/ThirdParty/TaxIdentification/TaxIdentificationNumber" />
										</td>
									</tr>
									</xsl:if>
							</table>
						</td>
					</tr>	
					<tr id="datosTercero" style="display:none">
						<td width="100%">
							<table border="0" cellpadding="2" cellspacing="0" width="100%">
								<tr>
									<td width="50%" colspan="2">
										<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('PersonType','',concat('lang_',$lang))" />
										<h3><xsl:value-of select="$traduccion"/>:</h3>
										<font color="FFFFFF">__</font>
										<xsl:variable name="toTranslate" select="/*/FileHeader/ThirdParty/TaxIdentification/PersonTypeCode" />
										<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('PersonTypeCode',$toTranslate,concat('lang_',$lang))" />
										<xsl:value-of select="$traduccion2" />
									</td>
									<td width="50%">
										<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('ResidenceType','',concat('lang_',$lang))" />
										<h3><xsl:value-of select="$traduccion"/>:</h3>
										<font color="FFFFFF">__</font>
										<xsl:variable name="toTranslate" select="/*/FileHeader/ThirdParty/TaxIdentification/ResidenceTypeCode" />
										<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('ResidenceTypeCode',$toTranslate,concat('lang_',$lang))" />
										<xsl:value-of select="$traduccion2" />
									</td>
								</tr>
								<xsl:if test='/*/FileHeader/ThirdParty/LegalEntity!=""'>
									<tr>
										<td valign="top" width="10%">
											<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Address','',concat('lang_',$lang))" />
											<h3><xsl:value-of select="$traduccion"/>:</h3>
										</td>
										<td valign="top" width="40%">
											<xsl:if test='/*/FileHeader/ThirdParty/LegalEntity/AddressInSpain!=""'>
												<xsl:value-of select="/*/FileHeader/ThirdParty/LegalEntity/AddressInSpain/Address" />
												<br />
												<xsl:value-of select="/*/FileHeader/ThirdParty/LegalEntity/AddressInSpain/PostCode" />
												<font color="FFFFFF">__</font>
												<xsl:value-of select="/*/FileHeader/ThirdParty/LegalEntity/AddressInSpain/Town" />
												<br />
												<xsl:value-of select="/*/FileHeader/ThirdParty/LegalEntity/AddressInSpain/Province" />
												<br />
												<xsl:value-of select="/*/FileHeader/ThirdParty/LegalEntity/AddressInSpain/CountryCode" />
											</xsl:if>
											<xsl:if test='/*/FileHeader/ThirdParty/LegalEntity/OverseasAddress!=""'>
												<xsl:value-of select="/*/FileHeader/ThirdParty/LegalEntity/OverseasAddress/Address" />
												<br />
												<xsl:value-of select="/*/FileHeader/ThirdParty/LegalEntity/OverseasAddress/PostCodeAndTown" />
												<br />
												<xsl:value-of select="/*/FileHeader/ThirdParty/LegalEntity/OverseasAddress/Province" />
												<br />
												<xsl:value-of select="/*/FileHeader/ThirdParty/LegalEntity/OverseasAddress/CountryCode" />
											</xsl:if>
										</td>
										<xsl:if test='/*/FileHeader/ThirdParty/LegalEntity/TradeName!=""'>
											<td valign="top" width="50%">
												<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TradeName','',concat('lang_',$lang))" />
												<h3><xsl:value-of select="$traduccion"/>:</h3>
												<font color="FFFFFF">__</font>
												<xsl:value-of select="/*/FileHeader/ThirdParty/LegalEntity/TradeName" />
											</td>
										</xsl:if>
									</tr>
									<xsl:if test='/*/FileHeader/ThirdParty/LegalEntity/RegistrationData!=""'>
										<tr>
											<td width="30%" colspan="3">
												<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('RegistryData','',concat('lang_',$lang))" />
												<h3><xsl:value-of select="$traduccion"/>:</h3>
											</td>
										</tr>
										<tr>
											<td colspan="3">
												<table border="0" cellpadding="2" cellspacing="0"
													width="100%">
													<tr>
														<td width="25%">
															<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Book','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion"/>:</h3>
															<font color="FFFFFF">__</font>
															<xsl:value-of select="/*/FileHeader/ThirdParty/LegalEntity/RegistrationData/Book" />
														</td>
														<td width="25%">
															<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('RegisterOfCompaniesLocation','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion"/>:</h3>
															<font color="FFFFFF">___</font>
															<xsl:value-of select="/*/FileHeader/ThirdParty/LegalEntity/RegistrationData/RegisterOfCompaniesLocation" />
														</td>
														<td width="25%">
															<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Sheet','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion"/>:</h3>
															<font color="FFFFFF">___</font>
															<xsl:value-of select="/*/FileHeader/ThirdParty/LegalEntity/RegistrationData/Sheet" />
														</td>
														<td width="25%">
															<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Folio','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion"/>:</h3>
															<font color="FFFFFF">___</font>
															<xsl:value-of select="/*/FileHeader/ThirdParty/LegalEntity/RegistrationData/Folio" />
														</td>
													</tr>
													<tr>
														<td width="25%">
															<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Section','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion"/>:</h3>
															<font color="FFFFFF">__</font>
															<xsl:value-of select="/*/FileHeader/ThirdParty/LegalEntity/RegistrationData/Section" />
														</td>
														<td width="25%">
															<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Volume','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion"/>:</h3>
															<font color="FFFFFF">___</font>
															<xsl:value-of select="/*/FileHeader/ThirdParty/LegalEntity/RegistrationData/Volume" />
														</td>
														<td width="25%">
															<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('AdditionalRegistrationData','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion"/>:</h3>
															<font color="FFFFFF">___</font>
															<xsl:value-of select="/*/FileHeader/ThirdParty/LegalEntity/RegistrationData/AdditionalRegistrationData" />
														</td>
													</tr>
												</table>
											</td>
										</tr>
									</xsl:if>
									<xsl:if test='/*/FileHeader/ThirdParty/LegalEntity/ContactDetails!=""'>
										<tr>
											<td width="30%" colspan="3">
												<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('ContactData','',concat('lang_',$lang))" />
												<h3><xsl:value-of select="$traduccion"/>:</h3>
											</td>
										</tr>
										<tr>
											<td colspan="3">
												<table border="0" cellpadding="2" cellspacing="0"
													width="100%">
													<tr>
														<td width="25%">
															<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Telephone','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion"/>:</h3>
															<font color="FFFFFF">__</font>
															<xsl:value-of	select="/*/FileHeader/ThirdParty/LegalEntity/ContactDetails/Telephone" />
														</td>
														<td width="25%">
															<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TeleFax','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion"/>:</h3>
															<font color="FFFFFF">___</font>
															<xsl:value-of select="/*/FileHeader/ThirdParty/LegalEntity/ContactDetails/TeleFax" />
														</td>
														<td width="25%">
															<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('WebAddress','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion"/>:</h3>
															<font color="FFFFFF">___</font>
															<xsl:value-of select="/*/FileHeader/ThirdParty/LegalEntity/ContactDetails/WebAddress" />
														</td>
														<td width="25%">
															<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('ElectronicMail','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion"/>:</h3>
															<font color="FFFFFF">___</font>
															<xsl:value-of select="/*/FileHeader/ThirdParty/LegalEntity/ContactDetails/ElectronicMail" />
														</td>
													</tr>
													<tr>
														<td width="25%">
															<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('ContactPersons','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion"/>:</h3>
															<font color="FFFFFF">__</font>
															<xsl:value-of select="/*/FileHeader/ThirdParty/LegalEntity/ContactDetails/ContactPersons" />
														</td>
														<td width="25%">
															<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('CnoCnae','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion"/>:</h3>
															<font color="FFFFFF">___</font>
															<xsl:value-of select="/*/FileHeader/ThirdParty/LegalEntity/ContactDetails/CnoCnae" />
														</td>
														<td width="25%">
															<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('INETownCode','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion"/>:</h3>
															<font color="FFFFFF">___</font>
															<xsl:value-of select="/*/FileHeader/ThirdParty/LegalEntity/ContactDetails/INETownCode" />
														</td>
														<td width="25%">
															<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('AdditionalContactDetails','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion"/>:</h3>
															<font color="FFFFFF">___</font>
															<xsl:value-of select="/*/FileHeader/ThirdParty/LegalEntity/ContactDetails/AdditionalContactDetails" />
														</td>
													</tr>
												</table>
											</td>
										</tr>
									</xsl:if>
								</xsl:if>
								<xsl:if test='/*/FileHeader/ThirdParty/Individual!=""'>
									<tr>
										<td valign="top" width="10%">
											<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Address','',concat('lang_',$lang))" />
											<h3><xsl:value-of select="$traduccion"/>:</h3>
										</td>
										<td valign="top" width="40%">
											<xsl:if test='/*/FileHeader/ThirdParty/Individual/AddressInSpain!=""'>
												<xsl:value-of select="/*/FileHeader/ThirdParty/Individual/AddressInSpain/Address" />
												<br />
												<xsl:value-of select="/*/FileHeader/ThirdParty/Individual/AddressInSpain/PostCode" />
												<font color="FFFFFF">_</font>
												<font color="FFFFFF">_</font>
												<xsl:value-of select="/*/FileHeader/ThirdParty/Individual/AddressInSpain/Town" />
												<br />
												<xsl:value-of select="/*/FileHeader/ThirdParty/Individual/AddressInSpain/Province" />
												<br />
												<xsl:value-of select="/*/FileHeader/ThirdParty/Individual/AddressInSpain/CountryCode" />
											</xsl:if>
											<xsl:if
												test='/*/FileHeader/ThirdParty/Individual/OverseasAddress!=""'>
												<xsl:value-of select="/*/FileHeader/ThirdParty/Individual/OverseasAddress/Address" />
												<br />
												<xsl:value-of select="/*/FileHeader/ThirdParty/Individual/OverseasAddress/PostCodeAndTown" />
												<br />
												<xsl:value-of select="/*/FileHeader/ThirdParty/Individual/OverseasAddress/Province" />
												<br />
												<xsl:value-of select="/*/FileHeader/ThirdParty/Individual/OverseasAddress/CountryCode" />
											</xsl:if>
										</td>
									</tr>
									<xsl:if test='/*/FileHeader/ThirdParty/Individual/ContactDetails!=""'>
										<tr>
											<td width="30%" colspan="3">
												<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('ContactData','',concat('lang_',$lang))" />
												<h3><xsl:value-of select="$traduccion"/>:</h3>
											</td>
										</tr>
										<tr>
											<td colspan="3">
												<table border="0" cellpadding="2" cellspacing="0"
													width="100%">
													<tr>
														<td width="25%">
															<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Telephone','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion"/>:</h3>
															<font color="FFFFFF">__</font>
															<xsl:value-of	select="/*/FileHeader/ThirdParty/Individual/ContactDetails/Telephone" />
														</td>
														<td width="25%">
															<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TeleFax','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion"/>:</h3>
															<font color="FFFFFF">___</font>
															<xsl:value-of select="/*/FileHeader/ThirdParty/Individual/ContactDetails/TeleFax" />
														</td>
														<td width="25%">
															<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('WebAddress','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion"/>:</h3>
															<font color="FFFFFF">___</font>
															<xsl:value-of select="/*/FileHeader/ThirdParty/Individual/ContactDetails/WebAddress" />
														</td>
														<td width="25%">
															<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('ElectronicMail','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion"/>:</h3>
															<font color="FFFFFF">___</font>
															<xsl:value-of select="/*/FileHeader/ThirdParty/Individual/ContactDetails/ElectronicMail" />
														</td>
													</tr>
													<tr>
														<td width="25%">
															<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('ContactPersons','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion"/>:</h3>
															<font color="FFFFFF">__</font>
															<xsl:value-of select="/*/FileHeader/ThirdParty/Individual/ContactDetails/ContactPersons" />
														</td>
														<td width="25%">
															<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('CnoCnae','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion"/>:</h3>
															<font color="FFFFFF">___</font>
															<xsl:value-of select="/*/FileHeader/ThirdParty/Individual/ContactDetails/CnoCnae" />
														</td>
														<td width="25%">
															<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('INETownCode','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion"/>:</h3>
															<font color="FFFFFF">___</font>
															<xsl:value-of select="/*/FileHeader/ThirdParty/Individual/ContactDetails/INETownCode" />
														</td>
														<td width="25%">
															<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('AdditionalContactDetails','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion"/>:</h3>
															<font color="FFFFFF">___</font>
															<xsl:value-of select="/*/FileHeader/ThirdParty/Individual/ContactDetails/AdditionalContactDetails" />
														</td>
													</tr>
												</table>
											</td>
										</tr>
									</xsl:if>
								</xsl:if>		
							</table>
						</td>
					</tr> 
				</table>
			</td>
		</tr> 
	</xsl:template>
	<xsl:template match="/*/FileHeader/FactoringAssignmentData">
		<xsl:variable name="lang" select="/*/Invoices/Invoice/InvoiceIssueData/LanguageName"/> 
		<tr id="cesionario">
			 <td width="100%">
				<table border="0" cellpadding="2" cellspacing="0" width="100%">
					<tr>
						 <td width="100%">
							<table border="0" cellpadding="2" cellspacing="0" width="100%">
								<tr>
									<td colspan="3">
										<font color="FFFFFF">_</font>
									</td>
								</tr>
								<tr>
									<td colspan="3">
										<hr color="#a0d7a0"/>
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('FactoringData','',concat('lang_',$lang))" />
										<h2><xsl:value-of select="$traduccion"/></h2>
									</td>
									<td align="right">							
										<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('MoreData','',concat('lang_',$lang))" />
										<a id="datosCesionarioLink" href="javascript:mostrarCapa('datosCesionario')"><xsl:value-of select="$traduccion"/></a>
									</td>
								</tr>
								<tr>
									<td colspan="3"><font color="FFFFFF">_</font></td>        	
								</tr>
								<xsl:if test='/*/FileHeader/FactoringAssignmentData/Assignee/LegalEntity!=""'>
									<tr>
										<td width="50%" colspan="2">
											<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('CorporateName','',concat('lang_',$lang))" />
											<h3><xsl:value-of select="$traduccion"/></h3>
											<font color="FFFFFF">__</font>
											<xsl:value-of select="/*/FileHeader/FactoringAssignmentData/Assignee/LegalEntity/CorporateName"/>
											</td>
									</tr>
									</xsl:if>
									<xsl:if test='/*/FileHeader/FactoringAssignmentData/Assignee/Individual!=""'>
									<tr>
										<td width="50%" colspan="2">
											<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('NameAndSurnames','',concat('lang_',$lang))" />
											<h3><xsl:value-of select="$traduccion"/>:</h3>
											<font color="FFFFFF">__</font>
											<xsl:value-of select="/*/FileHeader/FactoringAssignmentData/Assignee/Individual/Name" />
											<font color="FFFFFF">_</font>
											<xsl:value-of select="/*/FileHeader/FactoringAssignmentData/Assignee/Individual/FirstSurname" />
											<font color="FFFFFF">_</font>
											<xsl:value-of select="/*/FileHeader/FactoringAssignmentData/Assignee/Individual/SecondSurname" />
											<font color="FFFFFF">_</font>
										</td>
									</tr>
									</xsl:if>
									<tr>
										<td width="50%">
											<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TaxIdentificationNumber','',concat('lang_',$lang))" />
											<h3><xsl:value-of select="$traduccion"/>:</h3>
											<font color="FFFFFF">__</font>
											<xsl:value-of select="/*/FileHeader/FactoringAssignmentData/Assignee/TaxIdentification/TaxIdentificationNumber" />
										</td>
									</tr>
							</table>
						</td>
					</tr>
					<tr id="datosCesionario" style="display:none">
						<td width="100%">
							<table border="0" cellpadding="2" cellspacing="0" width="100%">							
								<tr>
									<td width="50%" colspan="2">
										<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('PersonType','',concat('lang_',$lang))" />
										<h3><xsl:value-of select="$traduccion"/>:</h3>
										<font color="FFFFFF">__</font>
										<xsl:variable name="toTranslate" select="/*/FileHeader/FactoringAssignmentData/Assignee/TaxIdentification/PersonTypeCode" />
										<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('PersonTypeCode',$toTranslate,concat('lang_',$lang))" />
										<xsl:value-of select="$traduccion2" />
									</td>
									<td width="50%">
										<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('ResidenceType','',concat('lang_',$lang))" />
										<h3><xsl:value-of select="$traduccion"/>:</h3>
										<font color="FFFFFF">__</font>
										<xsl:variable name="toTranslate" select="/*/FileHeader/FactoringAssignmentData/Assignee/TaxIdentification/ResidenceTypeCode" />
										<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('ResidenceTypeCode',$toTranslate,concat('lang_',$lang))" />
										<xsl:value-of select="$traduccion2" />
									</td>
								</tr>
								<xsl:apply-templates select="/*/FileHeader/FactoringAssignmentData/PaymentDetails"/>
								<tr>	
									<td colspan="3" width="50%">
										<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('FactoringAssignmentClauses','',concat('lang_',$lang))" />
										<h3><xsl:value-of select="$traduccion"/>:</h3>
										<font color="FFFFFF">__</font>
										<xsl:value-of select="/*/FileHeader/FactoringAssignmentData/FactoringAssignmentClauses"/>								
									</td>
								</tr>
								<xsl:if test='/*/FileHeader/FactoringAssignmentData/Assignee/LegalEntity!=""'>
									<tr>
										<td valign="top" width="10%">
											<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Address','',concat('lang_',$lang))" />
											<h3><xsl:value-of select="$traduccion"/>:</h3>
										</td>
										<td valign="top" width="40%">
											<xsl:if test='/*/FileHeader/FactoringAssignmentData/Assignee/LegalEntity/AddressInSpain!=""'>
												<xsl:value-of select="/*/FileHeader/FactoringAssignmentData/Assignee/LegalEntity/AddressInSpain/Address" />
												<br />
												<xsl:value-of select="/*/FileHeader/FactoringAssignmentData/Assignee/LegalEntity/AddressInSpain/PostCode" />
												<font color="FFFFFF">__</font>
												<xsl:value-of select="/*/FileHeader/FactoringAssignmentData/Assignee/LegalEntity/AddressInSpain/Town" />
												<br />
												<xsl:value-of select="/*/FileHeader/FactoringAssignmentData/Assignee/LegalEntity/AddressInSpain/Province" />
												<br />
												<xsl:value-of select="/*/FileHeader/FactoringAssignmentData/Assignee/LegalEntity/AddressInSpain/CountryCode" />
											</xsl:if>
											<xsl:if test='/*/FileHeader/FactoringAssignmentData/Assignee/LegalEntity/OverseasAddress!=""'>
												<xsl:value-of select="/*/FileHeader/FactoringAssignmentData/Assignee/LegalEntity/OverseasAddress/Address" />
												<br />
												<xsl:value-of select="/*/FileHeader/FactoringAssignmentData/Assignee/LegalEntity/OverseasAddress/PostCodeAndTown" />
												<br />
												<xsl:value-of select="/*/FileHeader/FactoringAssignmentData/Assignee/LegalEntity/OverseasAddress/Province" />
												<br />
												<xsl:value-of select="/*/FileHeader/FactoringAssignmentData/Assignee/LegalEntity/OverseasAddress/CountryCode" />
											</xsl:if>
										</td>
										<xsl:if test='/*/FileHeader/FactoringAssignmentData/Assignee/LegalEntity/TradeName!=""'>
											<td valign="top" width="50%">
												<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TradeName','',concat('lang_',$lang))" />
												<h3><xsl:value-of select="$traduccion"/>:</h3>
												<font color="FFFFFF">__</font>
												<xsl:value-of select="/*/FileHeader/FactoringAssignmentData/Assignee/LegalEntity/TradeName" />
											</td>
										</xsl:if>
									</tr>
									<xsl:if test='/*/FileHeader/FactoringAssignmentData/Assignee/LegalEntity/RegistrationData!=""'>
										<tr>
											<td width="30%" colspan="3">
												<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('RegistryData','',concat('lang_',$lang))" />
												<h3><xsl:value-of select="$traduccion"/>:</h3>
											</td>
										</tr>
										<tr>
											<td colspan="3">
												<table border="0" cellpadding="2" cellspacing="0"
													width="100%">
													<tr>
														<td width="25%">
															<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Book','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion"/>:</h3>
															<font color="FFFFFF">__</font>
															<xsl:value-of select="/*/FileHeader/FactoringAssignmentData/Assignee/LegalEntity/RegistrationData/Book" />
														</td>
														<td width="25%">
															<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('RegisterOfCompaniesLocation','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion"/>:</h3>
															<font color="FFFFFF">___</font>
															<xsl:value-of select="/*/FileHeader/FactoringAssignmentData/Assignee/LegalEntity/RegistrationData/RegisterOfCompaniesLocation" />
														</td>
														<td width="25%">
															<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Sheet','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion"/>:</h3>
															<font color="FFFFFF">___</font>
															<xsl:value-of select="/*/FileHeader/FactoringAssignmentData/Assignee/LegalEntity/RegistrationData/Sheet" />
														</td>
														<td width="25%">
															<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Folio','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion"/>:</h3>
															<font color="FFFFFF">___</font>
															<xsl:value-of select="/*/FileHeader/FactoringAssignmentData/Assignee/LegalEntity/RegistrationData/Folio" />
														</td>
													</tr>
													<tr>
														<td width="25%">
															<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Section','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion"/>:</h3>
															<font color="FFFFFF">__</font>
															<xsl:value-of select="/*/FileHeader/FactoringAssignmentData/Assignee/LegalEntity/RegistrationData/Section" />
														</td>
														<td width="25%">
															<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Volume','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion"/>:</h3>
															<font color="FFFFFF">___</font>
															<xsl:value-of select="/*/FileHeader/FactoringAssignmentData/Assignee/LegalEntity/RegistrationData/Volume" />
														</td>
														<td width="25%">
															<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('AdditionalRegistrationData','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion"/>:</h3>
															<font color="FFFFFF">___</font>
															<xsl:value-of select="/*/FileHeader/FactoringAssignmentData/Assignee/LegalEntity/RegistrationData/AdditionalRegistrationData" />
														</td>
													</tr>
												</table>
											</td>
										</tr>
									</xsl:if>
									<xsl:if test='/*/FileHeader/FactoringAssignmentData/Assignee/LegalEntity/ContactDetails!=""'>
										<tr>
											<td width="30%" colspan="3">
												<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('ContactData','',concat('lang_',$lang))" />
												<h3><xsl:value-of select="$traduccion"/>:</h3>
											</td>
										</tr>
										<tr>
											<td colspan="3">
												<table border="0" cellpadding="2" cellspacing="0"
													width="100%">
													<tr>
														<td width="25%">
															<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Telephone','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion"/>:</h3>
															<font color="FFFFFF">__</font>
															<xsl:value-of	select="/*/FileHeader/FactoringAssignmentData/Assignee/LegalEntity/ContactDetails/Telephone" />
														</td>
														<td width="25%">
															<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TeleFax','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion"/>:</h3>
															<font color="FFFFFF">___</font>
															<xsl:value-of select="/*/FileHeader/FactoringAssignmentData/Assignee/LegalEntity/ContactDetails/TeleFax" />
														</td>
														<td width="25%">
															<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('WebAddress','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion"/>:</h3>
															<font color="FFFFFF">___</font>
															<xsl:value-of select="/*/FileHeader/FactoringAssignmentData/Assignee/LegalEntity/ContactDetails/WebAddress" />
														</td>
														<td width="25%">
															<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('ElectronicMail','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion"/>:</h3>
															<font color="FFFFFF">___</font>
															<xsl:value-of select="/*/FileHeader/FactoringAssignmentData/Assignee/LegalEntity/ContactDetails/ElectronicMail" />
														</td>
													</tr>
													<tr>
														<td width="25%">
															<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('ContactPersons','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion"/>:</h3>
															<font color="FFFFFF">__</font>
															<xsl:value-of select="/*/FileHeader/FactoringAssignmentData/Assignee/LegalEntity/ContactDetails/ContactPersons" />
														</td>
														<td width="25%">
															<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('CnoCnae','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion"/>:</h3>
															<font color="FFFFFF">___</font>
															<xsl:value-of select="/*/FileHeader/FactoringAssignmentData/Assignee/LegalEntity/ContactDetails/CnoCnae" />
														</td>
														<td width="25%">
															<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('INETownCode','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion"/>:</h3>
															<font color="FFFFFF">___</font>
															<xsl:value-of select="/*/FileHeader/FactoringAssignmentData/Assignee/LegalEntity/ContactDetails/INETownCode" />
														</td>
														<td width="25%">
															<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('AdditionalContactDetails','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion"/>:</h3>
															<font color="FFFFFF">___</font>
															<xsl:value-of select="/*/FileHeader/FactoringAssignmentData/Assignee/LegalEntity/ContactDetails/AdditionalContactDetails" />
														</td>
													</tr>
												</table>
											</td>
										</tr>
									</xsl:if>
								</xsl:if>
								<xsl:if test='/*/FileHeader/FactoringAssignmentData/Assignee/Individual!=""'>
									<tr>
										<td valign="top" width="10%">
											<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Address','',concat('lang_',$lang))" />
											<h3><xsl:value-of select="$traduccion"/>:</h3>
										</td>
										<td valign="top" width="40%">
											<xsl:if test='/*/FileHeader/FactoringAssignmentData/Assignee/Individual/AddressInSpain!=""'>
												<xsl:value-of select="/*/FileHeader/FactoringAssignmentData/Assignee/Individual/AddressInSpain/Address" />
												<br />
												<xsl:value-of select="/*/FileHeader/FactoringAssignmentData/Assignee/Individual/AddressInSpain/PostCode" />
												<font color="FFFFFF">_</font>
												<font color="FFFFFF">_</font>
												<xsl:value-of select="/*/FileHeader/FactoringAssignmentData/Assignee/Individual/AddressInSpain/Town" />
												<br />
												<xsl:value-of select="/*/FileHeader/FactoringAssignmentData/Assignee/Individual/AddressInSpain/Province" />
												<br />
												<xsl:value-of select="/*/FileHeader/FactoringAssignmentData/Assignee/Individual/AddressInSpain/CountryCode" />
											</xsl:if>
											<xsl:if
												test='/*/FileHeader/FactoringAssignmentData/Assignee/Individual/OverseasAddress!=""'>
												<xsl:value-of select="/*/FileHeader/FactoringAssignmentData/Assignee/Individual/OverseasAddress/Address" />
												<br />
												<xsl:value-of select="/*/FileHeader/FactoringAssignmentData/Assignee/Individual/OverseasAddress/PostCodeAndTown" />
												<br />
												<xsl:value-of select="/*/FileHeader/FactoringAssignmentData/Assignee/Individual/OverseasAddress/Province" />
												<br />
												<xsl:value-of select="/*/FileHeader/FactoringAssignmentData/Assignee/Individual/OverseasAddress/CountryCode" />
											</xsl:if>
										</td>
									</tr>
									<xsl:if test='/*/FileHeader/FactoringAssignmentData/Assignee/Individual/ContactDetails!=""'>
										<tr>
											<td width="30%" colspan="3">
												<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('ContactData','',concat('lang_',$lang))" />
												<h3><xsl:value-of select="$traduccion"/>:</h3>
											</td>
										</tr>
										<tr>
											<td colspan="3">
												<table border="0" cellpadding="2" cellspacing="0"
													width="100%">
													<tr>
														<td width="25%">
															<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Telephone','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion"/>:</h3>
															<font color="FFFFFF">__</font>
															<xsl:value-of	select="/*/FileHeader/FactoringAssignmentData/Assignee/Individual/ContactDetails/Telephone" />
														</td>
														<td width="25%">
															<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TeleFax','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion"/>:</h3>
															<font color="FFFFFF">___</font>
															<xsl:value-of select="/*/FileHeader/FactoringAssignmentData/Assignee/Individual/ContactDetails/TeleFax" />
														</td>
														<td width="25%">
															<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('WebAddress','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion"/>:</h3>
															<font color="FFFFFF">___</font>
															<xsl:value-of select="/*/FileHeader/FactoringAssignmentData/Assignee/Individual/ContactDetails/WebAddress" />
														</td>
														<td width="25%">
															<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('ElectronicMail','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion"/>:</h3>
															<font color="FFFFFF">___</font>
															<xsl:value-of select="/*/FileHeader/FactoringAssignmentData/Assignee/Individual/ContactDetails/ElectronicMail" />
														</td>
													</tr>
													<tr>
														<td width="25%">
															<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('ContactPersons','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion"/>:</h3>
															<font color="FFFFFF">__</font>
															<xsl:value-of select="/*/FileHeader/FactoringAssignmentData/Assignee/Individual/ContactDetails/ContactPersons" />
														</td>
														<td width="25%">
															<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('CnoCnae','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion"/>:</h3>
															<font color="FFFFFF">___</font>
															<xsl:value-of select="/*/FileHeader/FactoringAssignmentData/Assignee/Individual/ContactDetails/CnoCnae" />
														</td>
														<td width="25%">
															<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('INETownCode','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion"/>:</h3>
															<font color="FFFFFF">___</font>
															<xsl:value-of select="/*/FileHeader/FactoringAssignmentData/Assignee/Individual/ContactDetails/INETownCode" />
														</td>
														<td width="25%">
															<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('AdditionalContactDetails','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion"/>:</h3>
															<font color="FFFFFF">___</font>
															<xsl:value-of select="/*/FileHeader/FactoringAssignmentData/Assignee/Individual/ContactDetails/AdditionalContactDetails" />
														</td>
													</tr>
												</table>
											</td>
										</tr>
									</xsl:if>
								</xsl:if>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</xsl:template>
	<xsl:template match="/*/Invoices/Invoice">
		<xsl:variable name="lang" select="/*/Invoices/Invoice/InvoiceIssueData/LanguageName"/>
		<xsl:variable name="numFactura" select="InvoiceHeader/InvoiceNumber"/>		
		<tr id="{$numFactura}" style="display:none">
			<td width="100%">
				<table border="0" cellpadding="2" cellspacing="0" width="100%">					
					<tr>
						<td><font color="FFFFFF">_</font></td>
					</tr>
					<tr>
						<td>
							<hr color="#a0d7a0"/>
						</td>
					</tr>
					<tr>
						<td>
							<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('InvoiceSummary','',concat('lang_',$lang))" />
							<h2><xsl:value-of select="$traduccion"/></h2>
						</td>
					</tr>
					<tr>
						<td><font color="FFFFFF">_</font></td>
					</tr>
					<tr>
						<td width="100%">
							<table bordercolor="#e6f0fa" border="1" cellpadding="2" cellspacing="0" width="100%">
								<tr>
									<td>
										<table border="0" cellpadding="2" cellspacing="0" width="100%">
											<tr>
												<td align="center" width="25%">
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Number','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/></h3>
													<br/><xsl:value-of select="InvoiceHeader/InvoiceNumber"/>
												</td>																	
												<td align="center" width="25%">
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Serie','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/></h3>
													<br/>
													<xsl:choose>
														<xsl:when test='InvoiceHeader/InvoiceSeriesCode!=""' >
															<xsl:value-of select="InvoiceHeader/InvoiceSeriesCode"/>
														</xsl:when>
														<xsl:otherwise>
															-
														</xsl:otherwise>
													</xsl:choose>																			
												</td>
												<td align="center" width="25%">
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Type','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/></h3>
													<br/>
													<xsl:variable name="toTranslate" select="InvoiceHeader/InvoiceDocumentType"/>
													<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('InvoiceDocumentType',$toTranslate,concat('lang_',$lang))"/>
													<xsl:value-of select="$traduccion2"/>
												</td>
												<td align="center" width="25%">
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Class','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/></h3>
													<br/>
													<xsl:variable name="toTranslate" select="InvoiceHeader/InvoiceClass"/>
													<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('InvoiceClassType',$toTranslate,concat('lang_',$lang))"/>
													<xsl:value-of select="$traduccion2"/>
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<table border="0" cellpadding="2" cellspacing="0" width="100%">
											<tr>
												<td align="center" width="25%">
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('ExpedDate','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/></h3>
													<br/>
														<xsl:value-of select="substring(InvoiceIssueData/IssueDate,9,2)"/>-<xsl:value-of select="substring(InvoiceIssueData/IssueDate,6,2)"/>-<xsl:value-of select="substring(InvoiceIssueData/IssueDate,1,4)"/>
												</td>
												<td align="center" width="25%">
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('OperationDate','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/></h3>
													<br/>
													<xsl:choose>
														<xsl:when test='InvoiceIssueData/OperationDate!=""' >
															<xsl:value-of select="substring(InvoiceIssueData/OperationDate,9,2)"/>-<xsl:value-of select="substring(InvoiceIssueData/OperationDate,6,2)"/>-<xsl:value-of select="substring(InvoiceIssueData/OperationDate,1,4)"/>
														</xsl:when>
														<xsl:otherwise>
															-
														</xsl:otherwise>
													</xsl:choose>
												</td>
												<td align="center" width="25%">
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('ExpedPlace','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/></h3>
													<br/>
													<xsl:choose>
														<xsl:when test='InvoiceIssueData/PlaceOfIssue!=""' >
															<xsl:value-of select="InvoiceIssueData/PlaceOfIssue/PostCode"/><font color="FFFFFF">_</font><xsl:value-of select="InvoiceIssueData/PlaceOfIssue/PlaceOfIssueDescription"/>
														</xsl:when>
														<xsl:otherwise>
															-
														</xsl:otherwise>
													</xsl:choose>
												</td>
												<td align="center" width="25%">																		
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('InvoicingPeriod','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/></h3>
													<br/>
													<xsl:choose>
														<xsl:when test='InvoiceIssueData/InvoicingPeriod!=""' >
															<xsl:value-of select="substring(InvoiceIssueData/InvoicingPeriod/StartDate,9,2)"/>-<xsl:value-of select="substring(InvoiceIssueData/InvoicingPeriod/StartDate,6,2)"/>-<xsl:value-of select="substring(InvoiceIssueData/InvoicingPeriod/StartDate,1,4)"/> - 
															<xsl:value-of select="substring(InvoiceIssueData/InvoicingPeriod/EndDate,9,2)"/>-<xsl:value-of select="substring(InvoiceIssueData/InvoicingPeriod/EndDate,6,2)"/>-<xsl:value-of select="substring(InvoiceIssueData/InvoicingPeriod/EndDate,1,4)"/>
														</xsl:when>
														<xsl:otherwise>
															-
														</xsl:otherwise>
													</xsl:choose>
												</td>
												<td align="center" width="25%">
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Language','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/></h3>
													<br/>
													<xsl:choose>
														<xsl:when test='InvoiceIssueData/LanguageName!=""' >
															<xsl:value-of select="InvoiceIssueData/LanguageName"/>
														</xsl:when>
														<xsl:otherwise>
															-
														</xsl:otherwise>
													</xsl:choose>
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<table border="0" cellpadding="2" cellspacing="0" width="100%">
											<tr>
												<td align="center" width="25%">
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('OperationCurrency','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/></h3><br/>
													<xsl:value-of select="InvoiceIssueData/InvoiceCurrencyCode"/>
													<xsl:if test='InvoiceIssueData/InvoiceCurrencyCode="EUR"'>
														<font color="#ffffff">_</font>
														<xsl:variable name="traduccioneuro"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('EuroSimbol','',concat('lang_',$lang))" />
														<xsl:value-of select="$traduccioneuro"/>
													</xsl:if>
												</td>
												<xsl:if test='InvoiceIssueData/InvoiceCurrencyCode!="EUR"'>
													<td align="center" width="25%">
														<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('ExchangeType','',concat('lang_',$lang))" />
														<h3><xsl:value-of select="$traduccion"/></h3>
														<br/><xsl:value-of select="InvoiceIssueData/ExchangeRate"/>
													</td>
													<td align="center" width="25%">
														<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('ExchangeDate','',concat('lang_',$lang))" />
														<h3><xsl:value-of select="$traduccion"/></h3>
														<br/><xsl:value-of select="substring(InvoiceIssueData/ExchangeRateDate,9,2)"/>-<xsl:value-of select="substring(InvoiceIssueData/ExchangeRateDate,6,2)"/>-<xsl:value-of select="substring(InvoiceIssueData/ExchangeRateDate,1,4)"/>
													</td>
												</xsl:if>
												<td align="center" width="25%">
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TaxCurrency','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/></h3>
													<br/><xsl:value-of select="InvoiceIssueData/TaxCurrencyCode"/>
													<xsl:if test='InvoiceIssueData/TaxCurrencyCode="EUR"'>
														<font color="#ffffff">_</font>
														<xsl:variable name="traduccioneuro"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('EuroSimbol','',concat('lang_',$lang))" />
														<xsl:value-of select="$traduccioneuro"/>
													</xsl:if>
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</td>											
					</tr>					
					<xsl:apply-templates select="InvoiceHeader/Corrective"/>
					<tr>
						<td><font color="FFFFFF">_</font></td>
					</tr>
					<tr>
						<td>
							<hr color="#a0d7a0"/>
						</td>
					</tr>
					<tr>
						<td>
							<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Details','',concat('lang_',$lang))" />
							<h2><xsl:value-of select="$traduccion"/></h2>
						</td>
					</tr>
					<tr>
						<td><font color="FFFFFF">_</font></td>
					</tr>
					<tr>
						<td width="100%">
							<table bordercolor="#e6f0fa" border="1" cellpadding="2" cellspacing="0" width="100%">
								<tr>
									<td width="48%" align="center">
										<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Description','',concat('lang_',$lang))" />
										<h3><xsl:value-of select="$traduccion"/></h3>
									</td>
									<td width="12%" align="center">
										<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TransactionDate','',concat('lang_',$lang))" />
										<h3><xsl:value-of select="$traduccion"/></h3>
									</td>
									<td width="10%" align="center">
										<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Quantity','',concat('lang_',$lang))" />
										<h3><xsl:value-of select="$traduccion"/></h3>
									</td>
									<td width="15%" align="center">
										<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('UnitAmount','',concat('lang_',$lang))" />
										<h3><xsl:value-of select="$traduccion"/></h3>
									</td>
									<td width="15%" align="center">
										<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Total','',concat('lang_',$lang))" />
										<h3><xsl:value-of select="$traduccion"/></h3>
									</td>
								</tr>
								<xsl:for-each select="Items/InvoiceLine">
									<tr style="cursor:hand" onclick="mostrarDetalle('{$numFactura}','{ItemDescription}')" onMouseOver="this.style.background='#e6f0fa'" onMouseOut="this.style.background='#FFFFFF'">
										<td width="48%" valign="top">
											<xsl:apply-templates select="ItemDescription"/>
										</td>
										<td width="12%" valign="top" align="center">
											<xsl:choose>
												<xsl:when test='TransactionDate!=""' >
													<xsl:value-of select="substring(TransactionDate,9,2)"/>-<xsl:value-of select="substring(TransactionDate,6,2)"/>-<xsl:value-of select="substring(TransactionDate,1,4)"/>
												</xsl:when>
												<xsl:otherwise>
													<font color="FFFFFF">_</font>
												</xsl:otherwise>
											</xsl:choose>
										</td>
										<td width="10%" valign="top" align="right">
											<xsl:variable name="decimales"><xsl:value-of select="Quantity"/></xsl:variable>
											<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/> 
										</td>
										<td width="15%" valign="top" align="right">
											<xsl:variable name="decimales"><xsl:value-of select="UnitPriceWithoutTax"/></xsl:variable>
											<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/> 
										</td>
										<td width="15%" valign="top" align="right">
											<xsl:variable name="decimales"><xsl:value-of select="TotalCost"/></xsl:variable>
											<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/> 
										</td>
									</tr>
								</xsl:for-each>
							</table>
						</td>
					</tr>
					<tr>
						<td><font color="FFFFFF">_</font></td>
					</tr>
					<tr>
						<td>
							<hr color="#a0d7a0"/>
						</td>
					</tr>
					<tr>
						<td width="100%">
							<table border="0" cellpadding="2" cellspacing="0" width="100%">
								<tr>
									<td>
										<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Amounts','',concat('lang_',$lang))" />
										<h2><xsl:value-of select="$traduccion"/></h2>
									</td>
								</tr>
								<tr>
									<td><font color="FFFFFF">_</font></td>
								</tr>
								<tr>
									<td width="100%">
										<table border="0" cellpadding="2" cellspacing="0" width="100%">
											<tr>
												<td width="80%" align="right">
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('GrossAmount','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/><font color="FFFFFF">___</font></h3>
												</td>
												<td width="20%" align="center">
													<table bordercolor="#e6f0fa" border="1" cellpadding="2" cellspacing="0" width="100%">
														<tr>
															<td align="right">
																<xsl:variable name="decimales"><xsl:value-of select="InvoiceTotals/TotalGrossAmount"/></xsl:variable>
																<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<xsl:if test='InvoiceTotals/GeneralDiscounts!=""' >
								<tr>
									<td>							
										<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Discounts','',concat('lang_',$lang))" />
										<h3><i><xsl:value-of select="$traduccion"/></i></h3>
										<table bordercolor="#e6f0fa" border="1" cellpadding="2" cellspacing="0" width="100%">
											<tr>
												<td width="70%" valign="top" align="center">
													<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Item','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion2"/></h3>
												</td>
												<td width="10%" valign="top" align="center">
													<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Type','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion2"/> (%)</h3>
												</td>
												<td width="20%" valign="top" align="center">
													<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Amount','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion2"/></h3>
												</td>
											</tr>
											<tr>
												<td width="70%" valign="top">
													<table border="0" cellpadding="2" cellspacing="0" width="100%">
														<xsl:for-each select="InvoiceTotals/GeneralDiscounts/Discount">
															<tr>
																<td width="100%">
																	<xsl:apply-templates select="DiscountReason"/>
																</td>
															</tr>
														</xsl:for-each>
													</table>
												</td>
												<td width="10%" valign="top">
													<table border="0" cellpadding="2" cellspacing="0" width="100%">
														<xsl:for-each select="InvoiceTotals/GeneralDiscounts/Discount">
															<tr>
																<td width="100%" align="center">
																	<xsl:choose>
																		<xsl:when test='DiscountRate!=""' >
																			<xsl:variable name="decimales"><xsl:value-of select="DiscountRate"/></xsl:variable>
																			<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>																			
																		</xsl:when>
																		<xsl:otherwise>
																			-
																		</xsl:otherwise>
																	</xsl:choose>																	
																</td>
															</tr>
														</xsl:for-each>
													</table>
												</td>
												<td width="20%" valign="top">
													<table border="0" cellpadding="2" cellspacing="0" width="100%">
														<xsl:for-each select="InvoiceTotals/GeneralDiscounts/Discount">
															<tr>
																<td align="right">
																	<xsl:variable name="decimales"><xsl:value-of select="DiscountAmount"/></xsl:variable>
																	<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
																</td>
															</tr>
														</xsl:for-each>
													</table>
												</td>
											</tr>
										</table>							
									</td>
								</tr>
								<tr>
									<td><font color="FFFFFF">_</font></td>
								</tr>
								<tr>
									<td width="100%">
										<table border="0" cellpadding="2" cellspacing="0" width="100%">
											<tr>
												<td width="80%" align="right">
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TotalDiscounts','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/><font color="FFFFFF">___</font></h3>
												</td>
												<td width="20%" align="center">
													<table bordercolor="#e6f0fa" border="1" cellpadding="2" cellspacing="0" width="100%">
														<tr>
															<td align="right">
																<xsl:variable name="decimales"><xsl:value-of select="InvoiceTotals/TotalGeneralDiscounts"/></xsl:variable>
																<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td><font color="FFFFFF">_</font></td>
								</tr>
								</xsl:if>
								<xsl:if test='InvoiceTotals/GeneralSurcharges!=""' >
								<tr>
									<td>	
										<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Charges','',concat('lang_',$lang))" />
										<h3><i><xsl:value-of select="$traduccion"/></i></h3>
										<table bordercolor="#e6f0fa" border="1" cellpadding="2" cellspacing="0" width="100%">
											<tr>
												<td width="70%" valign="top" align="center">
													<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Item','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion2"/></h3>
												</td>
												<td width="10%" valign="top" align="center">
													<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Type','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion2"/> (%)</h3>
												</td>
												<td width="20%" valign="top" align="center">
													<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Amount','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion2"/></h3>
												</td>
											</tr>
											<tr>
												<td width="70%" valign="top">
													<table border="0" cellpadding="2" cellspacing="0" width="100%">
														<xsl:for-each select="InvoiceTotals/GeneralSurcharges/Charge">
															<tr>
																<td width="100%">
																	<xsl:apply-templates select="ChargeReason"/>
																</td>
															</tr>
														</xsl:for-each>
													</table>
												</td>
												<td width="10%" valign="top">
													<table border="0" cellpadding="2" cellspacing="0" width="100%">
														<xsl:for-each select="InvoiceTotals/GeneralSurcharges/Charge">
															<tr>
																<td width="100%" align="center">
																	<xsl:choose>
																		<xsl:when test='ChargeRate!=""' >
																			<xsl:variable name="decimales"><xsl:value-of select="ChargeRate"/></xsl:variable>
																			<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
																		</xsl:when>
																		<xsl:otherwise>
																			-
																		</xsl:otherwise>
																	</xsl:choose>
																</td>
															</tr>
														</xsl:for-each>
													</table>
												</td>
												<td width="20%" valign="top">
													<table border="0" cellpadding="2" cellspacing="0" width="100%">
														<xsl:for-each select="InvoiceTotals/GeneralSurcharges/Charge">
															<tr>
																<td align="right">
																	<xsl:variable name="decimales"><xsl:value-of select="ChargeAmount"/></xsl:variable>
																	<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
																</td>
															</tr>
														</xsl:for-each>
													</table>
												</td>
											</tr>
										</table>					
									</td>
								</tr>
								<tr>
									<td><font color="FFFFFF">_</font></td>
								</tr>
								<tr>
									<td width="100%">
										<table border="0" cellpadding="2" cellspacing="0" width="100%">
											<tr>
												<td width="80%" align="right">
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TotalCharges','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/><font color="FFFFFF">___</font></h3>
												</td>
												<td width="20%" align="center">
													<table bordercolor="#e6f0fa" border="1" cellpadding="2" cellspacing="0" width="100%">
														<tr>
															<td align="right">
																<xsl:variable name="decimales"><xsl:value-of select="InvoiceTotals/TotalGeneralSurcharges"/></xsl:variable>
																<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td><font color="FFFFFF">_</font></td>
								</tr>
								</xsl:if>
								<tr>
									<td width="100%">
										<table border="0" cellpadding="2" cellspacing="0" width="100%">
											<tr>
												<td width="80%" align="right">
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TotalAmountBeforeTaxes','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/><font color="FFFFFF">___</font></h3>
												</td>
												<td width="20%" align="center">
													<table bordercolor="#e6f0fa" border="1" cellpadding="2" cellspacing="0" width="100%">
														<tr>
															<td align="right">
																<xsl:variable name="decimales"><xsl:value-of select="InvoiceTotals/TotalGrossAmountBeforeTaxes"/></xsl:variable>
																<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td><font color="FFFFFF">_</font></td>
								</tr>
								<xsl:if test='TaxesOutputs!=""' >
								<tr>
									<td>									
										<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TaxesOutputs','',concat('lang_',$lang))" />
										<h3><i><xsl:value-of select="$traduccion"/></i></h3>
										<xsl:choose>
											<xsl:when test='/*/FileHeader/Batch/InvoiceCurrencyCode="EUR"'>
												<table bordercolor="#e6f0fa" border="1" cellpadding="2" cellspacing="0" width="100%">
													<tr>
														<td width="10%" valign="center" align="center">
															<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TaxType','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion2"/></h3>
														</td>
														<td width="10%" valign="center" align="center">
															<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Type','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion2"/> (%)</h3>
														</td>
														<td width="20%" valign="center" align="center">
															<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TaxableBase','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion2"/></h3>
														</td>
														<td width="20%" valign="center" align="center">
															<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TaxAmount','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion2"/></h3>
														</td>
														<td width="10%" valign="center" align="center">
															<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('SpecialTaxableBase','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion2"/></h3>
														</td>
														<td width="10%" valign="center" align="center">
															<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('SpecialTaxAmount','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion2"/></h3>
														</td>
														<td width="10%" valign="center" align="center">
															<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('EquivalenceSurcharge','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion2"/></h3>
														</td>
														<td width="10%" valign="center" align="center">
															<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('EquivalenceSurchargeAmount','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion2"/></h3>
														</td>
													</tr>
													<xsl:for-each select="TaxesOutputs/Tax">
														<tr>
															<td>
																<xsl:variable name="toTranslate" select="TaxTypeCode"/>
																<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TaxTypeCode',$toTranslate,concat('lang_',$lang))"/>
																<xsl:value-of select="$traduccion2"/>
															</td>
															<td align="center">
																<xsl:choose>
																	<xsl:when test='TaxRate!=""' >
																		<xsl:variable name="decimales"><xsl:value-of select="TaxRate"/></xsl:variable>
																		<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
																	</xsl:when>
																	<xsl:otherwise>
																		-
																	</xsl:otherwise>
																</xsl:choose>
															</td>
															<td align="right">
																<xsl:choose>
																	<xsl:when test='TaxableBase!=""' >
																		<xsl:variable name="decimales"><xsl:value-of select="TaxableBase/TotalAmount"/></xsl:variable>
																		<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>																					
																	</xsl:when>
																	<xsl:otherwise>
																		-
																	</xsl:otherwise>
																</xsl:choose>																		
															</td>
															<td align="right">
																<xsl:choose>
																	<xsl:when test='TaxAmount!=""' >
																		<xsl:variable name="decimales"><xsl:value-of select="TaxAmount/TotalAmount"/></xsl:variable>
																		<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>																					
																	</xsl:when>
																	<xsl:otherwise>
																		-
																	</xsl:otherwise>
																</xsl:choose>
															</td>
															<td align="right">
																<xsl:choose>
																	<xsl:when test='SpecialTaxableBase!=""' >
																		<xsl:variable name="decimales"><xsl:value-of select="SpecialTaxableBase/TotalAmount"/></xsl:variable>
																		<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>																					
																	</xsl:when>
																	<xsl:otherwise>
																		-
																	</xsl:otherwise>
																</xsl:choose>
															</td>
															<td align="right">
																<xsl:choose>
																	<xsl:when test='SpecialTaxAmount!=""' >
																		<xsl:variable name="decimales"><xsl:value-of select="SpecialTaxAmount/TotalAmount"/></xsl:variable>
																		<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>																					
																	</xsl:when>
																	<xsl:otherwise>
																		-
																	</xsl:otherwise>
																</xsl:choose>
															</td>
															<td align="right">
																<xsl:choose>
																	<xsl:when test='EquivalenceSurcharge!=""' >
																		<xsl:variable name="decimales"><xsl:value-of select="EquivalenceSurcharge"/></xsl:variable>
																		<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>																					
																	</xsl:when>
																	<xsl:otherwise>
																		-
																	</xsl:otherwise>
																</xsl:choose>
															</td>
															<td align="right">
																<xsl:choose>
																	<xsl:when test='EquivalenceSurchargeAmount!=""' >
																		<xsl:variable name="decimales"><xsl:value-of select="EquivalenceSurchargeAmount/TotalAmount"/></xsl:variable>
																		<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>																					
																	</xsl:when>
																	<xsl:otherwise>
																		-
																	</xsl:otherwise>
																</xsl:choose>
															</td>
														</tr>
													</xsl:for-each>
												</table>
											</xsl:when>
											<xsl:otherwise>
												<table bordercolor="#e6f0fa" border="1" cellpadding="2" cellspacing="0" width="100%">
													<tr>
														<td rowspan="2" width="50%" valign="center" align="center">
															<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TaxType','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion2"/></h3>
														</td>
														<td rowspan="2" width="10%" valign="center" align="center">
															<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Type','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion2"/> (%)</h3>
														</td>
														<td width="20%" valign="top" align="center" colspan="2">
															<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TaxableBase','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion2"/></h3>
														</td>
														<td width="20%" valign="top" align="center" colspan="2">
															<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TaxAmount','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion2"/></h3>
														</td>
														<td width="10%" valign="top" align="center" colspan="2">
															<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('SpecialTaxableBase','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion2"/></h3>
														</td>
														<td width="10%" valign="top" align="center" colspan="2">
															<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('SpecialTaxAmount','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion2"/></h3>
														</td>
														<td rowspan="2" width="10%" valign="center" align="center">
															<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('EquivalenceSurcharge','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion2"/></h3>
														</td>
														<td width="10%" valign="top" align="center" colspan="2">
															<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('EquivalenceSurchargeAmount','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion2"/></h3>
														</td>
													</tr>
													<tr>
														<td valign="top" align="center">
															<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('AmountLowcase','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion2"/></h3>
														</td>
														<td valign="top" align="center">
															<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('EquivalentLowcase','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion2"/></h3>
														</td>
														<td valign="top" align="center">														
															<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('AmountLowcase','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion2"/></h3>
														</td>
														<td valign="top" align="center">
															<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('EquivalentLowcase','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion2"/></h3>
														</td>
														<td valign="top" align="center">														
															<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('AmountLowcase','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion2"/></h3>
														</td>
														<td valign="top" align="center">
															<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('EquivalentLowcase','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion2"/></h3>
														</td>
														<td valign="top" align="center">														
															<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('AmountLowcase','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion2"/></h3>
														</td>
														<td valign="top" align="center">
															<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('EquivalentLowcase','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion2"/></h3>
														</td>
														<td valign="top" align="center">														
															<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('AmountLowcase','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion2"/></h3>
														</td>
														<td valign="top" align="center">
															<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('EquivalentLowcase','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion2"/></h3>
														</td>
													</tr>
													<xsl:for-each select="TaxesOutputs/Tax">
														<tr>
															<td width="100%">
																<xsl:variable name="toTranslate" select="TaxTypeCode"/>
																<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TaxTypeCode',$toTranslate,concat('lang_',$lang))"/>
																<xsl:value-of select="$traduccion2"/>
															</td>
															<td width="100%" align="center">
																<xsl:choose>
																	<xsl:when test='TaxRate!=""' >
																		<xsl:variable name="decimales"><xsl:value-of select="TaxRate"/></xsl:variable>
																		<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
																	</xsl:when>
																	<xsl:otherwise>
																		-
																	</xsl:otherwise>
																</xsl:choose>
															</td>
															<td width="100%" align="right">
																<xsl:choose>
																	<xsl:when test='TaxableBase/TotalAmount!=""' >
																		<xsl:variable name="decimales"><xsl:value-of select="TaxableBase/TotalAmount"/></xsl:variable>
																		<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
																	</xsl:when>
																	<xsl:otherwise>
																		-
																	</xsl:otherwise>
																</xsl:choose>
															</td>
														    <td width="100%" align="right">
																<xsl:choose>
																	<xsl:when test='TaxableBase/EquivalentInEuros!=""' >
																		<xsl:variable name="decimales"><xsl:value-of select="TaxableBase/EquivalentInEuros"/></xsl:variable>
																		<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
																	</xsl:when>
																	<xsl:otherwise>
																		-
																	</xsl:otherwise>
																</xsl:choose>
															</td>
															<td align="right">
																<xsl:choose>
																	<xsl:when test='TaxAmount/TotalAmount!=""' >
																		<xsl:variable name="decimales"><xsl:value-of select="TaxAmount/TotalAmount"/></xsl:variable>
																		<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
																	</xsl:when>
																	<xsl:otherwise>
																		-
																	</xsl:otherwise>
																</xsl:choose>
															</td>
															<td width="100%" align="right">
																<xsl:choose>
																	<xsl:when test='TaxAmount/EquivalentInEuros!=""' >
																		<xsl:variable name="decimales"><xsl:value-of select="TaxAmount/EquivalentInEuros"/></xsl:variable>
																		<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
																	</xsl:when>
																	<xsl:otherwise>
																		-
																	</xsl:otherwise>
																</xsl:choose>
															</td>
															<td align="right">
																<xsl:choose>
																	<xsl:when test='SpecialTaxableBase/TotalAmount!=""' >
																		<xsl:variable name="decimales"><xsl:value-of select="SpecialTaxableBase/TotalAmount"/></xsl:variable>
																		<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
																	</xsl:when>
																	<xsl:otherwise>
																		-
																	</xsl:otherwise>
																</xsl:choose>
															</td>
															<td width="100%" align="right">
																<xsl:choose>
																	<xsl:when test='SpecialTaxableBase/EquivalentInEuros!=""' >
																		<xsl:variable name="decimales"><xsl:value-of select="SpecialTaxableBase/EquivalentInEuros"/></xsl:variable>
																		<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
																	</xsl:when>
																	<xsl:otherwise>
																		-
																	</xsl:otherwise>
																</xsl:choose>
															</td>
															<td align="right">
																<xsl:choose>
																	<xsl:when test='SpecialTaxAmount/TotalAmount!=""' >
																		<xsl:variable name="decimales"><xsl:value-of select="SpecialTaxAmount/TotalAmount"/></xsl:variable>
																		<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
																	</xsl:when>
																	<xsl:otherwise>
																		-
																	</xsl:otherwise>
																</xsl:choose>
															</td>
															<td width="100%" align="right">
																<xsl:choose>
																	<xsl:when test='SpecialTaxAmount/EquivalentInEuros!=""' >
																		<xsl:variable name="decimales"><xsl:value-of select="SpecialTaxAmount/EquivalentInEuros"/></xsl:variable>
																		<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
																	</xsl:when>
																	<xsl:otherwise>
																		-
																	</xsl:otherwise>
																</xsl:choose>
															</td>
															<td width="100%" align="center">
																<xsl:choose>
																	<xsl:when test='EquivalenceSurcharge!=""' >
																		<xsl:variable name="decimales"><xsl:value-of select="EquivalenceSurcharge"/></xsl:variable>
																		<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
																	</xsl:when>
																	<xsl:otherwise>
																		-
																	</xsl:otherwise>
																</xsl:choose>
															</td>
															<td align="right">
																<xsl:choose>
																	<xsl:when test='EquivalenceSurchargeAmount/TotalAmount!=""' >
																		<xsl:variable name="decimales"><xsl:value-of select="EquivalenceSurchargeAmount/TotalAmount"/></xsl:variable>
																		<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
																	</xsl:when>
																	<xsl:otherwise>
																		-
																	</xsl:otherwise>
																</xsl:choose>
															</td>
															<td width="100%" align="right">
																<xsl:choose>
																	<xsl:when test='EquivalenceSurchargeAmount/EquivalentInEuros!=""' >
																		<xsl:variable name="decimales"><xsl:value-of select="EquivalenceSurchargeAmount/EquivalentInEuros"/></xsl:variable>
																		<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
																	</xsl:when>
																	<xsl:otherwise>
																		-
																	</xsl:otherwise>
																</xsl:choose>
															</td>
														</tr>
													</xsl:for-each>
												</table>
											</xsl:otherwise>
										</xsl:choose>
									</td>
								</tr>
								<tr>
									<td><font color="FFFFFF">_</font></td>
								</tr>
								<tr>
									<td width="100%">
										<table border="0" cellpadding="2" cellspacing="0" width="100%">
											<tr>
												<td width="80%" align="right">
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TotalTaxesOutputs','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/><font color="FFFFFF">___</font></h3>
												</td>
												<td width="20%" align="center">
													<table bordercolor="#e6f0fa" border="1" cellpadding="2" cellspacing="0" width="100%">
														<tr>
															<td align="right">
																<xsl:variable name="decimales"><xsl:value-of select="InvoiceTotals/TotalTaxOutputs"/></xsl:variable>
																<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td><font color="FFFFFF">_</font></td>
								</tr>
								</xsl:if>
								<xsl:if test='TaxesWithheld!=""' >
								<tr>
									<td>									
										<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TaxesWithheld','',concat('lang_',$lang))" />
										<h3><i><xsl:value-of select="$traduccion"/></i></h3>
										<xsl:choose>
											<xsl:when test='/*/FileHeader/Batch/InvoiceCurrencyCode="EUR"'>
												<table bordercolor="#e6f0fa" border="1" cellpadding="2" cellspacing="0" width="100%">
													<tr>
														<td width="50%" valign="top" align="center">
															<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TaxType','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion2"/></h3>
														</td>
														<td width="10%" valign="top" align="center">
															<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Type','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion2"/> (%)</h3>
														</td>
														<td width="20%" valign="top" align="center">
															<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TaxableBase','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion2"/></h3>
														</td>
														<td width="20%" valign="top" align="center">
															<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TaxAmount','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion"/></h3>
														</td>
													</tr>
													<tr>
														<td width="50%" valign="top">
															<table border="0" cellpadding="2" cellspacing="0" width="100%">
																<xsl:for-each select="TaxesWithheld/Tax">
																	<tr>
																		<td width="100%">
																			<xsl:variable name="toTranslate" select="TaxTypeCode"/>
																			<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TaxTypeCode',$toTranslate,concat('lang_',$lang))"/>
																			<xsl:value-of select="$traduccion2"/>
																		</td>
																	</tr>
																</xsl:for-each>
															</table>
														</td>
														<td width="10%" valign="top">
															<table border="0" cellpadding="2" cellspacing="0" width="100%">
																<xsl:for-each select="TaxesWithheld/Tax">
																	<tr>
																		<td width="100%" align="center">
																			<xsl:choose>
																				<xsl:when test='TaxRate!=""' >
																					<xsl:variable name="decimales"><xsl:value-of select="TaxRate"/></xsl:variable>
																					<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
																				</xsl:when>
																				<xsl:otherwise>
																					-
																				</xsl:otherwise>
																			</xsl:choose>
																		</td>
																	</tr>
																</xsl:for-each>
															</table>
														</td>
														<td width="10%" valign="top">
															<table border="0" cellpadding="2" cellspacing="0" width="100%">
																<xsl:for-each select="TaxesWithheld/Tax">
																	<tr>
																		<td width="100%" align="right">
																			<xsl:choose>
																				<xsl:when test='TaxableBase!=""' >
																					<xsl:variable name="decimales"><xsl:value-of select="TaxableBase/TotalAmount"/></xsl:variable>
																					<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
																				</xsl:when>
																				<xsl:otherwise>
																					-
																				</xsl:otherwise>
																			</xsl:choose>
																		</td>
																	</tr>
																</xsl:for-each>
															</table>
														</td>
														<td width="20%" valign="top">
															<table border="0" cellpadding="2" cellspacing="0" width="100%">
																<xsl:for-each select="TaxesWithheld/Tax">
																	<tr>
																		<td align="right">
																			<xsl:choose>
																				<xsl:when test='TaxAmount!=""' >
																					<xsl:variable name="decimales"><xsl:value-of select="TaxAmount/TotalAmount"/></xsl:variable>
																					<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
																				</xsl:when>
																				<xsl:otherwise>
																					-
																				</xsl:otherwise>
																			</xsl:choose>
																		</td>
																	</tr>
																</xsl:for-each>
															</table>
														</td>
													</tr>
												</table>
											</xsl:when>
											<xsl:otherwise>
												<table bordercolor="#e6f0fa" border="1" cellpadding="2" cellspacing="0" width="100%">
													<tr>
														<td width="50%" valign="top" align="center">
															<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TaxType','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion2"/></h3>
														</td>
														<td width="10%" valign="top" align="center">
															<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Type','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion2"/> (%)</h3>
														</td>
														<td width="20%" valign="top" align="center" colspan="2">
															<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TaxableBase','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion2"/></h3>
														</td>
														<td width="20%" valign="top" align="center" colspan="2">
															<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TaxAmount','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion2"/></h3>
														</td>
													</tr>
													<tr>
														<td><font color="#ffffff">_</font></td>
														<td><font color="#ffffff">_</font></td>
														<td valign="top" align="center">
															<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('AmountLowcase','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion2"/></h3>
														</td>
														<td valign="top" align="center">
															<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('EquivalentLowcase','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion2"/></h3>
														</td>
														<td valign="top" align="center">														
															<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('AmountLowcase','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion2"/></h3>
														</td>
														<td valign="top" align="center">
															<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('EquivalentLowcase','',concat('lang_',$lang))" />
															<h3><xsl:value-of select="$traduccion2"/></h3>
														</td>
													</tr>
													<tr>
														<td width="30%" valign="top">
															<table border="0" cellpadding="2" cellspacing="0" width="100%">
																<xsl:for-each select="TaxesWithheld/Tax">
																	<tr>
																		<td width="100%">
																			<xsl:variable name="toTranslate" select="TaxTypeCode"/>
																			<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TaxTypeCode',$toTranslate,concat('lang_',$lang))"/>
																			<xsl:value-of select="$traduccion2"/>
																		</td>
																	</tr>
																</xsl:for-each>
															</table>
														</td>
														<td width="10%" valign="top">
															<table border="0" cellpadding="2" cellspacing="0" width="100%">
																<xsl:for-each select="TaxesWithheld/Tax">
																	<tr>
																		<td width="100%" align="center">
																			<xsl:choose>
																				<xsl:when test='TaxRate!=""' >
																					<xsl:variable name="decimales"><xsl:value-of select="TaxRate"/></xsl:variable>
																					<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
																				</xsl:when>
																				<xsl:otherwise>
																					-
																				</xsl:otherwise>
																			</xsl:choose>
																		</td>
																	</tr>
																</xsl:for-each>
															</table>
														</td>
														<td width="15%" valign="top">
															<table border="0" cellpadding="2" cellspacing="0" width="100%">
																<xsl:for-each select="TaxesWithheld/Tax">
																	<tr>
																		<td width="100%" align="right">
																			<xsl:choose>
																				<xsl:when test='TaxableBase/TotalAmount!=""' >
																					<xsl:variable name="decimales"><xsl:value-of select="TaxableBase/TotalAmount"/></xsl:variable>
																					<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
																				</xsl:when>
																				<xsl:otherwise>
																					-
																				</xsl:otherwise>
																			</xsl:choose>
																		</td>
																	</tr>
																</xsl:for-each>
															</table>
														</td>
														<td width="15%" valign="top">
															<table border="0" cellpadding="2" cellspacing="0" width="100%">
																<xsl:for-each select="TaxesWithheld/Tax">
																	<tr>
																		<td width="100%" align="right">
																			<xsl:choose>
																				<xsl:when test='TaxableBase/EquivalentInEuros!=""' >
																					<xsl:variable name="decimales"><xsl:value-of select="TaxableBase/EquivalentInEuros"/></xsl:variable>
																					<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
																				</xsl:when>
																				<xsl:otherwise>
																					-
																				</xsl:otherwise>
																			</xsl:choose>
																		</td>
																	</tr>
																</xsl:for-each>
															</table>
														</td>
														<td width="15%" valign="top">
															<table border="0" cellpadding="2" cellspacing="0" width="100%">
																<xsl:for-each select="TaxesWithheld/Tax">
																	<tr>
																		<td align="right">
																			<xsl:choose>
																				<xsl:when test='TaxAmount/TotalAmount!=""' >
																					<xsl:variable name="decimales"><xsl:value-of select="TaxAmount/TotalAmount"/></xsl:variable>
																					<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
																				</xsl:when>
																				<xsl:otherwise>
																					-
																				</xsl:otherwise>
																			</xsl:choose>
																		</td>
																	</tr>
																</xsl:for-each>
															</table>
														</td>
														<td width="15%" valign="top">
															<table border="0" cellpadding="2" cellspacing="0" width="100%">
																<xsl:for-each select="TaxesWithheld/Tax">
																	<tr>
																		<td align="right">
																			<xsl:choose>
																				<xsl:when test='TaxAmount/EquivalentInEuros!=""' >
																					<xsl:variable name="decimales"><xsl:value-of select="TaxAmount/EquivalentInEuros"/></xsl:variable>
																					<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
																				</xsl:when>
																				<xsl:otherwise>
																					-
																				</xsl:otherwise>
																			</xsl:choose>
																		</td>
																	</tr>
																</xsl:for-each>
															</table>
														</td>
													</tr>
												</table>
											</xsl:otherwise>
										</xsl:choose>														
									</td>
								</tr>
								<tr>
									<td><font color="FFFFFF">_</font></td>
								</tr>
								<tr>
									<td width="100%">
										<table border="0" cellpadding="2" cellspacing="0" width="100%">
											<tr>
												<td width="80%" align="right">
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TotalTaxesWithheld','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/><font color="FFFFFF">___</font></h3>
												</td>
												<td width="20%" align="center">
													<table bordercolor="#e6f0fa" border="1" cellpadding="2" cellspacing="0" width="100%">
														<tr>
															<td align="right">
																<xsl:variable name="decimales"><xsl:value-of select="InvoiceTotals/TotalTaxesWithheld"/></xsl:variable>
																<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td><font color="FFFFFF">_</font></td>
								</tr>
								</xsl:if>	
								<tr>
									<td width="100%">
										<table border="0" cellpadding="2" cellspacing="0" width="100%">
											<tr>
												<td width="80%" align="right">
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('InvoiceTotal','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/><font color="FFFFFF">___</font></h3>
												</td>
												<td width="20%" align="center">
													<table bordercolor="#e6f0fa" border="1" cellpadding="2" cellspacing="0" width="100%">
														<tr>
															<td align="right">
																<xsl:variable name="decimales"><xsl:value-of select="InvoiceTotals/InvoiceTotal"/></xsl:variable>
																<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td><font color="FFFFFF">_</font></td>
								</tr>	
								<xsl:if test='InvoiceTotals/Subsidies!=""' >
								<tr>
									<td>									
										<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Subsidies','',concat('lang_',$lang))" />
										<h3><i><xsl:value-of select="$traduccion"/></i></h3>
										<table bordercolor="#e6f0fa" border="1" cellpadding="2" cellspacing="0" width="100%">
											<tr>
												<td width="70%" valign="top" align="center">
													<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Item','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion2"/></h3>
												</td>
												<td width="10%" valign="top" align="center">
													<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Type','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion2"/> (%)</h3>
												</td>
												<td width="20%" valign="top" align="center">
													<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Amount','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion2"/></h3>
												</td>
											</tr>
											<tr>
												<td width="70%" valign="top">
													<table border="0" cellpadding="2" cellspacing="0" width="100%">
														<xsl:for-each select="InvoiceTotals/Subsidies/Subsidy">
															<tr>
																<td width="100%">
																	<xsl:apply-templates select="SubsidyDescription"/>
																</td>
															</tr>
														</xsl:for-each>
													</table>
												</td>
												<td width="10%" valign="top">
													<table border="0" cellpadding="2" cellspacing="0" width="100%">
														<xsl:for-each select="InvoiceTotals/Subsidies/Subsidy">
															<tr>
																<td width="100%" align="center">
																	<xsl:choose>
																		<xsl:when test='SubsidyRate!=""' >
																			<xsl:variable name="decimales"><xsl:value-of select="SubsidyRate"/></xsl:variable>
																			<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
																		</xsl:when>
																		<xsl:otherwise>
																			-
																		</xsl:otherwise>
																	</xsl:choose>
																</td>
															</tr>
														</xsl:for-each>
													</table>
												</td>
												<td width="20%" valign="top">
													<table border="0" cellpadding="2" cellspacing="0" width="100%">
														<xsl:for-each select="InvoiceTotals/Subsidies/Subsidy">
															<tr>
																<td align="right">
																	<xsl:variable name="decimales"><xsl:value-of select="SubsidyAmount"/></xsl:variable>
																	<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
																</td>
															</tr>
														</xsl:for-each>
													</table>
												</td>
											</tr>
										</table>					
									</td>
								</tr>
								<tr>
									<td><font color="FFFFFF">_</font></td>
								</tr>
								</xsl:if>
								<xsl:if test='InvoiceTotals/PaymentsonAccount!=""' >
								<tr>
									<td align="right">									
										<table border="0" cellpadding="2" cellspacing="0" width="40%">
											<tr>
												<td>
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('PaymentOnAccount','',concat('lang_',$lang))" />
													<h3><i><xsl:value-of select="$traduccion"/></i></h3>
												</td>
											</tr>
										</table>
										<table bordercolor="#e6f0fa" border="1" cellpadding="2" cellspacing="0" width="40%">
											<tr>
												<td width="50%" valign="top" align="center">
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Date','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/></h3>
												</td>
												<td width="50%" valign="top" align="center">
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Amount','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/></h3>
												</td>
											</tr>
											<tr>
												<td width="50%" valign="top">
													<table border="0" cellpadding="2" cellspacing="0" width="100%">
														<xsl:for-each select="InvoiceTotals/PaymentsonAccount/PaymentOnAccount">
															<tr>
																<td width="100%" align="center">
																	<xsl:value-of select="substring(PaymentOnAccountDate,9,2)"/>-<xsl:value-of select="substring(PaymentOnAccountDate,6,2)"/>-<xsl:value-of select="substring(PaymentOnAccountDate,1,4)"/>
																</td>
															</tr>
														</xsl:for-each>
													</table>
												</td>
												<td width="50%" valign="top">
													<table border="0" cellpadding="2" cellspacing="0" width="100%">
														<xsl:for-each select="InvoiceTotals/PaymentsonAccount/PaymentOnAccount">
															<tr>
																<td align="right">
																	<xsl:variable name="decimales"><xsl:value-of select="PaymentOnAccountAmount"/></xsl:variable>
																	<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
																</td>
															</tr>
														</xsl:for-each>
													</table>
												</td>
											</tr>
										</table>									
									</td>
								</tr>
								<tr>
									<td><font color="FFFFFF">_</font></td>
								</tr>
								<tr>
									<td width="100%">
										<table border="0" cellpadding="2" cellspacing="0" width="100%">
											<tr>
												<td width="80%" align="right">
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TotalSubsidies','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/><font color="FFFFFF">___</font></h3>
												</td>
												<td width="20%" align="center">
													<table bordercolor="#e6f0fa" border="1" cellpadding="2" cellspacing="0" width="100%">
														<tr>
															<td align="right">
																<xsl:variable name="decimales"><xsl:value-of select="InvoiceTotals/TotalPaymentOnAccountAmount"/></xsl:variable>
																<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td><font color="FFFFFF">_</font></td>
								</tr>
								</xsl:if>
								<tr>
									<td width="100%">
										<table border="0" cellpadding="2" cellspacing="0" width="100%">
											<tr>
												<td width="80%" align="right">
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TotalToPay','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/><font color="FFFFFF">___</font></h3>
												</td>
												<td width="20%" align="center">
													<table bordercolor="#e6f0fa" border="1" cellpadding="2" cellspacing="0" width="100%">
														<tr>
															<td align="right">
																<xsl:variable name="decimales"><xsl:value-of select="InvoiceTotals/TotalOutstandingAmount"/></xsl:variable>
																<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td><font color="FFFFFF">_</font></td>
								</tr>
								<xsl:if test='InvoiceTotals/AmountsWithheld!=""' >
								<tr>
									<td>									
										<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('AmountsWithheld','',concat('lang_',$lang))" />
										<h3><i><xsl:value-of select="$traduccion"/></i></h3>
										<table bordercolor="#e6f0fa" border="1" cellpadding="2" cellspacing="0" width="100%">
											<tr>
												<td width="70%" valign="top" align="center">
													<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Item','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion2"/></h3>
												</td>
												<td width="10%" valign="top" align="center">
													<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Type','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion2"/> (%)</h3>
												</td>
												<td width="20%" valign="top" align="center">
													<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Amount','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion2"/></h3>
												</td>
											</tr>
											<tr>
												<td width="70%" valign="top">
													<xsl:apply-templates select="InvoiceTotals/AmountsWithheld/WithholdingReason"/>
												</td>
												<td width="10%" valign="top" align="center">
													<xsl:choose>
														<xsl:when test='InvoiceTotals/AmountsWithheld/WithholdingRate!=""' >
															<xsl:variable name="decimales"><xsl:value-of select="InvoiceTotals/AmountsWithheld/WithholdingRate"/></xsl:variable>
															<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
														</xsl:when>
														<xsl:otherwise>
															-
														</xsl:otherwise>
													</xsl:choose>
												</td>
												<td width="20%" valign="top" align="right">
													<xsl:variable name="decimales"><xsl:value-of select="InvoiceTotals/AmountsWithheld/WithholdingAmount"/></xsl:variable>
													<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
												</td>
											</tr>
										</table>					
									</td>
								</tr>
								<tr>
									<td><font color="FFFFFF">_</font></td>
								</tr>
								</xsl:if>
								<xsl:if test='InvoiceTotals/ReimbursableExpenses!=""' >
								<tr>
									<td>									
										<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('ReimbursableExpenses','',concat('lang_',$lang))" />
										<h3><i><xsl:value-of select="$traduccion"/></i></h3>
										<table bordercolor="#e6f0fa" border="1" cellpadding="2" cellspacing="0" width="100%">
											<tr>
												<td colspan="3" width="30%" valign="top" align="center">
													<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('ReimbursableExpensesSellerParty','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion2"/></h3>
												</td>
												<td colspan="3" width="30%" valign="top" align="center">
													<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('ReimbursableExpensesBuyerParty','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion2"/> (%)</h3>
												</td>
												<td rowspan="2" width="10%" valign="top" align="center">
													<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('IssueDate','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion2"/></h3>
												</td>
												<td rowspan="2" width="10%" valign="top" align="center">
													<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('InvoiceNumber','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion2"/></h3>
												</td>
												<td rowspan="2" width="10%" valign="top" align="center">
													<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Serie','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion2"/></h3>
												</td>
												<td rowspan="2" width="10%" valign="top" align="center">
													<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('ReimbursableExpensesAmount','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion2"/></h3>
												</td>
											</tr>
											<tr>
												<td width="10%" valign="top" align="center">
													<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('PersonType','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion2"/></h3>
												</td>
												<td width="10%" valign="top" align="center">
													<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('ResidenceType','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion2"/> (%)</h3>
												</td>
												<td width="10%" valign="top" align="center">
													<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TaxIdentificationNumber','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion2"/></h3>
												</td>
												<td width="10%" valign="top" align="center">
													<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('PersonType','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion2"/></h3>
												</td>
												<td width="10%" valign="top" align="center">
													<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('ResidenceType','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion2"/> (%)</h3>
												</td>
												<td width="10%" valign="top" align="center">
													<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TaxIdentificationNumber','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion2"/></h3>
												</td>
											</tr>
											<xsl:for-each select="InvoiceTotals/ReimbursableExpenses/ReimbursableExpenses">
												<tr>
													<xsl:choose>
														<xsl:when test='ReimbursableExpensesSellerParty!=""' >
															<td width="10%" align="center">
																<xsl:variable name="toTranslate" select="ReimbursableExpensesSellerParty/PersonTypeCode" />
																<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('PersonTypeCode',$toTranslate,concat('lang_',$lang))" />
																<xsl:value-of select="$traduccion2" />
															</td>
															<td width="10%" align="center">
																<xsl:variable name="toTranslate" select="ReimbursableExpensesSellerParty/ResidenceTypeCode" />
																<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('ResidenceTypeCode',$toTranslate,concat('lang_',$lang))" />
																<xsl:value-of select="$traduccion2" />
															</td>
															<td width="10%" align="center">
																<xsl:value-of select="ReimbursableExpensesSellerParty/TaxIdentificationNumber" />
															</td>
														</xsl:when>
														<xsl:otherwise>
															<td width="10%" align="center">
																<font color="FFFFFF">_</font>
															</td>
															<td width="10%" align="center">
																<font color="FFFFFF">_</font>
															</td>
															<td width="10%" align="center">
																<font color="FFFFFF">_</font>
															</td>
														</xsl:otherwise>
													</xsl:choose>
													<xsl:choose>
														<xsl:when test='ReimbursableExpensesBuyerParty!=""' >
															<td width="10%" align="center">
																<xsl:variable name="toTranslate" select="ReimbursableExpensesBuyerParty/PersonTypeCode" />
																<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('PersonTypeCode',$toTranslate,concat('lang_',$lang))" />
																<xsl:value-of select="$traduccion2" />
															</td>
															<td width="10%" align="center">
																<xsl:variable name="toTranslate" select="ReimbursableExpensesBuyerParty/ResidenceTypeCode" />
																<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('ResidenceTypeCode',$toTranslate,concat('lang_',$lang))" />
																<xsl:value-of select="$traduccion2" />
															</td>
															<td width="10%" align="center">
																<xsl:value-of select="ReimbursableExpensesBuyerParty/TaxIdentificationNumber" />
															</td>
														</xsl:when>
														<xsl:otherwise>
															<td width="10%" align="center">
																<font color="FFFFFF">_</font>
															</td>
															<td width="10%" align="center">
																<font color="FFFFFF">_</font>
															</td>
															<td width="10%" align="center">
																<font color="FFFFFF">_</font>
															</td>
														</xsl:otherwise>
													</xsl:choose>
													<td width="10%" align="center">
														<xsl:choose>
															<xsl:when test='IssueDate!=""'>
																<xsl:value-of select="substring(IssueDate,9,2)"/>-<xsl:value-of select="substring(IssueDate,6,2)"/>-<xsl:value-of select="substring(IssueDate,1,4)"/>
															</xsl:when>
															<xsl:otherwise>
																<font color="FFFFFF">_</font>
															</xsl:otherwise>
														</xsl:choose>
													</td>
													<td width="10%" align="center">
														<xsl:choose>
															<xsl:when test='InvoiceNumber!=""'>
																<xsl:value-of select="InvoiceNumber" />
															</xsl:when>
															<xsl:otherwise>
																<font color="FFFFFF">_</font>
															</xsl:otherwise>
														</xsl:choose>
													</td>
													<td width="10%" align="center">
														<xsl:choose>
															<xsl:when test='InvoiceSeriesCode!=""'>
																<xsl:value-of select="InvoiceSeriesCode" />
															</xsl:when>
															<xsl:otherwise>
																<font color="FFFFFF">_</font>
															</xsl:otherwise>
														</xsl:choose>
													</td>
													<td width="10%" align="center">
														<xsl:choose>
															<xsl:when test='ReimbursableExpensesAmount!=""'>
																<xsl:variable name="decimales"><xsl:value-of select="ReimbursableExpensesAmount"/></xsl:variable>
																<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
															</xsl:when>
															<xsl:otherwise>
																<font color="FFFFFF">_</font>
															</xsl:otherwise>
														</xsl:choose>
													</td>
												</tr>
											</xsl:for-each>
										</table>
									</td>
								</tr>
								<tr>
									<td><font color="FFFFFF">_</font></td>
								</tr>	
								</xsl:if>
								<xsl:if test='InvoiceTotals/TotalReimbursableExpenses!=""' >
								<tr>
									<td width="100%">
										<table border="0" cellpadding="2" cellspacing="0" width="100%">
											<tr>
												<td width="80%" align="right">
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TotalReimbursable','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/><font color="FFFFFF">___</font></h3>
												</td>
												<td width="20%" align="center">
													<table bordercolor="#e6f0fa" border="1" cellpadding="2" cellspacing="0" width="100%">
														<tr>
															<td align="right">
																<xsl:variable name="decimales"><xsl:value-of select="InvoiceTotals/TotalReimbursableExpenses"/></xsl:variable>
																<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td><font color="FFFFFF">_</font></td>
								</tr>
								</xsl:if>
								<xsl:if test='InvoiceTotals/TotalFinancialExpenses!=""' >
								<tr>
									<td width="100%">
										<table border="0" cellpadding="2" cellspacing="0" width="100%">
											<tr>
												<td width="80%" align="right">
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TotalFinancialExpenses','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/><font color="FFFFFF">___</font></h3>
												</td>
												<td width="20%" align="center">
													<table bordercolor="#e6f0fa" border="1" cellpadding="2" cellspacing="0" width="100%">
														<tr>
															<td align="right">
																<xsl:variable name="decimales"><xsl:value-of select="InvoiceTotals/TotalFinancialExpenses"/></xsl:variable>
																<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td><font color="FFFFFF">_</font></td>
								</tr>
								</xsl:if>
								<tr>
									<td width="100%">
										<table border="0" cellpadding="2" cellspacing="0" width="100%">
											<tr>
												<td width="80%" align="right">
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TotalToExecute','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/><font color="FFFFFF">___</font></h3>
												</td>
												<td width="20%" align="center">
													<table bordercolor="#e6f0fa" border="1" cellpadding="2" cellspacing="0" width="100%">
														<tr>
															<td align="right">
																<xsl:variable name="decimales"><xsl:value-of select="InvoiceTotals/TotalExecutableAmount"/></xsl:variable>
																<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<xsl:apply-templates select="PaymentDetails"/>
					<xsl:apply-templates select="LegalLiterals"/>
					<xsl:apply-templates select="AdditionalData"/>					
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<xsl:apply-templates select="Items/InvoiceLine">
					<xsl:with-param name="nFactura" select="$numFactura"/>
					<xsl:with-param name="lang" select="$lang"/>
				</xsl:apply-templates>
			</td>
		</tr>
	</xsl:template>
	<xsl:template match="/*/Invoices/Invoice/InvoiceHeader/Corrective">
		<xsl:variable name="lang" select="/*/Invoices/Invoice/InvoiceIssueData/LanguageName"/>
		<tr>
			<td><font color="FFFFFF">_</font></td>
		</tr>
		<tr>
			<td>
				<hr color="#a0d7a0"/>
			</td>
		</tr>	
		<tr>
			<td width="100%">
				<table border="0" cellpadding="2" cellspacing="0" width="100%">
					<tr>
						<td colspan="2">
							<xsl:variable name="traduccion" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('CorrectiveInvoice','',concat('lang_',$lang))"/>
							<h2><xsl:value-of select="$traduccion"/></h2>
						</td>
					</tr>
					<tr>
						<td colspan="2"><font color="FFFFFF">_</font></td>
					</tr>
					<tr>
						<td width="50%">
							<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Number','',concat('lang_',$lang))" />
							<h3><xsl:value-of select="$traduccion2"/>:</h3>
							<font color="FFFFFF">__</font>
							<xsl:value-of select="InvoiceNumber"/><br/>
						</td>
						<xsl:if test='InvoiceSeriesCode!=""'>
							<td width="50%">
								<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Serie','',concat('lang_',$lang))" />
								<h3><xsl:value-of select="$traduccion2"/>:</h3>
								<font color="FFFFFF">__</font>
								<xsl:value-of select="InvoiceSeriesCode"/><br/>
							</td>
						</xsl:if>
					</tr>
					<tr>
						<td colspan="2" width="100%">
							<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('AdditionalReasonDescription','',concat('lang_',$lang))" />
							<h3><xsl:value-of select="$traduccion2"/>:</h3>
							<font color="FFFFFF">__</font>
							<xsl:value-of select="AdditionalReasonDescription"/><br/>
						</td>
					</tr>					
					<tr>
						<td width="50%">
							<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Reason','',concat('lang_',$lang))" />
							<h3><xsl:value-of select="$traduccion"/>:</h3>
							<font color="FFFFFF">__</font>
							<xsl:value-of select="ReasonCode"/> - <xsl:value-of select="ReasonDescription"/>
						</td>
						<td width="50%">
							<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('RectificationCriteria','',concat('lang_',$lang))" />
							<h3><xsl:value-of select="$traduccion"/>:</h3>
							<font color="FFFFFF">__</font>
							<xsl:value-of select="CorrectionMethod"/> - <xsl:value-of select="CorrectionMethodDescription"/>
						</td>
					</tr>
					
					<tr>
						<td valign="top" colspan="2">
							<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TaxablePeriod','',concat('lang_',$lang))" />
							<h3><xsl:value-of select="$traduccion"/>:</h3>
						</td>						
					</tr>
					<tr>								
						<td colspan="2">
							<table border="0" cellpadding="2" cellspacing="0" width="100%">
								<tr>
									<td width="50%">
										<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('StartingDate','',concat('lang_',$lang))" />
										<h3><xsl:value-of select="$traduccion"/>:</h3><font color="FFFFFF">__</font>
										<xsl:value-of select="substring(TaxPeriod/StartDate,9,2)"/>-<xsl:value-of select="substring(TaxPeriod/StartDate,6,2)"/>-<xsl:value-of select="substring(TaxPeriod/StartDate,1,4)"/>
									</td>
									<td width="50%">
										<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('EndingDate','',concat('lang_',$lang))" />
										<h3><xsl:value-of select="$traduccion"/>:</h3><font color="FFFFFF">___</font>
										<xsl:value-of select="substring(TaxPeriod/EndDate,9,2)"/>-<xsl:value-of select="substring(TaxPeriod/EndDate,6,2)"/>-<xsl:value-of select="substring(TaxPeriod/EndDate,1,4)"/>
									</td>
								</tr>
							</table>
						</td>
					</tr>						
				</table>
			</td>
		</tr>
	</xsl:template>
	<xsl:template match="PaymentDetails">
		<xsl:variable name="lang" select="/*/Invoices/Invoice/InvoiceIssueData/LanguageName"/>
		<tr>
			<td><font color="FFFFFF">_</font></td>
		</tr>
		<tr>
			<td>
				<hr color="#a0d7a0"/>
			</td>
		</tr>
		<tr>
			<td>
				<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('PaymentData','',concat('lang_',$lang))" />
				<h2><xsl:value-of select="$traduccion"/></h2>
			</td>
		</tr>
		<tr>
			<td><font color="FFFFFF">_</font></td>
		</tr>
		<tr>
			<td width="100%">
				<table bordercolor="#e6f0fa" border="1" cellpadding="2" cellspacing="0" width="100%">
					<tr>
						<td width="10%" align="center">
							<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('DueDate','',concat('lang_',$lang))" />
							<h3><xsl:value-of select="$traduccion"/></h3>
						</td>
						<td width="10%" align="center">
							<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Amount','',concat('lang_',$lang))" />
							<h3><xsl:value-of select="$traduccion"/></h3>
						</td>
						<td width="10%" align="center">
							<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('PaymentMean','',concat('lang_',$lang))" />
							<h3><xsl:value-of select="$traduccion"/></h3>
						</td>
						<td width="15%" align="center">
							<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('DebitAccount','',concat('lang_',$lang))" />
							<h3><xsl:value-of select="$traduccion"/></h3>
						</td>
						<td width="15%" align="center">
							<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('CreditAccount','',concat('lang_',$lang))" />
							<h3><xsl:value-of select="$traduccion"/></h3>
						</td>
						<td width="10%" align="center">
							<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('PaymentReference','',concat('lang_',$lang))" />
							<h3><xsl:value-of select="$traduccion"/></h3>
						</td>
						<td width="10%" align="center">
							<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('DebitReference','',concat('lang_',$lang))" />
							<h3><xsl:value-of select="$traduccion"/></h3>
						</td>
						<td width="10%" align="center">
							<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('RegulatoryReportingData','',concat('lang_',$lang))" />
							<h3><xsl:value-of select="$traduccion"/></h3>
						</td>
						<td width="10%" align="center">
							<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Comments','',concat('lang_',$lang))" />
							<h3><xsl:value-of select="$traduccion"/></h3>
						</td>
					</tr>
					<xsl:for-each select="Installment">
						<tr>
							<td width="10%" valign="top" align="center">
								<xsl:value-of select="substring(InstallmentDueDate,9,2)"/>-<xsl:value-of select="substring(InstallmentDueDate,6,2)"/>-<xsl:value-of select="substring(InstallmentDueDate,1,4)"/>
							</td>
							<td width="10%" valign="top" align="right">
								<xsl:variable name="decimales"><xsl:value-of select="InstallmentAmount"/></xsl:variable>
								<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>			
							</td>
							<td width="10%" valign="top"  align="center">
								<xsl:variable name="toTranslate" select="PaymentMeans"/>
								<xsl:variable name="traduccion" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('PaymentMeansType',$toTranslate,concat('lang_',$lang))"/>
								<xsl:value-of select="$traduccion"/>
							</td>
							<td width="15%" valign="top"  align="center">
								<xsl:if test='AccountToBeDebited!=""' >
									<table border="0" cellpadding="2" cellspacing="0" width="80%" align="center">											
										<tr>
											<xsl:choose>
												<xsl:when test='AccountToBeDebited/IBAN!=""'>
													<td width="30%">
														<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('IBAN','',concat('lang_',$lang))" />
														<h3><xsl:value-of select="$traduccion"/>:</h3>
													</td>
													<td width="70%">
														<xsl:value-of select="AccountToBeDebited/IBAN"/>
													</td>
												</xsl:when>
												<xsl:when test='AccountToBeDebited/AccountNumber!=""'>	
													<td width="30%">
														<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('AccountNumber','',concat('lang_',$lang))" />
														<h3><xsl:value-of select="$traduccion"/>:</h3>
													</td>
													<td width="70%">
														<xsl:value-of select="AccountToBeDebited/AccountNumber"/>
													</td>
												</xsl:when>
												<xsl:otherwise>
													<td width="30%">
														<font color="FFFFFF">_</font>
													</td>
													<td width="70%">
														<font color="FFFFFF">_</font>
													</td>
												</xsl:otherwise>
											</xsl:choose>
										</tr>
										<xsl:if test='AccountToBeDebited/BankCode!=""' >
											<tr>
												<td>
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Entity','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/>:</h3>
												</td>
												<td>
													<xsl:apply-templates select="AccountToBeDebited/BankCode"/>
												</td>
											</tr>
										</xsl:if>
										<xsl:if test='AccountToBeDebited/BranchCode!=""' >
											<tr>
												<td>
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Office','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/>:</h3>
												</td>
												<td>
													<xsl:apply-templates select="AccountToBeDebited/BranchCode"/>
												</td>
											</tr>
										</xsl:if>
										<xsl:if test='AccountToBeDebited/BranchInSpainAddress!=""' >
											<tr>
												<td valign="top">
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('AddressLowcase','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/>:</h3>
												</td>
												<td>
													<xsl:value-of select="AccountToBeDebited/BranchInSpainAddress/Address"/><br/>
													<xsl:value-of select="AccountToBeDebited/BranchInSpainAddress/PostCode"/><font color="FFFFFF">__</font>
													<xsl:value-of select="AccountToBeDebited/BranchInSpainAddress/Town"/><br/>
													<xsl:value-of select="AccountToBeDebited/BranchInSpainAddress/Province"/><br/>
													<xsl:value-of select="AccountToBeDebited/BranchInSpainAddress/CountryCode"/>
												</td>
											</tr>	
										</xsl:if>													
										<xsl:if test='AccountToBeDebited/OverseasBranchAddress!=""' >
											<tr>
												<td valign="top">
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('AddressLowcase','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/>:</h3>
												</td>
												<td>
													<xsl:value-of select="AccountToBeDebited/OverseasBranchAddress/Address"/><br/>
													<xsl:value-of select="AccountToBeDebited/OverseasBranchAddress/PostCodeAndTown"/><br/>
													<xsl:value-of select="AccountToBeDebited/OverseasBranchAddress/Province"/><br/>
													<xsl:value-of select="AccountToBeDebited/OverseasBranchAddress/CountryCode"/>
												</td>
											</tr>
										</xsl:if>
										<xsl:if test='AccountToBeDebited/BIC!=""' >
											<tr>
												<td>
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('BIC','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/>:</h3>
												</td>
												<td>
													<xsl:apply-templates select="AccountToBeDebited/BIC"/>
												</td>
											</tr>
										</xsl:if>
									</table>
								</xsl:if>
							</td>	
							<td width="15%" valign="top"  align="center">		
								<xsl:if test='AccountToBeCredited!=""' >
									<table border="0" cellpadding="2" cellspacing="0" width="80%" align="center">											
										<tr>
											<xsl:choose>
												<xsl:when test='AccountToBeCredited/IBAN!=""'>
													<td width="30%">
														<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('IBAN','',concat('lang_',$lang))" />
														<h3><xsl:value-of select="$traduccion"/>:</h3>
													</td>
													<td width="70%">
														<xsl:value-of select="AccountToBeCredited/IBAN"/>
													</td>
												</xsl:when>
												<xsl:when test='AccountToBeCredited/AccountNumber!=""'>	
													<td width="30%">
														<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('AccountNumber','',concat('lang_',$lang))" />
														<h3><xsl:value-of select="$traduccion"/>:</h3>
													</td>
													<td width="70%">
														<xsl:value-of select="AccountToBeCredited/AccountNumber"/>
													</td>
												</xsl:when>
												<xsl:otherwise>
													<td width="30%">
														<font color="FFFFFF">_</font>
													</td>
													<td width="70%">
														<font color="FFFFFF">_</font>
													</td>
												</xsl:otherwise>
											</xsl:choose>
										</tr>
										<xsl:if test='AccountToBeCredited/BankCode!=""' >
											<tr>
												<td>
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Entity','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/>:</h3>
												</td> 
												<td>
													<xsl:apply-templates select="AccountToBeCredited/BankCode"/>
												</td>
											</tr>
										</xsl:if>
										<xsl:if test='AccountToBeCredited/BranchCode!=""' >
											<tr>
												<td>
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Office','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/>:</h3>
												</td>
												<td>
													<xsl:apply-templates select="AccountToBeCredited/BranchCode"/>
												</td>
											</tr>
										</xsl:if>
										<xsl:if test='AccountToBeCredited/BranchInSpainAddress!=""' >
											<tr>
												<td valign="top">
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('AddressLowcase','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/>:</h3>
												</td>
												<td>
													<xsl:value-of select="AccountToBeCredited/BranchInSpainAddress/Address"/><br/>
													<xsl:value-of select="AccountToBeCredited/BranchInSpainAddress/PostCode"/><font color="FFFFFF">__</font>
													<xsl:value-of select="AccountToBeCredited/BranchInSpainAddress/Town"/><br/>
													<xsl:value-of select="AccountToBeCredited/BranchInSpainAddress/Province"/><br/>
													<xsl:value-of select="AccountToBeCredited/BranchInSpainAddress/CountryCode"/>
												</td>
											</tr>	
										</xsl:if>													
										<xsl:if test='AccountToBeCredited/OverseasBranchAddress!=""' >
											<tr>
												<td valign="top">
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('AddressLowcase','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/>:</h3>
												</td>
												<td>
													<xsl:value-of select="AccountToBeCredited/OverseasBranchAddress/Address"/><br/>
													<xsl:value-of select="AccountToBeCredited/OverseasBranchAddress/PostCodeAndTown"/><br/>
													<xsl:value-of select="AccountToBeCredited/OverseasBranchAddress/Province"/><br/>
													<xsl:value-of select="AccountToBeCredited/OverseasBranchAddress/CountryCode"/>
												</td>
											</tr>
										</xsl:if>
										<xsl:if test='AccountToBeCredited/BIC!=""' >
											<tr>
												<td>
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('BIC','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/>:</h3>
												</td>
												<td>
													<xsl:apply-templates select="AccountToBeCredited/BIC"/>
												</td>
											</tr>
										</xsl:if>
									</table>
								</xsl:if>
							</td>
							<td width="10%" valign="top">
								<xsl:choose>
									<xsl:when test='PaymentReconciliationReference!=""' >
										<xsl:value-of select="PaymentReconciliationReference"/>
									</xsl:when>
									<xsl:otherwise>
										<font color="FFFFFF">_</font>
									</xsl:otherwise>
								</xsl:choose>
							</td>
							<td width="10%" valign="top">
								<xsl:choose>
									<xsl:when test='DebitReconciliationReference!=""' >
										<xsl:value-of select="DebitReconciliationReference"/>
									</xsl:when>
									<xsl:otherwise>
										<font color="FFFFFF">_</font>
									</xsl:otherwise>
								</xsl:choose>
							</td>
							<td width="10%" valign="top">
								<xsl:choose>
									<xsl:when test='RegulatoryReportingData!=""' >
										<xsl:value-of select="RegulatoryReportingData"/>
									</xsl:when>
									<xsl:otherwise>
										<font color="FFFFFF">_</font>
									</xsl:otherwise>
								</xsl:choose>
							</td>
							<td width="10%" valign="top">
								<xsl:choose>
									<xsl:when test='CollectionAdditionalInformation!=""' >
										<xsl:apply-templates select="CollectionAdditionalInformation"/>
									</xsl:when>
									<xsl:otherwise>
										<font color="FFFFFF">_</font>
									</xsl:otherwise>
								</xsl:choose>
							</td>
						</tr>
					</xsl:for-each>
				</table>
			</td>
		</tr>
	</xsl:template>
	<xsl:template match="/*/FileHeader/FactoringAssignmentData/PaymentDetails">
		<xsl:variable name="lang" select="/*/Invoices/Invoice/InvoiceIssueData/LanguageName"/>
		<tr>
			<td colspan="3" width="100%">
				<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('PaymentData','',concat('lang_',$lang))" />
				<h3><i><xsl:value-of select="$traduccion"/></i></h3>
			</td>
		</tr>
		<tr>
			<td colspan="3" width="100%">
				<table bordercolor="#e6f0fa" border="1" cellpadding="2" cellspacing="0" width="100%">
					<tr>
						<td width="10%" align="center">
							<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('DueDate','',concat('lang_',$lang))" />
							<h3><xsl:value-of select="$traduccion"/></h3>
						</td>
						<td width="10%" align="center">
							<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Amount','',concat('lang_',$lang))" />
							<h3><xsl:value-of select="$traduccion"/></h3>
						</td>
						<td width="10%" align="center">
							<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('PaymentMean','',concat('lang_',$lang))" />
							<h3><xsl:value-of select="$traduccion"/></h3>
						</td>
						<td width="15%" align="center">
							<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('DebitAccount','',concat('lang_',$lang))" />
							<h3><xsl:value-of select="$traduccion"/></h3>
						</td>
						<td width="15%" align="center">
							<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('CreditAccount','',concat('lang_',$lang))" />
							<h3><xsl:value-of select="$traduccion"/></h3>
						</td>
						<td width="10%" align="center">
							<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('PaymentReference','',concat('lang_',$lang))" />
							<h3><xsl:value-of select="$traduccion"/></h3>
						</td>
						<td width="10%" align="center">
							<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('DebitReference','',concat('lang_',$lang))" />
							<h3><xsl:value-of select="$traduccion"/></h3>
						</td>
						<td width="10%" align="center">
							<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('RegulatoryReportingData','',concat('lang_',$lang))" />
							<h3><xsl:value-of select="$traduccion"/></h3>
						</td>
						<td width="10%" align="center">
							<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Comments','',concat('lang_',$lang))" />
							<h3><xsl:value-of select="$traduccion"/></h3>
						</td>
					</tr>
					<xsl:for-each select="Installment">
						<tr>
							<td width="10%" valign="top" align="center">
								<xsl:value-of select="substring(InstallmentDueDate,9,2)"/>-<xsl:value-of select="substring(InstallmentDueDate,6,2)"/>-<xsl:value-of select="substring(InstallmentDueDate,1,4)"/>
							</td>
							<td width="10%" valign="top" align="right">
								<xsl:variable name="decimales"><xsl:value-of select="InstallmentAmount"/></xsl:variable>
								<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>			
							</td>
							<td width="10%" valign="top"  align="center">
								<xsl:variable name="toTranslate" select="PaymentMeans"/>
								<xsl:variable name="traduccion" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('PaymentMeansType',$toTranslate,concat('lang_',$lang))"/>
								<xsl:value-of select="$traduccion"/>
							</td>
							<td width="15%" valign="top"  align="center">
								<xsl:if test='AccountToBeDebited!=""' >
									<table border="0" cellpadding="2" cellspacing="0" width="80%" align="center">											
										<tr>
											<xsl:choose>
												<xsl:when test='AccountToBeDebited/IBAN!=""'>
													<td width="30%">
														<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('IBAN','',concat('lang_',$lang))" />
														<h3><xsl:value-of select="$traduccion"/>:</h3>
													</td>
													<td width="70%">
														<xsl:value-of select="AccountToBeDebited/IBAN"/>
													</td>
												</xsl:when>
												<xsl:when test='AccountToBeDebited/AccountNumber!=""'>	
													<td width="30%">
														<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('AccountNumber','',concat('lang_',$lang))" />
														<h3><xsl:value-of select="$traduccion"/>:</h3>
													</td>
													<td width="70%">
														<xsl:value-of select="AccountToBeDebited/AccountNumber"/>
													</td>
												</xsl:when>
												<xsl:otherwise>
													<td width="30%">
														<font color="FFFFFF">_</font>
													</td>
													<td width="70%">
														<font color="FFFFFF">_</font>
													</td>
												</xsl:otherwise>
											</xsl:choose>
										</tr>
										<xsl:if test='AccountToBeDebited/BankCode!=""' >
											<tr>
												<td>
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Entity','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/>:</h3>
												</td>
												<td>
													<xsl:apply-templates select="AccountToBeDebited/BankCode"/>
												</td>
											</tr>
										</xsl:if>
										<xsl:if test='AccountToBeDebited/BranchCode!=""' >
											<tr>
												<td>
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Office','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/>:</h3>
												</td>
												<td>
													<xsl:apply-templates select="AccountToBeDebited/BranchCode"/>
												</td>
											</tr>
										</xsl:if>
										<xsl:if test='AccountToBeDebited/BranchInSpainAddress!=""' >
											<tr>
												<td valign="top">
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('AddressLowcase','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/>:</h3>
												</td>
												<td>
													<xsl:value-of select="AccountToBeDebited/BranchInSpainAddress/Address"/><br/>
													<xsl:value-of select="AccountToBeDebited/BranchInSpainAddress/PostCode"/><font color="FFFFFF">__</font>
													<xsl:value-of select="AccountToBeDebited/BranchInSpainAddress/Town"/><br/>
													<xsl:value-of select="AccountToBeDebited/BranchInSpainAddress/Province"/><br/>
													<xsl:value-of select="AccountToBeDebited/BranchInSpainAddress/CountryCode"/>
												</td>
											</tr>	
										</xsl:if>													
										<xsl:if test='AccountToBeDebited/OverseasBranchAddress!=""' >
											<tr>
												<td valign="top">
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('AddressLowcase','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/>:</h3>
												</td>
												<td>
													<xsl:value-of select="AccountToBeDebited/OverseasBranchAddress/Address"/><br/>
													<xsl:value-of select="AccountToBeDebited/OverseasBranchAddress/PostCodeAndTown"/><br/>
													<xsl:value-of select="AccountToBeDebited/OverseasBranchAddress/Province"/><br/>
													<xsl:value-of select="AccountToBeDebited/OverseasBranchAddress/CountryCode"/>
												</td>
											</tr>
										</xsl:if>
										<xsl:if test='AccountToBeDebited/BIC!=""' >
											<tr>
												<td>
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('BIC','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/>:</h3>
												</td>
												<td>
													<xsl:apply-templates select="AccountToBeDebited/BIC"/>
												</td>
											</tr>
										</xsl:if>
									</table>
								</xsl:if>
							</td>	
							<td width="15%" valign="top"  align="center">		
								<xsl:if test='AccountToBeCredited!=""' >
									<table border="0" cellpadding="2" cellspacing="0" width="80%" align="center">											
										<tr>
											<xsl:choose>
												<xsl:when test='AccountToBeCredited/IBAN!=""'>
													<td width="30%">
														<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('IBAN','',concat('lang_',$lang))" />
														<h3><xsl:value-of select="$traduccion"/>:</h3>
													</td>
													<td width="70%">
														<xsl:value-of select="AccountToBeCredited/IBAN"/>
													</td>
												</xsl:when>
												<xsl:when test='AccountToBeCredited/AccountNumber!=""'>	
													<td width="30%">
														<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('AccountNumber','',concat('lang_',$lang))" />
														<h3><xsl:value-of select="$traduccion"/>:</h3>
													</td>
													<td width="70%">
														<xsl:value-of select="AccountToBeCredited/AccountNumber"/>
													</td>
												</xsl:when>
												<xsl:otherwise>
													<td width="30%">
														<font color="FFFFFF">_</font>
													</td>
													<td width="70%">
														<font color="FFFFFF">_</font>
													</td>
												</xsl:otherwise>
											</xsl:choose>
										</tr>
										<xsl:if test='AccountToBeCredited/BankCode!=""' >
											<tr>
												<td>
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Entity','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/>:</h3>
												</td> 
												<td>
													<xsl:apply-templates select="AccountToBeCredited/BankCode"/>
												</td>
											</tr>
										</xsl:if>
										<xsl:if test='AccountToBeCredited/BranchCode!=""' >
											<tr>
												<td>
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Office','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/>:</h3>
												</td>
												<td>
													<xsl:apply-templates select="AccountToBeCredited/BranchCode"/>
												</td>
											</tr>
										</xsl:if>
										<xsl:if test='AccountToBeCredited/BranchInSpainAddress!=""' >
											<tr>
												<td valign="top">
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('AddressLowcase','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/>:</h3>
												</td>
												<td>
													<xsl:value-of select="AccountToBeCredited/BranchInSpainAddress/Address"/><br/>
													<xsl:value-of select="AccountToBeCredited/BranchInSpainAddress/PostCode"/><font color="FFFFFF">__</font>
													<xsl:value-of select="AccountToBeCredited/BranchInSpainAddress/Town"/><br/>
													<xsl:value-of select="AccountToBeCredited/BranchInSpainAddress/Province"/><br/>
													<xsl:value-of select="AccountToBeCredited/BranchInSpainAddress/CountryCode"/>
												</td>
											</tr>	
										</xsl:if>													
										<xsl:if test='AccountToBeCredited/OverseasBranchAddress!=""' >
											<tr>
												<td valign="top">
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('AddressLowcase','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/>:</h3>
												</td>
												<td>
													<xsl:value-of select="AccountToBeCredited/OverseasBranchAddress/Address"/><br/>
													<xsl:value-of select="AccountToBeCredited/OverseasBranchAddress/PostCodeAndTown"/><br/>
													<xsl:value-of select="AccountToBeCredited/OverseasBranchAddress/Province"/><br/>
													<xsl:value-of select="AccountToBeCredited/OverseasBranchAddress/CountryCode"/>
												</td>
											</tr>
										</xsl:if>
										<xsl:if test='AccountToBeCredited/BIC!=""' >
											<tr>
												<td>
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('BIC','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/>:</h3>
												</td>
												<td>
													<xsl:apply-templates select="AccountToBeCredited/BIC"/>
												</td>
											</tr>
										</xsl:if>
									</table>
								</xsl:if>
							</td>
							<td width="10%" valign="top">
								<xsl:choose>
									<xsl:when test='PaymentReconciliationReference!=""' >
										<xsl:value-of select="PaymentReconciliationReference"/>
									</xsl:when>
									<xsl:otherwise>
										<font color="FFFFFF">_</font>
									</xsl:otherwise>
								</xsl:choose>
							</td>
							<td width="10%" valign="top">
								<xsl:choose>
									<xsl:when test='DebitReconciliationReference!=""' >
										<xsl:value-of select="DebitReconciliationReference"/>
									</xsl:when>
									<xsl:otherwise>
										<font color="FFFFFF">_</font>
									</xsl:otherwise>
								</xsl:choose>
							</td>
							<td width="10%" valign="top">
								<xsl:choose>
									<xsl:when test='RegulatoryReportingData!=""' >
										<xsl:value-of select="RegulatoryReportingData"/>
									</xsl:when>
									<xsl:otherwise>
										<font color="FFFFFF">_</font>
									</xsl:otherwise>
								</xsl:choose>
							</td>
							<td width="10%" valign="top">
								<xsl:choose>
									<xsl:when test='CollectionAdditionalInformation!=""' >
										<xsl:apply-templates select="CollectionAdditionalInformation"/>
									</xsl:when>
									<xsl:otherwise>
										<font color="FFFFFF">_</font>
									</xsl:otherwise>
								</xsl:choose>
							</td>
						</tr>
					</xsl:for-each>
				</table>
			</td>
		</tr>
	</xsl:template>
	<xsl:template match="/*/Invoices/Invoice/LegalLiterals">
		<xsl:variable name="lang" select="/*/Invoices/Invoice/InvoiceIssueData/LanguageName"/>
		<tr>
			<td><font color="FFFFFF">_</font></td>
		</tr>
		<tr>
			<td>
				<hr color="#a0d7a0"/>
			</td>
		</tr>
		<tr>
			<td>
				<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Literals','',concat('lang_',$lang))" />
				<h2><xsl:value-of select="$traduccion"/></h2>
			</td>
		</tr>
		<tr>
			<td><font color="FFFFFF">_</font></td>
		</tr>
		<tr>
			<td width="100%">
				<table bordercolor="#e6f0fa" border="1" cellpadding="2" cellspacing="0" width="50%">
					<tr>
						<td align="center">
							<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Mention','',concat('lang_',$lang))" />
							<h3><xsl:value-of select="$traduccion"/></h3>
						</td>						
					</tr>
					<xsl:for-each select="LegalReference">
						<tr>
							<td align="center">
								<xsl:value-of select="."/>
							</td>						
						</tr>
					</xsl:for-each>
				</table>
			</td>
		</tr>
	</xsl:template>
	<xsl:template match="/*/Invoices/Invoice/AdditionalData">
		<xsl:variable name="lang" select="/*/Invoices/Invoice/InvoiceIssueData/LanguageName"/>
		<tr>
			<td><font color="FFFFFF">_</font></td>
		</tr>
		<tr>
			<td>
				<hr color="#a0d7a0"/>
			</td>
		</tr>
		<tr>
			<td width="100%">
				<table border="0" cellpadding="2" cellspacing="0" width="100%">
					<tr>
						<td colspan="2">
							<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('AdditionalData','',concat('lang_',$lang))" />
							<h2><xsl:value-of select="$traduccion"/></h2>
						</td>
					</tr>
					<tr>
						<td colspan="2"><font color="FFFFFF">_</font></td>
					</tr>
					<xsl:if test='RelatedInvoice!=""'>
						<tr>
							<td width="50%" colspan="2">
								<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('AssociatedInvoice','',concat('lang_',$lang))" />
								<h3><xsl:value-of select="$traduccion"/>:</h3>
								<font color="FFFFFF">__</font>										
								<xsl:value-of select="RelatedInvoice"/><br/>
							</td>
						</tr>
					</xsl:if>
					<xsl:if test='InvoiceAdditionalInformation!=""'>
						<tr>
							<td width="50%" colspan="2">
								<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Comments','',concat('lang_',$lang))" />
								<h3><xsl:value-of select="$traduccion"/>:</h3>
								<font color="FFFFFF">__</font>
								<xsl:apply-templates select="InvoiceAdditionalInformation"/>							
							</td>
						</tr>
					</xsl:if>					
					<xsl:if test='RelatedDocuments!=""'>
						<tr>
							<td colspan="2">									
								<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('RelatedDocuments','',concat('lang_',$lang))" />
								<h3><xsl:value-of select="$traduccion"/></h3>
								<table bordercolor="#e6f0fa" border="1" cellpadding="2" cellspacing="0" width="100%">
									<tr>
										<td width="10%" valign="top" align="center">
											<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Compression','',concat('lang_',$lang))" />
											<h3><xsl:value-of select="$traduccion2"/></h3>
										</td>
										<td width="10%" valign="top" align="center">
											<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Format','',concat('lang_',$lang))" />
											<h3><xsl:value-of select="$traduccion2"/></h3>
										</td>
										<td width="10%" valign="top" align="center">
											<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Encoding','',concat('lang_',$lang))" />
											<h3><xsl:value-of select="$traduccion2"/></h3>
										</td>
										<td width="35%" valign="top" align="center">
											<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Description','',concat('lang_',$lang))" />
											<h3><xsl:value-of select="$traduccion2"/></h3>
										</td>
										<td width="35%" valign="top" align="center">
											<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Data','',concat('lang_',$lang))" />
											<h3><xsl:value-of select="$traduccion2"/></h3>
										</td>
									</tr>
									<xsl:for-each select="RelatedDocuments/Attachment">
										<tr>
											<td width="10%">
												<xsl:choose>
													<xsl:when test='AttachmentCompressionAlgorithm!=""' >
														<xsl:apply-templates select="AttachmentCompressionAlgorithm"/>
													</xsl:when>
													<xsl:otherwise>
														-
													</xsl:otherwise>
												</xsl:choose>
											</td>
											<td width="10%">
												<xsl:choose>
													<xsl:when test='AttachmentFormat!=""' >
														<xsl:apply-templates select="AttachmentFormat"/>
													</xsl:when>
													<xsl:otherwise>
														-
													</xsl:otherwise>
												</xsl:choose>
											</td>
											<td width="10%">
												<xsl:choose>
													<xsl:when test='AttachmentEncoding!=""' >
														<xsl:apply-templates select="AttachmentEncoding"/>
													</xsl:when>
													<xsl:otherwise>
														-
													</xsl:otherwise>
												</xsl:choose>
											</td>
											<td width="35%">
												<xsl:choose>
													<xsl:when test='AttachmentDescription!=""' >
														<xsl:apply-templates select="AttachmentDescription"/>
													</xsl:when>
													<xsl:otherwise>
														-
													</xsl:otherwise>
												</xsl:choose>
											</td>
											<td width="35%">
												<xsl:choose>
													<xsl:when test='AttachmentData!=""' >
														<xsl:value-of select="substring(AttachmentData,1,30)"/>...
													</xsl:when>
													<xsl:otherwise>
														-
													</xsl:otherwise>
												</xsl:choose>
											</td>
										</tr>
									</xsl:for-each>
								</table>							
							</td>
						</tr>
					</xsl:if>					
				</table>
			</td>
		</tr>
	</xsl:template>
	<xsl:template match="/*/Invoices/Invoice/Items/InvoiceLine">		
		<xsl:param name="nFactura"/>
		<xsl:param name="lang"/>
		<tr id="{$nFactura}_{ItemDescription}" style="display:none">
			<td width="100%">
				<table border="0" cellpadding="2" cellspacing="0" width="100%">
					<tr>
						<td align="center" colspan="2">
							<h1><xsl:value-of select="ItemDescription"/></h1>
						</td>											
					</tr>
					<tr>
						<td align="right">
							<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('BackInvoice','',concat('lang_',$lang))" />
							<a href="#" onclick="mostrarDetalle('{$nFactura}','{ItemDescription}')"><xsl:value-of select="$traduccion"/></a>
						</td>
					</tr>
					<tr>
						<td colspan="3"><font color="FFFFFF">_</font></td>
					</tr>
					<tr>
						<td width="100%">
							<table bordercolor="#e6f0fa" border="1" cellpadding="2" cellspacing="0" width="100%">													
								<tr>
									<td>
										<table border="0" cellpadding="2" cellspacing="0" width="100%">
											<tr>
												<td align="center" width="25%">
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('LineOrder','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/></h3>
													<br/>
													<xsl:choose>
														<xsl:when test='SequenceNumber!=""' >
															<xsl:value-of select="SequenceNumber"/>
														</xsl:when>
														<xsl:otherwise>
															-
														</xsl:otherwise>
													</xsl:choose>
												</td>
												<td align="center" width="25%">
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Quantity','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/></h3>
													<br/><xsl:value-of select="Quantity"/>
												</td>
												<td align="center" width="25%">																		
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('UnitOfMeasure','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/></h3>
													<br/>
													<xsl:choose>
														<xsl:when test='UnitOfMeasure!=""' >
															<xsl:variable name="toTranslate" select="UnitOfMeasure"/>
															<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('UnitOfMeasure',$toTranslate,concat('lang_',$lang))"/>
															<xsl:value-of select="$traduccion2"/>	
														</xsl:when>
														<xsl:otherwise>
															-
														</xsl:otherwise>
													</xsl:choose>																		
												</td>
												<td align="center" width="25%">
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('ArticleId','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/></h3>
													<br/><xsl:value-of select="ArticleCode"/>
												</td>																	
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<table border="0" cellpadding="2" cellspacing="0" width="100%">
											<tr>
												<td align="center" width="25%">
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('IssuerFile','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/></h3>
													<br/>
													<xsl:choose>
														<xsl:when test='IssuerContractReference!="" and IssuerContractReference!=null and IssuerContractReference!=" "' >
															<xsl:value-of select="IssuerContractReference"/>
														</xsl:when>
														<xsl:otherwise>
															-
														</xsl:otherwise>
													</xsl:choose>
												</td>
												<td align="center" width="25%">
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('IssuerContractDate','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/></h3>
													<br/>
													<xsl:choose>
														<xsl:when test='IssuerContractDate!=""'>
															<xsl:value-of select="substring(IssuerContractDate,9,2)"/>-<xsl:value-of select="substring(IssuerContractDate,6,2)"/>-<xsl:value-of select="substring(IssuerContractDate,1,4)"/>
														</xsl:when>
														<xsl:otherwise>
															-
														</xsl:otherwise>
													</xsl:choose>
												</td>																		
												<td align="center" width="25%">
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('IssuerOperationReference','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/></h3>
													<br/>
													<xsl:choose>
														<xsl:when test='IssuerTransactionReference!=""' >
															<xsl:value-of select="IssuerTransactionReference"/>
														</xsl:when>
														<xsl:otherwise>
															-
														</xsl:otherwise>
													</xsl:choose>																			
												</td>
												<td align="center" width="25%">
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('IssuerTransactionDate','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/></h3>
													<br/>
													<xsl:choose>
														<xsl:when test='IssuerTransactionDate!=""'>
															<xsl:value-of select="substring(IssuerTransactionDate,9,2)"/>-<xsl:value-of select="substring(IssuerTransactionDate,6,2)"/>-<xsl:value-of select="substring(IssuerTransactionDate,1,4)"/>
														</xsl:when>
														<xsl:otherwise>
															-
														</xsl:otherwise>
													</xsl:choose>
												</td>																		
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<table border="0" cellpadding="2" cellspacing="0" width="100%">
											<tr>																	
												<td align="center" width="25%">
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('ReceiverFile','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/></h3>
													<br/>
													<xsl:choose>
														<xsl:when test='ReceiverContractReference!=""' >
															<xsl:value-of select="ReceiverContractReference"/>
														</xsl:when>
														<xsl:otherwise>
															-
														</xsl:otherwise>
													</xsl:choose>
												</td>
												<td align="center" width="25%">
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('ReceiverContractDate','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/></h3>
													<br/>
													<xsl:choose>
														<xsl:when test='ReceiverContractDate!=""'>
															<xsl:value-of select="substring(ReceiverContractDate,9,2)"/>-<xsl:value-of select="substring(ReceiverContractDate,6,2)"/>-<xsl:value-of select="substring(ReceiverContractDate,1,4)"/>
														</xsl:when>
														<xsl:otherwise>
															-
														</xsl:otherwise>
													</xsl:choose>
												</td>	
												<td align="center" width="25%">
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('ReceiverOperationReference','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/></h3>
													<br/>
													<xsl:choose>
														<xsl:when test='ReceiverTransactionReference!=""' >
															<xsl:value-of select="ReceiverTransactionReference"/>
														</xsl:when>
														<xsl:otherwise>
															-
														</xsl:otherwise>
													</xsl:choose>
												</td>
												<td align="center" width="25%">
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('ReceiverTransactionDate','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/></h3>
													<br/>
													<xsl:choose>
														<xsl:when test='ReceiverTransactionDate!=""'>
															<xsl:value-of select="substring(ReceiverTransactionDate,9,2)"/>-<xsl:value-of select="substring(ReceiverTransactionDate,6,2)"/>-<xsl:value-of select="substring(ReceiverTransactionDate,1,4)"/>
														</xsl:when>
														<xsl:otherwise>
															-
														</xsl:otherwise>
													</xsl:choose>
												</td>	
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<table border="0" cellpadding="2" cellspacing="0" width="100%">
											<tr>																	
												<td align="center" width="50%">
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('FileReference','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/></h3>
													<br/>
													<xsl:choose>
														<xsl:when test='FileReference!=""' >
															<xsl:value-of select="FileReference"/>
														</xsl:when>
														<xsl:otherwise>
															-
														</xsl:otherwise>
													</xsl:choose>
												</td>
												<td align="center" width="50%">
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('FileDate','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/></h3>
													<br/>
													<xsl:choose>
														<xsl:when test='FileDate!=""'>
															<xsl:value-of select="substring(FileDate,9,2)"/>-<xsl:value-of select="substring(FileDate,6,2)"/>-<xsl:value-of select="substring(FileDate,1,4)"/>
														</xsl:when>
														<xsl:otherwise>
															-
														</xsl:otherwise>
													</xsl:choose>
												</td>
												</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<table border="0" cellpadding="2" cellspacing="0" width="100%">
											<tr>
												<td align="center" width="50%">
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('DetailPeriod','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/></h3><br/>
													<xsl:choose>
														<xsl:when test='LineItemPeriod!=""' >
															<xsl:value-of select="substring(LineItemPeriod/StartDate,9,2)"/>-<xsl:value-of select="substring(LineItemPeriod/StartDate,6,2)"/>-<xsl:value-of select="substring(LineItemPeriod/StartDate,1,4)"/> >> 
															<xsl:value-of select="substring(LineItemPeriod/EndDate,9,2)"/>-<xsl:value-of select="substring(LineItemPeriod/EndDate,6,2)"/>-<xsl:value-of select="substring(LineItemPeriod/EndDate,1,4)"/>
														</xsl:when>
														<xsl:otherwise>
															-
														</xsl:otherwise>
													</xsl:choose>
												</td>
												<td align="center" width="50%">
													<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TransactionDate','',concat('lang_',$lang))" />
													<h3><xsl:value-of select="$traduccion"/></h3>
													<br/>
													<xsl:choose>
														<xsl:when test='TransactionDate!=""' >
															<xsl:value-of select="substring(TransactionDate,9,2)"/>-<xsl:value-of select="substring(TransactionDate,6,2)"/>-<xsl:value-of select="substring(TransactionDate,1,4)"/>
													</xsl:when>
														<xsl:otherwise>
															-
														</xsl:otherwise>
													</xsl:choose>
												</td>																		
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</td>											
					</tr>
					<xsl:apply-templates select="DeliveryNotesReferences"/>
					<tr>
						<td><font color="FFFFFF">_</font></td>
					</tr>
					<tr>
						<td>
							<hr color="#a0d7a0"/>
						</td>
					</tr>
					<tr>
						<td>
							<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Amounts','',concat('lang_',$lang))" />
							<h2><xsl:value-of select="$traduccion"/></h2>
						</td>
					</tr>
					<tr>
						<td><font color="FFFFFF">_</font></td>
					</tr>
					<tr>
						<td width="100%">
							<table border="0" cellpadding="2" cellspacing="0" width="100%">
								<tr>
									<td width="80%" align="right">
										<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('UnitPriceWithoutTax','',concat('lang_',$lang))" />
										<h3><xsl:value-of select="$traduccion"/><font color="FFFFFF">___</font></h3>
									</td>
									<td width="20%" align="center">
										<table bordercolor="#e6f0fa" border="1" cellpadding="2" cellspacing="0" width="100%">
											<tr>
												<td align="right">
													<xsl:variable name="decimales"><xsl:value-of select="UnitPriceWithoutTax"/></xsl:variable>
													<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>														
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td><font color="FFFFFF">_</font></td>
					</tr>
					<tr>
						<td width="100%">
							<table border="0" cellpadding="2" cellspacing="0" width="100%">
								<tr>
									<td width="80%" align="right">
										<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TotalCost','',concat('lang_',$lang))" />
										<h3><xsl:value-of select="$traduccion"/><font color="FFFFFF">___</font></h3>
									</td>
									<td width="20%" align="center">
										<table bordercolor="#e6f0fa" border="1" cellpadding="2" cellspacing="0" width="100%">
											<tr>
												<td align="right">
													<xsl:variable name="decimales"><xsl:value-of select="TotalCost"/></xsl:variable>
													<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td><font color="FFFFFF">_</font></td>
					</tr>
					<xsl:if test='DiscountsAndRebates!=""' >
					<tr>
						<td>							
							<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Discounts','',concat('lang_',$lang))" />
							<h3><i><xsl:value-of select="$traduccion"/></i></h3>
							<table bordercolor="#e6f0fa" border="1" cellpadding="2" cellspacing="0" width="100%">
								<tr>
									<td width="70%" valign="top" align="center">
										<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Item','',concat('lang_',$lang))" />
										<h3><xsl:value-of select="$traduccion2"/></h3>
									</td>
									<td width="10%" valign="top" align="center">
										<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Type','',concat('lang_',$lang))" />
										<h3><xsl:value-of select="$traduccion2"/> (%)</h3>
									</td>
									<td width="20%" valign="top" align="center">
										<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Amount','',concat('lang_',$lang))" />
										<h3><xsl:value-of select="$traduccion2"/></h3>
									</td>
								</tr>
								<tr>
									<td width="70%" valign="top">
										<table border="0" cellpadding="2" cellspacing="0" width="100%">
											<xsl:for-each select="DiscountsAndRebates/Discount">
												<tr>
													<td width="100%">
														<xsl:apply-templates select="DiscountReason"/>
													</td>
												</tr>
											</xsl:for-each>
										</table>
									</td>
									<td width="10%" valign="top">
										<table border="0" cellpadding="2" cellspacing="0" width="100%">
											<xsl:for-each select="DiscountsAndRebates/Discount">
												<tr>
													<td width="100%" align="center">
														<xsl:choose>
															<xsl:when test='DiscountRate!=""' >
																<xsl:variable name="decimales"><xsl:value-of select="DiscountRate"/></xsl:variable>
																<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
															</xsl:when>
															<xsl:otherwise>
																-
															</xsl:otherwise>
														</xsl:choose>																	
													</td>
												</tr>
											</xsl:for-each>
										</table>
									</td>
									<td width="20%" valign="top">
										<table border="0" cellpadding="2" cellspacing="0" width="100%">
											<xsl:for-each select="DiscountsAndRebates/Discount">
												<tr>
													<td align="right">
														<xsl:variable name="decimales"><xsl:value-of select="DiscountAmount"/></xsl:variable>
														<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
													</td>
												</tr>
											</xsl:for-each>
										</table>
									</td>
								</tr>
							</table>							
						</td>
					</tr>
					<tr>
						<td><font color="FFFFFF">_</font></td>
					</tr>							
					</xsl:if>
					<xsl:if test='Charges!=""' >
					<tr>
						<td>							
							<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Charges','',concat('lang_',$lang))" />
							<h3><i><xsl:value-of select="$traduccion"/></i></h3>
							<table bordercolor="#e6f0fa" border="1" cellpadding="2" cellspacing="0" width="100%">
								<tr>
									<td width="70%" valign="top" align="center">
										<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Item','',concat('lang_',$lang))" />
										<h3><xsl:value-of select="$traduccion2"/></h3>
									</td>
									<td width="10%" valign="top" align="center">
										<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Type','',concat('lang_',$lang))" />
										<h3><xsl:value-of select="$traduccion2"/> (%)</h3>
									</td>
									<td width="20%" valign="top" align="center">
										<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Amount','',concat('lang_',$lang))" />
										<h3><xsl:value-of select="$traduccion2"/></h3>
									</td>
								</tr>
								<tr>
									<td width="70%" valign="top">
										<table border="0" cellpadding="2" cellspacing="0" width="100%">
											<xsl:for-each select="Charges/Charge">
												<tr>
													<td width="100%">
														<xsl:apply-templates select="ChargeReason"/>
													</td>
												</tr>
											</xsl:for-each>
										</table>
									</td>
									<td width="10%" valign="top">
										<table border="0" cellpadding="2" cellspacing="0" width="100%">
											<xsl:for-each select="Charges/Charge">
												<tr>
													<td width="100%" align="center">
														<xsl:choose>
															<xsl:when test='ChargeRate!=""' >
																<xsl:variable name="decimales"><xsl:value-of select="ChargeRate"/></xsl:variable>
																<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
															</xsl:when>
															<xsl:otherwise>
																-
															</xsl:otherwise>
														</xsl:choose>
													</td>
												</tr>
											</xsl:for-each>
										</table>
									</td>
									<td width="20%" valign="top">
										<table border="0" cellpadding="2" cellspacing="0" width="100%">
											<xsl:for-each select="Charges/Charge">
												<tr>
													<td align="right">
														<xsl:variable name="decimales"><xsl:value-of select="ChargeAmount"/></xsl:variable>
														<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
													</td>
												</tr>
											</xsl:for-each>
										</table>
									</td>
								</tr>
							</table>					
						</td>
					</tr>
					<tr>
						<td><font color="FFFFFF">_</font></td>
					</tr>							
					</xsl:if>
					<tr>
						<td width="100%">
							<table border="0" cellpadding="2" cellspacing="0" width="100%">
								<tr>
									<td width="80%" align="right">
										<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('GrossAmount','',concat('lang_',$lang))" />
										<h3><xsl:value-of select="$traduccion"/><font color="FFFFFF">___</font></h3>
									</td>
									<td width="20%" align="center">
										<table bordercolor="#e6f0fa" border="1" cellpadding="2" cellspacing="0" width="100%">
											<tr>
												<td align="right">
													<xsl:variable name="decimales"><xsl:value-of select="GrossAmount"/></xsl:variable>
													<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td><font color="FFFFFF">_</font></td>
					</tr>
					<xsl:if test='SpecialTaxableEvent!=""'>
					<tr>
						<td>									
							<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('SpecialTaxableEvent','',concat('lang_',$lang))" />
							<h3><i><xsl:value-of select="$traduccion"/></i></h3>
							<table bordercolor="#e6f0fa" border="1" cellpadding="2" cellspacing="0" width="100%">
								<tr>
									<td width="40%" valign="top" align="center">
										<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('SpecialTaxableEventCode','',concat('lang_',$lang))" />
										<h3><xsl:value-of select="$traduccion2"/></h3>
									</td>
									<td width="60%" valign="top" align="center">
										<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('SpecialTaxableEventReason','',concat('lang_',$lang))" />
										<h3><xsl:value-of select="$traduccion2"/></h3>
									</td>
								</tr>
								<tr>
									<td width="40%" valign="top" align="center">
										<xsl:variable name="toTranslate" select="SpecialTaxableEvent/SpecialTaxableEventCode"/>
										<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('SpecialTaxableEventCodeType',$toTranslate,concat('lang_',$lang))"/>
										<xsl:value-of select="$traduccion2"/>
									</td>
									<td width="60%" valign="top" align="center">
										<xsl:value-of select="SpecialTaxableEvent/SpecialTaxableEventReason"/>
									</td>
								</tr>
							</table>		
						</td>
					</tr>
					<tr>
						<td><font color="FFFFFF">_</font></td>
					</tr>
					</xsl:if>
					<xsl:if test='TaxesOutputs!=""' >
					<tr>
						<td>									
							<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TaxesOutputs','',concat('lang_',$lang))" />
							<h3><i><xsl:value-of select="$traduccion"/></i></h3>
							<xsl:choose>
								<xsl:when test='/*/FileHeader/Batch/InvoiceCurrencyCode="EUR"'>
									<table bordercolor="#e6f0fa" border="1" cellpadding="2" cellspacing="0" width="100%">
										<tr>
											<td width="10%" valign="center" align="center">
												<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TaxType','',concat('lang_',$lang))" />
												<h3><xsl:value-of select="$traduccion2"/></h3>
											</td>
											<td width="10%" valign="center" align="center">
												<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Type','',concat('lang_',$lang))" />
												<h3><xsl:value-of select="$traduccion2"/> (%)</h3>
											</td>
											<td width="20%" valign="center" align="center">
												<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TaxableBase','',concat('lang_',$lang))" />
												<h3><xsl:value-of select="$traduccion2"/></h3>
											</td>
											<td width="20%" valign="center" align="center">
												<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TaxAmount','',concat('lang_',$lang))" />
												<h3><xsl:value-of select="$traduccion2"/></h3>
											</td>
											<td width="10%" valign="center" align="center">
												<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('SpecialTaxableBase','',concat('lang_',$lang))" />
												<h3><xsl:value-of select="$traduccion2"/></h3>
											</td>
											<td width="10%" valign="center" align="center">
												<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('SpecialTaxAmount','',concat('lang_',$lang))" />
												<h3><xsl:value-of select="$traduccion2"/></h3>
											</td>
											<td width="10%" valign="center" align="center">
												<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('EquivalenceSurcharge','',concat('lang_',$lang))" />
												<h3><xsl:value-of select="$traduccion2"/></h3>
											</td>
											<td width="10%" valign="center" align="center">
												<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('EquivalenceSurchargeAmount','',concat('lang_',$lang))" />
												<h3><xsl:value-of select="$traduccion2"/></h3>
											</td>
										</tr>
										<xsl:for-each select="TaxesOutputs/Tax">
											<tr>
												<td>
													<xsl:variable name="toTranslate" select="TaxTypeCode"/>
													<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TaxTypeCode',$toTranslate,concat('lang_',$lang))"/>
													<xsl:value-of select="$traduccion2"/>
												</td>
												<td align="center">
													<xsl:choose>
														<xsl:when test='TaxRate!=""' >
															<xsl:variable name="decimales"><xsl:value-of select="TaxRate"/></xsl:variable>
															<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
														</xsl:when>
														<xsl:otherwise>
															-
														</xsl:otherwise>
													</xsl:choose>
												</td>
												<td align="right">
													<xsl:choose>
														<xsl:when test='TaxableBase!=""' >
															<xsl:variable name="decimales"><xsl:value-of select="TaxableBase/TotalAmount"/></xsl:variable>
															<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>																					
														</xsl:when>
														<xsl:otherwise>
															-
														</xsl:otherwise>
													</xsl:choose>																		
												</td>
												<td align="right">
													<xsl:choose>
														<xsl:when test='TaxAmount!=""' >
															<xsl:variable name="decimales"><xsl:value-of select="TaxAmount/TotalAmount"/></xsl:variable>
															<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>																					
														</xsl:when>
														<xsl:otherwise>
															-
														</xsl:otherwise>
													</xsl:choose>
												</td>
												<td align="right">
													<xsl:choose>
														<xsl:when test='SpecialTaxableBase!=""' >
															<xsl:variable name="decimales"><xsl:value-of select="SpecialTaxableBase/TotalAmount"/></xsl:variable>
															<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>																					
														</xsl:when>
														<xsl:otherwise>
															-
														</xsl:otherwise>
													</xsl:choose>
												</td>
												<td align="right">
													<xsl:choose>
														<xsl:when test='SpecialTaxAmount!=""' >
															<xsl:variable name="decimales"><xsl:value-of select="SpecialTaxAmount/TotalAmount"/></xsl:variable>
															<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>																					
														</xsl:when>
														<xsl:otherwise>
															-
														</xsl:otherwise>
													</xsl:choose>
												</td>
												<td align="right">
													<xsl:choose>
														<xsl:when test='EquivalenceSurcharge!=""' >
															<xsl:variable name="decimales"><xsl:value-of select="EquivalenceSurcharge"/></xsl:variable>
															<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>																					
														</xsl:when>
														<xsl:otherwise>
															-
														</xsl:otherwise>
													</xsl:choose>
												</td>
												<td align="right">
													<xsl:choose>
														<xsl:when test='EquivalenceSurchargeAmount!=""' >
															<xsl:variable name="decimales"><xsl:value-of select="EquivalenceSurchargeAmount/TotalAmount"/></xsl:variable>
															<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>																					
														</xsl:when>
														<xsl:otherwise>
															-
														</xsl:otherwise>
													</xsl:choose>
												</td>
											</tr>
										</xsl:for-each>
									</table>
								</xsl:when>
								<xsl:otherwise>
									<table bordercolor="#e6f0fa" border="1" cellpadding="2" cellspacing="0" width="100%">
										<tr>
											<td rowspan="2" width="50%" valign="center" align="center">
												<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TaxType','',concat('lang_',$lang))" />
												<h3><xsl:value-of select="$traduccion2"/></h3>
											</td>
											<td rowspan="2" width="10%" valign="center" align="center">
												<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Type','',concat('lang_',$lang))" />
												<h3><xsl:value-of select="$traduccion2"/> (%)</h3>
											</td>
											<td width="20%" valign="top" align="center" colspan="2">
												<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TaxableBase','',concat('lang_',$lang))" />
												<h3><xsl:value-of select="$traduccion2"/></h3>
											</td>
											<td width="20%" valign="top" align="center" colspan="2">
												<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TaxAmount','',concat('lang_',$lang))" />
												<h3><xsl:value-of select="$traduccion2"/></h3>
											</td>
											<td width="10%" valign="top" align="center" colspan="2">
												<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('SpecialTaxableBase','',concat('lang_',$lang))" />
												<h3><xsl:value-of select="$traduccion2"/></h3>
											</td>
											<td width="10%" valign="top" align="center" colspan="2">
												<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('SpecialTaxAmount','',concat('lang_',$lang))" />
												<h3><xsl:value-of select="$traduccion2"/></h3>
											</td>
											<td rowspan="2" width="10%" valign="center" align="center">
												<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('EquivalenceSurcharge','',concat('lang_',$lang))" />
												<h3><xsl:value-of select="$traduccion2"/></h3>
											</td>
											<td width="10%" valign="top" align="center" colspan="2">
												<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('EquivalenceSurchargeAmount','',concat('lang_',$lang))" />
												<h3><xsl:value-of select="$traduccion2"/></h3>
											</td>
										</tr>
										<tr>
											<td valign="top" align="center">
												<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('AmountLowcase','',concat('lang_',$lang))" />
												<h3><xsl:value-of select="$traduccion2"/></h3>
											</td>
											<td valign="top" align="center">
												<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('EquivalentLowcase','',concat('lang_',$lang))" />
												<h3><xsl:value-of select="$traduccion2"/></h3>
											</td>
											<td valign="top" align="center">														
												<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('AmountLowcase','',concat('lang_',$lang))" />
												<h3><xsl:value-of select="$traduccion2"/></h3>
											</td>
											<td valign="top" align="center">
												<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('EquivalentLowcase','',concat('lang_',$lang))" />
												<h3><xsl:value-of select="$traduccion2"/></h3>
											</td>
											<td valign="top" align="center">														
												<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('AmountLowcase','',concat('lang_',$lang))" />
												<h3><xsl:value-of select="$traduccion2"/></h3>
											</td>
											<td valign="top" align="center">
												<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('EquivalentLowcase','',concat('lang_',$lang))" />
												<h3><xsl:value-of select="$traduccion2"/></h3>
											</td>
											<td valign="top" align="center">														
												<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('AmountLowcase','',concat('lang_',$lang))" />
												<h3><xsl:value-of select="$traduccion2"/></h3>
											</td>
											<td valign="top" align="center">
												<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('EquivalentLowcase','',concat('lang_',$lang))" />
												<h3><xsl:value-of select="$traduccion2"/></h3>
											</td>
											<td valign="top" align="center">														
												<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('AmountLowcase','',concat('lang_',$lang))" />
												<h3><xsl:value-of select="$traduccion2"/></h3>
											</td>
											<td valign="top" align="center">
												<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('EquivalentLowcase','',concat('lang_',$lang))" />
												<h3><xsl:value-of select="$traduccion2"/></h3>
											</td>
										</tr>
										<xsl:for-each select="TaxesOutputs/Tax">
											<tr>
												<td width="100%">
													<xsl:variable name="toTranslate" select="TaxTypeCode"/>
													<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TaxTypeCode',$toTranslate,concat('lang_',$lang))"/>
													<xsl:value-of select="$traduccion2"/>
												</td>
												<td width="100%" align="center">
													<xsl:choose>
														<xsl:when test='TaxRate!=""' >
															<xsl:variable name="decimales"><xsl:value-of select="TaxRate"/></xsl:variable>
															<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
														</xsl:when>
														<xsl:otherwise>
															-
														</xsl:otherwise>
													</xsl:choose>
												</td>
												<td width="100%" align="right">
													<xsl:choose>
														<xsl:when test='TaxableBase/TotalAmount!=""' >
															<xsl:variable name="decimales"><xsl:value-of select="TaxableBase/TotalAmount"/></xsl:variable>
															<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
														</xsl:when>
														<xsl:otherwise>
															-
														</xsl:otherwise>
													</xsl:choose>
												</td>
											    <td width="100%" align="right">
													<xsl:choose>
														<xsl:when test='TaxableBase/EquivalentInEuros!=""' >
															<xsl:variable name="decimales"><xsl:value-of select="TaxableBase/EquivalentInEuros"/></xsl:variable>
															<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
														</xsl:when>
														<xsl:otherwise>
															-
														</xsl:otherwise>
													</xsl:choose>
												</td>
												<td align="right">
													<xsl:choose>
														<xsl:when test='TaxAmount/TotalAmount!=""' >
															<xsl:variable name="decimales"><xsl:value-of select="TaxAmount/TotalAmount"/></xsl:variable>
															<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
														</xsl:when>
														<xsl:otherwise>
															-
														</xsl:otherwise>
													</xsl:choose>
												</td>
												<td width="100%" align="right">
													<xsl:choose>
														<xsl:when test='TaxAmount/EquivalentInEuros!=""' >
															<xsl:variable name="decimales"><xsl:value-of select="TaxAmount/EquivalentInEuros"/></xsl:variable>
															<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
														</xsl:when>
														<xsl:otherwise>
															-
														</xsl:otherwise>
													</xsl:choose>
												</td>
												<td align="right">
													<xsl:choose>
														<xsl:when test='SpecialTaxableBase/TotalAmount!=""' >
															<xsl:variable name="decimales"><xsl:value-of select="SpecialTaxableBase/TotalAmount"/></xsl:variable>
															<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
														</xsl:when>
														<xsl:otherwise>
															-
														</xsl:otherwise>
													</xsl:choose>
												</td>
												<td width="100%" align="right">
													<xsl:choose>
														<xsl:when test='SpecialTaxableBase/EquivalentInEuros!=""' >
															<xsl:variable name="decimales"><xsl:value-of select="SpecialTaxableBase/EquivalentInEuros"/></xsl:variable>
															<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
														</xsl:when>
														<xsl:otherwise>
															-
														</xsl:otherwise>
													</xsl:choose>
												</td>
												<td align="right">
													<xsl:choose>
														<xsl:when test='SpecialTaxAmount/TotalAmount!=""' >
															<xsl:variable name="decimales"><xsl:value-of select="SpecialTaxAmount/TotalAmount"/></xsl:variable>
															<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
														</xsl:when>
														<xsl:otherwise>
															-
														</xsl:otherwise>
													</xsl:choose>
												</td>
												<td width="100%" align="right">
													<xsl:choose>
														<xsl:when test='SpecialTaxAmount/EquivalentInEuros!=""' >
															<xsl:variable name="decimales"><xsl:value-of select="SpecialTaxAmount/EquivalentInEuros"/></xsl:variable>
															<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
														</xsl:when>
														<xsl:otherwise>
															-
														</xsl:otherwise>
													</xsl:choose>
												</td>
												<td width="100%" align="center">
													<xsl:choose>
														<xsl:when test='EquivalenceSurcharge!=""' >
															<xsl:variable name="decimales"><xsl:value-of select="EquivalenceSurcharge"/></xsl:variable>
															<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
														</xsl:when>
														<xsl:otherwise>
															-
														</xsl:otherwise>
													</xsl:choose>
												</td>
												<td align="right">
													<xsl:choose>
														<xsl:when test='EquivalenceSurchargeAmount/TotalAmount!=""' >
															<xsl:variable name="decimales"><xsl:value-of select="EquivalenceSurchargeAmount/TotalAmount"/></xsl:variable>
															<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
														</xsl:when>
														<xsl:otherwise>
															-
														</xsl:otherwise>
													</xsl:choose>
												</td>
												<td width="100%" align="right">
													<xsl:choose>
														<xsl:when test='EquivalenceSurchargeAmount/EquivalentInEuros!=""' >
															<xsl:variable name="decimales"><xsl:value-of select="EquivalenceSurchargeAmount/EquivalentInEuros"/></xsl:variable>
															<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
														</xsl:when>
														<xsl:otherwise>
															-
														</xsl:otherwise>
													</xsl:choose>
												</td>
											</tr>
										</xsl:for-each>
									</table>
								</xsl:otherwise>
							</xsl:choose>
						</td>
					</tr>
					<tr>
						<td><font color="FFFFFF">_</font></td>
					</tr>							
					</xsl:if>
					<xsl:if test='TaxesWithheld!=""' >
					<tr>
						<td>									
							<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TaxesWithheld','',concat('lang_',$lang))" />
							<h3><i><xsl:value-of select="$traduccion"/></i></h3>
							<xsl:choose>
								<xsl:when test='/*/FileHeader/Batch/InvoiceCurrencyCode="EUR"'>
									<table bordercolor="#e6f0fa" border="1" cellpadding="2" cellspacing="0" width="100%">
										<tr>
											<td width="50%" valign="top" align="center">
												<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TaxType','',concat('lang_',$lang))" />
												<h3><xsl:value-of select="$traduccion2"/></h3>
											</td>
											<td width="10%" valign="top" align="center">
												<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Type','',concat('lang_',$lang))" />
												<h3><xsl:value-of select="$traduccion2"/> (%)</h3>
											</td>
											<td width="20%" valign="top" align="center">
												<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TaxableBase','',concat('lang_',$lang))" />
												<h3><xsl:value-of select="$traduccion2"/></h3>
											</td>
											<td width="20%" valign="top" align="center">
												<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TaxAmount','',concat('lang_',$lang))" />
												<h3><xsl:value-of select="$traduccion2"/></h3>
											</td>
										</tr>
										<tr>
											<td width="50%" valign="top">
												<table border="0" cellpadding="2" cellspacing="0" width="100%">
													<xsl:for-each select="TaxesWithheld/Tax">
														<tr>
															<td width="100%">
																<xsl:variable name="toTranslate" select="TaxTypeCode"/>
																<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TaxTypeCode',$toTranslate,concat('lang_',$lang))"/>
																<xsl:value-of select="$traduccion2"/>
															</td>
														</tr>
													</xsl:for-each>
												</table>
											</td>
											<td width="10%" valign="top">
												<table border="0" cellpadding="2" cellspacing="0" width="100%">
													<xsl:for-each select="TaxesWithheld/Tax">
														<tr>
															<td width="100%" align="center">
																<xsl:choose>
																	<xsl:when test='TaxRate!=""' >
																		<xsl:variable name="decimales"><xsl:value-of select="TaxRate"/></xsl:variable>
																		<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
																	</xsl:when>
																	<xsl:otherwise>
																		-
																	</xsl:otherwise>
																</xsl:choose>
															</td>
														</tr>
													</xsl:for-each>
												</table>
											</td>
											<td width="10%" valign="top">
												<table border="0" cellpadding="2" cellspacing="0" width="100%">
													<xsl:for-each select="TaxesWithheld/Tax">
														<tr>
															<td width="100%" align="right">
																<xsl:choose>
																	<xsl:when test='TaxableBase!=""' >
																		<xsl:variable name="decimales"><xsl:value-of select="TaxableBase/TotalAmount"/></xsl:variable>
																		<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
																	</xsl:when>
																	<xsl:otherwise>
																		-
																	</xsl:otherwise>
																</xsl:choose>
															</td>
														</tr>
													</xsl:for-each>
												</table>
											</td>
											<td width="20%" valign="top">
												<table border="0" cellpadding="2" cellspacing="0" width="100%">
													<xsl:for-each select="TaxesWithheld/Tax">
														<tr>
															<td align="right">
																<xsl:choose>
																	<xsl:when test='TaxAmount!=""' >
																		<xsl:variable name="decimales"><xsl:value-of select="TaxableBase/TotalAmount"/></xsl:variable>
																		<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
																	</xsl:when>
																	<xsl:otherwise>
																		-
																	</xsl:otherwise>
																</xsl:choose>
															</td>
														</tr>
													</xsl:for-each>
												</table>
											</td>
										</tr>
									</table>
								</xsl:when>
								<xsl:otherwise>
									<table bordercolor="#e6f0fa" border="1" cellpadding="2" cellspacing="0" width="100%">
										<tr>
											<td width="50%" valign="top" align="center">
												<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TaxType','',concat('lang_',$lang))" />
												<h3><xsl:value-of select="$traduccion2"/></h3>
											</td>
											<td width="10%" valign="top" align="center">
												<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Type','',concat('lang_',$lang))" />
												<h3><xsl:value-of select="$traduccion2"/> (%)</h3>
											</td>
											<td width="20%" valign="top" align="center" colspan="2">
												<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TaxableBase','',concat('lang_',$lang))" />
												<h3><xsl:value-of select="$traduccion2"/></h3>
											</td>
											<td width="20%" valign="top" align="center" colspan="2">
												<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TaxAmount','',concat('lang_',$lang))" />
												<h3><xsl:value-of select="$traduccion2"/></h3>
											</td>
										</tr>
										<tr>
											<td><font color="#ffffff">_</font></td>
											<td><font color="#ffffff">_</font></td>
											<td valign="top" align="center">
												<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('AmountLowcase','',concat('lang_',$lang))" />
												<h3><xsl:value-of select="$traduccion2"/></h3>
											</td>
											<td valign="top" align="center">
												<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('EquivalentLowcase','',concat('lang_',$lang))" />
												<h3><xsl:value-of select="$traduccion2"/></h3>
											</td>
											<td valign="top" align="center">														
												<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('AmountLowcase','',concat('lang_',$lang))" />
												<h3><xsl:value-of select="$traduccion2"/></h3>
											</td>
											<td valign="top" align="center">
												<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('EquivalentLowcase','',concat('lang_',$lang))" />
												<h3><xsl:value-of select="$traduccion2"/></h3>
											</td>
										</tr>
										<tr>
											<td width="30%" valign="top">
												<table border="0" cellpadding="2" cellspacing="0" width="100%">
													<xsl:for-each select="TaxesWithheld/Tax">
														<tr>
															<td width="100%">
																<xsl:variable name="toTranslate" select="TaxTypeCode"/>
																<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('TaxTypeCode',$toTranslate,concat('lang_',$lang))"/>
																<xsl:value-of select="$traduccion2"/>
															</td>
														</tr>
													</xsl:for-each>
												</table>
											</td>
											<td width="10%" valign="top">
												<table border="0" cellpadding="2" cellspacing="0" width="100%">
													<xsl:for-each select="TaxesWithheld/Tax">
														<tr>
															<td width="100%" align="center">
																<xsl:choose>
																	<xsl:when test='TaxRate!=""' >
																		<xsl:variable name="decimales"><xsl:value-of select="TaxRate"/></xsl:variable>
																		<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
																	</xsl:when>
																	<xsl:otherwise>
																		-
																	</xsl:otherwise>
																</xsl:choose>
															</td>
														</tr>
													</xsl:for-each>
												</table>
											</td>
											<td width="15%" valign="top">
												<table border="0" cellpadding="2" cellspacing="0" width="100%">
													<xsl:for-each select="TaxesWithheld/Tax">
														<tr>
															<td width="100%" align="right">
																<xsl:choose>
																	<xsl:when test='TaxableBase/TotalAmount!=""' >
																		<xsl:variable name="decimales"><xsl:value-of select="TaxableBase/TotalAmount"/></xsl:variable>
																		<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
																	</xsl:when>
																	<xsl:otherwise>
																		-
																	</xsl:otherwise>
																</xsl:choose>
															</td>
														</tr>
													</xsl:for-each>
												</table>
											</td>
											<td width="15%" valign="top">
												<table border="0" cellpadding="2" cellspacing="0" width="100%">
													<xsl:for-each select="TaxesWithheld/Tax">
														<tr>
															<td width="100%" align="right">
																<xsl:choose>
																	<xsl:when test='TaxableBase/EquivalentInEuros!=""' >
																		<xsl:variable name="decimales"><xsl:value-of select="TaxableBase/EquivalentInEuros"/></xsl:variable>
																		<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
																	</xsl:when>
																	<xsl:otherwise>
																		-
																	</xsl:otherwise>
																</xsl:choose>
															</td>
														</tr>
													</xsl:for-each>
												</table>
											</td>
											<td width="15%" valign="top">
												<table border="0" cellpadding="2" cellspacing="0" width="100%">
													<xsl:for-each select="TaxesWithheld/Tax">
														<tr>
															<td align="right">
																<xsl:choose>
																	<xsl:when test='TaxAmount/TotalAmount!=""' >
																		<xsl:variable name="decimales"><xsl:value-of select="TaxAmount/TotalAmount"/></xsl:variable>
																		<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
																	</xsl:when>
																	<xsl:otherwise>
																		-
																	</xsl:otherwise>
																</xsl:choose>
															</td>
														</tr>
													</xsl:for-each>
												</table>
											</td>
											<td width="15%" valign="top">
												<table border="0" cellpadding="2" cellspacing="0" width="100%">
													<xsl:for-each select="TaxesWithheld/Tax">
														<tr>
															<td align="right">
																<xsl:choose>
																	<xsl:when test='TaxAmount/EquivalentInEuros!=""' >
																		<xsl:variable name="decimales"><xsl:value-of select="TaxAmount/EquivalentInEuros"/></xsl:variable>
																		<xsl:value-of select="java:es.mityc.appfacturae.utils.visualize.VisualizeUtil.getValueKey($decimales)"/>
																	</xsl:when>
																	<xsl:otherwise>
																		-
																	</xsl:otherwise>
																</xsl:choose>
															</td>
														</tr>
													</xsl:for-each>
												</table>
											</td>
										</tr>
									</table>
								</xsl:otherwise>
							</xsl:choose>														
						</td>
					</tr>
					<tr>
						<td><font color="FFFFFF">_</font></td>
					</tr>							
					</xsl:if>
					<xsl:if test='AdditionalLineItemInformation!=""' >
						<tr>
							<td><font color="FFFFFF">_</font></td>
						</tr>
						<tr>
							<td>
								<hr color="#a0d7a0"/>
							</td>
						</tr>
						<tr>
							 <td width="100%">
								<table border="0" cellpadding="2" cellspacing="0" width="100%">
									<tr>
										<td>
											<h2>
												<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('Comments','',concat('lang_',$lang))" />
												<xsl:value-of select="$traduccion"/>
											</h2>
										</td>
									</tr>
									<tr>
										<td colspan="2"><font color="FFFFFF">_</font></td>        	
									</tr>
									<tr>
										<td width="100%">
											<xsl:apply-templates select="AdditionalLineItemInformation"/>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</xsl:if>
				</table>
			</td>
		</tr>
	</xsl:template>
	<xsl:template match="/*/Invoices/Invoice/Items/InvoiceLine/DeliveryNotesReferences">
		<xsl:variable name="lang" select="/*/Invoices/Invoice/InvoiceIssueData/LanguageName"/>
		<tr>
			<td><font color="FFFFFF">_</font></td>
		</tr>
		<tr>
			<td>
				<hr color="#a0d7a0"/>
			</td>
		</tr>
		<tr>
			 <td width="100%">
				<table border="0" cellpadding="2" cellspacing="0" width="100%">
					<tr>
						<td>
							<xsl:variable name="traduccion"	select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('DeliveryNotes','',concat('lang_',$lang))" />
							<h2><xsl:value-of select="$traduccion"/></h2>
						</td>
					</tr>
					<tr>
						<td colspan="2"><font color="FFFFFF">_</font></td>        	
					</tr>
					<tr>
						<td width="100%">
							<table bordercolor="#e6f0fa" border="1" cellpadding="2" cellspacing="0" width="50%">
								<tr>
									<td align="center">
										<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('DeliveryNumber','',concat('lang_',$lang))" />
										<h3><xsl:value-of select="$traduccion2"/></h3>
									</td>
									<td align="center">
										<xsl:variable name="traduccion2" select="java:es.mityc.appfacturae.utils.visualize.DictionaryLoader.getTranslation('DeliveryDate','',concat('lang_',$lang))" />
										<h3><xsl:value-of select="$traduccion2"/></h3>
									</td>						
								</tr>
								<xsl:for-each select="DeliveryNote">
									<tr>
										<td align="center">
											<xsl:value-of select="DeliveryNoteNumber"/>
										</td>
										<td align="center">
											<xsl:choose>
												<xsl:when test='DeliveryNoteDate!=""'>
													<xsl:value-of select="substring(DeliveryNoteDate,9,2)" />
													-
													<xsl:value-of select="substring(DeliveryNoteDate,6,2)" />
													-
													<xsl:value-of select="substring(DeliveryNoteDate,1,4)" />
												</xsl:when>
												<xsl:otherwise>
													<font color="FFFFFF">_</font>
												</xsl:otherwise>
											</xsl:choose>
										</td>						
									</tr>
								</xsl:for-each>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</xsl:template>	
</xsl:stylesheet>